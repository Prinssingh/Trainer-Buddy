package gym.exercise.workout.trainerbuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences sp;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slpash_screen);
        sp =this.getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
       // sp.edit().clear().commit();

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
}