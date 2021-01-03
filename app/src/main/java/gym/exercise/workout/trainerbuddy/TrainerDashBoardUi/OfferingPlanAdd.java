package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;

public class OfferingPlanAdd extends Fragment {

    ImportantFunctions impFun;
    DataBaseHandler DB;

    TextInputEditText Title,About,Prize ,Days;
    public static OfferingPlanAdd newInstance() {
        return new OfferingPlanAdd();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root= inflater.inflate(R.layout.trainer_offering_plan_add, container, false);

        impFun =new ImportantFunctions(requireContext());
        DB = new DataBaseHandler(requireContext());

        Title=Root.findViewById(R.id.offeringPlanAddTitle);
        About=Root.findViewById(R.id.offeringPlanAddAbout);
        Prize=Root.findViewById(R.id.offeringPlanAddPrize);
        Days=Root.findViewById(R.id.offeringPlanAddDays);

        Button AddPlan = Root.findViewById(R.id.offeringPlanAddButton);
        AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Saving Plan...", Toast.LENGTH_SHORT).show();
                AddTrainersNewOfferingPlan();
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

        plan.setTitle(Title.getText().toString());
        plan.setAbout(About.getText().toString());
        plan.setDays(Integer.parseInt(Days.getText().toString()));
        plan.setPrize(Integer.parseInt(Prize.getText().toString()));

        return plan;
    }

    private  void AddTrainersNewOfferingPlan(){
        if (isValidInput()){
            if(impFun.isConnectedToInternet()){
                SubscriptionPlan mplan =getInputPlan();
                String UID = impFun.getSharedPrefUID();
                try{
                    DB.setTrainersOfferingPlan(UID,mplan); // it Internally sets LDB;
                    Toast.makeText(requireContext(), "Plan Saved Success!!", Toast.LENGTH_SHORT).show();
                    // TODO Clear All Enteries!!
                    clearAllEnteries();
                    requireActivity().finish();}
                catch (Exception e){
                    Log.d("DB", "AddTrainersNewOfferingPlan: Error"+e);
                }
            }
            else{
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }

        }
    }

    public boolean isValidInput(){
        boolean valid=true;
        if(Title.getText().toString().isEmpty()){
            Title.setError("Your can't left Title field empty");
            Title.requestFocus();
            valid=false;
        }
        else if( About.getText().toString().isEmpty()){
            About.setError("You can't About field empty!!");
            About.requestFocus();
            valid=false;
        }
        else if(Prize.getText().toString().isEmpty()){
            Prize.setError("Your can't left Prize field empty");
            Prize.requestFocus();
            valid=false;
        }
        else if(Days.getText().toString().isEmpty()){
            Days.setError("Your can't left Days field empty");
            Days.requestFocus();
            valid=false;
        }
        return valid;
    }

    public void setAllDisable(){
        Title.setEnabled(false);
        About.setEnabled(false);
        Prize.setEnabled(false);
        Days.setEnabled(false);
    }

    public void setAllEnable(){
        Title.setEnabled(true);
        About.setEnabled(true);
        Prize.setEnabled(true);
        Days.setEnabled(true);
    }

}