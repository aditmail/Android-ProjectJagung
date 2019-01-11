package com.example.aditmail.projectjagung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatDataPupukBenih extends AppCompatActivity implements ListView.OnItemClickListener {

    public static String pupuk_id;

    private String JSON_STRING;
    private ListView lihatDataPupuk;
    private EditText totalHargaPupukBenih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_pupuk);

        lihatDataPupuk = (ListView)findViewById(R.id.lihatDataPupuk);
        lihatDataPupuk.setOnItemClickListener(this);

        totalHargaPupukBenih = (EditText)findViewById(R.id.editText_GrandTotal) ;
        totalHargaPupukBenih.setEnabled(false);

        getJSON();
        getTotalHargaPupukBenih();

    }

    private void showDataPupuk() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            //untuk melakukan looping
            //untuk mengetahui apabila seluruh transaksi telah dimasukkan
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.KEY_ID_PUPUK);
                String periode = jo.getString(Konfigurasi.KEY_REG_PERIODE);
                String tanggal_input = jo.getString(Konfigurasi.KEY_TANGGAL_INPUT_PUPUK);
                String jenisBenih = jo.getString(Konfigurasi.KEY_JENIS_BENIH);
                String jenisPupuk = jo.getString(Konfigurasi.KEY_JENIS_PUPUK);
                String namaPestisida = jo.getString(Konfigurasi.KEY_NAMA_PESTISIDA);
                String totalHarga = jo.getString(Konfigurasi.KEY_TOTAL_HARGA);

                HashMap<String, String> dataPupuk = new HashMap<>();
                //menyimpan data dari database
                dataPupuk.put(Konfigurasi.KEY_ID_PUPUK, id);
                dataPupuk.put(Konfigurasi.KEY_PERIODE, periode);
                dataPupuk.put(Konfigurasi.KEY_TANGGAL_INPUT_PUPUK, tanggal_input);
                dataPupuk.put(Konfigurasi.KEY_JENIS_BENIH, jenisBenih);
                dataPupuk.put(Konfigurasi.KEY_JENIS_PUPUK, jenisPupuk);
                dataPupuk.put(Konfigurasi.KEY_NAMA_PESTISIDA, namaPestisida);
                dataPupuk.put(Konfigurasi.KEY_TOTAL_HARGA, totalHarga);
                //menaruh informasi kedalam list
                list.add(dataPupuk);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                //data listView nya disimpan di Lihat Debit
                //namun layout yang digunakan pada list_item.xml
                LihatDataPupukBenih.this, list, R.layout.list_item_pupuk,
                new String[]{Konfigurasi.KEY_ID_PUPUK, Konfigurasi.KEY_PERIODE, Konfigurasi.KEY_TANGGAL_INPUT_PUPUK, Konfigurasi.KEY_JENIS_BENIH, Konfigurasi.KEY_JENIS_PUPUK,
                        Konfigurasi.KEY_NAMA_PESTISIDA, Konfigurasi.KEY_TOTAL_HARGA},
                new int[]{R.id.listIDPupuk, R.id.listPeriode, R.id.listTglInput, R.id.listJenisBenih, R.id.listJenisPupuk, R.id.listNamaPestisida, R.id.listTotalHarga});

        lihatDataPupuk.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataPupukBenih.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataPupuk();
            }

            @Override
            protected String doInBackground(Void... params) {
                requestHandler rh = new requestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_TAMPIL_SELURUH_DATA_PUPUK, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(LihatDataPupukBenih.this, UpdateDataPupukBenih.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        pupuk_id = map.get(Konfigurasi.KEY_ID_PUPUK);
        String tanggal_input = map.get(Konfigurasi.KEY_TANGGAL_INPUT_PUPUK).toString();
        intent.putExtra(Konfigurasi.KEY_TANGGAL_INPUT_PUPUK, tanggal_input);
        finish();
        startActivity(intent);
    }

    private void getTotalHargaPupukBenih(){
        class GetTotalHasilPupukBenih extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataPupukBenih.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showTotalHargaPupukBenih(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCoba rh = new RequestHandlerCoba();

                String s = rh.sendGetRequestParam4(Konfigurasi.URL_TAMPIL_TOTAL_HARGA_PUPUK_BENIH, MainActivity.id_pelanggan);
                return s;
            }
        }
        GetTotalHasilPupukBenih ge = new GetTotalHasilPupukBenih();
        ge.execute();
    }

    private void showTotalHargaPupukBenih(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String grandTotal = c.getString(Konfigurasi.KEY_GRANDTOTAL_HARGA_PUPUK_BENIH);


            if (totalHargaPupukBenih.getText().toString().contains("null")){
                totalHargaPupukBenih.setText("KOSONG");
                Toast.makeText(this, "Data Belum Diinput", Toast.LENGTH_LONG).show();
            }else {
                totalHargaPupukBenih.setText("Rp. " + grandTotal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
