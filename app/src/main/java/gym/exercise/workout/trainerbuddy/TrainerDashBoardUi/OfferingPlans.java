package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import gym.exercise.workout.trainerbuddy.R;

public class OfferingPlans extends Fragment {


    public OfferingPlans() {
        // Required empty public constructor
    }

    public static OfferingPlans newInstance() {

        return new OfferingPlans();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Root=inflater.inflate(R.layout.fragment_offering_plans, container, false);



        return Root;
    }
}