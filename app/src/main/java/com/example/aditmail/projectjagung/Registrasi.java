package com.example.aditmail.projectjagung;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.aditmail.projectjagung.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registrasi extends Activity {

    private static final String TAG = Registrasi.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    ProgressDialog pDialog;
    Button btn_register;
    EditText editText_nama;
    EditText editText_handphone;
    EditText editText_surel;
    EditText editText_userID;
    EditText editText_alamat;
    EditText editText_kabupaten;
    EditText editText_kecamatan;
    Spinner spinner_Provinsi;

    //BANTEN
    Spinner spinner_kabupatenBanten, spinner_kabupatenPandegelang, spinner_kabupatenLebak, spinner_kabupatenTangerang,
            spinner_kabupatenSerang,spinner_kotaCilegon, spinner_kotaSerang, spinner_kotaTangerang, spinner_kotaTangerangSelatan;
    LinearLayout linear_banten;

    //JABAR
    Spinner spinner_kabupatenJawaBarat, spinner_kabupatenBandung, spinner_kabupatenBandungBarat, spinner_kabupatenBekasi,
            spinner_kabupatenBogor, spinner_kabupatenCiamis, spinner_kabupatenCianjur, spinner_kabupatenCirebon, spinner_kabupatenGarut,
            spinner_kabupatenIndramayu, spinner_kabupatenKarawang, spinner_kabupatenKuningan, spinner_kabupatenMajalengka, spinner_kabupatenPangandaran,
            spinner_kabupatenPurwakarta, spinner_kabupatenSubang, spinner_kabupatenSukabumi, spinner_kabupatenSumedang, spinner_kabupatenTasikmalaya,
            spinner_kotaBandung, spinner_kotaBanjar, spinner_kotaBekasi, spinner_kotaBogor, spinner_kotaCimahi, spinner_kotaCirebon, spinner_kotaDepok,
            spinner_kotaSukabumi, spinner_kotaTasikmalaya;
    LinearLayout linear_jabar;

    //JATENG
    Spinner spinner_kabupatenJawaTengah, spinner_kabupatenBanjarnegara, spinner_kabupatenBanyumas, spinner_kabupatenBatang, spinner_kabupatenBlora, spinner_kabupatenBoyolali,
            spinner_kabupatenBrebes, spinner_kabupatenCilacap, spinner_kabupatenDemak, spinner_kabupatenGrobogan, spinner_kabupatenJepara,
            spinner_kabupatenKaranganyar, spinner_kabupatenKebumen, spinner_kabupatenKendal, spinner_kabupatenKlaten, spinner_kabupatenKudus,
            spinner_kabupatenMagelang, spinner_kabupatenPati, spinner_kabupatenPekalongan, spinner_kabupatenPemalang, spinner_kabupatenPurbalingga,
            spinner_kabupatenPurworejo, spinner_kabupatenRembang, spinner_kabupatenSemarang, spinner_kabupatenSragen, spinner_kabupatenSukoharjo,
            spinner_kabupatenTegal, spinner_kabupatenTemanggung, spinner_kabupatenWonogiri, spinner_kabupatenWonosobo, spinner_kotaMagelang,
            spinner_kotaPekalongan, spinner_kotaSalatiga, spinner_kotaSemarang, spinner_kotaSurakarta, spinner_kotaTegal;
    LinearLayout linear_jateng;

    //LAMPUNG
    Spinner spinner_kabupatenLampung, spinner_kabupatenLampungBarat, spinner_kabupatenLampungSelatan, spinner_kabupatenLampungTengah, spinner_kabupatenLampungTimur, spinner_kabupatenLampungUtara,
            spinner_kabupatenMesuji, spinner_kabupatenPesawaran, spinner_kabupatenPesisirBarat, spinner_kabupatenPringsewu, spinner_kabupatenTanggamus, spinner_kabupatenTulangBawang,
            spinner_kabupatenTulangBawangBarat, spinner_kabupatenWayKanan, spinner_kotaBandarLampung, spinner_kotaMetro;
    LinearLayout linear_lampung;

    Spinner spinner_gender;
    Intent intent;
    int success;
    ConnectivityManager conMgr;
    String tag_json_obj = "json_obj_req";

    String [] daftarProvinsi = {
            "Pilih :",
            "Banten",
            "Jawa Barat",
            "Jawa Tengah",
            "Lampung"
    };

    String [] daftarKabupatenBanten = {
            "Pilih :","Kabupaten Pandegelang", "Kabupaten Lebak", "Kabupaten Tangerang", "Kabupaten Serang", "Kota Cilegon" ,"Kota Serang",
            "Kota Tangerang", "Kota Tangerang Selatan"
    };

    String [] daftarKabupatenJawaBarat = {
            "Pilih :", "Kabupaten Bandung", "Kabupaten Bandung Barat", "Kabupaten Bekasi", "Kabupaten Bogor", "Kabupaten Ciamis", "Kabupaten Cianjur",
            "Kabupaten Cirebon", "Kabupaten Garut", "Kabupaten Indramayu", "Kabupaten Karawang", "Kabupaten Kuningan", "Kabupaten Majalengka", "Kabupaten Pangandaran",
            "Kabupaten Purwakarta", "Kabupaten Subang", "Kabupaten Sukabumi", "Kabupaten Sumedang", "Kabupaten Tasikamalaya", "Kota Bandung", "Kota Banjar", "Kota Bekasi",
            "Kota Bogor", "Kota Cimahi", "Kota Cirebon", "Kota Depok", "Kota Sukabumi", "Kota Tasikmalaya"
    };

    String [] daftarKabupatenJawaTengah = {
            "Pilih :", "Kabupaten Cilacap", "Kabupaten Banyumans", "Kabupaten Purbalingga", "Kabupaten Banjarnegara", "Kabupaten Kebumen", "Kabupaten Purworejo",
            "Kabupaten Wonosobo", "Kabupaten Magelang", "Kabupaten Boyolali", "Kabupaten Klaten", "Kabupaten Sukoharjo", "Kabupaten Wonogiri", "Kabupaten Karanganyar",
            "Kabupaten Sragen", "Kabupaten Grobogan", "Kabupaten Blora", "Kabupaten Rembang", "Kabupaten Pati", "Kabupaten Kudus", "Kabupaten Jepara", "Kabupaten Demak",
            "Kabupaten Semarang", "Kabupaten Temanggung", "Kabupaten Kendal", "Kabupaten Batang", "Kabupaten Pekalongan", "Kabupaten Pemalang", "Kabupaten Tegal",
            "Kabupaten Brebes", "Kota Magelang", "Kota Surakarta", "Kota Salatiga", "Kota Semarang", "Kota Pekalongan", "Kota Tegal"
    };

    String [] daftarKabupatenLampung = {
            "Pilih :", "Kabupaten Lampung Barat", "Kabupaten Lampung Selatan", "Kabupaten Lampung Tengah", "Kabupaten Lampung Timur", "Kabupaten Lampung Utara", "Kabupaten Mesuji",
            "Kabupaten Pesawaran", "Kabupaten Pesisir Barat", "Kabupaten Pringsewu", "Kabupaten Tanggamus", "Kabupaten Tulang Bawang", "Kabupaten Tulang Bawang Barat",
            "Kabupaten Way Kanan", "Kota Bandar Lampung", "Kota Metro"
    };

    private String url = Konfigurasi.URL_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

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

        btn_register = (Button) findViewById(R.id.btnRegister);

        editText_nama = (EditText)findViewById(R.id.inputNama);
        editText_handphone = (EditText)findViewById(R.id.inputNoHP);
        editText_surel = (EditText)findViewById(R.id.inputEmail);
        editText_alamat = (EditText)findViewById(R.id.inputAlamat);
        editText_kabupaten = (EditText)findViewById(R.id.inputKabupaten);
        editText_kecamatan = (EditText)findViewById(R.id.inputKecamatan);
        spinner_gender = (Spinner) findViewById(R.id.spinner_gender);

        spinner_Provinsi = (Spinner) findViewById(R.id.spinner_provinsi);

        // Provinsi Banten
        spinner_kabupatenBanten = (Spinner)findViewById(R.id.spinner_kabupatenBanten);  spinner_kabupatenPandegelang = (Spinner)findViewById(R.id.spinner_pandegelang);
        spinner_kabupatenLebak = (Spinner)findViewById(R.id.spinner_lebak);             spinner_kabupatenTangerang = (Spinner)findViewById(R.id.spinner_tangerang);
        spinner_kabupatenSerang = (Spinner)findViewById(R.id.spinner_serang);           spinner_kotaCilegon = (Spinner)findViewById(R.id.spinner_cilegon);
        spinner_kotaSerang = (Spinner)findViewById(R.id.spinner_kotaSerang);            spinner_kotaTangerang = (Spinner)findViewById(R.id.spinner_kotaTangerang);
        spinner_kotaTangerangSelatan = (Spinner)findViewById(R.id.spinner_kotaTangsel);
        linear_banten = (LinearLayout)findViewById(R.id.linear_banten);
        //-------------------- BANTEN -------------------------------------------------------

        // Provinsi Jawa Barat
        spinner_kabupatenJawaBarat = (Spinner)findViewById(R.id.spinner_kabupatenJawaBarat);    spinner_kabupatenBandung = (Spinner)findViewById(R.id.spinner_bandung);
        spinner_kabupatenBandungBarat = (Spinner)findViewById(R.id.spinner_bandungBarat);       spinner_kabupatenBekasi = (Spinner)findViewById(R.id.spinner_bekasi);
        spinner_kabupatenBogor = (Spinner)findViewById(R.id.spinner_bogor);                     spinner_kabupatenCiamis = (Spinner)findViewById(R.id.spinner_ciamis);
        spinner_kabupatenCianjur = (Spinner)findViewById(R.id.spinner_cianjur);                 spinner_kabupatenCirebon = (Spinner)findViewById(R.id.spinner_cirebon);
        spinner_kabupatenGarut = (Spinner)findViewById(R.id.spinner_garut);                     spinner_kabupatenIndramayu = (Spinner)findViewById(R.id.spinner_indramayu);
        spinner_kabupatenKarawang = (Spinner)findViewById(R.id.spinner_karawang);               spinner_kabupatenKuningan = (Spinner)findViewById(R.id.spinner_kuningan);
        spinner_kabupatenMajalengka = (Spinner)findViewById(R.id.spinner_majalengka);           spinner_kabupatenPangandaran = (Spinner)findViewById(R.id.spinner_pangandaran);
        spinner_kabupatenPurwakarta = (Spinner)findViewById(R.id.spinner_purwakarta);           spinner_kabupatenSubang = (Spinner)findViewById(R.id.spinner_subang);
        spinner_kabupatenSukabumi = (Spinner)findViewById(R.id.spinner_sukabumi);               spinner_kabupatenSumedang = (Spinner)findViewById(R.id.spinner_sumedang);
        spinner_kabupatenTasikmalaya = (Spinner)findViewById(R.id.spinner_tasikmalaya);         spinner_kotaBandung = (Spinner)findViewById(R.id.spinner_kotaBandung);
        spinner_kotaBanjar = (Spinner)findViewById(R.id.spinner_kotaBanjar);                    spinner_kotaBekasi = (Spinner)findViewById(R.id.spinner_kotaBekasi);
        spinner_kotaBogor = (Spinner)findViewById(R.id.spinner_kotaBogor);                      spinner_kotaCimahi = (Spinner)findViewById(R.id.spinner_kotaCimahi);
        spinner_kotaCirebon = (Spinner)findViewById(R.id.spinner_kotaCirebon);                  spinner_kotaDepok = (Spinner)findViewById(R.id.spinner_kotaDepok);
        spinner_kotaSukabumi = (Spinner)findViewById(R.id.spinner_kotaSukabumi);                spinner_kotaTasikmalaya = (Spinner)findViewById(R.id.spinner_kotaTasikmalaya);
        linear_jabar = (LinearLayout)findViewById(R.id.linear_jabar);
        //-------------------- JABAR -------------------------------------------------------

        // Provinsi Jawa Tengah
        spinner_kabupatenJawaTengah = (Spinner)findViewById(R.id.spinner_kabupatenJawaTengah);
        spinner_kabupatenBanjarnegara = (Spinner)findViewById(R.id.spinner_banjarnegara);   spinner_kabupatenBanyumas  = (Spinner)findViewById(R.id.spinner_banyumas);
        spinner_kabupatenBatang = (Spinner)findViewById(R.id.spinner_batang);               spinner_kabupatenBlora = (Spinner)findViewById(R.id.spinner_blora);
        spinner_kabupatenBoyolali = (Spinner)findViewById(R.id.spinner_boyolali);           spinner_kabupatenBrebes = (Spinner)findViewById(R.id.spinner_brebes);
        spinner_kabupatenCilacap = (Spinner)findViewById(R.id.spinner_cilacap);             spinner_kabupatenDemak = (Spinner)findViewById(R.id.spinner_demak);
        spinner_kabupatenGrobogan = (Spinner)findViewById(R.id.spinner_grobogan);           spinner_kabupatenJepara = (Spinner)findViewById(R.id.spinner_jepara);
        spinner_kabupatenKaranganyar = (Spinner)findViewById(R.id.spinner_karanganyar);     spinner_kabupatenKebumen = (Spinner)findViewById(R.id.spinner_kebumen);
        spinner_kabupatenKendal = (Spinner)findViewById(R.id.spinner_kendal);               spinner_kabupatenKlaten = (Spinner)findViewById(R.id.spinner_klaten);
        spinner_kabupatenKudus = (Spinner)findViewById(R.id.spinner_kudus);                 spinner_kabupatenMagelang = (Spinner)findViewById(R.id.spinner_magelang);
        spinner_kabupatenPati = (Spinner)findViewById(R.id.spinner_pati);
        spinner_kabupatenPekalongan = (Spinner)findViewById(R.id.spinner_pekalongan);       spinner_kabupatenPemalang = (Spinner)findViewById(R.id.spinner_pemalang);
        spinner_kabupatenPurbalingga = (Spinner)findViewById(R.id.spinner_purbalingga);     spinner_kabupatenPurworejo = (Spinner)findViewById(R.id.spinner_purworejo);
        spinner_kabupatenRembang = (Spinner)findViewById(R.id.spinner_rembang);             spinner_kabupatenSemarang = (Spinner)findViewById(R.id.spinner_semarang);
        spinner_kabupatenSragen = (Spinner)findViewById(R.id.spinner_sragen);               spinner_kabupatenSukoharjo = (Spinner)findViewById(R.id.spinner_sukoharjo);
        spinner_kabupatenTegal = (Spinner)findViewById(R.id.spinner_tegal);                 spinner_kabupatenTemanggung = (Spinner)findViewById(R.id.spinner_temanggung);
        spinner_kabupatenWonogiri = (Spinner)findViewById(R.id.spinner_wonogiri);           spinner_kabupatenWonosobo = (Spinner)findViewById(R.id.spinner_wonosobo);
        spinner_kotaMagelang = (Spinner)findViewById(R.id.spinner_kotaMagelang);            spinner_kotaPekalongan = (Spinner)findViewById(R.id.spinner_kotaPekalongan);
        spinner_kotaSalatiga = (Spinner)findViewById(R.id.spinner_kotaSalatiga);            spinner_kotaSemarang = (Spinner)findViewById(R.id.spinner_kotaSemarang);
        spinner_kotaSurakarta = (Spinner)findViewById(R.id.spinner_kotaSurakarta);          spinner_kotaTegal = (Spinner)findViewById(R.id.spinner_kotaTegal);
        linear_jateng = (LinearLayout)findViewById(R.id.linear_jateng);
        //--------------------------------------------------------------- JATENG --------------------------------------------------------


        // Provinsi Lampung
        spinner_kabupatenLampung = (Spinner)findViewById(R.id.spinner_kabupatenLampung);            spinner_kabupatenLampungBarat = (Spinner)findViewById(R.id.spinner_lampungBarat);
        spinner_kabupatenLampungSelatan = (Spinner)findViewById(R.id.spinner_lampungSelatan);       spinner_kabupatenLampungTengah = (Spinner)findViewById(R.id.spinner_lampungTengah);
        spinner_kabupatenLampungTimur = (Spinner)findViewById(R.id.spinner_lampungTimur);           spinner_kabupatenLampungUtara = (Spinner)findViewById(R.id.spinner_lampungUtara);
        spinner_kabupatenMesuji = (Spinner)findViewById(R.id.spinner_mesuji);                       spinner_kabupatenPesawaran = (Spinner)findViewById(R.id.spinner_pesawaran);
        spinner_kabupatenPesisirBarat = (Spinner)findViewById(R.id.spinner_pesisirBarat);           spinner_kabupatenPringsewu = (Spinner)findViewById(R.id.spinner_pringsewu);
        spinner_kabupatenTanggamus = (Spinner)findViewById(R.id.spinner_tanggamus);                 spinner_kabupatenTulangBawang = (Spinner)findViewById(R.id.spinner_tulangBawang);
        spinner_kabupatenTulangBawangBarat = (Spinner)findViewById(R.id.spinner_tulangBawangBarat); spinner_kabupatenWayKanan = (Spinner)findViewById(R.id.spinner_wayKanan);
        spinner_kotaBandarLampung = (Spinner)findViewById(R.id.spinner_kotaBandarLampung);          spinner_kotaMetro = (Spinner)findViewById(R.id.spinner_kotaMetro);
        linear_lampung = (LinearLayout)findViewById(R.id.linear_lampung);
        //--------------------------------------------------------------- LAMPUNG --------------------------------------------------------

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, daftarProvinsi);
        spinner_Provinsi.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, daftarKabupatenBanten);
        spinner_kabupatenBanten.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, daftarKabupatenJawaBarat);
        spinner_kabupatenJawaBarat.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, daftarKabupatenJawaTengah);
        spinner_kabupatenJawaTengah.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, daftarKabupatenLampung);
        spinner_kabupatenLampung.setAdapter(adapter4);

        spinner_Provinsi.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner_Provinsi.getSelectedItem().equals("Banten")){
                    spinner_kabupatenJawaBarat.setVisibility(View.GONE);
                    spinner_kabupatenJawaTengah.setVisibility(View.GONE);
                    spinner_kabupatenLampung.setVisibility(View.GONE);
                    linear_lampung.setVisibility(View.GONE);
                    linear_jabar.setVisibility(View.GONE);
                    linear_jateng.setVisibility(View.GONE);

                    spinner_kabupatenBanten.setVisibility(View.VISIBLE);
                    linear_banten.setVisibility(View.VISIBLE);
                }
                else if (spinner_Provinsi.getSelectedItem().equals("Jawa Barat")){
                    spinner_kabupatenBanten.setVisibility(View.GONE);
                    spinner_kabupatenJawaTengah.setVisibility(View.GONE);
                    spinner_kabupatenLampung.setVisibility(View.GONE);
                    linear_lampung.setVisibility(View.GONE);
                    linear_banten.setVisibility(View.GONE);
                    linear_jateng.setVisibility(View.GONE);

                    spinner_kabupatenJawaBarat.setVisibility(View.VISIBLE);
                    linear_jabar.setVisibility(View.VISIBLE);
                }
                else if (spinner_Provinsi.getSelectedItem().equals("Jawa Tengah")){
                    spinner_kabupatenBanten.setVisibility(View.GONE);
                    spinner_kabupatenJawaBarat.setVisibility(View.GONE);
                    spinner_kabupatenLampung.setVisibility(View.GONE);
                    linear_lampung.setVisibility(View.GONE);
                    linear_banten.setVisibility(View.GONE);
                    linear_jabar.setVisibility(View.GONE);

                    spinner_kabupatenJawaTengah.setVisibility(View.VISIBLE);
                    linear_jateng.setVisibility(View.VISIBLE);
                }
                else if (spinner_Provinsi.getSelectedItem().equals("Lampung")){
                    spinner_kabupatenBanten.setVisibility(View.GONE);
                    spinner_kabupatenJawaBarat.setVisibility(View.GONE);
                    spinner_kabupatenJawaTengah.setVisibility(View.GONE);
                    linear_banten.setVisibility(View.GONE);
                    linear_jabar.setVisibility(View.GONE);
                    linear_jateng.setVisibility(View.GONE);

                    spinner_kabupatenLampung.setVisibility(View.VISIBLE);
                    linear_lampung.setVisibility(View.VISIBLE);

                }
                else if (spinner_Provinsi.getSelectedItem().equals("Pilih :")){
                    spinner_kabupatenBanten.setVisibility(View.GONE);
                    spinner_kabupatenJawaBarat.setVisibility(View.GONE);
                    spinner_kabupatenJawaTengah.setVisibility(View.GONE);
                    spinner_kabupatenLampung.setVisibility(View.GONE);
                    linear_banten.setVisibility(View.GONE);
                    linear_jabar.setVisibility(View.GONE);
                    linear_jateng.setVisibility(View.GONE);
                    linear_lampung.setVisibility(View.GONE);

                    editText_kecamatan.setText("");
                    editText_kabupaten.setText("");

                    Toast.makeText(Registrasi.this, "Harap Pilih Provinsi Tempat Anda Tinggal", Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        spinner_kabupatenBanten.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner_kabupatenBanten.getSelectedItem().equals("Kabupaten Pandegelang")) {

                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenTangerang.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kabupatenPandegelang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);


                    spinner_kabupatenPandegelang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPandegelang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                });

                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kabupaten Lebak")) {
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kabupatenTangerang.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kabupatenLebak.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kabupatenLebak.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenLebak_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kabupaten Tangerang")) {
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kabupatenTangerang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kabupatenTangerang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTangerang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kabupaten Serang")) {
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kabupatenTangerang.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kabupatenSerang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kabupatenSerang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSerang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kota Cilegon")) {
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kotaCilegon.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kotaCilegon.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaCilegon_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kota Serang")) {
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kotaSerang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kotaSerang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaSerang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kota Tangerang")) {
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    spinner_kotaTangerang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kotaTangerang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaTangerang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenBanten.getSelectedItem().equals("Kota Tangerang Selatan")) {
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);

                    spinner_kotaTangerangSelatan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenBanten[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenBanten[indeks]);

                    spinner_kotaTangerangSelatan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaTangerangSelatan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }

                else if (spinner_kabupatenBanten.getSelectedItem().equals("Pilih :")) {
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kabupatenPandegelang.setVisibility(View.GONE);
                    spinner_kabupatenLebak.setVisibility(View.GONE);
                    spinner_kabupatenSerang.setVisibility(View.GONE);
                    spinner_kotaCilegon.setVisibility(View.GONE);
                    spinner_kotaSerang.setVisibility(View.GONE);
                    spinner_kotaTangerang.setVisibility(View.GONE);
                    spinner_kotaTangerangSelatan.setVisibility(View.GONE);

                    editText_kabupaten.setText("");
                    editText_kecamatan.setText("");

                    Toast.makeText(Registrasi.this, "Harap Pilih Kabupaten Tempat Anda Tinggal", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){
            }
        });

        spinner_kabupatenJawaBarat.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Bandung")) {

                    spinner_kabupatenBandungBarat.setVisibility(View.GONE);         spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);                spinner_kabupatenCiamis.setVisibility(View.GONE);
                    spinner_kabupatenCianjur.setVisibility(View.GONE);              spinner_kabupatenCirebon.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);                spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);             spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);           spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);           spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);             spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE);          spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);                    spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);                     spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);                   spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);                  spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenBandung.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenBandung.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBandung_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Bandung Barat")) {
                    spinner_kabupatenBandung.setVisibility(View.GONE);              spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);                spinner_kabupatenCiamis.setVisibility(View.GONE);
                    spinner_kabupatenCianjur.setVisibility(View.GONE);              spinner_kabupatenCirebon.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);                spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);             spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);           spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);           spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);             spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE);          spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);                    spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);                     spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);                   spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);                  spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenBandungBarat.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenBandungBarat.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBandungBarat_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Bekasi")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE);         spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);                spinner_kabupatenCiamis.setVisibility(View.GONE);
                    spinner_kabupatenCianjur.setVisibility(View.GONE);              spinner_kabupatenCirebon.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);                spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);             spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);           spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);           spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);             spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE);          spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);                    spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);                     spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);                   spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);                  spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenBekasi.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenBekasi.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBekasi_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Bogor")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE);         spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenBekasi.setVisibility(View.GONE);               spinner_kabupatenCiamis.setVisibility(View.GONE);
                    spinner_kabupatenCianjur.setVisibility(View.GONE);              spinner_kabupatenCirebon.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);                spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);             spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);           spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);           spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);             spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE);          spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);                    spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);                     spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);                   spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);                  spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenBogor.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenBogor.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBogor_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Ciamis")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE);         spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);                spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCianjur.setVisibility(View.GONE);              spinner_kabupatenCirebon.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);                spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);             spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);           spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);           spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);             spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE);          spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);                    spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);                     spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);                   spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);                  spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenCiamis.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenCiamis.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenCiamis_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Cianjur")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE);     spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);            spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCirebon.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);        spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);     spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);   spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenCianjur .setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenCianjur.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenCianjur_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Cirebon")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenGarut.setVisibility(View.GONE);        spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);     spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);   spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenCirebon.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenCirebon.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenCirebon_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Garut")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenIndramayu.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);     spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);   spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenGarut.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenGarut.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenGarut_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }

                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Indramayu")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenKarawang.setVisibility(View.GONE);     spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);   spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenIndramayu.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenIndramayu.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenIndramayu_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Karawang")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKuningan.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);   spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);    spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenKarawang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenKarawang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKarawang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Kuningan")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenMajalengka.setVisibility(View.GONE);   spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenKuningan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenKuningan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKuningan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Majalengka")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenPangandaran.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenMajalengka.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenMajalengka.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenMajalengka_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Pangandaran")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPurwakarta.setVisibility(View.GONE);   spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenPangandaran.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenPangandaran.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPangandaran_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Purwakarta")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE);  spinner_kabupatenSubang.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenPurwakarta.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenPurwakarta.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPurwakarta_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Subang")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE);  spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSukabumi.setVisibility(View.GONE);     spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenSubang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenSubang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSubang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Sukabumi")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE);  spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);       spinner_kabupatenSumedang.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenSukabumi.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenSukabumi.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSukabumi_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Sumedang")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);       spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenTasikmalaya.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenSumedang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenSumedang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSumedang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kabupaten Tasikmalaya")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kotaBandung.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kabupatenTasikmalaya.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kabupatenTasikmalaya.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTasikmalaya_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Bandung")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBanjar.setVisibility(View.GONE);        spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaBandung.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaBandung.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaBandung_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Banjar")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBekasi.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaBanjar.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaBanjar.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaBanjar_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Bekasi")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBogor.setVisibility(View.GONE);         spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaBekasi.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaBekasi.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaBekasi_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Bogor")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaCimahi.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaBogor .setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaBogor.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaBogor_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Cimahi")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaBogor.setVisibility(View.GONE);
                    spinner_kotaCirebon.setVisibility(View.GONE);       spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaCimahi.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaCimahi.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaCimahi_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Cirebon")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaBogor.setVisibility(View.GONE);
                    spinner_kotaCimahi.setVisibility(View.GONE);        spinner_kotaDepok.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaCirebon.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaCirebon.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaCirebon_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Depok")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaBogor.setVisibility(View.GONE);
                    spinner_kotaCimahi.setVisibility(View.GONE);        spinner_kotaCirebon.setVisibility(View.GONE);
                    spinner_kotaSukabumi.setVisibility(View.GONE);      spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaDepok.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaDepok.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaDepok_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Sukabumi")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE);  spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaBogor.setVisibility(View.GONE);
                    spinner_kotaCimahi.setVisibility(View.GONE);        spinner_kotaCirebon.setVisibility(View.GONE);
                    spinner_kotaDepok.setVisibility(View.GONE);         spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    spinner_kotaSukabumi.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaSukabumi.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaSukabumi_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Kota Tasikmalaya")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaBogor.setVisibility(View.GONE);
                    spinner_kotaCimahi.setVisibility(View.GONE);        spinner_kotaCirebon.setVisibility(View.GONE);
                    spinner_kotaDepok.setVisibility(View.GONE);         spinner_kotaSukabumi.setVisibility(View.GONE);

                    spinner_kotaTasikmalaya.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaBarat[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaBarat[indeks]);

                    spinner_kotaTasikmalaya.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaTasikmalaya_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }

                else if (spinner_kabupatenJawaBarat.getSelectedItem().equals("Pilih :")) {
                    spinner_kabupatenBandungBarat.setVisibility(View.GONE); spinner_kabupatenBekasi.setVisibility(View.GONE);
                    spinner_kabupatenBogor.setVisibility(View.GONE);        spinner_kabupatenBandung.setVisibility(View.GONE);
                    spinner_kabupatenCiamis.setVisibility(View.GONE);       spinner_kabupatenCianjur.setVisibility(View.GONE);
                    spinner_kabupatenCirebon.setVisibility(View.GONE);      spinner_kabupatenGarut.setVisibility(View.GONE);
                    spinner_kabupatenIndramayu.setVisibility(View.GONE);    spinner_kabupatenKarawang.setVisibility(View.GONE);
                    spinner_kabupatenKuningan.setVisibility(View.GONE);     spinner_kabupatenMajalengka.setVisibility(View.GONE);
                    spinner_kabupatenPangandaran.setVisibility(View.GONE); spinner_kabupatenPurwakarta.setVisibility(View.GONE);
                    spinner_kabupatenSubang.setVisibility(View.GONE);   spinner_kabupatenSukabumi.setVisibility(View.GONE);
                    spinner_kabupatenSumedang.setVisibility(View.GONE); spinner_kabupatenTasikmalaya.setVisibility(View.GONE);
                    spinner_kotaBandung.setVisibility(View.GONE);       spinner_kotaBanjar.setVisibility(View.GONE);
                    spinner_kotaBekasi.setVisibility(View.GONE);        spinner_kotaBogor.setVisibility(View.GONE);
                    spinner_kotaCimahi.setVisibility(View.GONE);        spinner_kotaCirebon.setVisibility(View.GONE);
                    spinner_kotaDepok.setVisibility(View.GONE);         spinner_kotaSukabumi.setVisibility(View.GONE);
                    spinner_kotaTasikmalaya.setVisibility(View.GONE);

                    editText_kabupaten.setText("");
                    editText_kecamatan.setText("");

                    Toast.makeText(Registrasi.this, "Harap Pilih Kabupaten Tempat Anda Tinggal", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){
            }
        });

        spinner_kabupatenJawaTengah.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Banjarnegara")) {
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenBanjarnegara.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenBanjarnegara.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBanjarnegara_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Banyumas")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenBanyumas.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenBanyumas.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBanyumas_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Batang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenBatang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenBatang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBatang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Blora")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenBlora.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenBlora.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBlora_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Boyolali")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenBoyolali.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenBoyolali.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBoyolali_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Brebes")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenBrebes.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenBrebes.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenBrebes_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Cilacap")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenCilacap.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenCilacap.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenCilacap_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Demak")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenDemak.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenDemak.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenDemak_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Grobogan")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenGrobogan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenGrobogan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenGrobogan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Jepara")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenJepara.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenJepara.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenJepara_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Karanganyar")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenKaranganyar.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenKaranganyar.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKaranganyar_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Kebumen")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenKebumen.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenKebumen.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKebumen_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Kendal")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenKendal.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenKendal.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKendal_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Klaten")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenKlaten.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenKlaten.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKlaten_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Kudus")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenKudus.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenKudus.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenKudus_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Magelang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenMagelang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenMagelang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenMagelang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Pati")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenPati.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenPati.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPati_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Pekalongan")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenPekalongan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenPekalongan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPekalongan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Pemalang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenPemalang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenPemalang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPemalang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Purbalingga")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenPurbalingga.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenPurbalingga.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPurbalingga_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Purworejo")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenPurworejo.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenPurworejo.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPurworejo_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Rembang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenRembang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenRembang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenRembang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Semarang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenSemarang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenSemarang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSemarang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Sragen")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenSragen.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenSragen.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSragen_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Sukoharjo")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenSukoharjo.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenSukoharjo.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenSukoharjo_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Tegal")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenTegal.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenTegal.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTegal_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Temanggung")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenTemanggung.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenTemanggung.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTemanggung_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Wonogiri")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenWonogiri.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenWonogiri.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenWonogiri_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kabupaten Wonosobo")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kabupatenWonosobo.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kabupatenWonosobo.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenWonosobo_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kota Magelang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kotaMagelang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kotaMagelang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaMagelang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kota Pekalongan")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kotaPekalongan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kotaPekalongan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaPekalongan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kota Salatiga")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kotaSalatiga.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kotaSalatiga.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaSalatiga_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kota Semarang")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kotaSemarang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kotaSemarang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaSemarang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kota Surakarta")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaTegal.setVisibility(View.GONE);

                    spinner_kotaSurakarta.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kotaSurakarta.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaSurakarta_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Kota Tegal")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);

                    spinner_kotaTegal.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenJawaTengah[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenJawaTengah[indeks]);

                    spinner_kotaTegal.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaTegal_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }

                else if (spinner_kabupatenJawaTengah.getSelectedItem().equals("Pilih :")) {
                    spinner_kabupatenBanjarnegara.setVisibility(View.GONE);
                    spinner_kabupatenBanyumas.setVisibility(View.GONE);         spinner_kabupatenBatang.setVisibility(View.GONE);
                    spinner_kabupatenBlora.setVisibility(View.GONE);            spinner_kabupatenBoyolali.setVisibility(View.GONE);
                    spinner_kabupatenBrebes.setVisibility(View.GONE);           spinner_kabupatenCilacap.setVisibility(View.GONE);
                    spinner_kabupatenDemak.setVisibility(View.GONE);            spinner_kabupatenGrobogan.setVisibility(View.GONE);
                    spinner_kabupatenJepara.setVisibility(View.GONE);           spinner_kotaSemarang.setVisibility(View.GONE);
                    spinner_kabupatenKaranganyar.setVisibility(View.GONE);      spinner_kabupatenKebumen.setVisibility(View.GONE);
                    spinner_kabupatenKendal.setVisibility(View.GONE);           spinner_kabupatenKlaten.setVisibility(View.GONE);
                    spinner_kabupatenKudus.setVisibility(View.GONE);            spinner_kabupatenMagelang.setVisibility(View.GONE);
                    spinner_kabupatenPati.setVisibility(View.GONE);             spinner_kabupatenPekalongan.setVisibility(View.GONE);
                    spinner_kabupatenPemalang.setVisibility(View.GONE);         spinner_kabupatenPurbalingga.setVisibility(View.GONE);
                    spinner_kabupatenPurworejo.setVisibility(View.GONE);        spinner_kabupatenRembang.setVisibility(View.GONE);
                    spinner_kabupatenSemarang.setVisibility(View.GONE);         spinner_kabupatenSragen.setVisibility(View.GONE);
                    spinner_kabupatenSukoharjo.setVisibility(View.GONE);        spinner_kabupatenTegal.setVisibility(View.GONE);
                    spinner_kabupatenTemanggung.setVisibility(View.GONE);       spinner_kabupatenWonogiri.setVisibility(View.GONE);
                    spinner_kabupatenWonosobo.setVisibility(View.GONE);         spinner_kotaMagelang.setVisibility(View.GONE);
                    spinner_kotaPekalongan.setVisibility(View.GONE);            spinner_kotaSalatiga.setVisibility(View.GONE);
                    spinner_kotaSurakarta.setVisibility(View.GONE);             spinner_kotaTegal.setVisibility(View.GONE);

                    editText_kecamatan.setText("");
                    editText_kabupaten.setText("");

                    Toast.makeText(Registrasi.this, "Harap Pilih Kabupaten Tempat Anda Tinggal", Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){
            }
        });

        spinner_kabupatenLampung.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Lampung Barat")) {

                    spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenLampungBarat.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenLampungBarat.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenLampungBarat_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Lampung Selatan")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenLampungSelatan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenLampungSelatan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenLampungSelatan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Lampung Tengah")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenLampungTengah.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenLampungTengah.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenLampungTengah_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Lampung Timur")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenLampungTimur.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenLampungTimur.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenLampungTimur_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Lampung Utara")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenLampungUtara.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenLampungUtara.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenLampungUtara_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Mesuji")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenMesuji.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenMesuji.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenMesuji_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Pesawaran")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);

                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenPesawaran.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenPesawaran.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPesawaran_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Pesisir Barat")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenPesisirBarat.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenPesisirBarat.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPesisirBarat_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Pringsewu")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenPringsewu.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenPringsewu.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenPringsewu_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Tanggamus")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenTanggamus.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenTanggamus.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTanggamus_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Tulang Bawang")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenTulangBawang.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenTulangBawang.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTulangBawang_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Tulang Bawang Barat")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenTulangBawangBarat.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenTulangBawangBarat.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenTulangBawangBarat_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kabupaten Way Kanan")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kabupatenWayKanan.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kabupatenWayKanan.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKabupatenWayKanan_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kota Bandar Lampung")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaMetro.setVisibility(View.GONE);

                    spinner_kotaBandarLampung.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kotaBandarLampung.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaBandarLampung_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }
                else if (spinner_kabupatenLampung.getSelectedItem().equals("Kota Metro")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);

                    spinner_kotaMetro.setVisibility(View.VISIBLE);

                    int indeks = parent.getSelectedItemPosition();
                    Toast.makeText(getApplication(), "Anda Memilih : " + daftarKabupatenLampung[indeks], Toast.LENGTH_SHORT).show();
                    editText_kabupaten.setText("");
                    editText_kabupaten.setText(daftarKabupatenLampung[indeks]);

                    spinner_kotaMetro.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int indeks2 = parent.getSelectedItemPosition();
                            Resources res = getResources();
                            String[] planets = res.getStringArray(R.array.kecamatanKotaMetro_array);
                            editText_kecamatan.setText(planets[indeks2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }

                    });
                }

                else if (spinner_kabupatenLampung.getSelectedItem().equals("Pilih :")) {
                    spinner_kabupatenLampungBarat.setVisibility(View.GONE);         spinner_kabupatenLampungSelatan.setVisibility(View.GONE);
                    spinner_kabupatenLampungTengah.setVisibility(View.GONE);        spinner_kabupatenLampungTimur.setVisibility(View.GONE);
                    spinner_kabupatenLampungUtara.setVisibility(View.GONE);         spinner_kabupatenMesuji.setVisibility(View.GONE);
                    spinner_kabupatenPesawaran.setVisibility(View.GONE);
                    spinner_kabupatenPesisirBarat.setVisibility(View.GONE);         spinner_kabupatenPringsewu.setVisibility(View.GONE);
                    spinner_kabupatenTanggamus.setVisibility(View.GONE);            spinner_kabupatenTulangBawang.setVisibility(View.GONE);
                    spinner_kabupatenTulangBawangBarat.setVisibility(View.GONE);    spinner_kabupatenWayKanan.setVisibility(View.GONE);
                    spinner_kotaBandarLampung.setVisibility(View.GONE);             spinner_kotaMetro.setVisibility(View.GONE);

                    editText_kecamatan.setText("");
                    editText_kabupaten.setText("");

                    Toast.makeText(Registrasi.this, "Harap Pilih Kabupaten Tempat Anda Tinggal", Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0){
            }
        });

