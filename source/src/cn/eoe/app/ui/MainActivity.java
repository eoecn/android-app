package cn.eoe.app.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cn.eoe.app.R;
import cn.eoe.app.adapter.BasePageAdapter;
import cn.eoe.app.biz.BaseDao;
import cn.eoe.app.biz.BlogsDao;
import cn.eoe.app.biz.NewsDao;
import cn.eoe.app.biz.TopDao;
import cn.eoe.app.biz.WikiDao;
import cn.eoe.app.config.Constants;
import cn.eoe.app.db.DBHelper;
import cn.eoe.app.entity.BlogsResponseEntity;
import cn.eoe.app.entity.CategorysEntity;
import cn.eoe.app.entity.NavigationModel;
import cn.eoe.app.entity.NewsResponseEntity;
import cn.eoe.app.entity.WikiResponseEntity;
import cn.eoe.app.https.NetWorkHelper;
import cn.eoe.app.indicator.PageIndicator;
import cn.eoe.app.slidingmenu.SlidingMenu;
import cn.eoe.app.ui.base.BaseSlidingFragmentActivity;
import cn.eoe.app.utils.IntentUtil;
import cn.eoe.app.utils.PopupWindowUtil;
import cn.eoe.app.widget.CustomButton;

import com.umeng.fb.UMFeedbackService;

