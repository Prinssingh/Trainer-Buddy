package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import gym.exercise.workout.trainerbuddy.Entities.Trainer;
import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.editTrainerProfile;

public class Profile extends Fragment implements Serializable {

    CardView editImage;

    TextView name, mobile, alternate, adddress, gymName, age, weight, height, about, dob, food;


    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root = inflater.inflate(R.layout.trainer_profile, container, false);

        name = Root.findViewById(R.id.trainerName);
        mobile = Root.findViewById(R.id.trainerMobile);
        alternate = Root.findViewById(R.id.trainerAlternate);
        adddress = Root.findViewById(R.id.trainerAddress);
        gymName = Root.findViewById(R.id.trainerGymName);
        age = Root.findViewById(R.id.trainerAge);
        weight = Root.findViewById(R.id.trainerWeight);
        height = Root.findViewById(R.id.trainerHeight);
        about = Root.findViewById(R.id.trainerAbout);
        dob = Root.findViewById(R.id.trainerDOB);
        food = Root.findViewById(R.id.trainerFood);

        editImage = Root.findViewById(R.id.editImage);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast toast = Toast.makeText(getContext(), "text", Toast.LENGTH_SHORT);
//                toast.show();
                sendTrainerDetail();
            }
        });

        return Root;
    }

    public void sendTrainerDetail() {
        Trainer trainer = new Trainer();

        trainer.setName(name.getText().toString());
        //trainer.setAge(Integer.parseInt(String.valueOf(age.getText())));
        trainer.setMobile(mobile.getText().toString());
        trainer.setAlternateMobile(alternate.getText().toString());
        trainer.setGymAddress(adddress.getText().toString());
        trainer.setGymName(gymName.getText().toString());
        //trainer.setHeight(Float.parseFloat(String.valueOf(height.getText())));
       // trainer.setWeight(Float.parseFloat(String.valueOf(weight.getText())));
        trainer.setAbout(about.getText().toString());
        //trainer.setFood(name.getText().toString());

        Intent intent = new Intent(getContext(), editTrainerProfile.class);
        intent.putExtra("object", (Serializable) trainer);
        startActivity(intent);

    }

}