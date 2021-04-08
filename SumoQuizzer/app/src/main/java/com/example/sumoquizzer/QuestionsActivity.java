package com.example.sumoquizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView question,noIndicator;
    private FloatingActionButton bookmarkBtn;
    private LinearLayout optionsContainer;
    private Button shareBtn,nextBtn;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;
    private String category;
    private  int setNo;

    private  int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        noIndicator = findViewById(R.id.no_indicator);
        bookmarkBtn = findViewById(R.id.bookmarkBtn);
        optionsContainer = findViewById(R.id.options_container);
        shareBtn = findViewById(R.id.shareBtn);
        nextBtn = findViewById(R.id.nextBtn);

        category = getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo",1);




        list = new ArrayList<>();
        myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getValue(QuestionModel.class));
                }
                if(list.size() > 0){

                    for(int i = 0 ; i < 4 ; i++){
                        optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button)v);
                            }
                        });
                    }

                    playAnim(question,0,list.get(position).getQuestion());

                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextBtn.setEnabled(false);
                            nextBtn.setAlpha(0.7f);
                            position++;
                            enableOption(true);
                            if(position == list.size()){
                                /// score activity
                                return ;
                            }
                            count = 0;
                            playAnim(question,0,list.get(position).getQuestion());
                        }
                    });
                }
                else{
                    finish();
                    Toast.makeText(QuestionsActivity.this,"no question", Toast.LENGTH_SHORT).show();;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionsActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        //playAnim(question,0,list.get(position).getQuestion());



    }

    private void playAnim(View view, int value,final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value == 0 && count<4 ){
                    String option="";
                    if(count == 0 && count < 4){
                        option = list.get(position).getOptionA();
                    }else if(count == 1){
                        option = list.get(position).getOptionB();
                    }else if(count == 2){
                        option = list.get(position).getOptionC();
                    }else if(count == 3){
                        option = list.get(position).getOptionD();
                    }

                    playAnim(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //data change

                if(value == 0){
                    try{
                        ((TextView)view).setText(data);
                        noIndicator.setText(position+1+"/"+list.size());
                    }catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAns())){
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }else{
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button)optionsContainer.findViewWithTag(list.get(position).getCorrectAns());
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4caf50")));
        }
    }

    private void enableOption(boolean enable){
        for(int i = 0 ; i < 4 ; i++){
            optionsContainer.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }
}