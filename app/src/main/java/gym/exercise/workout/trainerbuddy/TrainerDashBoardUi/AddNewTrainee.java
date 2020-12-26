package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    }

    public Trainee getData(){
        Trainee trainee = new Trainee();

        String age = ageSpinner.getSelectedItem().toString();

        EditText text = (EditText)Root.findViewById(R.id.weight);
        String weight = text.getText().toString();

        EditText text1 = (EditText)Root.findViewById(R.id.height);
        String height = text.getText().toString();


        //String fee = subscriptionFee.getSelectedItem().toString();

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
}