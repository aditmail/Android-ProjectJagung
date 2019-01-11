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
import android.app.DatePickerDialog;

import android.widget.DatePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.aditmail.projectjagung.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateHasilPanen extends AppCompatActivity {

    public static String tanggal_input;

    EditText editText_updateTanggalDimulaiPanen;
    EditText editText_updateTanggalBerakhirPanen;

    TextView txtView_editPanen;
    EditText editText_updateWaktuPanen, editText_updateJumlahBeratPanen, editText_updateSortirHasilPanen, editText_updatePenjualanHasilPanen,
            editText_updateKomentarPanen, editText_tanggalInput;

    Spinner spinner_satuanWaktuPanen;
    Button btnUpdatePanen, btnHapusPanen;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = UpdateHasilPanen.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    final Calendar myCalendar = Calendar.getInstance();

    private String urlUpdateHasilPanen = Konfigurasi.URL_UPDATE_HASIL_PANEN;
    private String urlHapusPanen = Konfigurasi.URL_HAPUS_DATA_PANEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hasil_panen);

        Intent intent = getIntent();

        tanggal_input = intent.getStringExtra(Konfigurasi.KEY_TANGGAL_INPUT);

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

        editText_tanggalInput = (EditText)findViewById(R.id.editText_tanggalInput);
        editText_tanggalInput.setText(tanggal_input);

        editText_updateTanggalDimulaiPanen = (EditText)findViewById(R.id.update_tanggalDimulaiPanen);
        editText_updateTanggalBerakhirPanen = (EditText)findViewById(R.id.update_tanggalBerakhirPanen);

        editText_updateWaktuPanen = (EditText)findViewById(R.id.update_waktuPanen);
        editText_updateJumlahBeratPanen = (EditText)findViewById(R.id.update_jumlahBeratPanen);
        editText_updateSortirHasilPanen = (EditText)findViewById(R.id.update_sortirHasilPanen);
        editText_updatePenjualanHasilPanen = (EditText)findViewById(R.id.update_penjualanHasilPanen);
        editText_updateKomentarPanen = (EditText)findViewById(R.id.update_komentarPanen);

        txtView_editPanen = (TextView)findViewById(R.id.textView_editPanen);

        spinner_satuanWaktuPanen = (Spinner)findViewById(R.id.spinner_waktuPanen);
        btnUpdatePanen = (Button)findViewById(R.id.btnPerbaharuiPanen);
        btnHapusPanen = (Button)findViewById(R.id.btnHapusPanen);

        getDataHasilPanen();

        spinner_satuanWaktuPanen.setEnabled(false);
        editText_updateWaktuPanen.setEnabled(false);
        editText_updateTanggalDimulaiPanen.setEnabled(false);
        editText_updateTanggalBerakhirPanen.setEnabled(false);
        editText_updateJumlahBeratPanen.setEnabled(false);
        editText_updateSortirHasilPanen.setEnabled(false);
        editText_updatePenjualanHasilPanen.setEnabled(false);
        editText_updateKomentarPanen.setEnabled(false);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        editText_updateTanggalDimulaiPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateHasilPanen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        editText_updateTanggalBerakhirPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateHasilPanen.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        txtView_editPanen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (editText_updateWaktuPanen.getText().toString().contains("Hari")) {
                    editText_updateWaktuPanen.setText("");
                }
                else if (editText_updateWaktuPanen.getText().toString().contains("Bulan")) {
                    editText_updateWaktuPanen.setText("");
                }
                else if (editText_updateWaktuPanen.getText().toString().contains("Tahun")) {
                    editText_updateWaktuPanen.setText("");
                }


                spinner_satuanWaktuPanen.setEnabled(true);
                editText_updateWaktuPanen.setEnabled(true);
                editText_updateTanggalDimulaiPanen.setEnabled(true);
                editText_updateTanggalBerakhirPanen.setEnabled(true);
                editText_updateJumlahBeratPanen.setEnabled(true);
                editText_updateSortirHasilPanen.setEnabled(true);
                editText_updatePenjualanHasilPanen.setEnabled(true);
                editText_updateKomentarPanen.setEnabled(true);

                btnUpdatePanen.setVisibility(View.VISIBLE);
                txtView_editPanen.setVisibility(View.GONE);

            }
        });

        btnUpdatePanen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final String updateSatuanWaktuPanen = spinner_satuanWaktuPanen.getSelectedItem().toString();
                final String updateWaktuPanen = editText_updateWaktuPanen.getText().toString() + " " + updateSatuanWaktuPanen;

                final String updateTglMulaiPanen = editText_updateTanggalDimulaiPanen.getText().toString();
                final String updateTglBerakhirPanen = editText_updateTanggalBerakhirPanen.getText().toString();
                final String updateJumlahBeratPanen = editText_updateJumlahBeratPanen.getText().toString();
                final String updateSortirHasilPanen = editText_updateSortirHasilPanen.getText().toString();
                final String updatePenjualanHasilPanen = editText_updatePenjualanHasilPanen.getText().toString();
                final String updateKomentarPanen = editText_updateKomentarPanen.getText().toString();

                if (updateWaktuPanen.trim().length() > 0 && updateTglMulaiPanen.trim().length() > 0 && updateTglBerakhirPanen.trim().length() > 0 &&
                        updateJumlahBeratPanen.trim().length() > 0 && updateSortirHasilPanen.trim().length() > 0 && updatePenjualanHasilPanen.trim().length() > 0 &&
                        updateKomentarPanen.trim().length() > 0) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateHasilPanen.this);
                        alertDialogBuilder.setMessage("Apakah Anda Yakin Memperbaharui Hasil Panen?");

                        alertDialogBuilder.setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        updateHasilPanen(updateWaktuPanen, updateTglMulaiPanen, updateTglBerakhirPanen, updateJumlahBeratPanen, updateSortirHasilPanen,
                                                updatePenjualanHasilPanen, updateKomentarPanen);
                                        Toast.makeText(UpdateHasilPanen.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();

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
                    if (TextUtils.isEmpty(updateWaktuPanen)) {
                        editText_updateWaktuPanen.setError("Harap Masukkan Data Waktu Panen");
                        editText_updateWaktuPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateTglMulaiPanen)) {
                        editText_updateTanggalDimulaiPanen.setError("Harap Masukkan Data Tanggal Dimulainya Masa Panen");
                        editText_updateTanggalDimulaiPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateTglBerakhirPanen)) {
                        editText_updateTanggalBerakhirPanen.setError("Harap Masukkan Data Tanggal Berakhirnya Masa Panen");
                        editText_updateTanggalBerakhirPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateJumlahBeratPanen)) {
                        editText_updateJumlahBeratPanen.setError("Harap Masukkan Data Jumlah Berat Hasil Panen");
                        editText_updateJumlahBeratPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateSortirHasilPanen)) {
                        editText_updateSortirHasilPanen.setError("Harap Masukkan Data Sortir Hasil Panen");
                        editText_updateSortirHasilPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updatePenjualanHasilPanen)) {
                        editText_updatePenjualanHasilPanen.setError("Harap Masukkan Data Penjualan Hasil Panen");
                        editText_updatePenjualanHasilPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateKomentarPanen)) {
                        editText_updateKomentarPanen.setError("Harap Masukkan Data Keterangan/Komentar");
                        editText_updateKomentarPanen.requestFocus();
                    }


                }
            }
        });

        btnHapusPanen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateHasilPanen.this);
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
                params.put(Konfigurasi.KEY_ID_PANEN, LihatHasilPanen.panen_id);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateHasilPanen (final String updateWaktuPanen, final String updateTglMulaiPanen, final String updateTglBerakhirPanen, final String updateJumlahBeratPanen,
                                  final String updateSortirHasilPanen, final String updatePenjualanHasilPanen,final String updateKomentarPanen) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlUpdateHasilPanen, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Menyimpan: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Berhasil Memperbaharui!", jObj.toString());

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
                params.put(Konfigurasi.KEY_REG_WAKTU_PANEN, updateWaktuPanen);
                params.put(Konfigurasi.KEY_REG_TGL_MULAI_PANEN, updateTglMulaiPanen);
                params.put(Konfigurasi.KEY_REG_TGL_BERAKHIR_PANEN, updateTglBerakhirPanen);
                params.put(Konfigurasi.KEY_REG_JUMLAH_BERAT_PANEN, updateJumlahBeratPanen);
                params.put(Konfigurasi.KEY_REG_SORTIR_HASIL_PANEN, updateSortirHasilPanen);
                params.put(Konfigurasi.KEY_REG_PENJUALAN_HASIL_PANEN, updatePenjualanHasilPanen);
                params.put(Konfigurasi.KEY_REG_KOMENTAR_PANEN, updateKomentarPanen);
                params.put(Konfigurasi.KEY_ID_PANEN, LihatHasilPanen.panen_id);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText_updateTanggalDimulaiPanen.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText_updateTanggalBerakhirPanen.setText(sdf.format(myCalendar.getTime()));
    }

    private void getDataHasilPanen(){
        class GetDataHasilPanen extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateHasilPanen.this,"Fetching...","Wait...",false,false);
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

                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_DATA_HASIL_PANEN, LihatHasilPanen.panen_id);
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

            String waktuPanen = c.getString(Konfigurasi.TAG_WAKTU_PANEN);
            String tglMulaiPanen = c.getString(Konfigurasi.TAG_TGL_MULAI_PANEN);
            String tglBerakhirPanen = c.getString(Konfigurasi.TAG_TGL_BERAKHIR_PANEN);
            String jumlahBeratPanen = c.getString(Konfigurasi.TAG_JUMLAH_BERAT_PANEN);
            String sortirHasilPanen = c.getString(Konfigurasi.TAG_SORTIR_HASIL_PANEN);
            String penjualanHasilPanen = c.getString(Konfigurasi.TAG_PENJUALAN_HASIL_PANEN);
            String komentarPanen = c.getString(Konfigurasi.TAG_KOMENTAR_PANEN);

            editText_updateWaktuPanen.setText(waktuPanen);
            editText_updateTanggalDimulaiPanen.setText(tglMulaiPanen);
            editText_updateTanggalBerakhirPanen.setText(tglBerakhirPanen);
            editText_updateJumlahBeratPanen.setText(jumlahBeratPanen);
            editText_updateSortirHasilPanen.setText(sortirHasilPanen);
            editText_updatePenjualanHasilPanen.setText(penjualanHasilPanen);
            editText_updateKomentarPanen.setText(komentarPanen);

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


}
