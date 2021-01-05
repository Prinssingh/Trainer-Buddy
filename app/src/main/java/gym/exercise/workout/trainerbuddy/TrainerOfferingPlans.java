package gym.exercise.workout.trainerbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.OfferingPlanAdd;
import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.OfferingPlanEdit;

public class TrainerOfferingPlans extends AppCompatActivity {

    private String PlanId;
    int  Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_offering_plans_activity);

        Toolbar toolbar= findViewById(R.id.trainersToolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Getting Extras

        if(getIntent()!= null) {
            Bundle extras = getIntent().getExtras();
            Log.e("Test","+"+extras);
           if (extras!=null){
               PlanId = extras.getString("PlanID");
               Position = extras.getInt("Position");
           }
        }


        if (savedInstanceState == null) {
            if (PlanId==null)
                ChangeFragment(OfferingPlanAdd.newInstance());
            else{
                ChangeFragment(OfferingPlanEdit.newInstance(PlanId,Position));
            }
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

    public void  ChangeFragment(Fragment fragment){

        FragmentManager fragmentManager = TrainerOfferingPlans.this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.OfferingPlanscontainer,  fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        }
}