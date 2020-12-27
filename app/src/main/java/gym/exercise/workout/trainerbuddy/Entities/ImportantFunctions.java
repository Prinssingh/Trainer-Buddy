package gym.exercise.workout.trainerbuddy.Entities;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private  Context context;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;

    public ImportantFunctions(Context context){
        this.context =context;}

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

//
//    public void showProgressingView(LayoutInflater layoutInflater) {
//
//        if (!isProgressShowing) {
//            isProgressShowing = true;
//            progressView = (ViewGroup) layoutInflater.inflate(R.layout.mprogressbar, null);
//            View v = context.getWindow().findViewById(android.R.id.content).getRootView();
//            ViewGroup viewGroup = (ViewGroup) v;
//            viewGroup.addView(progressView);
//        }
//    }
//
//    public void hideProgressingView() {
//        View v = requireActivity().findViewById(android.R.id.content).getRootView();
//        ViewGroup viewGroup = (ViewGroup) v;
//        viewGroup.removeView(progressView);
//        isProgressShowing = false;
//    }
}
