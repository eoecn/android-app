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
import android.widget.ImageView;
import android.widget.TextView;
import cn.eoe.app.R;
import cn.eoe.app.biz.BlogsDao;
import cn.eoe.app.entity.BlogContentItem;
import cn.eoe.app.entity.BlogsCategoryListEntity;
import cn.eoe.app.entity.BlogsMoreResponse;
import cn.eoe.app.utils.ImageUtil;

/**
 * 博客部分的Fragment
 * 
 * @author wangxin
 * 
 */
public class BlogFragment extends BaseListFragment {

	List<BlogContentItem> items_list = new ArrayList<BlogContentItem>();
	private Activity mActivity;
	private String more_url;
	private BlogsCategoryListEntity loadMoreEntity;
	private MyAdapter mAdapter;

	public BlogFragment() {
	}

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

	public BlogFragment(Activity c, BlogsCategoryListEntity categorys) {
		this.mActivity = c;

		if (categorys != null) {
			more_url = categorys.getMore_url();
			this.items_list = categorys.getItems();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		listview.setXListViewListener(this);
		// construct the RelativeLayout
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
				BlogContentItem item = (BlogContentItem) mAdapter
						.getItem(position - 1);
				startDetailActivity(mActivity, item.getDetail_url(), "博客",
						item.getTitle());
			}
		});
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	class MyAdapter extends BaseAdapter {

		List<BlogContentItem> mList = new ArrayList<BlogContentItem>();

		public MyAdapter() {

		}

		public void appendToList(List<BlogContentItem> lists) {

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
			BlogContentItem item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.blogs_item_layout,
						null);
				holder.header_ = (TextView) convertView
						.findViewById(R.id.tx_header_title);
				holder.title_ = (TextView) convertView
						.findViewById(R.id.txt_title);
				holder.short_ = (TextView) convertView
						.findViewById(R.id.txt_short_content);
				holder.img_thu = (ImageView) convertView
						.findViewById(R.id.img_thu);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.header_.setText(item.getName());
			holder.title_.setText(item.getTitle());
			holder.short_.setText(item.getShort_content());
			String url = item.getHead_image_url().replaceAll("=small",
					"=middle");
			if (url.equals(null) || url.equals("")) {
				holder.img_thu.setVisibility(View.GONE);
			} else {
				holder.img_thu.setVisibility(View.VISIBLE);
				ImageUtil.setThumbnailView(url, holder.img_thu, mActivity,
						callback1, false);
			}
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView header_;
		public TextView title_;
		public TextView short_;
		public ImageView img_thu;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		onLoad();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (more_url==null || more_url.equals("")) {
			mHandler.sendEmptyMessage(1);
			return;
		} else {

			new Thread() {
				@Override
				public void run() {
					BlogsMoreResponse response = new BlogsDao(mActivity)
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
