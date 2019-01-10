package dvorak.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.EditText;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.*;
import android.content.BroadcastReceiver;
//import dvorak.app.Network.*;

public abstract class Main extends AppCompatActivity {

    /* 002 */
    Button btnOnOff, btnDiscover, btnSend;
    ListView listView;
    TextView read_msg_box, connectionStatus;
    EditText writeMsg;

    /* 003 */
    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    // end 002, 003

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setup(); // 002
        clickListener(); // 002
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /* 002 */
    private void setup() {
        btnOnOff = findViewById(R.id.atn_direct_enable);
        btnDiscover = findViewById(R.id.discover);
        btnSend = findViewById(R.id.sendButton);
        listView = findViewById(R.id.peerListView);
        read_msg_box = findViewById(R.id.readMsg);
        connectionStatus = findViewById(R.id.connectionStatus);
        writeMsg = findViewById(R.id.writeMsg);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        /* 003 */
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize( this.getMainLooper(), null );

        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction( WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION );
        mIntentFilter.addAction( WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION );
        mIntentFilter.addAction( WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION );
        mIntentFilter.addAction( WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION );
    }

    /* 002 */
    private void clickListener() {
        btnOnOff.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    if (wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(false);
                        btnOnOff.setText("ON");
                    }
                    else {
                        wifiManager.setWifiEnabled(true);
                        btnOnOff.setText("OFF");
                    }
            }

        });
    }

    /* 003 */
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }



    /* 003 */
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
