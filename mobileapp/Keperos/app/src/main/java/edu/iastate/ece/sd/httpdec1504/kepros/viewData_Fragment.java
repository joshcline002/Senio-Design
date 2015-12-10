package edu.iastate.ece.sd.httpdec1504.kepros;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.Scanner;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

/**
 * Created by wipark on 8/25/15.
 */
public class viewData_Fragment extends android.support.v4.app.Fragment {

    View rootview;
    ViewGroup view;
    Button btDataOn, btDataOff;
    TextView txtArduino, txtString, txtStringLength, bluetoothDataRec;
    Handler bluetoothIn;
    ArrayList<String> mylist = new ArrayList<String>();




    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private BluetoothDevice myBTDevice;
    private UUID MY_UUID;
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<DataPoint>();
    GraphView graph1;
    GraphView graph2;
    int x = 0;
    Button btn;
    boolean notpaused;

    MySQLiteHelper db;


    public int[][] EMGdata1 = new int [100][1];  // EMG Data is ... 1 number? not sure.
    public int[][] EMGdata2 = new int [100][1];

    public int[][] IMUdata1 = new int [100][6]; // IMU data holding 100 values of 6, AclX | AclY | AclZ | GyX | GyY | GyZ
    public int[][] IMUdata2 = new int [100][6];

    String stringtoParse = "";

    public int numberCount = 7; // start it at outside number from switch statements
    public int dataCount = 0; // start at array indicator 0. go to 100 then change to 0
    public boolean switchCount = false;



    private InputStream mmInStream;
    private OutputStream mmOutStream;

    // Will Attempt

    Handler bluetoothHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            int bytes = msg.arg1;
            byte[] buff = new byte[bytes];
            buff = (byte[])msg.obj;
            //Log.d("bytes", bytes + " " + buff[11] + " " + buff[10]);
            if(buff[11] == (-1) && buff[10] != -1) {
                int EMG1 = ((buff[bytes - 11] << 8) | buff[bytes - 10]);
                //Log.d("HEMG1 ", " " + EMG1);
                int EMG2 = ((buff[bytes - 9] << 8) | buff[bytes - 8]);
                //Log.d("HEMG2 ", " " + EMG2);
                int GYRO_DEGREE_X = buff[bytes - 7];
                //Log.d("HGYRO DEGREE X ", " " + GYRO_DEGREE_X);
                int GYRO_DEGREE_Y = buff[bytes - 6];
                //Log.d("HGYRO DEGREE Y ", " " + GYRO_DEGREE_Y);
                int GYRO_DEGREE_Z = buff[bytes - 5];
                //Log.d("HGYRO DEGREE Z ", " " + GYRO_DEGREE_Z);

                series1.appendData(new DataPoint(x, EMG1), true, 50);
                series2.appendData(new DataPoint(x, EMG2), true, 50);

                series3.appendData(new DataPoint(x, GYRO_DEGREE_X), true, 50);
                series4.appendData(new DataPoint(x, GYRO_DEGREE_Y), true, 50);
                series5.appendData(new DataPoint(x, GYRO_DEGREE_Z), true, 50);
            }
                x++;

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
        btn = (Button) rootview.findViewById(R.id.startButton);
        graph1 = (GraphView) rootview.findViewById(R.id.graph1);
        graph2 = (GraphView) rootview.findViewById(R.id.graph2);
        series1 = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();
        series3 = new LineGraphSeries<DataPoint>();
        series4 = new LineGraphSeries<DataPoint>();
        series5 = new LineGraphSeries<DataPoint>();
        graph1.setTitle("EMG");
        graph2.setTitle("Degrees");
        graph1.addSeries(series1);
        graph1.addSeries(series2);

        graph2.addSeries(series3);
        graph2.addSeries(series4);
        graph2.addSeries(series5);

        graph1.getViewport().setScrollable(true);
        graph1.getViewport().setScalable(true);
        graph1.getViewport().setMaxX(50);
        graph1.getViewport().setMinX(0);
        graph1.getLegendRenderer().setVisible(true);
        graph1.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

