package com.madiyar.ikazak.kazak.writers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.madiyar.ikazak.kazak.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by madiyar on 11/20/14.
 */
public class Writers extends FragmentActivity {
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    String tabContent[];
    ArrayList<Integer> tab_name;
    Display display;
    ArrayList<Integer> fromDatabaseCheckedList;
    String tag;
    //
//    DataBaseSource dataBaseSource;
//    List<Makal> listOfmakal;
//    List<Makal> listOfmakal_next;
//    private String[] allColumns_writer_lyric_list = { DatabaseHelper.COLUMN_ID_WLL,DatabaseHelper.COLUMN_TAG_WLL,DatabaseHelper.COLUMN_LYRIC_WLL,DatabaseHelper.COLUMN_CONTENT_WLL};
//    public static final String TABLE_WRITER_LYRICS_LIST= "WriterLyricsList";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int pos;
    View tabView1, tabView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writers);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(intent.getStringExtra("name"));
//
//        listOfmakal = new ArrayList<Makal>();
//        listOfmakal_next = new ArrayList<Makal>();
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
//        String s = in.getStringExtra("tag");
        if (bundle != null) {
            String text = (String) bundle.get("name");
            getActionBar().setTitle(text);
            tag = (String) bundle.get("tag");
            Log.i("TAG", tag);
            editor = prefs.edit();

            pos = (Integer) bundle.get("pos");
            Log.i("POS", pos+"");

            editor.putInt("pos", pos);
            editor.putString("tag", tag);
            editor.commit();
        } else {
            Log.i("INFO", "NULL");
        }


//        Lyrics_activity fragment = new Lyrics_activity();
//        Bundle bundle1 = new Bundle();
////        bundle1.putSerializable("lyric", (java.io.Serializable) listOfmakal_next);
//        bundle1.putString("lyricc", "ss");
//        fragment.setArguments(bundle1);
//        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(android.R.id.tabcontent, fragment);
//        ft.commit();


        fromDatabaseCheckedList = new ArrayList<Integer>();
        tabContent = getResources().getStringArray(R.array.tabsName);
        display = getWindowManager().getDefaultDisplay();
        tab_name = new ArrayList<Integer>();
        tab_name.add(0);
        tab_name.add(1);

        mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabView1 = getLayoutInflater().inflate(R.layout.writer_biography_view, mTabHost, false);
        tabView2 = getLayoutInflater().inflate(R.layout.writer_lyric_view, mTabHost, false);
        final HashMap<Integer, Class> hm = new HashMap<Integer, Class>();
        hm.put(0, Biography_activity.class);
        hm.put(1, Lyrics_activity.class);
        HashMap<Integer, View> hashMapView = new HashMap<Integer, View>();
        hashMapView.put(0, tabView1);
        hashMapView.put(1, tabView2);

        Class tabNamePosition[] = new Class[2];
        View tabView[] = new View[2];
        for (int i = 0; i < tab_name.size(); i++) {
            if (hm.containsKey(tab_name.get(i))) {
                tabNamePosition[i] = hm.get(tab_name.get(i));
                tabView[i] = hashMapView.get(tab_name.get(i));
            }
        }

        int width = display.getWidth();  // deprecated
        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        String tabSpecName[] = {"bir", "eki"};
        for (int i = 0; i < 2; i++) {
            mTabsAdapter.addTab(mTabHost.newTabSpec(tabSpecName[i])
                    .setIndicator(tabView[i]), tabNamePosition[i], null);
            LinearLayout lnLay = (LinearLayout) tabView[i].findViewById(R.id.tab_layout);
            lnLay.getLayoutParams().width = width / 2;
        }
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#383838"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    public class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener,
            ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();


        final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(final String _tag, final Class<?> _class, final Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(final Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(final String tag) {
                View v = new View(mContext);
                return v;
            }
        }

        public TabsAdapter(final FragmentActivity activity, final TabHost tabHost, final ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(final TabHost.TabSpec tabSpec, final Class<?> clss, final Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();
            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            Log.i("SIZE", mTabs.size() + "");
            return mTabs.size();
        }

        @Override
        public Fragment getItem(final int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(final String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
//        TabWidget widget = mTabHost.getTabWidget();
//        int oldFocusability = widget.getDescendantFocusability();
//        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
//        widget.setDescendantFocusability(oldFocusability);
            for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
            }
            mTabHost.getTabWidget().getChildAt(position).setBackgroundColor(Color.parseColor("#383838"));
        }

        @Override
        public void onPageScrollStateChanged(final int state) {

        }

    }
}
