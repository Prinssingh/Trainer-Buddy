package com.example.forkifybhojdaan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment {

    public TextView dontHaveAnAccount,forgetPassword;
    public FrameLayout parentFrameLayout;

    public ProgressBar progressBar;

    private TextInputEditText userNameSignIn,passwordSignIn;
    private Button btnSignIn;

    public SignInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        dontHaveAnAccount = view.findViewById(R.id.dontHaveAnAccount);

        forgetPassword = view.findViewById(R.id.forgetPassword);
        userNameSignIn = view.findViewById(R.id.userNameSignIn);
        passwordSignIn = view.findViewById(R.id.passwordSignIn);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        progressBar = view.findViewById(R.id.progressBarSignIn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userNameSignIn.getText().toString();
                String password = passwordSignIn.getText().toString();
            }
        });





        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ResetPasswordFragment());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    public void setEnabled(){
        userNameSignIn.setEnabled(true);
        passwordSignIn.setEnabled(true);
    }

    public void setDisabled(){
        userNameSignIn.setEnabled(false);
        passwordSignIn.setEnabled(false);
    }

}