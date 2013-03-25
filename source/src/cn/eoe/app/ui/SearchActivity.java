package cn.eoe.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.eoe.app.R;
import cn.eoe.app.adapter.BasePageAdapter;
import cn.eoe.app.biz.BaseDao;
import cn.eoe.app.biz.SearchDao;
import cn.eoe.app.entity.CategorysEntity;
import cn.eoe.app.ui.base.BaseFragmentActivity;

public class SearchActivity extends BaseFragmentActivity implements
		OnClickListener {

	private ImageView btnGohome;
	private EditText edtSearch;
	private ListView mListView;
	private LinearLayout loadLayout;
	private String mTag;
	private InputMethodManager imm;
	private ViewPager mViewPager;
	private BasePageAdapter mBasePageAdapter;
	private List<Object> categoryList;
	private SearchDao searchDao;
	private ImageView mWait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		Intent i = getIntent();
		mTag = i.getStringExtra("tag");
		initData();
		initView();
		initViewPager();
	}

	public void initData() {
		searchDao = new SearchDao(this);
		imm = (InputMethodManager) getApplicationContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);

	}

	public void initView() {
//		txtEmpty = (TextView) findViewById(R.id.txt_empty);
		btnGohome = (ImageView) findViewById(R.id.btn_gohome);
		btnGohome.setOnClickListener(this);
		edtSearch = (EditText) findViewById(R.id.edt_search);
		mListView = (ListView) findViewById(R.id.list_view);
		loadLayout = (LinearLayout) findViewById(R.id.view_loading);

		loadLayout.setVisibility(View.GONE);
		mViewPager = (ViewPager) findViewById(R.id.above_pager);
edtSearch.setHint("即将为您搜索 " + mTag);
		edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					imm.showSoftInput(v, 0);
				} else {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		});

		edtSearch.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					if (v.getTag() == null) {
						v.setTag(1);
						edtSearch.clearFocus();
						String searchContent = edtSearch.getText().toString();
						searchDao.setValue(mTag, searchContent);
						new MyTask().execute(searchDao);

					} else {
						v.setTag(null);
					}
					return true;
				}
				return false;
			}
		});
		mWait=(ImageView)findViewById(R.id.search_imageview_wait);
	}

	public void initViewPager() {
		mBasePageAdapter = new BasePageAdapter(SearchActivity.this);
		mViewPager.setAdapter(mBasePageAdapter);
	}

	public class MyTask extends AsyncTask<BaseDao, String, Map<String, Object>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mWait.setVisibility(View.GONE);
			loadLayout.setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			mViewPager.removeAllViews();
			mBasePageAdapter.Clear();
			super.onPreExecute();
		}

		@Override
		protected Map<String, Object> doInBackground(BaseDao... params) {
			BaseDao dao = params[0];
			List<CategorysEntity> categorys = new ArrayList<CategorysEntity>();
			Map<String, Object> map = new HashMap<String, Object>();
			if ((categoryList = searchDao.mapperJson()) != null) {
				categorys = searchDao.getCategorys();
//				map.put("tabs", categorys);
				map.put("list", categoryList);
				return map;
			} else {
				return null;
			}

		}

		@Override
		protected void onPostExecute(Map<String, Object> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mBasePageAdapter.Clear();
			mViewPager.removeAllViews();

			loadLayout.setVisibility(View.GONE);
			mViewPager.setVisibility(View.VISIBLE);

			if (result == null) {
				mBasePageAdapter.addNullFragment();
				loadLayout.setVisibility(View.GONE);
//				txtEmpty.setVisibility(View.VISIBLE);
				return;
			}
			if (searchDao.getHasChild()) {
				mBasePageAdapter.addFragment((List) result.get("list"));
				loadLayout.setVisibility(View.GONE);
//				txtEmpty.setVisibility(View.GONE);
			} else {
				mWait.setVisibility(View.VISIBLE);
				
			}
			mBasePageAdapter.notifyDataSetChanged();
			mViewPager.setCurrentItem(0);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_gohome:
			finish();
			break;
		}
	}

}
