package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import gym.exercise.workout.trainerbuddy.R;

public class TraineeList extends Fragment {


    public static TraineeList newInstance() {
        return new TraineeList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root=inflater.inflate(R.layout.trainer_trainee_list, container, false);




        return Root;
    }



}