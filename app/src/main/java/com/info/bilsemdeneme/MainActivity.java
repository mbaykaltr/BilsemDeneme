package com.info.bilsemdeneme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private ImageView soru_resim, resim_a,resim_b,resim_c,resim_d,sonraki,baloncuk_img;
    private ImageView zaman_bar_zemin,zaman_bar_dolan,SoruNo_bar_dolan,SoruNo_Zemin;
    private ImageView baslat_buton;
    private TextView tesekkur,aciklama_txt,soru_no_txt,soru_no_kalan_txt,sinav_aciklama_txt,baslik1_txt,baslik2_txt,hikaye_txt;
    private EditText ad_txt,soyad_txt,email_txt;
    private Button karne_buton;
    private String soru_resmi,verilen_cevap="E",dogru_cevap="E",aciklama,ek_aciklama;
    private int genislik,yukseklik,soru_no=0,tur=0,dogru_sayisi=0,sorusayisi, soru_id;
    private CardView card_soru,card_a,card_b,card_c, card_d;
    private Animation geridon,ileridon,iconacik,iconkapali,zaman,zaman30,zaman60,iconacikgecikmeli,paylasacik,paylaskapali;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ConstraintLayout soru_area;
    private ScrollView scrool,hikaye_scrool;
    private int tur1=0,tur2=0,tur3=0,tur4=0,tur5=0,tur6=0,tur7=0,tur8=0,zorluk=2,sinif;
    private ProgressBar progress_bekle;
    private CheckBox checkBox_mail;
    private ImageView fon_img;
    private RadioButton rb1Sinif, rb2Sinif, rb3Sinif;
    private RadioGroup rdySinif;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("SinavBilgisi",MODE_PRIVATE);
        editor = sp.edit();


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        genislik = size.x;
        yukseklik = size.y;

        double density = getResources().getDisplayMetrics().density;
        final int punto = (int)((genislik+1200)/(density*70));
        final int bosluk = ((genislik-200)/40);



        Log.e("ekran ölçüleri","Genişlik : "+String.valueOf(genislik)+" , Yükseklik : "+ String.valueOf(yukseklik));
        Log.e("çözünürlük",String.valueOf(density));

        soru_resim = findViewById(R.id.soru_resim);
        resim_a = findViewById(R.id.resim_a);
        resim_b = findViewById(R.id.resim_b);
        resim_c = findViewById(R.id.resim_c);
        resim_d = findViewById(R.id.resim_d);
        card_soru = findViewById(R.id.card_soru);
        card_a = findViewById(R.id.card_a);
        card_b = findViewById(R.id.card_b);
        card_c = findViewById(R.id.card_c);
        card_d = findViewById(R.id.card_d);
        sonraki=findViewById(R.id.sonraki);
        soru_area=findViewById(R.id.soru_are);
        tesekkur=findViewById(R.id.tesekkür_txt);
        aciklama_txt=findViewById(R.id.aciklama_txt);
        baloncuk_img=findViewById(R.id.baloncuk_img);
        zaman_bar_dolan=findViewById(R.id.zaman_bar_dolan);
        zaman_bar_zemin=findViewById(R.id.zaman_bar_zemin);
        soru_no_txt=findViewById(R.id.soru_no_txt);
        soru_no_kalan_txt=findViewById(R.id.soru_no_kalan);
        SoruNo_bar_dolan=findViewById(R.id.SoruNo_bar_dolan);
        SoruNo_Zemin=findViewById(R.id.SoruNo_Zemin);
        sinav_aciklama_txt=findViewById(R.id.sinav_aciklama_txt);
        baslik1_txt=findViewById(R.id.baslik1_txt);
        baslik2_txt=findViewById(R.id.baslik2_txt);
        baslat_buton=findViewById(R.id.baslat_buton);
        scrool=findViewById(R.id.scrool);
        hikaye_scrool=findViewById(R.id.hikaye_scrool);
        hikaye_txt=findViewById(R.id.hikaye_txt);
        ad_txt=findViewById(R.id.ad_txt);
        soyad_txt=findViewById(R.id.soyad_txt);
        email_txt = findViewById(R.id.email_txt);
        karne_buton=findViewById(R.id.karne_buton);
        progress_bekle = findViewById(R.id.progress_bekle);
        checkBox_mail = findViewById(R.id.checkBox_mail);
        fon_img = findViewById(R.id.fon_img);

        rdySinif = findViewById(R.id.rdySinif);
        rb1Sinif = findViewById(R.id.rb1Sinif);
        rb2Sinif = findViewById(R.id.rb2Sinif);
        rb3Sinif = findViewById(R.id.rb3Sinif);



        String urlfon = "https://app.1e1okul.com/bilsemdeneme/resim/fon_img.png";
        Picasso.with(this).load(urlfon).resize(genislik, yukseklik).resize(genislik,yukseklik).into(fon_img);



        card_soru.setRadius(yukseklik/50);
        card_a.setRadius(yukseklik/50);
        card_b.setRadius(yukseklik/50);
        card_c.setRadius(yukseklik/50);
        card_d.setRadius(yukseklik/50);

        ileridon = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ileridon);
        geridon = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.geridon);
        iconacik = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.iconacik);
        iconkapali = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.iconkapali);
        iconacikgecikmeli= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.iconacikgecikmeli);
        zaman30=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zaman30);
        zaman60=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zaman60);
        zaman= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zaman);
        paylasacik=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.paylasacik);
        paylaskapali= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.paylaskapali);


        soru_no = sp.getInt("soru_no",-1);
        dogru_sayisi = sp.getInt("dogru_sayisi",0);
        aciklama=sp.getString("aciklama","Açıklama yok");
        sinif = sp.getInt("sinif",0);
        sorusayisi=sp.getInt("sorusayisi",35);

       // if(soru_no==2){
       //   soru_no=soru_no+24;
       //  dogru_sayisi=dogru_sayisi+20;  }


        tur1=sp.getInt("tur1",0);
        tur2=sp.getInt("tur2",0);
        tur3=sp.getInt("tur3",0);
        tur4=sp.getInt("tur4",0);
        tur5=sp.getInt("tur5",0);
        tur6=sp.getInt("tur6",0);
        tur7=sp.getInt("tur7",0);
        tur8=sp.getInt("tur8",0);



        if(soru_no==-1){    //////  eğer soru_no=-1 ise bura  çalışsın



            soru_no_kalan_txt.setVisibility(View.INVISIBLE);
            soru_no_txt.setVisibility(View.INVISIBLE);
            soru_area.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorseffaf));

            baslik1_txt.setTextSize(3*punto);
            baslik2_txt.setTextSize((3*punto)/2);
            sinav_aciklama_txt.setTextSize(punto);



            baslik1_txt.setVisibility(View.VISIBLE);
            baslik2_txt.setVisibility(View.VISIBLE);
            sinav_aciklama_txt.setVisibility(View.VISIBLE);
            scrool.setVisibility(View.VISIBLE);


            String urlbslt = "https://app.1e1okul.com/bilsemdeneme/resim/baslat_buton_g.png";
            Picasso.with(this).load(urlbslt).resize(genislik / 5, genislik / 5).centerInside().into(baslat_buton);



            baslat_buton.setVisibility(View.VISIBLE);
            rdySinif.setVisibility(View.VISIBLE);


            baslat_buton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(sinif==0){

                                AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
                                gu.setIcon(R.drawable.ic_warning_black_24dp);
                                gu.setTitle("Sınıf Seçilmedi.");
                                gu.setMessage("Sınavı başlatmak için lütfen sınıf seçiniz.");


                                gu.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                               gu.create().show();

                    }else{

                    soru_no = soru_no + 1;
                    editor.putInt("soru_no", soru_no);

                    editor.commit();

                    rdySinif.setVisibility(View.INVISIBLE);

                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();


                }}
            });


            //////  eğer soru_no=-1 ise buraya kadar çalışsın


        }else {



            if(soru_no<sorusayisi) {

                soru_no = soru_no + 1;

            }

            soru_id=soru_no;
            if(sinif==1) {sorusayisi=30;
            editor.putInt("sorusayisi",30);
                soru_id=soru_no+35;}
            else if(sinif==3)soru_id=soru_no+65;


            editor.putInt("soru_no", soru_no);
            editor.commit();


            String urlcr = "https://app.1e1okul.com/bilsemdeneme/resim/cronometre.png";
            Picasso.with(this).load(urlcr).resize(genislik / 12, yukseklik / 2).centerInside().into(zaman_bar_zemin);


            String urlsn = "https://app.1e1okul.com/bilsemdeneme/resim/sorunobar.png";
            Picasso.with(this).load(urlsn).resize(genislik / 12, yukseklik / 2).centerInside().into(SoruNo_Zemin);
            card_soru.setVisibility(View.INVISIBLE);
            soru_no_txt.setTextSize(punto);
            soru_no_txt.setText(String.valueOf(soru_no));
            soru_no_kalan_txt.setTextSize(punto);
            soru_no_kalan_txt.setText(String.valueOf(sorusayisi - soru_no));

            float s_sayi = sorusayisi;

            View v = SoruNo_bar_dolan;
            v.setPivotY(1);
            v.setScaleY((soru_no) / s_sayi);



////////////////// soru ve şık resimleri alınıyor///////////////////////
            String url0 = "https://app.1e1okul.com/bilsemdeneme/resim/sinif"+sinif+"/s" + String.valueOf(soru_no) + "_sr.PNG";
            Picasso.with(this).load(url0).resize(genislik / 2, yukseklik / 2).centerInside().into(soru_resim);
            card_soru.setVisibility(View.INVISIBLE);


            String url1 = "https://app.1e1okul.com/bilsemdeneme/resim/sinif"+sinif+"/s" + String.valueOf(soru_no) + "_a.PNG";
            Picasso.with(this).load(url1).resize(genislik / 4, yukseklik / 4).centerInside().into(resim_a);
            card_a.setVisibility(View.INVISIBLE);


            String url2 = "https://app.1e1okul.com/bilsemdeneme/resim/sinif"+sinif+"/s" + String.valueOf(soru_no) + "_b.PNG";
            Picasso.with(this).load(url2).resize(genislik / 4, yukseklik / 4).centerInside().into(resim_b);
            card_b.setVisibility(View.INVISIBLE);

            String url3 = "https://app.1e1okul.com/bilsemdeneme/resim/sinif"+sinif+"/s" + String.valueOf(soru_no) + "_c.PNG";
            Picasso.with(this).load(url3).resize(genislik / 4, yukseklik / 4).centerInside().into(resim_c);
            card_c.setVisibility(View.INVISIBLE);

            String url4 = "https://app.1e1okul.com/bilsemdeneme/resim/sinif"+sinif+"/s" + String.valueOf(soru_no) + "_d.PNG";
            Picasso.with(this).load(url4).resize(genislik / 4, yukseklik / 4).centerInside().into(resim_d);
            card_d.setVisibility(View.INVISIBLE);

////////////////// soru ve şık resimleri alınıyor bitti ///////////////////////



            String url = "https://app.1e1okul.com/bilsemdeneme/soruWithId.php";
            StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("gelen soru response", response);


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray soru = jsonObject.getJSONArray("soru");
                        JSONObject f = soru.getJSONObject(0);

                        editor.putString("dogru_cevap", f.getString("dogru_cevap"));
                        editor.commit();
                        dogru_cevap = f.getString("dogru_cevap");
                        tur = f.getInt("tur");
                        ek_aciklama = f.getString("ek_aciklama");


                        if (aciklama.equals(f.getString("aciklama"))) {


                        } else {

                            editor.putInt("tur", f.getInt("tur"));
                            editor.putString("aciklama", f.getString("aciklama"));
                            editor.commit();

                          //  aciklama_txt.setTextSize(punto);

                            aciklama_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) Math.round(yukseklik/20));
                           // aciklama_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) punto);

                            aciklama_txt.setPadding(genislik/30,genislik/30,genislik/30 ,genislik/30);

                            aciklama_txt.setText(f.getString("aciklama"));
                            aciklama_txt.setVisibility(View.VISIBLE);
                            baloncuk_img.setVisibility(View.VISIBLE);

                        }


                        if(soru_no==32){

                            hikaye_scrool.setVisibility(View.VISIBLE);
                            hikaye_txt.setTextSize(punto);
                            hikaye_txt.setText(ek_aciklama);

                            editor.putString("aciklama", f.getString("aciklama"));
                            editor.commit();

                            aciklama_txt.setEllipsize(TextUtils.TruncateAt.MIDDLE);

                            //aciklama_txt.setTextSize(punto);
                            aciklama_txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) Math.round(yukseklik/20));
                            aciklama_txt.setPadding(genislik/30, genislik/30, genislik/30, genislik/30);
                            aciklama_txt.setText(f.getString("aciklama"));
                            aciklama_txt.setVisibility(View.VISIBLE);
                            baloncuk_img.setVisibility(View.VISIBLE);

                            zaman_bar_zemin.setVisibility(View.VISIBLE);
                            zaman_bar_dolan.setVisibility(View.VISIBLE);
                            zaman_bar_dolan.startAnimation(zaman60);

                        }
                       else {

                            if (tur==1) {

                                card_soru.startAnimation(iconacik);
                                card_soru.setVisibility(View.VISIBLE);

                                zaman_bar_zemin.setVisibility(View.VISIBLE);
                                zaman_bar_dolan.setVisibility(View.VISIBLE);
                                zaman_bar_dolan.startAnimation(zaman);



                            }else {


                                card_soru.setAnimation(iconacik);
                                card_soru.setVisibility(View.VISIBLE);


                                card_a.setAnimation(iconacik);
                                card_a.setVisibility(View.VISIBLE);

                                card_b.setAnimation(iconacik);
                                card_b.setVisibility(View.VISIBLE);


                                card_c.setAnimation(iconacik);
                                card_c.setVisibility(View.VISIBLE);


                                card_d.setAnimation(iconacik);
                                card_d.setVisibility(View.VISIBLE);

                                zaman_bar_zemin.setVisibility(View.VISIBLE);
                                zaman_bar_dolan.startAnimation(zaman30);


                            } }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();

                    params.put("soru_no", String.valueOf(soru_id));

                    return params;
                }

            };

            Volley.newRequestQueue(this).add(postStringRequest);


        }//////  eğer soru_no=!-1 ise buraya kadar çalışsın


        card_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verilen_cevap = "A";

              card_a.startAnimation(iconkapali);
              card_a.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.startAnimation(iconkapali);
                zaman_bar_zemin.startAnimation(iconkapali);
                zaman_bar_zemin.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.setVisibility(View.INVISIBLE);
              baloncuk_img.setVisibility(View.INVISIBLE);
              aciklama_txt.setVisibility(View.INVISIBLE);



              card_b.startAnimation(ileridon);
              card_c.startAnimation(ileridon);
              card_d.startAnimation(ileridon);
              card_soru.startAnimation(ileridon);

            }
        });

        card_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verilen_cevap = "B";

                card_b.startAnimation(iconkapali);
                card_b.setVisibility(View.INVISIBLE);

                zaman_bar_dolan.startAnimation(iconkapali);
                zaman_bar_zemin.startAnimation(iconkapali);
                zaman_bar_zemin.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.setVisibility(View.INVISIBLE);
                baloncuk_img.setVisibility(View.INVISIBLE);
                aciklama_txt.setVisibility(View.INVISIBLE);

                card_a.startAnimation(ileridon);
                card_c.startAnimation(ileridon);
                card_d.startAnimation(ileridon);
                card_soru.startAnimation(ileridon);


            }
        });


        card_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verilen_cevap = "C";

                card_c.startAnimation(iconkapali);
                card_c.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.startAnimation(iconkapali);
                zaman_bar_zemin.startAnimation(iconkapali);
                zaman_bar_zemin.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.setVisibility(View.INVISIBLE);
                baloncuk_img.setVisibility(View.INVISIBLE);
                aciklama_txt.setVisibility(View.INVISIBLE);

                card_a.startAnimation(ileridon);
                card_b.startAnimation(ileridon);
                card_d.startAnimation(ileridon);
                card_soru.startAnimation(ileridon);



            }
        });

        card_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verilen_cevap = "D";

                card_d.startAnimation(iconkapali);
                card_d.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.startAnimation(iconkapali);
                zaman_bar_zemin.startAnimation(iconkapali);
                zaman_bar_zemin.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.setVisibility(View.INVISIBLE);
                baloncuk_img.setVisibility(View.INVISIBLE);
                aciklama_txt.setVisibility(View.INVISIBLE);


                card_a.startAnimation(ileridon);
                card_b.startAnimation(ileridon);
                card_c.startAnimation(ileridon);
                card_soru.startAnimation(ileridon);


            }

        });

        checkBox_mail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox_mail.isChecked()){

                    email_txt.startAnimation(paylasacik);
                    email_txt.setVisibility(View.VISIBLE);
                }else {
                    email_txt.startAnimation(paylaskapali);
                    email_txt.setVisibility(View.INVISIBLE);
                }


            }
        });


        zaman.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                String url0 = "https://app.1e1okul.com/bilsemdeneme/resim/soruisareti.PNG";
                Picasso.with(getApplicationContext()).load(url0).resize(genislik/2,yukseklik/2).centerInside().into(soru_resim);
                soru_resim.startAnimation(iconacik);

                //card_soru.setVisibility(View.INVISIBLE);
                baloncuk_img.setVisibility(View.INVISIBLE);
                aciklama_txt.setVisibility(View.INVISIBLE);

                card_a.startAnimation(iconacikgecikmeli);
                card_c.startAnimation(iconacikgecikmeli);
                card_d.startAnimation(iconacikgecikmeli);
                card_b.startAnimation(iconacikgecikmeli);

                card_a.setVisibility(View.VISIBLE);
                card_c.setVisibility(View.VISIBLE);
                card_d.setVisibility(View.VISIBLE);
                card_b.setVisibility(View.VISIBLE);


                card_a.setClickable(true);
                card_b.setClickable(true);
                card_c.setClickable(true);
                card_d.setClickable(true);

                zaman_bar_dolan.startAnimation(zaman30);



            }
        });


        zaman60.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                //soru_resim.startAnimation(iconacik);


                baloncuk_img.setVisibility(View.INVISIBLE);
                aciklama_txt.setVisibility(View.INVISIBLE);
                hikaye_scrool.setVisibility(View.INVISIBLE);
                card_soru.setVisibility(View.VISIBLE);
                card_soru.startAnimation(iconacik);


                card_a.startAnimation(iconacikgecikmeli);
                card_c.startAnimation(iconacikgecikmeli);
                card_d.startAnimation(iconacikgecikmeli);
                card_b.startAnimation(iconacikgecikmeli);

                card_a.setVisibility(View.VISIBLE);
                card_c.setVisibility(View.VISIBLE);
                card_d.setVisibility(View.VISIBLE);
                card_b.setVisibility(View.VISIBLE);


                card_a.setClickable(true);
                card_b.setClickable(true);
                card_c.setClickable(true);
                card_d.setClickable(true);

                zaman_bar_dolan.startAnimation(zaman30);



            }
        });


        ileridon.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {



                if(verilen_cevap.equals(sp.getString("dogru_cevap","E"))) {

                    new SoruDao().CozulmeArttir(MainActivity.this,soru_id);

                    Log.e("Doğru cevap verildi","Soru türü "+tur);

                    editor.putInt("dogru_sayisi", dogru_sayisi + 1);
                    editor.commit();

                    if(tur==1){
                        tur1=tur1+1;
                        editor.putInt("tur1",tur1);
                        editor.commit();

                    }
                    if(tur==2){
                        tur2=tur2+1;
                        editor.putInt("tur2",tur2);
                        editor.commit();

                    }
                    if(tur==3){
                        tur3=tur3+1;
                        editor.putInt("tur3",tur3);
                        editor.commit();

                    }
                    if(tur==4){
                        tur4=tur4+1;
                        editor.putInt("tur4",tur4);
                        editor.commit();

                    }
                    if(tur==5){
                        tur5=tur5+1;
                        editor.putInt("tur5",tur5);
                        editor.commit();

                    }
                    if(tur==6){
                        tur6=tur6+1;
                        editor.putInt("tur6",tur6);
                        editor.commit();

                    }
                    if(tur==7){
                        tur7=tur7+1;
                        editor.putInt("tur7",tur7);
                        editor.commit();

                    }
                    if(tur==8){
                        tur8=tur8+1;
                        editor.putInt("tur8",tur8);
                        editor.commit();

                    }


                    Log.e("Bravvoo", "Doğru cevap tıklandı : ");

                }else {  Log.e("Maalesef", "Yanlış cevap tıklandı : ");}


                Log.e("Sonraki tıklandı", "Doğru cevap sayısı : "+dogru_sayisi);




                if(soru_no==sorusayisi){

                    baloncuk_img.setVisibility(View.INVISIBLE);
                    aciklama_txt.setVisibility(View.INVISIBLE);
                    tesekkur.setVisibility(View.VISIBLE);
                    soru_no_kalan_txt.setVisibility(View.INVISIBLE);
                    soru_no_txt.setVisibility(View.INVISIBLE);
                    SoruNo_bar_dolan.setVisibility(View.INVISIBLE);
                    SoruNo_Zemin.setVisibility(View.INVISIBLE);
                    zaman_bar_zemin.setVisibility(View.INVISIBLE);

                    ad_txt.setVisibility(View.VISIBLE);
                    soyad_txt.setVisibility(View.VISIBLE);
                    karne_buton.setVisibility(View.VISIBLE);
                    checkBox_mail.setVisibility(View.VISIBLE);

                    if(checkBox_mail.isChecked()){

                        email_txt.startAnimation(paylasacik);
                        email_txt.setVisibility(View.VISIBLE);

                    }


                    soru_area.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorseffaf));


                    karne_buton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final int ds = sp.getInt("dogru_sayisi",0);
                            final String ad=ad_txt.getText().toString();
                            final String soyad=soyad_txt.getText().toString();
                            final String email=email_txt.getText().toString();



                            if(ad!=null){
                                editor.putString("ad", ad);
                                editor.commit();
                            }

                            if(soyad!=null) {
                                editor.putString("soyad", soyad);
                                editor.commit();
                            }


                                String url = "https://app.1e1okul.com/bilsemdeneme/Sira.php";
                                StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {

                                        Log.e(" şimdi girdik onrespons"," bakalım ne olacak");


                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            JSONArray bilsem_ogrenci = jsonObject.getJSONArray("bilsem_ogrenci");
                                                JSONObject f = bilsem_ogrenci.getJSONObject(0);

                                            Log.e("girdik tray içine"," bakalım ne olacak");

                                                int sayi = f.getInt("sayi");
                                                int sira = f.getInt("sira");

                                            if(checkBox_mail.isChecked()){

                                          new SoruDao().mailGonder(MainActivity.this,sinif,ad,soyad,email,sayi,sira,ds,tur1,tur2,tur3,tur4,tur5,tur6,tur7,tur8);

                                            }


                                            editor.putInt("sayi",sayi);
                                                editor.putInt("sira",sira);
                                                editor.commit();

                                            startActivity(new Intent(MainActivity.this,Sonuc.class));
                                            finish();



                                        } catch (JSONException e) {
                                            Log.e("girdik catch içine"," bakalım ne olacak");
                                            e.printStackTrace();
                                        }



                                    }



                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> params = new HashMap<>();
                                        params.put("ad",ad);
                                        params.put("soyad",soyad);
                                        params.put("sinif",String.valueOf(sinif));
                                        params.put("email",email);
                                        params.put("dogru_sayisi",String.valueOf(ds));
                                        params.put("tur1",String.valueOf(tur1));
                                        params.put("tur2",String.valueOf(tur2));
                                        params.put("tur3",String.valueOf(tur3));
                                        params.put("tur4",String.valueOf(tur4));
                                        params.put("tur5",String.valueOf(tur5));
                                        params.put("tur6",String.valueOf(tur6));
                                        params.put("tur7",String.valueOf(tur7));

                                        return params;
                                    }

                                };


                                Volley.newRequestQueue(MainActivity.this).add(postStringRequest);


                            progress_bekle.setVisibility(View.VISIBLE);


                        }
                    });


                }else {

                    String url4 = "https://app.1e1okul.com/bilsemdeneme/resim/sonraki.png";
                    Picasso.with(getApplication()).load(url4).resize(genislik / 3, yukseklik / 5).centerInside().into(sonraki);



                    sonraki.setAnimation(iconacik);

                    sonraki.setVisibility(View.VISIBLE);

                }


                card_soru.setVisibility(View.INVISIBLE);
                card_a.setClickable(false);
                card_b.setClickable(false);
                card_c.setClickable(false);
                card_d.setClickable(false);



            }
        });



        zaman30.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub


                verilen_cevap = "E";

                card_a.startAnimation(ileridon);
                card_b.startAnimation(ileridon);
                card_c.startAnimation(ileridon);
                card_d.startAnimation(ileridon);
                card_soru.startAnimation(ileridon);
                zaman_bar_zemin.setVisibility(View.INVISIBLE);
                zaman_bar_dolan.setVisibility(View.INVISIBLE);


            }
        });



        sonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();

            }
        });



        tesekkur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
                gu.setIcon(R.drawable.ic_warning_black_24dp);
                gu.setTitle("Çıkış için \"Evet\" e basın.");
                gu.setMessage("Başarılar Dileriz");


                gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        editor.remove("sinif");
                        editor.remove("dogru_sayisi");
                        editor.remove("soru_no");
                        editor.remove("sorusayisi");
                        editor.remove("tur");
                        editor.remove("tur1");
                        editor.remove("tur2");
                        editor.remove("tur3");
                        editor.remove("tur4");
                        editor.remove("tur5");
                        editor.remove("tur6");
                        editor.remove("tur7");
                        editor.remove("tur8");

                        editor.remove("aciklama");
                        editor.commit();



                        finish();

                    }
                });

                gu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });

                gu.create().show();

            }
        });


        rdySinif.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb1Sinif:
                        Log.e("Main Radio Buton","1. sınıf seçildi");
                        sinif=1;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                    case R.id.rb2Sinif:
                        Log.e("Main Radio Buton","2. sınıf seçildi");
                        sinif=2;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                    case R.id.rb3Sinif:
                        Log.e("Main Radio Buton","3. sınıf seçildi");
                        sinif=3;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                }
            }
        });





    }


    @Override
    public void onBackPressed() {
        // geri tuşuna dokunulduğunda
        AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
        gu.setIcon(R.drawable.ic_warning_black_24dp);
        gu.setTitle("Çıkış için \"Evet\" e basın.");
        gu.setMessage("Başarılar Dileriz");


        gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editor.remove("sinif");
                editor.remove("dogru_sayisi");
                editor.remove("soru_no");
                editor.remove("sorusayisi");
                editor.remove("tur");
                editor.remove("tur1");
                editor.remove("tur2");
                editor.remove("tur3");
                editor.remove("tur4");
                editor.remove("tur5");
                editor.remove("tur6");
                editor.remove("tur7");
                editor.remove("tur8");

                editor.remove("aciklama");
                editor.remove("SinavBilgisi");
                editor.commit();



                finish();

            }
        });


        gu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        gu.create().show();

        // super.onBackPressed();
    }






}





