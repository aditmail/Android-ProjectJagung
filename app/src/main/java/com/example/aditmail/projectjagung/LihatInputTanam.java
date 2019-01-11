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

public class LihatInputTanam extends AppCompatActivity implements ListView.OnItemClickListener {

    public static String tanam_id;

    private String JSON_STRING;
    private ListView lihatDataInputTanam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_input_tanam);

        lihatDataInputTanam = (ListView)findViewById(R.id.lihatDataInputTanam);
        lihatDataInputTanam.setOnItemClickListener(this);
        getJSON();
    }

    private void showDataTanam() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            //untuk melakukan looping
            //untuk mengetahui apabila seluruh transaksi telah dimasukkan
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_tanam = jo.getString(Konfigurasi.KEY_ID_TANAM);
                String tanggal_input = jo.getString(Konfigurasi.KEY_TANGGAL_INPUT);
                String stokBenih = jo.getString(Konfigurasi.KEY_STOK_BENIH);
                String waktuTanam = jo.getString(Konfigurasi.KEY_WAKTU_TANAM);
                String penyebaranBenih = jo.getString(Konfigurasi.KEY_PENYEBARAN_BENIH);

                HashMap<String, String> dataTanam = new HashMap<>();
                //menyimpan data dari database
                dataTanam.put(Konfigurasi.KEY_ID_TANAM, id_tanam);
                dataTanam.put(Konfigurasi.KEY_TANGGAL_INPUT, tanggal_input);
                dataTanam.put(Konfigurasi.KEY_STOK_BENIH, stokBenih);
                dataTanam.put(Konfigurasi.KEY_WAKTU_TANAM, waktuTanam);
                dataTanam.put(Konfigurasi.KEY_PENYEBARAN_BENIH, penyebaranBenih);
                //menaruh informasi kedalam list
                list.add(dataTanam);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                //data listView nya disimpan di Lihat Debit
                //namun layout yang digunakan pada list_item.xml
                LihatInputTanam.this, list, R.layout.list_item,
                new String[]{Konfigurasi.KEY_ID_TANAM, Konfigurasi.KEY_TANGGAL_INPUT, Konfigurasi.KEY_STOK_BENIH, Konfigurasi.KEY_WAKTU_TANAM, Konfigurasi.KEY_PENYEBARAN_BENIH},
                new int[]{R.id.listIDTanam, R.id.listTglInput, R.id.listStokBenih, R.id.listWaktuTanam, R.id.listPenyebaranBenih});

        lihatDataInputTanam.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatInputTanam.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataTanam();
            }

            @Override
            protected String doInBackground(Void... params) {
                requestHandler rh = new requestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_SELURUH_DATA_TANAM, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LihatInputTanam.this, UpdateInputTanam.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        tanam_id = map.get(Konfigurasi.KEY_ID_TANAM);
        String tanggal_input = map.get(Konfigurasi.KEY_TANGGAL_INPUT).toString();
        intent.putExtra(Konfigurasi.KEY_TANGGAL_INPUT, tanggal_input);
        startActivity(intent);
    }
}
