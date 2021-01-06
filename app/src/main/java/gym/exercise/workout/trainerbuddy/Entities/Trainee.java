package gym.exercise.workout.trainerbuddy.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Trainee {
    private String UID;
    private String Name;
    private String Email;
    private String Mobile;
    private Bitmap Photo;
    private String Password;
    private String DOB;
    private int Weight;
    private int Height;
    private int Age;
    private String Gender;
    private String AlternateMobile;
    private String GymName;
    private String GymAddress;
    private String DeviceID;
    private String Occupation;
    private SubscriptionPlan MySubscriptionPlan;
    private String FoodPref;
    private String WeightPref;
    private String MusclePref;
    private String TrainerUID;
    private String WeightUnitPref;
    private String HeightUnitPref;



    //Constructors
    public Trainee(){}



    //MapperObject
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", this.Name);
        result.put("Email", this.Email);
        result.put("Mobile", this.Mobile);
        result.put("DOB", this.DOB);
        result.put("Password",this.Password);
        result.put("Weight",this.Weight);
        result.put("Height",this.Height);
        result.put("Age",this.Age);
        result.put("Gender",this.Gender);
        result.put("AlternateMobile", this.AlternateMobile);
        result.put("GymName", this.GymName);
        result.put("GymAddress", this.GymAddress);
        result.put("DeviceID",this.DeviceID);
        result.put("Occupation",this.Occupation);
        result.put("FoodPref",this.FoodPref);
        result.put("WeightPref",this.WeightPref);
        result.put("MusclePref",this.MusclePref);
        result.put("MySubscriptionPlan",MySubscriptionPlan.toMap());
        result.put("TrainerUID",this.TrainerUID);
        result.put("WeightUnitPref",this.WeightUnitPref);
        result.put("HeightUnitPref",this.HeightUnitPref);

        return result;
    }



    /*Getter And Setters*/
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setName(String Name){this.Name=Name;}
    public String getName(){return this.Name;}

    public void setMobile(String Mobile){this.Mobile=Mobile;}
    public String getMobile(){return  this.Mobile;}

    public void setEmail(String Email){this.Email=Email;}
    public String getEmail(){return this.Email;}

    public void  setPassword(String Password){this.Password=Password;}
    public String getPassword(){return  this.Password;}

    public void setPhoto(Bitmap Photo){this.Photo=Photo;}
    public Bitmap getPhoto(){return this.Photo;}

    public void  setWeight(int Weight){this.Weight=Weight;}
    public int getWeight(){return this.Weight;}

    public void  setHeight(int Height){this.Height=Height;}
    public int getHeight(){return this.Height;}

    public void  setAge(int Age){this.Age=Age;}
    public int getAge(){return this.Age;}

    public void setGender(String Gende){this.Gender=Gende;}
    public String getGender(){return this.Gender;}

    public void setAlternateMobile(String AlternateMobile){this.AlternateMobile=AlternateMobile;}
    public String getAlternateMobile(){return this.AlternateMobile;}


    public void setGymName(String GymName){this.GymName=GymName;}
    public  String getGymName(){return this.GymName;}

    public void setGymAddress(String GymAddress){this.GymAddress=GymAddress;}
    public  String getGymAddress(){return this.GymAddress;}

    public void setDeviceID (String DeviceID){this.DeviceID=DeviceID;}
    public  String getDeviceID(){return this.DeviceID;}

    public String getOccupation() { return Occupation; }
    public void setOccupation(String occupation) { Occupation = occupation; }

    public SubscriptionPlan getMySubscriptionPlan() { return MySubscriptionPlan;}

    public void setMySubscriptionPlan(SubscriptionPlan mySubscriptionPlan) { this.MySubscriptionPlan = mySubscriptionPlan; }

    public String getFoodPref() { return FoodPref;}

    public void setFoodPref(String foodPref) { FoodPref = foodPref;}

    public String getWeightPref() { return WeightPref; }

    public void setWeightPref(String weightPref) { WeightPref = weightPref;}

    public String getMusclePref() { return MusclePref; }

    public void setMusclePref(String musclePref) { MusclePref = musclePref;}

    public String getTrainerUID() { return TrainerUID; }

    public void setTrainerUID(String trainerUID) { TrainerUID = trainerUID; }

    public String getDOB() { return DOB; }

    public void setDOB(String DOB) { this.DOB = DOB; }

    public String getWeightUnitPref() { return WeightUnitPref; }

    public void setWeightUnitPref(String weightUnitPref) { WeightUnitPref = weightUnitPref; }

    public String getHeightUnitPref() { return HeightUnitPref; }

    public void setHeightUnitPref(String heightUnitPref) { HeightUnitPref = heightUnitPref; }





    //Extra Important Functions
    public byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }
    public Bitmap byteToBitmap(byte[] bitmapdata ){
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    @NonNull
    @Override
    public String toString() {
        return this.Name;
    }
}






