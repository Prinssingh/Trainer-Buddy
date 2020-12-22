package gym.exercise.workout.trainerbuddy.Entities;


import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;



import java.util.Date;
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
    private Date Subscription;
    private int SubscriptionDay;
    private String Certificates;
    private String About;
    private String GymName;
    private String GymAddress;
    private String DeviceID;



    public Trainer(){}

    public Trainer(String Name, String Email, String Mobile, String Password ,
                   float Weight, float Height, int Age){
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

    public Date getSubscription() {
        return Subscription;
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
}






