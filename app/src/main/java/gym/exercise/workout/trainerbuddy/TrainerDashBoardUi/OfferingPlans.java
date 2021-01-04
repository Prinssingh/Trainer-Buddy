package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.TrainerOfferingPlans;

public class OfferingPlans extends Fragment {

    LocalDataBaseHandler LDB;
    RecyclerView recyclerView;
    TextView NoItemIndicator;

    public static OfferingPlans newInstance() {

        return new OfferingPlans();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Root=inflater.inflate(R.layout.trainer_offering_plans, container, false);
        recyclerView =Root.findViewById(R.id.TrainersOfferingPlansList);
        LDB= new LocalDataBaseHandler(requireContext());
        NoItemIndicator= Root.findViewById(R.id.NoOfferingPlansToShow);
        Button AddPlan = Root.findViewById(R.id.AddOfferingPlanButton);
        AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainerOfferingPlans.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

      try{
          NoItemIndicator.setVisibility(View.INVISIBLE);
          SubscriptionPlan[] myListData= LDB.getTrainerOfferingPlanLDB().toArray(new SubscriptionPlan[0]);
          OfferingPlansListAdapter adapter = new OfferingPlansListAdapter(myListData,requireContext());
          recyclerView.setHasFixedSize(true);
          recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
          recyclerView.setAdapter(adapter);
      }
      catch (Exception e) {
          Toast.makeText(requireContext(), "Error" + e, Toast.LENGTH_SHORT).show();
          NoItemIndicator.setVisibility(View.VISIBLE);
      }

        return Root;
    }
}