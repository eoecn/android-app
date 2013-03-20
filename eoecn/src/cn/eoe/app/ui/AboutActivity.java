package cn.eoe.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.eoe.app.R;
import cn.eoe.app.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity {

	private ImageView mBack;
	private TextView mWeixin;
	private TextView mWeibo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		mWeixin = (TextView) findViewById(R.id.about_textview_weixin);
		// http://weixin.qq.com/r/7HX_8R7EfiABhw_SnyDI
		// http://e.weibo.com/eoeandroid00?ref=http%3A%2F%2Fwww.weibo.com%2Fu%2F1959452825%3Fwvr%3D5%26
		String htmlLinkText = "<a href=\"http://weixin.qq.com/r/7HX_8R7EfiABhw_SnyDI\"> "+getResources().getString(R.string.about_weixin)+"</a>";
		String htmlLinkTextWeibo = "<a href=\"#\"> "+getResources().getString(R.string.about_sina)+"</a>";
		mWeixin.setText(Html.fromHtml(htmlLinkText));
		mWeixin.setMovementMethod(LinkMovementMethod.getInstance());
		mWeibo = (TextView) findViewById(R.id.about_textview_weibo);
		mWeibo.setText(Html.fromHtml(htmlLinkTextWeibo));
		mWeibo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri
						.parse("http://e.weibo.com/eoeandroid00?ref=http%3A%2F%2Fwww.weibo.com%2Fu%2F1959452825%3Fwvr%3D5%26");
				intent.setData(content_url);
				startActivity(intent);
			}
		});
		mBack = (ImageView) findViewById(R.id.about_imageview_gohome);
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
