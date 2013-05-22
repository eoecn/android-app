 package cn.eoe.app.ui;

import org.json.JSONObject;

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
import android.widget.LinearLayout;
import cn.eoe.app.R;
import cn.eoe.app.config.Urls;
import cn.eoe.app.https.HttpUtils;
import cn.eoe.app.https.NetWorkHelper;
import cn.eoe.app.ui.base.BaseActivity;
import cn.eoe.app.utils.CommonLog;
import cn.eoe.app.utils.IntentUtil;
import cn.eoe.app.utils.LogFactory;

public class UserLoginUidActivity extends BaseActivity implements
		OnClickListener {
	
	private static final CommonLog log = LogFactory.createLog();
	
	
	public static String SharedName = "login";
	public static String UID = "uid";// 用户名
	public static String PWD = "pwd";// 密码
	public static String KEY = "key";// key

	private EditText editUserID;
	private EditText editPwd;
	private Button btnEnter;
	private String key;
	private LinearLayout goHome;
	SharedPreferences share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_uid);
		initControl();
		initSharePreferences();
	}

	private void initSharePreferences() {
		share = getSharedPreferences(SharedName, Context.MODE_PRIVATE);
		if (share.contains(UID)) {
			editUserID.setText(share.getString(UID, ""));
			editPwd.setText(share.getString(PWD, ""));
		}
	}

	private void initControl() {
		editUserID = (EditText) findViewById(R.id.edittext_user_username);
		editPwd = (EditText) findViewById(R.id.edittext_user_pwd);
		btnEnter = (Button) findViewById(R.id.button_user_login);
		btnEnter.setOnClickListener(this);
		goHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		goHome.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_user_login:
			checkUsername(editUserID.getText().toString(), editPwd.getText()
					.toString());
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
		}
	}

	private void checkUsername(String name, String pwd) {
		if (TextUtils.isEmpty(name)) {
			showShortToast(getResources().getString(R.string.user_username));
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			showShortToast(getResources().getString(R.string.user_pwd));
			return;
		}else if (!NetWorkHelper.checkNetState(this)){
			showLongToast(getResources().getString(R.string.httpisNull));
			return ;
		}
		String loginUser = String.format(Urls.USER_LOGIN, name, pwd);
		new LoginAsyncTask().execute(loginUser);
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
	
//			if (!HttpUtils.isNetworkAvailable(UserLoginUidActivity.this)) {
//				showLongToast(getResources().getString(R.string.httpisNull));
//				return false;
//			}

			String result = "";
			try {
				result = HttpUtils.getByHttpClient(
						UserLoginUidActivity.this, params[0]);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			try {
				JSONObject jsonObj = new JSONObject(result);
				JSONObject response = jsonObj.getJSONObject("response");
				int error = 1;
				if ((error = response.getInt("isErr")) == 0) {
					key = response.getString(KEY);
					return true;
				}
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
				edit.putString(UID, editUserID.getText().toString());
				edit.putString(PWD, editPwd.getText().toString());
				edit.putString(KEY, key);
				edit.commit();
				IntentUtil.start_activity(UserLoginUidActivity.this, UserCenterActivity.class);
				finish();
			} else {
				showLongToast(getResources().getString(
						R.string.user_login_error));
				editUserID.setText("");
				editPwd.setText("");
			}
		}

	}

}
