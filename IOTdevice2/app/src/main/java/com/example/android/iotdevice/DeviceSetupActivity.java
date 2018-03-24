package com.example.android.iotdevice;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DeviceSetupActivity extends AppCompatActivity {

    byte[] buf = new byte[1024];
    int i = 0;
    String s = "";
    TCPclient mTcpClient;
    Device newDevice = new Device("a","a","a");
    String newDevicePacket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setup);

        final EditText devSSID = (EditText) findViewById(R.id.etSSID);
        final EditText devPass = (EditText) findViewById(R.id.etPassword);
        final EditText devName = (EditText) findViewById(R.id.etDeviceName);
        Button   btSubmit =  (Button)findViewById(R.id.btnSubmit);
        final TextView submitStatus = (TextView)findViewById(R.id.tvStatus);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new ConnectTask().execute("");



        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTcpClient != null) {

                    newDevice.setName(devName.getText().toString());
                    newDevice.setPassword(devPass.getText().toString());
                    newDevice.setSsid(devSSID.getText().toString());

                    try {
                        mTcpClient.sendMessage(newDevice.sendDevInfo());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    public class ConnectTask extends AsyncTask<String, String, TCPclient> {

        @Override
        protected TCPclient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TCPclient(new TCPclient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

        }
    }
}
