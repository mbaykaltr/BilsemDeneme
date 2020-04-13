package com.info.bilsemdeneme;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class Sonuc extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private int tur1 = 0, tur2 = 0, tur3 = 0, tur4 = 0, tur5 = 0, tur6 = 0, tur7 = 0, tur8 = 0, sira = 0, dogru_sayisi, katilan, sinif, soruSayisi=35,barajNet;
    private int genislik, yukseklik;
    private ProgressBar tur1_bar, tur2_bar, tur3_bar, tur4_bar, tur5_bar, tur6_bar, tur7_bar, tur8_bar, puan_Bar, sira_bar, sonuc_net_bar;
    private TextView tur1_txt, tur2_txt, tur3_txt, tur4_txt, tur5_txt, tur6_txt, tur7_txt, tur8_txt;
    private TextView ogrenci_ad_txt, sonuc_sira_txt, sonuc_puan_txt, sonuc_baslik_txt, sonuc_ozet_txt, sonuc_net_txt, konular_baslik;
    private TextView ayarpaylas_txt, ayarpaylas_txt2, ayarpaylas_txt3, tvRota;
    private String ad, soyad;
    private ImageView paylas_mail_img, paylas_whatsapp_img, sertifica_img, paylas_icon, fon_img, imgRota, bilsem_1, bilsem_2, bilsem_3;
    private Animation paylasgeri, paylasileri, paylasacik, paylaskapali, hopla;
    private ConstraintLayout paylas_kutusu, sonuc_ekran, clRota, clYayin, clBasari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonuc);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        sp = getSharedPreferences("SinavBilgisi", MODE_PRIVATE);
        editor = sp.edit();

        katilan = sp.getInt("sayi", 3000);
        sira = sp.getInt("sira", 100);
        ad = sp.getString("ad", "Misafir");
        soyad = sp.getString("soyad", "Öğrenci");
        sinif = sp.getInt("sinif",0);

        if(sinif==1){
            soruSayisi =30;
            barajNet=18;
        }else if(sinif==2)barajNet=22;
        else barajNet=23;



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        genislik = size.x;
        yukseklik = size.y;

        double density = getResources().getDisplayMetrics().density;
        final int punto = (int) ((genislik + 1200) / (density * 70) + 2);
        final int bosluk = ((genislik - 200) / 40);


        paylas_mail_img = findViewById(R.id.mail_paylas_img);
        paylas_whatsapp_img = findViewById(R.id.whatsapp_paylas_img);
        sertifica_img = findViewById(R.id.sertifica_img);
        paylas_icon = findViewById(R.id.paylas_icon);
        paylas_kutusu = findViewById(R.id.paylas_kutusu);
        sonuc_ekran = findViewById(R.id.sonuc_ekran);
        fon_img = findViewById(R.id.sonuc_fon_img);
        imgRota = findViewById(R.id.imgRota);
        bilsem_1 = findViewById(R.id.imgBilsem1);
        bilsem_2 = findViewById(R.id.imgBilsem2);
        bilsem_3 = findViewById(R.id.imgBilsem3);
        paylasileri = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.paylasileri);
        paylasgeri = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.paylasgeri);
        paylasacik = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.paylasacik);
        paylaskapali = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.paylaskapali);
        hopla = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hopla);




        String urlfon = "https://app.1e1okul.com/bilsemdeneme/resim/fon_img.png";
        Picasso.with(this).load(urlfon).resize(genislik, yukseklik).resize(genislik, yukseklik).into(fon_img);

        String urlokul = "https://app.1e1okul.com/bilsemdeneme/resim/e1okul.png";
        Picasso.with(this).load(urlokul).resize(yukseklik / 4, yukseklik / 4).centerInside().into(sertifica_img);

        String urlrota = "https://app.1e1okul.com/bilsemdeneme/resim/rota.png";
        Picasso.with(this).load(urlrota).resize(yukseklik / 4, yukseklik / 4).centerInside().into(imgRota);
        imgRota.startAnimation(hopla);

        String urlmail = "https://app.1e1okul.com/bilsemdeneme/resim/sha.png";
        Picasso.with(this).load(urlmail).resize(yukseklik / 8, yukseklik / 8).centerInside().into(paylas_mail_img);

        String urlwhatsapp = "https://app.1e1okul.com/bilsemdeneme/resim/whatsapp.png";
        Picasso.with(this).load(urlwhatsapp).resize(yukseklik / 9, yukseklik / 9).centerInside().into(paylas_whatsapp_img);

        String url_bilsem_1 = "https://app.1e1okul.com/bilsemdeneme/resim/bilsem1.png";
        Picasso.with(this).load(url_bilsem_1).resize(yukseklik / 4, yukseklik / 5).centerInside().into(bilsem_1);

        String url_bilsem_2 = "https://app.1e1okul.com/bilsemdeneme/resim/bilsem2.png";
        Picasso.with(this).load(url_bilsem_2).resize(yukseklik / 4, yukseklik / 5).centerInside().into(bilsem_2);


        String url_bilsem_3 = "https://app.1e1okul.com/bilsemdeneme/resim/bilsem3.png";
        Picasso.with(this).load(url_bilsem_3).resize(yukseklik / 4, yukseklik / 5).centerInside().into(bilsem_3);


        tur1_txt = findViewById(R.id.hikaye_txt);
        tur2_txt = findViewById(R.id.tur2_txt);
        tur3_txt = findViewById(R.id.tur3_txt);
        tur4_txt = findViewById(R.id.tur4_txt);
        tur5_txt = findViewById(R.id.tur5_txt);
        tur6_txt = findViewById(R.id.tur6_txt);
        tur7_txt = findViewById(R.id.tur7_txt);
        tur8_txt = findViewById(R.id.tur8_txt);
        sonuc_sira_txt = findViewById(R.id.sonuc_sira_txt);
        sonuc_puan_txt = findViewById(R.id.sonuc_puan_txt);
        sonuc_baslik_txt = findViewById(R.id.sonuc_baslik_txt);
        sonuc_ozet_txt = findViewById(R.id.sonuc_ozet_txt);
        sonuc_net_txt = findViewById(R.id.sonuc_net_txt);
        ogrenci_ad_txt = findViewById(R.id.ogrenci_ad_txt);
        ayarpaylas_txt = findViewById(R.id.ayar_paylas_txt);
        ayarpaylas_txt2 = findViewById(R.id.ayar_paylas_txt2);
        ayarpaylas_txt3 = findViewById(R.id.ayar_paylas_txt3);


        clRota = findViewById(R.id.clRota);
        clYayin = findViewById(R.id.clYayin);
        clBasari = findViewById(R.id.clBasari);
        tvRota = findViewById(R.id.tvRota);
        tvRota.setTextSize((2 * punto) / 3);
        tvRota.setTextColor(getResources().getColor(R.color.colorseffaf_yesil));


        tur1_bar = findViewById(R.id.tur1_bar);
        tur2_bar = findViewById(R.id.tur2_bar);
        tur3_bar = findViewById(R.id.tur3_bar);
        tur4_bar = findViewById(R.id.tur4_bar);
        tur5_bar = findViewById(R.id.tur5_bar);
        tur6_bar = findViewById(R.id.tur6_bar);
        tur7_bar = findViewById(R.id.tur7_bar);
        tur8_bar = findViewById(R.id.tur8_bar);
        puan_Bar = findViewById(R.id.puan_Bar);
        sira_bar = findViewById(R.id.sira_bar);
        sonuc_net_bar = findViewById(R.id.sonuc_net_bar);
        konular_baslik = findViewById(R.id.konular_baslik);


        tur1 = sp.getInt("tur1", 0);
        tur2 = sp.getInt("tur2", 0);
        tur3 = sp.getInt("tur3", 0);
        tur4 = sp.getInt("tur4", 0);
        tur5 = sp.getInt("tur5", 0);
        tur6 = sp.getInt("tur6", 0);
        tur7 = sp.getInt("tur7", 0);
        tur8 = sp.getInt("tur8", 0);
        sira = sp.getInt("sira", 100);
        dogru_sayisi = sp.getInt("dogru_sayisi", 0);


        tur1_bar.setProgress(tur1);
        tur2_bar.setProgress(tur2);
        tur3_bar.setProgress(tur3);
        tur4_bar.setProgress(tur4);
        tur5_bar.setProgress(tur5);
        tur6_bar.setProgress(tur6);
        tur7_bar.setProgress(tur7);
        tur8_bar.setProgress(tur8);
        puan_Bar.setProgress(dogru_sayisi * 20);

        sonuc_net_bar.setProgress(dogru_sayisi);

        sira_bar.setMax(katilan);
        sira_bar.setProgress(katilan - sira);




        if (dogru_sayisi >= barajNet) {

            sonuc_ozet_txt.setTextSize((2 * punto) / 3);
            sonuc_ozet_txt.setText("Tebrikler\n Barajı geçtiniz ve sınavda " + sira + ". oldunuz.\n");
            sonuc_ozet_txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        } else {

            sonuc_ozet_txt.setTextSize((2 * punto) / 3);
            sonuc_ozet_txt.setText("Barajı Geçemediniz.\n"+ String.valueOf(barajNet - dogru_sayisi) + " soruya daha doğru cevap vermiş olsaydınız barajı geçebilecektiniz.\n");
            sonuc_ozet_txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }


        double sayi;
        sayi = (tur1_bar.getProgress() / tur1_bar.getMax());
        int say = (int) sayi * 100;
        Log.e("tur1bar max", String.valueOf(tur1_bar.getMax()));
        Log.e("tur1bar progress", String.valueOf(tur1_bar.getProgress()));
        Log.e("hesaplanan", String.valueOf(sayi));
        // hesapView.setText(String.valueOf(String.format("%10.2f", hesap))+" €");
        Log.e("hesaplanan", String.valueOf(say));
        Log.e("hesaplanan", String.valueOf(String.format("%10.2f", sayi)));


        sonuc_baslik_txt.setTextSize((3 * punto) / 2);
        ayarpaylas_txt.setTextSize((3 * punto) / 2);
        ayarpaylas_txt2.setTextSize((3 * punto) / 2);
        ayarpaylas_txt3.setTextSize((3 * punto) / 2);

        ogrenci_ad_txt.setTextSize(punto);

        ogrenci_ad_txt.setText("\tÖğrenci: " + ad + " " + soyad + "\n");



        sonuc_net_txt.setTextSize((2 * punto) / 3);
        sonuc_net_txt.setText("Toplam Soru: "+soruSayisi+"   Doğru Cevap: " + String.valueOf(dogru_sayisi));

        sonuc_puan_txt.setTextSize((2 * punto) / 3);
        sonuc_puan_txt.setText("Baraj Puan: "+barajNet*20+"    Puanınız: " + String.valueOf(dogru_sayisi * 20));



        sonuc_sira_txt.setTextSize((2 * punto) / 3);
        sonuc_sira_txt.setText("Katılan: " + katilan + "    Sıranız: " + sira);

        konular_baslik.setTextSize((2 * punto) / 3);


        tur1_txt.setTextSize((2 * punto) / 3);
        tur1_txt.setText("Bellek Kullanımı %" + (tur1 * 25));

        tur2_txt.setTextSize((2 * punto) / 3);
        tur2_txt.setText("Kavram İlişkisi %" + (tur2 * 20));


        tur3_txt.setTextSize((2 * punto) / 3);
        tur3_txt.setText("Görsel Bağıntılar %" + String.valueOf(String.format("%3.0f", (tur3 * 16.6))));

        tur4_txt.setTextSize((2 * punto) / 3);
        tur4_txt.setText("Parça Bütün İlişkisi %" + tur4 * 25);

        tur5_txt.setTextSize((2 * punto) / 3);
        tur5_txt.setText("Şekil Gölge İlişkisi %" + tur5 * 25);

        tur6_txt.setTextSize((2 * punto) / 3);
        if(sinif==1){tur6_txt.setText("Üç Boyutlu Düşünme %" + tur6 * 25);}
        else{ tur6_txt.setText("Üç Boyutlu Düşünme %" + tur6 * 20);}


        tur7_txt.setTextSize((2 * punto) / 3);
        tur7_txt.setText("Şifre Çözme %" + String.valueOf(String.format("%3.0f", (tur7 * 33.3))));


        tur8_txt.setTextSize((2 * punto) / 3);
        tur8_txt.setText("Sözel Dikkat %" + tur8 * 25);

        if(sinif==1){
            tur8_txt.setVisibility(View.INVISIBLE);
            tur8_txt.setVisibility(View.INVISIBLE);

        }


        sonuc_baslik_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    clBasari.setVisibility(View.VISIBLE);
                    clYayin.setVisibility(View.INVISIBLE);
                   }
        });


        clRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (clBasari.getVisibility() == View.INVISIBLE){

                    clBasari.setVisibility(View.VISIBLE);
                    clYayin.setVisibility(View.INVISIBLE);

                }else {

                    clBasari.setVisibility(View.INVISIBLE);
                    clYayin.setVisibility(View.VISIBLE);
                }


            }
        });



        paylas_kutusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clBasari.setVisibility(View.VISIBLE);
                clYayin.setVisibility(View.INVISIBLE);


                if(paylas_mail_img.getVisibility()==View.INVISIBLE){

                    paylas_icon.startAnimation(paylasileri);

                    paylas_mail_img.startAnimation(paylasacik);
                    paylas_mail_img.setVisibility(View.VISIBLE);

                    paylas_whatsapp_img.startAnimation(paylasacik);
                    paylas_whatsapp_img.setVisibility(View.VISIBLE);



                }else{ paylas_icon.startAnimation(paylasgeri);

                    paylas_mail_img.startAnimation(paylaskapali);
                    paylas_mail_img.setVisibility(View.INVISIBLE);

                    paylas_whatsapp_img.startAnimation(paylaskapali);
                    paylas_whatsapp_img.setVisibility(View.INVISIBLE);}



            }
        });



        paylas_whatsapp_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clRota.setVisibility(View.INVISIBLE);

               //  Bitmap karne_resmi= screenShot(sonuc_ekran);
               Bitmap karne_resmi = getViewBitmap(sonuc_ekran);
                //save the image now:
                saveImage(Sonuc.this,karne_resmi);
                //share it
                sendwhatsapp();

                clRota.setVisibility(View.VISIBLE);


            }
        });


        paylas_mail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clRota.setVisibility(View.INVISIBLE);

                // Bitmap karne_resmi= screenShot(sonuc_ekran);
                Bitmap karne_resmi = getViewBitmap(sonuc_ekran);
                //save the image now:
                saveImage(Sonuc.this,karne_resmi);
                //share it
                send();

                clRota.setVisibility(View.VISIBLE);

            }
        });


        paylasacik.setAnimationListener(new Animation.AnimationListener() {
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
                if (ContextCompat.checkSelfPermission(Sonuc.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions(Sonuc.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        // geri tuşuna dokunulduğunda
        AlertDialog.Builder gu = new AlertDialog.Builder(Sonuc.this);
        gu.setIcon(R.drawable.ic_warning_black_24dp);
        gu.setTitle("Çıkış için \"Evet\" e basın.");
        gu.setMessage("Başarılar Dileriz");


        gu.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                File myFile = new File("/storage/emulated/0/BilsemDeneme/BilsemKarne.png");
                if (myFile.exists()) {
                    myFile.delete();


                }
                editor.remove("sinif");
                editor.remove("dogru_sayisi");
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


    private static void saveImage(Context context, Bitmap finalBitmap) {



        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/BilsemDeneme");
        Log.i("Directory", "==" + myDir);
        myDir.mkdirs();

        String fname = "BilsemKarne" + ".png";


        File file = new File(myDir, fname);

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendwhatsapp() {

        try {


            File myFile = new File("/storage/emulated/0/BilsemDeneme/BilsemKarne.png");
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);


            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri imageUri = FileProvider.getUriForFile(Sonuc.this, BuildConfig.APPLICATION_ID + ".provider",myFile);
           // sendIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile));
            sendIntent.putExtra("android.intent.extra.STREAM", imageUri);
            sendIntent.setType(type);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Merhaba, bu benim BİLSEM Deneme Sınavındaki başarı tablom. Aynı sınava siz de buradan katılabilirsiniz : " + "https://play.google.com/store/apps/details?id=com.info.bilsemdeneme");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            //startActivity(Intent.createChooser(sendIntent, "BİLSEM Sonucu Paylaş"));
            startActivity(sendIntent);


        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("resim paylaşmak için", e.getMessage());

        }
    }

    public void send() {

        try {


            File myFile = new File("/storage/emulated/0/BilsemDeneme/BilsemKarne.png");
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
             Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType(type);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Merhaba, bu benim BİLSEM Deneme Sınavındaki başarı tablom. Aynı sınava siz de buradan katılabilirsiniz : " + "https://play.google.com/store/apps/details?id=com.info.bilsemdeneme");
            sharingIntent.setType("text/plain");
            Uri imageUri = FileProvider.getUriForFile(Sonuc.this, BuildConfig.APPLICATION_ID + ".provider",myFile);
            sharingIntent.putExtra("android.intent.extra.STREAM", imageUri);
            //sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile));
            startActivity(Intent.createChooser(sharingIntent, "BİLSEM Sonucu Paylaş"));


        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("resim paylaşmak için", e.getMessage());

        }
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        Log.e("resim yollandı", "evet yollandı");

        return bitmap;
    }

    public static Bitmap getViewBitmap(View v) {


        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }




}
