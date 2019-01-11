package com.example.aditmail.projectjagung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KalkulasiTotal extends AppCompatActivity {

    public static String penjualan, pengeluaran;

    TextView cariPeriode, periodeHasilPanenHide, periodePupukBenihHide, periodeHasilPanenShow, periodePupukBenihShow, cariTotal,
            grandTotal, totalPenjualan, totalPengeluaran;
    String periode;
    Button btnGrandTotal;

    private String JSON_STRING;
    private ListView lihatDataHasilPanen, lihatDataPupukBenih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulasi_total);

        Intent intent = getIntent();
        periode = intent.getStringExtra(CariPeriode.CARIPERIODE);

        cariPeriode = (TextView)findViewById(R.id.PeriodePanen);
        cariPeriode.setText("Periode : " + periode);

        periodeHasilPanenHide = (TextView)findViewById(R.id.periodeHasilPanenHide);
        periodeHasilPanenShow = (TextView)findViewById(R.id.periodeHasilPanenShow);

        periodePupukBenihHide = (TextView)findViewById(R.id.periodePupukBenihHide);
        periodePupukBenihShow = (TextView)findViewById(R.id.periodePupukBenihShow);

        lihatDataHasilPanen = (ListView)findViewById(R.id.ListViewPeriode);
        lihatDataPupukBenih = (ListView)findViewById(R.id.ListViewPupukBenih);

        totalPenjualan = (TextView) findViewById(R.id.textView_totalPenjualan);
        totalPengeluaran = (TextView)findViewById(R.id.textView_totalPengeluaran);

        //cariTotal = (TextView)findViewById(R.id.periodeGrandTotal);

        getListPenjualan();
        getListPengeluaran();

        getTotalHargaPanen();
        getTotalHargaPupukBenih();

        grandTotal = (TextView) findViewById(R.id.textView_GrandTotal);
        btnGrandTotal = (Button) findViewById(R.id.btnKalkulasiHasil);

        periodeHasilPanenHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatDataHasilPanen.setVisibility(View.VISIBLE);
                periodeHasilPanenHide.setVisibility(View.GONE);
                periodeHasilPanenShow.setVisibility(View.VISIBLE);
            }
        });

        btnGrandTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GrandTotal();
            }
        });


        periodeHasilPanenShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatDataHasilPanen.setVisibility(View.GONE);
                periodeHasilPanenHide.setVisibility(View.VISIBLE);
                periodeHasilPanenShow.setVisibility(View.GONE);
            }
        });

        periodePupukBenihHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatDataPupukBenih.setVisibility(View.VISIBLE);
                periodePupukBenihHide.setVisibility(View.GONE);
                periodePupukBenihShow.setVisibility(View.VISIBLE);
            }
        });

        periodePupukBenihShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatDataPupukBenih.setVisibility(View.GONE);
                periodePupukBenihHide.setVisibility(View.VISIBLE);
                periodePupukBenihShow.setVisibility(View.GONE);
            }
        });



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
                //String id = jo.getString(Konfigurasi.KEY_ID_BENIH);
                //String tanggal_input = jo.getString(Konfigurasi.KEY_TANGGAL_INPUT_PANEN);
                String hasilBenih = jo.getString(Konfigurasi.KEY_HASIL_BENIH);
               // String hargaHasilPanen = jo.getString(Konfigurasi.KEY_HARGA_PANEN);
                String totalHarga = jo.getString(Konfigurasi.KEY_TOTAL_HARGA_PANEN);

                HashMap<String, String> dataPanen = new HashMap<>();
                //menyimpan data dari database
              //  dataPanen.put(Konfigurasi.KEY_ID_BENIH, id);
              //  dataPanen.put(Konfigurasi.KEY_TANGGAL_INPUT_PANEN, tanggal_input);
                dataPanen.put(Konfigurasi.KEY_HASIL_BENIH, hasilBenih);
              //  dataPanen.put(Konfigurasi.KEY_HARGA_PANEN, "Rp. " + hargaHasilPanen);
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
                KalkulasiTotal.this, list, R.layout.list_kalkulasi,
                new String[]{ Konfigurasi.KEY_HASIL_BENIH, Konfigurasi.KEY_TOTAL_HARGA_PANEN},
                new int[]{R.id.listHasilBenih, R.id.listTotalHarga});
        lihatDataHasilPanen.setAdapter(adapter);
    }

    private void getListPenjualan() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KalkulasiTotal.this, "Mengambil Data", "Mohon Tunggu...", false, false);
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
                RequestHandlerCoba rh = new RequestHandlerCoba();
                String s = rh.sendGetRequestParam5(Konfigurasi.URL_TAMPIL_PERIODE_PENJUALAN, MainActivity.id_pelanggan, periode);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getListPengeluaran() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KalkulasiTotal.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDataPupukBenih();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandlerCoba rh = new RequestHandlerCoba();
                String s = rh.sendGetRequestParam5(Konfigurasi.URL_TAMPIL_PERIODE_PENGELUARAN, MainActivity.id_pelanggan, periode);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showDataPupukBenih() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            //untuk melakukan looping
            //untuk mengetahui apabila seluruh transaksi telah dimasukkan
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                //String tanggal_input = jo.getString(Konfigurasi.KEY_TANGGAL_INPUT_PANEN);
                String jenisPupuk = jo.getString(Konfigurasi.KEY_JENIS_PUPUK);
                String jenisBenih = jo.getString(Konfigurasi.KEY_JENIS_BENIH);
                String namaPestisida = jo.getString(Konfigurasi.KEY_NAMA_PESTISIDA);
                String totalKeseluruhan = jo.getString(Konfigurasi.KEY_GRANDTOTAL_HARGA_PUPUK_BENIH);

                HashMap<String, String> dataPupukBenih = new HashMap<>();
                //  dataPanen.put(Konfigurasi.KEY_TANGGAL_INPUT_PANEN, tanggal_input);
                dataPupukBenih.put(Konfigurasi.KEY_JENIS_PUPUK, jenisPupuk);
                dataPupukBenih.put(Konfigurasi.KEY_JENIS_BENIH, jenisBenih);
                dataPupukBenih.put(Konfigurasi.KEY_NAMA_PESTISIDA, namaPestisida);
                dataPupukBenih.put(Konfigurasi.KEY_GRANDTOTAL_HARGA_PUPUK_BENIH, "Rp. " + totalKeseluruhan);
                list.add(dataPupukBenih);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                //data listView nya disimpan di Lihat Debit
                //namun layout yang digunakan pada list_item.xml
                KalkulasiTotal.this, list, R.layout.list_kalkulasi_pengeluaran,
                new String[]{ Konfigurasi.KEY_JENIS_PUPUK, Konfigurasi.KEY_JENIS_BENIH, Konfigurasi.KEY_NAMA_PESTISIDA, Konfigurasi.KEY_GRANDTOTAL_HARGA_PUPUK_BENIH},
                new int[]{R.id.listJenisPupuk, R.id.listJenisBenih, R.id.listNamaPestisida, R.id.listTotalKeseluruhan});
        lihatDataPupukBenih.setAdapter(adapter);
    }

    private void getTotalHargaPanen(){
        class GetTotalHasilPanen extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KalkulasiTotal.this,"Fetching...","Wait...",false,false);
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

                String s = rh.sendGetRequestParam5(Konfigurasi.URL_TAMPIL_TOTAL_HARGA_PANEN_PERIODE, MainActivity.id_pelanggan, periode);
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

            String grandTotalPanen = c.getString(Konfigurasi.KEY_GRANDTOTAL_HARGA_PANEN);

            totalPenjualan.setText("Rp. " + grandTotalPanen);
            penjualan = grandTotalPanen;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getTotalHargaPupukBenih(){
        class GetTotalHasilPanen extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KalkulasiTotal.this,"Fetching...","Wait...",false,false);
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

                String s = rh.sendGetRequestParam5(Konfigurasi.URL_TAMPIL_TOTAL_HARGA_PUPUK_BENIH_PERIODE, MainActivity.id_pelanggan, periode);
                return s;
            }
        }
        GetTotalHasilPanen ge = new GetTotalHasilPanen();
        ge.execute();
    }

    private void showTotalHargaPupukBenih(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String grandTotalPupukBenih = c.getString(Konfigurasi.KEY_GRANDTOTAL_HARGA_PUPUK_BENIH);

            totalPengeluaran.setText("Rp. " + grandTotalPupukBenih);
            pengeluaran = grandTotalPupukBenih;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GrandTotal() {
            double valPenjualan = Double.parseDouble(penjualan);
            double valPengeluaran = Double.parseDouble(pengeluaran);
            double Total = valPenjualan-valPengeluaran;
            String TotalALL = new Double(Total).toString();
            grandTotal.setText("Rp. " + TotalALL);

        }

}
