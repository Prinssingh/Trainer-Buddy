package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.TrainerOfferingPlans;

public class OfferingPlans extends Fragment {



    public static OfferingPlans newInstance() {

        return new OfferingPlans();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Root=inflater.inflate(R.layout.trainer_offering_plans, container, false);

        Button AddPlan = Root.findViewById(R.id.AddOfferingPlanButton);
        AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainerOfferingPlans.class);
                startActivity(intent);
            }
        });

        return Root;
    }
}