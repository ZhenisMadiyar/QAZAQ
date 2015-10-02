package com.madiyar.ikazak.kazak.database;

import java.util.Comparator;

/**
 * Created by madiyar on 11/23/14.
 */
public class MakalCategory {
    long id;
    String tag;
    String point;
    String content;
    String biography;
    byte[] blob;

    public byte[] getByte() {
        return blob;
    }

    public void setByte(byte[] blob) {
        this.blob = blob;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    boolean selected;

    public MakalCategory(){

    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
    public void setTag(String tag){
        this.tag = tag;
    }
    public String getTag(){
        return tag;
    }
    public void setConent(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }
    public String toString(){
        return content;
    }
    public static Comparator<MakalCategory> makalCategoryComparator = new Comparator<MakalCategory>() {

        public int compare(MakalCategory s1, MakalCategory s2) {
            String StudentName1 = s1.getContent().toUpperCase();
            String StudentName2 = s2.getContent().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };
}
