package com.onazifi.shelf;

import org.quaere.Group;

import java.util.ArrayList;
import java.util.Collections;

import static org.quaere.DSL.from;

public class Library {

	private ArrayList<BookItem> arrayBookItem;
	public static final int AUTHOR = 1;
	public static final int TITLE = 2;
	public static final int RATE = 3;
	public static final int DOWNLOAD_DATE = 4;

	public Library() {
		arrayBookItem = new ArrayList<BookItem>();
	}

	public void setColectionBookItem(ArrayList<BookItem> _array) {
		this.arrayBookItem = _array;
	}

	public void addBookItem(BookItem _bi) {
		this.arrayBookItem.add(_bi);
	}

	public ArrayList<ArrayList<BookItem>> groupbyArrayBookItem(int type) {

		BookItem[] books = BookItem.ALL_BOOKS;
        ArrayList<BookItem> b = new ArrayList<BookItem>();
        for (int i = 0; i < books.length; i++) {
            b.add(books[i]);
        }
        Collections.sort(b, BookItem.StuNameComparator);
        for(BookItem str: b){
            System.out.println(str);
        }
		ArrayList<ArrayList<BookItem>> groupList = new ArrayList<ArrayList<BookItem>>();
		String getType = "";
		
		switch (type) {
		case AUTHOR:
			getType = "bookitem.getAuthor()";
			break;
		case TITLE:
			getType = "bookitem.getTitle()";
			break;
		case DOWNLOAD_DATE:
			getType = "bookitem.getDownloadDate()";
			break;
		case RATE:
			getType = "bookitem.getRate()";
			break;
		default:
			return groupList;
		}

		/*
		 * books is a object of BookItem
		 * bookitem is item for point to list
		 * getType is a string value for set type of grouping
		 * groupbyArrayBookItem return back array of array of items
		 */
		Iterable<Group> groups = from("bookitem").in(b).group("bookitem").by(getType).into("b").select("b");

		for (Group group : groups) {
            ArrayList<BookItem> obj = new ArrayList<BookItem>();
			for (Object Item : group.getGroup()) {
				obj.add((BookItem) Item);
            }
			groupList.add(obj);
		}
//        Collections.sort(obj, BookItem.StuNameComparator);
		return groupList;
	}
}
