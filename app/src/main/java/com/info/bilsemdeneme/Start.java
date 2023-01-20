package com.info.bilsemdeneme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;


public class Start extends AppCompatActivity {

    private ImageView iv_start;
    private TextView tv_ready, tv_gerisay;
    private Animation iconacik, birhopla, hopla, vibration, fadin;
    private int n,genislik,yukseklik;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    //private AdRequest adRequest;
   // private AdView mAdView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        iv_start = findViewById(R.id.iv_start);
        tv_ready = findViewById(R.id.tv_ready);
        tv_gerisay = findViewById(R.id.tv_gerisay);
        iconacik = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.iconacik);
        hopla = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.birhopla);
        birhopla = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hopla);
        vibration = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibration);
        fadin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadin);
        MediaPlayer mpstart = MediaPlayer.create(Start.this, R.raw.basla);
        MediaPlayer mpheart = MediaPlayer.create(Start.this, R.raw.kalpp);
        MediaPlayer mptitre = MediaPlayer.create(Start.this, R.raw.titre);
        sp = getSharedPreferences("SinavBilgisi", MODE_PRIVATE);
        editor = sp.edit();


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        genislik = size.x;
        yukseklik = size.y;

        double density = getResources().getDisplayMetrics().density;
        final int punto = (int) ((genislik + 1200) / (density * 70) + 2);


        String urlbslt = "https://app.1e1okul.com/bilsemdeneme/resim/baslat_buton_g.png";
        Picasso.with(this).load(urlbslt).resize(genislik /4, genislik / 4).centerInside().into(iv_start);
        // tv_ready.setScaleX(20*genislik/50);
        //iv_start.setScaleX(25*genislik/50);


        tv_ready.setTextSize(punto);


/*
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });  */

/*/ reklamı bir süreliğine kaldırdık
        mAdView = findViewById(R.id.AV_Banner);
        AdRequest adRequest = new AdRequest.Builder().build();




        try {
            mAdView.loadAd(adRequest);
            Log.e("reklamda","Hata yok: ");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("reklam hatası,","Hata: "+e);
        }
*/  //reklamı bir süreliğine kaldırdık

        n=2;
        tv_ready.setAnimation(vibration);
        mptitre.start();


        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_gerisay.startAnimation(fadin);

                fadin.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                       mpheart.start();
                       // mpadrenalin.start();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                        n=n-1;
                        tv_gerisay.setText(String.valueOf(n));
                       mpheart.start();


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub

                        iv_start.setVisibility(View.INVISIBLE);
                        tv_ready.setText("BAŞLIYORUZ !!!");

                        mpstart.start();

                        startActivity(new Intent(Start.this, MainActivity.class));
                        finish();

                    }
                });


            }
        });



        mpstart.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();

            }
        });

        mptitre.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                 mp.stop();
                 mp.reset();

            }
        });


    }


    @Override
    public void onBackPressed() {


                editor.remove("sinif");
                editor.remove("soru_no");
                editor.commit();
        startActivity(new Intent(Start.this, MainActivity.class));
        finish();

        // super.onBackPressed();
    }



}


