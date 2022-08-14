package com.example.jsonapi;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Task extends AsyncTask<String, String, String> {

    String apiUrl = "http://ec2-65-0-17-235.ap-south-1.compute.amazonaws.com:8080/";
    String roadAd = "gc/";
    String latAndLong = "rgc/";
    String address = "충청남도천안시서북구부대동275";
    String str, receiveMsg;
    String data;

    @Override
    protected String doInBackground(String... params) {
        URL roadUrl = null;

        try{
            roadUrl = new URL(apiUrl+roadAd+address+"?format=json");

            HttpURLConnection conn = (HttpURLConnection) roadUrl.openConnection();

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("ReceiveMsg : ", receiveMsg);

                JSONObject jsonObject = new JSONObject(receiveMsg);

                data = jsonObject.getString("data");

                reader.close();
            }else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }


        return data;
    }
}
