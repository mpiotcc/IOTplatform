package com.example.android.iotdevice;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mantas on 19/03/2018.
 */

public class Device {

    private String name;
    private String ssid;
    private String password;

    public Device(String n, String ss, String pass)
    {
        this.name = n;
        this.ssid = ss;
        this.password = pass;
    }

    public String sendDevInfo() throws JSONException {
        JSONObject jsonDevInfo = new JSONObject();
        jsonDevInfo.put("name", this.getName()); // Set the first name/pair
        jsonDevInfo.put("ssid", this.getssid()); // Set the first name/pair
        jsonDevInfo.put("password", this.getpassword()); // Set the first name/pair


        return jsonDevInfo.toString();

    }

   public String getName(){
        return this.name;
   }
    public String getssid(){
        return this.ssid;
    }
    public String getpassword(){
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
