package cn.eoe.app.ui;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.eoe.app.R;
import cn.eoe.app.biz.DetailDao;
import cn.eoe.app.db.DetailColumn;
import cn.eoe.app.db.biz.DetailDB;
import cn.eoe.app.entity.DetailResponseEntity;
import cn.eoe.app.https.HttpUtils;
import cn.eoe.app.https.NetWorkHelper;
import cn.eoe.app.ui.base.BaseActivity;
import cn.eoe.app.utils.CommonUtil;
import cn.eoe.app.utils.IntentUtil;
import cn.eoe.app.utils.Utility;

public class DetailsActivity extends BaseActivity implements OnClickListener {

    private DetailDao detailDao;
    private DetailResponseEntity responseEntity;
    static final String mimeType = "text/html";
    static final String encoding = "utf-8";

    private RelativeLayout good;
    private RelativeLayout bed;
    private RelativeLayout collect;
    private RelativeLayout share;
    private RelativeLayout discuss;
    private ImageView imgGood;
    private ImageView imgBed;
    private ImageView imgCollect;
    private ImageView imgShare;
    private ImageView imgDiscuss;
    private boolean IsGood = false;
    private boolean IsBed = false;
    private boolean IsCollect = false;
    private TextView detailTitle;

    private LinearLayout loadLayout;
    private LinearLayout failLayout;
    private Button bn_refresh;
    private ImageView imgGoHome;
    private WebView mWebView;
    private String mUrl;
    private String mTitle;
    private String shareUrl;
    private String shareTitle;

    private int screen_width;

