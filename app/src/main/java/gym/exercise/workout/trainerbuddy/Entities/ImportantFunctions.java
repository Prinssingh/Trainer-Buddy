package gym.exercise.workout.trainerbuddy.Entities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import gym.exercise.workout.trainerbuddy.R;

public class ImportantFunctions {
    private final Context context;
    Activity activity;
    ViewGroup progressView;
    private LayoutInflater layoutInflater;
    protected boolean isProgressShowing = false;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public ImportantFunctions(Context context) {
        this.context =context;
        activity=(Activity)context;
        sp=context.getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    @SuppressLint("CommitPrefEdits")
    public ImportantFunctions(Context context, LayoutInflater layoutInflater){
        this.context =context;
        this.layoutInflater=layoutInflater;
        activity=(Activity)context;
        sp=context.getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
        editor=sp.edit();

    }


    public Boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        String status = null;
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
            }
        }else{
            status = "No internet is available";
        }

        return isConnected;
    }

    public void ShowToast(LayoutInflater layoutInflater, String Title, String Message) {
        @SuppressLint("InflateParams")
        View ToastView= layoutInflater.inflate(R.layout.toast_view,null);
        TextView title =ToastView.findViewById(R.id.ToastTitle);
        TextView message = ToastView.findViewById(R.id.ToastMsg);
        title.setText(Title);
        message.setText(Message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(ToastView);
        toast.show();
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @SuppressLint("InflateParams")
    public void showProgressingView() {

        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) layoutInflater.inflate(R.layout.mprogressbar, null);
            View v = activity.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        View v = activity.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

/* Shared Preferences  */
    public SharedPreferences mSharedPref(){
        return sp;
    }
    public SharedPreferences.Editor mEditor(){
        return editor;
    }

    public String getSharedPrefUID(){
        return sp.getString("User_UID",null);
    }
    public void setSharedPrefUID(String UID){
        editor.putString("User_UID",UID).commit();
    }

    public String getSharedPrefEmail(){
        return sp.getString("User_Email",null);
    }
    public void setSharedPrefEmail(String Email){
        editor.putString("User_Email",Email).commit();
    }

    public String getSharedPrefName(){
        return sp.getString("User_Name",null);
    }
    public void setSharedPrefName(String Name){
        editor.putString("User_Name",Name).commit();
    }

    public String getSharedPrefUserType(){
        return sp.getString("User_Type",null);
    }
    public void setSharedPrefUserType(String Type){
        editor.putString("User_Type",Type).commit();
    }

    public String getSharedPrefPassword(){
        return sp.getString("User_Password",null);
    }
    public void setSharedPrefPassword(String Type){
        editor.putString("User_Password",Type).commit();
    }


    public boolean isSharedPrefLogin(){
        return sp.getBoolean("Login",false);
    }
    public void setSharedPrefLogin(boolean Type){
        editor.putBoolean("Login",Type).commit();
    }


    public boolean isSharedPrefRegister(){
        return sp.getBoolean("Register",false);
    }
    public void setSharedPrefRegister(boolean Type){
        editor.putBoolean("Register",Type).commit();
    }




}
