package gym.exercise.workout.trainerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.OfferingPlanAdd;

public class TrainerOfferingPlans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_offering_plans_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OfferingPlanAdd.newInstance())
                    .commitNow();
        }
    }
}