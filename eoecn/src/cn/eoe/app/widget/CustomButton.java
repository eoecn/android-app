package cn.eoe.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.eoe.app.R;

public class CustomButton extends RelativeLayout {

	private String text;
	private int image=-1;
	private int background=-1;
	private int textid=-1;
	private int imgid=-1;

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		int resourceText = -1;
		textid=attrs.getAttributeResourceValue(null, "TextID",0);
		imgid=attrs.getAttributeResourceValue(null, "ImageID",0);
		TextView name = new TextView(context);
		initTextView(name);
		ImageView img = new ImageView(context);
		initImageView(img);
		resourceText = attrs.getAttributeResourceValue(null, "Text", 0);
		if (resourceText > 0) {
			text=context.getResources().getString(resourceText);
			name.setText(text);
		}
		image = attrs.getAttributeResourceValue(null, "Image", 0);
		if (image > 0) {
			img.setImageResource(image);
		}
		background = attrs.getAttributeResourceValue(null, "Background", 0);
		if (background > 0) {
			this.setBackgroundResource(background);
		}
		this.addView(name);
		this.addView(img);
	}

	public CustomButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private void initTextView(TextView tv) {
		tv.setTextColor(Color.BLACK);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		tv.setTextColor(this.getResources().getColor(R.color.black));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimensionPixelSize(R.dimen.title_text_size));
		tv.setLayoutParams(params);
		tv.setId(textid);
	}

	private void initImageView(ImageView img) {
		img.setId(imgid);
		img.setPadding(3, 3, 3, 3);
		img.setScaleType(ScaleType.FIT_CENTER);
		img.setAdjustViewBounds(true);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
//		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.ABOVE, textid);
		img.setLayoutParams(params);
	}

}
