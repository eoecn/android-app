package cn.eoe.app.utils;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import cn.eoe.app.R;
import cn.eoe.app.entity.CategorysEntity;

public class PopupWindowUtil<T> implements OnClickListener {
	PopupWindow popupWindow;
	ViewPager mViewpager;

	public PopupWindowUtil(ViewPager viewpager) {
		mViewpager = viewpager;
	}

	int width = 0;

	public void showActionWindow(View parent, Context context, List<T> tabs) {
		// final RingtoneclipModel currentData = model;
		// final int res_id = currentData.getId();
		int[] location = new int[2];
		int popWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.popupWindow_width);
		parent.getLocationOnScreen(location);
		View view = getView(context, tabs);
		popupWindow = new PopupWindow(view, popWidth,
				LayoutParams.WRAP_CONTENT);// new
											// PopupWindow(view,
											// popWidth,
											// LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的最右端
		int xPos = (int) (windowManager.getDefaultDisplay().getWidth()
				- popupWindow.getWidth() - context.getResources().getDimension(
				R.dimen.popupWindow_margin));
		// popupWindow.showAsDropDown(parent, -10,0);
		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, xPos,
				location[1] + parent.getHeight() - 20);
	}

	private View getView(Context context, List<T> tabs) {
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.back_popup_more);
		for (int i = 0; i < tabs.size(); i++) {
			if (i != tabs.size() - 1) {
				String name = "";
				if (tabs.get(i) instanceof String) {
					name = ((List<String>) tabs).get(i);
				} else if (tabs.get(i) instanceof CategorysEntity) {
					name = ((List<CategorysEntity>) tabs).get(i).getName();
				}
				Button btn = getButton(context, name, i);
				ImageView img = getImageView(context);
				layout.addView(btn);
				layout.addView(img);
			} else {
				String name = "";
				if (tabs.get(i) instanceof String) {
					name = ((List<String>) tabs).get(i);
				} else if (tabs.get(i) instanceof CategorysEntity) {
					name = ((List<CategorysEntity>) tabs).get(i).getName();
				}
				Button btn = getButton(context, name, i);
				layout.addView(btn);
			}
		}

		return layout;
	}

	private Button getButton(Context context, String text, int i) {
		Button btn = new Button(context);
		btn.setText(text);
		btn.setTextColor(context.getResources().getColor(R.color.white));
		btn.setTag(i);
		btn.setPadding(20, 15, 20, 15);
		btn.setBackgroundColor(Color.TRANSPARENT);
		btn.setOnClickListener(this);
		return btn;
	}

	private static ImageView getImageView(Context context) {
		ImageView img = new ImageView(context);
		img.setBackgroundResource(R.drawable.dis_popup_side);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		return img;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mViewpager.setCurrentItem(Integer.parseInt(v.getTag().toString()));
		popupWindow.dismiss();
	}
}
