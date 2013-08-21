package cn.eoe.app.utils;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.os.StatFs;

public class CommonUtil {

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return true为可用，否则为不可用
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	/**
	 * Checks if there is enough Space on SDCard
	 * 
	 * @param updateSize
	 *            Size to Check
	 * @return True if the Update will fit on SDCard, false if not enough space on SDCard Will also return false, if the SDCard is
	 *         not mounted as read/write
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return (updateSize < getRealSizeOnSdcard());
	}

	/**
	 * get the space is left over on sdcard
	 */
	public static long getRealSizeOnSdcard() {
		File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Checks if there is enough Space on phone self
	 * 
	 */
	public static boolean enoughSpaceOnPhone(long updateSize) {
		return getRealSizeOnPhone() > updateSize;
	}

	/**
	 * get the space is left over on phone self
	 */
	public static long getRealSizeOnPhone() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}
	
	/**
	 * 根据手机分辨率从dp转成px
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static  int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
	  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
	public static  int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f)-15;  
    }  

	/**
     * 检测是否存在摄像头设备
     * */
	public static boolean isCameraHardwareIsExist( Context context ) {
		boolean hasCamera = false;
		if ( null == context ) {
			return hasCamera;
		}

		if ( context.getPackageManager( ).hasSystemFeature(
				PackageManager.FEATURE_CAMERA ) ){
			if( null != getCameraInstance( ) ){
				hasCamera = true;
			}else{
				hasCamera = false;
			}
		} else {
			hasCamera = false;
		}

		return hasCamera;
	}

	private static Camera getCameraInstance( ){
		Camera camera = null;
		try {
			camera = Camera.open( );// 试图获取Camera实例
		}catch( Exception e ){
			// 摄像头不可用（正被占用或不存在）
			e.printStackTrace( );
		}

		return camera;// 不可用则返回null
	}
}
