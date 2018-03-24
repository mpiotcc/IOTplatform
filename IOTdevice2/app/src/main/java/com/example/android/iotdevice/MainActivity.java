package com.example.android.iotdevice;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button addNewDevice = (Button)findViewById((R.id.newDeviceButton));

       addNewDevice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            Intent addNewDeviceIntent = new Intent(MainActivity.this,WifiListActivity.class);
            startActivity(addNewDeviceIntent);
           }
       });

    }


}
