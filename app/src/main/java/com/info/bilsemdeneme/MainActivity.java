package com.info.bilsemdeneme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener{


    private static final int PERMISSION_REQUEST_CODE = 2296;
    private ImageView soru_resim, resim_a,resim_b,resim_c,resim_d,sonraki,sonraki2,baloncuk_img;
    private ImageView zaman_bar_zemin,zaman_bar_dolan,SoruNo_bar_dolan,SoruNo_Zemin;
    private ImageView baslat_buton;
    private TextView tesekkur,aciklama_txt,soru_no_txt,soru_no_kalan_txt,sinav_aciklama_txt,baslik1_txt,baslik2_txt,hikaye_txt;
    private EditText ad_txt,soyad_txt,email_txt;
    private Button karne_buton;
    private String soru_resmi,verilen_cevap="E",dogru_cevap="E",aciklama,ek_aciklama,konum;
    private int genislik,yukseklik,soru_no=0,tur=0,dogru_sayisi=0,sorusayisi, soru_id;
    private CardView card_soru,card_a,card_b,card_c, card_d;
    private Animation geridon,ileridon,iconacik,iconkapali,zaman,zaman30,zaman60,iconacikgecikmeli,paylasacik,paylaskapali;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ConstraintLayout soru_area;
    private ScrollView scrool,hikaye_scrool;
    private int tur1=0,tur2=0,tur3=0,tur4=0,tur5=0,tur6=0,tur7=0,tur8=0,zorluk=2,sinif,version;
    private int odeme1=0, odeme2=0, odeme3=0;
    private ProgressBar progress_bekle;
    private CheckBox checkBox_mail;
    private ImageView fon_img,bt_1sinif, bt_2sinif, bt_3sinif, bt_1sinifFree, bt_2sinifFree, bt_3sinifFree;
    private RadioButton rb1Sinif, rb2Sinif, rb3Sinif, rb1SinifFree, rb2SinifFree, rb3SinifFree;
    private Group grp_sinif_select;
    private RadioGroup rdySinif;
    private BillingClient mBillingClient;
    private List<SkuDetails> skuINAPPDetayListesi = new ArrayList<>();
    private List<SkuDetails> skuSUBSDetayListesi = new ArrayList<>();



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
        sonraki2=findViewById(R.id.sonraki2);
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
        grp_sinif_select = findViewById(R.id.grp_sinif_select);
        rdySinif = findViewById(R.id.rdySinif);
        rb1Sinif = findViewById(R.id.rb1Sinif);
        rb2Sinif = findViewById(R.id.rb2Sinif);
        rb3Sinif = findViewById(R.id.rb3Sinif);
        bt_1sinif=findViewById(R.id.bt_1sinif);
        bt_2sinif=findViewById(R.id.bt_2sinif);
        bt_3sinif=findViewById(R.id.bt_3sinif);
        bt_1sinifFree=findViewById(R.id.bt_1sinifFree);
        bt_2sinifFree=findViewById(R.id.bt_2sinifFree);
        bt_3sinifFree=findViewById(R.id.bt_3sinifFree);

        rb1SinifFree = findViewById(R.id.rb1SinifFree);
        rb2SinifFree = findViewById(R.id.rb2SinifFree);
        rb3SinifFree = findViewById(R.id.rb3SinifFree);




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
        odeme1 = sp.getInt("odeme1",0);
        odeme2 = sp.getInt("odeme2",0);
        odeme3 = sp.getInt("odeme3",0);
        konum = sp.getString("konum","giris");


        forceUpdate(version);

        if(konum.equals("sonuc")){  startActivity(new Intent(MainActivity.this,Sonuc.class));
            finish();}

        if (soru_no>=sorusayisi) karneBilgleriGir();


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



        if(!isNetworkConnected()){
            AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
            gu.setIcon(R.drawable.ic_wifi_off_red_24dp);

            gu.setTitle("Bağlantı Hatası!");
            gu.setMessage("Uygulamayı kullanabilmeniz için internete bağlı olmanız gerekir. \n\nLütfen bağlantınızı kontrol ediniz!!");

            gu.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();

                }
            });

            gu.create().show();


        }

        mBillingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {


                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    buttonlarinDurumuDegistir(true);

                    List<String> skuListINAPP = new ArrayList<>();

                    Log.e("MainAct. BillingResp","Ödeme sisteminde sorun yok");

                    skuListINAPP.add("sinif1deneme1");
                    skuListINAPP.add("sinif2deneme1");
                    skuListINAPP.add("sinif3deneme1a");


                    SkuDetailsParams.Builder paramsINAPP = SkuDetailsParams.newBuilder();

                    paramsINAPP.setSkusList(skuListINAPP).setType(BillingClient.SkuType.INAPP);

                    mBillingClient.querySkuDetailsAsync(paramsINAPP.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {

                            skuINAPPDetayListesi = list;

                        }
                    });


                    List<String> skuListSUBS = new ArrayList<>();

                    skuListSUBS.add("sinif3deneme1");

                    SkuDetailsParams.Builder paramsSUBS = SkuDetailsParams.newBuilder();

                    paramsSUBS.setSkusList(skuListSUBS).setType(BillingClient.SkuType.SUBS);

                    mBillingClient.querySkuDetailsAsync(paramsSUBS.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {

                            skuSUBSDetayListesi = list;

                        }
                    });

                   // odemeControl();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Ödeme sistemi için google play hesabını kontrol ediniz", Toast.LENGTH_SHORT).show();
                    Log.e("MainAct. BillingResp","Ödeme sisteminde bir sorun olabilir google play hesabını kontrol ediniz");
                  // nopay();
                    buttonlarinDurumuDegistir(false);
                }

            }

            @Override
            public void onBillingServiceDisconnected() {

                Toast.makeText(getApplicationContext(), "Ödeme sistemi şuanda geçerli değil", Toast.LENGTH_SHORT).show();
               Log.e("MainAct. BillingResp","Ödeme sistemi şuanda geçerli değil");
              //nopay();
                buttonlarinDurumuDegistir(false);

                 }



        });

       // *** if(odeme1==0 || odeme2 == 0 || odeme3 ==0 ) odemeControl();





        if(soru_no==-1) {    //////  eğer soru_no=-1 ise bura  çalışsın


            soru_no_kalan_txt.setVisibility(View.INVISIBLE);
            soru_no_txt.setVisibility(View.INVISIBLE);
            soru_area.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorseffaf));



            baslik1_txt.setTextSize(3 * punto);
            baslik2_txt.setTextSize((5 * punto) / 4);
            sinav_aciklama_txt.setTextSize(punto);


            baslik1_txt.setVisibility(View.VISIBLE);
            baslik2_txt.setVisibility(View.VISIBLE);
            sinav_aciklama_txt.setVisibility(View.VISIBLE);
            scrool.setVisibility(View.VISIBLE);


            String urlbslt = "https://app.1e1okul.com/bilsemdeneme/resim/baslat_buton_g.png";
            String url1sinifred = "https://app.1e1okul.com/bilsemdeneme/resim/1sinifred.png";
            String url1sinifgreen = "https://app.1e1okul.com/bilsemdeneme/resim/1sinifgreen.png";
            String url2sinifred = "https://app.1e1okul.com/bilsemdeneme/resim/2sinifred.png";
            String url2sinifgreen = "https://app.1e1okul.com/bilsemdeneme/resim/2sinifgreen.png";
            String url3sinifred = "https://app.1e1okul.com/bilsemdeneme/resim/3sinifred.png";
            String url3sinifgreen = "https://app.1e1okul.com/bilsemdeneme/resim/3sinifgreen.png";
            String url1sinifopen = "https://app.1e1okul.com/bilsemdeneme/resim/1sinifopen.png";
            String url2sinifopen = "https://app.1e1okul.com/bilsemdeneme/resim/2sinifopen.png";
            String url3sinifopen = "https://app.1e1okul.com/bilsemdeneme/resim/3sinifopen.png";


            //Picasso.with(this).load(urlbslt).resize(genislik / , genislik / 5).centerInside().into(baslat_buton);


            Picasso.with(this).load(url1sinifgreen).resize(genislik / 8, genislik / 8).centerInside().into(bt_1sinifFree);
            Picasso.with(this).load(url2sinifgreen).resize(genislik / 8, genislik / 8).centerInside().into(bt_2sinifFree);
            Picasso.with(this).load(url3sinifgreen).resize(genislik / 8, genislik / 8).centerInside().into(bt_3sinifFree);


            if(odeme1==0) Picasso.with(this).load(url1sinifred).resize(genislik / 8, genislik / 8).centerInside().into(bt_1sinif);
                else Picasso.with(this).load(url1sinifopen).resize(genislik / 8, genislik / 8).centerInside().into(bt_1sinif);
            if(odeme2==0) Picasso.with(this).load(url2sinifred).resize(genislik / 8, genislik / 8).centerInside().into(bt_2sinif);
                else Picasso.with(this).load(url2sinifopen).resize(genislik / 8, genislik / 8).centerInside().into(bt_2sinif);
            if(odeme3==0) Picasso.with(this).load(url3sinifred).resize(genislik / 8, genislik / 8).centerInside().into(bt_3sinif);
                else Picasso.with(this).load(url3sinifopen).resize(genislik / 8, genislik / 8).centerInside().into(bt_3sinif);



            //bt_1sinif.setOnClickListener(this);
            //bt_2sinif.setOnClickListener(this);
            //bt_3sinif.setOnClickListener(this);



            baslat_buton.setVisibility(View.INVISIBLE);
            rdySinif.setVisibility(View.INVISIBLE);
            grp_sinif_select.setVisibility(View.VISIBLE);

            bt_1sinifFree.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                        sinif=1;
                        editor.putInt("sinif", sinif);
                        soru_no = soru_no + 1;
                        editor.putInt("soru_no", soru_no);
                        editor.commit();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        finish();

                }
            });

            bt_2sinifFree.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    sinif=2;
                    editor.putInt("sinif", sinif);
                    soru_no = soru_no + 1;
                    editor.putInt("soru_no", soru_no);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();

                }
            });

            bt_3sinifFree.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    // test için ödeme durumları sıfırlanır

