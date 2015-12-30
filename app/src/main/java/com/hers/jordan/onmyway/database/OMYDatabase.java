package com.hers.jordan.onmyway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jordan on 21/12/2015.
 */
public class OMYDatabase extends SQLiteOpenHelper {

    private final static String dbName = "OnMyWayDatabase";
    private final static int dbVersion = 1;

    private static final String TABLE_PATHS = "paths";
    private static final String COLONNE_PATHS_ID = "_id";
    private static final String COLONNE_PATHS_NAME = "name";
    private static final String COLONNE_PATHS_START_ADDRESS = "startAddress";
    private static final String COLONNE_PATHS_START_POSITION_LAT = "startPositionLatitude";
    private static final String COLONNE_PATHS_START_POSITION_LONG = "startPositionLongitude";
    private static final String COLONNE_PATHS_FIRST_STEP = "firstStep";

    private static final String TABLE_STEPS = "steps";
    private static final String COLONNE_STEPS_ID = "_id";
    private static final String COLONNE_STEPS_NAME = "name";
    private static final String COLONNE_STEPS_POSITION_LAT = "latitude";
    private static final String COLONNE_STEPS_POSITION_LONG = "longitude";
    private static final String COLONNE_STEPS_TRANSPORT = "transport";
    private static final String COLONNE_STEPS_TIME = "time";
    private static final String COLONNE_STEPS_NEXT_STEP = "nextStep";

    private static final String TABLE_RANKING = "ranking";
    private static final String COLONNE_RANKING_ID = "_id";
    private static final String COLONNE_RANKING_DATE = "date";
    private static final String COLONNE_RANKING_TIME = "time";
    private static final String COLONNE_RANKING_PATH = "path";

    private static final String CREATE_TABLE_PATHS =
            "create table " + TABLE_PATHS + " ("
            + COLONNE_PATHS_ID + " integer primary key, "
            + COLONNE_PATHS_NAME + " text not null, "
            + COLONNE_PATHS_START_ADDRESS + " text not null, "
            + COLONNE_PATHS_START_POSITION_LAT + " real not null, "
            + COLONNE_PATHS_START_POSITION_LONG + " real not null, "
            + "foreign key (" + COLONNE_PATHS_FIRST_STEP + ") references "
            + TABLE_STEPS + " (" + COLONNE_STEPS_ID + "));";

    private static final String CREATE_TABLE_STEPS =
            "create table " + TABLE_STEPS + " ("
            + COLONNE_STEPS_ID + " integer primary key, "
            + COLONNE_STEPS_NAME + " text not null, "
            + COLONNE_STEPS_POSITION_LAT + " real not null, "
            + COLONNE_STEPS_POSITION_LONG + " real not null, "
            + COLONNE_STEPS_TRANSPORT + " text not null, "
            + COLONNE_STEPS_TIME + " integer not null, "
            + "foreign key (" + COLONNE_STEPS_NEXT_STEP + ") references "
            + TABLE_STEPS + " (" + COLONNE_STEPS_ID + "));";

    private static final String CREATE_TABLE_RANKING =
            "create table " + TABLE_RANKING + " ("
            + COLONNE_RANKING_ID + " integer primary key, "
            + COLONNE_RANKING_DATE + " text not null, "
            + COLONNE_RANKING_TIME + " integer not null, "
            + "foreign key (" + COLONNE_RANKING_PATH + ") references "
            + TABLE_PATHS + " (" + COLONNE_PATHS_ID + "));";

    public OMYDatabase(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // dans un ordre précis pour les fks
        db.execSQL(CREATE_TABLE_STEPS);
        db.execSQL(CREATE_TABLE_PATHS);
        db.execSQL(CREATE_TABLE_RANKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dans un ordre précis pour les fks
        db.execSQL("drop table if exists " + TABLE_RANKING + ";");
        db.execSQL("drop table if exists " + CREATE_TABLE_PATHS + ";");
        db.execSQL("drop table if exists " + CREATE_TABLE_STEPS + ";");
        onCreate(db);
    }

    // return -1 si sa na pas fonctionner
    public Long insertPath(String name, String startAddress, double latitude, double longitude, String refFirstStep){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLONNE_PATHS_NAME, name);
        contentValues.put(COLONNE_PATHS_START_ADDRESS, startAddress);
        contentValues.put(COLONNE_PATHS_START_POSITION_LAT, latitude);
        contentValues.put(COLONNE_PATHS_START_POSITION_LONG, longitude);
        contentValues.put(COLONNE_PATHS_FIRST_STEP, refFirstStep);

        return db.insert(TABLE_PATHS, null, contentValues);
    }

