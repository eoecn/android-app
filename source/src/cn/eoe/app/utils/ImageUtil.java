package cn.eoe.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import cn.eoe.app.R;
import cn.eoe.app.db.DBHelper;
import cn.eoe.app.db.ImageCacheColumn;

public class ImageUtil {
	private static final String TAG = "ImageUtil";
	private static int DayCount = 15;// 天数
	private static final long CLEARTIME = DayCount * 24 * 60 * 60 * 1000;
	/**
	 * 默认图片
	 */
	private final static int Default_Img = R.drawable.bg_load_default;

	private static Object lock = new Object();

	/**
	 * 内存图片软引用缓冲
	 */
	private static LinkedHashMap<String, SoftReference> imageCache = new LinkedHashMap<String, SoftReference>(
			20);

	/**
	 * 入口
	 * 
	 * @param imageUrl
	 * @param iv_item_image
	 * @param context
	 * @param callback
	 * @param b
	 */
	public static void setThumbnailView(String imageUrl,
			ImageView iv_item_image, Context context, ImageCallback callback,
			boolean b) {
		DBHelper dbHelper = DBHelper.getInstance(context);
		String md5 = ImageUtil.md5(imageUrl);
		
		
		// 缓存目录

		if (!CommonUtil.sdCardIsAvailable())/* true 为可用 */{
			String cachePath = context.getCacheDir().getAbsolutePath() + "/" + md5; // data里的缓存
			setThumbnailImage(iv_item_image, imageUrl, cachePath, dbHelper,
					callback, b);
			iv_item_image.setTag(cachePath);
		} else {
			String imagePath = getExternalCacheDir(context) + File.separator + md5; // sd卡
			setThumbnailImage(iv_item_image, imageUrl, imagePath, dbHelper,
					callback, b);
			iv_item_image.setTag(imagePath);
		}
	}

	/**
	 * 获得程序在sd开上的cahce目录
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static String getExternalCacheDir(Context context) {
		// android 2.2 以后才支持的特性
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir().getPath() + File.separator
					+ "img";
		}

		// Before Froyo we need to construct the external cache dir ourselves
		// 2.2以前我们需要自己构造
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/img/";
		return Environment.getExternalStorageDirectory().getPath() + cacheDir;
	}

	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * 设置图片函数
	 * 
	 * @param view
	 * @param imageUrl
	 * @param cachePath
	 * @param callback
	 * @param b
	 */
	private static void setThumbnailImage(ImageView view, String imageUrl,
			String cachePath, DBHelper dbHelper, ImageCallback callback,
			boolean b) {
		Bitmap bitmap = null;
		bitmap = ImageUtil.loadThumbnailImage(cachePath, imageUrl, dbHelper,
				callback, b);
		if (bitmap == null) {// 先查找数据库，再查找本地sd卡,若没有.再从网站加载，若网站上没有图片或错误时返回null
			// 设置默认图片
			view.setImageResource(Default_Img);
		} else {
			// 设置本地SD卡缓存图片
			view.setImageBitmap(bitmap);
		}
	}

	private static Bitmap getImageFromDB(String imagePath, String imageUrl,
			DBHelper dbHelper) {
		Cursor cursor = queryFromDbByImgUrl(dbHelper, imageUrl);
		if (cursor.moveToFirst()) {
			long currTimestamp = (new Date()).getTime();
			long timestamp = cursor.getLong(cursor
					.getColumnIndex(ImageCacheColumn.TIMESTAMP));
			long spanTime = currTimestamp - timestamp;
			int Past_time = cursor.getInt(cursor
					.getColumnIndex(ImageCacheColumn.PAST_TIME));
			if (spanTime > Past_time * 24 * 60 * 60 * 1000) {
				// 过期
				// 删除本地文件
				deleteImageFromLocal(imagePath);
				return null;
			} else {
				// 没过期
				return getImageFromLocal(imagePath);
			}
		} else {
			return null;
		}
	}

	private static Cursor queryFromDbByImgUrl(DBHelper dbHelper, String imageUrl) {
		// return dbHelper.query(ImageCacheColumn.TABLE_NAME, null,
		// ImageCacheColumn.Url + "=?", new String[] { imageUrl });
		return dbHelper.rawQuery("select * from " + ImageCacheColumn.TABLE_NAME
				+ "  where " + ImageCacheColumn.Url + "='" + imageUrl + "'",
				null);
	}

