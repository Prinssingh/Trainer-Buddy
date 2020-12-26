package gym.exercise.workout.trainerbuddy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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

public class TrainerDashBoard extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dash_board);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.TrainerToolBarProfile).setOnClickListener(this);
        toolbar.findViewById(R.id.TrainerNotification).setOnClickListener(this);
        toolbar.findViewById(R.id.TrainerSettings).setOnClickListener(this);


        //TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);


        setSupportActionBar(toolbar);
        //mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Bottom Navigation Control
        navigation = (BottomNavigationView) findViewById(R.id.BottomNav);
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to quit?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.TrainerToolBarProfile:
                navigation.setSelectedItemId(R.id.Trainer_Profile);
                ChangeFragment(Profile.newInstance());
                break;

            case R.id.TrainerNotification:
                Intent notifications= new Intent(this,TrainerDashBoard.class);// TODO TrainerNotificationActivity.class
                startActivity(notifications);
                break;

            case R.id.TrainerSettings:
                Intent settings = new Intent(this,TrainerDashBoard.class); // TODO settings.class
                startActivity(settings);
                break;
        }

    }
}