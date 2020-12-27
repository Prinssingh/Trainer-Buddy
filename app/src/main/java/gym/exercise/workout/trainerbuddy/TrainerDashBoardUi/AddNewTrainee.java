package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.R;

public class AddNewTrainee extends Fragment implements View.OnClickListener {
    FirebaseUser Cself =  FirebaseAuth.getInstance().getCurrentUser();
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    View Root;
    TextInputEditText name,mobile,alternateMobile,email;
    EditText weight,height;

    RadioGroup genderRadioGroup;

    RadioButton male,female,other,
                inKg,inLbs,inInches,inMeter,
                student,officeJob,travellingJob,physicalJob,labourJob,
                gain,loss,maintain,musselsGain,musselsMaintain,
                vegetarian, nonVegetarian;
    TextView etDate;

    DatePickerDialog picker;

    Button continuebutton;

    //extra use
    String gender="male", WeightUnitPref ="inKg", HeightUnitPref ="inInches",
            occupation="student", WeightPref="gain", MusclesPref="gain", FoofPref="vegetarian";

    AppCompatSpinner ageSpinner,subscriptionFee;


    public static AddNewTrainee newInstance() {
        return new AddNewTrainee();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        sp=requireContext().getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
        editor=sp.edit();

        Root=inflater.inflate(R.layout.trainer_add_trainee, container, false);


        //TextInputEditText
        name = Root.findViewById(R.id.name);
        mobile = Root.findViewById(R.id.mobile);
        alternateMobile = Root.findViewById(R.id.alternateMobile);
        email = Root.findViewById(R.id.email);

        //RadioGroup
        genderRadioGroup = Root.findViewById(R.id.genderRadioGroup);
        genderRadioGroup.setOnClickListener(this);
        //RadioButton
        male = Root.findViewById(R.id.male);
        male.setActivated(true);
        male.setOnClickListener(this);
        female = Root.findViewById(R.id.female);
        female.setOnClickListener(this);
        other = Root.findViewById(R.id.other);
        other.setOnClickListener(this);
        inKg = Root.findViewById(R.id.inKg);
        inKg.setOnClickListener(this);
        inLbs = Root.findViewById(R.id.inLbs);
        inLbs.setOnClickListener(this);
        inInches = Root.findViewById(R.id.inInches);
        inInches.setOnClickListener(this);
        inMeter = Root.findViewById(R.id.inMeter);
        inMeter.setOnClickListener(this);
        student = Root.findViewById(R.id.student);
        student.setOnClickListener(this);
        officeJob = Root.findViewById(R.id.officeJob);
        officeJob.setOnClickListener(this);
        travellingJob = Root.findViewById(R.id.travellingJob);
        travellingJob.setOnClickListener(this);
        physicalJob = Root.findViewById(R.id.physicalJob);
        physicalJob.setOnClickListener(this);
        labourJob = Root.findViewById(R.id.labourJob);
        labourJob.setOnClickListener(this);
        gain = Root.findViewById(R.id.gain);
        gain.setOnClickListener(this);
        loss = Root.findViewById(R.id.loss);
        loss.setOnClickListener(this);
        maintain = Root.findViewById(R.id.maintain);
        maintain.setOnClickListener(this);
        musselsGain = Root.findViewById(R.id.musselsGain);
        musselsGain.setOnClickListener(this);
        musselsMaintain = Root.findViewById(R.id.musselsMaintain);
        musselsMaintain.setOnClickListener(this);
        vegetarian = Root.findViewById(R.id.vegetarian);
        vegetarian.setOnClickListener(this);
        nonVegetarian = Root.findViewById(R.id.nonvegetarian);
        nonVegetarian.setOnClickListener(this);

        //EditText
        etDate = Root.findViewById(R.id.etDate);
        etDate.setOnClickListener(this);
        weight = (EditText)Root.findViewById(R.id.weight);
        height = (EditText)Root.findViewById(R.id.height);


        //Spinner
        ageSpinner = Root.findViewById(R.id.ageSpinner);
        subscriptionFee = Root.findViewById(R.id.subscriptionFee);

        //Button
        continuebutton = Root.findViewById(R.id.continuebutton);
        continuebutton.setOnClickListener(this);

        //age
        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        ageSpinner.setAdapter(spinnerArrayAdapter);

        //subscriptionFee
        List fee = new ArrayList<Integer>();
        for (int i = 300; i <= 3000; i+=50) {
            fee.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter1 = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, fee);
        spinnerArrayAdapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        subscriptionFee.setAdapter(spinnerArrayAdapter1);

        return Root;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.continuebutton:
                registerTrainee();
                break;
            case R.id.etDate:
                {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
                break;

        // Gender Selection
            case R.id.male:
                gender="male";
                break;
            case R.id.female:
                gender="female";
                break;
            case R.id.other:
                gender="other";
                break;

        //Weight Unit Pref
            case R.id.inKg:
                WeightUnitPref ="inKg";
                break;
            case R.id.inLbs:
                WeightUnitPref ="inLbs";
                break;

        //Height Unit Pref
            case R.id.inInches:
                HeightUnitPref ="inInches";
                break;
            case R.id.inMeter:
                HeightUnitPref ="inMeter";
                break;

        //Occupation Selection
            case R.id.student:
                occupation="student";
                break;
            case R.id.officeJob:
                occupation="officeJob";
                break;
            case R.id.travellingJob:
                occupation="travellingJob";
                break;
            case R.id.physicalJob:
                occupation="physicalJob";
                break;
            case R.id.labourJob:
                occupation="labourJob";
                break;

        //Weight Pref
            case R.id.gain:
                WeightPref="gain";
                break;
            case R.id.loss:
                WeightPref="loss";
                break;
            case R.id.maintain:
                WeightPref="maintain";
                break;

        //Muscles Pref
            case R.id.musselsGain:
                MusclesPref="gain";
                break;
            case R.id.musselsMaintain:
                MusclesPref="maintain";
                break;

        // Food Pref
            case R.id.vegetarian:
                FoofPref="vegetarian";
                break;
            case R.id.nonvegetarian:
                FoofPref="nonvegetarian";
                break;

        }

    }

