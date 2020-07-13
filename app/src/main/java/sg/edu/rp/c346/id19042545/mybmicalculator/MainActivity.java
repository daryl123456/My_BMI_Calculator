package sg.edu.rp.c346.id19042545.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCal, btnReset;
    TextView tvDate, tvBmi,tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etWeight.requestFocus();
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCal = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBmi = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clear();
            }
        });
    }

    protected void clear() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.clear();
        prefEdit.apply();
        tvDate.setText("Last Calculated Date:");
        tvBmi.setText("Last Calculated BMI:");
        tvOutcome.setText("");
    }
    protected void calculate() {
        //Step 1a:Get the user input from the EditeText and store it in a variable
        float getWeight = Float.parseFloat(etWeight.getText().toString());
        float getHeight = Float.parseFloat(etHeight.getText().toString());

        float calbmi =getWeight/(getHeight*getHeight);

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);
        String msg="";
        if (calbmi>=30){
            msg="You are obese";
        }else if (calbmi>=25){
            msg="You are overweight";
        }else if (calbmi>=18.5){
            msg="Your BMI is normal";
        }else{
            msg="You are underweight";
        }
        etWeight.setText("");
        etHeight.setText("");
        tvDate.setText("Last Calculated Date:"+datetime);
        tvBmi.setText("Last Calculated BMI:"+calbmi);
        tvOutcome.setText(msg);


        //step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //step 1c:Obtain an instances of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1d:Add the key-value pair
        prefEdit.putFloat("bmi",calbmi);
        prefEdit.putString("dt",datetime);
        prefEdit.putString("msg",msg);

        //Step 1e: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a:Obtain instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the key "greeting" from the SharedPreferences object
        float bmi = prefs.getFloat("bmi",0);
        String dt = prefs.getString("dt","");
        String message = prefs.getString("msg","");


        //Step 2c:Update the UI element with the value
        tvDate.setText("Last Calculated Date:"+dt);
        tvBmi.setText("Last Calculated BMI:"+bmi);
        tvOutcome.setText(message);

    }
}
