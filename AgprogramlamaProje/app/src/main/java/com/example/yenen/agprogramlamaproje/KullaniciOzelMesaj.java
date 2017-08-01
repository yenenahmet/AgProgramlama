package com.example.yenen.agprogramlamaproje;

import android.content.Context;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yenen.agprogramlamaproje.Adapter.HerkezAdapter;
import com.example.yenen.agprogramlamaproje.Adapter.KullaniciAdapaterOzel;
import com.example.yenen.agprogramlamaproje.Adapter.KullanicilarAdapter;
import com.example.yenen.agprogramlamaproje.Model.Herkez_Model;
import com.example.yenen.agprogramlamaproje.Model.KullanicicekModel;
import com.example.yenen.agprogramlamaproje.Model.OzelModel;
import com.example.yenen.agprogramlamaproje.Model.OzelmesajModel;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class KullaniciOzelMesaj extends AppCompatActivity implements  Runnable{
    public static String KullaniciAdiBaska;
    private int Lifi;
    private  static Socket clientSocket =null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    public   static boolean closed = false;
    public String GrubAdi;
    public EditText mesajYolatext;
    public ImageView btnGonder;
    public static ListView listView;
    public static List<Herkez_Model> list = new ArrayList<Herkez_Model>();
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_ozel_mesaj);
        Bundle extras = getIntent().getExtras();
        context=getApplicationContext();
        Lifi = extras.getInt("Lif");
        KullaniciAdiBaska = extras.getString("KullaniciAdi");
        getSupportActionBar().setTitle(KullaniciAdiBaska);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView =(ListView)findViewById(R.id.KullaniciOzelList);
        mesajYolatext =(EditText)findViewById(R.id.KullaniciOzelMesaj);
        btnGonder =(ImageView)findViewById(R.id.btnKullaniciOzelGonder);
        Veritabıcek();
        KullanicilariCEk();
        Thread  thread1 = new Thread()
        {
            @Override
            public void run() {
                main();
            }
        };
        thread1.start();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){

           // os.println("/cik");
           list.clear();
            this.finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void main(){


        try{
            clientSocket = new Socket("192.168.43.201",8086); // Tüm  KUllanıcılar için 8080 portu
            // grub ve tek kullanıcılar için protlar belirlenecek
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
            Log.e("bglandi","ilk");
        }catch(Exception ex){
            Log.e("Main","baglantı hatası"+ex.toString());

        }
        if(clientSocket != null && os != null && is != null){

            try{
                Looper.prepare();
                new Thread(new KullaniciOzelMesaj()).start();
                os.println(MyApi.Kullanici_Adi);
            }catch (Exception ex){
                Log.e("thread hatası",ex.toString());
            }
            try{
                while(!closed){
                    btnGonder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!closed){
                                String mesaj =  mesajYolatext.getText().toString();
                                os.println(MyApi.Kullanici_Adi +","+mesaj+","+String.valueOf(Lifi)+","+KullaniciAdiBaska);
                                Herkez_Model model = new Herkez_Model();
                                model.setTarihZaman("");
                                model.setKullanıcıAdi(MyApi.Kullanici_Adi);
                                model.setMesaj(mesaj);
                                list.add(model);
                                final KullaniciAdapaterOzel adapter = new KullaniciAdapaterOzel(context, list);
                                Log.e("GEldiii", "Hanimmmmmmmmmmm");
                                listView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.setAdapter(adapter);
                                        listView.refreshDrawableState();
                                        listView.setSelection(adapter.getCount() - 1);

                                    }
                                });
                            }
                        }
                    });

                }
                os.close();
                is.close();
                clientSocket.close();
                Log.e("kapandi","socket");
            }catch(Exception ex){
                Log.e("Main","iletişim hatası"+ex.toString());

            }
        }
    }
    @Override
    public void run() {
        String responseLine;
        try{
            while((responseLine = is.readLine())!= null){
                Log.e("Responseline =",responseLine);
                String[] kelime = responseLine.split(",");
        if(kelime[0].equals(KullaniciAdiBaska)) {
            KullanicilariCEk();

            Log.e("GEldiii", "Hanimm");
            Herkez_Model model = new Herkez_Model();
            try {

                model.setKullanıcıAdi(kelime[0]);
                model.setTarihZaman("");
                model.setMesaj(kelime[1]);
                list.add(model);
            } catch (Exception ex) {
                Log.e("Hata Geldi", "GEldiiiiiiii");
            }
            Log.e("GEldiii", "Hanimm2");
            final KullaniciAdapaterOzel adapter = new KullaniciAdapaterOzel(context, list);
            Log.e("GEldiii", "Hanimm3");
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adapter);
                    listView.refreshDrawableState();
                    listView.setSelection(adapter.getCount() - 1);

                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"yanlış kullanıcı", Toast.LENGTH_LONG).show();
        }
            }

        }catch(Exception ex){
            Log.e("run",ex.toString()+"RUN");
        }
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
                        if(KullaniciAdiBaska.equals(modevalues.getKullaniciAdi())){
                            Log.e("LifiBaska",String.valueOf(modevalues.getLif()));
                            Lifi = modevalues.getLif();
                        }
                        }
                    }



                }else{
                    Toast.makeText(getApplicationContext(),"Dönüş Tipinde Soru", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(),"Serviste Hata oluştu"+ t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

private void Veritabıcek(){
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MyApi.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    OzelmesajServis servis = retrofit.create(OzelmesajServis.class);
    Call<OzelmesajModel[]> call = servis.getMesajOzelcek(MyApi.Kullanici_Adi);
    call.enqueue(new Callback<OzelmesajModel[]>() {
        @Override
        public void onResponse(Response<OzelmesajModel[]> response, Retrofit retrofit) {
            if(response.isSuccess()){
                for(OzelmesajModel modevalues:response.body()){
                    Herkez_Model model = new Herkez_Model();
                    model.setTarihZaman("");
                    if(modevalues.getGeldimi()){
                        model.setKullanıcıAdi(modevalues.getKullaniciBaska());
                    }else if(!modevalues.getGeldimi()){
                        model.setKullanıcıAdi(MyApi.Kullanici_Adi);
                    }
                    model.setMesaj(modevalues.getMesaj());
                    list.add(model);
                    final KullaniciAdapaterOzel adapter = new KullaniciAdapaterOzel(context, list);
                    Log.e("GEldiii", "Hanimmmmmmmmmmm");
                    listView.setAdapter(adapter);
                    listView.refreshDrawableState();
                    listView.setSelection(adapter.getCount() - 1);


                }
            }else{
                Toast.makeText(getApplicationContext(),"Dönüş Tipinde Soru", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(getApplicationContext(),"Serviste Hata oluştu"+ t.toString(), Toast.LENGTH_LONG).show();

        }
    });
}
}
