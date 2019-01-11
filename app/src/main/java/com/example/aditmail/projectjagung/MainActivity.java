package com.example.aditmail.projectjagung;

import android.app.ProgressDialog;

import android.app.Fragment;
import android.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.view.View;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public int count=0;
    int tempInt = 0;

    ProgressDialog pDialog;

    private static final String TAG = MainActivity.class.getSimpleName();

    int success;

    String tag_json_obj = "json_obj_req";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    SharedPreferences sharedpreferences;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    TextView txt_id, txt_username, txt_email;

    public static String id_pelanggan;
    public static String username;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MainActivity.my_shared_preferences, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
          //  @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
      //      }
     //   });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //View header = navigationView.inflateHeaderView(R.layout.nav_header_main);

        txt_id = (TextView)findViewById(R.id.textView_id);
        //txt_username = header.findViewById(R.id.textView_username);
        //txt_email = header.findViewById(R.id.textView_email);

        id_pelanggan = getIntent().getStringExtra(Konfigurasi.KEY_REG_ID);
        username = getIntent().getStringExtra(Konfigurasi.KEY_REG_NAMA);
        email = getIntent().getStringExtra(Konfigurasi.KEY_REG_SUREL);

        txt_id.setText("ID: " + id_pelanggan);
        txt_username.setText(username);
        txt_email.setText(email);
/*
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, DataLahan.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();
                */
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_layout_satu) {
           /*
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FragmentSatu())
                    .commit();
                    */
            Intent intent = new Intent(MainActivity.this, DataLahan.class);
            startActivity(intent);

        } else if (id == R.id.nav_layout_pupuk) {
            /*
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FragmentDua())
                    .commit();
                    */
            //Intent intent = new Intent(MainActivity.this, InputHasil.class);
            Intent intent = new Intent(MainActivity.this, InputPupukBenih.class);
            //InputPupukBenih.test.setVisibility(View.VISIBLE);
            startActivity(intent);

        }else if (id == R.id.nav_layout_dua) {

            Intent dataHasilPanen = new Intent (MainActivity.this, InputHasilPanen.class);
            startActivity(dataHasilPanen);

        }else if (id == R.id.nav_layout_empat) {

            Intent dataProfil = new Intent (MainActivity.this, CariPeriode.class);
            startActivity(dataProfil);

        }else if (id == R.id.nav_layout_tiga) {

            Intent dataProfil = new Intent (MainActivity.this, updateProfil.class);
            startActivity(dataProfil);

        } else if (id == R.id.nav_log_out) {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(MainActivity.session_status, false);
            editor.putString(Konfigurasi.KEY_REG_ID, null);
            editor.putString(Konfigurasi.KEY_REG_NAMA, null);
            editor.commit();

            //kembali ke main Activity
            Intent intent = new Intent(MainActivity.this, MainActivityLogin.class);
            finish();
            startActivity(intent);


        } else if (id == R.id.nav_feedback) {
            Intent kritikSaran = new Intent (MainActivity.this, KritikSaran.class);
            startActivity(kritikSaran);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
