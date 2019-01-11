package com.example.aditmail.projectjagung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class DataLahan extends AppCompatActivity {

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    TextView btn_edit;
    EditText editText_lahan1, editText_lahan2, editText_lamaBertani, editText_keterangan, lihatStatusLahan;
    Button btnAdd, btnDelete, btnSimpan, btnSimpan2, btnUpdate, btnUpdate2;
    Spinner spinner_Lahan;

    LinearLayout linear2;

   // private String urlSatuLahan = Konfigurasi.URL_DATA_LAHAN;
    private String urlDuaLahan = Konfigurasi.URL_DATA_LAHAN2;

  //  private String urlUpdateSatuLahan = Konfigurasi.URL_UPDATE_DATA_LAHAN;
    private String urlUpdateDuaLahan = Konfigurasi.URL_UPDATE_DATA_LAHAN2;

    private static final String TAG = DataLahan.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_lahan);

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

        btn_edit = (TextView) findViewById(R.id.textView_edit);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);

       // btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan2 = (Button) findViewById(R.id.btnSimpan2);

       // btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate2 = (Button) findViewById(R.id.btnUpdate2);

        linear2 = (LinearLayout) findViewById(R.id.linear2);

        spinner_Lahan = (Spinner) findViewById(R.id.spinner_lahan);
        editText_lahan1 = (EditText) findViewById(R.id.inputLahan);
        editText_lahan2 = (EditText) findViewById(R.id.inputLahan2);
        editText_lamaBertani = (EditText) findViewById(R.id.inputLamaBertani);
        editText_keterangan = (EditText) findViewById(R.id.inputKeterangan);

        lihatStatusLahan = (EditText) findViewById(R.id.tampilLahan);

        getDataLahan();

        //btnSimpan.setClickable(false);
        //btnSimpan.setEnabled(false);

        btnAdd.setClickable(false);

        // btnAdd.setEnabled(false);

        editText_lahan1.setCursorVisible(false);
        editText_lahan1.setEnabled(false);

        editText_lahan2.setCursorVisible(false);
        editText_lahan2.setEnabled(false);

        editText_lamaBertani.setCursorVisible(false);
        editText_lamaBertani.setEnabled(false);
        editText_keterangan.setEnabled(false);
        lihatStatusLahan.setEnabled(false);

        spinner_Lahan.setClickable(false);

        if (editText_lahan1.getText().toString().equals("null")) {
            editText_lahan1.setText("");
            editText_lahan2.setText("");
            editText_lamaBertani.setText("");
            editText_keterangan.setText("");
            lihatStatusLahan.setText("");
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //btnSimpan.setEnabled(true);
                //btnSimpan2.setClickable(false);

                btnAdd.setEnabled(true);

                editText_lahan1.setCursorVisible(true);
                editText_lahan1.setEnabled(true);

                editText_lahan2.setCursorVisible(true);
                editText_lahan2.setEnabled(true);

                editText_lamaBertani.setCursorVisible(true);
                editText_lamaBertani.setEnabled(true);

                editText_keterangan.setCursorVisible(true);
                editText_keterangan.setEnabled(true);

                lihatStatusLahan.setEnabled(true);

                spinner_Lahan.setClickable(true);

                if (editText_lahan1.getText().toString().equals("null") || editText_lahan1.getText().toString().equals("")) {
                    editText_lahan1.setText("");
                    editText_lahan2.setText("");
                    editText_lamaBertani.setText("");
                    editText_keterangan.setText("");
                    lihatStatusLahan.setVisibility(View.GONE);
                    spinner_Lahan.setVisibility(View.VISIBLE);
                    btnSimpan2.setVisibility(View.VISIBLE);

                }  if (!lihatStatusLahan.getText().toString().equals("null") || !editText_lahan1.getText().toString().equals("")) {
                    btnUpdate2.setVisibility(View.VISIBLE);
                    btnSimpan2.setVisibility(View.GONE);

                    spinner_Lahan.setVisibility(View.VISIBLE);
                    lihatStatusLahan.setVisibility(View.GONE);
                }

                btn_edit.setClickable(false);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linear2.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.INVISIBLE);

            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                linear2.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);

            }

        });

        btnSimpan2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String statusKepemilikan = spinner_Lahan.getSelectedItem().toString();
                String lahanPertama = editText_lahan1.getText().toString();
                if (editText_lahan2.getText().toString().isEmpty()){
                    editText_lahan2.setText("Kosong");
                }
                String lahanKedua = editText_lahan2.getText().toString();
                String lamaBertani = editText_lamaBertani.getText().toString();
                String keterangan = editText_keterangan.getText().toString();

                if (lahanPertama.trim().length() >= 2 && lahanKedua.trim().length() >= 0 && lamaBertani.trim().length() >= 2) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        if (TextUtils.isEmpty(lahanKedua)) {
                            editText_lahan2.setText("Kosong");
                        }

                        checkDataLahan2(statusKepemilikan, lahanPertama, lahanKedua, lamaBertani, keterangan);
                        Toast.makeText(DataLahan.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                        btnSimpan2.setClickable(false);
                        btnDelete.setClickable(false);
                        editText_lahan1.setCursorVisible(false);
                        editText_lahan1.setEnabled(false);
                        editText_lahan2.setCursorVisible(false);
                        editText_lahan2.setEnabled(false);

                        editText_lamaBertani.setCursorVisible(false);
                        editText_lamaBertani.setEnabled(false);
                        spinner_Lahan.setClickable(false);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(lahanPertama)) {
                        editText_lahan1.setError("Harap Masukkan Data Lahan");
                        editText_lahan1.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(lamaBertani)) {
                        editText_lamaBertani.setError("Harap Masukkan Lama Bertani");
                        editText_lamaBertani.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(keterangan)) {
                        editText_keterangan.setError("Harap Masukkan Keterangan");
                        editText_keterangan.requestFocus();
                        return;
                    }
                    if (lamaBertani.trim().length() != 4) {
                        editText_lamaBertani.setError("Harap Masukkan Tahun dengan format (YYYY)");
                        editText_lamaBertani.requestFocus();
                        return;
                    }


                }

            }

        });

        btnUpdate2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String statusKepemilikan = spinner_Lahan.getSelectedItem().toString();
                final String lahanPertama = editText_lahan1.getText().toString();
                if (editText_lahan2.getText().toString().isEmpty()){
                    editText_lahan2.setText("Kosong");
                }
                final String lahanKedua = editText_lahan2.getText().toString();
                final String lamaBertani = editText_lamaBertani.getText().toString();
                final String keterangan = editText_keterangan.getText().toString();

                if (lahanPertama.trim().length() >= 2 && lahanKedua.trim().length() >= 0 && lamaBertani.trim().length() >= 2) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DataLahan.this);
                        alertDialogBuilder.setMessage("Apakah Anda Yakin Memperbaharui Data Lahan? ");

                        alertDialogBuilder.setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        updateDataLahan2(statusKepemilikan, lahanPertama, lahanKedua, lamaBertani, keterangan);
                                        Toast.makeText(DataLahan.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
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
                    if (TextUtils.isEmpty(lahanPertama)) {
                        editText_lahan1.setError("Harap Masukkan Data Lahan");
                        editText_lahan1.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(lamaBertani)) {
                        editText_lamaBertani.setError("Harap Masukkan Lama Bertani");
                        editText_lamaBertani.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(keterangan)) {
                        editText_keterangan.setError("Harap Masukkan Keterangan");
                        editText_keterangan.requestFocus();
                        return;
                    }
                    if (lamaBertani.trim().length() != 4) {
                        editText_lamaBertani.setError("Harap Masukkan Tahun dengan format (YYYY)");
                        editText_lamaBertani.requestFocus();
                    }

                }

            }
        });
    }

    private void checkDataLahan2(final String statusKepemilikan, final String lahanPertama, final String lahanKedua, final String lamaBertani, final String keterangan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlDuaLahan, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_REG_STATUS_LAHAN, statusKepemilikan);
                params.put(Konfigurasi.KEY_REG_LUAS_LAHAN1, lahanPertama);
                params.put(Konfigurasi.KEY_REG_LUAS_LAHAN2, lahanKedua);
                params.put(Konfigurasi.KEY_REG_LAMA_BERTANI, lamaBertani);
                params.put(Konfigurasi.KEY_REG_KETERANGAN, keterangan);
                params.put(Konfigurasi.KEY_REG_ID, MainActivity.id_pelanggan);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void updateDataLahan2(final String statusKepemilikan, final String lahanPertama, final String lahanKedua, final String lamaBertani, final String keterangan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlUpdateDuaLahan, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_REG_STATUS_LAHAN, statusKepemilikan);
                params.put(Konfigurasi.KEY_REG_LUAS_LAHAN1, lahanPertama);
                params.put(Konfigurasi.KEY_REG_LUAS_LAHAN2, lahanKedua);
                params.put(Konfigurasi.KEY_REG_LAMA_BERTANI, lamaBertani);
                params.put(Konfigurasi.KEY_REG_KETERANGAN, keterangan);
                params.put(Konfigurasi.KEY_REG_ID, MainActivity.id_pelanggan);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void getDataLahan(){
        class GetDataLahan extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DataLahan.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDataLahan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                requestHandler rh = new requestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_DATA_LAHAN,MainActivity.id_pelanggan);
                return s;
            }
        }
        GetDataLahan ge = new GetDataLahan();
        ge.execute();
    }

    private void showDataLahan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String statusLahan = c.getString((Konfigurasi.TAG_STATUS_LAHAN));
            String luasLahan1 = c.getString(Konfigurasi.TAG_LAHAN1);
            String luasLahan2 = c.getString(Konfigurasi.TAG_LAHAN2);
            String lamaBertani = c.getString(Konfigurasi.TAG_LAMA_BERTANI);
            String keterangan = c.getString(Konfigurasi.TAG_KETERANGAN);

            lihatStatusLahan.setText(statusLahan);
            editText_lahan1.setText(luasLahan1);
            editText_lahan2.setText(luasLahan2);
            editText_lamaBertani.setText(lamaBertani);
            editText_keterangan.setText(keterangan);

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

