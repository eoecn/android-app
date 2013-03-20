package cn.eoe.app;

import java.io.File;

import android.app.Application;

public class MyApplication extends Application {
	public static  File 				cacheDir;
@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
//		if (!CommonUtil.sdCardIsAvailable()) { // sdcard not available
//			cacheDir = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/" + getPackageName()
//					+ "/eoecn/cache/imgs");
//		} else {
//			cacheDir = new File(Constants.CachePath.IMAGE_CACHE_PATH);
//		}
//		
		
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//        .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75)
//        .threadPoolSize(3) // default
//        .threadPriority(Thread.NORM_PRIORITY - 1) // default
//        .denyCacheImageMultipleSizesInMemory()
//        .offOutOfMemoryHandling()
//        .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // default
//        .discCache(new UnlimitedDiscCache(cacheDir)) // default
//        .discCacheSize(50 * 1024 * 1024)
//        .discCacheFileCount(100)
//        .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//        .imageDownloader(new URLConnectionImageDownloader()) // default
//        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//        .enableLogging()
//        .build();
//		ImageLoader.getInstance().init(config);


		
	}
	
	
	}
