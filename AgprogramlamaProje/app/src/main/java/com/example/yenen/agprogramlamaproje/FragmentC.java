package com.example.yenen.agprogramlamaproje;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.yenen.agprogramlamaproje.Adapter.HerkezAdapter;
import com.example.yenen.agprogramlamaproje.Model.Herkez_Model;
import java.io.DataInputStream;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class    FragmentC extends Fragment implements  Runnable {

    private  static  Socket clientSocket =null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    public   static boolean closed = false;

    public  EditText mesajYolatext;
    public  Button btnGonder;
    public static ListView listView;
    public  static Context context;
    private static List<Herkez_Model> list = new ArrayList<Herkez_Model>();
    @Override
    public void onStop() {
        super.onStop();
        closed= true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context =getActivity();
        closed = false;
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.fragment_fragment_c, container, false);
        listView = (ListView)view.findViewById(R.id.ozelmesajlist);
        mesajYolatext = (EditText)view.findViewById(R.id.özelgöndertext);
        btnGonder = (Button) view.findViewById(R.id.btnözelmesajgönder);


        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Thread  thread = new Thread()
        {
            @Override
            public void run() {

                main();
            }
        };
        thread.start();


    }
    public  void main() {
        try{
            clientSocket = new Socket("192.168.1.29",8080); // Tüm  KUllanıcılar için 8080 portu
            // grub ve tek kullanıcılar için protlar belirlenecek
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        }catch(Exception ex){
            Log.e("Main","baglantı hatası"+ex.toString());

        }
        if(clientSocket != null && os != null && is != null){

                try{
                    new Thread(new FragmentC()).start();
                }catch (Exception ex){
                    Log.e("Thread hatası",ex.toString());
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
                                //FragmentC.MesajGonder(mesaj);
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
    public void onResume() {
        super.onResume();

        listView.refreshDrawableState();
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
                        }
                    });

                }
            }

        }catch(Exception ex){
            Log.e("run",ex.toString()+"RUN");
        }
    }
}
