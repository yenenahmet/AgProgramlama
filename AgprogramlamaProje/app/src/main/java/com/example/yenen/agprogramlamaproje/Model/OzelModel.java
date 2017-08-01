package com.example.yenen.agprogramlamaproje.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yenen on 23.4.2017.
 */
public class OzelModel {

    private String KullaniciAdi;
    private int lif;


    public String getKullaniciAdi(){
        return KullaniciAdi;
    }
    public void setKullaniciAdi(String KullaniciAdi){
        this.KullaniciAdi =KullaniciAdi;
    }

    public int getLif(){
        return  lif;
    }
    public void setLif(int lif){
        this.lif = lif;
    }
}
