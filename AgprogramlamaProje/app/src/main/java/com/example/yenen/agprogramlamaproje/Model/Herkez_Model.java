package com.example.yenen.agprogramlamaproje.Model;

import java.io.Serializable;

/**
 * Created by Yenen on 17.4.2017.
 */

    public class Herkez_Model implements Serializable {
        private static final long serialVersionUID = 1L;

        private String KullanıcıAdi;
        private String Mesaj;
        private String TarihZaman;

        public String getKullanıcıAdi(){
            return KullanıcıAdi;
        }
        public void setKullanıcıAdi(String KullanıcıAdi){
            this.KullanıcıAdi =KullanıcıAdi;
        }
        public String getMesaj(){
            return  Mesaj;
        }
        public void setMesaj(String Mesaj){
            this.Mesaj = Mesaj;
        }
        public String getTarihZaman(){
            return  TarihZaman;
        }
        public void setTarihZaman(String TarihZaman){
            this.TarihZaman = TarihZaman;
        }

    }

