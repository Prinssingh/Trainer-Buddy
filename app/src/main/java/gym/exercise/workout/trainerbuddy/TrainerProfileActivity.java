package gym.exercise.workout.trainerbuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.TrainerProfileShow;

public class TrainerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_profile_activity);

//        Toolbar toolbar = findViewById(R.id.TrainerProfileToolbar);
//        setSupportActionBar(toolbar);
//        setTitle("");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.TrainerProfileContainer, TrainerProfileShow.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}