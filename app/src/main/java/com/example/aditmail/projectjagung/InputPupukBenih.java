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

import android.widget.Spinner;
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

public class InputPupukBenih extends AppCompatActivity {

    EditText inputKuantitasPupuk, inputHargaPupuk, inputKuantitasBenih, inputHargaBenih, inputNamaPestisida,
            inputKuantitasPestisida, inputHargaPestisida, inputTitikTanam, inputKeterangan, inputPeriode, totalHarga;

    Spinner inputJenisPupuk, inputJenisBenih;
    Button btnSimpanPupukBenih;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = InputPupukBenih.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String urlPupukBenihInput = Konfigurasi.URL_INPUT_PUPUK_BENIH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pupuk_benih);

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

        inputKuantitasPupuk = (EditText)findViewById(R.id.editText_inputKuantitasPupuk);
        inputHargaPupuk = (EditText)findViewById(R.id.editText_inputHargaPupuk);
        inputKuantitasBenih = (EditText)findViewById(R.id.editText_inputKuantitasBenih);
        inputHargaBenih = (EditText)findViewById(R.id.editText_inputHargaBenih);
        inputNamaPestisida = (EditText)findViewById(R.id.editText_inputNamaPestisida);
        inputKuantitasPestisida = (EditText)findViewById(R.id.editText_inputKuantitasPestisida);
        inputHargaPestisida = (EditText)findViewById(R.id.editText_inputHargaPestisida);
        inputTitikTanam = (EditText)findViewById(R.id.editText_inputTitikTanam);
        inputKeterangan = (EditText)findViewById(R.id.editText_inputKeterangan);
        inputPeriode = (EditText)findViewById(R.id.editText_inputPeriode);
        totalHarga = (EditText)findViewById(R.id.editText_TotalHarga);

        inputJenisPupuk = (Spinner)findViewById(R.id.spinner_inputJenisPupuk);
        inputJenisBenih = (Spinner)findViewById(R.id.spinner_inputJenisBenih);

        btnSimpanPupukBenih = (Button)findViewById(R.id.btnSimpanPupukBenih);

        totalHarga.setEnabled(false);

        btnSimpanPupukBenih.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String simpanKuantitasPupuk = inputKuantitasPupuk.getText().toString();
                String simpanHargaPupuk = inputHargaPupuk.getText().toString();

                String simpanKuantitasBenih = inputKuantitasBenih.getText().toString();
                String simpanHargaBenih = inputHargaBenih.getText().toString();

                String simpanNamaPestisida = inputNamaPestisida.getText().toString();
                String simpanKuantitasPestisida = inputKuantitasPestisida.getText().toString();
                String simpanHargaPestisida = inputHargaPestisida.getText().toString();

                String simpanTitikTanam = inputTitikTanam.getText().toString();
                String simpanKeterangan = inputKeterangan.getText().toString();

                String simpanPeriode = inputPeriode.getText().toString();

                String simpanJenisPupuk = inputJenisPupuk.getSelectedItem().toString();
                String simpanJenisBenih = inputJenisBenih.getSelectedItem().toString();


                if (simpanKuantitasPupuk.trim().length() > 0 && simpanHargaPupuk.trim().length() > 0 && simpanKuantitasBenih.trim().length() > 0 &&
                        simpanHargaBenih.trim().length() > 0 && simpanNamaPestisida.trim().length() > 0 && simpanKuantitasPestisida.trim().length() > 0 &&
                        simpanHargaPestisida.trim().length() > 0 && simpanTitikTanam.trim().length() > 0 && simpanKeterangan.trim().length() > 0 && simpanPeriode.trim().length() >0) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        Kalkulasi();
                        String simpanTotalHarga = totalHarga.getText().toString();

                        checkDataPupukBenih(simpanKuantitasPupuk, simpanHargaPupuk, simpanKuantitasBenih, simpanHargaBenih, simpanNamaPestisida, simpanKuantitasPestisida,
                                simpanHargaPestisida, simpanTitikTanam, simpanKeterangan, simpanPeriode, simpanTotalHarga, simpanJenisPupuk, simpanJenisBenih);
                        Toast.makeText(InputPupukBenih.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(simpanKuantitasPupuk)) {
                        inputKuantitasPupuk.setError("Harap Masukkan Data Pupuk");
                        inputKuantitasPupuk.requestFocus();
                        return;
                    }
                    if (simpanKuantitasPupuk.contains(" ")) {
                        inputKuantitasPupuk.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputKuantitasPupuk.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaPupuk)) {
                        inputHargaPupuk.setError("Harap Masukkan Data Pupuk");
                        inputHargaPupuk.requestFocus();
                        return;
                    }
                    if (simpanHargaPupuk.contains(" ")) {
                        inputHargaPupuk.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputHargaPupuk.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKuantitasBenih)) {
                        inputKuantitasBenih.setError("Harap Masukkan Data Benih");
                        inputKuantitasBenih.requestFocus();
                        return;
                    }
                    if (simpanKuantitasBenih.contains(" ")) {
                        inputKuantitasBenih.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputKuantitasBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaBenih)) {
                        inputHargaBenih.setError("Harap Masukkan Data Benih");
                        inputHargaBenih.requestFocus();
                        return;
                    }
                    if (simpanHargaBenih.contains(" ")) {
                        inputHargaBenih.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputHargaBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanNamaPestisida)) {
                        inputNamaPestisida.setError("Harap Masukkan Data Pestisida");
                        inputNamaPestisida.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKuantitasPestisida)) {
                        inputKuantitasPestisida.setError("Harap Masukkan Data Pestisida");
                        inputKuantitasPestisida.requestFocus();
                        return;
                    }
                    if (simpanKuantitasPestisida.contains(" ")) {
                        inputKuantitasPestisida.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputKuantitasPestisida.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaPestisida)) {
                        inputHargaPestisida.setError("Harap Masukkan Data Pestisida");
                        inputHargaPestisida.requestFocus();
                        return;
                    }
                    if (simpanHargaPestisida.contains(" ")) {
                        inputHargaPestisida.setError("Tanda Spasi Tidak Boleh Digunakan");
                        inputHargaPestisida.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanTitikTanam)) {
                        inputTitikTanam.setError("Harap Masukkan Data Titik Tanam");
                        inputTitikTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKeterangan)) {
                        inputKeterangan.setError("Harap Masukkan Data Keterangan");
                        inputKeterangan.requestFocus();
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
        menu.add(0, 0, 0, "Data Pupuk dan Benih");
    }

    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent0 = new Intent(this, LihatDataPupukBenih.class);
                this.startActivity(intent0);
                return true;
        }
        return false;
    }

    private void checkDataPupukBenih (final String simpanKuantitasPupuk, final String simpanHargaPupuk, final String simpanKuantitasBenih, final String simpanHargaBenih,
                                      final String simpanNamaPestisida, final String simpanKuantitasPestisida, final String simpanHargaPestisida, final String simpanTitikTanam,
                                      final String simpanKeterangan, final String simpanPeriode, final String simpanTotalHarga, final String simpanJenisPupuk, final String simpanJenisBenih){

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlPupukBenihInput, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_REG_JENIS_PUPUK, simpanJenisPupuk);
                params.put(Konfigurasi.KEY_REG_KUANTITAS_PUPUK, simpanKuantitasPupuk);
                params.put(Konfigurasi.KEY_REG_HARGA_PUPUK, simpanHargaPupuk);
                params.put(Konfigurasi.KEY_REG_JENIS_BENIH, simpanJenisBenih);
                params.put(Konfigurasi.KEY_REG_KUANTITAS_BENIH, simpanKuantitasBenih);
                params.put(Konfigurasi.KEY_REG_HARGA_BENIH, simpanHargaBenih);
                params.put(Konfigurasi.KEY_REG_NAMA_PESTISIDA, simpanNamaPestisida);
                params.put(Konfigurasi.KEY_REG_KUANTITAS_PESTISIDA, simpanKuantitasPestisida);
                params.put(Konfigurasi.KEY_REG_HARGA_PESTISIDA, simpanHargaPestisida);
                params.put(Konfigurasi.KEY_REG_TITIK_TANAM_PUPUK, simpanTitikTanam);
                params.put(Konfigurasi.KEY_REG_KETERANGAN_TANAM, simpanKeterangan);
                params.put(Konfigurasi.KEY_REG_PERIODE, simpanPeriode);
                params.put(Konfigurasi.KEY_REG_TOTAL_HARGA, simpanTotalHarga);
                params.put(Konfigurasi.KEY_REG_ID, MainActivity.id_pelanggan);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void Kalkulasi (){
        double valKuantitasPupuk = Double.parseDouble(inputKuantitasPupuk.getText().toString());
        double valHargaPupuk = Double.parseDouble(inputHargaPupuk.getText().toString());
        double valKuantitasBenih = Double.parseDouble(inputKuantitasBenih.getText().toString());
        double valHargaBenih = Double.parseDouble(inputHargaBenih.getText().toString());
        double valKuantitasPestisida = Double.parseDouble(inputKuantitasPestisida.getText().toString());
        double valHargaPestisida = Double.parseDouble(inputHargaPestisida.getText().toString());

        //Proses kalkulasi

        totalHarga.setText(Double.toString((valKuantitasPupuk * valHargaPupuk) + (valKuantitasBenih * valHargaBenih) +
                (valKuantitasPestisida * valHargaPestisida)));
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