/*
        if (editText_kabupaten.getText().toString().isEmpty() && editText_kecamatan.getText().toString().isEmpty()){
            btn_register.setVisibility(View.GONE);
        }
        else if(!editText_kabupaten.getText().toString().isEmpty() && !editText_kecamatan.getText().toString().isEmpty()){
            btn_register.setVisibility(View.VISIBLE);
        }
        */


        btn_register.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                String nama = editText_nama.getText().toString();
                String hp = editText_handphone.getText().toString();
                String surel = editText_surel.getText().toString();
                String gender = spinner_gender.getSelectedItem().toString();

                String alamat = editText_alamat.getText().toString();
                String provinsi = spinner_Provinsi.getSelectedItem().toString();

                String kabupaten = editText_kabupaten.getText().toString();
                String kecamatan = editText_kecamatan.getText().toString();

                if (nama.trim().length() > 0 && hp.trim().length() > 0 && surel.trim().length() > 0 && alamat.trim().length() > 0 &&
                        kabupaten.trim().length() > 0  && kecamatan.trim().length() > 0 ) {

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(surel).matches()) {
                            editText_surel.setError("Enter a valid email");
                            editText_surel.requestFocus();
                            return;
                        }

                        //manggil checkRegister
                        checkRegister(nama, hp, surel, gender, alamat, provinsi, kabupaten, kecamatan);
                        Toast.makeText(Registrasi.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Registrasi.this, MainActivityLogin.class);
                       // finish();
                        startActivity(intent);


                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //Jika yang diisi kosong.. maka akan muncul notifikasi error
                    if (TextUtils.isEmpty(nama)) {
                        editText_nama.setError("Harap Masukkan Nama Lengkap Anda");
                        editText_nama.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(hp)) {
                        editText_handphone.setError("Harap Masukkan Nomor Handphone Anda");
                        editText_handphone.requestFocus();
                        return;
                    }
                    if (hp.trim().length()<=10) {
                        editText_handphone.setError("Harap Masukkan Nomor Handphone Anda Dengan Rentang 10-14 Angka");
                        editText_handphone.requestFocus();
                        return;
                    }
                    if (hp.contains(" ")) {
                        editText_handphone.setError("Tanda Spasi Tidak Boleh Digunakan");
                        editText_handphone.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(surel)) {
                        editText_surel.setError("Harap Masukkan Alamat Email Anda");
                        editText_surel.requestFocus();
                        return;

                    }
                    if (surel.contains(" ")) {
                        editText_surel.setError("Tanda Spasi Tidak Boleh Digunakan");
                        editText_surel.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(alamat)) {
                        editText_alamat.setError("Harap Masukkan Alamat Anda");
                        editText_alamat.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(kabupaten)) {
                        Toast.makeText(Registrasi.this, "Anda Belum Memilih Kabupaten Tempat Anda Tinggal", Toast.LENGTH_LONG).show();
                        return;

                    }
                    if (TextUtils.isEmpty(kecamatan)) {
                        Toast.makeText(Registrasi.this, "Anda Belum Memilih Kecamatan Tempat Anda Tinggal", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
    private void checkRegister(final String nama, final String hp, final String surel, final String gender, final String alamat, final String provinsi,
                               final String kabupaten, final String kecamatan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

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

                        //jika sudah mengisi maka akan direset lagi data-data nya
                        //sehingga di editText jadi kosong lagi
                        editText_nama.setText("");
                        editText_handphone.setText("");
                        editText_surel.setText("");
                        editText_alamat.setText("");
                        editText_kabupaten.setText("");
                        editText_kecamatan.setText("");

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
                params.put(Konfigurasi.KEY_REG_NAMA, nama);
                params.put(Konfigurasi.KEY_REG_HP, hp);
                params.put(Konfigurasi.KEY_REG_SUREL, surel);
                params.put(Konfigurasi.KEY_REG_GENDER, gender);
                params.put(Konfigurasi.KEY_REG_ALAMAT, alamat);
                params.put(Konfigurasi.KEY_REG_PROVINSI, provinsi);
                params.put(Konfigurasi.KEY_REG_KABUPATEN, kabupaten);
                params.put(Konfigurasi.KEY_REG_KECAMATAN, kecamatan);

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

    @Override
    public void onBackPressed() {
        intent = new Intent(Registrasi.this, MainActivityLogin.class);
        finish();
        startActivity(intent);
    }

}
