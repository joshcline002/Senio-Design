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
        // Table Names
        public static final String TABLE_EMG1 = "LEMG";
        public static final String TABLE_EMG2 = "REMG";
        public static final String TABLE_IMU1 = "ULIMU";
        public static final String TABLE_IMU2 = "URIMU";
        public static final String TABLE_IMU3 = "LLIMU";
        public static final String TABLE_IMU4 = "LRIMU";

        //Column Names
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TIMESTAMP = "TIMESTAMP";

        public static final String COLUMN_EMG1 = "LEMG";
        public static final String COLUMN_EMG2 = "REMG";

        public static final String COLUMN_ACCEL_X = "ACCELX";
        public static final String COLUMN_ACCEL_Y = "ACCELY";
        public static final String COLUMN_ACCEL_Z = "ACCELZ";

        public static final String COLUMN_GYRO_X = "GYROX";
        public static final String COLUMN_GYRO_Y = "GYROY";
        public static final String COLUMN_GYRO_Z = "GYROZ";

        private static final String DATABASE_NAME = "kepros.db";
        private static final int DATABASE_VERSION = 1;

        // Database Creation sql statement
        private static final String CREATE_TABLE_EMG1 = "CREATE TABLE "
                + TABLE_EMG1 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_EMG1 + " REAL)";

        private static final String CREATE_TABLE_EMG2 = "CREATE TABLE "
                + TABLE_EMG2 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_EMG2 + " REAL)";

        private static final String CREATE_TABLE_IMU1 = "CREATE TABLE "
                + TABLE_IMU1 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_ACCEL_X + " REAL,"
                + COLUMN_ACCEL_Y + " REAL,"
                + COLUMN_ACCEL_Z + " REAL,"
                + COLUMN_GYRO_X + " REAL,"
                + COLUMN_GYRO_Y + " REAL,"
                + COLUMN_GYRO_Z + " REAL)";

        private static final String CREATE_TABLE_IMU2 = "CREATE TABLE "
                + TABLE_IMU2 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_ACCEL_X + " REAL,"
                + COLUMN_ACCEL_Y + " REAL,"
                + COLUMN_ACCEL_Z + " REAL,"
                + COLUMN_GYRO_X + " REAL,"
                + COLUMN_GYRO_Y + " REAL,"
                + COLUMN_GYRO_Z + " REAL)";

        private static final String CREATE_TABLE_IMU3 = "CREATE TABLE "
                + TABLE_IMU3 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_ACCEL_X + " REAL,"
                + COLUMN_ACCEL_Y + " REAL,"
                + COLUMN_ACCEL_Z + " REAL,"
                + COLUMN_GYRO_X + " REAL,"
                + COLUMN_GYRO_Y + " REAL,"
                + COLUMN_GYRO_Z + " REAL)";

        private static final String CREATE_TABLE_IMU4 = "CREATE TABLE "
                + TABLE_IMU4 + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMESTAMP + " REAL,"
                + COLUMN_ACCEL_X + " REAL,"
                + COLUMN_ACCEL_Y + " REAL,"
                + COLUMN_ACCEL_Z + " REAL,"
                + COLUMN_GYRO_X + " REAL,"
                + COLUMN_GYRO_Y + " REAL,"
                + COLUMN_GYRO_Z + " REAL)";

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
            database.execSQL(CREATE_TABLE_EMG1);
            database.execSQL(CREATE_TABLE_EMG2);
            database.execSQL(CREATE_TABLE_IMU1);
            database.execSQL(CREATE_TABLE_IMU2);
            database.execSQL(CREATE_TABLE_IMU3);
            database.execSQL(CREATE_TABLE_IMU4);
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
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMG1);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMG2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMU1);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMU2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMU3);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMU4);

            // create new tables
            onCreate(db);
        }

    /**
     *
     * @param time
     * @param EMG1
     */
        public void createEMG1(String time, String EMG1){

            SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                    values.put(COLUMN_TIMESTAMP, Double.parseDouble(time));
                    values.put(COLUMN_EMG1, Double.parseDouble(EMG1));

            db.insert(TABLE_EMG1, null, values);
        }

    /**
     *
     * @param time
     * @param EMG2
     */
        public void createEMG2(String time, String EMG2){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIMESTAMP, Double.parseDouble(time));
            values.put(COLUMN_EMG1, Double.parseDouble(EMG2));

            db.insert(TABLE_EMG2, null, values);
        }

        public void createIMU1(String time, String ACCELX, String ACCELY, String ACCELZ, String GYROX, String GYROY, String GYROZ){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIMESTAMP, Double.parseDouble(time));
            values.put(COLUMN_ACCEL_X, Double.parseDouble(ACCELX));
            values.put(COLUMN_ACCEL_Y, Double.parseDouble(ACCELY));
            values.put(COLUMN_ACCEL_Z, Double.parseDouble(ACCELZ));
            values.put(COLUMN_GYRO_X, Double.parseDouble(GYROX));
            values.put(COLUMN_GYRO_Y, Double.parseDouble(GYROY));
            values.put(COLUMN_GYRO_Z, Double.parseDouble(GYROZ));

            db.insert(TABLE_IMU1, null, values);
        }

        public void createIMU2(String time, String ACCELX, String ACCELY, String ACCELZ, String GYROX, String GYROY, String GYROZ){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIMESTAMP, Double.parseDouble(time));
            values.put(COLUMN_ACCEL_X, Double.parseDouble(ACCELX));
            values.put(COLUMN_ACCEL_Y, Double.parseDouble(ACCELY));
            values.put(COLUMN_ACCEL_Z, Double.parseDouble(ACCELZ));
            values.put(COLUMN_GYRO_X, Double.parseDouble(GYROX));
            values.put(COLUMN_GYRO_Y, Double.parseDouble(GYROY));
            values.put(COLUMN_GYRO_Z, Double.parseDouble(GYROZ));

            db.insert(TABLE_IMU2, null, values);
        }

        public void createIMU3(String time, String ACCELX, String ACCELY, String ACCELZ, String GYROX, String GYROY, String GYROZ){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIMESTAMP, Double.parseDouble(time));
            values.put(COLUMN_ACCEL_X, Double.parseDouble(ACCELX));
            values.put(COLUMN_ACCEL_Y, Double.parseDouble(ACCELY));
            values.put(COLUMN_ACCEL_Z, Double.parseDouble(ACCELZ));
            values.put(COLUMN_GYRO_X, Double.parseDouble(GYROX));
            values.put(COLUMN_GYRO_Y, Double.parseDouble(GYROY));
            values.put(COLUMN_GYRO_Z, Double.parseDouble(GYROZ));

            db.insert(TABLE_IMU3, null, values);
        }

        public void createIMU4(String time, String ACCELX, String ACCELY, String ACCELZ, String GYROX, String GYROY, String GYROZ){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIMESTAMP, Double.parseDouble(time));
            values.put(COLUMN_ACCEL_X, Double.parseDouble(ACCELX));
            values.put(COLUMN_ACCEL_Y, Double.parseDouble(ACCELY));
            values.put(COLUMN_ACCEL_Z, Double.parseDouble(ACCELZ));
            values.put(COLUMN_GYRO_X, Double.parseDouble(GYROX));
            values.put(COLUMN_GYRO_Y, Double.parseDouble(GYROY));
            values.put(COLUMN_GYRO_Z, Double.parseDouble(GYROZ));

            db.insert(TABLE_IMU4, null, values);
        }

    /** This gets the Left EMG readings based on the start and end timestamps provided
     *
     * @param startTime The start time of the window you want the readings to come from
     * @param endTime The end time of the window you want the readings to come from
     * @return Returns a double array of the Left EMG readings within specified start and end times
     */
        public double[] getLEMG(String startTime, String endTime){
            String selectQuery = "SELECT " + COLUMN_EMG1 + " FROM " + TABLE_EMG1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
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
        String selectQuery = "SELECT " + COLUMN_EMG2 + " FROM " + TABLE_EMG2 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
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

    public double[] getULIMUACCELX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_X + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getULIMUACCELY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Y + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getULIMUACCELZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Z + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getULIMUGYROX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_X + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getULIMUGYROY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Y + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getULIMUGYROZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Z + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getURIMUACCELX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_X + " FROM " + TABLE_IMU2 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getURIMUACCELY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Y + " FROM " + TABLE_IMU2 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getURIMUACCELZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Z + " FROM " + TABLE_IMU2 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getURIMUGYROX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_X + " FROM " + TABLE_IMU2 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getURIMUGYROY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Y + " FROM " + TABLE_IMU2 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getURIMUGYROZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Z + " FROM " + TABLE_IMU1 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLLIMUACCELX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_X + " FROM " + TABLE_IMU3 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLLIMUACCELY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Y + " FROM " + TABLE_IMU3 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLLIMUACCELZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Z + " FROM " + TABLE_IMU3 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLLIMUGYROX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_X + " FROM " + TABLE_IMU3 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLLIMUGYROY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Y + " FROM " + TABLE_IMU3 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLLIMUGYROZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Z + " FROM " + TABLE_IMU3 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLRIMUACCELX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_X + " FROM " + TABLE_IMU4 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLRIMUACCELY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Y + " FROM " + TABLE_IMU4 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLRIMUACCELZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_ACCEL_Z + " FROM " + TABLE_IMU4 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_ACCEL_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLRIMUGYROX(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_X + " FROM " + TABLE_IMU4 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLRIMUGYROY(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Y + " FROM " + TABLE_IMU4 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getLRIMUGYROZ(String startTime, String endTime){
        String selectQuery = "SELECT " + COLUMN_GYRO_Z + " FROM " + TABLE_IMU4 + " WHERE TIMESTAMP > " + Double.parseDouble(startTime) + " AND TIMESTAMP < " + Double.parseDouble(endTime);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_GYRO_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    }
