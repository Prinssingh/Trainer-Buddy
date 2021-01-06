package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.TrainerOfferingPlans;

import static android.app.Activity.RESULT_OK;

public class OfferingPlans extends Fragment {

    LocalDataBaseHandler LDB;
    RecyclerView recyclerView;
    TextView NoItemIndicator;
    OfferingPlansListAdapter adapter;
    OfferingPlansListSwipeController swipControler;


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
                startActivityForResult(intent,2);

            }
        });


        PopulatePlanList();


        return Root;
    }
    public void PopulatePlanList(){
        try{
            ArrayList <SubscriptionPlan> myListData= LDB.getTrainerOfferingPlanLDB();
            if( myListData.size()==0){
                NoItemIndicator.setVisibility(View.VISIBLE);
                return;
            }
            // Adapter
            adapter = new OfferingPlansListAdapter(myListData,this,requireContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(adapter);

            // Swipe Control
            swipControler= new OfferingPlansListSwipeController(requireContext(), recyclerView) {
              @Override
              public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                        underlayButtons.add(new OfferingPlansListSwipeController.UnderlayButton(
                                "Delete",
                                R.drawable.address,
                                Color.parseColor("#FF3C30"),
                                new OfferingPlansListSwipeController.UnderlayButtonClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        // TODO: onDelete
                                    }
                                }
                        ));

                        underlayButtons.add(new OfferingPlansListSwipeController.UnderlayButton(
                                "Edit",
                                R.drawable.address,
                                Color.parseColor("#FF9502"),
                                new OfferingPlansListSwipeController.UnderlayButtonClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        // TODO: OnTransfer
                                    }
                                }
                        ));

                    }

            };




            NoItemIndicator.setVisibility(View.INVISIBLE);
        }
        catch (Exception e) {
            Toast.makeText(requireContext(), "Error" + e, Toast.LENGTH_SHORT).show();
            NoItemIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("HERE", "Request Code: "+requestCode);
        if (requestCode==2){
            //ADD
            if (data!=null && resultCode==RESULT_OK){
                SubscriptionPlan Plan= (SubscriptionPlan) data.getSerializableExtra("Plan");
                adapter.addItem(adapter.getItemCount(),Plan);
            }
            else{
                Toast.makeText(requireContext(), "Failed to add New plan!", Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode==3){
            //EDIT
            Toast.makeText(requireContext(), "Request Code"+requestCode, Toast.LENGTH_SHORT).show();
            if (resultCode==RESULT_OK){
                int Position =(int)data.getExtras().get("Position");
                Log.e("TAG", "onActivityResult: "+Position );
                SubscriptionPlan Plan= (SubscriptionPlan) data.getSerializableExtra("Plan");
                adapter.Update(Position,Plan);

            }
            else{}
        }
    }


    public void ShowPlanEditDialog(){}
}