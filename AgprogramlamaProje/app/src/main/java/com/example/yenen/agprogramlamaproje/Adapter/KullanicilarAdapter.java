package com.example.yenen.agprogramlamaproje.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yenen.agprogramlamaproje.Model.Herkez_Model;
import com.example.yenen.agprogramlamaproje.Model.KullanicicekModel;
import com.example.yenen.agprogramlamaproje.Model.OzelModel;
import com.example.yenen.agprogramlamaproje.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yenen on 23.4.2017.
 */
public class KullanicilarAdapter  extends ArrayAdapter<OzelModel> {
    private Context context;
    private ViewHolder viewHolder;
    private List<OzelModel> arrayList = new ArrayList<OzelModel>();

    public KullanicilarAdapter(Context context, List<OzelModel> list_item) {
        super(context, R.layout.lista_item,list_item);
        this.context =context;
        this.arrayList =list_item;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.lista_item, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.Kullanici_adi_lista = (TextView)view.findViewById(R.id.Kullanici_adi_lista);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.Kullanici_adi_lista.setText(arrayList.get(position).getKullaniciAdi());
      return  view;
    }
    public static class  ViewHolder{
        TextView Kullanici_adi_lista;

    }
}
