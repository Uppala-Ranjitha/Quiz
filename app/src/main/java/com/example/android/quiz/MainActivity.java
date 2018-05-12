package com.example.android.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup[] qRadioGroups;
    private CheckBox[] qCheckboxes;
    private String[] radioAnswers;
    private String checkboxAnswer;
    private EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.name_field);

        radioAnswers = getResources().getStringArray(R.array.radio_answers);
        checkboxAnswer = getResources().getString(R.string.checkbox_answer);

        qRadioGroups = new RadioGroup[7];
        qRadioGroups[0] = findViewById(R.id.q1_radio_group);
        qRadioGroups[1] = findViewById(R.id.q3_radio_group);
        qRadioGroups[2] = findViewById(R.id.q4_radio_group);
        qRadioGroups[3] = findViewById(R.id.q5_radio_group);
        qRadioGroups[4] = findViewById(R.id.q6_radio_group);
        qRadioGroups[5] = findViewById(R.id.q7_radio_group);
        qRadioGroups[6] = findViewById(R.id.q8_radio_group);

        qCheckboxes = new CheckBox[4];
        qCheckboxes[0] = findViewById(R.id.q2o1_checkbox);
        qCheckboxes[1] = findViewById(R.id.q2o2_checkbox);
        qCheckboxes[2] = findViewById(R.id.q2o3_checkbox);
        qCheckboxes[3] = findViewById(R.id.q2o4_checkbox);
    }

    public void submitButton (View view)    {
        String name = nameField.getText().toString();
        int correctAnswers = checkAnswers();
        int totalQuestions = radioAnswers.length+1;
        displayResult (correctAnswers, totalQuestions, name);
    }

    private int checkAnswers ()    {
        int score = 0;
        int i;
        int noOfRadioAnswers = radioAnswers.length;
        for (i=0; i<noOfRadioAnswers; i++) {
            int radioButtonID = qRadioGroups[i].getCheckedRadioButtonId();  //Gets the checked radiobutton id from the radiogroup
            View radioButton = qRadioGroups[i].findViewById(radioButtonID); //Stores the id into a variable
            int idx = qRadioGroups[i].indexOfChild(radioButton);    //Gets the index of the radiobutton

            RadioButton r = (RadioButton) qRadioGroups[i].getChildAt(idx);  //And finally gets the text of the checked radiobutton

            /*Try catch is used because there's a chance user clicks the submit button without selecting an option
            and we will get an error with the empty value */
            try {
                String selectedText = r.getText().toString();
                if (selectedText.equals(radioAnswers[i])){
                    score++;
                }
            }
            catch (Exception e) {
            }
        }

        int noOfCheckboxes = qCheckboxes.length;
        String answer="";
        // Checks which checkboxes are checked, gets the text from them and compares with the original answer
        for (i=0; i<noOfCheckboxes; i++){
            if (qCheckboxes[i].isChecked()){
                answer += qCheckboxes[i].getText().toString();
            }
            if (i == noOfCheckboxes-1){
                if (answer.equals(checkboxAnswer)){
                    score++;
                }
            }
        }

        return score;
    }

    //Displays the toast message according to the score.
    private void displayResult (int num, int total, String name){
        if (num ==0){
            CharSequence text  = num + "/" + total + "correct\n" + "Don't give up.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
        if (num<=(total/2) && num != 0){
            CharSequence text  = num + "/" + total + " correct\n" + "You can do better " + name + ".";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
        else if (num > (total/2) && num != total){
            CharSequence text  = "Good going " + name + "! Aim higher.\n" + num + "/" + total + " correct.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
        else if (num == total){
            CharSequence text  = "Awesome! You are a genius " + name + ".\n" + num + "/" + total + " correct!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }
}