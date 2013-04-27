package cn.eoe.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.eoe.app.R;
import cn.eoe.app.biz.UserDao;
import cn.eoe.app.entity.UserResponse;
import cn.eoe.app.indicator.PageIndicator;
import cn.eoe.app.ui.base.BaseFragmentActivity;
import cn.eoe.app.utils.PopupWindowUtil;
import cn.eoe.app.view.UserCollectFragment;
import cn.eoe.app.view.UserIntroFragment;
import cn.eoe.app.view.UserLogOutFragment;

public class UserCenterActivity extends BaseFragmentActivity implements
		OnClickListener {

	private ImageView imgTitleButton;

	ViewPager mViewPager;
	TabPageAdapter mTabsAdapter;
	PageIndicator mIndicator;
	LinearLayout llGoHome;
	private LinearLayout loadLayout;
	private LinearLayout loadFaillayout;

	ImageView ImgLeft;
	ImageView ImgRight;

	private SharedPreferences share;

	private UserDao mUserDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mUserDao = new UserDao(this);
		setContentView(R.layout.user_center_activity);
		share = getSharedPreferences(UserLoginUidActivity.SharedName,
				Context.MODE_PRIVATE);
		initControl();
		initViewPager();
	}

	// [start]初始化
	private void initControl() {
		ImgLeft = (ImageView) findViewById(R.id.imageview_user_left);
		ImgRight = (ImageView) findViewById(R.id.imageview_user_right);
		imgTitleButton = (ImageView) findViewById(R.id.imageview_user_title);
		imgTitleButton.setImageResource(R.drawable.button_user_more);
		imgTitleButton.setOnClickListener(new myOnClickListener());
		mViewPager = (ViewPager) findViewById(R.id.user_pager);
		mViewPager.setOffscreenPageLimit(2);
		mIndicator = (PageIndicator) findViewById(R.id.user_indicator);
		mIndicator
				.setOnPageChangeListener(new IndicatorOnPageChangedListener());
		llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		llGoHome.setOnClickListener(this);
		loadLayout = (LinearLayout) findViewById(R.id.view_loading);
		loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
	}

	private void initViewPager() {
		mTabsAdapter = new TabPageAdapter(this);
		mViewPager.setAdapter(mTabsAdapter);

		mIndicator.setViewPager(mViewPager);
		new ContentAsyncTask().execute();
	}

	// [end]
	public class TabPageAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments;
		public List<String> tabs = new ArrayList<String>();

		private final int[] COLORS = new int[] { R.color.red, R.color.green,
				R.color.blue, R.color.white, R.color.black };

		public TabPageAdapter(UserCenterActivity userCenterActivity) {
			super(userCenterActivity.getSupportFragmentManager());
			mFragments = new ArrayList<Fragment>();

		}

		public void addTab(String title, Fragment fragment) {
			mFragments.add(fragment);
			tabs.add(title);
			notifyDataSetChanged();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabs.get(position);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	public class ContentAsyncTask extends AsyncTask<String, Void, UserResponse> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loadLayout.setVisibility(View.VISIBLE);
		}

		@Override
		protected UserResponse doInBackground(String... params) {
			// TODO Auto-generated method stub
				return mUserDao.mapperJson(share.getString(
						UserLoginUidActivity.KEY, ""));
		}

		@Override
		protected void onPostExecute(UserResponse result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loadLayout.setVisibility(View.GONE);
			if (result == null) {
				UserLogOutFragment fragment=new UserLogOutFragment(
						UserCenterActivity.this,true);
				
				mTabsAdapter.addTab(getString(R.string.user_center_get_info_error),fragment );
				return;
			}
			mTabsAdapter.addTab(getString(R.string.user_center_my_Collect), new UserCollectFragment(result,
					UserCenterActivity.this));
			mTabsAdapter.addTab(getString(R.string.user_center_my_Intro), new UserIntroFragment(result));
			mTabsAdapter.addTab(getString(R.string.user_center_exit), new UserLogOutFragment(
					UserCenterActivity.this,false));
			mTabsAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(1);
		}
	}

	private class IndicatorOnPageChangedListener implements
			OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				ImgLeft.setVisibility(8);
				break;
			case 2:
				ImgRight.setVisibility(8);
				break;
			default:
				ImgLeft.setVisibility(0);
				ImgRight.setVisibility(0);
			}
		}

	}

	private class myOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imageview_user_title:
				new PopupWindowUtil(mViewPager).showActionWindow(v,
						UserCenterActivity.this, mTabsAdapter.tabs);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Linear_above_toHome:
			finish();
			break;
		}
	}
}
