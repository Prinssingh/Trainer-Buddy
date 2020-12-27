package gym.exercise.workout.trainerbuddy.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionPlan {
    private String Name;
    private String About;
    private int Prize;
    private  int Days;
    private String StartingDate=null;
    private String ExpiryDate=null;

    //Constructors
    public SubscriptionPlan(String name, String about, int prize, int days) {
        Name = name;
        About = about;
        Prize = prize;
        Days = days;
    }

    public SubscriptionPlan(String name, String about, int prize, int days, String startingDate, String expiryDate) {
        Name = name;
        About = about;
        Prize = prize;
        Days = days;
        StartingDate = startingDate;
        ExpiryDate = expiryDate;
    }


    //MapperObject
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", this.Name);
        result.put("About",this.About);
        result.put("Prize",this.Prize);
        result.put("Days",this.Days);

        if(StartingDate !=null){
            result.put("StartingDate",this.StartingDate);
            result.put("ExpiryDate",this.ExpiryDate);
        }

        return result;
    }


    /*Getter And Setters*/
    public String getName() {
        return Name;
    }

    public void setName(String name) { Name = name;}

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) { About = about;  }

    public int getPrize() {
        return Prize;
    }

    public void setPrize(int prize) { Prize = prize;}

    public int getDays() {
        return Days;
    }

    public void setDays(int days) { Days = days; }

    public String getStartingDate() { return StartingDate;}

    public void setStartingDate(String startingDate) { StartingDate = startingDate; }

    public String getExpiryDate() { return ExpiryDate; }

    public SubscriptionPlan setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
        return this;
    }


}
