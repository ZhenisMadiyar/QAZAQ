package com.onazifi.shelf;

import android.graphics.Bitmap;

import java.util.Comparator;

public class BookItem {

    private String author;
    private String title;
    private Bitmap image;
    private String content;
    public BookItem() {
        // TODO Auto-generated constructor stub
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BookItem(String _author, String _title, String _content) {
        this.author = _author;
        this.title = _title;
        this.content = _content;

//		this.image = _image;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String _author) {
        this.author = _author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap _image) {
        this.image = _image;
    }

    public static Comparator<BookItem> StuNameComparator = new Comparator<BookItem>() {

        public int compare(BookItem s1, BookItem s2) {
            String StudentName1 = s1.getAuthor().toUpperCase();
            String StudentName2 = s2.getAuthor().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    @Override
    public String toString() {
        return "[ name="+ author + "]";
    }
    public static final BookItem[] ALL_BOOKS = {
            new BookItem("1", "Ақ тышқан мен нәресте", "Content"),
            new BookItem("1", "Ай неге жалаңаш қалды", "Content"),
            new BookItem("2", "Ақыл, ғылым, бақыт", "Content"),
            new BookItem("13", "Ақылды етікші", "Content"),
            new BookItem("3", "Алтын балық", "Content"),
            new BookItem("3", "Батпан құйрық", "Content"),
            new BookItem("4", "Ғылманның өнері", "Content"),
            new BookItem("4", "Екі дос", "Content"),
            new BookItem("5", "Екі лақ", "Content"),
            new BookItem("5", "Жақсылық пен жамандық", "Content"),
            new BookItem("6", "Жеті өнерпаз", "Content"),
            new BookItem("6", "Қаңбақ шал", "Content"),
            new BookItem("7", "Қарт пен тапқыр жігіт", "Content"),
            new BookItem("7", "Қарттың ұлына өсиеті", "Content"),
            new BookItem("8", "Қасқыр мен кісі", "Content"),
            new BookItem("8", "Кім күшті", "Content"),
            new BookItem("9", "Қу түлкі", "Content"),
            new BookItem("9", "Өгіз", "Content"),
            new BookItem("10", "Өнеге", "Content"),
            new BookItem("10", "Патшаның қызы неге бақытсыз", "Content"),
            new BookItem("11", "Түлкі мен Бөдене", "Content"),
            new BookItem("11", "Түлкі мен ешкі", "Content"),
            new BookItem("12", "Үш ауыз ақыл сөз", "Content"),
            new BookItem("12", "Үш жетім", "Content"),
            new BookItem("2", "Ұр,тоқпақ!", "Content")
    };

}
//	public static final BookItem[] ALL_BOOKS={
//        new BookItem("Nahjolbalaqe","Dashti"),
//        new BookItem("Prof Tan","Text Mining"),
//        new BookItem("Ralph","Java convenient"),
//        new BookItem("Islam","Ali"),
//        new BookItem("Ralph","Java"),
//        new BookItem("Ralph","J2ME"),
//        new BookItem("Ralph","24 hours with Java"),
//        new BookItem("Ralph","Java Workshop"),
//        new BookItem("Prof Tan","Introduce DataMining"),
//        new BookItem("Dr. Salimi","Algorithms"),
//        new BookItem("Prof Tan","Proffessional DataMining"),
//        new BookItem("Dr. Salimi","DataMining 1"),
//        new BookItem("Dr. Salimi","Data Structures"),
//        new BookItem("Dr. Salimi","DataMinig 2"),
//        new BookItem("Dr. Salimi","C++ Programing"),
//        new BookItem("Dr. Salimi","24 hours with C#"),
//        new BookItem("Dr. Salimi","24 hours with C++"),
//        new BookItem("Mark Murphy","Begining Android 3"),
//        new BookItem("Mark Murphy","Begining Android 2"),
//        new BookItem("Mark Murphy","Pro Android 3"),
//        new BookItem("Mark Murphy","Pro Android Games"),
//        new BookItem("Mark Murphy","Begining Android Games"),
//        new BookItem("Reto Meier","C# Programing"),
//        new BookItem("Reto Meier","Android Programing"),
//        new BookItem("Reto Meier","C++ Programing"),
//        new BookItem("Reto Meier","PHP Programing"),
//        new BookItem("Reto Meier","Design Pattern"),
//        new BookItem("Reto Meier",".Net Framework 3.5"),
//        new BookItem("Reto Meier",".Net Framework 4"),
//        new BookItem("Reto Meier","ASP.net Web services"),
//        new BookItem("Reto Meier","Android Games"),
//        new BookItem("Dr. Salimi","TextMining")
//    };