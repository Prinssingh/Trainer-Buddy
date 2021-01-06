package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;
import gym.exercise.workout.trainerbuddy.R;

public class TrainerProfileShow extends Fragment {
    LocalDataBaseHandler LDB;
    Trainer self;

    ImageView profilePic;
    CardView editProfile;
    TextView name,mail, mobile, alternate, address,gender, gymName, age, weight, height, about, dob, food;

    public static TrainerProfileShow newInstance() {
        return new TrainerProfileShow();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LDB= new LocalDataBaseHandler(requireContext());
        self=LDB.getTrainerLDB();

        View Root= inflater.inflate(R.layout.trainer_profile_show, container, false);
        name = Root.findViewById(R.id.trainerName);
        name.setText(self.getName());
        mail = Root.findViewById(R.id.trainerEmail);
        mail.setText(self.getEmail());
        mobile = Root.findViewById(R.id.trainerMobile);
        mobile.setText(self.getMobile());
        alternate = Root.findViewById(R.id.trainerAlternate);
        alternate.setText(self.getAlternateMobile());
        address = Root.findViewById(R.id.trainerAddress);
        address.setText(self.getGymAddress());
        gymName = Root.findViewById(R.id.trainerGymName);
        gymName.setText(self.getGymName());
        age = Root.findViewById(R.id.trainerAge);
        age.setText(String.valueOf(self.getAge()));
        weight = Root.findViewById(R.id.trainerWeight);
        weight.setText(String.valueOf(self.getWeight()));
        height = Root.findViewById(R.id.trainerHeight);
        height.setText(String.valueOf(self.getHeight()));
        about = Root.findViewById(R.id.trainerAbout);
        about.setText(self.getAbout());
        dob = Root.findViewById(R.id.trainerDOB);
        dob.setText(self.getDOB());
        food = Root.findViewById(R.id.trainerFood);
        food.setText(self.getFoodPref());
        gender = Root.findViewById(R.id.gender);
        gender.setText(self.getGender());

        profilePic = Root.findViewById(R.id.profilePic);

        if (self.getPhoto() !=null){profilePic.setImageBitmap(self.getPhoto());}
        Log.d("TAG", "onCreateView: "+self.getPhoto());
        profilePic.setImageBitmap(self.getPhoto());

        editProfile=Root.findViewById(R.id.EditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HERE", "onClick: EDIT ");
                ChangeFragment();
            }
        });

        return Root;
    }

    public void setAllDisable(){
        name.setEnabled(false);
        age.setEnabled(false);
        mobile.setEnabled(false);
        alternate.setEnabled(false);
        mail.setEnabled(false);
        weight.setEnabled(false);
        address.setEnabled(false);
        gymName.setEnabled(false);
        height.setEnabled(false);
        dob.setEnabled(false);
        about.setEnabled(false);
        gender.setEnabled(false);
        food.setEnabled(false);

    }

    public void setAllEnable(){
        name.setEnabled(true);
        age.setEnabled(true);
        mobile.setEnabled(true);
        alternate.setEnabled(true);
        mail.setEnabled(true);
        weight.setEnabled(true);
        address.setEnabled(true);
        gymName.setEnabled(true);
        height.setEnabled(true);
        dob.setEnabled(true);
        about.setEnabled(true);
        gender.setEnabled(true);
        food.setEnabled(true);
    }

    public void  ChangeFragment(){
        Log.d("HERE1", "onClick: EDITChangeFragment1 ");
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Log.d("HERE2", "onClick: EDITChangeFragment12");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.d("HERE3", "onClick: EDITChangeFragment1 8");
        fragmentTransaction.replace(R.id.TrainerProfileContainer, TrainerProfileEdit.newInstance(self));
        Log.d("HERE41", "onClick: EDITChangeFragment1 sdfsf");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Log.d("HERE41", "onClick: EDITChangeFragment1 sdfsf");
    }

}