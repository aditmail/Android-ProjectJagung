package com.example.aditmail.projectjagung;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;

import android.text.TextUtils;
import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.aditmail.projectjagung.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class UpdateDataPupukBenih extends AppCompatActivity {

    public static String tanggal_input;

    EditText updateKuantitasPupuk, updateHargaPupuk, updateKuantitasBenih, updateHargaBenih, updateNamaPestisida,
            updateKuantitasPestisida, updateHargaPestisida, updateTitikTanam, updateKeterangan, updatePeriode, updateTotalHarga, lihatNamaPupuk, lihatNamaBenih;
    EditText tanggal_input_pupuk;

    TextView editPupuk;

    Spinner updateJenisPupuk, updateJenisBenih;
    Button btnUpdatePupuk, btnHapusPupuk;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = UpdateDataPupukBenih.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String urlUpdateDataPupukBenih = Konfigurasi.URL_UPDATE_DATA_PUPUK_BENIH;
    private String urlHapusDataPupukBenih = Konfigurasi.URL_HAPUS_DATA_PUPUK_BENIH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_pupuk);

        Intent intent = getIntent();

        tanggal_input = intent.getStringExtra(Konfigurasi.KEY_TANGGAL_INPUT_PUPUK);

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

        tanggal_input_pupuk = (EditText)findViewById(R.id.editText_tanggalInput);
        tanggal_input_pupuk.setText(tanggal_input);

        editPupuk = (TextView) findViewById(R.id.textView_editPupuk);

        lihatNamaBenih = (EditText) findViewById(R.id.editText_lihatNamaBenih);
        lihatNamaPupuk = (EditText) findViewById(R.id.editText_lihatNamaPupuk);

        updateKuantitasPupuk = (EditText) findViewById(R.id.editText_inputKuantitasPupuk);
        updateHargaPupuk = (EditText) findViewById(R.id.editText_inputHargaPupuk);
        updateKuantitasBenih = (EditText) findViewById(R.id.editText_inputKuantitasBenih);
        updateHargaBenih = (EditText) findViewById(R.id.editText_inputHargaBenih);

        updateNamaPestisida = (EditText) findViewById(R.id.editText_inputNamaPestisida);
        updateKuantitasPestisida = (EditText) findViewById(R.id.editText_inputKuantitasPestisida);
        updateHargaPestisida = (EditText) findViewById(R.id.editText_inputHargaPestisida);
        updateTitikTanam = (EditText) findViewById(R.id.editText_inputTitikTanam);
        updateKeterangan = (EditText) findViewById(R.id.editText_inputKeterangan);
        updatePeriode = (EditText) findViewById(R.id.editText_inputPeriode);
        updateTotalHarga = (EditText) findViewById(R.id.editText_TotalHarga);

        updateJenisPupuk = (Spinner) findViewById(R.id.spinner_inputJenisPupuk);
        updateJenisBenih = (Spinner) findViewById(R.id.spinner_inputJenisBenih);

        btnUpdatePupuk = (Button) findViewById(R.id.btnSimpanPupukBenih);
        btnHapusPupuk = (Button) findViewById(R.id.btnHapusPupuk);

        updateTotalHarga.setEnabled(false);

        lihatNamaBenih.setFocusableInTouchMode(false);
        lihatNamaPupuk.setFocusableInTouchMode(false);

        updateKuantitasPupuk.setFocusableInTouchMode(false);
        updateHargaPupuk.setEnabled(false);
        updateKuantitasBenih.setFocusableInTouchMode(false);
        updateHargaBenih.setEnabled(false);
        updateNamaPestisida.setFocusableInTouchMode(false);
        updateKuantitasPestisida.setFocusableInTouchMode(false);
        updateHargaPestisida.setEnabled(false);
        updateTitikTanam.setEnabled(false);
        updateKeterangan.setEnabled(false);
        updatePeriode.setEnabled(false);

        updateJenisBenih.setEnabled(false);
        updateJenisPupuk.setEnabled(false);

        getDataPupukBenih();

        editPupuk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                updateKuantitasPupuk.setFocusableInTouchMode(true);
                updateHargaPupuk.setEnabled(true);
                updateKuantitasBenih.setFocusableInTouchMode(true);
                updateHargaBenih.setEnabled(true);
                updateNamaPestisida.setFocusableInTouchMode(true);
                updateKuantitasPestisida.setFocusableInTouchMode(true);
                updateHargaPestisida.setEnabled(true);
                updateTitikTanam.setEnabled(true);
                updateKeterangan.setEnabled(true);
                updatePeriode.setEnabled(false);

                lihatNamaBenih.setVisibility(View.GONE);
                lihatNamaPupuk.setVisibility(View.GONE);

                updateJenisBenih.setVisibility(View.VISIBLE);
                updateJenisPupuk.setVisibility(View.VISIBLE);

                updateJenisBenih.setEnabled(true);
                updateJenisPupuk.setEnabled(true);

                updatePeriode.setEnabled(true);

                btnUpdatePupuk.setVisibility(View.VISIBLE);
                editPupuk.setVisibility(View.GONE);

            }
        });


        btnUpdatePupuk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String simpanKuantitasPupuk = updateKuantitasPupuk.getText().toString();
                final String simpanHargaPupuk = updateHargaPupuk.getText().toString();

                final String simpanKuantitasBenih = updateKuantitasBenih.getText().toString();
                final String simpanHargaBenih = updateHargaBenih.getText().toString();

                final String simpanNamaPestisida = updateNamaPestisida.getText().toString();
                final String simpanKuantitasPestisida = updateKuantitasPestisida.getText().toString();
                final String simpanHargaPestisida = updateHargaPestisida.getText().toString();

                final String simpanTitikTanam = updateTitikTanam.getText().toString();
                final String simpanKeterangan = updateKeterangan.getText().toString();
                final String simpanPeriode = updatePeriode.getText().toString();

                Kalkulasi();
                final String simpanTotalHarga = updateTotalHarga.getText().toString();

                final String simpanJenisPupuk = updateJenisPupuk.getSelectedItem().toString();
                final String simpanJenisBenih = updateJenisBenih.getSelectedItem().toString();

                if (simpanKuantitasPupuk.trim().length() > 0 && simpanHargaPupuk.trim().length() > 0 && simpanKuantitasBenih.trim().length() > 0 &&
                        simpanHargaBenih.trim().length() > 0 && simpanNamaPestisida.trim().length() > 0 && simpanKuantitasPestisida.trim().length() > 0 &&
                        simpanHargaPestisida.trim().length() > 0 && simpanTitikTanam.trim().length() > 0 && simpanKeterangan.trim().length() > 0 &&
                        simpanPeriode.trim().length() > 0 && simpanTotalHarga.trim().length() > 0) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateDataPupukBenih.this);
                        alertDialogBuilder.setMessage("Apakah Anda Yakin Memperbaharui Hasil Panen?");

                        alertDialogBuilder.setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        updateDataPupukBenih(simpanKuantitasPupuk, simpanHargaPupuk, simpanKuantitasBenih, simpanHargaBenih, simpanNamaPestisida, simpanKuantitasPestisida,
                                                simpanHargaPestisida, simpanTitikTanam, simpanKeterangan, simpanPeriode, simpanTotalHarga, simpanJenisPupuk, simpanJenisBenih);

                                    }
                                });

                        alertDialogBuilder.setNegativeButton("Tidak",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(simpanKuantitasPupuk)) {
                        updateKuantitasPupuk.setError("Harap Masukkan Data Pupuk");
                        updateKuantitasPupuk.requestFocus();
                        return;
                    }
                    if (simpanKuantitasPupuk.contains(" ")) {
                        updateKuantitasPupuk.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updateKuantitasPupuk.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaPupuk)) {
                        updateHargaPupuk.setError("Harap Masukkan Data Pupuk");
                        updateHargaPupuk.requestFocus();
                        return;
                    }
                    if (simpanHargaPupuk.contains(" ")) {
                        updateHargaPupuk.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updateHargaPupuk.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKuantitasBenih)) {
                        updateKuantitasBenih.setError("Harap Masukkan Data Benih");
                        updateKuantitasBenih.requestFocus();
                        return;
                    }
                    if (simpanKuantitasBenih.contains(" ")) {
                        updateKuantitasBenih.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updateKuantitasBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaBenih)) {
                        updateHargaBenih.setError("Harap Masukkan Data Benih");
                        updateHargaBenih.requestFocus();
                        return;
                    }
                    if (simpanHargaBenih.contains(" ")) {
                        updateHargaBenih.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updateHargaBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanNamaPestisida)) {
                        updateNamaPestisida.setError("Harap Masukkan Data Pestisida");
                        updateNamaPestisida.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKuantitasPestisida)) {
                        updateKuantitasPestisida.setError("Harap Masukkan Data Pestisida");
                        updateKuantitasPestisida.requestFocus();
                        return;
                    }
                    if (simpanKuantitasPestisida.contains(" ")) {
                        updateKuantitasPestisida.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updateKuantitasPestisida.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanHargaPestisida)) {
                        updateHargaPestisida.setError("Harap Masukkan Data Pestisida");
                        updateHargaPestisida.requestFocus();
                        return;
                    }
                    if (simpanHargaPestisida.contains(" ")) {
                        updateHargaPestisida.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updateHargaPestisida.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanTitikTanam)) {
                        updateTitikTanam.setError("Harap Masukkan Data Titik Tanam");
                        updateTitikTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(simpanKeterangan)) {
                        updateKeterangan.setError("Harap Masukkan Data Keterangan");
                        updateKeterangan.requestFocus();
                        return;

                    }
                    if (TextUtils.isEmpty(simpanPeriode)) {
                        updatePeriode.setError("Harap Masukkan Data Periode");
                        updatePeriode.requestFocus();
                    }
                    if (simpanPeriode.contains(" ")) {
                        updatePeriode.setError("Tanda Spasi Tidak Boleh Digunakan");
                        updatePeriode.requestFocus();
                    }
                }
            }
        });

        btnHapusPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateDataPupukBenih.this);
                alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data Panen?");

                //jika iya maka akan menjalankan resetTransaksi
                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                hapusDataPupuk();
                            }
                        });

                //jika tidak akan kembali ke menu / tidak terjadi apa-apa
                alertDialogBuilder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }

        });

    }

    private void hapusDataPupuk(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlHapusDataPupukBenih, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        Log.e("Berhasil Menghapus!", jObj.toString());

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
                Log.e(TAG, "Gagal Menghapus. " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put(Konfigurasi.KEY_ID_PUPUK, LihatDataPupukBenih.pupuk_id);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateDataPupukBenih (final String simpanKuantitasPupuk, final String simpanHargaPupuk, final String simpanKuantitasBenih, final String simpanHargaBenih,
                                      final String simpanNamaPestisida, final String simpanKuantitasPestisida, final String simpanHargaPestisida, final String simpanTitikTanam,
                                      final String simpanKeterangan, final String simpanPeriode, final String simpanTotalHarga, final String simpanJenisPupuk, final String simpanJenisBenih){

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlUpdateDataPupukBenih, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_ID_PUPUK, LihatDataPupukBenih.pupuk_id);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void Kalkulasi (){
        double valKuantitasPupuk = Double.parseDouble(updateKuantitasPupuk.getText().toString());
        double valHargaPupuk = Double.parseDouble(updateHargaPupuk.getText().toString());
        double valKuantitasBenih = Double.parseDouble(updateKuantitasBenih.getText().toString());
        double valHargaBenih = Double.parseDouble(updateHargaBenih.getText().toString());
        double valKuantitasPestisida = Double.parseDouble(updateKuantitasPestisida.getText().toString());
        double valHargaPestisida = Double.parseDouble(updateHargaPestisida.getText().toString());

        //Proses kalkulasi

        updateTotalHarga.setText(Double.toString((valKuantitasPupuk * valHargaPupuk) + (valKuantitasBenih * valHargaBenih) +
                (valKuantitasPestisida * valHargaPestisida)));
    }

    private void getDataPupukBenih(){
        class GetDataHasilPanen extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDataPupukBenih.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDataPupukBenih(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCoba rh = new RequestHandlerCoba();

                String s = rh.sendGetRequestParam2(Konfigurasi.URL_TAMPIL_DATA_PUPUK_BENIH, LihatDataPupukBenih.pupuk_id);
                return s;
            }
        }
        GetDataHasilPanen ge = new GetDataHasilPanen();
        ge.execute();
    }

    private void showDataPupukBenih(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String pilihanPupuk = c.getString(Konfigurasi.TAG_JENIS_PUPUK);
            String kuantitasPupuk = c.getString(Konfigurasi.TAG_KUANTITAS_PUPUK);
            String hargaPupuk = c.getString(Konfigurasi.TAG_HARGA_PUPUK);

            String pilihanBenih = c.getString(Konfigurasi.TAG_JENIS_BENIH);;
            String kuantitasBenih = c.getString(Konfigurasi.TAG_KUANTITAS_BENIH);
            String hargaBenih = c.getString(Konfigurasi.TAG_HARGA_BENIH);
            String namaPestisida = c.getString(Konfigurasi.TAG_NAMA_PESTISIDA);

            String kuantitasPestisida = c.getString(Konfigurasi.TAG_KUANTITAS_PESTISIDA);
            String hargaPestisida = c.getString(Konfigurasi.TAG_HARGA_PESTISIDA);
            String titikTanam = c.getString(Konfigurasi.TAG_TITIK_TANAM_PUPUK);
            String keteranganTanam = c.getString(Konfigurasi.TAG_KETERANGAN_TANAM);
            String periode = c.getString(Konfigurasi.TAG_PERIODE);
            String totalKeseluruhan = c.getString(Konfigurasi.TAG_TOTAL_HARGA);

            lihatNamaPupuk.setText(pilihanPupuk);
            updateKuantitasPupuk.setText(kuantitasPupuk);
            updateHargaPupuk.setText(hargaPupuk);

            lihatNamaBenih.setText(pilihanBenih);
            updateKuantitasBenih.setText(kuantitasBenih);
            updateHargaBenih.setText(hargaBenih);

            updateNamaPestisida.setText(namaPestisida);
            updateKuantitasPestisida.setText(kuantitasPestisida);
            updateHargaPestisida.setText(hargaPestisida);

            updateTitikTanam.setText(titikTanam);
            updateKeterangan.setText(keteranganTanam);
            updatePeriode.setText(periode);
            updateTotalHarga.setText(totalKeseluruhan);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateDataPupukBenih.this, LihatDataPupukBenih.class);
        finish();
        startActivity(intent);

    }
}
