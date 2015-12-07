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

        public static final String COLUMN_FORWARD_BEND = "ACCELY";
        public static final String COLUMN_FORWARD_CURVE = "ACCELZ";
        public static final String COLUMN_SIDE_CURVE = "ACCELX";

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
                + COLUMN_FORWARD_BEND + " INTEGER, "
                + COLUMN_FORWARD_CURVE + " INTEGER,"
                + COLUMN_SIDE_CURVE + " INTEGER)";


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
        public void createPosture(double time, int EMG1, int EMG2, int DEGREEX, int DEGREEY, int DEGREEZ, int FORWARDBEND, int FORWARDCURVE, int SIDECURVE){

            SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                    values.put(COLUMN_TIMESTAMP, time);
                    values.put(COLUMN_EMG1, EMG1);
                    values.put(COLUMN_EMG2, EMG2);
                    values.put(COLUMN_DEGREE_X, DEGREEX);
                    values.put(COLUMN_DEGREE_Y, DEGREEY);
                    values.put(COLUMN_DEGREE_Z, DEGREEZ);
                    values.put(COLUMN_FORWARD_BEND, FORWARDBEND);
                    values.put(COLUMN_FORWARD_CURVE, FORWARDCURVE);
                    values.put(COLUMN_SIDE_CURVE, SIDECURVE);

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

    public int[] getFORWARDBEND(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_FORWARD_BEND + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] read = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getInt(c.getColumnIndex(COLUMN_FORWARD_BEND));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public int[] getFORWARDCURVE(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_FORWARD_CURVE + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] read = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getInt(c.getColumnIndex(COLUMN_FORWARD_CURVE));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        return read;
    }

    public int[] getSIDECURVE(double startTime, double endTime){
        String selectQuery = "SELECT " + COLUMN_SIDE_CURVE + " FROM " + TABLE_POSTURE + " WHERE TIMESTAMP > " + startTime + " AND TIMESTAMP < " + endTime;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int size = c.getCount();
        int[] read = new int[size];
        int i =0;
        //
        if (c.moveToFirst()){
            do{
                read[i] = c.getInt(c.getColumnIndex(COLUMN_SIDE_CURVE));
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
}
