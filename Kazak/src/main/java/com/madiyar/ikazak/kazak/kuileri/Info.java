package com.madiyar.ikazak.kazak.kuileri;

import java.util.Comparator;

/**
 * Created by madiyar on 12/25/14.
 */
public class Info {
    String name;
    String audio;
    String author;
    String imageUrl;

    public Info(String name, String audio, String author, String imageUrl) {
        this.name = name;
        this.audio = audio;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public static Comparator<Info> kuiComparator = new Comparator<Info>() {

        public int compare(Info s1, Info s2) {
            String StudentName1 = s1.getName().toUpperCase();
            String StudentName2 = s2.getName().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };
}
