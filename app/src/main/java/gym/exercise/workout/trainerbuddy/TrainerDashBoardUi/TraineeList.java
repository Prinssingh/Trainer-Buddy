package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.R;

public class TraineeList extends Fragment {

    LocalDataBaseHandler LDB;
    RecyclerView recyclerView;

    public static TraineeList newInstance() {
        return new TraineeList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root=inflater.inflate(R.layout.trainer_trainee_list, container, false);
        recyclerView =Root.findViewById(R.id.Trainer_Trainee_List);
        LDB= new LocalDataBaseHandler(requireContext());



        return Root;
    }



}