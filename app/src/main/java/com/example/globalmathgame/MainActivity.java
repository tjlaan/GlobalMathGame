package com.example.globalmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvQuestionTitle;
    TextView tvQuestionText;
    TextView tvAnswerResult;
    Button btnOptionOne;
    Button btnOptionTwo;
    Button btnOptionThree;
    Random rand = new Random();
    int score;
    int questionCount;
    int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuestionText = findViewById(R.id.tvQuestionText);
        tvQuestionTitle = findViewById(R.id.tvQuestionTitle);
        tvAnswerResult = findViewById(R.id.tvAnswerResult);
        btnOptionOne = findViewById(R.id.btnOptionOne);
        btnOptionTwo = findViewById(R.id.btnOptionTwo);
        btnOptionThree = findViewById(R.id.btnOptionThree);

        setUpGame();
    }

    public void setUpGame() {
        score = 0;
        questionCount = 0;
        tvAnswerResult.setText("");

        generateQuestion();
    }

    public void setUpGame(View v) {
        setUpGame();
    }

    public void generateQuestion() {
        questionCount++;
        if(questionCount > 5) {
            Intent intent = new Intent(this, ScoreMessageService.class);
            intent.putExtra(ScoreMessageService.EXTRA_MESSAGE, getString(R.string.scoreMessage) + " " + Integer.toString(score) + "/5");
            startService(intent);
            setUpGame();
        } else {
            String newTitle = getString(R.string.questionTitle) + " " + questionCount;
            tvQuestionTitle.setText(newTitle);

            int right = rand.nextInt(101);
            int left = rand.nextInt(101);
            int operation = rand.nextInt(4);

            switch (operation) {
                case 0:
                    answer = left + right;
                    tvQuestionText.setText(left + " + " + right);
                    break;
                case 1:
                    if(left < right) {
                        int temp = right;
                        right = left;
                        left = temp;
                    }
                    answer = left - right;
                    tvQuestionText.setText(left + " - " + right);
                    break;
                case 2:
                    answer = left * right;
                    tvQuestionText.setText(left + " * " + right);
                    break;
                case 3:
                    while (right == 0 || left % right != 0) {
                        right = rand.nextInt(101);
                        left = rand.nextInt(101);
                    }
                    answer = left / right;
                    tvQuestionText.setText(left + " / " + right);
                    break;
                default:
                    //shouldn't get here
            }

            int correctButton = rand.nextInt(3);

            switch (correctButton) {
                case 0:
                    btnOptionOne.setText(Integer.toString(answer));
                    btnOptionTwo.setText(Integer.toString(rand.nextInt(1000)));
                    btnOptionThree.setText(Integer.toString(rand.nextInt(1000)));
                    break;
                case 1:
                    btnOptionOne.setText(Integer.toString(rand.nextInt(1000)));
                    btnOptionTwo.setText(Integer.toString(answer));
                    btnOptionThree.setText(Integer.toString(rand.nextInt(1000)));
                    break;
                case 2:
                    btnOptionOne.setText(Integer.toString(rand.nextInt(1000)));
                    btnOptionTwo.setText(Integer.toString(rand.nextInt(1000)));
                    btnOptionThree.setText(Integer.toString(answer));
                    break;
                default:
                    //shouldn't get here
            }
        }
    }

    public void onClick(View v) {
        if(((Button) v).getText().toString().equals(Integer.toString(answer))) {
            score++;
            tvAnswerResult.setText(getString(R.string.answerCorrect));
        } else {
            tvAnswerResult.setText(getString(R.string.answerWrong));
        }

        generateQuestion();
    }
}
