package com.info.bilsemdeneme;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Force extends AppCompatActivity {

    private Button bt_update, bt_later;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);




            bt_update = findViewById(R.id.bt_update);
            bt_later = findViewById(R.id.bt_later);


            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.info.bilsemdeneme&hl=tr&gl=US" + Force.this.getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=com.info.bilsemdeneme&hl=tr&gl=US" + Force.this.getPackageName())));
                    }

                }
            });


            bt_later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                }
            });











        }
}
