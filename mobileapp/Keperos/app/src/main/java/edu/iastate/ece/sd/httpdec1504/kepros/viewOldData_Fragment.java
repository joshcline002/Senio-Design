package edu.iastate.ece.sd.httpdec1504.kepros;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nick on 12/2/2015.
 */
public class viewOldData_Fragment extends android.support.v4.app.Fragment {

    ViewGroup rootview;
    ViewGroup view;
    ArrayList<String> mylist = new ArrayList<String>();

    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<DataPoint>();
    GraphView graph1;
    GraphView graph2;
    int x = 0;
    Button btn;

    MySQLiteHelper sql = new MySQLiteHelper(getContext());

    long startTimeMS = 0;
    long endTimeMS = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = (ViewGroup) inflater.inflate(R.layout.viewolddata_layout, container, false);
        view = rootview;

        graph1 = (GraphView) rootview.findViewById(R.id.graph1);
        graph2 = (GraphView) rootview.findViewById(R.id.graph2);
        series1 = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();
        series3 = new LineGraphSeries<DataPoint>();
        series4 = new LineGraphSeries<DataPoint>();
        series5 = new LineGraphSeries<DataPoint>();
        getTime(view);
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
        x =0;

        return rootview;
    }

    public void getTime(ViewGroup root){
        Button b = (Button) root.findViewById((R.id.get));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the from time
                EditText ft = (EditText) view.findViewById(R.id.etxt_fromtime);
                String ftText = ft.getText().toString();

                //Gets the to time
                EditText tt = (EditText) view.findViewById(R.id.etxt_totime);
                String ttText = tt.getText().toString();

                //Gets the from date
                EditText fd = (EditText) view.findViewById(R.id.etxt_fromdate);
                String fdText = fd.getText().toString();

                //Gets the to date
                EditText td = (EditText) view.findViewById(R.id.etxt_todate);
                String tdText = td.getText().toString();

                //Parse date to ms
                String pattern = "MM.dd.yy HH:mm";
                SimpleDateFormat format = new SimpleDateFormat(pattern);

                //Gets the start time ms
                try {
                    Date mDate = format.parse(fdText + " " + ftText);
                    startTimeMS = mDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Gets the end time ms
                try {
                    Date mDate = format.parse(tdText + " " + ttText);
                    endTimeMS = mDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Gets the info from db
                /* I don't know what values you methods you want me to call but it should go in like so,
                   once you change the methods to accept long

                   series1 = sql.methodName(startTimeMS, endTimeMS);
                 */
                    int[] LEMG = sql.getLEMG(startTimeMS, endTimeMS);
                    int[] REMG = sql.getREMG(startTimeMS, endTimeMS);
                    int[] DEGREEX = sql.getDEGREEX(startTimeMS, endTimeMS);
                    int[] DEGREEY = sql.getDEGREEY(startTimeMS, endTimeMS);
                    int[] DEGREEZ = sql.getDEGREEZ(startTimeMS, endTimeMS);

                for (int i = 0; i < LEMG.length; i++){
                    series1.appendData(new DataPoint(i, LEMG[i]), true, 200);
                    series2.appendData(new DataPoint(i, REMG[i]), true, 200);
                    series3.appendData(new DataPoint(i, DEGREEX[i]), true, 200);
                    series4.appendData(new DataPoint(i, DEGREEY[i]), true, 200);
                    series5.appendData(new DataPoint(i, DEGREEZ[i]), true, 200);
                }
            }
        });
    }
}
