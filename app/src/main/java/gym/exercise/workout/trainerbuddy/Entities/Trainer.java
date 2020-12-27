package gym.exercise.workout.trainerbuddy.Entities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.database.Exclude;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class Trainer  {
    private String Name;
    private String Email;
    private String Mobile;
    private Bitmap Photo;
    private String PhotoPath;
    private String Password;
    private float Weight;
    private float Height;
    private int Age;
    private String Gender;
    private String AlternateMobile;
    private String Certificates;
    private String About;
    private String GymName;
    private String GymAddress;
    private String DeviceID;
    private String Occupation;
    private SubscriptionPlan[] offeringPlan;
    private String FoodPref;
    private String WeightPref;
    private String MusclePref;


    //Constructors
    public Trainer(){}



    //MapperObject
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", this.Name);
        result.put("Email", this.Email);
        result.put("Mobile", this.Mobile);
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
        result.put("OfferingPlans",toMapSubscriptionPlans());
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSubscriptionPlans() {
        HashMap<String, Object> result = new HashMap<>();

        for (SubscriptionPlan plan: offeringPlan ) {
            String key = String.valueOf(plan.getDays()) + "DaysPlanIn" + String.valueOf(plan.getPrize());
            result.put(key,plan.toMap());
        }

        return result;
    }



    /*Getter And Setters*/

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

    public void  setWeight(float Weight){this.Weight=Weight;}
    public float getWeight(){return this.Weight;}

    public void  setHeight(float Height){this.Height=Height;}
    public float getHeight(){return this.Height;}

    public void  setAge(int Age){this.Age=Age;}
    public float getAge(){return this.Age;}

    public void setGender(String Gende){this.Gender=Gende;}
    public String getGender(){return this.Gender;}

    public void setAlternateMobile(String AlternateMobile){this.AlternateMobile=AlternateMobile;}
    public String getAlternateMobile(){return this.AlternateMobile;}

    public void setCertificates(String Certificates){this.Certificates=Certificates;}
    public String getCertificates(){return this.Certificates;}

    public void setAbout(String About){this.About=About;}
    public  String getAbout(){return this.About;}

    public void setGymName(String GymName){this.GymName=GymName;}
    public  String getGymName(){return this.GymName;}

    public void setGymAddress(String GymAddress){this.GymAddress=GymAddress;}
    public  String getGymAddress(){return this.GymAddress;}

    public void setDeviceID (String DeviceID){this.DeviceID=DeviceID;}
    public  String getDeviceID(){return this.DeviceID;}


    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }
    public Bitmap byteToBitmap(byte[] bitmapdata ){
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }
}