    private void AddTrainee(){

        if (isValidInput()){
            registerTrainee();
        }else{}
    }

    public Trainee getTraineeData(){
        Trainee trainee = new Trainee();

        String age = ageSpinner.getSelectedItem().toString();
        String Weight = weight.getText().toString();
        String Height = height.getText().toString();

       // String fee = subscriptionFee.getSelectedItem().toString();

        trainee.setName(name.getText().toString());
        trainee.setMobile(mobile.getText().toString());
        trainee.setEmail(email.getText().toString());
        trainee.setAlternateMobile(alternateMobile.getText().toString());
        trainee.setPassword(mobile.getText().toString());
        trainee.setGender(gender);
        trainee.setAge(Float.parseFloat(age));
        trainee.setWeight(Float.parseFloat(Weight));
        trainee.setHeight(Float.parseFloat(Height));

        // todo get subscription plan
        String start=DateFormat.format("dd/MM/yyyy hh:mm:ss", System.currentTimeMillis()).toString();
        String expiry=DateFormat.format("dd/MM/yyyy hh:mm:ss", System.currentTimeMillis() + 86400000 * 28).toString();
        trainee.setMySubscriptionPlan(new SubscriptionPlan("Name Test Plan","About Test Plan",199,28,start,expiry));

        trainee.setOccupation(occupation);
        trainee.setWeightPref(WeightPref);
        trainee.setMusclePref(MusclesPref);
        trainee.setFoodPref(FoofPref);
        trainee.setTrainerUID(sp.getString("User_UID", Cself.getUid()));
        return trainee;
    }

    public boolean isValidInput(){
        boolean valid=true;
        if(name.getText().toString().isEmpty()){
            name.setError("Your can't left name field empty");
            name.requestFocus();
            valid=false;
        }
        else if( email.getText().toString().isEmpty()){
            email.setError("You can't left email field empty!!");
            email.requestFocus();
            valid=false;
        }
        else if(mobile.getText().toString().isEmpty()){
            mobile.setError("Your can't left mobile field empty");
            mobile.requestFocus();
            valid=false;
        }
        else if (!name.getText().toString().matches("^[A-Z a-z]+$")){
            name.setError("Enter Character's and space only !!");
            name.requestFocus();
            valid=false;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            email.setError("Please Fill Correct email address!!");
            email.requestFocus();
            valid=false;
        }
        else if(!mobile.getText().toString().matches("^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$")){
            mobile.setError("Wrong mobile number formatting");
            mobile.requestFocus();
            valid=false;
        }
        else if(!alternateMobile.getText().toString().matches("^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$")){
            alternateMobile.setError("Wrong mobile number formatting");
            alternateMobile.requestFocus();
            valid=false;
        }

        return valid;
    }

