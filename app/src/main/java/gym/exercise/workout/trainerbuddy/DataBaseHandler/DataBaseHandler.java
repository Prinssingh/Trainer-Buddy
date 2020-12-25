package gym.exercise.workout.trainerbuddy.DataBaseHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;

public class DataBaseHandler {
    FirebaseDatabase database;
    DatabaseReference Root;
    private  Context  context;


    public  DataBaseHandler(Context context){
        database= FirebaseDatabase.getInstance();
        Root = database.getReference();
        this.context =context;

    }

    public void RegisterTrainer(Trainer trainer){

        Map<String, Object> childUpdates = new HashMap<>();
        String key = trainer.getMobile();

        childUpdates.put("/Trainers/" + key, trainer.toMap());
        Root.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.d("DATA","Saved");
            }
        });

    }

    public void RegisterTrainee(Trainee trainee){
        Map<String, Object> childUpdates = new HashMap<>();
        String key = trainee.getMobile();

        childUpdates.put("/Trainees/" + key, trainee.toMap());
        Root.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.d("DATA","Saved");
            }
        });
    }

    public void SaveProfilePhoto(Bitmap bitmap,String UID,String UserType){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(UserType+"/"+UID+".png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Profile Photo saved", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
