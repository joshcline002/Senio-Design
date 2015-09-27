package edu.iastate.ece.sd.httpdec1504.kepros;
/**
 * a
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**SQLite helper class for the SQLite db functionality
 * Created by jccline on 9/27/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
        public static final String TABLE_READINGS = "MeasurementReadings";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TIMESTAMP = "TIMESTAMP";
        public static final String COLUMN_EMG1 = "LEMG";
        public static final String COLUMN_EMG2 = "REMG";
        public static final String COLUMN_IMU1 = "ULIMU";
        public static final String COLUMN_IMU2 = "URIMU";
        public static final String COLUMN_IMU3 = "LLIMU";
        public static final String COLUMN_IMU4 = "LRIMU";

        private static final String DATABASE_NAME = "kepros.db";
        private static final int DATABASE_VERSION = 1;

        // Database Creation sql statement
        private static final String CREATE_TABLE_READINGS = "CREATE TABLE "
                + TABLE_READINGS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_EMG1 + " REAL,"
                + COLUMN_EMG2 + " REAL,"
                + COLUMN_IMU1 + " REAL,"
                + COLUMN_IMU2 + " REAL,"
                + COLUMN_IMU3 + " REAL,"
                + COLUMN_IMU4 + " REAL)";

    /**
     *
     * @param context
     */
        public MySQLiteHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    /** This creates the database it does not need to be called
     *
     * @param database
     */
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_TABLE_READINGS);
        }

    /** This needs to be done if changes to the db schema happens so that the current database is dropped
     * and the new one can then be implemented.
     *
     * @param db which db to drop
     * @param oldVersion the old version number of the database
     * @param newVersion the new version number of the database
     */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_READINGS);

            // create new tables
            onCreate(db);
        }

    /**This creates the row of the readings coming in to be added to the database
     *
     * @param List This is the list of string objects that are the readings with index (0) = Timestamp(double format), (1) = Left EMG
     *             (2) = Right EMG, (3) = Upper Left IMU, (4) = Upper Right IMU, (5) = Lower Left IMU, (6) = Lower Right IMU
     */
        public void createReading(ArrayList<String> List){

            SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                    values.put(COLUMN_TIMESTAMP,Double.parseDouble(List.get(0)));
                    values.put(COLUMN_EMG1,Double.parseDouble(List.get(1)));
                    values.put(COLUMN_EMG2,Double.parseDouble(List.get(2)));
                    values.put(COLUMN_IMU1,Double.parseDouble(List.get(3)));
                    values.put(COLUMN_IMU2,Double.parseDouble(List.get(4)));
                    values.put(COLUMN_IMU3,Double.parseDouble(List.get(5)));
                    values.put(COLUMN_IMU4, Double.parseDouble(List.get(6)));


            db.insert(TABLE_READINGS, null, values);
        }

    /** This gets the Left EMG readings based on the start and end timestamps provided
     *
     * @param startTime The start time of the window you want the readings to come from
     * @param endTime The end time of the window you want the readings to come from
     * @return Returns a double array of the Left EMG readings within specified start and end times
     */
        public double[] getLEMG(String startTime, String endTime){
            String selectQuery = "SELECT " + COLUMN_EMG1 + " FROM " + TABLE_READINGS + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            int size = c.getCount();
            double[] LEMG = new double[size];
            int i =0;
            //
            if (c.moveToFirst()){
                do{
                    LEMG[i] = c.getDouble(c.getColumnIndex(COLUMN_EMG1));
                    i++;
                } while (c.moveToNext());
            }
            c.close();
            return LEMG;
        }

    /** This gets the Right EMG readings based on the start and end timestamps provided
     *
     * @param startTime The String start time of the window you want the readings to come from
     * @param endTime The String end time of the window you want the readings to come from
     * @return Returns a double array of the Right EMG readings within specified start and end times
     */
    public double[] getREMG(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_EMG2 + " FROM " + TABLE_READINGS + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] REMG = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                REMG[i] = c.getDouble(c.getColumnIndex(COLUMN_EMG2));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return REMG;
    }

    /** This gets the Upper Left IMU readings based on the start and end timestamps provided
     *
     * @param startTime The String start time of the window you want the readings to come from
     * @param endTime The String end time of the window you want the readings to come from
     * @return Returns a double array of the Upper Left IMU readings within specified start and end times
     */
    public double[] getULIMU(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_IMU1 + " FROM " + TABLE_READINGS + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] ULIMU = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                ULIMU[i] = c.getDouble(c.getColumnIndex(COLUMN_IMU1));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return ULIMU;
    }

    /** This gets the Upper Right IMU readings based on the start and end timestamps provided
     *
     * @param startTime The String start time of the window you want the readings to come from
     * @param endTime The String end time of the window you want the readings to come from
     * @return Returns a double array of the Upper Right IMU readings within specified start and end times
     */
    public double[] getURIMU(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_IMU2 + " FROM " + TABLE_READINGS + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] URIMU = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                URIMU[i] = c.getDouble(c.getColumnIndex(COLUMN_IMU2));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return URIMU;
    }

    /** This gets the Lower Left IMU readings based on the start and end timestamps provided
     *
     * @param startTime The String start time of the window you want the readings to come from
     * @param endTime The String end time of the window you want the readings to come from
     * @return Returns a double array of the Lower Left IMU readings within specified start and end times
     */
    public double[] getLLIMU(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_IMU3 + " FROM " + TABLE_READINGS + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] LLIMU = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                LLIMU[i] = c.getDouble(c.getColumnIndex(COLUMN_IMU3));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return LLIMU;
    }

    /** This gets the Lower Right IMU readings based on the start and end timestamps provided
     *
     * @param startTime The String start time of the window you want the readings to come from
     * @param endTime The String end time of the window you want the readings to come from
     * @return Returns a double array of the Lower Right IMU readings within specified start and end times
     */
    public double[] getLRIMU(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_IMU4 + " FROM " + TABLE_READINGS + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] LRIMU = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                LRIMU[i] = c.getDouble(c.getColumnIndex(COLUMN_IMU4));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return LRIMU;
    }

    }
