package com.example.yenen.agprogramlamaproje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public  class Login extends AppCompatActivity    {
EditText Kullaniciadi;
    Button btnGiris;

    @Override
    protected   void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Kullaniciadi = (EditText)findViewById(R.id.editText5);
        btnGiris = (Button)findViewById(R.id.button2);
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApi.Kullanici_Adi = Kullaniciadi.getText().toString().trim();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }


}