        graph2.getViewport().setScrollable(true);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setMaxX(50);
        graph2.getViewport().setMinX(0);
        graph2.getLegendRenderer().setVisible(true);
        graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);


        //EMG1 == green EMG2 == red
        series1.setColor(Color.GREEN);
        series2.setColor(Color.RED);

        //DegreeX == BLUE DegreeY == GREEN DegreeZ == RED
        series3.setColor(Color.BLUE);
        series4.setColor(Color.GREEN);
        series5.setColor(Color.RED);

        series1.setTitle("LEMG");
        series2.setTitle("REMG");

        series3.setTitle("GyroX");
        series4.setTitle("GyroY");
        series5.setTitle("GyroZ");

        x =0;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setVisibility(View.GONE);
                init.start();
            }
        });

        return rootview;
    }

   /* @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        db = new MySQLiteHelper(getContext());
        notpaused = true;
    }*/

    @Override
    public void onStart(){
        super.onStart();
        db = new MySQLiteHelper(getContext());
        notpaused = true;
    }

    @Override
    public void onStop(){
        super.onStop();
        if(db != null) {
            db.close();
        }
        notpaused = false;
    }


    private Thread init = new Thread(){
        @Override
        public void start() {
            btAdapter = BluetoothAdapter.getDefaultAdapter();
            MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().compareTo("Posture") == 0) {
                        myBTDevice = device;
                    }
                }
            }
            if (!(myBTDevice == null)) {
                connectThread.start();
            } else {
                Toast.makeText(getContext(), "Not Paired", Toast.LENGTH_SHORT).show();
                //list.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        }
    };

    public void manageConnectedSocket(){

    }


    private Thread thread = new Thread() {

        @Override
        public void run() {
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                // byte[] buffer = new byte[1024];  // buffer store for the stream
                // int bytes; // bytes returned from read()
                while (true) {
                    //Log.d("Here", "Here");
                    while(notpaused) {
                        //Log.d("nowHere", "and here");
                        try {
                            byte[] buffer = new byte[1024];  // buffer store for the stream
                            tmpIn = btSocket.getInputStream();
                            tmpOut = btSocket.getOutputStream();
                            mmInStream = tmpIn;
                            mmOutStream = tmpOut;
                            int bytes = mmInStream.read(buffer);
                            //Log.i("NumOfBytes", "read nbytes: " + bytes);

                            byte[] cpyBuffer = buffer;

                            //Log.d("Number of Bytes ", " " + bytes);

                            if (bytes > 10) {

                                int EMG1 = ((cpyBuffer[bytes - 11] << 8) | cpyBuffer[bytes - 10]);
                                //Log.d("BTEMG1 ", " " + EMG1);
                                int EMG2 = ((cpyBuffer[bytes - 9] << 8) | cpyBuffer[bytes - 8]);
                                //Log.d("BTEMG2 ", " " + EMG2);
                                int GYRO_DEGREE_X = cpyBuffer[bytes - 7];
                                //Log.d("BTGYRO DEGREE X ", " " + GYRO_DEGREE_X);
                                int GYRO_DEGREE_Y = cpyBuffer[bytes - 6];
                                //Log.d("BTGYRO DEGREE Y ", " " + GYRO_DEGREE_Y);
                                int GYRO_DEGREE_Z = cpyBuffer[bytes - 5];
                                //Log.d("BTGYRO DEGREE Z ", " " + GYRO_DEGREE_Z);
                                int FORWARD_BEND = cpyBuffer[bytes - 4];
                                //Log.d("BTFORWARD BEND ", " " + FORWARD_BEND);
                                int FORWARD_CURVE = cpyBuffer[bytes - 3];
                                //Log.d("BTFORWARD_CURVE ", " " + FORWARD_CURVE);
                                int SIDE_CURVE = cpyBuffer[bytes - 2];
                                //Log.d("BTSIDE CURVE ", " " + SIDE_CURVE);
                                db.createPosture(System.currentTimeMillis(), EMG1, EMG2, GYRO_DEGREE_X, GYRO_DEGREE_Y, GYRO_DEGREE_Z, FORWARD_BEND, FORWARD_CURVE, SIDE_CURVE);
                                bluetoothHandler.obtainMessage(1, bytes, -1, cpyBuffer).sendToTarget();

                            }
                            try {
                                thread.sleep(35);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {

                        }
                    }
                }
        }
    };

    private Thread connectThread = new Thread() {
        @Override
        public void start() {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = myBTDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            }
            btSocket = tmp;
            run.start();
        }
    };

    private Thread run = new Thread(){
    @Override
    public void start() {
        // Cancel discovery because it will slow down the connection
        btAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            btSocket.connect();
            Log.d("BLUETOOTHSOCKET", "Bluetooth Socket Connected!");
            thread.start();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                btSocket.close();
                Log.d("BLUETOOTHSOCKET", "Bluetooth Socket Closed!");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connectThread.start();
            } catch (IOException closeException) {
            }
            return;
        }
    }
    };

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException e) { }
    }

   /* @Override
    public void onPause(){
        super.onPause();
        db.close();
        notpaused = false;
    }*/

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
            if(btSocket != null) {
                btSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
