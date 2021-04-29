package com.example.forkifybhojdaan;

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

import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment {


    public TextView alreadyHaveAnAccount,idNameFront,idNameBack;
    public FrameLayout parentFrameLayout;

    private TextInputEditText userNameSignUp,passwordSignUp,confirmPassword,firstName,lastName,organisationName,
            organizatonAddressLine1,organizatonAddressLine2,organizationZipCode,organizationCity,organizationState,memberPhoneNumber;

    private Button btnSignUp,btnChoseFileFront,btnChoseFileBack;

    private ProgressBar progressBar;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);

        alreadyHaveAnAccount = view.findViewById(R.id.alreadyHaveAnAccount);
        userNameSignUp = view.findViewById(R.id.userNameSignUp);
        passwordSignUp = view.findViewById(R.id.passwordSignUp);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        organisationName = view.findViewById(R.id.organizationName);
        organizatonAddressLine1 = view.findViewById(R.id.organizationAddressLine1);
        organizatonAddressLine2 = view.findViewById(R.id.organizationAddressLine2);
        organizationZipCode = view.findViewById(R.id.organizationZipcode);
        organizationCity = view.findViewById(R.id.organizationCity);
        organizationState = view.findViewById(R.id.organizationState);
        memberPhoneNumber = view.findViewById(R.id.memberPhoneNumber);

        idNameFront= view.findViewById(R.id.idNameFront);
        idNameBack = view.findViewById(R.id.idNameBack);

        btnSignUp = view.findViewById(R.id.btnSignIn);
        btnChoseFileFront = view.findViewById(R.id.memberIdFromt);
        btnChoseFileBack = view.findViewById(R.id.memberIdBack);

        progressBar = view.findViewById(R.id.progressBarSignUp);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });


    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    public void setEnabled(){
        userNameSignUp.setEnabled(true);
        passwordSignUp.setEnabled(true);
        confirmPassword.setEnabled(true);
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        organisationName.setEnabled(true);
        organizatonAddressLine1.setEnabled(true);
        organizatonAddressLine2.setEnabled(true);
        organizationZipCode.setEnabled(true);
        organizationCity.setEnabled(true);
        organizationState.setEnabled(true);
        memberPhoneNumber.setEnabled(true);
        btnChoseFileFront.setEnabled(true);
        btnChoseFileBack.setEnabled(true);
    }

    public void setDisabled(){
        userNameSignUp.setEnabled(false);
        passwordSignUp.setEnabled(false);
        confirmPassword.setEnabled(false);
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        organisationName.setEnabled(false);
        organizatonAddressLine1.setEnabled(false);
        organizatonAddressLine2.setEnabled(false);
        organizationZipCode.setEnabled(false);
        organizationCity.setEnabled(false);
        organizationState.setEnabled(false);
        memberPhoneNumber.setEnabled(false);
        btnChoseFileFront.setEnabled(false);
        btnChoseFileBack.setEnabled(false);
    }


}