/*
                   odeme3=1;

                    if(odeme3==1){

                        odeme1=0;
                        odeme2=0;
                        odeme3=0;

                        editor.putInt("odeme1",0);
                        editor.putInt("odeme2",0);
                        editor.putInt("odeme3",0);
                        editor.commit();
                         }  */

                    // test için ödeme durumları sıfırlanır//  else{

                    sinif=3;
                    editor.putInt("sinif", sinif);
                    soru_no = soru_no + 1;
                    editor.putInt("soru_no", soru_no);
                    editor.commit();

                        // test için ödeme durumları sıfırlanır// }

                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            });



            bt_1sinif.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(odeme1==0){

                        AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
                        gu.setIcon(R.drawable.ic_tr_icon);
                        gu.setTitle("Ücretli Sınav!");
                        gu.setMessage("1. Sınıf Bilsem Deneme Sınavı için ödeme yapmak üzeresiniz.\nÜcretli deneme sınavlarımız her çözüm için ücretlendirilmektedir. Ancak sistem izin verdiği durumlarda yeni bir ödeme yapmadan defalarca da kullanabilirsiniz. \n\nOnaylıyor musunuz?");


                        gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuINAPPDetayListesi.get(0))
                                        .build();

                                mBillingClient.launchBillingFlow(MainActivity.this, flowParams);

                            }
                        });


                        gu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }

                        });

                        gu.create().show();


                       }

                        else{
                            sinif = 11;
                            editor.putInt("sinif", sinif);
                            soru_no = soru_no + 1;
                            editor.putInt("soru_no", soru_no);
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();

                        }

                }
            });

            bt_2sinif.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (odeme2==0) {


                        AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
                        gu.setIcon(R.drawable.ic_tr_icon);
                        gu.setTitle("Ücretli Sınav!");
                        gu.setMessage("2. Sınıf Bilsem Deneme Sınavı için ödeme yapmak üzeresiniz.\nÜcretli deneme sınavlarımız her çözüm için ücretlendirilmektedir. Ancak sistem izin verdiği durumlarda yeni bir ödeme yapmadan defalarca da kullanabilirsiniz. \n\nOnaylıyor musunuz?");


                        gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuINAPPDetayListesi.get(1))
                                        .build();

                                mBillingClient.launchBillingFlow(MainActivity.this, flowParams);

                            }
                        });


                        gu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }

                        });

                        gu.create().show();

                    }
                    else{


                        sinif=21;
                        editor.putInt("sinif", sinif);
                        soru_no = soru_no + 1;
                        editor.putInt("soru_no", soru_no);
                        editor.commit();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        finish();

                    }

                }
            });


            bt_3sinif.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (odeme3==0) {

                        AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
                        gu.setIcon(R.drawable.ic_tr_icon);
                        gu.setTitle("Ücretli Sınav!");
                        gu.setMessage("3. Sınıf Bilsem Deneme Sınavı için ödeme yapmak üzeresiniz.\nÜcretli deneme sınavlarımız her çözüm için ücretlendirilmektedir. Ancak sistem izin verdiği durumlarda yeni bir ödeme yapmadan defalarca da kullanabilirsiniz. \n\nOnaylıyor musunuz?");


                        gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuINAPPDetayListesi.get(2))
                                        .build();

                                mBillingClient.launchBillingFlow(MainActivity.this, flowParams);

                            }
                        });


                        gu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }

                        });

                        gu.create().show();

                    }
                    else{

                        sinif=31;
                        editor.putInt("sinif", sinif);
                        soru_no = soru_no + 1;
                        editor.putInt("soru_no", soru_no);

                        editor.commit();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        finish();

                    }

                }
            });




            //////  eğer soru_no=-1 ise buraya kadar çalışsın


                /// test için  soru_no>=sorusayisi   --->   soru_no>=4
        }else if(soru_no>=sorusayisi){

            grp_sinif_select.setVisibility(View.INVISIBLE);
            karneBilgleriGir();

        }else{

            grp_sinif_select.setVisibility(View.INVISIBLE);


            if(soru_no<sorusayisi) {

                soru_no = soru_no + 1;}


            soru_id=soru_no;
            if(sinif==1) {sorusayisi=30;
            editor.putInt("sorusayisi",30);
                soru_id=soru_no+35;}
           else if(sinif==11) {sorusayisi=30;
                editor.putInt("sorusayisi",30);
                soru_id=soru_no+104;}
            else if(sinif==3)soru_id=soru_no+65;
            else if(sinif==21)soru_id=soru_no+134;
            else if(sinif==31)soru_id=soru_no+169;

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


        card_a.setOnClickListener(new OnClickListener() {
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

        card_b.setOnClickListener(new OnClickListener() {
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


        card_c.setOnClickListener(new OnClickListener() {
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

        card_d.setOnClickListener(new OnClickListener() {
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




                if(soru_no>=sorusayisi){

                    karneBilgleriGir();

                }else {

                    String url4 = "https://app.1e1okul.com/bilsemdeneme/resim/sonraki.png";
                    Picasso.with(getApplication()).load(url4).resize(genislik / 3, yukseklik / 5).centerInside().into(sonraki);
                  //  Picasso.with(getApplication()).load(url4).resize(genislik / 3, yukseklik / 5).centerInside().into(sonraki2);

                    sonraki.setAnimation(iconacik);
                    //sonraki2.setAnimation(iconacik);

                    sonraki.setVisibility(View.VISIBLE);
                    //sonraki2.setVisibility(View.VISIBLE);

                }



///// gizliyorum başka yere taşıyacağım sorun olmazsa sileceğim
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



        sonraki.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();

            }
        });

        sonraki2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();

            }
        });



        tesekkur.setOnClickListener(new OnClickListener() {
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
                        Log.e("Main Radio Buton","11. sınıf seçildi");
                        sinif=11;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                    case R.id.rb2Sinif:
                        Log.e("Main Radio Buton","21. sınıf seçildi");
                        sinif=21;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                    case R.id.rb3Sinif:
                        Log.e("Main Radio Buton","31. sınıf seçildi");
                        sinif=31;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;

                    case R.id.rb1SinifFree:
                        Log.e("Main Radio Buton","1. sınıf seçildi");
                        sinif=1;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                    case R.id.rb2SinifFree:
                        Log.e("Main Radio Buton","2. sınıf seçildi");
                        sinif=2;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                    case R.id.rb3SinifFree:
                        Log.e("Main Radio Buton","3. sınıf seçildi");
                        sinif=3;
                        editor.putInt("sinif", sinif);
                        editor.commit();
                        break;
                }
            }
        });


    }

    private void odemeControl() {

         mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, new PurchaseHistoryResponseListener() {
            @Override
            public void onPurchaseHistoryResponse( BillingResult billingResult,  List<PurchaseHistoryRecord> list) {

                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){

                    int n =1;
                    String urun1= "[sinif1deneme1]";
                    String urun2= "[sinif2deneme1]";
                    String urun3= "[sinif3deneme1a]";
                    String urunMevcut;


                    for ( final PurchaseHistoryRecord purchase : list){
                        ++n;
                        urunMevcut = purchase.getSkus().toString();

                        Log.e("urunMevcut",urunMevcut.trim().toString());

                        if (urunMevcut.trim().toString().equals(urun1)) {
                            odeme1=1;
                            editor.putInt("odeme1", 1);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"111", Toast.LENGTH_SHORT).show();
                            Log.e("odeme control","1111");
                        }

                        if (urunMevcut.trim().toString().equals(urun2.toString())) {
                            odeme2=1;
                            editor.putInt("odeme2", 1);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"22222222", Toast.LENGTH_SHORT).show();

                            Log.e("odeme control","22222222");

                        }

                        if (urunMevcut.trim().toString().equals(urun3)) {
                            odeme3=1;
                            editor.putInt("odeme3", 1);
                            editor.commit();

                            Toast.makeText(getApplicationContext(),"3333", Toast.LENGTH_SHORT).show();

                            Log.e("odeme control","3333");
                        }



                    }





                }

            }
        });


    }

    private void payvarning() {


    }


    @Override
    public void onBackPressed() {
        // geri tuşuna dokunulduğunda
        AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
        gu.setIcon(R.drawable.ic_warning_black_24dp);


        if(soru_no>-1)  {
            gu.setTitle("Sınavı Sonlandır!");
            gu.setMessage("Sınavı yarıda bitirip başa dönmek için\"EVET\"'e basınız. Bu sınavla ilgili verileriniz kaybolacak!!");

        }else {
            gu.setTitle("Çıkış için \"Evet\" e basın.");
            gu.setMessage("Başarılar Dileriz...");
        }


        gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editor.remove("sinif");
                editor.remove("dogru_sayisi");
                editor.remove("sorusayisi");
                editor.remove("soru_no");
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

                if(soru_no>-1) startActivity(new Intent(MainActivity.this,MainActivity.class));

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
    private void buttonlarinDurumuDegistir(boolean durum){

       // rb1Sinif.setSelected(durum);
       // rb2Sinif.setSelected(durum);
       // rb3Sinif.setSelected(durum);


        bt_1sinif.setEnabled(durum);
        bt_2sinif.setEnabled(durum);
        bt_3sinif.setEnabled(durum);

           }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {

            for (Purchase purchase : list) {

                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
                }

                if (String.valueOf(purchase.getSkus()).equals("[sinif1deneme1]")) {
                    odeme1=1;
                    editor.putInt("odeme1", 1);
                    editor.commit();
                    String url1sinifopen = "https://app.1e1okul.com/bilsemdeneme/resim/1sinifopen.png";
                  if(bt_1sinif.getVisibility()==View.VISIBLE)  Picasso.with(this).load(url1sinifopen).resize(genislik / 8, genislik / 8).centerInside().into(bt_1sinif);

                   // startActivity(new Intent(MainActivity.this,MainActivity.class));
                   // finish();
                   Toast.makeText(getApplicationContext(), "1.Sınıf Deneme Satın Alındı", Toast.LENGTH_SHORT).show();

                }

                if (String.valueOf(purchase.getSkus()).equals("[sinif2deneme1]")) {
                    odeme2=1;
                    editor.putInt("odeme2", 1);
                    editor.commit();
                    String url2sinifopen = "https://app.1e1okul.com/bilsemdeneme/resim/2sinifopen.png";
                    if(bt_1sinif.getVisibility()==View.VISIBLE)  Picasso.with(this).load(url2sinifopen).resize(genislik / 8, genislik / 8).centerInside().into(bt_2sinif);

                   Toast.makeText(getApplicationContext(), "2.Sınıf Deneme Satın Alındı", Toast.LENGTH_SHORT).show();



                    // startActivity(new Intent(MainActivity.this,MainActivity.class));
                  //  finish();

                }

                if (String.valueOf(purchase.getSkus()).equals("[sinif3deneme1a]")) {
                   odeme3=1;
                   editor.putInt("odeme3", 1);
                    editor.commit();
                    String url3sinifopen = "https://app.1e1okul.com/bilsemdeneme/resim/3sinifopen.png";
                    if(bt_1sinif.getVisibility()==View.VISIBLE)  Picasso.with(this).load(url3sinifopen).resize(genislik / 8, genislik / 8).centerInside().into(bt_3sinif);
                   Toast.makeText(getApplicationContext(), "3.Sınıf Deneme Satın Alındı", Toast.LENGTH_SHORT).show();

                    // startActivity(new Intent(MainActivity.this,MainActivity.class));
                   // finish();

                }

            }

/*
            AlertDialog.Builder gu = new AlertDialog.Builder(MainActivity.this);
            gu.setIcon(R.drawable.ic_yes_icon);
            gu.setTitle("Tebrikler");
            gu.setMessage("Yeni bir deneme sınavı satın aldınız. Dilerseniz hemen başlamak için giriş sayfasına dönebilirsiniz. ");

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

                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();

                }
            });


            gu.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }

            });


            gu.create().show();   */

            //Toast.makeText(getApplicationContext(), purchase.getSkus() + ": Ürün satın alındı.", Toast.LENGTH_SHORT).show();

        }




        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {

            Toast.makeText(getApplicationContext(), "Ödeme iptal edildi", Toast.LENGTH_SHORT).show();
            // nopay();

        }


    }




