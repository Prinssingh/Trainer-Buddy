package gym.exercise.workout.trainerbuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;

public class SplashScreen extends AppCompatActivity {
   ImportantFunctions impFun;
    //TODO
    SharedPreferences sp;
    DataBaseHandler db;
    LocalDataBaseHandler LDB;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slpash_screen);

        sp =this.getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
       // sp.edit().clear().commit();

        impFun=new ImportantFunctions(this);
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
       String UID= impFun.getSharedPrefUID();
        String UserType= impFun.getSharedPrefUserType();
       if (UID!=null){
           if(UserType.equals("Trainer")) {
               try {
                   db.getTrainer(UID);// it sets LOCAL DATA BASE internally
                   db.getTrainerSubscriptionGlobalPlans();
                    //todo testing

               }catch (Exception e){
                   Log.d("Trying To save LDB", "init: Error"+e);
               }
           }
           //TODO load Trainee and NewUserData Accordingly
//           else if(UserType.equals("Trainee")){}
//           else if(UserType.equals("NewUser")){}

       }

    }
}