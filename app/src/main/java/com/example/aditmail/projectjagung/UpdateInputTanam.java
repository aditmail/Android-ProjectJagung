package com.example.aditmail.projectjagung;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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

public class UpdateInputTanam extends AppCompatActivity {

    public static String tanggal_input;

    TextView txtView_editTanam;
    EditText editText_updateStokBenihTanam, editText_updateWaktuTanam, editText_updateTglMulaiTanam, editText_updateTglBerakhirTanam,
            editText_updatePenyebaranBenihTanam, editText_updateTitikTanam, editText_updateKomentarTanam;

    EditText editText_tanggalInputTanam;

    Spinner spinner_satuanWaktuTanam;
    Button btnUpdateTanam, btnHapusTanam;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = UpdateInputTanam.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    final Calendar myCalendar = Calendar.getInstance();

    private String urlUpdateHasilTanam = Konfigurasi.URL_UPDATE_DATA_TANAM;
    private String urlHapusTanam = Konfigurasi.URL_HAPUS_DATA_TANAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_input_tanam);

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

        editText_tanggalInputTanam = (EditText)findViewById(R.id.editText_tanggalInput);
        editText_tanggalInputTanam.setText(tanggal_input);

        editText_updateStokBenihTanam = (EditText)findViewById(R.id.update_stokBenih);
        editText_updateWaktuTanam = (EditText)findViewById(R.id.editText_waktuTanam);
        editText_updateTglMulaiTanam = (EditText)findViewById(R.id.editText_updateTanggalDimulai);
        editText_updateTglBerakhirTanam = (EditText)findViewById(R.id.editText_updateTanggalBerakhir);
        editText_updatePenyebaranBenihTanam = (EditText)findViewById(R.id.update_penyebaranBenih);
        editText_updateTitikTanam = (EditText)findViewById(R.id.update_titikTanam);
        editText_updateKomentarTanam = (EditText)findViewById(R.id.update_komentar);

        txtView_editTanam = (TextView)findViewById(R.id.textView_editTanam);

        spinner_satuanWaktuTanam = (Spinner)findViewById(R.id.spinner_updateWaktuTanam);
        btnUpdateTanam= (Button)findViewById(R.id.btnUpdateStok);
        btnHapusTanam = (Button)findViewById(R.id.btnHapusTanam);

        getDataTanam();

        spinner_satuanWaktuTanam.setEnabled(false);
        editText_updateStokBenihTanam.setEnabled(false);
        editText_updateWaktuTanam.setEnabled(false);
        editText_updateTglMulaiTanam.setEnabled(false);
        editText_updateTglBerakhirTanam.setEnabled(false);
        editText_updatePenyebaranBenihTanam.setEnabled(false);
        editText_updateTitikTanam.setEnabled(false);
        editText_updateKomentarTanam.setEnabled(false);

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

        txtView_editTanam.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (editText_updateWaktuTanam.getText().toString().contains("Hari")) {
                    editText_updateWaktuTanam.setText("");
                }
                else if (editText_updateWaktuTanam.getText().toString().contains("Bulan")) {
                    editText_updateWaktuTanam.setText("");
                }
                else if (editText_updateWaktuTanam.getText().toString().contains("Tahun")) {
                    editText_updateWaktuTanam.setText("");
                }

                editText_updateStokBenihTanam.setEnabled(true);
                spinner_satuanWaktuTanam.setEnabled(true);
                editText_updateWaktuTanam.setEnabled(true);
                editText_updateTglMulaiTanam.setEnabled(true);
                editText_updateTglBerakhirTanam.setEnabled(true);
                editText_updatePenyebaranBenihTanam.setEnabled(true);
                editText_updateTitikTanam.setEnabled(true);
                editText_updateKomentarTanam.setEnabled(true);

                btnUpdateTanam.setVisibility(View.VISIBLE);
                txtView_editTanam.setVisibility(View.GONE);

            }
        });

        btnUpdateTanam.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                final String stokBenihTanam = editText_updateStokBenihTanam.getText().toString();

                final String updateSatuanWaktuTanam = spinner_satuanWaktuTanam.getSelectedItem().toString();
                final String updateWaktuTanam = editText_updateWaktuTanam.getText().toString() + " " + updateSatuanWaktuTanam;

                final String updateTglMulaiTanam = editText_updateTglMulaiTanam.getText().toString();
                final String updateTglBerakhirTanam = editText_updateTglBerakhirTanam.getText().toString();
                final String updatePenyebaranBenihTanam = editText_updatePenyebaranBenihTanam.getText().toString();
                final String updateTitikTanam = editText_updateTitikTanam.getText().toString();
                final String updateKomentarTanam = editText_updateKomentarTanam.getText().toString();


                if (stokBenihTanam.trim().length() > 0 && updateWaktuTanam.trim().length() > 0 && updateTglMulaiTanam.trim().length() > 0 &&
                        updateTglBerakhirTanam.trim().length() > 0 && updatePenyebaranBenihTanam.trim().length() > 0 && updateTitikTanam.trim().length() > 0 &&
                        updateKomentarTanam.trim().length() > 0) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateInputTanam.this);
                        alertDialogBuilder.setMessage("Apakah Anda Yakin Memperbaharui Hasil Panen?");

                        alertDialogBuilder.setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        updateInputTanam(stokBenihTanam, updateWaktuTanam, updateTglMulaiTanam, updateTglBerakhirTanam, updatePenyebaranBenihTanam,
                                                updateTitikTanam, updateKomentarTanam);
                                        Toast.makeText(UpdateInputTanam.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();

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
                    if (TextUtils.isEmpty(stokBenihTanam)) {
                        editText_updateStokBenihTanam.setError("Harap Masukkan Data Stok Benih");
                        editText_updateStokBenihTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateWaktuTanam)) {
                        editText_updateWaktuTanam.setError("Harap Masukkan Data Waktu Tanam");
                        editText_updateWaktuTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateTglMulaiTanam)) {
                        editText_updateTglMulaiTanam.setError("Harap Masukkan Data Tanggal Dimulainya Masa Tanam");
                        editText_updateTglMulaiTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateTglBerakhirTanam)) {
                        editText_updateTglBerakhirTanam.setError("Harap Masukkan Data Tanggal Berakhirnya Masa Tanam");
                        editText_updateTglBerakhirTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updatePenyebaranBenihTanam)) {
                        editText_updatePenyebaranBenihTanam.setError("Harap Masukkan Data Benih Tanam");
                        editText_updatePenyebaranBenihTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateTitikTanam)) {
                        editText_updateTitikTanam.setError("Harap Masukkan Data Titik Tanam");
                        editText_updateTitikTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(updateKomentarTanam)) {
                        editText_updateKomentarTanam.setError("Harap Masukkan Data Keterangan/Komentar");
                        editText_updateKomentarTanam.requestFocus();
                    }


                }
            }
        });

        btnHapusTanam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateInputTanam.this);
                alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data Tanam?");

                //jika iya maka akan menjalankan resetTransaksi
                alertDialogBuilder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                hapusDataTanam();
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

    private void hapusDataTanam(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlHapusTanam, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_ID_TANAM, LihatInputTanam.tanam_id);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateInputTanam(final String stokBenihTanam, final String updateWaktuTanam, final String updateTglMulaiTanam,
                                  final String updateTglBerakhirTanam, final String updatePenyebaranBenihTanam,
                                  final String updateTitikTanam, final String updateKomentarTanam) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlUpdateHasilTanam, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Menyimpan: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Berhasil Menyimpan!", jObj.toString());

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
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_REG_STOK_BENIH, stokBenihTanam);
                params.put(Konfigurasi.KEY_REG_WAKTU_TANAM, updateWaktuTanam);
                params.put(Konfigurasi.KEY_REG_TGL_MULAI, updateTglMulaiTanam);
                params.put(Konfigurasi.KEY_REG_TGL_BERAKHIR, updateTglBerakhirTanam);
                params.put(Konfigurasi.KEY_REG_PENYEBARAN_BENIH, updatePenyebaranBenihTanam);
                params.put(Konfigurasi.KEY_REG_TITIK_TANAM, updateTitikTanam);
                params.put(Konfigurasi.KEY_REG_KOMENTAR, updateKomentarTanam);
                params.put(Konfigurasi.KEY_ID_TANAM, LihatInputTanam.tanam_id);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText_updateTglMulaiTanam.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText_updateTglBerakhirTanam.setText(sdf.format(myCalendar.getTime()));
    }

    private void getDataTanam(){
        class GetDataTanam extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateInputTanam.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDataTanam(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerTanam rh = new RequestHandlerTanam();
               // requestHandler rh = new requestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_DATA_TANAM, LihatInputTanam.tanam_id);
                //String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_DATA_TANAM, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetDataTanam ge = new GetDataTanam();
        ge.execute();
    }

    private void showDataTanam(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String stokBenihTanam = c.getString(Konfigurasi.TAG_STOK_BENIH_TANAM);
            editText_updateStokBenihTanam.setText(stokBenihTanam);
            String waktuTanam = c.getString(Konfigurasi.TAG_TANAM_WAKTU);
            editText_updateWaktuTanam.setText(waktuTanam);
            String tglMulaiTanam = c.getString(Konfigurasi.TAG_TGL_MULAI_TANAM);
            editText_updateTglMulaiTanam.setText(tglMulaiTanam);
            String tglBerakhirTanam = c.getString(Konfigurasi.TAG_TGL_BERAKHIR_TANAM);
            editText_updateTglBerakhirTanam.setText(tglBerakhirTanam);
            String penyebaranBenihTanam = c.getString(Konfigurasi.TAG_PENYEBARAN_BENIH_TANAM);
            editText_updatePenyebaranBenihTanam.setText(penyebaranBenihTanam);
            String titikTanam = c.getString(Konfigurasi.TAG_TITIK_TANAM);
            editText_updateTitikTanam.setText(titikTanam);
            String komentarTanam = c.getString(Konfigurasi.TAG_KOMENTAR_TANAM);
            editText_updateKomentarTanam.setText(komentarTanam);


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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
