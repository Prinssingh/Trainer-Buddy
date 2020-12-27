package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.R;

public class AddNewTrainee extends Fragment implements View.OnClickListener {

    View Root;
    TextInputEditText name,mobile,alternateMobile,email;
    EditText weight,height;

    RadioGroup genderRadioGroup;

    RadioButton male,female,other,inKg,inLbs,inInches,inMeter,student,officeJob,travellingJob,physicalJob,labourJob;
    RadioButton gain,loss,maintain,musselsGain,musselsMaintain,vegiterian,nonVegiterian;

    TextView etDate;

    DatePickerDialog picker;

    Button continuebutton;
    //extra use
    String gender,occupation,Weight,Mussels,food;

    AppCompatSpinner ageSpinner,subscriptionFee;
    //ImageView ageSpinnerImage;

    public static AddNewTrainee newInstance() {
        return new AddNewTrainee();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        female = Root.findViewById(R.id.female);
        other = Root.findViewById(R.id.other);
        inKg = Root.findViewById(R.id.inKg);
        inLbs = Root.findViewById(R.id.inLbs);
        inInches = Root.findViewById(R.id.inInches);
        inMeter = Root.findViewById(R.id.inMeter);
        student = Root.findViewById(R.id.student);
        officeJob = Root.findViewById(R.id.officeJob);
        travellingJob = Root.findViewById(R.id.travellingJob);
        physicalJob = Root.findViewById(R.id.physicalJob);
        labourJob = Root.findViewById(R.id.labourJob);
        gain = Root.findViewById(R.id.gain);
        loss = Root.findViewById(R.id.loss);
        maintain = Root.findViewById(R.id.maintain);
        musselsGain = Root.findViewById(R.id.musselsGain);
        musselsMaintain = Root.findViewById(R.id.musselsMaintain);
        vegiterian = Root.findViewById(R.id.vegiterian);
        nonVegiterian = Root.findViewById(R.id.nonVegiterian);



        //EditText
        etDate = Root.findViewById(R.id.etDate);


        //Spinner
        ageSpinner = Root.findViewById(R.id.ageSpinner);
        subscriptionFee = Root.findViewById(R.id.subscriptionFee);

        //Button
        continuebutton = Root.findViewById(R.id.continuebutton);
        continuebutton.setOnClickListener(this);


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });




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



        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="male";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="female";
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation="student";
            }
        });
        officeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation="officeJob";
            }
        });
        travellingJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation="travellingJob";
            }
        });
        physicalJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation="physicalJob";
            }
        });
        labourJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation="labourJob";
            }
        });
        gain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight="gain";
            }
        });
        loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight="loss";
            }
        });
        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weight="maintain";
            }
        });
        musselsGain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mussels="gain";
            }
        });
        musselsMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mussels="gain";
            }
        });
        vegiterian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food="vegiterian";
            }
        });
        nonVegiterian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food="nonVegiterian";
            }
        });




        return Root;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.continuebutton:
                break;

        }

    }

    public Trainee getData(){
        Trainee trainee = new Trainee();

        String age = ageSpinner.getSelectedItem().toString();

        EditText text = (EditText)Root.findViewById(R.id.weight);
        String weight = text.getText().toString();

        EditText text1 = (EditText)Root.findViewById(R.id.height);
        String height = text.getText().toString();

        String fee = subscriptionFee.getSelectedItem().toString();

        trainee.setName(name.toString());
        trainee.setMobile(mobile.toString());
        trainee.setEmail(email.toString());
        trainee.setAlternateMobile(alternateMobile.toString());
        trainee.setPassword(mobile.toString());
        trainee.setGender(gender);
        trainee.setAge(Float.parseFloat(age));
        trainee.setWeight(Float.parseFloat(weight));
        trainee.setHeight(Float.parseFloat(height));
        //setter ni bna h iska
        //trainee.setSubscriptionFee(Integer.parseInt(fee));

//        trainee.setOccupation(occupation);
//        trainee.setWeightGain(Weight);
//        trainee.setMussels(Mussels);
//        trainee.setFood(food);
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
        vegiterian.setEnabled(false);
        nonVegiterian.setEnabled(false);
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
        vegiterian.setEnabled(true);
        nonVegiterian.setEnabled(true);
        subscriptionFee.setEnabled(true);
        etDate.setEnabled(true);
    }
}