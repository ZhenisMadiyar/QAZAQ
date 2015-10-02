package com.madiyar.ikazak.kazak.makal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.madiyar.ikazak.kazak.R;
import com.madiyar.ikazak.kazak.database.Makal;

import java.util.HashMap;
import java.util.List;

/**
 * Created by madiyar on 11/16/14.
 */
public class AdapterMakalList extends BaseAdapter {
    Activity activity;
    List<Makal> makal;
    int textSize;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences prefsFavourite;
    SharedPreferences.Editor editorFavourite;
    Typeface typeface;
    ViewHolder holder = null;
    MediaPlayer mediaPlayer;
    String locale;
    int category;
    int sounds[];
    boolean click = true;
    int color;
    boolean c = true;
    static HashMap<String, Integer> hm = new HashMap<String, Integer>();
    MP3PlayerService mp3Service;
    int last_pos = 0;
    private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            mp3Service = ((MP3PlayerService.LocalBinder) binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

    public AdapterMakalList(Activity activity, List<Makal> makal, int textSize, Typeface typeface, int category, int color) {
        this.activity = activity;
        this.makal = makal;
        this.category = category;
        this.textSize = textSize;
        this.typeface = typeface;
        this.color = color;
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        prefsFavourite = PreferenceManager.getDefaultSharedPreferences(activity);

        locale = activity.getResources().getConfiguration().locale.toString();
        activity.startService(new Intent(activity, MP3PlayerService.class));
        //bind to our service by first creating a new connectionIntent
        Intent connectionIntent = new Intent(activity, MP3PlayerService.class);
        activity.bindService(connectionIntent, mp3PlayerServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public int getCount() {
        return makal.size();
    }

    @Override
    public Object getItem(int position) {
        return makal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(activity,
                    R.layout.row_item_makal_list, null);
            holder = new ViewHolder();

            holder.text = (TextView) view.findViewById(R.id.textViewMakalList);
            holder.play = (ImageButton) view.findViewById(R.id.imageButtonPlay);
            holder.share = (ImageButton) view.findViewById(R.id.imageButtonShare);
            holder.like = (ImageButton) view.findViewById(R.id.imageButtonLike);

            view.setTag(holder);
            holder.like.setTag(makal.get(position).getPoint());
            holder.share.setTag(makal.get(position).getContent());

        } else {
            holder = (ViewHolder) view.getTag();
            ((ViewHolder) view.getTag()).like.setTag(makal.get(position).getPoint());
            ((ViewHolder) view.getTag()).share.setTag(makal.get(position).getContent());
        }
//        Toast.makeText(getApplicationContext(), locale, Toast.LENGTH_SHORT).show();
        if (locale.equals("ru_ru")) {
            fillRusHashMap();
        } else if (locale.equals("en_gb")) {
            fillEngHashMap();
        } else {
            fillKazHashMap();
        }
        holder.text.setText(makal.get(position).getContent());
        holder.text.setTextColor(color);
        holder.text.setTextSize(textSize);
        holder.text.setTypeface(typeface);

        int color = Integer.decode("#f9c82d");
        int color2 = Integer.decode("#009edb");


//        final PlayPauseDrawable mPlayPauseDrawable = new PlayPauseDrawable(20, 0XFFE91E63, 0XFFffffff, 300);
//        holder.play.setImageDrawable(mPlayPauseDrawable);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    v.setSelected(false);
                }
                if (hm.containsKey(makal.get(position).getContent())) {
                    if (v.isSelected()) {
//                    mp3Service.pauseSong(activity);
                        mediaPlayer.pause();
                        v.setSelected(false);
                    } else {
//                    mp3Service.playSong(activity, sounds[position]);

                        mediaPlayer = MediaPlayer.create(activity, hm.get(makal.get(position).getContent()));
                        v.setSelected(true);
                        mediaPlayer.start();
                    }
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            v.setSelected(false);
                        }
                    });
                } else
                    Toast.makeText(activity, R.string.makal_sound, Toast.LENGTH_SHORT).show();

//                if (mediaPlayer.isPlaying()) {
//                    v.setSelected(false);
//                } else {
//                    v.setSelected(true);
//                    Log.i("SOUND:", "RELEASED");
//                    mediaPlayer.release();
//                }
//                Toast.makeText(activity, "Play clicked", Toast.LENGTH_SHORT).show();
            }
