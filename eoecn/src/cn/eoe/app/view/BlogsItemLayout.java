package cn.eoe.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.eoe.app.R;

public class BlogsItemLayout extends LinearLayout {

	private TextView txt_title;
	private TextView txt_short_content;
	private ImageView img_thu;
	
	public BlogsItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_short_content = (TextView) findViewById(R.id.txt_short_content);
		
	}

}
