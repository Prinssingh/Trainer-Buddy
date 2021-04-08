package com.example.sumoe_commerce;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {

    private EditText registeredEmail;
    private Button resetPasswordButon;
    private TextView goBack;

    private FrameLayout parentFrameLayout;

    private FirebaseAuth firebaseAuth;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view =  inflater.inflate(R.layout.fragment_reset_password, container, false);

        registeredEmail = view.findViewById(R.id.forget_password_email);
        resetPasswordButon = view.findViewById(R.id.reset_password_btn);
        goBack = view.findViewById(R.id.forget_password_go_back);

        firebaseAuth = FirebaseAuth.getInstance();

        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);

        emailIconContainer = view.findViewById(R.id.forget_password_email_icon_container);
        emailIcon = view.findViewById(R.id.forget_password_email_icon);
        emailIconText = view.findViewById(R.id.forget_password_email_icon_text);
        progressBar = view.findViewById(R.id.forget_password_progress_bar);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetPasswordButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetPasswordButon.setEnabled(false);
                resetPasswordButon.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Email send successfully",Toast.LENGTH_SHORT).show();

                            ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailIcon.getWidth()/2,emailIcon.getHeight()/2);
                            scaleAnimation.setDuration(100);
                            scaleAnimation.setInterpolator(new AccelerateInterpolator());
                            scaleAnimation.setRepeatMode(Animation.REVERSE);
                            scaleAnimation.setRepeatCount(1);

                            scaleAnimation.setAnimationListener(new Animation.AnimationListener(){
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    emailIconText.setText("Recovery email sent successfully ! check your inbox");
                                    emailIconText.setTextColor(getResources().getColor(R.color.success_green));

                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIcon.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                    emailIcon.setImageResource(R.drawable.email_after_send);
                                }
                            });

                            emailIcon.startAnimation(scaleAnimation);

                        }
                        else{
                            String error = task.getException().getMessage();

                            emailIconText.setText(error);
                            emailIconText.setTextColor(getResources().getColor(R.color.red_color));
                            TransitionManager.beginDelayedTransition(emailIconContainer);
                            emailIconText.setVisibility(View.VISIBLE);
                            resetPasswordButon.setEnabled(true);
                            resetPasswordButon.setTextColor(Color.rgb(255,255,255));
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        registeredEmail.addTextChangedListener(new TextWatcher() {
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

    }

    private void checkInputs() {
        if(TextUtils.isEmpty(registeredEmail.getText())){
            resetPasswordButon.setEnabled(false);
            resetPasswordButon.setTextColor(Color.argb(50,255,255,255));
        }
        else{
            resetPasswordButon.setEnabled(true);
            resetPasswordButon.setTextColor(Color.rgb(255,255,255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}