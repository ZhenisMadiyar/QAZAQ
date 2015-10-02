package com.madiyar.ikazak.kazak.traditions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.Makal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by madiyar on 11/20/14.
 */
public class Traditions extends Activity {
    TraditionAdapter adapter;
    ArrayList<TraditionList> arrayList;
    private String[] allColumns_tradition = {DatabaseHelper.COLUMN_ID_TRADITION, DatabaseHelper.COLUMN_TAG_TRADITION,
            DatabaseHelper.COLUMN_TRADITION_TITLE, DatabaseHelper.COLUMN_TRADITION_CONTENT};
    DataBaseSource dataSource;
    List<Makal> listOfCat;
    List<Makal> listOfCat_next;
    String tag;
    String content;
    ListView listView;


//    // array list to store section positions
//    ArrayList<Integer> mListSectionPos;
//
//    // array list to store listView data
//    ArrayList<String> mListItems;
//
//    // custom list view with pinned header
//    PinnedHeaderListView mListView;
//
//    // custom adapter
//    PinnedHeaderAdapter mAdaptor;
//
//    // search box
////    EditText mSearchView;
//
//    // loading view
//    ProgressBar mLoadingView;
//
//    // empty view
//    TextView mEmptyView;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.traditions);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        content = intent.getStringExtra("content");
        getActionBar().setTitle(content);

        listOfCat = new ArrayList<Makal>();
        listOfCat_next = new ArrayList<Makal>();
        dataSource = new DataBaseSource(getApplicationContext());
        dataSource.open();
        listOfCat = dataSource.getTradition(DatabaseHelper.TABLE_TRADITION, allColumns_tradition, tag);
        dataSource.close();
        listOfCat_next.addAll(listOfCat);
        Collections.sort(listOfCat_next, Makal.makalComparator);

        listView = (ListView) findViewById(R.id.listViewTradition);
        adapter = new TraditionAdapter(this, listOfCat_next);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), TraditionView.class);
                intent.putExtra("content", listOfCat_next.get(position).getPoint());
                intent.putExtra("con", listOfCat_next.get(position).getContent());
                startActivity(intent);
            }
        });

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int mLastFirstVisibleItem = 0;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {   }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (view.getId() == listView.getId()) {
//                    final int currentFirstVisibleItem = listView.getFirstVisiblePosition();
//
//                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
//                        // getSherlockActivity().getSupportActionBar().hide();
//                        getActionBar().hide();
//                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
//                        // getSherlockActivity().getSupportActionBar().show();
//                        getActionBar().show();
//                    }
//
//                    mLastFirstVisibleItem = currentFirstVisibleItem;
//                }
//            }
//        });
//        for (int i = 0; i < listOfCat_next.size(); i++) {
//            mListItems.add(listOfCat_next.get(i).getContent());
//            Log.i("INFO:", listOfCat_next.get(i).getContent());
//        }
//
//
//        if (savedInstanceState != null) {
//            mListItems = savedInstanceState.getStringArrayList("mListItems");
//            mListSectionPos = savedInstanceState.getIntegerArrayList("mListSectionPos");
//
//            if (mListItems != null && mListItems.size() > 0 && mListSectionPos != null && mListSectionPos.size() > 0) {
//                setListAdaptor();
//            }
//
////            String constraint = savedInstanceState.getString("constraint");
////            if (constraint != null && constraint.length() > 0) {
////                mSearchView.setText(constraint);
////                setIndexBarViewVisibility(constraint);
////            }
//        } else {
//                new Poplulate().execute(mListItems);
//        }

//

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


    // sort array and extract sections in background Thread here we use
    // AsyncTask
//    private class Poplulate extends AsyncTask<ArrayList<String>, Void, Void> {
//
//        private void showLoading(View contentView, View loadingView, View emptyView) {
////            contentView.setVisibility(View.GONE);
////            loadingView.setVisibility(View.VISIBLE);
////            emptyView.setVisibility(View.GONE);
//        }
//
//        private void showContent(View contentView, View loadingView, View emptyView) {
////            contentView.setVisibility(View.VISIBLE);
////            loadingView.setVisibility(View.GONE);
////            emptyView.setVisibility(View.GONE);
//        }
//
//        private void showEmptyText(View contentView, View loadingView, View emptyView) {
////            contentView.setVisibility(View.GONE);
////            loadingView.setVisibility(View.GONE);
////            emptyView.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // show loading indicator
////            showLoading(mListView, mLoadingView, mEmptyView);
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(ArrayList<String>... params) {
//            mListItems.clear();
//            mListSectionPos.clear();
//            ArrayList<String> items = params[0];
//            if (listOfCat_next.size() > 0) {
//
//                // NOT forget to sort array
////                Collections.sort(items, new SortIgnoreCase());
//                Collections.sort(items);
//                Collections.sort(listOfCat_next, Makal.makalComparator);
//
//                String prev_section = "";
//                for (int i = 0; i < listOfCat_next.size(); i++ ) {
//                    String current_section = listOfCat_next.get(i).getContent().substring(0, 1).toUpperCase(Locale.getDefault());
//
//                    if (!prev_section.equals(current_section)) {
//                        mListItems.add(current_section);
//                        mListItems.add(listOfCat_next.get(i).getContent());
//                        // array list of section positions
//                        mListSectionPos.add(mListItems.indexOf(current_section));
//                        prev_section = current_section;
//                    } else {
//                        mListItems.add(listOfCat_next.get(i).getContent());
//                    }
//                }
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (!isCancelled()) {
//                if (mListItems.size() <= 0) {
//                    showEmptyText(mListView, mLoadingView, mEmptyView);
//                } else {
//                    setListAdaptor();
//                    showContent(mListView, mLoadingView, mEmptyView);
//                }
//            }
//            super.onPostExecute(result);
//        }
//    }
//
//    public class SortIgnoreCase implements Comparator<String> {
//        public int compare(String s1, String s2) {
//            return s1.compareToIgnoreCase(s2);
//        }
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        if (mListItems != null && mListItems.size() > 0) {
//            outState.putStringArrayList("mListItems", mListItems);
//        }
//        if (mListSectionPos != null && mListSectionPos.size() > 0) {
//            outState.putIntegerArrayList("mListSectionPos", mListSectionPos);
//        }
////        String searchText = mSearchView.getText().toString();
//        if (searchText != null && searchText.length() > 0) {
//            outState.putString("constraint", searchText);
//        }
//        super.onSaveInstanceState(outState);
//    }

}