	/**
	 * 保存图片到SD卡
	 * 
	 * @param imagePath
	 * @param buffer
	 * @throws IOException
	 */
	public static void saveImage(String imagePath, byte[] buffer)
			throws IOException {
		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			File parentFile = f.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		}
	}

	/**
	 * 保存图片到缓存
	 * 
	 * @param imagePath
	 * @param bm
	 */
	public static void saveImage(String imagePath, Bitmap bm) {

		if (bm == null || imagePath == null || "".equals(imagePath)) {
			return;
		}

		File f = new File(imagePath);
		if (f.exists()) {
			return;
		} else {
			try {
				File parentFile = f.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				f.createNewFile();
				FileOutputStream fos;
				fos = new FileOutputStream(f);
				bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				f.delete();
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				f.delete();
			}
		}
	}

	private static void saveImageByDb(String imageUrl, DBHelper dbHelper) {
		String sql = null;
		if (queryFromDbByImgUrl(dbHelper, imageUrl).moveToFirst()) {
			sql = "update " + ImageCacheColumn.TABLE_NAME + " set "
					+ ImageCacheColumn.TIMESTAMP + "='"
					+ (new Date().getTime()) + "' where "
					+ ImageCacheColumn.Url + "='" + imageUrl + "'";
		} else {
			sql = "insert into " + ImageCacheColumn.TABLE_NAME + "("
					+ ImageCacheColumn.Url + "," + ImageCacheColumn.TIMESTAMP
					+ "," + ImageCacheColumn.PAST_TIME + ") values('"
					+ imageUrl + "'," + (new Date().getTime()) + "," + DayCount
					+ ")";
		}
		dbHelper.ExecSQL(sql);
	}

	/**
	 * 从SD卡加载图片
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getImageFromLocal(String imagePath) {
		File file = new File(imagePath);
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			file.setLastModified(System.currentTimeMillis());
			return bitmap;
		}
		return null;
	}

	/**
	 * 从本地文件中删除文件
	 * 
	 * @param imagePath
	 */
	private static void deleteImageFromLocal(String imagePath) {
		File file = new File(imagePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 从本地或者服务端异步加载缩略图图片
	 * 
	 * @return
	 * @param imagePath
	 *            本地缓存路径
	 * @param imgUrl
	 *            拼接后的请求路径
	 * @param callback
	 *            得到数据后的处理方法回调
	 * @throws IOException
	 */
	public static Bitmap loadThumbnailImage(final String imagePath,
			final String imgUrl, final DBHelper dbHelper,
			final ImageCallback callback, final boolean b) {
		// 在软链接缓存中，则返回Bitmap对象
		if (imageCache.containsKey(imgUrl)) {
			SoftReference reference = imageCache.get(imgUrl);
			Bitmap bitmap = (Bitmap) reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		// 若软链接缓存没有
		Bitmap bitmap = null;
		// 查询数据库 返回bitmap
		bitmap = getImageFromDB(imagePath, imgUrl, dbHelper);// 从本地加载
		if (bitmap != null) {
			return bitmap;
		} else {
			// 从网上加载
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.obj != null) {
						Bitmap bitmap = (Bitmap) msg.obj;
						callback.loadImage(bitmap, imagePath);
					}
				}
			};
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						URL url = new URL(imgUrl);
						URLConnection conn = url.openConnection();
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						conn.connect();
						InputStream in = conn.getInputStream();
						BitmapFactory.Options options = new Options();
						options.inSampleSize = 1;
						Bitmap bitmap = BitmapFactory.decodeStream(in,
								new Rect(0, 0, 0, 0), options);
						imageCache.put(imgUrl, new SoftReference(bitmap));

						Message msg = handler.obtainMessage();
						msg.obj = bitmap;
						handler.sendMessage(msg);
						if (bitmap != null) {
							// 保存文件到sd卡
							saveImage(imagePath, bitmap);
							// 保存到数据库
							saveImageByDb(imgUrl, dbHelper);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
						Log.e(ImageUtil.class.getName(), "图片url不存在");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			ThreadPoolManager.getInstance().addTask(runnable);
		}
		return null;
	}

	/**
	 * MD5
	 * 
	 * @param paramString
	 * @return
	 */
	private static String md5(String paramString) {
		return MD5.encode(paramString);
	}

	// ///////////////////////////////////////////////////////////////////////
	// 公共方法

	public interface ImageCallback {
		public void loadImage(Bitmap bitmap, String imagePath);
	}

	/**
	 * 每次打开含有大量图片的activity时,开一个新线程,检查并清理缓存
	 * 
	 * @param context
	 */
	public static void checkCache(final Context context) {
		new Thread() {
			public void run() {
				int state = 0;// 记录清除结果 0为都没清除, 1为只清除了sd卡, 2为只清除了rom Cache ,3
								// 为都清除了
				String cacheS = "0M";
				String cacheD = "0M";
				File sdCache = new File(getExternalCacheDir(context)); // sd卡"mnt/sdcard/android/data/cn.eoe.app/cache/";
				File cacheDir = context.getCacheDir(); // 手机data/data/com.mengniu.app/cache
				try {
					if (sdCache != null && sdCache.exists()) {
						long sdFileSize = getFileSize(sdCache);
						if (sdFileSize > 1024 * 1024 * 50) {
							// SD需要清理
							long clearFileSize = clear(sdCache);
							state += 1;
							cacheS = clearFileSize + "";
						}
					}
					if (cacheDir != null && cacheDir.exists()) {
						long cacheFileSize = getFileSize(cacheDir);
						if (cacheFileSize > 1024 * 1024 * 50) {
							// ROM需要清理
							long clearFileSize = clear(cacheDir);
							state += 2;
							cacheD = clearFileSize + "";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 清除路径
	 * 
	 * @param cacheDir
	 * @return
	 */
	public static long clear(File cacheDir) {
		long clearFileSize = 0;
		File[] files = cacheDir.listFiles();
		if (files == null)
			return 0;
		for (File f : files) {
			if (f.isFile()) {
				if (System.currentTimeMillis() - f.lastModified() > CLEARTIME) {
					long fileSize = f.length();
					if (f.delete()) {
						clearFileSize += fileSize;
					}
				}
			} else {
				clear(f);
			}
		}
		return clearFileSize;
	}

	/**
	 * 取得文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

}
