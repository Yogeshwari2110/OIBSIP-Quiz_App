package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class quiz extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView, answer;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn, checkAns;

    int score = 0;
    int totalQuestion = QuestionAnswers.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        answer = findViewById(R.id.ans);
        checkAns = findViewById(R.id.check);
        submitBtn = findViewById(R.id.next);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        checkAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAnswer == QuestionAnswers.correctAnswers[currentQuestionIndex]) {
                    answer.setText("Correct answer!");


                } else {
                    answer.setText("Wrong Answer!\nCorrect Answer is " + QuestionAnswers.correctAnswers[currentQuestionIndex]);
                }


            }
        });

        totalQuestionsTextView.setText("Total questions : " + totalQuestion);
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {


        ansA.setBackgroundColor(Color.rgb(38, 39, 60));
        ansB.setBackgroundColor(Color.rgb(38, 39, 60));
        ansC.setBackgroundColor(Color.rgb(38, 39, 60));
        ansD.setBackgroundColor(Color.rgb(38, 39, 60));


        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.next) {
            answer.setText("");
            if (selectedAnswer.equals(QuestionAnswers.correctAnswers[currentQuestionIndex])) {

                score++;
            }

            currentQuestionIndex++;
            loadNewQuestion();


        } else {
            //choices button clicked

            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.DKGRAY);

        }


    }

    void loadNewQuestion() {

        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswers.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswers.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswers.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswers.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswers.choices[currentQuestionIndex][3]);

    }

    void finishQuiz() {
        String passStatus = "";
        if (score > totalQuestion * 0.60) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();


    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}