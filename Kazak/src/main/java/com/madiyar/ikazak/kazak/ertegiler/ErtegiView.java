package com.madiyar.ikazak.kazak.ertegiler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;

/**
 * Created by madiyar on 12/4/14.
 */
public class ErtegiView extends Activity {
    String content;
    TextView textView;
//    DataBaseSource dataSource;
//    List<MakalCategory> listOfCat;
//    List<MakalCategory> listOfCat_next;
//    private String[] allColumns_ertegi = {DatabaseHelper.COLUMN_TAG_ERTEGI, DatabaseHelper.COLUMN_CONTENT_ERTEGI, DatabaseHelper.COLUMN_POINT_ERTEGI};
//    int pos;
//    ViewPager viewPager;
//    ViewPagerAdapter adapter;
//    ArrayList<ClassErtegi> ertegi;

//    int pagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ertegi_view);

        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        textView = (TextView) findViewById(R.id.ertegi_content);
        textView.setText(content);
//        pos = intent.getIntExtra("pos", 0);


//        listOfCat = new ArrayList<MakalCategory>();
//        listOfCat_next = new ArrayList<MakalCategory>();
//        dataSource = new DataBaseSource(getApplicationContext());
//        dataSource.open();
//        listOfCat = dataSource.getAllCategory(DatabaseHelper.TABLE_ERTEGI, allColumns_ertegi);
//        dataSource.close();
//        listOfCat_next.addAll(listOfCat);

//        viewPager = (ViewPager) findViewById(R.id.pager);
//        ertegi = new ArrayList<ClassErtegi>();

//        ertegi.add(new ClassErtegi("Ur Torpagym Ur!", "Page1", R.drawable.abc_ic_clear_holo_light));
//        ertegi.add(new ClassErtegi("Ur Torpagym Ur!", "Page2", R.drawable.abc_ic_clear_holo_light));
//        ertegi.add(new ClassErtegi("Ur Torpagym Ur!", "Page3", R.drawable.abc_ic_clear_holo_light));
//        ertegi.add(new ClassErtegi("Ur Torpagym Ur!", "Page4", R.drawable.abc_ic_clear_holo_light));


//        adapter = new ViewPagerAdapter(this, content);
//        viewPager.setAdapter(adapter);


    }
//    private class ViewPagerAdapter extends PagerAdapter {
//        Activity activity;
////        List<MakalCategory> ertegi;
//        LayoutInflater inflater;
//        String ertegi;
//        public ViewPagerAdapter(Activity activity, String ertegi) {
//            this.activity = activity;
//            this.ertegi = ertegi;
//        }
//
//        @Override
//        public int getCount() {
//            return ertegi.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object o) {
//            return view == ((LinearLayout) o);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//
//            TextView ertegiName;
//            TextView ertegiContent;
//            final ImageButton back, next;
//            int ertegiAudio;
//
//            inflater = (LayoutInflater) activity
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View itemView = inflater.inflate(R.layout.ertegi_view_pager_item, container,
//                    false);
//
//            ertegiName = (TextView) itemView.findViewById(R.id.textViewName);
//            ertegiContent = (TextView) itemView.findViewById(R.id.textViewContent);
//            back = (ImageButton) itemView.findViewById(R.id.imageButtonBack);
//            next = (ImageButton) itemView.findViewById(R.id.imageButtonNext);
//
//            back.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pagePosition = viewPager.getCurrentItem();
//                    Log.i("Page:", pagePosition+"");
//                    if (pagePosition == 0) {
//                        back.setEnabled(false);
//                    }else{
//                        viewPager.setCurrentItem(pagePosition - 1, true);
//                        pagePosition = pagePosition - 1;
//                    }
//
//                    if (pagePosition != ertegi.size()){
//                        next.setEnabled(true);
//                    }
//                }
//            });
//
//            next.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pagePosition = viewPager.getCurrentItem();
//                    viewPager.setCurrentItem(pagePosition + 1, true);
//                    pagePosition = pagePosition + 1;
//                    Log.i("Page:", pagePosition+"");
//                    back.setEnabled(true);
//                    if (pagePosition == ertegi.size()-1) {
//                        next.setEnabled(false);
//                    }
//
//                }
//            });
//            ertegiName.setText(ertegi.get(position).getContent());
//            ertegiContent.setText(ertegi.get(position).getTag());
//
//            ((ViewPager) container).addView(itemView);
//
//            return itemView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager) container).removeView((LinearLayout) object);
//
//        }
//    }
//
}
