package cn.eoe.app.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.eoe.app.R;
import cn.eoe.app.biz.WikiDao;
import cn.eoe.app.entity.WikiCategoryListEntity;
import cn.eoe.app.entity.WikiContentItem;
import cn.eoe.app.entity.WikiMoreResponse;

public class WikiFragment extends BaseListFragment {

	List<WikiContentItem> items_list = new ArrayList<WikiContentItem>();
	private Activity mActivity;
	private WikiCategoryListEntity loadMoreEntity;
	private MyAdapter mAdapter;
	private String more_url;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				more_url = loadMoreEntity.getMore_url();
				mAdapter.appendToList(loadMoreEntity.getItems());
				break;
			}
			onLoad();
		};

	};

	public WikiFragment(Activity c, WikiCategoryListEntity categorys) {
		this.mActivity = c;
		if (categorys != null) {
			this.more_url = categorys.getMore_url();
			this.items_list = categorys.getItems();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		listview.setXListViewListener(this);
		// construct the RelativeLayout
		mAdapter = new MyAdapter();
		mAdapter.appendToList(items_list);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				WikiContentItem item = (WikiContentItem) mAdapter
						.getItem(position - 1);
				startDetailActivity(mActivity, item.getDetail_url(), "教程",
						item.getTitle());
			}
		});
		return view;
	}

	class MyAdapter extends BaseAdapter {

		List<WikiContentItem> mList = new ArrayList<WikiContentItem>();

		public MyAdapter() {

		}

		public void appendToList(List<WikiContentItem> lists) {

			if (lists == null) {
				return;
			}
			mList.addAll(lists);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			WikiContentItem item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater
						.inflate(R.layout.wiki_item_layout, null);
				holder.title_ = (TextView) convertView
						.findViewById(R.id.wiki_title);
				holder.title_.setText(item.getTitle());
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				holder.title_.setText(item.getTitle());
			}
			return convertView;
		}
	}

	static class ViewHolder {
		public TextView title_;
		public TextView short_;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		onLoad();
	}

	@Override
	public void onLoadMore() {
		if (more_url==null || more_url.equals("")) {
			mHandler.sendEmptyMessage(1);
			return;
		} else {
			new Thread() {
				@Override
				public void run() {
					WikiMoreResponse response = new WikiDao(mActivity)
							.getMore(more_url);
					if (response != null) {
						loadMoreEntity = response.getResponse();
						mHandler.sendEmptyMessage(0);
					}
				}
			}.start();
		}
	}

}
