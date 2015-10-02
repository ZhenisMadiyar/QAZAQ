package com.madiyar.ikazak.kazak.writers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.DataBaseSource;
import com.madiyar.ikazak.kazak.database.DatabaseHelper;
import com.madiyar.ikazak.kazak.database.MakalCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madiyar on 11/21/14.
 */
public class Biography_activity extends Fragment {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    DataBaseSource dataSource;
    List<MakalCategory> listOfCat;
    List<MakalCategory> listOfCat_next;
    //    String writerList[] = {"asdad", "asdasd", "asdasd", "asdasd", "asdasd", "asdasd", "asdasd"};
    private String[] allColumns_writer_list = { DatabaseHelper.COLUMN_ID_WL,DatabaseHelper.COLUMN_TAG_WL,DatabaseHelper.COLUMN_CONTENT_WL, DatabaseHelper.COLUMN_BIOGRAPHY_WL};
    public static final String TABLE_WRITER_LIST  = "WriterList";
    int pos;
    TextView textView;
    ImageView imageView;
    int images[] = {R.drawable.abay, R.drawable.akhmet_baitursynuly, R.drawable.mirzhakyp_dulat,
            R.drawable.fariza_ongarsynova, R.drawable.iliyas_zhansugirov,
            R.drawable.kasym_amanzholov, R.drawable.magzhan_zhumabaev,
            R.drawable.mukagali_makataev, R.drawable.mukhtar_shakhanov,
            R.drawable.tumanbay_moldagaliyev, R.drawable.saken_seifulin,
            R.drawable.shakarim_kudaiberdiuly, R.drawable.sultanmakhmut_toraigyrov,
            R.drawable.tolegen_aibergenov, R.drawable.ybyrai_altynsarin,
            R.drawable.zhambyl_zhabayev, R.drawable.tumanbay_moldagaliyev};
    TextView nameWriter;
    String names[];
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writer_biography, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pos = prefs.getInt("pos", 0);

        names = getResources().getStringArray(R.array.writer_name);
        textView = (TextView) view.findViewById(R.id.textViewWriterBio);
        imageView = (ImageView) view.findViewById(R.id.imageViewAkyn);
        nameWriter = (TextView) view.findViewById(R.id.textViewNameAuthor);
        nameWriter.setText(names[pos]);

        listOfCat = new ArrayList<MakalCategory>();
        listOfCat_next = new ArrayList<MakalCategory>();
        dataSource = new DataBaseSource(getActivity());
        dataSource.open();
        listOfCat = dataSource.getWriterList(TABLE_WRITER_LIST, allColumns_writer_list);
        dataSource.close();
        listOfCat_next.addAll(listOfCat);
        textView.setText(listOfCat_next.get(pos).getBiography());
        imageView.setBackground(getResources().getDrawable(images[pos]));
        return view;
    }
}
