package com.example.aditmail.projectjagung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CariPeriode extends AppCompatActivity {

    private EditText cariPeriode;
    private Button btnCariPeriode;

    public final static String CARIPERIODE = "com.example.aditmail.projectjagung.PERIODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_periode);

        cariPeriode = (EditText) findViewById(R.id.editText_Periode);
        btnCariPeriode = (Button) findViewById(R.id.btnCariPeriode);

        btnCariPeriode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CariPeriode.this, KalkulasiTotal.class);
                String periode = cariPeriode.getText().toString();
                intent.putExtra(CARIPERIODE, periode);
                startActivity(intent);
            }
        });



    }
}
