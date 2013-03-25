package cn.eoe.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.GridView;

public class CustomGridView extends GridView {

	public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	public CustomGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
