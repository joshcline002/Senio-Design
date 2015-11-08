package edu.iastate.ece.sd.httpdec1504.kepros;

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

    Button btDataOn, btDataOff;
    TextView txtArduino, txtString, txtStringLength, bluetoothDataRec;
    Handler bluetoothIn;
    ArrayList<String> mylist = new ArrayList<String>();




    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private BluetoothDevice myBTDevice;
    private UUID MY_UUID;
    private TextView text;
    private ArrayAdapter<String> CheckAdapter;
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
    ListView list;
    Button btn;

    public float[][] EMGdata1 = new float [100][1];  // EMG Data is ... 1 number? not sure.
    public float[][] EMGdata2 = new float [100][1];

    public float[][] IMUdata1 = new float [100][6]; // IMU data holding 100 values of 6, AclX | AclY | AclZ | GyX | GyY | GyZ
    public float[][] IMUdata2 = new float [100][6];

    String stringtoParse = "";

    public int numberCount = 7; // start it at outside number from switch statements
    public int dataCount = 0; // start at array indicator 0. go to 100 then change to 0
    public boolean switchCount = false;


    private InputStream mmInStream;
    private OutputStream mmOutStream;


    Handler bluetoothHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
<<<<<<< HEAD
            recDataString.append(msg.obj.toString());
            if(String.valueOf(recDataString).equals(";")) {
                bluetoothHandler.postDelayed(stringToList, 1);
            } else {
                stringtoParse = String.valueOf(recDataString);
                mylist.add(stringtoParse);
                try{
                    startGraphing(String.valueOf(recDataString));
                }
                catch(InterruptedException e){
=======
                recDataString.append(msg.obj.toString());
                 if(String.valueOf(recDataString).equals(";")) {
                     bluetoothHandler.postDelayed(stringToList, 1);
                 } else {
                     stringtoParse = String.valueOf(recDataString);
                     mylist.add(stringtoParse);
                    /* int i = 0;
                     int x = 0;
                     String[] tempArray = stringtoParse.split("\\s+");

                     x = tempArray.length;

                     Scanner scan = new Scanner(stringtoParse);
                     float tempVal = 0;
                     for (i = 0; i < x ; i++)
                     {
                         tempVal = Float.valueOf(tempArray[i]);

                         if(tempVal == 9999)
                         {
                             numberCount = 0; // hits the 9999 value and then starts the number counter to catch data
                         } else
                         {
                             switch(numberCount)
                             {
                                 case 0:
                                     // EMG
                                     if (switchCount == false)
                                     {EMGdata1[dataCount][0] = tempVal;
                                     }else {
                                         EMGdata2[dataCount][0] = tempVal;
                                     }
                                     break;
                                 case 1:
                                     // AcelX
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][0] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][0] = tempVal;
                                     }
                                     break;
                                 case 2:
                                     // AcelY
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][1] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][1] = tempVal;
                                     }
                                     break;
                                 case 3:
                                     // Acel Z
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][2] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][2] = tempVal;
                                     }
                                     break;
                                 case 4:
                                     // Gy X
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][3] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][3] = tempVal;
                                     }
                                     break;
                                 case 5:
                                     // Gy Y
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][4] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][4] = tempVal;
                                     }
                                     break;
                                 case 6:
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

                             if(numberCount == 6)
                             {
                                 dataCount++;
                             }
                             if (dataCount == 101)
                             {
                                 dataCount = 0;
                                 switchCount = !switchCount;
                             }
                         }
                     }*/
                 }
                recDataString.delete(0, recDataString.length()); //clear all string data
>>>>>>> origin/master
                }

                    /* int i = 0;
                     int x = 0;
                     String[] tempArray = stringtoParse.split("\\s+");
                     x = tempArray.length;
                     Scanner scan = new Scanner(stringtoParse);
                     float tempVal = 0;
                     for (i = 0; i < x ; i++)
                     {
                         tempVal = Float.valueOf(tempArray[i]);
                         if(tempVal == 9999)
                         {
                             numberCount = 0; // hits the 9999 value and then starts the number counter to catch data
                         } else
                         {
                             switch(numberCount)
                             {
                                 case 0:
                                     // EMG
                                     if (switchCount == false)
                                     {EMGdata1[dataCount][0] = tempVal;
                                     }else {
                                         EMGdata2[dataCount][0] = tempVal;
                                     }
                                     break;
                                 case 1:
                                     // AcelX
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][0] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][0] = tempVal;
                                     }
                                     break;
                                 case 2:
                                     // AcelY
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][1] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][1] = tempVal;
                                     }
                                     break;
                                 case 3:
                                     // Acel Z
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][2] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][2] = tempVal;
                                     }
                                     break;
                                 case 4:
                                     // Gy X
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][3] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][3] = tempVal;
                                     }
                                     break;
                                 case 5:
                                     // Gy Y
                                     if (switchCount == false)
                                     {IMUdata1[dataCount][4] = tempVal;
                                     } else {
                                         IMUdata2[dataCount][4] = tempVal;
                                     }
                                     break;
                                 case 6:
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
                             if(numberCount == 6)
                             {
                                 dataCount++;
                             }
                             if (dataCount == 101)
                             {
                                 dataCount = 0;
                                 switchCount = !switchCount;
                             }
                         }
                     }*/
            }
            recDataString.delete(0, recDataString.length()); //clear all string data
        }
    };

    public void startGraphing(String s) throws InterruptedException {
        GraphView graph1 = (GraphView) getView().findViewById(R.id.graph1);
        GraphView graph2 = (GraphView) getView().findViewById(R.id.graph2);
        //Sets the new data point
        series1.appendData(new DataPoint(1,2), true, 10);
        series2.appendData(new DataPoint(1,2), true, 10);
        graph1.addSeries(series1);
        graph2.addSeries(series1);
    }



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
        CheckAdapter = new ArrayAdapter<String>(rootview.getContext(), android.R.layout.simple_list_item_1);
        btn = (Button) rootview.findViewById(R.id.startButton);
        list = (ListView) rootview.findViewById(R.id.datalist);
        list.setAdapter(CheckAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setVisibility(View.VISIBLE);
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
                list.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        }
    };

    private Thread thread = new Thread() {
        @Override
        public void start() {
            manageConnectedSocket();
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

    public void manageConnectedSocket(){
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // byte[] buffer = new byte[1024];  // buffer store for the stream
        // int bytes; // bytes returned from read()
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                byte[] buffer = new byte[1024];  // buffer store for the stream
                tmpIn = btSocket.getInputStream();
                tmpOut = btSocket.getOutputStream();
                mmInStream = tmpIn;
                mmOutStream = tmpOut;
                int bytes = mmInStream.read(buffer);
                //Log.i("NumOfBytes", "read nbytes: " + bytes);

                String readMessage = new String(buffer, 0, bytes);
                //Log.d("readmessage stuff", readMessage);
                bluetoothHandler.obtainMessage(1, bytes, -1, readMessage).sendToTarget();
                Log.d("CONNECTED", "YEP ");
                //  mylist.add("Woooooop"); // If there is no myList add calll... then it doesnt show data. which makes no sense
                // i lied

                //   bytes = btSocket.getInputStream().read(buffer);
                //Log.d("ReadingBytes", "What should be inputed here im not sure");
                // mHandler.obtainMessage(1, bytes, -1, buffer)
                //       .sendToTarget();
                //bluetoothHandler.obtainMessage(1, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }

    private Runnable stringToList = new Runnable() {
        public void run() {
            StringBuilder readings = new StringBuilder();
            for (int i = 0; i<mylist.size(); i++) {
                readings.append(mylist.get(i));
            }
            CheckAdapter.add(String.valueOf(readings));
            if(CheckAdapter.getCount()>100){
                CheckAdapter.clear();
            }
            CheckAdapter.notifyDataSetChanged();
            mylist.clear();
            list.setSelection(CheckAdapter.getCount()-1);
        }
    };
}
