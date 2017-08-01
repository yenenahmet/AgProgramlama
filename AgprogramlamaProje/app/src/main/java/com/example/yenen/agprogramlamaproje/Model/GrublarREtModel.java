package com.example.yenen.agprogramlamaproje.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yenen on 23.4.2017.
 */
public class GrublarREtModel {
    @SerializedName("MesajAtan")
    @Expose
    private String MesajAtan;
    @SerializedName("mesaj")
    @Expose
    private String mesaj;
    @SerializedName("Tarih")
    @Expose
    private String Tarih;

    public String getMesajAtan(){
        return MesajAtan;
    }
    public void setMesajAtan(String MesajAtan){
        this.MesajAtan =MesajAtan;
    }

    public String getMesaj(){
        return  mesaj;
    }
    public void setMesaj(String mesaj){
        this.mesaj = mesaj;
    }

    public String getTarih(){
        return  Tarih;
    }
    public void setTarih(String Tarih){
        this.Tarih = Tarih;
    }
}
