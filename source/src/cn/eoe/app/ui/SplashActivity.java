package cn.eoe.app.ui;

import android.os.Bundle;
import android.os.Handler;
import cn.eoe.app.ui.base.BaseActivity;

import com.umeng.update.UmengUpdateAgent;

public class SplashActivity extends BaseActivity {

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			goHome();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		
		//getWindow().setBackgroundDrawable(
		//		getResources().getDrawable(R.drawable.splash_load));
		
	}

	protected void onResume() {
		super.onResume();
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}

	private void goHome() {
		openActivity(MainActivity.class);
		defaultFinish();
	};

}
