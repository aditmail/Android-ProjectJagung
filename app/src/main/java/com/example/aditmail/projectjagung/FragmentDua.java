package com.example.aditmail.projectjagung;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.aditmail.projectjagung.DatePickerFrag.DatePickerFragment3;
import com.example.aditmail.projectjagung.DatePickerFrag.DatePickerFragment4;

import com.example.aditmail.projectjagung.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADITMAIL on 27/01/2018.
 */

public class FragmentDua extends Fragment {


    EditText editText_tanggalDimulaiPanen;
    EditText editText_tanggalBerakhirPanen;

    EditText editText_waktuPanen, editText_jumlahBeratPanen, editText_sortirHasilPanen, editText_penjualanHasilPanen, editText_komentarPanen;
    Spinner spinner_satuanWaktuPanen;
    Button btnSimpanPanen;

    ProgressDialog pDialog;

    int success;
    ConnectivityManager conMgr;

    private static final String TAG = FragmentSatu.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String urlHasilPanen = Konfigurasi.URL_INPUT_HASIL_PANEN;


    View myView;

    public FragmentDua() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_dua, container, false);
        return myView;

    }

    public void onStart() {
        super.onStart();

        editText_tanggalDimulaiPanen = (EditText) getActivity().findViewById(R.id.editText_tanggalDimulaiPanen);
        editText_tanggalBerakhirPanen = (EditText) getActivity().findViewById(R.id.editText_tanggalBerakhirPanen);

        editText_waktuPanen = (EditText) getActivity().findViewById(R.id.input_waktuPanen);
        editText_jumlahBeratPanen = (EditText) getActivity().findViewById(R.id.input_jumlahBeratPanen);
        editText_sortirHasilPanen = (EditText) getActivity().findViewById(R.id.input_sortirHasilPanen);
        editText_penjualanHasilPanen = (EditText) getActivity().findViewById(R.id.input_penjualanHasilPanen);
        editText_komentarPanen = (EditText) getActivity().findViewById(R.id.input_komentarPanen);

        spinner_satuanWaktuPanen = (Spinner)getActivity().findViewById(R.id.spinner_waktuPanen);
        btnSimpanPanen = (Button)getActivity().findViewById(R.id.btnSimpanPanen);


        editText_tanggalDimulaiPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment picker = new DatePickerFragment3();
                picker.show(getFragmentManager(), "datePicker");
            }

        });

        editText_tanggalBerakhirPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment picker = new DatePickerFragment4();
                picker.show(getFragmentManager(), "datePicker");
            }

        });

        btnSimpanPanen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String satuanWaktuPanen = spinner_satuanWaktuPanen.getSelectedItem().toString();
                String waktuPanen = editText_waktuPanen.getText().toString()+ " " + satuanWaktuPanen ;
                String tglDimulaiPanen = editText_tanggalDimulaiPanen.getText().toString();
                String tglBerakhirPanen = editText_tanggalBerakhirPanen.getText().toString();
                String jumlahBeratPanen = editText_jumlahBeratPanen.getText().toString() ;
                String sortirHasilPanen = editText_sortirHasilPanen.getText().toString();
                String penjualanHasilPanen = editText_penjualanHasilPanen.getText().toString();
                String komentarPanen = editText_komentarPanen.getText().toString();


                if (waktuPanen.trim().length() > 0 && tglDimulaiPanen.trim().length() > 0 && tglBerakhirPanen.trim().length() > 0 && jumlahBeratPanen.trim().length() >0 &&
                        sortirHasilPanen.trim().length() > 0 && penjualanHasilPanen.trim().length() > 0 && komentarPanen.trim().length() > 0) {

                    /*
                   if (conMgr.getActiveNetworkInfo() != null
                           && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

*/
                    checkHasilPanen(waktuPanen, tglDimulaiPanen, tglBerakhirPanen, jumlahBeratPanen, sortirHasilPanen, penjualanHasilPanen, komentarPanen);

                    //else {
                    //      Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    //}

                } else {
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(waktuPanen)) {
                        editText_waktuPanen.setError("Harap Masukkan Data Waktu Panen");
                        editText_waktuPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tglDimulaiPanen)) {
                        editText_tanggalDimulaiPanen.setError("Harap Masukkan Data Tanggal Dimulainya Masa Panen");
                        editText_tanggalDimulaiPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tglBerakhirPanen)) {
                        editText_tanggalBerakhirPanen.setError("Harap Masukkan Data Tanggal Berakhirnya Masa Panen");
                        editText_tanggalBerakhirPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(jumlahBeratPanen)) {
                        editText_jumlahBeratPanen.setError("Harap Masukkan Data Jumlah Berat Hasil Panen");
                        editText_jumlahBeratPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(sortirHasilPanen)) {
                        editText_sortirHasilPanen.setError("Harap Masukkan Data Sortir Hasil Panen");
                        editText_sortirHasilPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(penjualanHasilPanen)) {
                        editText_penjualanHasilPanen.setError("Harap Masukkan Data Penjualan Hasil Panen");
                        editText_penjualanHasilPanen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(komentarPanen)) {
                        editText_komentarPanen.setError("Harap Masukkan Data Keterangan/Komentar");
                        editText_komentarPanen.requestFocus();
                    }


                }

            }

        });


    }

    private void checkHasilPanen (final String waktuPanen, final String tglDimulaiPanen, final String tglBerakhirPanen, final String jumlahBeratPanen,
                                 final String sortirHasilPanen, final String penjualanHasilPanen,final String komentarPanen) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlHasilPanen, new Response.Listener<String>() {

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
                params.put(Konfigurasi.KEY_REG_WAKTU_PANEN, waktuPanen);
                params.put(Konfigurasi.KEY_REG_TGL_MULAI_PANEN, tglDimulaiPanen);
                params.put(Konfigurasi.KEY_REG_TGL_BERAKHIR_PANEN, tglBerakhirPanen);
                params.put(Konfigurasi.KEY_REG_JUMLAH_BERAT_PANEN, jumlahBeratPanen);
                params.put(Konfigurasi.KEY_REG_SORTIR_HASIL_PANEN, sortirHasilPanen);
                params.put(Konfigurasi.KEY_REG_PENJUALAN_HASIL_PANEN, penjualanHasilPanen);
                params.put(Konfigurasi.KEY_REG_KOMENTAR_PANEN, komentarPanen);
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