public class MainActivity extends BaseSlidingFragmentActivity implements
        OnClickListener, AnimationListener {
    private final String LIST_TEXT = "text";
    private final String LIST_IMAGEVIEW = "img";

    // [start]变量
    /**
     * 数字代表列表顺序
     */
    private int mTag = 0;

    private CustomButton cbFeedback;
    private CustomButton cbAbove;
    private View title;
    private LinearLayout mlinear_listview;

    // title标题
    private ImageView imgQuery;
    private ImageView imgMore;
    private ImageView imgLeft;
    private ImageView imgRight;
    private FrameLayout mFrameTv;
    private ImageView mImgTv;

    // views
    private ViewPager mViewPager;
    private BasePageAdapter mBasePageAdapter;
    private PageIndicator mIndicator;
    private LinearLayout loadLayout;
    private LinearLayout loadFaillayout;

    // init daos
    private TopDao topDao;
    private BlogsDao blogsDao;
    private NewsDao newsDao;
    private WikiDao wikiDao;

    private List<Object> categoryList;

    private List<NavigationModel> navs;

    private ListView lvTitle;
    private SimpleAdapter lvAdapter;
    private LinearLayout llGoHome;
    private ImageButton imgLogin;
    private Button bn_refresh;

    private TextView mAboveTitle;
    private SlidingMenu sm;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;

    // load responseData
    private BlogsResponseEntity responseData;
    private NewsResponseEntity newsResponseData;
    private WikiResponseEntity wikiResponseData;

    private String current_page;

    private InputMethodManager imm;

    private boolean isShowPopupWindows = false;

    // [end]

    // [start]生命周期
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        setContentView(R.layout.above_slidingmenu);
        initClass();
        initControl();
        initViewPager();
        initListView();
        initgoHome();
        initNav();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            DBHelper db = DBHelper.getInstance(this);
            db.closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // [end]

    // [start]初始化函数
    private void initSlidingMenu() {
        setBehindContentView(R.layout.behind_slidingmenu);
        // customize the SlidingMenu
        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
        //sm.setShadowWidth(20);
        sm.setBehindScrollScale(0);
    }

    private void initControl() {

        imm = (InputMethodManager) getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        loadLayout = (LinearLayout) findViewById(R.id.view_loading);
        loadFaillayout = (LinearLayout) findViewById(R.id.view_load_fail);
        mAboveTitle = (TextView) findViewById(R.id.tv_above_title);
        mAboveTitle.setText("社区精选");
        imgQuery = (ImageView) findViewById(R.id.imageview_above_query);
        imgQuery.setOnClickListener(this);
        imgQuery.setVisibility(View.GONE);
        imgMore = (ImageView) findViewById(R.id.imageview_above_more);
        imgMore.setOnClickListener(this);
        imgLeft = (ImageView) findViewById(R.id.imageview_above_left);
        imgRight = (ImageView) findViewById(R.id.imageview_above_right);
        // editSearch.setOnKeyListener(onkey);
        mViewPager = (ViewPager) findViewById(R.id.above_pager);
        mIndicator = (PageIndicator) findViewById(R.id.above_indicator);
        lvTitle = (ListView) findViewById(R.id.behind_list_show);
        llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
        imgLogin = (ImageButton) findViewById(R.id.login_login);

        imgLogin.setOnClickListener(this);
        cbFeedback = (CustomButton) findViewById(R.id.cbFeedback);
        cbFeedback.setOnClickListener(this);
        cbAbove = (CustomButton) findViewById(R.id.cbAbove);
        cbAbove.setOnClickListener(this);
        title = findViewById(R.id.main_title);
        mlinear_listview = (LinearLayout) findViewById(R.id.main_linear_listview);
        mFrameTv = (FrameLayout) findViewById(R.id.fl_off);
        mImgTv = (ImageView) findViewById(R.id.iv_off);

        bn_refresh = (Button) findViewById(R.id.bn_refresh);
        bn_refresh.setOnClickListener(this);
    }

    private void initClass() {
        blogsDao = new BlogsDao(this);
        newsDao = new NewsDao(this);
        wikiDao = new WikiDao(this);
        topDao = new TopDao(this);
    }

    private void initViewPager() {
        mBasePageAdapter = new BasePageAdapter(MainActivity.this);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mBasePageAdapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnPageChangeListener(new MyPageChangeListener());
        new MyTask().execute(topDao);
    }

    private void initListView() {
        lvAdapter = new SimpleAdapter(this, getData(),
                R.layout.behind_list_show, new String[]{LIST_TEXT,
                LIST_IMAGEVIEW},
                new int[]{R.id.textview_behind_title,
                        R.id.imageview_behind_icon}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub.
                View view = super.getView(position, convertView, parent);
                if (position == mTag) {
                    view.setBackgroundResource(R.drawable.back_behind_list);
                    lvTitle.setTag(view);
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                return view;
            }
        };
        lvTitle.setAdapter(lvAdapter);
        lvTitle.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                NavigationModel navModel = navs.get(position);
                mAboveTitle.setText(navModel.getName());
                current_page = navModel.getTags();
                if (lvTitle.getTag() != null) {
                    if (lvTitle.getTag() == view) {
                        MainActivity.this.showContent();
                        return;
                    }
                    ((View) lvTitle.getTag())
                            .setBackgroundColor(Color.TRANSPARENT);
                }
                lvTitle.setTag(view);
                view.setBackgroundResource(R.drawable.back_behind_list);
                imgQuery.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        imgQuery.setVisibility(View.GONE);
                        new MyTask().execute(topDao);
                        break;
                    case 1:
                        new MyTask().execute(newsDao);
                        break;
                    case 2:
                        new MyTask().execute(wikiDao);
                        break;
                    case 3:
                        new MyTask().execute(blogsDao);
                        break;
                }
            }
        });

    }

    private void initNav() {
        navs = new ArrayList<NavigationModel>();
        NavigationModel nav1 = new NavigationModel(getResources().getString(
                R.string.menuGood), "");
        NavigationModel nav2 = new NavigationModel(getResources().getString(
                R.string.menuNews), Constants.TAGS.NEWS_TAG);
        NavigationModel nav3 = new NavigationModel(getResources().getString(
                R.string.menuStudio), Constants.TAGS.WIKI_TAG);
        NavigationModel nav4 = new NavigationModel(getResources().getString(
                R.string.menuBlog), Constants.TAGS.BLOG_TAG);
        Collections.addAll(navs, nav1, nav2, nav3, nav4);
    }

    private void initgoHome() {
        llGoHome.setOnClickListener(this);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuGood));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_handpick);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuNews));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_news);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuStudio));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_studio);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put(LIST_TEXT, getResources().getString(R.string.menuBlog));
        map.put(LIST_IMAGEVIEW, R.drawable.dis_menu_blog);
        list.add(map);
        return list;
    }

    // [end]

    // [start]继承方法
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.Linear_above_toHome:
                showMenu();
                break;
            case R.id.login_login:
                SharedPreferences share = this.getSharedPreferences(
                        UserLoginUidActivity.SharedName, Context.MODE_PRIVATE);
                // [start] 修复上一个bug
                String Key = share.getString(UserLoginUidActivity.KEY, "");
                if (Key != "" && !Key.contains(":")) {
                    Editor edit = share.edit();
                    edit.putString(UserLoginUidActivity.KEY, "");
                    edit.commit();
                }
                // [end] 下一版本删除掉
                if (share.contains(UserLoginUidActivity.KEY)
                        && !share.getString(UserLoginUidActivity.KEY, "")
                        .equals("")) {
                    IntentUtil.start_activity(this, UserCenterActivity.class);
                } else {
                    IntentUtil.start_activity(this, UserLoginActivity.class);
                }
                break;
            case R.id.imageview_above_more:
                if (isShowPopupWindows) {
                    new PopupWindowUtil(mViewPager).showActionWindow(v, this,
                            mBasePageAdapter.tabs);
                }
                break;
            case R.id.imageview_above_query:

                if (NetWorkHelper.isNetworkAvailable(MainActivity.this)) {
                    IntentUtil.start_activity(this, SearchActivity.class,
                            new BasicNameValuePair("tag", current_page));
                } else {
                    Toast.makeText(getApplicationContext(), "网络连接失败,请检查网络",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cbFeedback:
                UMFeedbackService.openUmengFeedbackSDK(this);
                break;
            case R.id.cbAbove:
                IntentUtil.start_activity(this, AboutActivity.class);
                break;
            case R.id.bn_refresh:
                switch (mTag) {
                    case 0:
                        imgQuery.setVisibility(View.GONE);
                        new MyTask().execute(topDao);
                        break;
                    case 1:
                        new MyTask().execute(newsDao);
                        break;
                    case 2:
                        new MyTask().execute(wikiDao);
                        break;
                    case 3:
                        new MyTask().execute(blogsDao);
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    /**
     * 连续按两次返回键就退出
     */
    private int keyBackClickCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        keyBackClickCount = 0;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    Toast.makeText(this,
                            getResources().getString(R.string.press_again_exit),
                            Toast.LENGTH_SHORT).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    mFrameTv.setVisibility(View.VISIBLE);
                    mImgTv.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.tv_off);
                    anim.setAnimationListener(new tvOffAnimListener());
                    mImgTv.startAnimation(anim);
                    break;
                default:
                    break;
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (sm.isMenuShowing()) {
                toggle();
            } else {
                showMenu();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.dispatchTouchEvent(event);
        if (mIsAnim || mViewPager.getChildCount() <= 1) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                if (dX < 8 && dY > 8 && !mIsTitleHide && !down) {
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.push_top_in);
//				anim.setFillAfter(true);
                    anim.setAnimationListener(MainActivity.this);
                    title.startAnimation(anim);
                } else if (dX < 8 && dY > 8 && mIsTitleHide && down) {
                    Animation anim = AnimationUtils.loadAnimation(
                            MainActivity.this, R.anim.push_top_out);
//				anim.setFillAfter(true);
                    anim.setAnimationListener(MainActivity.this);
                    title.startAnimation(anim);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // [end]

    /**
     * 加载分类list的task
     *
     * @author wangxin
     */
    public class MyTask extends AsyncTask<BaseDao, String, Map<String, Object>> {

        private boolean mUseCache;

        public MyTask() {
            mUseCache = true;
        }

        public MyTask(boolean useCache) {
            mUseCache = useCache;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            imgLeft.setVisibility(View.GONE);
            imgRight.setVisibility(View.GONE);
            loadLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            mViewPager.removeAllViews();
            mBasePageAdapter.Clear();
            MainActivity.this.showContent();
            super.onPreExecute();
            isShowPopupWindows = false;
        }

        @Override
        protected Map<String, Object> doInBackground(BaseDao... params) {
            BaseDao dao = params[0];
            List<CategorysEntity> categorys = new ArrayList<CategorysEntity>();
            Map<String, Object> map = new HashMap<String, Object>();
            if (dao instanceof TopDao) {
                mTag = 0;
                if ((categoryList = topDao.mapperJson(mUseCache)) != null) {

                    categorys = topDao.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else if (dao instanceof BlogsDao) {
                mTag = 3;
                if ((responseData = blogsDao.mapperJson(mUseCache)) != null) {
                    categoryList = (List) responseData.getList();
                    categorys = responseData.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else if (dao instanceof NewsDao) {
                mTag = 1;
                if ((newsResponseData = newsDao.mapperJson(mUseCache)) != null) {
                    categoryList = (List) newsResponseData.getList();
                    categorys = newsResponseData.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else if (dao instanceof WikiDao) {
                mTag = 2;
                if ((wikiResponseData = wikiDao.mapperJson(mUseCache)) != null) {
                    categoryList = (List) wikiResponseData.getList();
                    categorys = wikiResponseData.getCategorys();
                    map.put("tabs", categorys);
                    map.put("list", categoryList);
                }
            } else {
                return null;
            }
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            isShowPopupWindows = true;
            mBasePageAdapter.Clear();
            mViewPager.removeAllViews();
            if (!result.isEmpty()) {
                mBasePageAdapter.addFragment((List) result.get("tabs"),
                        (List) result.get("list"));
                imgRight.setVisibility(View.VISIBLE);
                loadLayout.setVisibility(View.GONE);
                loadFaillayout.setVisibility(View.GONE);
            } else {
                mBasePageAdapter.addNullFragment();
                loadLayout.setVisibility(View.GONE);
                loadFaillayout.setVisibility(View.VISIBLE);
            }
            mViewPager.setVisibility(View.VISIBLE);
            mBasePageAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(0);
            mIndicator.notifyDataSetChanged();

        }
    }

    /**
     * viewPager切换页面
     *
     * @author mingxv
     */
    class MyPageChangeListener implements OnPageChangeListener {
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
            if (arg0 == 0) {
                getSlidingMenu().setTouchModeAbove(
                        SlidingMenu.TOUCHMODE_FULLSCREEN);
                imgLeft.setVisibility(View.GONE);
            } else if (arg0 == mBasePageAdapter.mFragments.size() - 1) {
                imgRight.setVisibility(View.GONE);
                getSlidingMenu()
                        .setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            } else {
                imgRight.setVisibility(View.VISIBLE);
                imgLeft.setVisibility(View.VISIBLE);
                getSlidingMenu()
                        .setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
            }
        }
    }

    class tvOffAnimListener implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            defaultFinish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        if (mIsTitleHide) {
            title.setVisibility(View.GONE);
        } else {

        }
        mIsAnim = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
        title.setVisibility(View.VISIBLE);
        if (mIsTitleHide) {
            FrameLayout.LayoutParams lp = (LayoutParams) mlinear_listview
                    .getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            mlinear_listview.setLayoutParams(lp);
        } else {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) title
                    .getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            title.setLayoutParams(lp);
            FrameLayout.LayoutParams lp1 = (LayoutParams) mlinear_listview
                    .getLayoutParams();
            lp1.setMargins(0,
                    getResources().getDimensionPixelSize(R.dimen.title_height),
                    0, 0);
            mlinear_listview.setLayoutParams(lp1);
        }
    }

    private float lastX = 0;
    private float lastY = 0;

}
