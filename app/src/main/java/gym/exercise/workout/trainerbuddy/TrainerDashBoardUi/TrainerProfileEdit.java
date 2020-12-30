package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gym.exercise.workout.trainerbuddy.Entities.Trainer;
import gym.exercise.workout.trainerbuddy.R;


public class TrainerProfileEdit extends Fragment implements View.OnClickListener{

    private static Trainer self;
    Button update;
    CardView EditProfileImage;
    ImageView PHOTO;
    EditText nameEdit, mobileEdit, alternateEdit, addressEdit, gymNameEdit, ageEdit, weightEdit, heightEdit, aboutEdit, dobEdit, foodEdit;

    public static TrainerProfileEdit newInstance(Trainer trainer) {
        self =trainer;
        return  new TrainerProfileEdit();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View Root= inflater.inflate(R.layout.trainer_profile_edit, container, false);

        //Photo Section
        EditProfileImage=Root.findViewById(R.id.TrainerProfileImage);
        EditProfileImage.setOnClickListener(this);
        PHOTO=Root.findViewById(R.id.TrainerProfilePIC);


        // Entities Section
        nameEdit = Root.findViewById(R.id.nameEdit);
        mobileEdit = Root.findViewById(R.id.mobileEdit);
        alternateEdit = Root.findViewById(R.id.alternateEdit);
        addressEdit = Root.findViewById(R.id.addressEdit);
        gymNameEdit = Root.findViewById(R.id.gymNameEdit);
        ageEdit = Root.findViewById(R.id.ageEdit);
        weightEdit =Root.findViewById(R.id.weightEdit);
        heightEdit = Root.findViewById(R.id.heightEdit);
        aboutEdit = Root.findViewById(R.id.aboutEdit);
        dobEdit =Root.findViewById(R.id.dobEdit);
        foodEdit = Root.findViewById(R.id.foodEdit);

        update = Root.findViewById(R.id.update);


        nameEdit.setText(self.getName());
        mobileEdit.setText(self.getMobile());
        alternateEdit.setText(self.getAlternateMobile());
        addressEdit.setText(self.getGymAddress());
        gymNameEdit.setText(self.getGymName());
        ageEdit.setText(self.getAge());
        weightEdit.setText(self.getWeight());
        heightEdit.setText( self.getHeight());
        aboutEdit.setText(self.getAbout());
        //dobEdit.setText(self.getName());
        //foodEdit.setText(self.getName());

        return Root;
    }


    public void  ChangeFragment(){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.TrainerProfileContainer,  TrainerProfileShow.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.TrainerProfileImage:
                break;
            case R.id.update:
                break;
        }
    }

    public Trainer getInputdata(){
        if(!nameEdit.getText().toString().isEmpty())
                self.setName(nameEdit.getText().toString());

        if(!nameEdit.getText().toString().isEmpty())
            self.setName(nameEdit.getText().toString());




        return self;
    }
}