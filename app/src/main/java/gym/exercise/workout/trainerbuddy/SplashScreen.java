package gym.exercise.workout.trainerbuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sp;
    DataBaseHandler db;
    //TODO
    LocalDataBaseHandler LDB;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slpash_screen);
        sp =this.getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
       // sp.edit().clear().commit();
        db= new DataBaseHandler(this);
        LDB= new LocalDataBaseHandler(this);
        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!sp.getBoolean("Login",false)){
                    Intent LoginIntent = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(LoginIntent);
                    finish();
                }
                else{
                    Intent LoginIntent = new Intent(SplashScreen.this,DashBoard.class);
                    startActivity(LoginIntent);
                    finish();
                }

            }
        }, 4000);
    }

    public void init(){
       String UID= sp.getString("User_UID",null);
       if (UID!=null){
           db.getTrainer(UID);// it sets LOCAL DATA BASE internally

           Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile);
           ByteArrayOutputStream stream = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
           byte[] bitMapData = stream.toByteArray();
           Log.d("TAG", "init: "+bitMapData);
           LDB.setTrainerPhotoLDB(bitMapData,UID);
       }

    }
}