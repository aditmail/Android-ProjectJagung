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


public class UpdateDataHasilPanenBaru extends AppCompatActivity {

    public static String tanggal_input;

    EditText updateHasilBenih, updateKuantitasHasilPanen, updateHargaPanen, updatePeriode, updateTotalNilaiPanen, tanggalInput;
    Button btnUpdateHasilPanen, btnHapusHasilPanen;

    TextView editHasilPanen;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = InputHasilPanen.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String urlUpdateHasilPanen = Konfigurasi.URL_UPDATE_DATA_HASIL_PANEN_BARU;
    private String urlHapusPanen = Konfigurasi.URL_HAPUS_DATA_PANEN_BARU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_hasil_panen_baru);

        Intent intent = getIntent();

        tanggal_input = intent.getStringExtra(Konfigurasi.KEY_TANGGAL_INPUT_PANEN);

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

        tanggalInput = (EditText) findViewById(R.id.editText_tanggalInput);
        tanggalInput.setText(tanggal_input);

        updateHasilBenih = (EditText) findViewById(R.id.editText_inputHasilBenih);
        updateKuantitasHasilPanen = (EditText) findViewById(R.id.editText_inputKuantitasHasil);
        updateHargaPanen = (EditText) findViewById(R.id.editText_inputHargaPanen);

        updatePeriode = (EditText) findViewById(R.id.editText_inputPeriode);
        updateTotalNilaiPanen = (EditText) findViewById(R.id.editText_TotalHarga);

        editHasilPanen = (TextView) findViewById(R.id.textView_editHasilPanen);
        btnHapusHasilPanen = (Button) findViewById(R.id.btnHapusHasilPanen);
        btnUpdateHasilPanen = (Button) findViewById(R.id.btnSimpanHasilPanenBaru);

        updateTotalNilaiPanen.setEnabled(false);
        tanggalInput.setEnabled(false);

        getDataHasilPanen();

        updateHasilBenih.setFocusableInTouchMode(false);
        updateKuantitasHasilPanen.setFocusableInTouchMode(false);
        updateHargaPanen.setEnabled(false);
        updateTotalNilaiPanen.setEnabled(false);
        updatePeriode.setEnabled(false);

        editHasilPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateHasilBenih.setFocusableInTouchMode(true);
                updateKuantitasHasilPanen.setFocusableInTouchMode(true);
                updateHargaPanen.setEnabled(true);
                updatePeriode.setEnabled(true);

                btnUpdateHasilPanen.setVisibility(View.VISIBLE);
                editHasilPanen.setVisibility(View.GONE);
            }
        });

        btnUpdateHasilPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String simpanHasilBenih = updateHasilBenih.getText().toString();
                final String simpanKuantitasHasilPanen = updateKuantitasHasilPanen.getText().toString();
                final String simpanHargaPanen = updateHargaPanen.getText().toString();
                final String simpanPeriode = updatePeriode.getText().toString();

                if (simpanHasilBenih.trim().length() > 1 && simpanKuantitasHasilPanen.trim().length() > 1 && simpanHargaPanen.trim().length() > 1 ) {

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {

                    Kalkulasi();
                    final String simpanTotalHargaPanen = updateTotalNilaiPanen.getText().toString();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateDataHasilPanenBaru.this);
                    alertDialogBuilder.setMessage("Apakah Anda Yakin Memperbaharui Hasil Panen?");

                    alertDialogBuilder.setPositiveButton("Ya",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    updateDataHasilPanen(simpanHasilBenih, simpanKuantitasHasilPanen, simpanHargaPanen, simpanPeriode, simpanTotalHargaPanen);
                                    Toast.makeText(UpdateDataHasilPanenBaru.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
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

                } else{
                        //Jika yang diisi kosong.. maka akan muncul notifikasi error
                        if (TextUtils.isEmpty(simpanHasilBenih)) {
                            updateHasilBenih.setError("Harap Masukkan Data Hasil Benih");
                            updateHasilBenih.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(simpanKuantitasHasilPanen)) {
                            updateKuantitasHasilPanen.setError("Harap Masukkan Data Kuantitas Hasil Panen");
                            updateKuantitasHasilPanen.requestFocus();
                            return;
                        }
                        if (simpanKuantitasHasilPanen.contains(" ")) {
                            updateKuantitasHasilPanen.setError("Tanda Spasi Tidak Boleh Digunakan");
                            updateKuantitasHasilPanen.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(simpanHargaPanen)) {
                            updateHargaPanen.setError("Harap Masukkan Data Harga Hasil Panen");
                            updateHargaPanen.requestFocus();
                            return;
                        }
                        if (simpanHargaPanen.contains(" ")) {
                            updateHargaPanen.setError("Tanda Spasi Tidak Boleh Digunakan");
                            updateHargaPanen.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(simpanPeriode)) {
                            updatePeriode.setError("Harap Masukkan Data Periode");
                            updatePeriode.requestFocus();
                            return;
                        }
                        if (simpanPeriode.contains(" ")) {
                            updatePeriode.setError("Tanda Spasi Tidak Boleh Digunakan");
                            updatePeriode.requestFocus();

                        }
                    }
                }


        });

        btnHapusHasilPanen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateDataHasilPanenBaru.this);
                alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data Panen?");

                //jika iya maka akan menjalankan resetTransaksi
                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                hapusDataPanen();
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

    private void hapusDataPanen(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlHapusPanen, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_ID_PANEN, LihatDataHasilPanenBaru.panen_ID);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateDataHasilPanen (final String simpanHasilBenih, final String simpanKuantitasHasilPanen, final String simpanHargaPanen, final String simpanPeriode,
                                       final String simpanTotalHargaPanen){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlUpdateHasilPanen, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_REG_ID, LihatDataHasilPanenBaru.panen_ID);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void getDataHasilPanen(){
        class GetDataHasilPanen extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDataHasilPanenBaru.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDataHasilPanen(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCoba rh = new RequestHandlerCoba();

                String s = rh.sendGetRequestParam3(Konfigurasi.URL_TAMPIL_DATA_HASIL_PANEN_BARU, LihatDataHasilPanenBaru.panen_ID);
                return s;
            }
        }
        GetDataHasilPanen ge = new GetDataHasilPanen();
        ge.execute();
    }

    private void showDataHasilPanen(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String hasilBenih = c.getString(Konfigurasi.KEY_HASIL_BENIH);
            String kuantitasHasilPanen = c.getString(Konfigurasi.KEY_KUANTITAS_HASIL_PANEN);
            String hargaSatuanPanen = c.getString(Konfigurasi.KEY_HARGA_PANEN);
            String periode = c.getString(Konfigurasi.KEY_PERIODE);
            String grandTotal = c.getString(Konfigurasi.KEY_TOTAL_HARGA_PANEN);

            updateHasilBenih.setText(hasilBenih);
            updateKuantitasHasilPanen.setText(kuantitasHasilPanen);
            updateHargaPanen.setText(hargaSatuanPanen);
            updatePeriode.setText(periode);
            updateTotalNilaiPanen.setText(grandTotal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Kalkulasi () {
        double valKuantitasHasilPanen = Double.parseDouble(updateKuantitasHasilPanen.getText().toString());
        double valHargaPanen = Double.parseDouble(updateHargaPanen.getText().toString());

        updateTotalNilaiPanen.setText(Double.toString(valKuantitasHasilPanen * valHargaPanen));
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UpdateDataHasilPanenBaru.this, LihatDataHasilPanenBaru.class);
        finish();
        startActivity(intent);

    }
}
