package com.madiyar.ikazak.kazak.database;

import java.util.Comparator;

/**
 * Created by madiyar on 11/23/14.
 */
public class Makal {

    long id;
    String tag;
    String point;
    String content;
    String biography;
    boolean selected;

    public Makal(){

    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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
    public void setPoint(String point){
        this.point = point;
    }
    public String getPoint(){
        return point;
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
    public void setSelected(boolean selected){
        this.selected = selected;

    }

    public static Comparator<Makal> makalComparator = new Comparator<Makal>() {

        public int compare(Makal s1, Makal s2) {
            String StudentName1 = s1.getContent().toUpperCase();
            String StudentName2 = s2.getContent().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    public boolean isSelected(){
        return selected;

    }

}
