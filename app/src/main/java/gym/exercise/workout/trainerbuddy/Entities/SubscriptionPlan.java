package gym.exercise.workout.trainerbuddy.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionPlan {


    private String ID;
    private String Title;
    private String About;
    private int Prize;
    private  int Days;
    private String StartingDate=null;
    private String ExpiryDate=null;

    public SubscriptionPlan() {
    }

    //Constructors
    public SubscriptionPlan(String title, String about, int prize, int days) {
        Title = title;
        About = about;
        Prize = prize;
        Days = days;
    }

    public SubscriptionPlan(String title, String about, int prize, int days, String startingDate, String expiryDate) {
        Title = title;
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
        result.put("Title", this.Title);
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
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }

    public String getGeneratedID(){
        ID= Days +"DaysPlanIn"+ Prize;
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) { Title = title;}

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
