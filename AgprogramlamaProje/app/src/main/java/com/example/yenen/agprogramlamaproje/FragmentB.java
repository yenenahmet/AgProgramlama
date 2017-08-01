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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class FragmentB extends Fragment {
    LinearLayout lA;
    LinearLayout lB;
    LinearLayout lc;
    LinearLayout tüm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TammamlananDoldur();
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.fragment_fragment_b, container, false);

     lA = (LinearLayout)view.findViewById(R.id.Agrubu_Liner);
        lA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApi.Kullanici_Adi.substring(0,1).equals("A")){
                    Intent intent = new Intent(getActivity(), Grub_Mesajlari.class);
                    intent.putExtra("grub","A");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"Malesef Bu gruba Ait Değilsiniz !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    lB = (LinearLayout)view.findViewById(R.id.bgrubu_Liner);
        lB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(MyApi.Kullanici_Adi.substring(0,1).equals("B")){
                        Intent intent = new Intent(getActivity(), Grub_Mesajlari.class);
                        intent.putExtra("grub","B");
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(),"Malesef Bu gruba Ait Değilsiniz !!", Toast.LENGTH_LONG).show();
                    }
            }
        });
lc = (LinearLayout)view.findViewById(R.id.cgrubu_Liner);
        lc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApi.Kullanici_Adi.substring(0,1).equals("C")){
                    Intent intent = new Intent(getActivity(), Grub_Mesajlari.class);
                    intent.putExtra("grub","C");
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"Malesef Bu gruba Ait Değilsiniz !!", Toast.LENGTH_LONG).show();
                }
            }
        });
tüm = (LinearLayout)view.findViewById(R.id.Tümgrubu_Liner);
        tüm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Grub_Mesajlari.class);
                intent.putExtra("grub","tüm");
                startActivity(intent);
            }
        });
        return view;
    }
}
