package com.example.android.iotdevice;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class WifiListActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    WifiManager WifiObj;
    WifiInfo wifiInfo;
    String wifis[];
    ArrayList<String> words = new ArrayList<>();
    ArrayAdapter<String> itemsAdapter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            Toast.makeText(getApplicationContext(), "Starting to scan", Toast.LENGTH_SHORT).show();
            WifiObj.startScan();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);

        WifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiScanReceiver wifiReciever;
        wifiInfo = WifiObj.getConnectionInfo();
        wifiReciever = new WifiScanReceiver();


         itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);

        ListView listView = (ListView) findViewById(R.id.wifiListView);
        listView.setAdapter(itemsAdapter);

        itemsAdapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                WifiConfiguration wc = new WifiConfiguration();
                wc.SSID = "\"IOTdeviceAP\"";
                wc.preSharedKey  = "\"SecretPassword\"";
                wc.hiddenSSID = false;
                wc.status = WifiConfiguration.Status.ENABLED;
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                int res = WifiObj.addNetwork(wc);
                Log.d("WifiPreference", "add Network returned " + res );
                boolean b = false;
                while (b == false) {
                    b = WifiObj.enableNetwork(res, true);
                }
                Log.d("WifiPreference", "enableNetwork returned " + b );

                if(b == true) {
                    Intent deviceSetupIntent = new Intent(WifiListActivity.this, DeviceSetupActivity.class);
                    startActivity(deviceSetupIntent);
                }


            }
        });

        registerReceiver(wifiReciever, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


        if (WifiObj.isWifiEnabled() == false) {
            Toast.makeText(getApplicationContext(), "Wifi is disabled ....making it enabled", Toast.LENGTH_SHORT).show();
            WifiObj.setWifiEnabled(true);
        }


        if (WifiObj.isScanAlwaysAvailable()) {
            ActivityCompat.requestPermissions(WifiListActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    public class WifiScanReceiver extends BroadcastReceiver {
        @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = WifiObj.getScanResults();
            Toast.makeText(getApplicationContext(), "Found devices", Toast.LENGTH_SHORT).show();
            wifis = new String[wifiScanList.size()];
            itemsAdapter.clear();
            for (int i = 0; i < wifiScanList.size(); i++) {
                wifis[i] = ((wifiScanList.get(i)).SSID);
                if(wifis[i].equals("IOTdeviceAP")) {
                    words.add(wifis[i]);
                    itemsAdapter.notifyDataSetChanged();
                }
                //itemsAdapter.notifyDataSetChanged();
                //itemsAdapter.add(wifis[i]);
            }

        }
    }
}









