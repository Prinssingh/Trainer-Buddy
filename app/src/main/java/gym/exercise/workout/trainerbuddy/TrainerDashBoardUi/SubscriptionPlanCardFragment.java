package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;

public class SubscriptionPlanCardFragment extends Fragment {
    private SubscriptionPlan mPlan;

    public SubscriptionPlanCardFragment(SubscriptionPlan mPlan) {
        this.mPlan = mPlan;
    }

    public SubscriptionPlanCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Root =inflater.inflate(R.layout.subscription_plan_card_fragment, container, false);


        return Root;
    }
}