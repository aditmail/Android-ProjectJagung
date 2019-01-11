package com.example.aditmail.projectjagung;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;

import android.text.TextUtils;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.aditmail.projectjagung.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InputHasilPanen extends AppCompatActivity {

    EditText inputHasilBenih, inputKuantitasHasilPanen, inputHargaPanen, inputPeriode, totalNilaiPanen;
    Button btnSimpanHasilPanen;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = InputHasilPanen.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String urlDataHasilPanen = Konfigurasi.URL_INPUT_DATA_HASIL_PANEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_hasil_panen);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        inputHasilBenih = (EditText) findViewById(R.id.editText_inputHasilBenih);
        inputKuantitasHasilPanen = (EditText) findViewById(R.id.editText_inputKuantitasHasil);
        inputHargaPanen = (EditText) findViewById(R.id.editText_inputHargaPanen);
        inputPeriode = (EditText) findViewById(R.id.editText_inputPeriode);
        totalNilaiPanen = (EditText) findViewById(R.id.editText_TotalHarga);

        btnSimpanHasilPanen = (Button) findViewById(R.id.btnSimpanHasilPanen);

        totalNilaiPanen.setEnabled(false);

        btnSimpanHasilPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String simpanHasilBenih = inputHasilBenih.getText().toString();
                String simpanKuantitasHasilPanen = inputKuantitasHasilPanen.getText().toString();
                String simpanPeriode = inputPeriode.getText().toString();

                String simpanHargaPanen = inputHargaPanen.getText().toString();

                if (simpanHasilBenih.trim().length() > 0 && simpanKuantitasHasilPanen.trim().length() > 0 && simpanHargaPanen.trim().length() > 0 &&
                        simpanPeriode.trim().length() > 0) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        Kalkulasi();
                        String simpanTotalHargaPanen = totalNilaiPanen.getText().toString();

                        checkDataHasilPanen(simpanHasilBenih, simpanKuantitasHasilPanen, simpanHargaPanen, simpanPeriode, simpanTotalHargaPanen);
                        Toast.makeText(InputHasilPanen.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(simpanHasilBenih)) {
                        inputHasilBenih.setError("Harap Masukkan Data Hasil Benih");
                        inputHasilBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKuantitasHasilPanen)) {
                        inputKuantitasHasilPanen.setError("Harap Masukkan Data Kuantitas Hasil Panen");
                        inputKuantitasHasilPanen.requestFocus();
                        return;
                    }
                    if (simpanKuantitasHasilPanen.contains(" ")) {
                        inputKuantitasHasilPanen.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputKuantitasHasilPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaPanen)) {
                        inputHargaPanen.setError("Harap Masukkan Data Harga Hasil Panen");
                        inputHargaPanen.requestFocus();
                        return;
                    }
                    if (simpanHargaPanen.contains(" ")) {
                        inputHargaPanen.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputHargaPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanPeriode)) {
                        inputPeriode.setError("Harap Masukkan Data Periode");
                        inputPeriode.requestFocus();
                        return;
                    }
                    if (simpanPeriode.contains(" ")) {
                        inputPeriode.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputPeriode.requestFocus();
                        return;
                    }

                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //manggil method createmenu
        CreateMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu) {
        menu.add(0, 0, 0, "Data Hasil Panen");
    }

    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent0 = new Intent(this, LihatDataHasilPanenBaru.class);
                this.startActivity(intent0);
                return true;
        }
        return false;
    }

    private void checkDataHasilPanen (final String simpanHasilBenih, final String simpanKuantitasHasilPanen, final String simpanHargaPanen, final String simpanPeriode,
                                      final String simpanTotalHargaPanen){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlDataHasilPanen, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_REG_HASIL_BENIH, simpanHasilBenih);
                params.put(Konfigurasi.KEY_REG_KUANTITAS_HASIL_PANEN, simpanKuantitasHasilPanen);
                params.put(Konfigurasi.KEY_REG_HARGA_PANEN, simpanHargaPanen);
                params.put(Konfigurasi.KEY_REG_PERIODE, simpanPeriode);
                params.put(Konfigurasi.KEY_REG_TOTAL_HARGA_PANEN, simpanTotalHargaPanen);
                params.put(Konfigurasi.KEY_REG_ID, MainActivity.id_pelanggan);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void Kalkulasi () {
        double valKuantitasHasilPanen = Double.parseDouble(inputKuantitasHasilPanen.getText().toString());
        double valHargaPanen = Double.parseDouble(inputHargaPanen.getText().toString());

        totalNilaiPanen.setText(Double.toString(valKuantitasHasilPanen * valHargaPanen));
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
