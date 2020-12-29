package gym.exercise.workout.trainerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gym.exercise.workout.trainerbuddy.Entities.Trainer;

public class editTrainerProfile extends AppCompatActivity {

    Button update;

    EditText nameEdit, mobileEdit, alternateEdit, addressEdit, gymNameEdit, ageEdit, weightEdit, heightEdit, aboutEdit, dobEdit, foodEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trainer_profile);
        Trainer trainer = (Trainer) getIntent().getSerializableExtra("Trainer");

        nameEdit = findViewById(R.id.nameEdit);
        mobileEdit = findViewById(R.id.mobileEdit);
        alternateEdit = findViewById(R.id.alternateEdit);
        addressEdit = findViewById(R.id.addressEdit);
        gymNameEdit = findViewById(R.id.gymNameEdit);
        ageEdit = findViewById(R.id.ageEdit);
        weightEdit =findViewById(R.id.weightEdit);
        heightEdit = findViewById(R.id.heightEdit);
        aboutEdit = findViewById(R.id.aboutEdit);
        dobEdit =findViewById(R.id.dobEdit);
        foodEdit = findViewById(R.id.foodEdit);

        update = findViewById(R.id.update);


        nameEdit.setText(trainer.getName());
        mobileEdit.setText(trainer.getMobile());
        alternateEdit.setText(trainer.getAlternateMobile());
        addressEdit.setText(trainer.getGymAddress());
        gymNameEdit.setText(trainer.getGymName());
        //ageEdit.setText((int) trainer.getAge());
        //weightEdit.setText((int) trainer.getWeight());
        //heightEdit.setText((int) trainer.getHeight());
        aboutEdit.setText(trainer.getAbout());
//        dobEdit.setText(trainer.getName());
//        foodEdit.setText(trainer.getName());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "This is a message displayed in a Toast",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}