/*
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_1sinif:

                Log.e("bt_1sinif","bana dokunuldu");
                // do your code
                break;
            case R.id.bt_2sinif:
                Log.e("bt_2sinif","bana dokunuldu");
                break;
            case R.id.bt_3sinif:
                // do your code
                break;
            case R.id.bt_1sinifFree:
                // do your code
                break;
            case R.id.bt_2sinifFree:
                // do your code
                break;
            case R.id.bt_3sinifFree:
                // do your code
                break;
            default:
                break;
        }*/



    public void nopay(){
        sinif = 0;
        editor.putInt("sinif", sinif);
        editor.commit();
        rb1Sinif.setChecked(false);
        rb2Sinif.setChecked(false);
        rb3Sinif.setChecked(false);
    }

    public void forceUpdate(int version){

        final int prod_version = 20;

        if(prod_version != version){

            String url = "https://app.1e1okul.com/bilsemdeneme/get_version.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray kategoriArray = jsonObject.getJSONArray("version");
                        Log.e("KatDao.GetirWeb","kategoriler alındı mı?");

                        for(int i=0;i<kategoriArray.length();i++){
                            JSONObject k = kategoriArray.getJSONObject(i);

                            int new_version = k.getInt("version");
                            if (prod_version < new_version){  startActivity(new Intent(MainActivity.this,Force.class));
                                finish();
                            } else {
                                editor.putInt("version", new_version);
                                editor.commit();
                            }

                            Log.e("gelen version"," "+ new_version);

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            Volley.newRequestQueue(this).add(stringRequest);

        }




    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void karneBilgleriGir() {


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


        if (ContextCompat.checkSelfPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{WRITE_EXTERNAL_STORAGE},
                    1);
        }


        /*    //// klasör oluşturma izin için

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/BilsemDeneme");

        Log.e("Main.KarneBilgilerigir", "Oluşan klasör yolu: " + myDir);
        myDir.mkdirs();

            //// klasör oluşturma izin için/**/

            if(checkBox_mail.isChecked()){

                email_txt.startAnimation(paylasacik);
                email_txt.setVisibility(View.VISIBLE);

            }


            soru_area.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorseffaf));


            karne_buton.setOnClickListener(new OnClickListener() {
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

    }



    private boolean checkPermission() {

        if (SDK_INT >= Build.VERSION_CODES.R) {

            return Environment.isExternalStorageManager();
        } else {

            int result = ContextCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1== PackageManager.PERMISSION_GRANTED;
        }

    }

    /*



    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        // perform action when allow permission success
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }   */



}





