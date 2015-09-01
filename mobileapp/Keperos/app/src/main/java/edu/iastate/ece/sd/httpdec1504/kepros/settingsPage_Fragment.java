package edu.iastate.ece.sd.httpdec1504.kepros;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import java.util.Set;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.*;


import static android.R.layout.simple_list_item_1;

/**
 * Created by wipark on 8/25/15.
 */
public class settingsPage_Fragment extends android.support.v4.app.Fragment {
    View rootview;
    Button BluetoothMenu;
    View v;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.settingspage_layout, container, false);

        //BluetoothMenu.setId(R.id.buttonBTSwitch);
        BluetoothMenu = (Button) rootview.findViewById(R.id.buttonBTSwitch);

       // BluetoothMenu.findViewById(R.id.buttonBTSwitch);
        BluetoothMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BTButton", "The button was pressed");

                Intent intent = new Intent(getActivity(), Bluetooth.class);
                startActivity(intent);


            }
        });

        return rootview;
    }







}









