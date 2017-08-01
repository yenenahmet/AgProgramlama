package com.example.yenen.agprogramlamaproje;

import android.content.Context;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yenen.agprogramlamaproje.Adapter.HerkezAdapter;
import com.example.yenen.agprogramlamaproje.Model.GrublarREtModel;
import com.example.yenen.agprogramlamaproje.Model.Herkez_Model;

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

public class Grub_Mesajlari extends AppCompatActivity implements  Runnable {
    private  static Socket clientSocket =null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    public   static boolean closed = false;
    public String GrubAdi;
    public  EditText mesajYolatext;
    public ImageView btnGonder;
    public static ListView listView;
    public static List<Herkez_Model> list = new ArrayList<Herkez_Model>();
    public static Context context;
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grub__mesajlari);
        closed= false;
        Bundle extras = getIntent().getExtras();
        context = getApplicationContext();
        GrubAdi = extras.getString("grub");
        getSupportActionBar().setTitle(GrubAdi +"Grubu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.grublist);
        mesajYolatext = (EditText)findViewById(R.id.GrubMesaj);
        btnGonder = (ImageView)findViewById(R.id.btngrubgonder);
        MesajlariServistenAl();
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

            os.println("/cik");
            list.clear();
            this.finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void main(){
        int port =0 ;
        if(GrubAdi.equals("A")){
            port =8081;
        }else if(GrubAdi.equals("B")){
            port = 8082;
        }else if(GrubAdi.equals("C")){
            port = 8083;
        }else if(GrubAdi.equals("tüm")){
            port = 8080;
        }
        try{
            clientSocket = new Socket("192.168.43.201",port); // Tüm  KUllanıcılar için 8080 portu
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
                    new Thread(new Grub_Mesajlari()).start();
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
                                Date simdikiZaman = new Date();
                                os.println(MyApi.Kullanici_Adi +","+simdikiZaman.toString()+","+mesaj);

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
                if(responseLine.toString().equals("ismiz:")){
                    os.println(MyApi.Kullanici_Adi);
                }else{
                    Log.e("GEldiii","Hanimm");
                    Herkez_Model model = new Herkez_Model();
                    try{
                        String[] kelime = responseLine.split(",");
                        model.setKullanıcıAdi(kelime[0]);
                        model.setTarihZaman(kelime[1]);
                        model.setMesaj(kelime[2]);
                        list.add(model);
                    }catch (Exception ex){
                        Log.e("Hata Geldi","GEldiiiiiiii");
                    }
                    Log.e("GEldiii","Hanimm2");
                    final HerkezAdapter adapter = new HerkezAdapter(context, list);
                    Log.e("GEldiii","Hanimm3");
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

        }catch(Exception ex){
            Log.e("run",ex.toString()+"RUN");
        }

    }

        private void MesajlariServistenAl(){
    // Kullanici Daha önceki Mesajları Web servis ten alıyor....
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyApi.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TummesajlarCekServis servis = retrofit.create(TummesajlarCekServis.class);
            Call<GrublarREtModel[]> call = servis.getMesajcek(GrubAdi);
            call.enqueue(new Callback<GrublarREtModel[]>() {
                @Override
                public void onResponse(Response<GrublarREtModel[]> response, Retrofit retrofit) {
                    if(response.isSuccess()){
                        for(GrublarREtModel modelvalues:response.body()){
                            Herkez_Model items = new Herkez_Model();
                            items.setMesaj(modelvalues.getMesaj());
                            items.setKullanıcıAdi(modelvalues.getMesajAtan());
                            items.setTarihZaman(modelvalues.getTarih());
                            list.add(items);
                        }
                        final HerkezAdapter adapter = new HerkezAdapter(getApplicationContext(), list);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listView.refreshDrawableState();
                        listView.post(new Runnable() {
                            @Override
                            public void run() {
                                listView.setSelection(adapter.getCount() - 1);
                            }
                        });
                        Toast.makeText(getApplicationContext(),"Önceki Mesajlar Başarıyla GEtirildi...", Toast.LENGTH_LONG).show();
                    }else {
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