    SharedPreferences sharePre;
    private DetailDB mDetailDB;
    private String mKey;
    private int mDBID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Intent i = getIntent();
        mUrl = i.getStringExtra("url");
        mTitle = i.getStringExtra("title");
        shareTitle = i.getStringExtra("sharetitle");
        sharePre = getSharedPreferences(UserLoginUidActivity.SharedName,
                Context.MODE_PRIVATE);
        mKey = sharePre.getString(UserLoginUidActivity.KEY, "");
        initData();
        initControl();
        initAppraise();
    }

    private void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screen_width = dm.widthPixels;
    }

    private void initControl() {
        detailDao = new DetailDao(this, mUrl);
        detailTitle = (TextView) findViewById(R.id.details_textview_title);
        detailTitle.setText(mTitle);
        loadLayout = (LinearLayout) findViewById(R.id.view_loading);
        failLayout = (LinearLayout) findViewById(R.id.view_load_fail);
        bn_refresh=(Button)findViewById(R.id.bn_refresh);
        good = (RelativeLayout) findViewById(R.id.rlGood);
        bed = (RelativeLayout) findViewById(R.id.rlBed);
        collect = (RelativeLayout) findViewById(R.id.rlCollect);
        share = (RelativeLayout) findViewById(R.id.rlShare);
        discuss = (RelativeLayout) findViewById(R.id.rlDiscuss);
        mWebView = (WebView) findViewById(R.id.detail_webView);
        this.mWebView.setBackgroundColor(0);
        this.mWebView.setBackgroundResource(R.color.detail_bgColor);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        good.setOnClickListener(this);
        bed.setOnClickListener(this);
        collect.setOnClickListener(this);
        share.setOnClickListener(this);
        discuss.setOnClickListener(this);
        bn_refresh.setOnClickListener(this);
        imgGood = (ImageView) findViewById(R.id.imageview_details_good);
        imgBed = (ImageView) findViewById(R.id.imageview_details_bed);
        imgCollect = (ImageView) findViewById(R.id.imageview_details_collect);
        imgShare = (ImageView) findViewById(R.id.imageview_details_share);
        imgDiscuss = (ImageView) findViewById(R.id.imageview_details_discuss);
        imgGoHome = (ImageView) findViewById(R.id.details_imageview_gohome);
        imgGoHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        MyTask mTask = new MyTask();
        mTask.execute();
    }

    private void initAppraise() {
        mDetailDB = new DetailDB(this);
        Cursor cursor = mDetailDB.querySQL(mUrl);
        if (cursor.moveToFirst()) {
            mDBID = cursor.getInt(cursor.getColumnIndex(DetailColumn._ID));
            setAppraise(
                    cursor.getInt(cursor.getColumnIndex(DetailColumn.GOOD)),
                    cursor.getInt(cursor.getColumnIndex(DetailColumn.BAD)),
                    cursor.getInt(cursor.getColumnIndex(DetailColumn.COLLECT)));
        }
    }

    // [start] 初始化评价icon

    public void setAppraise(int good, int bad, int collect) {
        initGood(IsGood = (good == 1));
        initBad(IsBed = (bad == 1));
        initCollect(IsCollect = (collect == 1));
    }

    private void initGood(boolean b) {
        if (b) {
            imgGood.setImageResource(R.drawable.button_details_good_selected);
        } else {
            imgGood.setImageResource(R.drawable.button_details_good_default);
        }
    }

    private void initBad(boolean b) {
        if (b) {
            imgBed.setImageResource(R.drawable.button_details_bed_selected);
        } else {
            imgBed.setImageResource(R.drawable.button_details_bed_default);
        }
    }

    private void initCollect(boolean b) {
        if (b) {
            imgCollect
                    .setImageResource(R.drawable.button_details_collect_selected);
        } else {
            imgCollect
                    .setImageResource(R.drawable.button_details_collect_default);
        }
    }

    // [end]

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // 保存状态
        if (mKey.equals(null) && mKey.equals("")) {
            return;
        }
        if (mDBID == -1 && (IsGood || IsBed || IsCollect)) {
            // 添加
            mDetailDB.insertSQL(mUrl, mKey, IsGood ? 1 : 0, IsBed ? 1 : 0,
                    IsCollect ? 1 : 0);
        } else if (mDBID != -1) {
            // 修改
            mDetailDB.updateSQL(mDBID, IsGood ? 1 : 0, IsBed ? 1 : 0,
                    IsCollect ? 1 : 0);
        }
        mDetailDB.dbClose();
    }

    class MyTask extends AsyncTask<String, Integer, String> {

        private boolean mUseCache;

        public MyTask() {
            mUseCache = false;
        }

        public MyTask(boolean useCache) {
            mUseCache = useCache;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadLayout.setVisibility(View.VISIBLE);
            failLayout.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if ((responseEntity = detailDao.mapperJson(mUseCache)) != null) {
                shareUrl = responseEntity.getShare_url();
                return responseEntity.getContent();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                String linkCss = "<link rel=\"stylesheet\" href=\"file:///android_asset/pygments.css\" type=\"text/css\"/>";
                String content = linkCss + result;
                try {
                    content = content.replace(
                            "img{}",
                            "img{width:"
                                    + CommonUtil.px2dip(DetailsActivity.this,
                                    screen_width) + "}");
                    content = content.replaceAll("<br />", "");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                loadLayout.setVisibility(View.GONE);
                failLayout.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
                mWebView.setBackgroundResource(R.color.detail_bgColor);
                mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
            } else {
                loadLayout.setVisibility(View.GONE);
                mWebView.setVisibility(View.GONE);
                failLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bn_refresh)
        {
            new MyTask().execute();
            return;
        }
        if (responseEntity == null)
            return;
        if (mKey.equals(null) || mKey.equals("")) {
            showLongToast(getResources().getString(R.string.user_login_prompt));
            return;
        }
        String url = null;
        switch (v.getId()) {
            case R.id.rlGood:
                url = updateUrl(IsGood, responseEntity.getBar().getUserlike()
                        .getGood());
                break;
            case R.id.rlBed:
                url = updateUrl(IsBed, responseEntity.getBar().getUserlike()
                        .getBad());
                break;
            case R.id.rlCollect:
                url = updateUrl(IsCollect, responseEntity.getBar().getFavorite());
                break;
            case R.id.rlShare:
                recommandToYourFriend(shareUrl, shareTitle);
                break;
            case R.id.rlDiscuss:
                IntentUtil.start_activity(DetailsActivity.this,
                        DetailsDiscussActivity.class, new BasicNameValuePair(
                        "discuss_list", responseEntity.getBar()
                        .getComment().getGet()),
                        new BasicNameValuePair("discuss", responseEntity.getBar()
                                .getComment().getSubmit()), new BasicNameValuePair(
                        "title", mTitle));
                break;
        }
        if (url != null) {
            if (!NetWorkHelper.checkNetState(this)) {
                showLongToast(getResources().getString(R.string.httpisNull));
                return;
            }
            new LoginAsyncTask().execute(url, v.getId());
        }
    }

    private String updateUrl(boolean isAdd, String url) {
        String result = url;
        if (isAdd) {
            result = result.replaceAll("add", "del");
        }
        String key = sharePre.getString(UserLoginUidActivity.KEY, "");
        result += Utility.getParams(key);
        return result;
    }

    class LoginAsyncTask extends AsyncTask<Object, Void, Boolean> {

        private int id;

        @Override
        protected Boolean doInBackground(Object... params) {
            // TODO Auto-generated method stub
            id = Integer.parseInt(params[1].toString());
//			if (!HttpUtils.isNetworkAvailable(DetailsActivity.this)) {
//				showLongToast(getResources().getString(R.string.httpisNull));
//				return false;
//			}
            String result;
            try {
                result = HttpUtils.getByHttpClient(DetailsActivity.this,
                        params[0].toString());
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
            switch (id) {
                case R.id.rlGood:
                    if (result) {
                        IsGood = !IsGood;
                        initGood(IsGood);
                        showShortToast("评价成功");
                    } else {
                        showShortToast("评价失败");
                    }
                    break;
                case R.id.rlBed:
                    if (result) {
                        IsBed = !IsBed;
                        initBad(IsBed);
                        showShortToast("评价成功");
                    } else {
                        showShortToast("评价失败");
                    }
                    break;
                case R.id.rlCollect:
                    if (result) {
                        IsCollect = !IsCollect;
                        initCollect(IsCollect);
                        if (IsCollect) {
                            showShortToast("收藏成功");
                        } else {
                            showShortToast("取消收藏成功");
                        }

                    } else {
                        showShortToast("收藏操作失败");
                    }
                    break;
            }

        }

    }
}
