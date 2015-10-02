/**
 * ShelfView Application v1.2
 * 
 * This project is a tutorial project and free.
 * 
 *  Android version: IceCream Sandwich
 *  @author: Omid nazifi
 *  Date: 5/10/2012
 * 
 */
package com.onazifi.shelf;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShelfViewActivity extends ListActivity {
	/** Called when the activity is first created. */
	private VerticalAdapter verListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * Calling Library & BookItem classes for create list of groups
		 *  groupbyArrayBookItem return back array of array of items
		 */
		Library lb = new Library();
		for (BookItem item : BookItem.ALL_BOOKS) {
			lb.addBookItem(item);
		}
		ArrayList<ArrayList<BookItem>> groupList = new ArrayList<ArrayList<BookItem>>();
		groupList = lb.groupbyArrayBookItem(Library.AUTHOR);

		verListAdapter = new VerticalAdapter(this, R.layout.row, groupList);
		setListAdapter(verListAdapter);

		verListAdapter.notifyDataSetChanged();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ShelfViewActivity.this, "CLICKED"+i, Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	/**
	 * This class add a list of ArrayList to ListView that it include multi
	 * items as bookItem.
	 */
	private class VerticalAdapter extends ArrayAdapter<ArrayList<BookItem>> {

		private int resource;

		public VerticalAdapter(Context _context, int _ResourceId,
				ArrayList<ArrayList<BookItem>> _items) {
			super(_context, _ResourceId, _items);
			this.resource = _ResourceId;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View rowView;

			if (convertView == null) {
				rowView = LayoutInflater.from(getContext()).inflate(resource,
						null);
			} else {
				rowView = convertView;
			}

            GridView gridView = (GridView) rowView.findViewById(R.id.subListview);
			HorizontalAdapter horListAdapter = new HorizontalAdapter(getContext(), R.layout.item, getItem(position));
            gridView.setAdapter(horListAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), getItem(position).get(i).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
			return rowView;
		}
	}

	/*
	 * This class add some items to Horizontal ListView this ListView include
	 * several bookItem.
	 */
    private class HorizontalAdapter extends ArrayAdapter<BookItem> {

        private int resource;

        public HorizontalAdapter(Context _context, int _textViewResourceId,ArrayList<BookItem> _items) {
            super(_context, _textViewResourceId, _items);
            this.resource = _textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(getContext()).inflate(this.resource, null);

            TextView topText = (TextView) retval.findViewById(R.id.title);
//			TextView bottomText = (TextView) retval.findViewById(R.id.author);

            topText.setText(getItem(position).getTitle());
//			bottomText.setText(getItem(position).getTitle());

            return retval;
        }
    }
}