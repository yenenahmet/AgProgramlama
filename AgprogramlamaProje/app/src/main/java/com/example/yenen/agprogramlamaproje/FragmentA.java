package com.example.yenen.agprogramlamaproje;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yenen.agprogramlamaproje.Adapter.KullanicilarAdapter;
import com.example.yenen.agprogramlamaproje.Model.Herkez_Model;
import com.example.yenen.agprogramlamaproje.Model.KullanicicekModel;
import com.example.yenen.agprogramlamaproje.Model.OzelModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class FragmentA extends Fragment {
    ListView listview;
    KullanicilarAdapter adapter ;
    private   List<OzelModel> list = new ArrayList<OzelModel>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.fragment_, container, false);
Log.e("Fragaaa","AAAAAAAAAAAAAaa");

        listview = (ListView)view.findViewById(R.id.lista);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"Lif = "+String.valueOf(list.get(position).getLif()), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), KullaniciOzelMesaj.class);
                intent.putExtra("KullaniciAdi",list.get(position).getKullaniciAdi());
                intent.putExtra("Lif",list.get(position).getLif());
                startActivity(intent);

            }
        });
        KullanicilariCEk();
        return view;
    }
    private void KullanicilariCEk(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KullanicilariCekServis servis = retrofit.create(KullanicilariCekServis.class);
        Call<KullanicicekModel[]> call = servis.getKullanicicek();
    call.enqueue(new Callback<KullanicicekModel[]>() {
        @Override
        public void onResponse(Response<KullanicicekModel[]> response, Retrofit retrofit) {
            if(response.isSuccess()){
                for(KullanicicekModel modevalues:response.body()){
                    if(!MyApi.Kullanici_Adi.equals(modevalues.getKullaniciAdi())) {
                        OzelModel model = new OzelModel();
                        model.setKullaniciAdi(modevalues.getKullaniciAdi());
                        model.setLif(modevalues.getLif());
                        list.add(model);
                    }
                }
                adapter = new KullanicilarAdapter(getActivity(),list);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }else{
                Toast.makeText(getActivity(),"Dönüş Tipinde Soru", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(getActivity(),"Serviste Hata oluştu"+ t.toString(), Toast.LENGTH_LONG).show();
        }
    });
    }
}
