package gym.exercise.workout.trainerbuddy.Entities;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionPlan {
    private String Name;
    private String About;
    private int Prize;
    private  int Days;
    private Date StartingDate=null;
    private Date ExpiryDate=null;

    public SubscriptionPlan(String name, String about, int prize, int days) {
        Name = name;
        About = about;
        Prize = prize;
        Days = days;
    }

    public SubscriptionPlan(String name, String about, int prize, int days, Date startingDate, Date expiryDate) {
        Name = name;
        About = about;
        Prize = prize;
        Days = days;
        StartingDate = startingDate;
        ExpiryDate = expiryDate;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", this.Name);
        result.put("About",this.About);
        result.put("Prize",this.About);
        result.put("Days",this.About);

        if(StartingDate !=null){
            result.put("StartingDate",this.StartingDate);
            result.put("ExpiryDate",this.ExpiryDate);
        }

        return result;
    }

    public String getName() {
        return Name;
    }

    public SubscriptionPlan setName(String name) {
        Name = name;
        return this;
    }

    public String getAbout() {
        return About;
    }

    public SubscriptionPlan setAbout(String about) {
        About = about;
        return this;
    }

    public int getPrize() {
        return Prize;
    }

    public SubscriptionPlan setPrize(int prize) {
        Prize = prize;
        return this;
    }

    public int getDays() {
        return Days;
    }

    public SubscriptionPlan setDays(int days) {
        Days = days;
        return this;
    }

    public Date getStartingDate() {
        return StartingDate;
    }

    public SubscriptionPlan setStartingDate(Date startingDate) {
        StartingDate = startingDate;
        return this;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public SubscriptionPlan setExpiryDate(Date expiryDate) {
        ExpiryDate = expiryDate;
        return this;
    }


}
