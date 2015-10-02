package de.inovex.android.widgets.example;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class ViewPagerActivity extends Activity {

    LayoutInflater mInflater;
    ActionBar actionBar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.main);

        actionBar = getActionBar();
        mInflater = getLayoutInflater();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ViewPager pager = (ViewPager) findViewById(R.id.awesomepager);
        pager.setAdapter(new AwesomePagerAdapter());
//        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // When swiping between different app sections, select the corresponding tab.
//                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
//                // Tab.
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//        for (int i = 0; i < 5; i++) {
//            // Create a tab with text corresponding to the page title defined by the adapter.
//            // Also specify this Activity object, which implements the TabListener interface, as the
//            // listener for when this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText("Hello" + i)
//                            .setTabListener(this));
//        }
    }


    // Bind the title indicator to the adapter
//		CirclePageIndicator circleIndicator = (CirclePageIndicator)findViewById(R.id.circles);
    // circleIndicator.setViewPager(pager);
    // TabPageIndicator tabIndicator = (TabPageIndicator)
    // findViewById(R.id.tabs);
    // tabIndicator.setViewPager(pager);
    // TitlePageIndicator titleIndicator = (TitlePageIndicator)
    // findViewById(R.id.titles);
    //titleIndicator.setViewPager(pager);

    public class AwesomePagerAdapter extends PagerAdapter {

        private Stack<WeakReference<View>> mViews = new Stack<WeakReference<View>>();

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(View collection, int position) {
            WeakReference<View> refView = null;
            View v = null;
            while (v == null && mViews.size() > 0) {
                refView = mViews.pop();
                v = refView.get();
            }
            View tv;
            if (v != null) {
                refView.clear();
                tv = v;
            } else {
                tv = mInflater.inflate(R.layout.page_item, null);
            }
            final int r = (int) (Math.random() * 80);
            final int g = (int) (Math.random() * 50);
            final int b = (int) (Math.random() * 100);
            tv.setBackgroundColor(Color.argb(255, r, g, b));
            // tv.setCompoundDrawables(left, top, right, bottom)
            ((ViewPager) collection).addView(tv);
            return tv;

        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
            mViews.push(new WeakReference<View>((View) view));

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

    }
}