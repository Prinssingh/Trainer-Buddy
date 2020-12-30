package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
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

    ImageView editImage;
    CardView editProfile;
    TextView name, mobile, alternate, adddress, gymName, age, weight, height, about, dob, food;

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
        mobile = Root.findViewById(R.id.trainerMobile);
        mobile.setText(self.getMobile());
        alternate = Root.findViewById(R.id.trainerAlternate);
        alternate.setText(self.getAlternateMobile());
        adddress = Root.findViewById(R.id.trainerAddress);
        adddress.setText(self.getGymAddress());
        gymName = Root.findViewById(R.id.trainerGymName);
        gymName.setText(self.getGymName());
        age = Root.findViewById(R.id.trainerAge);
        age.setText(self.getAge());
        weight = Root.findViewById(R.id.trainerWeight);
        weight.setText(self.getWeight());
        height = Root.findViewById(R.id.trainerHeight);
        height.setText(self.getHeight());
        about = Root.findViewById(R.id.trainerAbout);
        //TODO
        dob = Root.findViewById(R.id.trainerDOB);
        food = Root.findViewById(R.id.trainerFood);
        food.setText(self.getFoodPref());

        editImage = Root.findViewById(R.id.editImage);
        if(self.getPhoto()!=null){
            editImage.setImageBitmap(self.getPhoto());
        }

        editProfile=Root.findViewById(R.id.EditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeFragment();
            }
        });

        return Root;
    }

    public void  ChangeFragment(){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.TrainerProfileContainer, TrainerProfileEdit.newInstance(self));
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}