package gym.exercise.workout.trainerbuddy.DataBaseHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;

public class DataBaseHandler {
    FirebaseDatabase database;
    DatabaseReference Root;
    private  Context  context;
    private LocalDataBaseHandler LDB;
    private final List<SubscriptionPlan> offeringPlan= new ArrayList<SubscriptionPlan>();


    public  DataBaseHandler(Context context){
        database= FirebaseDatabase.getInstance();
        Root = database.getReference();
        this.context =context;
        LDB=new LocalDataBaseHandler(context);

    }

    //Trainer Operations
    public void RegisterTrainer(Trainer trainer){

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Trainers/" + trainer.getUID(), trainer.toMap());
        Root.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error==null){
                    Log.d("SavingTrainer", "Saving Trainer Done!!");
                    try{ LDB.setTrainerLDB(trainer);}
                    catch (Exception ignored){}
                }
                else {throw error.toException(); }
            }
        });

    }

    public void getTrainer(String UID){
        DatabaseReference trainerRef=database.getReference("/Trainers/").child(UID);
        trainerRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //todo Remove Log
                Log.d("Testing Global DB", "onDataChange: "+snapshot.getKey()+" Values "+snapshot.getValue());
                LDB.setTrainerLDB(Objects.requireNonNull(snapshot.getValue(Trainer.class)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Testing Global DB", "onCancelled: "+error);
            }
        });


    }

    public void getTrainerSubscriptionPlan(){

        DatabaseReference myRef = Root.child("TrainerSubscriptionPlans/");
        myRef.orderByChild("Prize").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ClearTrainer Subscription Plans LDB Table
                LDB.ClearTrainerSubscriptionPlansLDB();

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    SubscriptionPlan plan =singleSnapshot.getValue(SubscriptionPlan.class);
                    assert plan != null;
                    plan.setID(singleSnapshot.getKey());
                    LDB.setTrainerSubscriptionPlansLDB(plan);
                    offeringPlan.add(plan);
                    Log.d("DB CLASS", "singleSnapshot"+singleSnapshot+" value"+singleSnapshot.getValue(SubscriptionPlan.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }

        });

    }

    public void setTrainersOfferingPlans(String UID,SubscriptionPlan mPlan){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Trainers/" +UID +"/OfferingPlans/"+mPlan.getGeneratedID(), mPlan.toMap());
        Root.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error==null){
                   //TODO SAVING LDB
                }
                else {throw error.toException(); }
            }
        });


    }


    //Trainee Operations
    public void RegisterTrainee(Trainee trainee){
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/Trainees/" + trainee.getUID(), trainee.toMap());
        Root.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
               if(error==null){
                   Log.d("Adding Trainee","Success!!");
                   try{
                       LDB.setTraineeLDB(trainee);
                       Log.d("Adding Trainee", "LDB Success: ");
                   }
                   catch (Exception e){
                       Log.d("Adding Trainee", "LDB ERROR: "+e); }

               }
               else throw error.toException();
            }


        });
    }







    // Photo saving and retriving

    public void getProfilePhoto(String UID,String UserType){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(UserType+"/"+UID+".png");
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
