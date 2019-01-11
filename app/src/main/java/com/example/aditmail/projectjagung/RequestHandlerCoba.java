package com.example.aditmail.projectjagung;

/**
 * Created by ADITMAIL on 04/02/2018.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandlerCoba {

    public String sendGetRequestParam(String requestURL, String panen_id){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + panen_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();
    }

    public String sendGetRequestParam2(String requestURL, String pupuk_id){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + pupuk_id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();
    }

    public String sendGetRequestParam3(String requestURL, String panen_ID){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + panen_ID);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();
    }

    public String sendGetRequestParam4(String requestURL, String id_pelanggan){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + id_pelanggan);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();
    }

   public String sendGetRequestParam5(String requestURL, String id_pelanggan, String periode){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + id_pelanggan + "&periode=" + periode);
           // URL url = new URL(requestURL + id_pelanggan);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();
    }


}
