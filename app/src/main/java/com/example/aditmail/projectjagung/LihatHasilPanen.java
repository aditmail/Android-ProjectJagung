package com.example.aditmail.projectjagung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatHasilPanen extends AppCompatActivity implements ListView.OnItemClickListener {

    public static String panen_id;

    private String JSON_STRING;
    private ListView lihatDataHasilPanen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_hasil_panen);

        lihatDataHasilPanen = (ListView)findViewById(R.id.lihatDataHasilPanen);
        lihatDataHasilPanen.setOnItemClickListener(this);
        getJSON();
    }

    private void showDataPanen() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            //untuk melakukan looping
            //untuk mengetahui apabila seluruh transaksi telah dimasukkan
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.KEY_ID_PANEN);
                String tanggal_input = jo.getString(Konfigurasi.KEY_TANGGAL_INPUT);
                String waktuPanen = jo.getString(Konfigurasi.KEY_WAKTU_PANEN);
                String jumlahBeratPanen = jo.getString(Konfigurasi.KEY_JUMLAH_BERAT_PANEN);

                HashMap<String, String> dataPanen = new HashMap<>();
                //menyimpan data dari database
                dataPanen.put(Konfigurasi.KEY_ID_PANEN, id);
                dataPanen.put(Konfigurasi.KEY_TANGGAL_INPUT, tanggal_input);
                dataPanen.put(Konfigurasi.KEY_WAKTU_PANEN, waktuPanen);
                dataPanen.put(Konfigurasi.KEY_JUMLAH_BERAT_PANEN, jumlahBeratPanen);
                //menaruh informasi kedalam list
                list.add(dataPanen);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                //data listView nya disimpan di Lihat Debit
                //namun layout yang digunakan pada list_item.xml
                LihatHasilPanen.this, list, R.layout.list_item_panen,
                new String[]{Konfigurasi.KEY_ID_PANEN, Konfigurasi.KEY_TANGGAL_INPUT, Konfigurasi.KEY_WAKTU_PANEN, Konfigurasi.KEY_JUMLAH_BERAT_PANEN},
                new int[]{R.id.listIdPanen, R.id.listTglInput, R.id.listWaktuPanen, R.id.listJumlahBeratPanen});

        lihatDataHasilPanen.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatHasilPanen.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataPanen();
            }

            @Override
            protected String doInBackground(Void... params) {
                requestHandler rh = new requestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_SELURUH_DATA_PANEN, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LihatHasilPanen.this, UpdateHasilPanen.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        panen_id = map.get(Konfigurasi.KEY_ID_PANEN);
        String tanggal_input = map.get(Konfigurasi.KEY_TANGGAL_INPUT).toString();
        intent.putExtra(Konfigurasi.KEY_TANGGAL_INPUT, tanggal_input);
        startActivity(intent);
    }

}
