package gym.exercise.workout.trainerbuddy.DataBaseHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
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
    public static final String SUBSCRIPTION_PLAN_TABLE_NAME = "subscriptionplans";

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

    //subscription Plan table
    public static final String TITLE = "title";
    public static final String PLAN_ID = "planid";
    public static final String DAYS = "days";
    public static final String PRIZE = "prize";
    public static final String STARTING_DATE = "startingdate";
    public static final String EXPIRY_DATE = "expirydate";


    //Trainer Columns
    public static final String CERTIFICATES = "certificates";
    public static final String ABOUT = "about";

    //Trainee Columns
    public static final String THE_TRAINER = "trainer";



    // CREATE TABLES COMMAND
    public static final String CREATE_TRAINER_TABLE = "CREATE TABLE " + TRAINER_TABLE_NAME + "(" +

            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            NAME + " VARCHAR NOT NULL," + EMAIL +" VARCHAR NOT NULL,"+MOBILE +" VARCHAR," +
            PHOTO +" BLOB,"+ PASSWORD  + " VARCHAR NOT NULL," + WEIGHT +" INTEGER," + HEIGHT + " INTEGER," +
            AGE + " INTEGER," + GENDER + " VARCHAR," + ALTERNATE_MOBILE + " VARCHAR," +
            GYM_NAME + " VARCHAR," + GYM_ADDRESS + " VARCHAR," + DEVICE_ID +" VARCHAR,"+
            CERTIFICATES +" TEXT," + ABOUT +" TEXT" +");";

    public static final String CREATE_TRAINEE_TABLE = "CREATE TABLE " + TRAINEE_TABLE_NAME + "(" +

            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            NAME + " VARCHAR NOT NULL," + EMAIL +" VARCHAR NOT NULL,"+MOBILE +" VARCHAR," +
            PHOTO +" BLOB,"+ PASSWORD  + " VARCHAR NOT NULL," + WEIGHT +" INTEGER," + HEIGHT + " INTEGER," +
            AGE + " INTEGER," + GENDER + " VARCHAR," + ALTERNATE_MOBILE + " VARCHAR," +
            GYM_NAME + " VARCHAR," + GYM_ADDRESS + " VARCHAR," + DEVICE_ID +" VARCHAR,"+
            THE_TRAINER +" INTEGER,"+" FOREIGN KEY ("+THE_TRAINER+") REFERENCES "+TRAINER_TABLE_NAME+"("+COLUMN_ID+")"
            +");";

    public static final String CREATE_SUBSCRIPTION_PLAN_TABLE = "CREATE TABLE " + SUBSCRIPTION_PLAN_TABLE_NAME + "(" +

            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ PLAN_ID+" VARCHAR ,"+
            TITLE + " VARCHAR NOT NULL," + ABOUT +" VARCHAR ,"+
            DAYS +" INTEGER NOT NULL,"+ PRIZE +" INTEGER NOT NULL,"+
            STARTING_DATE +" VARCHAR,"+EXPIRY_DATE+" VARCHAR" +");";





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
        sqLiteDatabase.execSQL(CREATE_SUBSCRIPTION_PLAN_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {// on upgrade drop older tables
        // Deleting  previous tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRAINER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRAINEE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SUBSCRIPTION_PLAN_TABLE_NAME);

        //Creating New Tables
        onCreate(sqLiteDatabase);
    }

    public void setTrainerLDB(Trainer trainer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TRAINER_TABLE_NAME);

        ContentValues values = new ContentValues();
        values.put(NAME,trainer.getName());
        if(trainer.getPhoto()!=null){
            values.put(PHOTO,trainer.bitmapToByte(trainer.getPhoto()));}
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

        db.insert(TRAINER_TABLE_NAME, null, values);
        Log.d("LDB SET TRAINER", "setTrainerLDB: "+values);

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

        try{trainer.setPhoto(trainer.byteToBitmap(c.getBlob(c.getColumnIndex(PHOTO))));}
        catch (Exception ignored){}

        trainer.setMobile(c.getString(c.getColumnIndex(MOBILE)));
        trainer.setPassword(c.getString(c.getColumnIndex(PASSWORD)));
        trainer.setWeight(c.getInt(c.getColumnIndex(WEIGHT)));
        trainer.setHeight(c.getInt(c.getColumnIndex(HEIGHT)));
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

     public void setTrainerPhotoLDB(byte[] img,String UID){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(PHOTO,img);
         String whereClause = "id=?";
         String whereArgs[] = {"1"};
         db.update(TRAINER_TABLE_NAME, values,whereClause,whereArgs);
     }

    public void setTraineeLDB(Trainee trainee){
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


        db.insert(TRAINEE_TABLE_NAME, null, values);

    }

    public ArrayList<Trainee> getTraineesLDB(){
        ArrayList<Trainee> trainees= new ArrayList<Trainee>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TRAINEE_TABLE_NAME;

        Log.e("LOG", selectQuery);

        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){

                Trainee trainee =new Trainee();
                trainee.setName(c.getString(c.getColumnIndex(NAME)));
                trainee.setEmail(c.getString(c.getColumnIndex(EMAIL)));
                trainee.setPhoto(trainee.byteToBitmap(c.getBlob(c.getColumnIndex(PHOTO))));
                trainee.setMobile(c.getString(c.getColumnIndex(MOBILE)));
                trainee.setPassword(c.getString(c.getColumnIndex(PASSWORD)));
                trainee.setWeight(c.getInt(c.getColumnIndex(WEIGHT)));
                trainee.setHeight(c.getInt(c.getColumnIndex(HEIGHT)));
                trainee.setAge(c.getInt(c.getColumnIndex(AGE)));
                trainee.setAlternateMobile(c.getString(c.getColumnIndex(ALTERNATE_MOBILE)));
                trainee.setGender(c.getString(c.getColumnIndex(GENDER)));
                trainee.setGymName(c.getString(c.getColumnIndex(GYM_NAME)));
                trainee.setGymAddress(c.getString(c.getColumnIndex(GYM_ADDRESS)));
                trainee.setDeviceID(c.getString(c.getColumnIndex(DEVICE_ID)));

                trainees.add(trainee);
                c.moveToNext();
            }

        }
        return trainees;
    }

    public void setTrainerSubscriptionPlansLDB(SubscriptionPlan subscriptionPlan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAN_ID,subscriptionPlan.getID());
        values.put(TITLE,subscriptionPlan.getTitle());
        values.put(ABOUT,subscriptionPlan.getAbout());
        values.put(DAYS,subscriptionPlan.getDays());
        values.put(PRIZE,subscriptionPlan.getPrize());
        if(subscriptionPlan.getStartingDate()!=null)
        { values.put(STARTING_DATE,subscriptionPlan.getStartingDate());
          values.put(EXPIRY_DATE,subscriptionPlan.getExpiryDate());
        }

        db.insert(SUBSCRIPTION_PLAN_TABLE_NAME, null, values);
    }

    public ArrayList<SubscriptionPlan> getTrainerSubscriptionPlansLDB(){

        ArrayList<SubscriptionPlan> subscriptionPlans =new ArrayList<SubscriptionPlan>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SUBSCRIPTION_PLAN_TABLE_NAME;

        Log.e("LOG", selectQuery);

        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                SubscriptionPlan sPlan = new SubscriptionPlan();
                sPlan.setID(c.getString(c.getColumnIndex(PLAN_ID)));
                sPlan.setTitle(c.getString(c.getColumnIndex(TITLE)));
                sPlan.setAbout(c.getString(c.getColumnIndex(ABOUT)));
                sPlan.setPrize(c.getInt(c.getColumnIndex(PRIZE)));
                sPlan.setDays(c.getInt(c.getColumnIndex(DAYS)));
                sPlan.setStartingDate(c.getString(c.getColumnIndex(STARTING_DATE)));
                sPlan.setExpiryDate(c.getString(c.getColumnIndex(EXPIRY_DATE)));
                subscriptionPlans.add(sPlan);
                c.moveToNext();
            }
        }

        return subscriptionPlans;

    }

    public int getTrainerSubscriptionPlansCountLDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SUBSCRIPTION_PLAN_TABLE_NAME;

        Log.e("LOG", selectQuery);

        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
        return c.getCount();
    };

    public void ClearTrainerSubscriptionPlansLDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SUBSCRIPTION_PLAN_TABLE_NAME, null, null);
//        String deleteSubscriptions = "DELETE FROM " + SUBSCRIPTION_PLAN_TABLE_NAME;
//
//
//        @SuppressLint("Recycle") Cursor c = db.rawQuery(deleteSubscriptions, null);


    }
}
