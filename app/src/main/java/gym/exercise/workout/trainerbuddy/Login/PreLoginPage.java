package gym.exercise.workout.trainerbuddy.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gym.exercise.workout.trainerbuddy.R;

public class PreLoginPage extends Fragment implements View.OnClickListener {
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public static PreLoginPage newInstance() {
        return new PreLoginPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root =inflater.inflate(R.layout.pre_login_page, container, false);

        sp=requireContext().getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
        editor=sp.edit();

        Root.findViewById(R.id.Trainer).setOnClickListener(this);
        Root.findViewById(R.id.Trainee).setOnClickListener(this);
        Root.findViewById(R.id.NewUser).setOnClickListener(this);

        return Root ;
    }




    public void  ChangeFragment(Fragment fragment){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,  fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Trainer:
                editor.putString("Type","Trainer").commit();

                break;
            case R.id.Trainee:
                editor.putString("Type","Trainee").commit();
                break;

            case R.id.NewUser:
                editor.putString("Type","NewUser").apply();
                break;
        }
        ChangeFragment(LoginPage.newInstance());
    }
}