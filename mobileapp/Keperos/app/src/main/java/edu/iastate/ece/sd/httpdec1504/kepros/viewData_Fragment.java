package edu.iastate.ece.sd.httpdec1504.kepros;

import android.app.Fragment;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by wipark on 8/25/15.
 */
public class viewData_Fragment extends android.support.v4.app.Fragment {

    View rootview;

    Button btDataOn, btDataOff;
    TextView txtArduino, txtString, txtStringLength, bluetoothDataRec;
    Handler bluetoothIn;
    ArrayList<String> mylist = new ArrayList<String>();




    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private BluetoothDevice myBTDevice;
    private UUID MY_UUID;
    private TextView text;
    private ArrayAdapter<String> CheckAdapter;
    ListView list;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           mylist.add((String)msg.obj);
        }
    };

    private StringBuilder recDataString = new StringBuilder();
    //private ConnectedThread mConnectedThread;

    // String for MAC address
    private static String address;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.viewdata_layout, container, false);
        return rootview;


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        CheckAdapter = new ArrayAdapter<String>(rootview.getContext(), android.R.layout.simple_list_item_1);
        list = (ListView) rootview.findViewById(R.id.datalist);
        list.setAdapter(CheckAdapter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().compareTo("Posture") == 0) {
                    myBTDevice=device;
                    CheckAdapter.add(device.getName());
                }
            }
        }
        CheckAdapter.add(MY_UUID.toString());

        mHandler.postDelayed(stringToList, 10);
        ConnectThread();
        run();
    }

    private Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                while (true) {
                    sleep(1000);
                    manageConnectedSocket();;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    public void ConnectThread() {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = myBTDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            mylist.add("tmp = myBTDevice.createRfcommSocketToServiceRecord(MY_UUID);");
        } catch (IOException e) { }
        btSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        btAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            btSocket.connect();
            mylist.add("btSocket.connect();");
            thread.start();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                btSocket.close();
                mylist.add("Closed");
            } catch (IOException closeException) { }
            return;
        }

    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException e) { }
    }

    public void manageConnectedSocket(){

        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        while (true) {
            try {
                bytes = btSocket.getInputStream().read(buffer);
                mHandler.obtainMessage(1, bytes, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }

    private Runnable stringToList = new Runnable() {
        public void run() {
            for (int i = 0; i<mylist.size(); i++) {
                CheckAdapter.add(mylist.get(i));
            }
            mylist.clear();
            mHandler.postDelayed(stringToList, 10);
        }
    };

    private Runnable stop = new Runnable() {
        public void run() {
           cancel();
        }
    };


}
