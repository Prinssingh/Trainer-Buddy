package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;

public class OfferingPlanEdit extends Fragment {
    private  String PlanId;
    TextInputEditText Title,About,Prize ,Days;
    ImportantFunctions impFun;
    DataBaseHandler DB;
    LocalDataBaseHandler LDB;
    public OfferingPlanEdit(String planId) {
        PlanId = planId;
    }

    public static OfferingPlanEdit newInstance(String PlanId) {
        return new OfferingPlanEdit(PlanId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root= inflater.inflate(R.layout.trainer_offering_plan_edit, container, false);

        impFun =new ImportantFunctions(requireContext());
        DB = new DataBaseHandler(requireContext());
        LDB = new LocalDataBaseHandler(requireContext());

        SubscriptionPlan mplan =LDB.getTrainerOfferedPlanByID(PlanId);

        Title=Root.findViewById(R.id.offeringPlanEditTitle);
        Title.setText(mplan.getTitle());
        About=Root.findViewById(R.id.offeringPlanEditAbout);
        About.setText(mplan.getAbout());
        Prize=Root.findViewById(R.id.offeringPlanEditPrize);
        Prize.setText(String.valueOf(mplan.getPrize()));
        Days=Root.findViewById(R.id.offeringPlanEditDays);
        Days.setText(String.valueOf(mplan.getDays()));


        Button AddPlan = Root.findViewById(R.id.offeringPlanUpdateButton);
        AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTrainersOfferingPlan();
            }
        });
        return Root;
    }

    private  void clearAllEnteries(){
        Title.setText("");
        About.setText("");
        Prize.setText("");
        Days.setText("");
    }

    private SubscriptionPlan getInputPlan(){
        SubscriptionPlan plan =new SubscriptionPlan();
        plan.setID(PlanId);
        plan.setTitle(Title.getText().toString());
        plan.setAbout(About.getText().toString());
        plan.setDays(Integer.parseInt(Days.getText().toString()));
        plan.setPrize(Integer.parseInt(Prize.getText().toString()));

        return plan;
    }


    private boolean isValidInput(){

        // todo Validation Code

        return true;
    }

    private  void UpdateTrainersOfferingPlan(){
        if (isValidInput()){
            if(impFun.isConnectedToInternet()){
                SubscriptionPlan mplan =getInputPlan();
                String UID = impFun.getSharedPrefUID();
                try{
                    // TODO Update Global and Local Data!!
                    //DB.setTrainersOfferingPlan(UID,mplan); // it Internally sets LDB;
                    Toast.makeText(requireContext(), "Plan Update Success!!", Toast.LENGTH_SHORT).show();
                    clearAllEnteries();
                    //Todo finish the activity and restart dashbaord activity
                    requireActivity().finish();
                }
                catch (Exception e){
                    Log.d("DB", "AddTrainersNewOfferingPlan: Error"+e);
                }
            }
            else{
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }

        }
    }

}