    // return -1 si sa na pas fonctionner
    public Long insertStep(String name, double latitude, double longitude, String transport, long time, String refNextStep){

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String dateTime = sdf.format(time);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLONNE_STEPS_NAME, name);
        contentValues.put(COLONNE_STEPS_POSITION_LAT, latitude);
        contentValues.put(COLONNE_STEPS_POSITION_LONG, longitude);
        contentValues.put(COLONNE_STEPS_TRANSPORT, transport);
        contentValues.put(COLONNE_STEPS_TIME, dateTime);
        contentValues.put(COLONNE_STEPS_NEXT_STEP, refNextStep);

        return db.insert(TABLE_STEPS, null, contentValues);
    }

    // return -1 si sa na pas fonctionner
    public Long insertRanking(Date date, long time, String refPath){

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String dateTime = sdf.format(date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLONNE_RANKING_DATE, dateTime);
        contentValues.put(COLONNE_RANKING_TIME, time);
        contentValues.put(COLONNE_RANKING_PATH, refPath);

        return db.insert(TABLE_RANKING, null, contentValues);
    }

    public Cursor getPath(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from" + TABLE_PATHS + " where id=" + id + "", null);
        return res;
    }

    public Cursor getStep(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from" + TABLE_STEPS + " where id=" + id + "", null);
        return res;
    }

    public Cursor getRanking(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from" + TABLE_RANKING + " where id=" + id + "", null );
        return res;
    }

    public int numberOfRowsPath(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_PATHS);
        return numRows;
    }

    public int numberOfRowsStep(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_STEPS);
        return numRows;
    }

    // return the number of ranking in the database
    public int numberOfRowsRanking(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_RANKING);
        return numRows;
    }

    // update path where _id = id with all new data
    public boolean updatePath (int id, String name, String startAddress, double latitude, double longitude, String refFirstStep)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLONNE_PATHS_NAME, name);
        contentValues.put(COLONNE_PATHS_START_ADDRESS, startAddress);
        contentValues.put(COLONNE_PATHS_START_POSITION_LAT, latitude);
        contentValues.put(COLONNE_PATHS_START_POSITION_LONG, longitude);
        contentValues.put(COLONNE_PATHS_FIRST_STEP, refFirstStep);

        db.update(TABLE_PATHS, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    // update step where _id = id with all new data
    public boolean updateStep (int id, String name, double latitude, double longitude, String transport, long time, String refNextStep){

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String dateTime = sdf.format(time);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLONNE_STEPS_NAME, name);
        contentValues.put(COLONNE_STEPS_POSITION_LAT, latitude);
        contentValues.put(COLONNE_STEPS_POSITION_LONG, longitude);
        contentValues.put(COLONNE_STEPS_TRANSPORT, transport);
        contentValues.put(COLONNE_STEPS_TIME, dateTime);
        contentValues.put(COLONNE_STEPS_NEXT_STEP, refNextStep);

        db.update(TABLE_STEPS, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    // update ranking where _id = id with all new data
    public boolean updateRanking (int id, Date date, long time, String refPath){

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String dateTime = sdf.format(date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLONNE_RANKING_DATE, dateTime);
        contentValues.put(COLONNE_RANKING_TIME, time);
        contentValues.put(COLONNE_RANKING_PATH, refPath);

        db.update(TABLE_RANKING, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    // delete the path with _id = id with everyy steps and ranking concern
    public Integer deletePath (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // cursor of the Path to remove
        Cursor cursorPath = getPath(id);

        // delete all steps, (remove from the last step until the first)
        int numRow = cursorPath.getColumnIndex(COLONNE_PATHS_FIRST_STEP);
        int idFirstStep = cursorPath.getInt(numRow);
        deleteStep(idFirstStep);

        // delete all ranking
        deleteRankings(id);

        // delete path
        return db.delete(TABLE_PATHS, "id = ? ", new String[] { Integer.toString(id) });
    }

    // delete the stap with _id = id
    public Integer deleteStep (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // cursor of the step to remove
        Cursor cursorStep = getStep(id);

        // delete next step if exist
        int nextStepId = cursorStep.getColumnIndexOrThrow(COLONNE_STEPS_NEXT_STEP);

        // next step exist
        if(nextStepId!=-1){
            deleteStep(nextStepId);
        }

        return db.delete(TABLE_STEPS, "id = ? ", new String[] { Integer.toString(id) });
    }

    // delete all ranking with the fk = pathId
    public Integer deleteRankings (int pathId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RANKING, COLONNE_RANKING_PATH + " = ? ", new String[] { Integer.toString(pathId) });
    }

    // delete all ranking with _id = id
    public Integer deleteRanking (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RANKING, "id = ? ", new String[] { Integer.toString(id) });
    }

    // return an arraylist of every path name
    public ArrayList<String> getAllPathName()
    {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_PATHS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLONNE_PATHS_NAME)));
            res.moveToNext();
        }
        return array_list;
    }



}