    public void setAllDisable(){
        name.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        other.setEnabled(false);
        ageSpinner.setEnabled(false);
        mobile.setEnabled(false);
        email.setEnabled(false);
        weight.setEnabled(false);
        inKg.setEnabled(false);
        inLbs.setEnabled(false);
        height.setEnabled(false);
        inInches.setEnabled(false);
        inMeter.setEnabled(false);
        student.setEnabled(false);
        officeJob.setEnabled(false);
        travellingJob.setEnabled(false);
        physicalJob.setEnabled(false);
        labourJob.setEnabled(false);
        gain.setEnabled(false);
        loss.setEnabled(false);
        maintain.setEnabled(false);
        musselsGain.setEnabled(false);
        musselsMaintain.setEnabled(false);
        vegetarian.setEnabled(false);
        nonVegetarian.setEnabled(false);
        subscriptionFee.setEnabled(false);
        etDate.setEnabled(false);
    }

    public void setAllEnable(){
        name.setEnabled(true);
        male.setEnabled(true);
        female.setEnabled(true);
        other.setEnabled(true);
        ageSpinner.setEnabled(true);
        mobile.setEnabled(true);
        email.setEnabled(true);
        weight.setEnabled(true);
        inKg.setEnabled(true);
        inLbs.setEnabled(true);
        height.setEnabled(true);
        inInches.setEnabled(true);
        inMeter.setEnabled(true);
        student.setEnabled(true);
        officeJob.setEnabled(true);
        travellingJob.setEnabled(true);
        physicalJob.setEnabled(true);
        labourJob.setEnabled(true);
        gain.setEnabled(true);
        loss.setEnabled(true);
        maintain.setEnabled(true);
        musselsGain.setEnabled(true);
        musselsMaintain.setEnabled(true);
        vegetarian.setEnabled(true);
        nonVegetarian.setEnabled(true);
        subscriptionFee.setEnabled(true);
        etDate.setEnabled(true);
    }

    public void registerTrainee(){
         Log.d("Adding Trainee Failed", "Trainee Registration ");
         final Trainee trainee =getTraineeData();
         final FirebaseAuth mAuth=  FirebaseAuth.getInstance();
         setAllDisable();
         showProgressingView();
         Log.d("TAG", "registerTrainee Before Trainer Uid : "+ FirebaseAuth.getInstance().getCurrentUser().getUid());
         Log.d("TAG", "registerTrainee: "+trainee.getEmail()+ trainee.getPassword());
            mAuth.createUserWithEmailAndPassword(trainee.getEmail(), trainee.getPassword())
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "Trainee Registration Success!!");
                                Toast.makeText(getContext(), "Trainee Registration Success!!", Toast.LENGTH_SHORT).show();

                                //Saving Trainee Data
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String Key =user.getUid();
                                DataBaseHandler db =new DataBaseHandler(requireContext());
                                try{
                                    db.RegisterTrainee(trainee,Key);
                                    sendVerificationEmail();
                                    FirebaseAuth.getInstance().signOut();
                                    ReLogin();
                                }catch (Exception e){
                                    Log.d("Adding Trainee Error", "onComplete: "+e);
                                }
                                // Todo updateUI


                                hideProgressingView();
                                setAllEnable();
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.d("Adding Trainee Failed", "Trainee Registration failed", task.getException());
                                Toast.makeText(getContext(), "Trainee Registration Failed!!", Toast.LENGTH_SHORT).show();
                                hideProgressingView();
                                setAllEnable();
                            }
                        }
                    });



    }

    protected boolean isProgressShowing = false;
    ViewGroup progressView;
    public void showProgressingView() {

        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.mprogressbar, null);
            View v = requireActivity().findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        View v = requireActivity().findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Log.d("TAG", "Email Send To trainee Success!!!" );
                            Toast.makeText(requireContext(), "Email Send Success", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Log.d("TAG", "Email Send Failed!!!" + task.isSuccessful());
                            Toast.makeText(requireContext(), "Email Send Failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    private void ReLogin(){

        FirebaseAuth mAuth=  FirebaseAuth.getInstance();
        String Email =sp.getString("User_Email","prinssingh7448@gmail.com");
        String Password =sp.getString("User_Password","1rtyrty2");
        String UID =sp.getString("User_UID","UIDDDDDDDD");

        Log.d("Re-login", "Sp Email"+Email +"Sp Pwd "+Password+"  UID  "+UID);

        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Log.d("Re-login", "onComplete: "+user.getUid());
                            Toast.makeText(requireContext(), "TrainerAfter Re-login"+user.getUid(), Toast.LENGTH_LONG).show();

                        } else {

                            try{
                                throw Objects.requireNonNull(task.getException());
                            }catch (FirebaseAuthInvalidUserException e){
                                //Email Not Found
                                Toast.makeText(requireContext(), "Email not found", Toast.LENGTH_SHORT).show();

                            }catch (FirebaseAuthInvalidCredentialsException e){


                            }catch (Exception e) {
                                Toast.makeText(requireContext(),"Exception"+e,Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                });


    }


}