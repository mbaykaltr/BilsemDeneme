package com.info.bilsemdeneme;

import java.io.Serializable;

public class Soru implements Serializable {

private int soru_id;
private int tur;
private  int sure;
private String aciklama;
private String soru_metni;
private String soru_resmi;
private String dogru_cevap;
    private String a_metni;
    private String b_metni;
    private String c_metni;
    private String d_metni;
    private String a_resmi;
    private String b_resmi;
    private String c_resmi;
    private String d_resmi;

      public Soru() {
    }

    public Soru(int soru_id) {
        this.soru_id = soru_id;
    }

    public Soru (int soru_id, int tur, String aciklama, String soru_metni, String dogru_cevap) {

        this.soru_id = soru_id;
        this.tur = tur;
        this.aciklama = aciklama;
        this.soru_metni = soru_metni;
        this.dogru_cevap = dogru_cevap;

    }

    public Soru(int soru_id, int tur, int sure, String aciklama, String soru_metni, String soru_resmi, String dogru_cevap, String a_metni, String b_metni, String c_metni, String d_metni, String a_resmi, String b_resmi, String c_resmi, String d_resmi) {
        this.soru_id = soru_id;
        this.tur = tur;
        this.sure = sure;
        this.aciklama = aciklama;
        this.soru_metni = soru_metni;
        this.soru_resmi = soru_resmi;
        this.dogru_cevap = dogru_cevap;
        this.a_metni = a_metni;
        this.b_metni = b_metni;
        this.c_metni = c_metni;
        this.d_metni = d_metni;
        this.a_resmi = a_resmi;
        this.b_resmi = b_resmi;
        this.c_resmi = c_resmi;
        this.d_resmi = d_resmi;
    }

    public int getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(int soru_id) {
        this.soru_id = soru_id;
    }

    public int getTur() {
        return tur;
    }

    public void setTur(int tur) {
        this.tur = tur;
    }

    public int getSure() {
        return sure;
    }

    public void setSure(int sure) {
        this.sure = sure;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getSoru_metni() {
        return soru_metni;
    }

    public void setSoru_metni(String soru_metni) {
        this.soru_metni = soru_metni;
    }

    public String getSoru_resmi() {
        return soru_resmi;
    }

    public void setSoru_resmi(String soru_resmi) {
        this.soru_resmi = soru_resmi;
    }

    public String getDogru_cevap() {
        return dogru_cevap;
    }

    public void setDogru_cevap(String dogru_cevap) {
        this.dogru_cevap = dogru_cevap;
    }

    public String getA_metni() {
        return a_metni;
    }

    public void setA_metni(String a_metni) {
        this.a_metni = a_metni;
    }

    public String getB_metni() {
        return b_metni;
    }

    public void setB_metni(String b_metni) {
        this.b_metni = b_metni;
    }

    public String getC_metni() {
        return c_metni;
    }

    public void setC_metni(String c_metni) {
        this.c_metni = c_metni;
    }

    public String getD_metni() {
        return d_metni;
    }

    public void setD_metni(String d_metni) {
        this.d_metni = d_metni;
    }

    public String getA_resmi() {
        return a_resmi;
    }

    public void setA_resmi(String a_resmi) {
        this.a_resmi = a_resmi;
    }

    public String getB_resmi() {
        return b_resmi;
    }

    public void setB_resmi(String b_resmi) {
        this.b_resmi = b_resmi;
    }

    public String getC_resmi() {
        return c_resmi;
    }

    public void setC_resmi(String c_resmi) {
        this.c_resmi = c_resmi;
    }

    public String getD_resmi() {
        return d_resmi;
    }

    public void setD_resmi(String d_resmi) {
        this.d_resmi = d_resmi;
    }
}
