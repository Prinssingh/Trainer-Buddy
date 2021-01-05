package gym.exercise.workout.trainerbuddy.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.DataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.ImportantFunctions;
import gym.exercise.workout.trainerbuddy.Entities.Trainer;
import gym.exercise.workout.trainerbuddy.R;

import static gym.exercise.workout.trainerbuddy.R.id.registerButton;

public class RegisterPage extends Fragment implements View.OnClickListener {
    TextView login;
    Button register;
    EditText Name,Email,Password,Mobile,confirmPass;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    private FirebaseAuth mAuth;
    ImportantFunctions impFun;








    public static RegisterPage newInstance() {
        return new RegisterPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root =inflater.inflate(R.layout.register_page, container, false);


        mAuth =FirebaseAuth.getInstance();
        impFun= new ImportantFunctions(getContext());



        login=Root.findViewById(R.id.Login);
        login.setOnClickListener(this);
        register=Root.findViewById(registerButton);
        register.setOnClickListener(this);
        Name=Root.findViewById(R.id.nameRegister);
        Email=Root.findViewById(R.id.emailRegister);
        Mobile=Root.findViewById(R.id.mobileRegister);
        Password=Root.findViewById(R.id.passwordRegister);
        confirmPass=Root.findViewById(R.id.confirmPasswordRegister);

        return Root;
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
            case R.id.registerButton:
                RegisterToApp();
                break;
        }


    }

    public boolean isValidInput(){
        boolean valid=true;
        if(Name.getText().toString().isEmpty()){
            Name.setError("Your can't left name field empty");
            Name.requestFocus();
            valid=false;
        }
        else if( Email.getText().toString().isEmpty()){
            Email.setError("You can't left email field empty!!");
            Email.requestFocus();
            valid=false;
        }
        else if(Mobile.getText().toString().isEmpty()){
            Mobile.setError("Your can't left Password field empty");
            Mobile.requestFocus();
            valid=false;
        }
        else if(Password.getText().toString().isEmpty()){
            Password.setError("Your can't left Password field empty");
            Password.requestFocus();
            valid=false;
        }
        else if (!Name.getText().toString().matches("^[A-Z a-z]+$")){
            Name.setError("Enter Character's and space only !!");
            Name.requestFocus();
            valid=false;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
            Email.setError("Please Fill Correct email address!!");
            Email.requestFocus();
            valid=false;
        }
        else if(!Mobile.getText().toString().matches("^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$")){
            Mobile.setError("Wrong mobile number formatting");
            Mobile.requestFocus();
            valid=false;
        }
        else if(Password.getText().length()<6){
            Password.setError("Weak PassWord!! Use atleast 6 character password");
            Password.requestFocus();
            valid=false;
        }
        else if(!Password.getText().toString().equals(confirmPass.getText().toString())){
            confirmPass.setError("Password & Confirm password are not matching!!");
            confirmPass.requestFocus();
            valid=false;

        }

        return valid;
    }
    public void setAllDisable(){
        Name.setEnabled(false);
        Email.setEnabled(false);
        Mobile.setEnabled(false);
        Password.setEnabled(false);
        confirmPass.setEnabled(false);
        register.setEnabled(false);
        login.setEnabled(false);
    }

    public void setAllEnable(){
        Name.setEnabled(true);
        Email.setEnabled(true);
        Mobile.setEnabled(true);
        Password.setEnabled(true);
        confirmPass.setEnabled(true);
        register.setEnabled(true);
        login.setEnabled(true);
    }

    public void RegisterToApp(){

        if(isValidInput()){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!= null){

                impFun.setSharedPrefUID(user.getUid());
                impFun.setSharedPrefEmail(user.getEmail());
                impFun.setSharedPrefName(user.getDisplayName());
                impFun.setSharedPrefRegister(true);


                ChangeFragment(LoginPage.newInstance());
            }
            else if(impFun.isConnectedToInternet()){
                showProgressingView();
                setAllDisable();
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();

//                                    //  set an Display name to  User.
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(Name.getText().toString())
                                            .build();

                                    assert user != null;
                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("TAG", "User profile updated. Set Diaplay NAme");
                                                    }
                                                }
                                            });
                                    //Setup Data
                                    impFun.setSharedPrefRegister(true);
                                    impFun.setSharedPrefUID(user.getUid());
                                    impFun.setSharedPrefEmail(email);
                                    impFun.setSharedPrefName(user.getDisplayName());
                                    impFun.setSharedPrefPassword(password);
                                    //todo show message
                                    Toast.makeText(getContext(),"Register Success!!",Toast.LENGTH_LONG).show();

                                    Trainer trainer =new Trainer();
                                    trainer.setName(Name.getText().toString());
                                    trainer.setEmail(email);
                                    trainer.setMobile(Mobile.getText().toString());
                                    trainer.setPassword(password);
                                    trainer.setUID(user.getUid());
                                    DataBaseHandler db =new DataBaseHandler(requireContext());
                                    db.RegisterTrainer(trainer);

                                    //send Verification Email
                                    sendVerificationEmail();

                                    // Hide Pregressbar and set Enable Everything
                                    hideProgressingView();
                                    setAllEnable();

                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    hideProgressingView();
                                    setAllEnable();
                                    Log.w("TAG", "createUserWithEmail:failed", task.getException());
                                    Toast.makeText(getContext(), "Registration failed.", Toast.LENGTH_LONG).show();
                                    try
                                    {
                                        throw Objects.requireNonNull(task.getException());
                                    }

                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        Log.d("TAG", "onComplete: weak_password");
                                        Password.setError("Weak PassWord!! Use atleast 6 character password");
                                        Password.requestFocus();
                                    }

                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        Log.d("TAG", "onComplete: malformed_email");
                                        Email.setError("Please Fill Correct email address!!");
                                        Email.requestFocus();
                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
                                        Log.d("TAG", "onComplete: exist_email");
                                        Toast.makeText(requireContext(), "Email Already Exists!! Try login!", Toast.LENGTH_SHORT).show();
                                         ChangeFragment( LoginPage.newInstance());
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d("TAG", "onComplete: " + e.getMessage());
                                    }
                                }
                            }
                        });
            }
            else{
                impFun.ShowToast(getLayoutInflater(),"No Internet Connection!!",
                        "Please, Connect to  Internet for Register with Math Reasoning!!");
            }
        }
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

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Log.d("TAG", "Email Send Success!!!" + task.isSuccessful());
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(requireContext(), "Email Send LOGIN PLEASE", Toast.LENGTH_SHORT).show();
                            // Goto to login page
                            ChangeFragment(LoginPage.newInstance());
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Log.d("TAG", "Email Send Failed!!!" + task.isSuccessful());

                        }

                    }
                });
    }



}