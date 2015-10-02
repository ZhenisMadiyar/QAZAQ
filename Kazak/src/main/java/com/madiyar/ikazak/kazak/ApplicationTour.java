package com.madiyar.ikazak.kazak;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.taurusxi.guidebackgroundcoloranimation.library.ColorAnimationView;

public class ApplicationTour extends FragmentActivity {
    private static final int[] resource = new int[]{R.drawable.tour_first_screen, R.drawable.tour_second_screen,
            R.drawable.tour_third_screen, R.drawable.tour_fourth_screen};
    private static final String TAG = ApplicationTour.class.getSimpleName();
    int dotsCount;
    TextView dots[];
    Button button_ok;
    LinearLayout dotsLayout;
    public static final String MyPrefs = "MyPrefs";
    CheckBox checkBox;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tour);

        button_ok = (Button) findViewById(R.id.button_ok);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (!sp.getBoolean("first", false)) {//if true
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.commit();
            Tour();
//            Toast.makeText(getApplicationContext(), "Started with tour", Toast.LENGTH_SHORT).show();
        }else {
//            Toast.makeText(getApplicationContext(), "Started without tour", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
        }

        // Four : Also ,you can call this method like this:
        // colorAnimationView.setmViewPager(viewPager,this,resource.length,0xffFF8080,0xff8080FF,0xffffffff,0xff80ff80);
    }

    private void Tour() {
        MyFragmentStatePager adapter = new MyFragmentStatePager(getSupportFragmentManager());
        setUiPageViewController();
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
//        /**
//         *  Frist: You need call this method after you set the Viewpager adpter;
//         * Second: setmViewPager(ViewPager mViewPager,Object obj， int count, int... colors)
//         *          so,you can set any length colors to make the animation more cool!
//         * Third: If you call this method like below, make the colors no data, it will create
//         *          a change color by default.
//         * */
        colorAnimationView.setmViewPager(viewPager, resource.length);
        colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG", "onPageScrolled");
                if (position == 3) {
                    button_ok.setVisibility(View.VISIBLE);
                }else
                    button_ok.setVisibility(View.GONE);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("TAG", "onPageSelected");
                // making everything as un selected
                for (int i = 0; i < dotsCount; i++){
                    dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                // only one selected
                dots[position].setTextColor(Color.parseColor("#131619"));
                if (position == 3) {
                    button_ok.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAG", "onPageScrollStateChanged");
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox.isChecked()) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("first", true);
                    editor.commit();
                }else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("first", false);
                    editor.commit();
                }
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });
    }

    private void setUiPageViewController() {
        dotsLayout = (LinearLayout)findViewById(R.id.viewPagerCountDots);
        dotsCount = 4;
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsLayout.addView(dots[i]);
        }

        dots[0].setTextColor(Color.parseColor("#131619"));
    }

    public class MyFragmentStatePager extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment(position);
        }

        @Override
        public int getCount() {
            return resource.length;
        }
    }

    @SuppressLint("ValidFragment")
    public class MyFragment extends Fragment {
        private int position;

        public MyFragment(int position) {
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(resource[position]);
            return imageView;
        }
    }
}
