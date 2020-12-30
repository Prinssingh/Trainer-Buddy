package gym.exercise.workout.trainerbuddy.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.R;

public class ForgetPage extends Fragment implements View.OnClickListener{
    EditText Email;
    Button ResetPassword;
    TextView Login;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    ImportantFunctions impFun;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;




    public static ForgetPage newInstance() {
        return new ForgetPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root =inflater.inflate(R.layout.forget_page, container, false);
        impFun =new ImportantFunctions(getContext());
        Login= Root.findViewById(R.id.Login);
        Login.setOnClickListener(this);

        ResetPassword= Root.findViewById(R.id.resetPassword);
        ResetPassword.setOnClickListener(this);
        Email =Root.findViewById(R.id.forgetEmail);

        return Root ;
    }




    public void  ChangeFragment(Fragment fragment){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.LoginContainer,  fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.Login:
                ChangeFragment(LoginPage.newInstance());
                break;
            case R.id.resetPassword:
                sendPwdResetEmail(Email.getText().toString());
                break;


        }

    }

    public boolean isValidInput(){
        boolean valid=true;
        if( Email.getText().toString().isEmpty()){
            Email.setError("Empty!!");
            Email.requestFocus();
            valid=false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
            Email.setError(" Email malformed!!");
            Email.requestFocus();
            valid=false;
        }

        return valid;
    }
    
    private void sendPwdResetEmail(String email){
        if( isValidInput()){
            setDisableAll();
            showProgressingView();
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                hideProgressingView();
                               setEnabledAll();
                                EmailSendSuccess();
                            }
                            else{
                                hideProgressingView();
                                setEnabledAll();
                                EmailSendFailed();
                            }

                        }
                    });

        }
        
    }

    private void setEnabledAll(){
        Login.setEnabled(true);
        ResetPassword.setEnabled(true);
        Email.setEnabled(true);
    }

    private void setDisableAll(){
        Login.setEnabled(false);
        ResetPassword.setEnabled(false);
        Email.setEnabled(false);
    }

    public void showProgressingView() {

        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.mprogressbar, null);
            View v = requireActivity().findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        View v = requireActivity().findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

    public void EmailSendSuccess(){
        AlertDialog.Builder AlertSuccess;
        AlertSuccess=new AlertDialog.Builder(requireContext(),android.R.style.Theme_Translucent_NoTitleBar);

        View SuccessView= getLayoutInflater().inflate(R.layout.email_send_success,null);
        AlertSuccess.setView(SuccessView);
        Button login =SuccessView.findViewById(R.id.Login);

        final AlertDialog Success=AlertSuccess.create();
        Success.show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Success.dismiss();
                ChangeFragment(LoginPage.newInstance());
            }
        });
    }

    public void EmailSendFailed(){
        AlertDialog.Builder AlertSuccess;
        AlertSuccess=new AlertDialog.Builder(requireContext(),android.R.style.Theme_Translucent_NoTitleBar);

        View SuccessView= getLayoutInflater().inflate(R.layout.email_send_failed,null);
        AlertSuccess.setView(SuccessView);
        Button login =SuccessView.findViewById(R.id.Login);

        final AlertDialog Success=AlertSuccess.create();
        Success.show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Success.dismiss();
                ChangeFragment(LoginPage.newInstance());
            }
        });
    }
}