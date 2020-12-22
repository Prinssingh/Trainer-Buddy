package gym.exercise.workout.trainerbuddy.DataBaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;

public class LocalDataBaseHandler extends SQLiteOpenHelper {

    //Database Name
    public static final String DATABASE_NAME = "TrainerBuddy.db";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    //Tables Name
    public static final String TRAINER_TABLE_NAME = "trainer";
    public static final String TRAINEE_TABLE_NAME = "trainee";

    //Common Columns
    public static final String COLUMN_ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String PHOTO = "photo";
    public static final String PASSWORD = "password";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String ALTERNATE_MOBILE = "altemobile";
    public static final String GYM_NAME = "gymname";
    public static final String GYM_ADDRESS = "gymaddress";
    public static final String DEVICE_ID = "deviceid";


    //Trainer Columns
    public static final String CERTIFICATES = "certificates";
    public static final String ABOUT = "about";

    //Trainee Columns
    public static final String THE_TRAINER = "trainer";



    // CREATE TABLES COMMAND
    public static final String CREATE_TRAINER_TABLE = "CREATE TABLE " + TRAINER_TABLE_NAME + "(" +
            COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+
            NAME + "VARCHAR NOT NULL," + EMAIL +"VARCHAR NOT NULL,"+MOBILE +"VARCHAR," +
            PHOTO +"BLOB,"+ PASSWORD  + "VARCHAR NOT NULL," + WEIGHT +"FLOAT," + HEIGHT + "FLOAT," +
            AGE + "INTEGER," + GENDER + "VARCHAR," + ALTERNATE_MOBILE + "VARCHAR," +
            GYM_NAME + "VARCHAR," + GYM_ADDRESS + "VARCHAR," + DEVICE_ID +"VARCHAR,"+
            CERTIFICATES +"TEXT," + ABOUT +"TEXT" +");";

    public static final String CREATE_TRAINEE_TABLE = "CREATE TABLE " + TRAINEE_TABLE_NAME + "(" +
            COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+
            NAME + "VARCHAR NOT NULL," + EMAIL +"VARCHAR NOT NULL,"+MOBILE +"VARCHAR," +
            PHOTO +"BLOB,"+ PASSWORD  + "VARCHAR NOT NULL," + WEIGHT +"FLOAT," + HEIGHT + "FLOAT," +
            AGE + "INTEGER," + GENDER + "VARCHAR," + ALTERNATE_MOBILE + "VARCHAR," +
            GYM_NAME + "VARCHAR," + GYM_ADDRESS + "VARCHAR," + DEVICE_ID +"VARCHAR,"+
            THE_TRAINER +"INTEGER,"+" FOREIGN KEY ("+THE_TRAINER+") REFERENCES "+TRAINER_TABLE_NAME+"("+COLUMN_ID+")"
            +");";





    public LocalDataBaseHandler(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    public LocalDataBaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TRAINER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TRAINEE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {// on upgrade drop older tables
        // Deleting  previous tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRAINER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRAINEE_TABLE_NAME);

        //Creating New Tables
        onCreate(sqLiteDatabase);
    }

    public long setTrainerLDB(Trainer trainer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,trainer.getName());
        values.put(PHOTO,trainer.bitmapToByte(trainer.getPhoto()));
        values.put(EMAIL,trainer.getEmail());
        values.put(MOBILE,trainer.getMobile());
        values.put(PASSWORD,trainer.getPassword());
        values.put(WEIGHT,trainer.getWeight());
        values.put(HEIGHT,trainer.getHeight());
        values.put(AGE,trainer.getAge());
        values.put(ALTERNATE_MOBILE,trainer.getAlternateMobile());
        values.put(GENDER,trainer.getGender());
        values.put(GYM_NAME,trainer.getGymName());
        values.put(GYM_ADDRESS,trainer.getGymAddress());
        values.put(DEVICE_ID,trainer.getDeviceID());
        values.put(CERTIFICATES,trainer.getCertificates());
        values.put(ABOUT,trainer.getAbout());

        return  db.insert(TRAINER_TABLE_NAME, null, values);

    }

