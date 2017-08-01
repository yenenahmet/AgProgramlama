package com.example.yenen.agprogramlamaproje.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yenen.agprogramlamaproje.Model.Herkez_Model;
import com.example.yenen.agprogramlamaproje.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yenen on 24.4.2017.
 */
public class KullaniciAdapaterOzel extends ArrayAdapter<Herkez_Model> {
    private Context context;
    private ViewHolder viewHolder;
    private List<Herkez_Model> arrayList = new ArrayList<Herkez_Model>();

    public KullaniciAdapaterOzel(Context context, List<Herkez_Model> list_item) {
        super(context, R.layout.herkez_item, list_item);
        this.context = context;
        this.arrayList = list_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.herkez_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Kullanici_adi_özelmesaj = (TextView) view.findViewById(R.id.Kullanici_adi_özelmesaj);
            viewHolder.img_profil_özelmesaj = (ImageView) view.findViewById(R.id.img_profil_Özelmesaj);
            viewHolder.özelmesaj = (TextView) view.findViewById(R.id.özelmesaj);
            viewHolder.txt_zaman_özelmesaj = (TextView) view.findViewById(R.id.txt_zaman_özelmesaj);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.Kullanici_adi_özelmesaj.setText(arrayList.get(position).getKullanıcıAdi());
        viewHolder.özelmesaj.setText(arrayList.get(position).getMesaj());
        viewHolder.txt_zaman_özelmesaj.setText(arrayList.get(position).getTarihZaman());
        // Picasso.with(getContext()).load(MyApi.URL_IMAGES).transform(new CircleTransformation()).into(viewHolder.img_profil_özelmesaj);
        return view;
    }

    public static class ViewHolder {
        TextView Kullanici_adi_özelmesaj;
        TextView özelmesaj;
        ImageView img_profil_özelmesaj;
        TextView txt_zaman_özelmesaj;
    }
}