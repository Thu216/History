package vn.hn.huyld.historytest.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import vn.hn.huyld.historytest.R;
import vn.hn.huyld.historytest.adapter.PostsAdapter;
import vn.hn.huyld.historytest.control.JSHelper;
import vn.hn.huyld.historytest.model.Question;

public class QuestionActivity extends AppCompatActivity {

    private static final long COUNT_DOWN_IN_MILLIS = 180000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView tvQuesCount;
    private TextView tvScore;
    private TextView tvCountDown;
    private TextView tvPost;
    private TextView tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton radA;
    private RadioButton radB;
    private RadioButton radC;
    private RadioButton radD;
    private Button btnConfirm;

    private ColorStateList textColorDefaultRB;
    private ColorStateList textColorDefaultCD;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Question> questionList = new ArrayList<>();
    private String post;
    private int questionCount;
    private int questionTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        init();


        textColorDefaultRB = radA.getTextColors();
        textColorDefaultCD = tvCountDown.getTextColors();

        Intent intent = getIntent();
        post = intent.getStringExtra(PostsAdapter.EXTRA_POST);
        tvPost.setText(post);

        if (savedInstanceState == null) {
            JSHelper jsHelper = new JSHelper(this);
            try {
                questionList = jsHelper.getQuestion(post);
                questionTotal = questionList.size();
                Collections.shuffle(questionList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            assert questionList != null;
            questionTotal = questionList.size();
            questionCount = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCount - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();
            } else {
                updateCountdownText();
                showSolution();
            }
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (radA.isChecked() || radB.isChecked() || radC.isChecked() || radD.isChecked()) {
                        checkAnswer();
                    } else {
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }



    private void showNextQuestion() {
        radA.setTextColor(textColorDefaultRB);
        radB.setTextColor(textColorDefaultRB);
        radC.setTextColor(textColorDefaultRB);
        radD.setTextColor(textColorDefaultRB);
        radioGroup.clearCheck();

        if (questionCount < questionTotal) {
            currentQuestion = questionList.get(questionCount);

            tvQuestion.setText(currentQuestion.mQues);
            radA.setText(currentQuestion.mAnsA);
            radB.setText(currentQuestion.mAnsB);
            radC.setText(currentQuestion.mAnsC);
            radD.setText(currentQuestion.mAnsD);

            questionCount++;
            tvQuesCount.setText("Câu: " + questionCount + "/" + questionTotal);
            answered = false;
            btnConfirm.setText("Đồng ý");


            timeLeftInMillis = COUNT_DOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishTesting();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountdownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvCountDown.setText(timeFormatted);
        if (timeLeftInMillis < 15000) {
            tvCountDown.setTextColor(Color.RED);
        } else {
            tvCountDown.setTextColor(textColorDefaultCD);
        }
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton radSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNr = radioGroup.indexOfChild(radSelected) + 1;
        if (answerNr == currentQuestion.mAnsTrue) {
            score++;
            tvScore.setText("Điểm hiện tại: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        radA.setTextColor(Color.RED);
        radB.setTextColor(Color.RED);
        radC.setTextColor(Color.RED);
        radD.setTextColor(Color.RED);

        switch (currentQuestion.mAnsTrue) {
            case 1:
                radA.setTextColor(Color.GREEN);
                break;
            case 2:
                radB.setTextColor(Color.GREEN);
                break;
            case 3:
                radC.setTextColor(Color.GREEN);
                break;
            case 4:
                radD.setTextColor(Color.GREEN);
                break;
        }

        if (questionCount < questionTotal) {
            btnConfirm.setText("Tiếp theo");

        } else {
            btnConfirm.setText("Kết thúc");

        }
    }

    private void finishTesting() {
        finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void init() {
        tvQuesCount = findViewById(R.id.tvQuestionCount);
        tvScore = findViewById(R.id.tvScore);
        tvCountDown = findViewById(R.id.tvCountDown);
        tvPost = findViewById(R.id.tvPosts);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        radA = findViewById(R.id.radA);
        radB = findViewById(R.id.radB);
        radC = findViewById(R.id.radC);
        radD = findViewById(R.id.radD);
        btnConfirm = findViewById(R.id.btnConfirm);
    }


}
