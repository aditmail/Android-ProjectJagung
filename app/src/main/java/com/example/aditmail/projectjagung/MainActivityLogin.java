package com.example.aditmail.projectjagung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
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


public class MainActivityLogin extends AppCompatActivity {

    ProgressDialog pDialog;

    Button btn_login;
    TextView btn_register;
    EditText txt_noTelp, txt_email;

    Intent intent;

    int success;

    ConnectivityManager conMgr;

    private String url = Konfigurasi.URL_LOGIN;

    private static final String TAG = MainActivityLogin.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;

    Boolean session = false;

    String id, name, surel;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

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

        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_register = (TextView) findViewById(R.id.textView_Registrasi);

        txt_noTelp = (EditText) findViewById(R.id.editText_LoginNoTelp);
        txt_email = (EditText) findViewById(R.id.editText_LoginEmail);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        id = sharedpreferences.getString(Konfigurasi.KEY_REG_ID, null);
        name = sharedpreferences.getString(Konfigurasi.KEY_REG_NAMA, null);
        surel = sharedpreferences.getString(Konfigurasi.KEY_REG_SUREL, null);

        if (session) {
            Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
            intent.putExtra(Konfigurasi.KEY_REG_ID, id);
            intent.putExtra(Konfigurasi.KEY_REG_NAMA, name);
            intent.putExtra(Konfigurasi.KEY_REG_SUREL, surel);

            startActivity(intent);
            finish();
        }


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String noTelepon = txt_noTelp.getText().toString();
                String surel = txt_email.getText().toString();

                // mengecek kolom yang kosong
                //jika ga kosong.. lanjut
                if (noTelepon.trim().length() > 0 && surel.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {

                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(surel).matches()) {
                           txt_email.setError("Enter a valid email");
                           txt_email.requestFocus();
                            return;
                        }

                        checkLogin(noTelepon, surel);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (TextUtils.isEmpty(noTelepon)) {
                        txt_noTelp.setError("Harap Masukkan Nomor Telepon Anda");
                        txt_noTelp.requestFocus();
                        return;
                    }
                    if (noTelepon.trim().length()<=10) {
                        txt_noTelp.setError("Harap Masukkan Nomor Handphone Anda Dengan Rentang 10-14 Angka");
                        txt_noTelp.requestFocus();
                        return;
                    }
                    if (noTelepon.contains(" ")) {
                        txt_noTelp.setError("Tanda Spasi Tidak Boleh Digunakan");
                        txt_noTelp.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(surel)) {
                        txt_email.setError("Harap Masukkan Alamat Email Anda");
                        txt_email.requestFocus();
                        return;
                    }
                    if (surel.contains(" ")) {
                        txt_email.setError("Tanda Spasi Tidak Boleh Digunakan");
                        txt_email.requestFocus();
                    }

                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(MainActivityLogin.this, Registrasi.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void checkLogin(final String noTelepon, final String surel) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String name = jObj.getString(Konfigurasi.KEY_REG_NAMA);
                        String id = jObj.getString(Konfigurasi.KEY_REG_ID);
                        String email = jObj.getString(Konfigurasi.KEY_REG_SUREL);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);

                        editor.putString(Konfigurasi.KEY_REG_ID, id);
                        editor.putString(Konfigurasi.KEY_REG_NAMA, name);
                        editor.putString(Konfigurasi.KEY_REG_SUREL, email);
                        editor.commit();

                        // Memanggil menu utama
                        Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
                        intent.putExtra(Konfigurasi.KEY_REG_ID, id);
                        intent.putExtra(Konfigurasi.KEY_REG_NAMA, name);
                        intent.putExtra(Konfigurasi.KEY_REG_SUREL, email);
                        startActivity(intent);
                        finish();

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
                Map<String, String> params = new HashMap<String, String>();
                params.put(Konfigurasi.KEY_REG_HP, noTelepon);
                params.put(Konfigurasi.KEY_REG_SUREL, surel);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    //buat nampilin dialog box nyaa
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    //buat ngilangin dialog box nya
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
