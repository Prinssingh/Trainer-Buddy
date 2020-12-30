package gym.exercise.workout.trainerbuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.TrainerProfileShow;

public class TrainerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.trainer_profile_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.TrainerProfileContainer, TrainerProfileShow.newInstance())
                    .commitNow();
        }
    }
}