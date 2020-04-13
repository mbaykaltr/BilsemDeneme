package com.info.bilsemdeneme;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SoruDao {



    public void SoruGetirWeb (final Context context, final int soru_no) {


        final Soru gelen_soru = new Soru(soru_no);

        String url = "http://app.1e1okul.com/bilsemdeneme/soruWithId.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray soru = jsonObject.getJSONArray("soru");
                    JSONObject f = soru.getJSONObject(0);

                    gelen_soru.setDogru_cevap(f.getString("dogru_cevap"));
                    gelen_soru.setTur(f.getInt("tur"));
                    gelen_soru.setAciklama(f.getString("aciklama"));





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

                params.put("soru_no", String.valueOf(soru_no));

                return params;
            }


        };

        Volley.newRequestQueue(context).add(postStringRequest);


            return ;

    }


    public void CozulmeArttir(Context mcontex, final int soru_id) {

      String url = "http://app.1e1okul.com/bilsemdeneme/cozulme_arttir.php";


        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.e("arttırma isteği sonucu", response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("soru_id",String.valueOf(soru_id));


                return params;
            }
        };

        Volley.newRequestQueue(mcontex).add(postStringRequest);

    }


    public void SonucEkleWeb (final Context context, final String ad, final String soyad, final int  dogru_sayisi) {


        String url ="http://app.1e1okul.com/bilsemdeneme/sonucekle.php";

        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("sonuc ekleme cevabı",response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ad",String.valueOf(ad));
                params.put("soyad",String.valueOf(soyad));
                params.put("dogru_sayisi",String.valueOf(dogru_sayisi));

                return params;
            }
        };

        Volley.newRequestQueue(context).add(postStringRequest);

    }


    public void mailGonder (final Context context, int sinif, final String ad, final String soyad,final String email,final int sayi, final int sira,
                            final int  dogru_sayisi,final int  tur1,final int  tur2,final int  tur3,final int  tur4,final int  tur5,final int  tur6,final int  tur7, final int  tur8 ) {


        String url ="http://app.1e1okul.com/bilsemdeneme/mailsend/mail_gonder"+sinif+".php";

        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Log.e("mail gönderme cevabı",response);

                Toast.makeText(context, "Karneniz "+email+" adresine "+response, Toast.LENGTH_LONG).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ad",String.valueOf(ad));
                params.put("soyad",String.valueOf(soyad));
                params.put("email",String.valueOf(email));
                params.put("sayi",String.valueOf(sayi));
                params.put("sira",String.valueOf(sira));
                params.put("dogru_sayisi",String.valueOf(dogru_sayisi));
                params.put("tur1",String.valueOf(tur1));
                params.put("tur2",String.valueOf(tur2));
                params.put("tur3",String.valueOf(tur3));
                params.put("tur4",String.valueOf(tur4));
                params.put("tur5",String.valueOf(tur5));
                params.put("tur6",String.valueOf(tur6));
                params.put("tur7",String.valueOf(tur7));
                params.put("tur8",String.valueOf(tur8));

                return params;
            }
        };
        postStringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context).add(postStringRequest);

    }


    public void SiraGetirWeb (final Context context, final int sayi) {



        String url = "http://app.1e1okul.com/bilsemdeneme/siraWithId.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray bilsem_ogrenci = jsonObject.getJSONArray("bilsem_ogrenci");
                    JSONObject f = bilsem_ogrenci.getJSONObject(0);


                    int sira = f.getInt("sira");



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
                params.put("ogr_id", String.valueOf(sayi));

                return params;
            }


        };

        Volley.newRequestQueue(context).add(postStringRequest);


        return ;

    }


}
