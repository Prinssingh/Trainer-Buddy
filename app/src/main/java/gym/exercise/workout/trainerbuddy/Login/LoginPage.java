package gym.exercise.workout.trainerbuddy.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import gym.exercise.workout.trainerbuddy.DashBoard;
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.R;

public class LoginPage extends Fragment implements View.OnClickListener {

    EditText loginEmail,loginPassword;
    Button loginButton;
    TextView forgetPassword,Register1;
    ImageView Register2;
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    ImportantFunctions impFun;
    SharedPreferences sp;
    SharedPreferences.Editor editor;




    public static LoginPage newInstance() {
        return new LoginPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View Root =inflater.inflate(R.layout.login_page, container, false);
        impFun= new ImportantFunctions(getContext());
        sp=requireContext().getSharedPreferences("TrainerBuddyPref", Context.MODE_PRIVATE);
        editor=sp.edit();

        Register1=Root.findViewById(R.id.Register1);
        Register1.setOnClickListener(this);
        Register2=Root.findViewById(R.id.Register2);
        Register2.setOnClickListener(this);
        forgetPassword =Root.findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(this);
        loginButton= Root.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        loginEmail =Root.findViewById(R.id.loginEmail);
        loginPassword=Root.findViewById(R.id.loginPassword);


        return Root;
    }



    public void  ChangeFragment(Fragment fragment){

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,  fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.Register1 :
            case R.id.Register2:
                ChangeFragment(RegisterPage.newInstance());
                break;

            case R.id.forgetPassword:
                ChangeFragment(ForgetPage.newInstance());
                break;

            case R.id.loginButton:
                LoginToApp();
                break;
        }

    }

    public void LoginToApp(){
        final String EmailId=loginEmail.getText().toString();
        final String password =loginPassword.getText().toString();

        if(isValidInput() && impFun.isConnectedToInternet()){

            mAuth.signInWithEmailAndPassword(EmailId,password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            editor.putString("User_UID",user.getUid()).commit();
                            editor.putString("User_Email",EmailId).commit();
                            editor.putString("User_Password",password).commit();
                            checkIfEmailVerified();

                        } else {

                            try{
                                throw Objects.requireNonNull(task.getException());
                            }catch (FirebaseAuthInvalidUserException e){
                                //Email Not Found
                                Toast.makeText(requireContext(), "Email not found", Toast.LENGTH_SHORT).show();

                            }catch (FirebaseAuthInvalidCredentialsException e){
                                //Password miss Match
                                loginPassword.setError("Password dose't Matched!!");

                            }catch (Exception e) {
                                Toast.makeText(requireContext(),"Exception"+e,Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                });

       }
        else {
            impFun.ShowToast(getLayoutInflater(), "No Internet Connection!!",
                    "Please, Connect to  Internet for Register with Math Reasoning!!");
        }

    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified())
        {
            editor.putBoolean("Login",true).commit();
            Toast.makeText(requireContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
            // Goto DashBoard
            Intent homeintent = new Intent(requireActivity(), DashBoard.class);
            startActivity(homeintent);
            requireActivity().finish();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(requireContext(), "Email Not verified!!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }

    public boolean isValidInput(){
        boolean valid=true;
        if( loginEmail.getText().toString().isEmpty()){
            loginEmail.setError("Empty!!");
            loginEmail.requestFocus();
            valid=false;
        }
        else if(loginPassword.getText().toString().isEmpty()){
            loginPassword.setError("Empty!!");
            loginPassword.requestFocus();
            valid=false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail.getText().toString()).matches()){
            loginEmail.setError(" Email malformed!!");
            loginEmail.requestFocus();
            valid=false;
        }
        else if(loginPassword.getText().length()<6){
            loginPassword.setError("Weak PassWord!!");
            loginPassword.requestFocus();
            valid=false;
        }

        return valid;
    }



}