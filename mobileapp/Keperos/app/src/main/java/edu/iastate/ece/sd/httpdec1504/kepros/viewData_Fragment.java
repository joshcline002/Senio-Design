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
                recDataString.append(msg.obj.toString());
               /* series1.appendData(new DataPoint(x, 1), true, 200);//Just Change the Y value Not X
                series2.appendData(new DataPoint(x, 1), true, 200);
                series3.appendData(new DataPoint(x, 2), true, 200);
                series4.appendData(new DataPoint(x, 3), true, 200); */
               // x ++;

                stringtoParse = String.valueOf(recDataString);
                //Log.d("StringtoParse", stringtoParse);

                     int i = 0;
                     int y = 0;
                     String[] tempArray = stringtoParse.split("\\s+");
                     //  String[] tempArray = stringtoParse.split("\\s+");

                     y = tempArray.length;

                     Scanner scan = new Scanner(stringtoParse);
                     int tempVal = 0;
                    // float tempVal = 0;
                     for (i = 0; i < y ; i++)
                     {
                         if(tempArray[i] == null || tempArray[i] == " " || tempArray[i] == "" || tempArray[i] == "\n" || tempArray[i] == "\t"){
                             // do nothing
                         }
                         else{
                             //tempVal = Float.valueOf(tempArray[i]);
                             if(tempArray[i] == "")
                             {
                                 // do nothing
                             }
                             else{
                                 tempVal = Integer.parseInt(tempArray[i]);
                                 //Log.d("tempVal", String.valueOf(tempVal));
                             }

                         }

                         if(tempVal == 999)
                         {
                             numberCount = 0; // hits the 9999 value and then starts the number counter to catch data
                         }

                        // Log.d("dataCount", String.valueOf(dataCount));
                             switch(numberCount)
                             {
                                 case 1:
                                     // EMG
                                     if (switchCount == false)
                                     { //Log.d("EMG DATA ADD", String.valueOf(tempVal));
                                         EMGdata1[dataCount][0] = tempVal;
                                         series1.appendData(new DataPoint(x, tempVal), true, 200);
                                     }else {
                                         EMGdata2[dataCount][0] = tempVal;
                                         series1.appendData(new DataPoint(x, tempVal), true, 200);
                                     }
                                     break;
                                 case 2:
                                     // AcelX
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][0] = tempVal;
                                         series2.appendData(new DataPoint(x, tempVal), true, 200);

                                     } else {
                                         IMUdata2[dataCount][0] = tempVal;
                                         series2.appendData(new DataPoint(x, tempVal), true, 200);

                                     }
                                     break;
                                 case 3:
                                     // AcelY
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][1] = tempVal;
                                         series3.appendData(new DataPoint(x, tempVal), true, 200);

                                     } else {
                                         IMUdata2[dataCount][1] = tempVal;
                                         series3.appendData(new DataPoint(x, tempVal), true, 200);

                                     }
                                     break;
                                 case 4:
                                     // Acel Z
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][2] = tempVal;
                                         series4.appendData(new DataPoint(x, tempVal), true, 200);

                                     } else {
                                         IMUdata2[dataCount][2] = tempVal;
                                         series4.appendData(new DataPoint(x, tempVal), true, 200);

                                     }
                                     break;
                                 case 5:
                                     // Gy X
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][3] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][3] = tempVal;
                                     }
                                     break;
                                 case 6:
                                     // Gy Y
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][4] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][4] = tempVal;
                                     }
                                     break;
                                 case 7:
                                     // Gy Z
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][5] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][5] = tempVal;
                                     }
                                     break;
                                 default:
                                     // What
                                     break;
                             }

                             if(numberCount == 7)
                             {
                                 if(switchCount == true)
                                 {
                                     Log.d("Parse Array 2", "EMG: " +  String.valueOf(EMGdata2[dataCount][0]) + " ACLx: " + String.valueOf(IMUdata2[dataCount][0])+ " ACLy: " + String.valueOf(IMUdata2[dataCount][1])+ " ACLz: " + String.valueOf(IMUdata2[dataCount][2]) + " GYx: " + String.valueOf(IMUdata2[dataCount][3]) + " GYy: " + String.valueOf(IMUdata2[dataCount][4]) + " GYz: " + String.valueOf(IMUdata2[dataCount][5])  );
                                 } else
                                 {
                                     Log.d("Parse Array 1", "EMG: " +  String.valueOf(EMGdata1[dataCount][0]) + " ACLx: " + String.valueOf(IMUdata1[dataCount][0])+ " ACLy: " + String.valueOf(IMUdata1[dataCount][1])+ " ACLz: " + String.valueOf(IMUdata1[dataCount][2]) + " GYx: " + String.valueOf(IMUdata1[dataCount][3]) + " GYy: " + String.valueOf(IMUdata1[dataCount][4]) + " GYz: " + String.valueOf(IMUdata1[dataCount][5])  );
                                 }
                                 dataCount++;
                                 numberCount = 0;
                             }
                             if (dataCount == 100)
                             {
                                 dataCount = 0;
                                 switchCount = !switchCount;
                             }

                         numberCount++;
                         x ++;



                     }





            recDataString.delete(0, recDataString.length()); //clear all string data
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
        graph2.setTitle("IMU");
        graph1.addSeries(series1);
        graph2.addSeries(series2);
        graph2.addSeries(series3);
        graph2.addSeries(series4);
        graph1.getViewport().setScalable(true);
        graph1.getViewport().setMaxX(200);
        graph1.getViewport().setMinX(0);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setMaxX(200);
        graph2.getViewport().setMinX(0);
        graph1.getViewport().setScrollable(true);

        //EMG1 == green EMG2 == red
        series1.setColor(Color.GREEN);
        series2.setColor(Color.RED);

        //DegreeX == BLUE DegreeY == GREEN DegreeZ == RED
        series3.setColor(Color.BLUE);
        series4.setColor(Color.GREEN);
        series5.setColor(Color.RED);

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

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
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
            db = new MySQLiteHelper(getContext());
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
                   // Log.d("Here", "Here");
                    try {
                        byte[] buffer = new byte[1024];  // buffer store for the stream
                        tmpIn = btSocket.getInputStream();
                        tmpOut = btSocket.getOutputStream();
                        mmInStream = tmpIn;
                        mmOutStream = tmpOut;
                        int bytes = mmInStream.read(buffer);
                        //Log.i("NumOfBytes", "read nbytes: " + bytes);

                        byte[] cpyBuffer = buffer;

                        Log.d("Number of Bytes ", " " + bytes);

                        if(bytes > 10) {

                            int EMG1 = ((cpyBuffer[bytes-11] << 8) | cpyBuffer[bytes-10]);
                            Log.d("EMG1 ", " " + EMG1);
                            int EMG2 = ((cpyBuffer[bytes-9] << 8) | cpyBuffer[bytes-8]);
                            Log.d("EMG2 ", " " + EMG2);
                            int GYRO_DEGREE_X = cpyBuffer[bytes-7];
                            Log.d("GYRO DEGREE X ", " " + GYRO_DEGREE_X);
                            int GYRO_DEGREE_Y = cpyBuffer[bytes-6];
                            Log.d("GYRO DEGREE Y ", " " + GYRO_DEGREE_Y);
                            int GYRO_DEGREE_Z = cpyBuffer[bytes-5];
                            Log.d("GYRO DEGREE Z ", " " + GYRO_DEGREE_Z);
                            int FORWARD_BEND = cpyBuffer[bytes-4];
                            Log.d("FORWARD BEND ", " " + FORWARD_BEND);
                            int FORWARD_CURVE = cpyBuffer[bytes-3];
                            Log.d("FORWARD_CURVE ", " " + FORWARD_CURVE);
                            int SIDE_CURVE = cpyBuffer[bytes-2];
                            Log.d("SIDE CURVE ", " " + SIDE_CURVE);

                            bluetoothHandler.obtainMessage(1, bytes, -1, cpyBuffer).sendToTarget();

                            db.createPosture(System.currentTimeMillis(), EMG1, EMG2, GYRO_DEGREE_X, GYRO_DEGREE_Y, GYRO_DEGREE_Z, FORWARD_BEND, FORWARD_CURVE, SIDE_CURVE);
                            thread.wait(50);
                        }
                    } catch (IOException e) {

                } catch (InterruptedException e) {
                        e.printStackTrace();
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


}
