package com.example.aditmail.projectjagung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class LihatDataHasilPanenBaru extends AppCompatActivity implements ListView.OnItemClickListener{

    public static String panen_ID;

    private String JSON_STRING;
    private ListView lihatDataHasilPanen;
    private EditText totalHargaPanen, cariPeriodez;
    private LinearLayout linearCari;
    private Button btnCariPeriode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_hasil_panen_baru);

        lihatDataHasilPanen = (ListView) findViewById(R.id.lihatDataHasilPanenBaru);
        lihatDataHasilPanen.setOnItemClickListener(this);

        totalHargaPanen = (EditText) findViewById(R.id.editText_GrandTotal);
        totalHargaPanen.setEnabled(false);

        getJSON();
        getTotalHargaPanen();

    }

    private void showDataHasilPanenBaru() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            //untuk melakukan looping
            //untuk mengetahui apabila seluruh transaksi telah dimasukkan
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.KEY_ID_BENIH);
                String periode = jo.getString(Konfigurasi.KEY_PERIODE);
                String tanggal_input = jo.getString(Konfigurasi.KEY_TANGGAL_INPUT_PANEN);
                String hasilBenih = jo.getString(Konfigurasi.KEY_HASIL_BENIH);
                String hargaHasilPanen = jo.getString(Konfigurasi.KEY_HARGA_PANEN);
                String totalHarga = jo.getString(Konfigurasi.KEY_TOTAL_HARGA_PANEN);

                HashMap<String, String> dataPanen = new HashMap<>();
                //menyimpan data dari database
                dataPanen.put(Konfigurasi.KEY_ID_BENIH, id);
                dataPanen.put(Konfigurasi.KEY_PERIODE, periode);
                dataPanen.put(Konfigurasi.KEY_TANGGAL_INPUT_PANEN, tanggal_input);
                dataPanen.put(Konfigurasi.KEY_HASIL_BENIH, hasilBenih);
                dataPanen.put(Konfigurasi.KEY_HARGA_PANEN, "Rp. " + hargaHasilPanen);
                dataPanen.put(Konfigurasi.KEY_TOTAL_HARGA_PANEN, "Rp. " + totalHarga);
                //menaruh informasi kedalam list
                list.add(dataPanen);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                //data listView nya disimpan di Lihat Debit
                //namun layout yang digunakan pada list_item.xml
                LihatDataHasilPanenBaru.this, list, R.layout.list_item_hasil_panen_baru,
                new String[]{Konfigurasi.KEY_ID_BENIH, Konfigurasi.KEY_PERIODE, Konfigurasi.KEY_TANGGAL_INPUT_PANEN, Konfigurasi.KEY_HASIL_BENIH, Konfigurasi.KEY_HARGA_PANEN, Konfigurasi.KEY_TOTAL_HARGA_PANEN},
                new int[]{R.id.listIDPanen, R.id.listPeriode, R.id.listTglInput, R.id.listHasilBenihBaru, R.id.listHargaHasilPanenBaru, R.id.listTotalHargaBaru});
        lihatDataHasilPanen.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataHasilPanenBaru.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataHasilPanenBaru();
            }

            @Override
            protected String doInBackground(Void... params) {
                requestHandler rh = new requestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_SELURUH_DATA_PANEN_BARU, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LihatDataHasilPanenBaru.this, UpdateDataHasilPanenBaru.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        panen_ID = map.get(Konfigurasi.KEY_ID_BENIH);
        String tanggal_input = map.get(Konfigurasi.KEY_TANGGAL_INPUT_PANEN).toString();
        intent.putExtra(Konfigurasi.KEY_TANGGAL_INPUT_PANEN, tanggal_input);
        startActivity(intent);
        finish();
    }

    private void getTotalHargaPanen(){
        class GetTotalHasilPanen extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataHasilPanenBaru.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showTotalHargaPanen(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCoba rh = new RequestHandlerCoba();

                String s = rh.sendGetRequestParam4(Konfigurasi.URL_TAMPIL_TOTAL_HARGA_PANEN, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetTotalHasilPanen ge = new GetTotalHasilPanen();
        ge.execute();
    }

    private void showTotalHargaPanen(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String grandTotal = c.getString(Konfigurasi.KEY_GRANDTOTAL_HARGA_PANEN);

            totalHargaPanen.setText("Rp. " + grandTotal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
