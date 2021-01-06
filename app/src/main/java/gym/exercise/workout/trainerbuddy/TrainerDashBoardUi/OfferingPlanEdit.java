package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Intent;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class OfferingPlanEdit extends Fragment {
    private final String PlanId;
    private final int Position;
    TextInputEditText Title,About,Prize ,Days;
    ImportantFunctions impFun;
    DataBaseHandler DB;
    LocalDataBaseHandler LDB;

    public OfferingPlanEdit(String planId, int position) {
        PlanId = planId;
        Position =position;
    }

    public static OfferingPlanEdit newInstance(String PlanId,int Position) {
        return new OfferingPlanEdit(PlanId,Position);
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


    private  void UpdateTrainersOfferingPlan(){
        if (isValidInput()){
            if(impFun.isConnectedToInternet()){
                SubscriptionPlan mplan =getInputPlan();
                String UID = impFun.getSharedPrefUID();
                try{
                    // TODO Update Global and Local Data!!
                    DB.UpdateTrainersOfferingPlan(UID,mplan);
                    Toast.makeText(requireContext(), "Plan Update Success!!", Toast.LENGTH_SHORT).show();
                    clearAllEnteries();
                    //Todo finish the activity and restart dashbaord activity
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Position",Position);
                    Log.e("TAG", ""+Position);
                    returnIntent.putExtra("Plan",mplan);
                    requireActivity().setResult(RESULT_OK,returnIntent);
                    requireActivity().finish();
                }
                catch (Exception e){
                    Log.d("DB", "AddTrainersNewOfferingPlan: Error"+e);
                    Intent returnIntent = new Intent();
                    //returnIntent.putExtra("result",result);
                    requireActivity().setResult(RESULT_CANCELED,returnIntent);
                    requireActivity().finish();

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