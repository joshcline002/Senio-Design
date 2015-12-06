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
        public static final String TABLE_POSTURE = "POSTURE";

        //Column Names
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TIMESTAMP = "TIMESTAMP";

        public static final String COLUMN_EMG1 = "LEMG";
        public static final String COLUMN_EMG2 = "REMG";

        public static final String COLUMN_TOP_ACCEL_X = "TOPX";
        public static final String COLUMN_TOP_ACCEL_Y = "TOPY";
        public static final String COLUMN_TOP_ACCEL_Z = "TOPZ";

        public static final String COLUMN_BOT_ACCEL_X = "BOTX";
        public static final String COLUMN_BOT_ACCEL_Y = "BOTY";
        public static final String COLUMN_BOT_ACCEL_Z = "BOTZ";

        public static final String COLUMN_DEGREE_X = "GYROX";
        public static final String COLUMN_DEGREE_Y = "GYROY";
        public static final String COLUMN_DEGREE_Z = "GYROZ";

        private static final String DATABASE_NAME = "kepros.db";
        private static final int DATABASE_VERSION = 1;

        // Database Creation sql statement
        private static final String CREATE_TABLE_POSTURE = "CREATE TABLE "
                + TABLE_POSTURE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TIMESTAMP + " REAL, "
                + COLUMN_EMG1 + " INTEGER, "
                + COLUMN_EMG2 + " INTEGER, "
                + COLUMN_DEGREE_X + " INTEGER, "
                + COLUMN_DEGREE_Y + " INTEGER, "
                + COLUMN_DEGREE_Z + " INTEGER, "
                + COLUMN_TOP_ACCEL_X + " REAL, "
                + COLUMN_TOP_ACCEL_Y + " REAL, "
                + COLUMN_TOP_ACCEL_Z + " REAL, "
                + COLUMN_BOT_ACCEL_X + " REAL, "
                + COLUMN_BOT_ACCEL_Y + " REAL, "
                + COLUMN_BOT_ACCEL_Z + " REAL)";


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
            database.execSQL(CREATE_TABLE_POSTURE);
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
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTURE);

            // create new tables
            onCreate(db);
        }

    /**
     *
     * @param time
     * @param EMG1
     */
        public void createPosture(double time, int EMG1, int EMG2, int DEGREEX, int DEGREEY, int DEGREEZ, double TOPX, double TOPY, double TOPZ, double BOTX, double BOTY, double BOTZ){

            SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                    values.put(COLUMN_TIMESTAMP, time);
                    values.put(COLUMN_EMG1, EMG1);
                    values.put(COLUMN_EMG2, EMG2);
                    values.put(COLUMN_DEGREE_X, DEGREEX);
                    values.put(COLUMN_DEGREE_Y, DEGREEY);
                    values.put(COLUMN_DEGREE_Z, DEGREEZ);
                    values.put(COLUMN_TOP_ACCEL_X, TOPX);
                    values.put(COLUMN_TOP_ACCEL_Y, TOPY);
                    values.put(COLUMN_TOP_ACCEL_Z, TOPZ);
                    values.put(COLUMN_BOT_ACCEL_X, BOTX);
                    values.put(COLUMN_BOT_ACCEL_Y, BOTY);
                    values.put(COLUMN_BOT_ACCEL_Z, BOTZ);

            db.insert(TABLE_POSTURE, null, values);
        }

    /** This gets the Left EMG readings based on the start and end timestamps provided
     *
     * @param startTime The start time of the window you want the readings to come from
     * @param endTime The end time of the window you want the readings to come from
     * @return Returns a double array of the Left EMG readings within specified start and end times
     */
        public int[] getLEMG(double startTime, double endTime){
            String selectQuery = "SELECT " + COLUMN_EMG1 + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            int size = c.getCount();
            int[] LEMG = new int[size];
            int i =0;
            //
            if (c.moveToFirst()){
                do{
                    LEMG[i] = c.getInt(c.getColumnIndex(COLUMN_EMG1));
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
    public int[] getREMG(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_EMG2 + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] REMG = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                REMG[i] = c.getInt(c.getColumnIndex(COLUMN_EMG2));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return REMG;
    }

    public double[] getTOPACCELX(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_TOP_ACCEL_X + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_TOP_ACCEL_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getTOPACCELY(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_TOP_ACCEL_Y + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_TOP_ACCEL_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getTOPACCELZ(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_TOP_ACCEL_Z + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_TOP_ACCEL_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public int[] getDEGREEX(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_DEGREE_X + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] read = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getInt(c.getColumnIndex(COLUMN_DEGREE_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public int[] getDEGREEY(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_DEGREE_Y + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] read = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getInt(c.getColumnIndex(COLUMN_DEGREE_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public int[] getDEGREEZ(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_DEGREE_Z + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] read = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getInt(c.getColumnIndex(COLUMN_DEGREE_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getBOTACCELX(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_BOT_ACCEL_X + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_BOT_ACCEL_X));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getBOTACCELY(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_BOT_ACCEL_Y + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_BOT_ACCEL_Y));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public double[] getBOTACCELZ(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_BOT_ACCEL_Z + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        double[] read = new double[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getDouble(c.getColumnIndex(COLUMN_BOT_ACCEL_Z));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }
    }
