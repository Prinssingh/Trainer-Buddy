package gym.exercise.workout.trainerbuddy.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.database.Exclude;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trainee {

    private String Name;
    private String Email;
    private String Mobile;
    private Bitmap Photo;
    private String Password;
    private float Weight;
    private float Height;
    private float Age;
    private String Gender;
    private String AlternateMobile;
    private Date Subscription;
    private int SubscriptionDay;
    private String Ocupation;
    private String GymName;
    private String GymAddress;
    private String DeviceID;
    private Trainer MyTrainer;

    public Trainee(){}

    public Trainee(String Name, String Email, String Mobile, String Password ,
                   float Weight, float Height, float Age){
        this.Name =Name;
        this.Email =Email;
        this.Mobile =Mobile;
        this.Password =Password;
        this.Weight =Weight;
        this.Height =Height;
        this.Age=Age;
    }


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

    public void  setAge(float Age){this.Age=Age;}
    public float getAge(){return this.Age;}

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

    public void setMyTrainer(Trainer myTrainer)
    {this.MyTrainer=myTrainer;}


    public byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }
    public Bitmap byteToBitmap(byte[] bitmapdata ){
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

}






