package com.example.aditmail.projectjagung;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v4.app.DialogFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.util.Log;
import android.widget.Button;
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

import com.example.aditmail.projectjagung.DatePickerFrag.DatePickerFragment;
import com.example.aditmail.projectjagung.DatePickerFrag.DatePickerFragment2;


import java.util.Calendar;

/**
 * Created by ADITMAIL on 27/01/2018.
 */

public class FragmentSatu extends Fragment {

    View myView;

    EditText editText_tanggalDimulai;
    EditText editText_tanggalBerakhir;
    EditText editText_stokBenih, editText_waktuTanam, editText_penyebaranBenih, editText_titikTanam, editText_komentar;
    Spinner spinner_satuanWaktu;
    Button btnSimpanTanam, btncobaSimpan;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = FragmentSatu.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String urlInputTanam = Konfigurasi.URL_INPUT_TANAM;

    public FragmentSatu() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_satu, container, false);
        return myView;
    }

    public void onStart() {
        super.onStart();

        editText_tanggalDimulai = (EditText) getActivity().findViewById(R.id.editText_tanggalDimulai);
        editText_tanggalBerakhir = (EditText) getActivity().findViewById(R.id.editText_tanggalBerakhir);

        editText_stokBenih = (EditText) getActivity().findViewById(R.id.input_stokBenih);
        editText_waktuTanam = (EditText) getActivity().findViewById(R.id.input_waktuTanam);
        editText_penyebaranBenih = (EditText) getActivity().findViewById(R.id.input_penyebaranBenih);
        editText_titikTanam = (EditText) getActivity().findViewById(R.id.input_titikTanam);
        editText_komentar = (EditText) getActivity().findViewById(R.id.input_komentar);

        spinner_satuanWaktu = (Spinner)getActivity().findViewById(R.id.spinner_waktuTanam);
        btnSimpanTanam = (Button)getActivity().findViewById(R.id.btnSimpanStok);
        btncobaSimpan = (Button)getActivity().findViewById(R.id.btnSimpanStokTanam);

        editText_tanggalDimulai.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }

        });

        editText_tanggalBerakhir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment picker = new DatePickerFragment2();
                picker.show(getFragmentManager(), "datePicker");
            }

        });

        btnSimpanTanam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String stokBenih = editText_stokBenih.getText().toString();
                String satuanWaktu = spinner_satuanWaktu.getSelectedItem().toString();
                String waktuTanam = editText_waktuTanam.getText().toString() + " " + satuanWaktu;
                String tglDimulai = editText_tanggalDimulai.getText().toString();
                String tglBerakhir = editText_tanggalBerakhir.getText().toString();
                String penyebaranBenih = editText_penyebaranBenih.getText().toString();
                String titikTanam = editText_titikTanam.getText().toString();
                String komentar = editText_komentar.getText().toString();

                if (stokBenih.trim().length() > 0 && waktuTanam.trim().length() > 0 && penyebaranBenih.trim().length() > 0 && titikTanam.trim().length() >0 &&
                        komentar.trim().length() > 0 && tglDimulai.trim().length() > 0 && tglBerakhir.trim().length() > 0 && komentar.trim().length() > 0) {

                    /*
                   if (conMgr.getActiveNetworkInfo() != null
                           && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

*/
                        checkInputTanam(stokBenih, waktuTanam, tglDimulai, tglBerakhir, penyebaranBenih, titikTanam, komentar);

               //else {
               //      Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
               //}

            } else {
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(stokBenih)) {
                        editText_stokBenih.setError("Harap Masukkan Data Stok Benih");
                        editText_stokBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(waktuTanam)) {
                        editText_waktuTanam.setError("Harap Masukkan Data Waktu Tanam");
                        editText_waktuTanam.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tglDimulai)) {
                        editText_tanggalDimulai.setError("Harap Masukkan Data Tanggal Dimulainya Penanaman");
                        editText_tanggalDimulai.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tglBerakhir)) {
                        editText_tanggalBerakhir.setError("Harap Masukkan Data Tanggal Berakhirnya Penanaman");
                        editText_tanggalBerakhir.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(penyebaranBenih)) {
                        editText_penyebaranBenih.setError("Harap Masukkan Data Penyebaran Benih");
                        editText_penyebaranBenih.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(titikTanam)) {
                        editText_titikTanam.setError("Harap Masukkan Data Titik Tanam");
                        editText_titikTanam.requestFocus();
                    }
                    if (TextUtils.isEmpty(komentar)) {
                        editText_komentar.setError("Harap Masukkan Data Keterangan/Komentar");
                        editText_komentar.requestFocus();
                    }


                }

            }

        });


    }

    private void checkInputTanam(final String stokBenih, final String waktuTanam, final String tglDimulai, final String tglBerakhir, final String penyebaranBenih,
                                 final String titikTanam, final String komentar) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlInputTanam, new Response.Listener<String>() {

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

                    Toast.makeText(getActivity(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(),
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
               Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_REG_STOK_BENIH, stokBenih);
                params.put(Konfigurasi.KEY_REG_WAKTU_TANAM, waktuTanam);
                params.put(Konfigurasi.KEY_REG_TGL_MULAI, tglDimulai);
                params.put(Konfigurasi.KEY_REG_TGL_BERAKHIR, tglBerakhir);
                params.put(Konfigurasi.KEY_REG_PENYEBARAN_BENIH, penyebaranBenih);
                params.put(Konfigurasi.KEY_REG_TITIK_TANAM, titikTanam);
                params.put(Konfigurasi.KEY_REG_KOMENTAR, komentar);
                params.put(Konfigurasi.KEY_REG_ID, MainActivity.id_pelanggan);

                return params;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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


