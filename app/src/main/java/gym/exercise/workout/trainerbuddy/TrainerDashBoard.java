package gym.exercise.workout.trainerbuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.AddNewTrainee;
import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.Earnings;
import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.Home;
import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.Profile;
import gym.exercise.workout.trainerbuddy.TrainerDashBoardUi.TraineeList;

public class TrainerDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dash_board);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        //mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Bottom Navigation Control
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.BottomNav);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.Trainee_Home); // change to whichever id should be default
             ChangeFragment(Home.newInstance());
        }
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Trainer_AddNewTrainee:
                        ChangeFragment(AddNewTrainee.newInstance());
                        break;
                    case R.id.Trainer_TraineeList:
                        ChangeFragment(TraineeList.newInstance());
                        break;
                    case R.id.Trainee_Home:
                        ChangeFragment(Home.newInstance());
                        break;
                    case R.id.Trainer_Profile:
                        ChangeFragment(Profile.newInstance());
                        break;
                    case R.id.Trainer_Earnings:
                        ChangeFragment(Earnings.newInstance());
                        break;

                }
                return true;

            }
        });



    }

    public void  ChangeFragment(Fragment fragment){

        FragmentManager fragmentManager = TrainerDashBoard.this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.DashBoardContainer,  fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}