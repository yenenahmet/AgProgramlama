package com.example.yenen.agprogramlamaproje.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yenen on 24.4.2017.
 */
public class OzelmesajModel {
    @SerializedName("KullaniciBaska")
    @Expose
    private String KullaniciBaska;
    @SerializedName("Mesaj")
    @Expose
    private String Mesaj;
    @SerializedName("zaman")
    @Expose
    private String zaman;
    @SerializedName("geldimi")
    @Expose
    private boolean geldimi;
    public String getKullaniciBaska(){
        return KullaniciBaska;
    }
    public void setKullaniciBaska(String KullaniciBaska){
        this.KullaniciBaska =KullaniciBaska;
    }

    public String getMesaj(){
        return  Mesaj;
    }
    public void setMesaj(String Mesaj){
        this.Mesaj = Mesaj;
    }

    public String getZaman(){
        return  zaman;
    }
    public void setZaman(String zaman){
        this.zaman = zaman;
    }

    public boolean getGeldimi(){
        return  geldimi;
    }
    public void setGeldimi(boolean geldimi){
        this.geldimi = geldimi;
    }
}
