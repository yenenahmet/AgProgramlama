package com.example.yenen.agprogramlamaproje;

import com.example.yenen.agprogramlamaproje.Model.GrublarREtModel;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Yenen on 23.4.2017.
 */
public interface TummesajlarCekServis {
    @GET("/Agprogramlama/SunucuveGrubListe/api/json/{Hangi}")
    Call<GrublarREtModel[]> getMesajcek(@Path("Hangi") String Hangi);
}
