package com.example.sumoe_commerce;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.sumoe_commerce.RegisterActivity.onResetPasswordFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private TextView dontHaveAnAccount,forgetPassword;
    private FrameLayout parentFrameLayout;

    private EditText email,password;
    private ImageButton closeBtn;
    private Button signIn;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        closeBtn = view.findViewById(R.id.sign_in_close_btn);
        signIn = view.findViewById(R.id.sign_in_btn);
        forgetPassword = view.findViewById(R.id.sign_in_forget_password);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }

        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailPassword();
            }
        });
    }

    private void checkEmailPassword() {

        Drawable customErrorIcon = getResources().getDrawable(R.drawable.error_icon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());


        if(email.getText().toString().matches(emailPattern)){
            if(password.length() >= 8){

                progressBar.setVisibility(View.VISIBLE);
                signIn.setEnabled(false);
                signIn.setTextColor(Color.argb(50,0,0,0));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mainIntent();
                        } else{
                            progressBar.setVisibility(View.INVISIBLE);
                            signIn.setEnabled(true);
                            signIn.setTextColor(Color.rgb(0,0,0));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_LONG);
                        }
                    }
                });

            }else{
                Toast.makeText(getActivity(),"password should be minimun 8 character",Toast.LENGTH_LONG);
                email.setError("password should be minimun 8 character",customErrorIcon);
            }
        }
        else{
            Toast.makeText(getActivity(),"Invalid Email!",Toast.LENGTH_LONG);
            email.setError("Invalid Email!",customErrorIcon);
        }
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signIn.setEnabled(true);
                signIn.setTextColor(Color.rgb(0,0,0));
            }
            else{
                signIn.setEnabled(false);
                signIn.setTextColor(Color.argb(50,0,0,0));
            }
        }
        else{
            signIn.setEnabled(false);
            signIn.setTextColor(Color.argb(50,0,0,0));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void mainIntent(){
                Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
    }
}