    public Trainer getTrainerLDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TRAINER_TABLE_NAME;

        Log.e("LOG", selectQuery);

        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        Trainer trainer =new Trainer();
        trainer.setName(c.getString(c.getColumnIndex(NAME)));
        trainer.setEmail(c.getString(c.getColumnIndex(EMAIL)));

        trainer.setPhoto(trainer.byteToBitmap(c.getBlob(c.getColumnIndex(NAME))));

        trainer.setMobile(c.getString(c.getColumnIndex(MOBILE)));
        trainer.setPassword(c.getString(c.getColumnIndex(PASSWORD)));
        trainer.setWeight(c.getFloat(c.getColumnIndex(WEIGHT)));
        trainer.setHeight(c.getFloat(c.getColumnIndex(HEIGHT)));
        trainer.setAge(c.getInt(c.getColumnIndex(AGE)));
        trainer.setAlternateMobile(c.getString(c.getColumnIndex(ALTERNATE_MOBILE)));
        trainer.setGender(c.getString(c.getColumnIndex(GENDER)));
        trainer.setGymName(c.getString(c.getColumnIndex(GYM_NAME)));
        trainer.setGymAddress(c.getString(c.getColumnIndex(GYM_ADDRESS)));
        trainer.setDeviceID(c.getString(c.getColumnIndex(DEVICE_ID)));

        trainer.setCertificates(c.getString(c.getColumnIndex(CERTIFICATES)));
        trainer.setAbout(c.getString(c.getColumnIndex(ABOUT)));



        return trainer;
    }

    public long setTraineeLDB(Trainee trainee){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME,trainee.getName());
        values.put(PHOTO,trainee.bitmapToByte(trainee.getPhoto()));
        values.put(EMAIL,trainee.getEmail());
        values.put(MOBILE,trainee.getMobile());
        values.put(PASSWORD,trainee.getPassword());
        values.put(WEIGHT,trainee.getWeight());
        values.put(HEIGHT,trainee.getHeight());
        values.put(AGE,trainee.getAge());
        values.put(ALTERNATE_MOBILE,trainee.getAlternateMobile());
        values.put(GENDER,trainee.getGender());
        values.put(GYM_NAME,trainee.getGymName());
        values.put(GYM_ADDRESS,trainee.getGymAddress());
        values.put(DEVICE_ID,trainee.getDeviceID());


        return  db.insert(TRAINEE_TABLE_NAME, null, values);

    }

    public Trainee getTraineeLDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TRAINER_TABLE_NAME;

        Log.e("LOG", selectQuery);

        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        Trainee trainee =new Trainee();
        trainee.setName(c.getString(c.getColumnIndex(NAME)));
        trainee.setEmail(c.getString(c.getColumnIndex(EMAIL)));
        //TODO
        //trainer.setPhoto(c.get(c.getColumnIndex(NAME)));

        trainee.setMobile(c.getString(c.getColumnIndex(MOBILE)));
        trainee.setPassword(c.getString(c.getColumnIndex(PASSWORD)));
        trainee.setWeight(c.getFloat(c.getColumnIndex(WEIGHT)));
        trainee.setHeight(c.getFloat(c.getColumnIndex(HEIGHT)));
        trainee.setAge(c.getInt(c.getColumnIndex(AGE)));
        trainee.setAlternateMobile(c.getString(c.getColumnIndex(ALTERNATE_MOBILE)));
        trainee.setGender(c.getString(c.getColumnIndex(GENDER)));
        trainee.setGymName(c.getString(c.getColumnIndex(GYM_NAME)));
        trainee.setGymAddress(c.getString(c.getColumnIndex(GYM_ADDRESS)));
        trainee.setDeviceID(c.getString(c.getColumnIndex(DEVICE_ID)));


        return trainee;
    }

}