//            @Override
//            public void onClick(final View v) {
////                mPlayPauseDrawable.toggle();
//                if (hm.containsKey(makal.get(position).getContent())) {
//                    if (v.isSelected()) {
//                        v.setSelected(false);
//                        mPlayPauseDrawable.animatePlay();
////                    mp3Service.pauseSong(activity);
//                        mediaPlayer.pause();
//                    } else {
//                        mPlayPauseDrawable.animatePause();
////                    mp3Service.playSong(activity, sounds[position]);
//                        mediaPlayer = MediaPlayer.create(activity, hm.get(makal.get(position).getContent()));
//                        v.setSelected(true);
//                        mediaPlayer.start();
////                    holder.play.setBackground(activity.getResources().getDrawable(R.drawable.pause));
//                    }
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            mp.release();
//                            v.setSelected(false);
//                        }
//                    });
//                }else
//                    Toast.makeText(activity, "Бұл мақалдың аудиосы табылмады", Toast.LENGTH_SHORT).show();
//
////                if (mediaPlayer.isPlaying()) {
////                    v.setSelected(false);
////                } else {
////                    v.setSelected(true);
////                    Log.i("SOUND:", "RELEASED");
////                    mediaPlayer.release();
////                }
////                Toast.makeText(activity, "Play clicked", Toast.LENGTH_SHORT).show();
//            }
        });


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) v.getTag();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "QAZAQ:");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
                activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        holder.like.setSelected(prefs.getBoolean(makal.get(position).getPoint(), false));
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String star = (String) v.getTag();
                if (v.isSelected()) {
                    editor = prefs.edit();
                    editor.putBoolean(star, false);
                    editor.commit();
                    v.setSelected(false);
                } else if (!v.isSelected()) {
                    editor = prefs.edit();
                    editor.putBoolean(star, true);
                    editor.commit();
//                    Log.d("hello", star);
                    v.setSelected(true);
                }
                Toast.makeText(activity, "Like clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void fillKazHashMap() {
        //adam
        hm.put("Адам деген ардақты ат.", R.raw.adam_1);
        hm.put("Біреу  біреуге сүйеу.", R.raw.adam_2);
        hm.put("Талабы жоқ жас – қанаты жоқ құс.", R.raw.adam_3);
        hm.put("Адам бір-біріне қонақ.", R.raw.adam_4);
        hm.put("Адамға адам жат емес.", R.raw.adam_5);
        hm.put("Адамына қарай сәлемі.", R.raw.adam_6);
        hm.put("Адамның сырты алдамшы.", R.raw.adam_7);
        hm.put("Адам қайғысы – заман қайғысы.", R.raw.adam_8);
        hm.put("Адам ұйымшыл, мал үйіршіл.", R.raw.adam_9);
        hm.put("Ағаш көркі — жапырақ,\n" +
                "Адам көркі — шүберек.", R.raw.adam_10);
        hm.put("Кісі қатесіз болмас,\n" +
                "Көл бақасыз болмас.", R.raw.adam_11);
        hm.put("Болған кісі, болдым демес,\n" +
                "Болдым десе, болғаны емес.", R.raw.adam_12);
        hm.put("Адам жүрген жермен адам жүреді,\n" +
                "Біреу білмегенді біреу біледі.", R.raw.adam_13);
        hm.put("Ат жақсысын мақта,\n" +
                "Адам жақсысын жақта.", R.raw.adam_14);
        hm.put("Күйесіз қазан жоқ,\n" +
                "Қайғысыз адам жоқ.", R.raw.adam_15);
        hm.put("Жасық адам жасына жетпей қартаяр,  \n" +
                "Асыл адам жасына жетпей марқаяр.", R.raw.adam_16);
        hm.put("Қағазға тіл бітірген — қалам,\n" +
                "Жансызға жан бітірген — адам.", R.raw.adam_17);
        hm.put("Дүниеде тас қатты,\n" +
                "Тастан да бас қатты.", R.raw.adam_18);
        hm.put("Адамның ұяты бетінде,\n" +
                "Адамгершілігі ниетінде.", R.raw.adam_19);
        hm.put("Атаңның баласы болма,\n" +
                "Адамның баласы бол.", R.raw.adam_20);

//        hm.put("Адам жүрген жермен адам жүреді,\n" +
//                "Біреу білмегенді біреу біледі.",0);
//        hm.put("Ат жақсысын мақта,\n" +
//                "Адам жақсысын жақта.",0);
//        hm.put("Күйесіз қазан жоқ,\n" +
//                "Қайғысыз адам жоқ.",0);
//        hm.put("Жасық адам жасына жетпей қартаяр,  \n" +
//                "Асыл адам жасына жетпей марқаяр.",0);
//        hm.put("Қағазға тіл бітірген — қалам,\n" +
//                "Жансызға жан бітірген — адам.",0);
//        hm.put("Дүниеде тас қатты,\n" +
//                "Тастан да бас қатты.",0);
//        hm.put("Адамның ұяты бетінде,\n" +
//                "Адамгершілігі ниетінде.",0);
//        hm.put("Атаңның баласы болма,\n" +
//                "Адамның баласы бол.",0);
        Log.i("", "*******");
        //akyl
        hm.put("Жақсы ай мен күндей, әлемге бірдей.", R.raw.akyldylyk_1);
        hm.put("Ат жаманы таймен ойнар.", R.raw.akyldylyk_2);
        hm.put("Жаман сиыр жазда бұзаулайды.", R.raw.akyldylyk_3);
        hm.put("Жақсыдан қалма, жаманға баспа.", R.raw.akyldylyk_4);
        hm.put("Көрмес, түйені де көрмес.", R.raw.akyldylyk_5);
        hm.put("Шаш ал десе, бас алар.", R.raw.akyldylyk_6);
        hm.put("Ақылсыз жігіт – ауыздықсыз ат.", R.raw.akyldylyk_7);
        hm.put("Ақыл байлық – азбас байлық.", R.raw.akyldylyk_8);
        hm.put("Адамның тізгіні – ақыл.", R.raw.akyldylyk_9);
        hm.put("Ашу тасады, ақыл басады.", R.raw.akyl_6);
        hm.put("Жақсыдан үйрен, жаманнан жирен.", R.raw.akyl_7);
        hm.put("Жақсы өз басынан көреді, \n" +
                "Жаман жолдасынан көреді.", R.raw.akyl_8);
        hm.put("Жақсы байқап сөйлер,\n" +
                "Жаман шайқап сөйлер.", R.raw.akyl_9);
        hm.put("Қорқақтың көзі үлкен,\n" +
                "Ақымақтың сөзі үлкен.", R.raw.akyl_10);
        hm.put("Ашу шабыстырады,\n" +
                "Ақыл жарастырады.", R.raw.akyl_11);
        hm.put("Ақылдыға жан қымбат,\n" +
                "Ақылсызға мал қымбат.", R.raw.akyl_12);
        hm.put("Көп ақылды жиналса,\n" +
                "Қиын істі бітірер.\n" +
                "Көп ақымақ жиналса,\n" +
                "Оңай істі бүлдірер.", R.raw.akyl_13);
        hm.put("Ақымақпен астаспа, ақылдымен қастаспа.", R.raw.akyl_14);
        hm.put("Досы көпті жау алмайды,\n" +
                "Ақылы көпті дау алмайды.", R.raw.akyl_15);
        hm.put("Жақсы сыйлағанының құлы,\n" +
                "Жаман қорыққанының құлы.", R.raw.akyl_16);

        //ar namys
        hm.put("Мал сақтама, ар сақта.", R.raw.namys_1);
        hm.put("Ел намысы – ер намысы.", R.raw.namys_2);
        hm.put("Жігіттің құны жүз жылқы, ары мың жылқы.", R.raw.namys_3);
        hm.put("Ер жігітке өлімнен ұят күшті.", R.raw.namys_4);
        hm.put("Үлкен пышақ ұялғанынан өтеді.", R.raw.namys_5);
        hm.put("Малыңа сүйенбе, арына сүйен.", R.raw.namys_6);
        hm.put("Ер мойнында қыл арқан шірімес.", R.raw.namys_7);
//        hm.put("",R.raw.);
        hm.put("Жарлы болсаң да, арлы бол.", R.raw.namys_8);
        hm.put("Беттің арын белбеуге түйіп алып.", R.raw.namys_9);
        hm.put("Тәнім – жанымның садағасы,\n" +
                "Жаным – арымның садағасы.", R.raw.namys_10);
        hm.put("Арсыз - жеңдім дейді, \n" +
                "Арлы - көндім дейді.", R.raw.namys_11);
        hm.put("Ұры - байымайды, сұғанақ - семірмейді.", R.raw.namys_12);
        hm.put("Баланың ұяты - әкеге, \n" +
                "Қыз ұяты - шешеге", R.raw.namys_13);
        hm.put("Көп - қорқытар, терең - батырар.", R.raw.namys_14);
        hm.put("Көпті жамандаған көмусіз қалады.", R.raw.namys_15);
        hm.put("Келгенше - қонақ ұялар, \n" +
                "Келген соң - үй иесі ұялады", R.raw.namys_16);
        hm.put("Ерді - намыс өлтірер, \n" +
                "Қоянды - қамыс өлтірер.", R.raw.namys_17);
        hm.put("Ар жазасы - бар жазадан ауыр жаза.", R.raw.namys_18);
        hm.put("Ат - шабысына қарай шабады, \n" +
                "Ер - намысына қарай шабады.", R.raw.namys_19);
        hm.put("Бұл жалғанды бұзатын-арсыз адам.", R.raw.namys_20);
//        hm.put("Беттің арын белбеуге түйіп алып.",R.raw.namys_);
        //bilim
        hm.put("Білімді бесіктен тесікке дейін ізден.", R.raw.bilim_1);
        hm.put("Ғылым – теңіз, білім – қайық.", R.raw.bilim_2);
        hm.put("Жердің сәні – егін, ердің сәні – білім.", R.raw.bilim_3);
        hm.put("Көп жасаған білмейді, көп көрген біледі.", R.raw.bilim_4);
        hm.put("Өзі білмегеннің аузына қарама.", R.raw.bilim_5);
        hm.put("Тіліңмен жүгірме, біліммен жүгір.", R.raw.bilim_6);
        hm.put("Ұстазыңды ұлы әкеңдей сыйла.", R.raw.bilim_7);
        hm.put("Құлақ естігенді көз көрер.", R.raw.bilim_8);
        hm.put("Асу бермес асқар жоқ.", R.raw.bilim_9);
        hm.put("Ұстаздан шәкірт озар.", R.raw.bilim_10);
        hm.put("Кітап – алтын қазына.", R.raw.bilim_11);
        hm.put("Оқусыз білім жоқ, білімсіз күнің жоқ.", R.raw.bilim_12);
        hm.put("Білім инемен құдық қазғандай.", R.raw.bilim_13);
        hm.put("Білегіңе сенбе, біліміңе сен.", R.raw.bilim_14);
        hm.put("Оқу – білім бұлағы, білім – өмір шырағы.", R.raw.bilim_15);
        hm.put("Білімнің басы – бейнет, соңы – зейнет.", R.raw.bilim_16);
        hm.put("Жердің сәні — егін,\n" +
                "Ердің сәні — білім.", R.raw.bilim_17);
        hm.put("Ат сүрінбей жер танымас,\n" +
                "Ер сүріибей ел танымас.", R.raw.bilim_18);
        hm.put("Оқу — білім азығы,\n" +
                "Білім — ырыс қазығы.", R.raw.bilim_19);
        hm.put("Мың малың болғанша,\n" +
                "Бір балаң ғалым болсын.", R.raw.bilim_20);
        //dostyk
        hm.put("Татулық – табылмас бақыт.", R.raw.dostyk_1);
        hm.put("Досыңды мақтағаның – өзіңді жақтағаның.", R.raw.dostyk_2);
        hm.put("Жолдасы көптің – олжасы көп.", R.raw.dostyk_3);
        hm.put("Елсізде ит те жолдас.", R.raw.dostyk_4);
        hm.put("Досы жоқ адам – тұзы жоқ тағам.", R.raw.dostyk_5);
        hm.put("Дос бергеннің түсіне қарама.", R.raw.dostyk_6);
        hm.put("Есептескен ел болмас.", R.raw.dostyk_7);
        hm.put("Дос тұтқанды қадірле.", R.raw.dostyk_8);
        hm.put("Жігіт жолдасынан белгілі.", R.raw.dostyk_9);
        hm.put("Тату үйдің бақыты тасыр,\n" +
                "Ұрыс-керіс үйдің ырысы кашар.", R.raw.dostyk_10);
        hm.put("Досыңның пышағымен мүйіз кес,\n" +
                "Дұшпаныңның пышағымен киіз кес.", R.raw.dostyk_11);
        hm.put("Дос сыртыңнан мақтар,\n" +
                "Дұшпан көзіңе мақтар.", R.raw.dostyk_12);
        hm.put("Дұшпан күлдіріп айтады,\n" +
                "Дос жылатып айтады.", R.raw.dostyk_13);
        hm.put("Дұшпаныңнан бір сақтан,\n" +
                "Жаман достан мың сақтан.", R.raw.dostyk_14);
        hm.put("Жақсымен жүрсең,\n" +
                "Жақсы боларсың.\n" +
                "Жаманмен жүрсең,\n" +
                "Жын урып, бақсы боларсың.", R.raw.dostyk_15);
        hm.put("Жаман жолдастан жақсы дұшпан артық.", R.raw.dostyk_16);
        hm.put("Досы көптің жаны семіреді,\n" +
                "Асы көптің тәні семіреді.", R.raw.dostyk_17);
        hm.put("Өз басыңды дауға берсең де,\n" +
                "Жолдасынды жауға берме.", R.raw.dostyk_18);
        hm.put("Қадірінді білгің келсе,\n" +
                "Көршіңнен қарыз сұра.", R.raw.dostyk_19);
        hm.put("Ай көрмесең туысың жат,\n" +
                "Жыл көрмесең жолдасың жат.", R.raw.dostyk_20);
        //til
        hm.put("Тіс – тілдің қамалы.", R.raw.til_1);
        hm.put("Көп сөз – көмір, аз сөз – алтын.", R.raw.til_2);
        hm.put("Көзі жаманның – сөзі жаман.", R.raw.til_3);
        hm.put("Ми ойлағанды тіл тындырады.", R.raw.til_4);
        hm.put("Тілмен тікен де алады.", R.raw.til_5);
        hm.put("Бұралқы сөз – күлуге жақсы.", R.raw.til_6);
        hm.put("Піл көтермегенді тіл көтереді.", R.raw.til_7);
        hm.put("Таудай сөздің тарыдай түйіні бар.", R.raw.til_8);
        hm.put("Сөз шынды табар.", R.raw.til_9);
        hm.put("Піл күшті, піл күшті емес, тіл күшті.", R.raw.til_10);
        hm.put("Басқа пәле – тілден.", R.raw.til_11);
        hm.put("Айтылған сөз – атылған оқ.", R.raw.til_12);
        hm.put("Ойнап сөйлесең де, ойлап сөйле.", R.raw.til_13);
        hm.put("Батырды шешен аттан түсіріпті.", R.raw.til_14);
        hm.put("Өнер алды – қызыл тіл.", R.raw.til_15);
        hm.put("Көз жетпеген жерге сөз жетеді.", R.raw.til_16);
        hm.put("Тіл – тиексіз.", R.raw.til_17);
        hm.put("Орынсыз сөз өзіңе тиер.", R.raw.til_18);
        hm.put("Алмас қылыш майданда серік,\n" +
                "Асыл сөз майданда да, сайранда да серік.", R.raw.til_19);
        hm.put("Қотыр қолдан жұғады,\n" +
                "Пәле тілден жұғады.", R.raw.til_20);
        //enbek
        hm.put("Еріншекке жоқ сылтау.", R.raw.enbek_1);
        hm.put("Жалқаудың соры – байлығы.", R.raw.enbek_2);
        hm.put("Көңілсіз бастаған іс көпке бармайды.", R.raw.enbek_3);
        hm.put("Жер тойынбай, ел тойынбайды.", R.raw.enbek_4);
        hm.put("Жеріне қарай астығы.", R.raw.enbek_5);
        hm.put("Ер дәулеті – еңбек.", R.raw.enbek_6);
        hm.put("Бақыт кілті – еңбекте.", R.raw.enbek_7);
        hm.put("Көп еңбегі – көңілді.", R.raw.enbek_8);
        hm.put("Тілде бар да, істе жоқ.", R.raw.enbek_9);
        hm.put("Түйір нан – тамшы тер.", R.raw.enbek_10);
        hm.put("Жалқауға күнде той.", R.raw.enbek_11);
        hm.put("Істемеген тістемейді.", R.raw.enbek_12);
        hm.put("Жалқауға сөз, шабанға таяқ өтпейді.", R.raw.enbek_13);
        hm.put("Мал баққанға бітеді.", R.raw.enbek_14);
        hm.put("Кім еңбек етсе – сол тоқ.", R.raw.enbek_15);
        hm.put("Есесі қайтқан еңбек – игі.", R.raw.enbek_16);
        hm.put("Ер тынысы – еңбеқ, ез тынысы – ермек.", R.raw.enbek_17);
        hm.put("Бейнетің қатты болса,\n" +
                "Татқаның тәтті болар.", R.raw.enbek_beinet);
        hm.put("Еңбек — өмірді ұзартады,\n" +
                "Ұят бетті қызартады.", R.raw.enbek_18);
        hm.put("Еңбек адамды бүтіндейді.\n" +
                "Жалқаулық адамды түтіпжейді.", R.raw.enbek_19);
        //otbasy
        hm.put("Балалы үйдің ұрлығы жатпас.", R.raw.otbasy_1);
        hm.put("Барды – балама, жоқты – санама.", R.raw.otbasy_2);
        hm.put("Бесіксіз үйде береке жоқ.", R.raw.otbasy_3);
        hm.put("Әкеге баланың алалығы жоқ", R.raw.otbasy_4);
        hm.put("Ерке бала екі жылар.", R.raw.otbasy_5);
        hm.put("Ағайын – алтау, ана – біреу.", R.raw.otbasy_6);
        hm.put("Қыз – қонақ.", R.raw.otbasy_7);
        hm.put("Ұл туғанға – күн туар.", R.raw.otbasy_8);
        hm.put("Ата – бәйтерек, бала – жапырақ.", R.raw.otbasy_9);
        hm.put("Ата – балаға сыншы.", R.raw.otbasy_10);
        hm.put("Бес саусақ бірдей емес.", R.raw.otbasy_11);
        hm.put("Ердің анасы – елдің анасы.", R.raw.otbasy_12);
        hm.put("Қарға баласын аппағым дер,\n" +
                "Кірпі баласын жұмсағым дер.",R.raw.otbasy_13);
        hm.put("Ананың көңілі балада,\n" +
                "Баланың көңілі далада.",R.raw.otbasy_14);
        hm.put("Құс балапаны үшін тұзаққа түседі,\n" +
                "Адам баласы үшін азапқа түседі.",R.raw.otbasy_15);
        hm.put("Ағайынның алтын сарайынан\n" +
                "Ананың жыртық лашығы артық.",R.raw.otbasy_16);
        hm.put("Өгізге туған күн, бұзауға да туады.",R.raw.otbasy_17);
        hm.put("Атадан жақсы ұл туса,\n" +
                "Елінің қамын жейді.\n" +
                "Атадан жаман ұл туса,\n" +
                "Елінің малын жейді.",R.raw.otbasy_18);
        hm.put("Он бала бір әкеге жүк болмайды,\n" +
                "Бір әке он балаға жүк болады.",R.raw.otbasy_19);
        hm.put("Тайдың мінгені білінбес.\n" +
                "Баланың істегені білінбес.", R.raw.otbasy_20);
        Log.i("", "");
        //otan
        hm.put("Адасқанның айыбы жоқ, қайтып үйірін тапқан соң.", R.raw.otan_1);
        hm.put("Ерінен айырылған көмгенше жылайды,\n" +
                "Елінен айырылған өлгенше жылайды.", R.raw.otan_2);
        hm.put("Отан оттан да ыстық.", R.raw.otan_3);
        hm.put("Ел іші – алтын бесік.", R.raw.otan_4);
        hm.put("Туған жердің ауасы да шипа.", R.raw.otan_5);
        hm.put("Отансыз адам – ормансыз бұлбұл.", R.raw.otan_6);
        hm.put("Отан үшін күрес – ерге тиген үлес.", R.raw.otan_7);
        hm.put("Отанды сүю отбасынан басталады.", R.raw.otan_8);
        hm.put("Туған жердің жуасы да тәтті.", R.raw.otan_9);
        hm.put("Әркімнің өз жері – жұмақ.", R.raw.otan_10);
        hm.put("Өз елінде көртышқан да батыр.", R.raw.otan_11);
        hm.put("Ит тойған жеріне, ер туған жеріне.", R.raw.otan_12);
        hm.put("Егілмеген жер жетім, елінен айырылған ер жетім.", R.raw.otan_13);
        hm.put("Үйде оңбаған, түзде де оңбайды.", R.raw.otan_14);
        hm.put("Сағынған елін аңсайды, \n" +
                "Сары ала қаз көлін аңсайды.", R.raw.otan_15);
        hm.put("Туған жердің күні де ыстық,\n" +
                "Түні де ыстық.", R.raw.otan_16);
        hm.put("Елінен безген ер болмас,\n" +
                "Көлінен безген қаз болмас.", R.raw.otan_17);
        hm.put("Жат жердің қаршығасынан\n" +
                "Өз еліңнің қарғасы артық.", R.raw.otan_18);
        hm.put("Кісі елінде сұлтан болғанша,\n" +
                "Өз елінде ұлтан бол.", R.raw.otan_19);
        hm.put("Отанға опасыздық еткенің,\n" +
                "Өз түбіңе өзің жеткенің.", R.raw.otan_20);
        //otirik
        hm.put("Шын – бір сөз, өтірік – мың сөз.", R.raw.otirik_1);
        hm.put("Көп қайда болса, шындық сонда.", R.raw.otirik_2);
        hm.put("Шындық – қамал бұзар.", R.raw.otirik_3);
        hm.put("Өсекші мен өтірікші – егіз.", R.raw.otirik_4);
        hm.put("Өтірік сөз өрге баспас.", R.raw.otirik_5);
        hm.put("Адалдық-ана сүті.", R.raw.otirik_6);
        hm.put("Ақ сөз ащы болады.", R.raw.otirik_7);
        hm.put("Аққа құдай да жақ.", R.raw.otirik_8);
        hm.put("Шындық бар жерде өтірік байқап жүреді,\n" +
                "Шындық жоқ жерде шіреніп, шайқап жүреді.", R.raw.otirik_9);
        hm.put("Бал тамған өтіріктен, қан тамған шындык артық.", R.raw.otirik_10);
        hm.put("Өзінің қисықтығын білмеген өзгенің түзулігін білмейді.", R.raw.otirik_11);
        hm.put("Шын - бір сөз, өтірік - мың сөз.", R.raw.otirik_12);
        hm.put("Өзі өтірікші өзгенің шын сөзіне сенбейді.", R.raw.otirik_13);
        hm.put("Қаланың жаңалығын даладағы ел бұрын естиді.", R.raw.otirik_14);
        hm.put("Түстік жерге өтірік айтсаң, кешке ізіңмен барады.", R.raw.otirik_15);
        hm.put("Кұлақ не естісе, ауыз соны айтады.", R.raw.otirik_16);
        hm.put("Жақсы өтіріктен жаман шындық артық.", R.raw.otirik_17);
        hm.put("Өтірік айтып жағынғанша,\n" +
                "Шыныңды айтып жалын.", R.raw.otirik_18);
        hm.put("Өтіріктің өзіне нанба, қисынына нан.", R.raw.otirik_19);
        hm.put("Шындықтан зор палуан жоқ:\n" +
                "Ол жауды да жығады,\n" +
                "Қамалды да бұзады.", R.raw.otirik_20);
    }

    private void fillRusHashMap() {
        //chelovek
        hm.put("Человек – достойное имя.", R.raw.chel_1);
        hm.put("Человек – опора человеку.", R.raw.chel_2);
        hm.put("Человек без цели, что птица без крыльев.", R.raw.chel_3);
        hm.put("В жизни человек человеку – гость.", R.raw.chel_4);
        hm.put("Человек не родился врагом человеку.", R.raw.chel_5);
        hm.put("Каков человек, таковы будут ему и почести.", R.raw.chel_6);
        hm.put("Внешность человека бывает обманчива.", R.raw.chel_7);
        hm.put("Заботы человека – заботы его времени.", R.raw.chel_8);
        hm.put("Скотину к стаду тянет, человека – к людям.", R.raw.chel_9);
        hm.put("Самая святая пища — хлеб,\n" +
                "Самое святое имя — человек.", R.raw.chelovek_10);
        hm.put("Труд украшает человека, а человек — свое время.", R.raw.chelovek_11);
        hm.put("Человек без мечты, что земля голая без леса.", R.raw.chelovek_12);
        hm.put("Человек крепче камня и нежнее цветка.", R.raw.chelovek_13);
        hm.put("Хорошего коня похвали,\n" +
                "Хорошего человека поддержи.", R.raw.chelovek_14);
        hm.put("У каждого человека есть своя высота.", R.raw.chelovek_15);
        hm.put("Мягкое дерево для червей добыча,\n" +
                "Мягкий человек для всякого добыча.", R.raw.chelovek_16);
        hm.put("Нет озера без лягушек, \n" +
                "Человека — без ошибок.", R.raw.chelovek_17);
        hm.put("Волка не насытят овцы, человека думы.", R.raw.chelovek_18);
        hm.put("Совесть человека на лице,\n" +
                "а человечность в поступках.", R.raw.chelovek_19);
        hm.put("Железо испытывается на огне, \n" +
                "Человек познается в беде.", R.raw.chelovek_20);
        //um
        hm.put("Мудрец, как солнце и луна, \n" +
                "Всему миру светит.", R.raw.um_1);
        hm.put("Прикажешь волосы снять – \n" +
                "рад голову снести.", R.raw.um_2);
        hm.put("Глупый конь с жеребёнком играет.", R.raw.um_3);
        hm.put("Глупая корова среди лета телится.", R.raw.um_4);
        hm.put("К дурню не подходи, \n" +
                "от умницы не отступай.", R.raw.um_5);
        hm.put("Не желающий видеть \n" +
                "и верблюда не видит.", R.raw.um_6);
        hm.put("Глупый жигит, что конь без узды.", R.raw.um_7);
        hm.put("Ум – богатство неиссякаемое.", R.raw.um_8);
        hm.put("Ум для человека, что вожжи для лошади.", R.raw.um_9);
        hm.put("Богатство дается не за красоту, \n" +
                "Ум — не за богатство.", R.raw.um_10);
        hm.put("Ребенок укрепляет супругов единство,\n" +
                "Мудрец укрепляет народа единство.", R.raw.um_11);
        hm.put("Глупый козел бодлив, \n" +
                "Глупый джигит драчлив.", R.raw.um_12);
        hm.put("Гнев — враг, ум — друг,\n" +
                "к своему уму еще ума прибавь.", R.raw.um_13);
        hm.put("Дыра над дырочкой смеется.", R.raw.um_14);
        //sovest
        hm.put("Не добро береги, а честь свою береги.", R.raw.sovest_1);
        hm.put("Честь народа – честь жигита.", R.raw.sovest_2);
        hm.put("Цена жигиту – сто коней, \n" +
                "Цена его чести – тысяча коней.", R.raw.sovest_3);
        hm.put("Для жигита лучше смерть, чем позор.", R.raw.sovest_4);
        hm.put("Большой нож со стыда режет.", R.raw.sovest_5);
        hm.put("На богатство не полагайся, \n" +
                "а на совесть опирайся.", R.raw.sovest_6);
        hm.put("За жигитом даже волосяная верёвка не пропадает.", R.raw.sovest_7);
        hm.put("Пусть и беден, но будь честен.", R.raw.sovest_8);
        //znaniye
        hm.put("Дорога к знаниям начинается с колыбели.", R.raw.znanie_1);
        hm.put("Наука – море, знания – лодка.", R.raw.znanie_2);
        hm.put("Землю украшает урожай, человека – знания.\n", R.raw.znanie_3);
        hm.put("Много знает не тот, кто больше прожил, \n" +
                "а тот, кто больше видел.", R.raw.znanie_4);
        hm.put("Не заглядывай в рот тому, \n" +
                "Кто сам ничего не знает.", R.raw.znanie_5);
        hm.put("Стремись удивить знаниями, а не красноречием.", R.raw.znanie_7);
        hm.put("Учение – основа знаний, знания – основа счастья.", R.raw.znanie_8);
        hm.put("Что услышат уши, то и глаза увидят.", R.raw.znanie_9);
        hm.put("Нет ничего невозможного.", R.raw.znanie_10);
//        hm.put("Книга – кладовая золота.", R.raw.znanie_13);
        hm.put("Нет жизни без знания, нет знания без учения.", R.raw.znanie_13);
        hm.put("Глубину науки постигать, что иголкой колодец копать.", R.raw.znanie_14);
        hm.put("Рассчитывай не на силу, а на свои знания.", R.raw.znanie_15);
        hm.put("Учение – основа знания, знание – основа жизни.", R.raw.znanie_17);
//        hm.put("В учении трудно, да плоды учения сладки.", R.raw.znanie_18);
//        hm.put("Прилежный ученик превзойдёт учителя.", R.raw.znanie_);

        //druzhba
        hm.put("Дружба – бесценное богатство.", R.raw.druzhba_1);
        hm.put("Друга хвалить, что себя похвалить.", R.raw.druzhba_2);
        hm.put("Друзей много – шире дорога.", R.raw.druzhba_3);
        hm.put("Когда людей нет – и собака друг.", R.raw.druzhba_4);
        hm.put("Жить без друзей, что есть пищу без соли.", R.raw.druzhba_5);
        hm.put("Подарок друга берут, не разглядывая.", R.raw.druzhba_6);
        hm.put("Услугами считаться – дружбу потерять.", R.raw.druzhba_7);
        hm.put("Уважай всякого, кто хочет жить в дружбе.", R.raw.druzhba_8);
        hm.put("О жигите судят по его друзьям.", R.raw.druzhba_9);

        //iyazyk
        hm.put("Зубы ограда для языка", R.raw.iyazyk_1);
        hm.put("Многословие — уголь, красноречие — золото.", R.raw.iyazyk_2);
        hm.put("Не жди от плохого человека хорошего слова.", R.raw.iyazyk_3);
        hm.put("О чём голова подумает, про то язык и скажет.", R.raw.iyazyk_4);
        hm.put("Язык и занозу вытащит.", R.raw.iyazyk_5);
        hm.put("Шутка хороша тем, что над ней можно посмеяться.", R.raw.iyazyk_6);
        hm.put("Сила языка намного сильнее силы слона.", R.raw.iyazyk_7);
        hm.put("Слов много, а сути мало.", R.raw.iyazyk_8);
        hm.put("Слово всегда найдёт правду.", R.raw.iyazyk_9);
        hm.put("Слон всех сильнее, а язык и слона сильнее.", R.raw.iyazyk_10);
        hm.put("Беда голове от языка.", R.raw.iyazyk_11);
        hm.put("Сказанное слово – как вылетевшая пуля.", R.raw.iyazyk_12);
        hm.put("Даже когда шутишь думай о том, что скажешь.", R.raw.iyazyk_13);
        hm.put("Меткое слово сбивает батыра с седла.", R.raw.iyazyk_14);
        hm.put("Первое из искусств — слово.", R.raw.iyazyk_15);
        hm.put("Куда глаза не доходят, туда слово приведёт.", R.raw.iyazyk_16);
        hm.put("У языка нет препятствий.", R.raw.iyazyk_17);
        hm.put("Неуместно сказать – себя наказать.", R.raw.iyazyk_18);

        //trud
        hm.put("У лентяя всегда найдётся оправдание.", R.raw.trud_1);
        hm.put("Богатство лентяю лень прибавляет.", R.raw.trud_2);
        hm.put("Дело, начатое без души, начатым и останется.", R.raw.trud_3);
        hm.put("Пока землю не насытишь, народ не накормишь.", R.raw.trud_4);
        hm.put("Урожай – по плодородию земли.", R.raw.trud_6);
        hm.put("Богатство жигита – труд.", R.raw.trud_7);
        hm.put("Счастья ключи – в труде.", R.raw.trud_8);
        hm.put("Коллективный труд – самый радостный труд.", R.raw.trud_9);
        hm.put("На словах всё может, а как делать – ничего не умеет.", R.raw.trud_10);
        hm.put("В кусочке хлеба – капля пота.", R.raw.trud_11);
        hm.put("Для лодыря каждый день – праздник.", R.raw.trud_12);
        hm.put("Кто не работает, тот не ест.", R.raw.trud_13);
        hm.put("Ленивого словом не проймёшь, неповоротливого – палкой.", R.raw.trud_14);
        hm.put("Скот множится у того, кто умеет пасти.", R.raw.trud_15);
        hm.put("Кто работает, тот и сыт.", R.raw.trud_16);
        hm.put("Хороший труд тот, который не напрасен.", R.raw.trud_17);
        hm.put("Жизнь жигита – в работе, жизнь лентяя – в гулянках.", R.raw.trud_18);

        //semya
        hm.put("В доме с детьми тайн не бывает.", R.raw.semya_1);
        hm.put("Всё, что есть – детям, нужду себе.", R.raw.semya_2);
        hm.put("В доме без колыбели уюта нет.", R.raw.semya_3);
        hm.put("Для отца все дети одинаковы.", R.raw.semya_4);
        hm.put("Избалованный ребёнок дважды плачет.", R.raw.semya_5);
        hm.put("Родни много, а мать – одна.", R.raw.semya_6);
        hm.put("Дочь – гостья в доме.", R.raw.semya_7);
        hm.put("Мать, родившую сына, счастье ждёт.", R.raw.semya_8);
        hm.put("Отец – дерево могучее,\n" +
                "Дети – его листья.", R.raw.semya_9);
        hm.put("Отец – судья своим детям.", R.raw.semya_10);
//        hm.put("Пять пальцев на одной руке, а все разные.", R.raw.semya_11);
        hm.put("Мать, родившая героя, всему народу мать.", R.raw.semya_11);

        //rodina
        hm.put("Не велико заблуждение,\n" +
                "Коль назад нашёл дорогу к своим.", R.raw.rodina_1);
        hm.put("Потерю супруга до могилы оплакивают,\n" +
                "Потерю Родины – до самой смерти.", R.raw.rodina_2);
        hm.put("Родина горячее огня.", R.raw.rodina_3);
        hm.put("Родная земля – золотая колыбель.", R.raw.rodina_4);
        hm.put("На Родине и воздух – лекарство.", R.raw.rodina_5);
        hm.put("Человек без Родины, что соловей без леса.", R.raw.rodina_6);
        hm.put("Жигита доля – за Родину стоять.", R.raw.rodina_7);
        hm.put("И дикий лук на Родине сладок.", R.raw.rodina_8);
        hm.put("Каждому родная земля – рай.", R.raw.rodina_9);
        hm.put("В своей норе и крот чувствует себя сильным.", R.raw.rodina_10);
        hm.put("Собаке хорошо, где сытно, жигиту – на Родине.", R.raw.rodina_11);
        hm.put("Незасеянная пашня – сирота, \n" +
                "Жигит без родины – сирота.", R.raw.rodina_12);
        hm.put("Если не нашёл счастья дома, \n" +
                "Не обретёшь и на чужбине.", R.raw.rodina_13);
        hm.put("Гусь тоскует по озеру родному, человек – по Родине.", R.raw.rodina_14);
        hm.put("На Родине и дни, и ночи прекрасны.", R.raw.rodina_15);
        //pravda
        hm.put("Правда – всего лишь одно слово, ложь – тысяча.", R.raw.pravda_1);
        hm.put("Где большинство, там и правда.", R.raw.pravda_2);
        hm.put("Правде покоряются крепости.", R.raw.pravda_3);
        hm.put("Ложь и сплетня — близнецы.", R.raw.pravda_4);
        hm.put("На лжи далеко не уедешь.", R.raw.pravda_5);
        hm.put("Правда чиста, как молоко матери.", R.raw.pravda_6);
        hm.put("Правда бывает горькая.", R.raw.pravda_7);
        hm.put("Безвинного и бог поддержит.", R.raw.pravda_8);
    }

    private void fillEngHashMap() {
        //friend
        hm.put("A friend's frown is better than a fool's smile.", R.raw.friendship_1);
        hm.put("A friend in need is a friend indeed.", R.raw.friendship_2);
        hm.put("A friend is easier lost than found.", R.raw.friendship_3);
        hm.put("A friend to everybody is a friend to nobody.", R.raw.friendship_4);
        hm.put("A problem shared is a problem halved.", R.raw.friendship_5);
        hm.put("A true friend is someone who reaches for your hand, but touches your heart.", R.raw.friendship_6);
        hm.put("False friends are worse than open enemies.", R.raw.friendship_7);
        hm.put("Flattery is all right so long as you don't inhale.", R.raw.friendship_8);
        hm.put("Give credit where credit is due.", R.raw.friendship_9);
        hm.put("Grief divided is made lighter.", R.raw.friendship_10);
        hm.put("Memory is the treasure of the mind.", R.raw.friendship_11);
        hm.put("Nothing dries sooner than a tear.", R.raw.friendship_12);
        hm.put("Old friends and old wine are best.", R.raw.friendship_13);
        hm.put("The best of friends must part.", R.raw.friendship_14);
        hm.put("The best things are not bought and sold.", R.raw.friendship_15);
        hm.put("There is no better looking-glass than an old friend.", R.raw.friendship_16);
        hm.put("To err is human (To forgive divine).", R.raw.friendship_17);
        hm.put("Two cannot fall out if one does not choose.", R.raw.friendship_18);

        //love
        hm.put("A loveless life is a living death.", R.raw.love_1);
        hm.put("Absence makes the heart grow fonder.", R.raw.love_2);
        hm.put("All's fair in love and war.", R.raw.love_3);
        hm.put("Beauty is in the eye of the beholder.", R.raw.love_4);
        hm.put("Before you meet the handsome prince \n" +
                "you have to kiss a lot of toads.", R.raw.love_5);
        hm.put("Better to have loved and lost, than to have never loved at all.", R.raw.love_6);
        hm.put("Cold hands, warm heart.", R.raw.love_7);
        hm.put("Distance makes the heart grow fonder.", R.raw.love_8);
        hm.put("Faint heart never won fair lady.", R.raw.love_9);
        hm.put("First impressions are the most lasting.", R.raw.love_10);
        hm.put("Hatred is as blind as love.", R.raw.love_11);
        hm.put("Love and a cough cannot be hid.", R.raw.love_12);
        hm.put("Love does much but money does all.", R.raw.love_13);
        hm.put("Love levels all inequalities.", R.raw.love_14);
        hm.put("Love makes a good eye squint.", R.raw.love_15);
        hm.put("Love sees no faults.", R.raw.love_16);
        hm.put("Love sought is good, but given unsought is better.", R.raw.love_17);
        hm.put("Love to live and live to love.", R.raw.love_18);
        hm.put("Love with life is heaven; and life unloving, hell.", R.raw.love_19);
        hm.put("Man is the head but woman turns it.", R.raw.love_20);
        hm.put("Marry in haste, repent at leisure.", R.raw.love_21);

        //relationship
        hm.put("A good friend is one's nearest relation.", R.raw.relationship_1);
        hm.put("Conscience makes cowards of us all.", R.raw.relationship_12);
        hm.put("A man of straw needs a woman of gold.", R.raw.relationship_3);
        hm.put("A wink is as good as a nod, to a blind man.", R.raw.relationship_4);
        hm.put("An injury is forgiven better than an injury revenged.", R.raw.relationship_5);
        hm.put("Anger and hate hinder good counsel.", R.raw.relationship_6);
        hm.put("Appearances are deceptive.", R.raw.relationship_7);
        hm.put("At a round table there's no dispute about the place.", R.raw.relationship_8);
        hm.put("Attack is the best form of defence", R.raw.relationship_9);
        hm.put("Be slow in choosing, but slower in changing.", R.raw.relationship_10);
        hm.put("Confess and be hanged.", R.raw.relationship_11);
        hm.put("Don't blow your own trumpet.", R.raw.relationship_13);
        hm.put("Do unto others as you would have them do to you.", R.raw.relationship_15);
        hm.put("Do as you would be done by.", R.raw.relationship_14);

        //life
        hm.put("All things come to those that wait.", R.raw.life_1);
        hm.put("An eye for an eye and a tooth for a tooth.", R.raw.life_2);
        hm.put("An open door may tempt a saint.", R.raw.life_3);
        hm.put("As one door closes, another always opens.", R.raw.life_4);
        hm.put("As you go through life, make this your goal, \n" +
                "watch the doughnut and not the hole.", R.raw.life_5);
        hm.put("Brevity is the soul of wit.", R.raw.life_6);
        hm.put("Cut your coat according to the cloth.", R.raw.life_7);
        hm.put("Discretion is the better part of valour.", R.raw.life_8);
        hm.put("Do right and fear no man.", R.raw.life_9);
        hm.put("Easy come, easy go.", R.raw.life_10);
        hm.put("Experience is the hardest teacher. \n" +
                "She gives the test first and the lesson afterwards.", R.raw.life_11);
        hm.put("Familiarity breeds contempt.", R.raw.life_12);
        hm.put("Fortune favours the brave.", R.raw.life_13);
        hm.put("Hall binks are oft sliddery.", R.raw.life_15);
        hm.put("He who laughs last, laughs longest.", R.raw.life_16);
        hm.put("Home is where the heart is.", R.raw.life_17);
        hm.put("Hope for the best and prepare for the worst.", R.raw.life_18);
        hm.put("If wishes were horses, beggars would ride.", R.raw.life_19);
        hm.put("In the country of the blind, the one-eyed man is king.", R.raw.life_20);
        hm.put("It never rains but it pours.", R.raw.life_21);

        //health
        hm.put("A watched pot never boils.", R.raw.health_1);
        hm.put("After dinner rest a while, \n" +
                "After supper walk a mile.", R.raw.health_2);
        hm.put("After dinner rest a while, \n" +
                "After supper walk a mile. ", R.raw.health_3);
        hm.put("An apple a day keeps the doctor away.", R.raw.health_4);
        hm.put("A drowning man will clutch at a straw.", R.raw.health_5);
        hm.put("An onion a day keeps everyone away.", R.raw.health_6);
        hm.put("Another pot ! Try the teapot.", R.raw.health_7);
        hm.put("Be not a baker if your head is made of butter.", R.raw.health_8);
        hm.put("Beauty is but skin deep.", R.raw.health_9);
        hm.put("Better late thrive than never do well.", R.raw.health_10);
        hm.put("Better to be poor and healthy rather than rich and sick.", R.raw.health_11);
        hm.put("Better to wear out than rust out.", R.raw.health_12);
        hm.put("Bread never falls but on its buttered side.", R.raw.health_13);
        hm.put("Cleanliness is next to Godliness.", R.raw.health_14);
        hm.put("Content is health to the sick and riches to the poor.", R.raw.health_15);
        hm.put("Don't bite the hand that feeds you.", R.raw.health_16);
        hm.put("Drink like a fish, water only.", R.raw.health_17);
        hm.put("Early to bed, \n" +
                "Early to rise, \n" +
                "Makes you healthy, \n" +
                "Wealthy & wise.", R.raw.health_18);
        hm.put("Fair words butter no cabbage.", R.raw.health_19);

        //conversation
        hm.put("A bad excuse is better then none.", R.raw.conversation_1);
        hm.put("Actions speak louder than words.", R.raw.conversation_2);
        hm.put("Angry words fan the fire like wind.", R.raw.conversation_3);
        hm.put("Bad news travels fast.", R.raw.conversation_4);
        hm.put("Call a spade a spade.", R.raw.conversation_5);
        hm.put("Do as I say not as I do.", R.raw.conversation_6);
        hm.put("Don't advertise: Tell it to a gossip", R.raw.conversation_7);
        hm.put("Don't go off half-cocked.", R.raw.conversation_8);
        hm.put("Few words and many deeds", R.raw.conversation_9);
        hm.put("For donkeys' ages.", R.raw.conversation_10);
        hm.put("Gossips are frogs, they drink and talk.", R.raw.conversation_11);
        hm.put("He who sings drives away sorrow.", R.raw.conversation_12);
        hm.put("If you don't say it you will not have to unsay it.", R.raw.conversation_13);
        hm.put("It takes two to have an argument.", R.raw.conversation_14);
        hm.put("Keep your mouth shut and your eyes open.", R.raw.conversation_15);
        hm.put("Listen to the pot calling the kettle black.", R.raw.conversation_16);
        hm.put("Many a true word spoken in jest.", R.raw.conversation_17);
        hm.put("No news is good news.", R.raw.conversation_18);
        hm.put("Nothing is ill said if it is not ill taken.", R.raw.conversation_19);
        hm.put("One picture is worth a thousand words.", R.raw.conversation_20);

        //wisdom
        hm.put("Accidents will happen.", R.raw.wisdom_6);
        hm.put("A stitch in time saves nine.", R.raw.wisdom_2);
        hm.put("A throne is only a bench covered in purple velvet.", R.raw.wisdom_3);
        hm.put("A wise man shall hold his tongue", R.raw.wisdom_4);
        hm.put("Till he sees his opportunity.", R.raw.wisdom_5);
        hm.put("All's well that ends well.", R.raw.wisdom_7);
        hm.put("Attack is the best means of defence.", R.raw.wisdom_8);
        hm.put("Charity begins at home.", R.raw.wisdom_9);
        hm.put("Different strokes for different folks.", R.raw.wisdom_10);
        hm.put("Don't try to teach your grand-mother to suck eggs.", R.raw.wisdom_11);
        hm.put("Empty vessels make the most sound.", R.raw.wisdom_12);
        hm.put("Empty barrels make the most noise.", R.raw.wisdom_13);
        hm.put("Four eyes are better than two.", R.raw.wisdom_14);
        hm.put("Innocent as a new born babe.", R.raw.wisdom_15);
        hm.put("It's an ill wind that blows no-one some good.", R.raw.wisdom_16);
        hm.put("No one can be caught in places he does not visit.", R.raw.wisdom_17);
        hm.put("No wise man ever wishes to be younger.", R.raw.wisdom_18);
        hm.put("Not in a month of Sundays.", R.raw.wisdom_19);
        hm.put("One man's junk is another man's treasure.", R.raw.wisdom_20);

        //growth
        hm.put("A little body doth often harbour a great soul.", R.raw.grouth_1);
        hm.put("A point is the beginning of magnitude.", R.raw.grouth_2);
        hm.put("A spark can start a great fire.", R.raw.grouth_3);
        hm.put("A short cut is often a wrong cut.", R.raw.grouth_4);
        hm.put("Big fish eat little fish.", R.raw.grouth_5);
        hm.put("Everything has an end.", R.raw.grouth_6);
        hm.put("Fall seven times. Stand up eight.", R.raw.grouth_7);
        hm.put("First in best dressed.\n", R.raw.grouth_8);
        hm.put("From trivial things, great contests often arise.", R.raw.grouth_9);
        hm.put("Give them an inch and they'll take a mile.\n", R.raw.grouth_10);
        hm.put("Grow angry slowly, there's plenty of time.", R.raw.grouth_11);
        hm.put("He who hesitates is lost.", R.raw.grouth_12);
        hm.put("It's either all or nothing.", R.raw.grouth_13);
        hm.put("It is easier to destroy than to build.", R.raw.grouth_15);
        hm.put("It is the first step that is difficult.", R.raw.grouth_16);
        hm.put("Little strokes fell great oaks.\n", R.raw.grouth_17);
        hm.put("Lost time is never found again.", R.raw.grouth_18);
        hm.put("Many drops make a shower.", R.raw.grouth_19);
//        hm.put("", R.raw.grouth_);
//        hm.put("", R.raw.grouth_);
        //money
        hm.put("A bad penny always comes back.", R.raw.money_1);
        hm.put("A light purse makes a heavy heart.", R.raw.money_2);
        hm.put("A little each day is much in a year.", R.raw.money_3);
        hm.put("A man's intentions seldom add to his income.", R.raw.money_4);
        hm.put("A penny saved is a penny earned.", R.raw.money_5);
        hm.put("A poor man is better than a liar.", R.raw.money_6);
        hm.put("A single penny fairly got is worth a thousand that are not.", R.raw.money_7);
        hm.put("Always you are to be rich next year.", R.raw.money_8);
        hm.put("Beggars can't be choosers.", R.raw.money_9);
        hm.put("Better to have than to wish.", R.raw.money_10);
        hm.put("Diamonds are forever.", R.raw.money_11);
        hm.put("Every man has his price.", R.raw.money_12);
        hm.put("Every man is the architect of his destiny.", R.raw.money_13);
        hm.put("Experience is the father of wisdom.", R.raw.money_14);
        hm.put("Fair exchange is no robbery.", R.raw.money_15);
        hm.put("He has enough who is content.", R.raw.money_16);
        hm.put("He that pays last never pays twice.", R.raw.money_17);
        hm.put("He is rich that is satisfied.", R.raw.money_18);
        hm.put("In for a penny, in for a pound.", R.raw.money_19);
        hm.put("It is better to be born lucky than rich.", R.raw.money_20);
    }

    private static class ViewHolder {
        TextView text;
        ImageButton share, like;
        ImageButton play;
    }
}
