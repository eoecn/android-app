package cn.eoe.app.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.eoe.app.R;
import cn.eoe.app.biz.UserDao;
import cn.eoe.app.https.HttpUtils;
import cn.eoe.app.ui.base.BaseActivity;
import cn.eoe.app.utils.IntentUtil;

public class UserLoginActivity extends BaseActivity implements OnClickListener {

	private EditText editKey;
	private ImageView imgTitleButton;
	private LinearLayout goHome;
	private Button mBtnAnother;
	private Button mBtnBind;
	private UserDao mUserDao;
	private SharedPreferences share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_activity);
		share = UserLoginActivity.this.getSharedPreferences(
				UserLoginUidActivity.SharedName, Context.MODE_PRIVATE);
		mUserDao = new UserDao(this);
		initControl();
	}

	private void initControl() {
		imgTitleButton = (ImageView) findViewById(R.id.imageview_user_title);
		imgTitleButton.setOnClickListener(this);
		goHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		goHome.setOnClickListener(this);
		editKey = (EditText) findViewById(R.id.edittext_user_key);
		editKey.setOnClickListener(this);
		mBtnAnother = (Button) findViewById(R.id.login_button_another);
		mBtnAnother.setOnClickListener(this);
		mBtnBind = (Button) findViewById(R.id.user_login_bind);
		mBtnBind.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edittext_user_key:
			break;
		case R.id.imageview_user_title:

			break;
		case R.id.Linear_above_toHome:
			if (share.contains(UserLoginUidActivity.KEY)
					&& !share.getString(UserLoginUidActivity.KEY, "")
							.equals("")) {
				IntentUtil.start_activity(this, UserCenterActivity.class);
				finish();
			} else {
				showLongToast(getResources().getString(
						R.string.user_center_error));
			}
			break;
		case R.id.login_button_another:
			IntentUtil.start_activity(this, UserLoginUidActivity.class);
			finish();
			break;
		case R.id.user_login_bind:
		    String key = editKey.getText().toString().trim();
		    if(TextUtils.isEmpty(key)) {
		        Toast.makeText(this, R.string.user_login_enter_key, Toast.LENGTH_SHORT).show();
		        break;
		    }
			new LoginAsyncTask().execute();
			break;
		}
	}

	class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showAlertDialog("温馨提示", "正在登录请稍等一下~");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (!HttpUtils.isNetworkAvailable(UserLoginActivity.this)) {
				showLongToast(getResources().getString(R.string.httpisNull));
				return false;
			}
			try {
				return mUserDao.mapperJson(editKey.getText().toString()) != null ? true
						: false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mAlertDialog.dismiss();
			if (result) {
				showLongToast("登录成功");
				Editor edit = share.edit();
				edit.putString(UserLoginUidActivity.KEY, editKey.getText()
						.toString());
				edit.commit();
				IntentUtil.start_activity(UserLoginActivity.this,
						UserCenterActivity.class);
				finish();
			} else {
				showLongToast(getResources().getString(
						R.string.user_loginKey_error));
			}
		}
	}
}
