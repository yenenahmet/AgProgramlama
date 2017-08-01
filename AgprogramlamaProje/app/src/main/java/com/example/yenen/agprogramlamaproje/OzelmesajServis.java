package com.example.yenen.agprogramlamaproje;

import com.example.yenen.agprogramlamaproje.Model.GrublarREtModel;
import com.example.yenen.agprogramlamaproje.Model.OzelmesajModel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Yenen on 24.4.2017.
 */
public interface OzelmesajServis {
    @GET("/Agprogramlama/KullaniciOzelMEsajlar/api/json/{KullaniciAdi}")
    Call<OzelmesajModel[]> getMesajOzelcek(@Path("KullaniciAdi") String KullaniciAdi);
}
