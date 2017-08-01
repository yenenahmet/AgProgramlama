package com.example.yenen.agprogramlamaproje;

import com.example.yenen.agprogramlamaproje.Model.GrublarREtModel;
import com.example.yenen.agprogramlamaproje.Model.KullanicicekModel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Yenen on 23.4.2017.
 */
public interface KullanicilariCekServis {
    @GET("/Agprogramlama/KullanicilariGoster/api/json")
    Call<KullanicicekModel[]> getKullanicicek();
}
