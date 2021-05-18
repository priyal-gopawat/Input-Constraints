package com.streamliners.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.streamliners.inputconstraints.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST = 0;
    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

    }

    /**
     * Sending booleans by intent
     * @param view
     */
    public void takeInput(View view) {
        //Create intent
        Intent intent = new Intent(this, InputActivity.class);

        //Validate input
        boolean upperCase = b.upperCase.isChecked();
        boolean lowerCase = b.lowerCase.isChecked();
        boolean digits = b.digits.isChecked();
        boolean mathOperations = b.mathOperations.isChecked();
        boolean otherSymbols = b.otherSymbols.isChecked();

        if (upperCase == false && lowerCase == false && digits == false && mathOperations == false && otherSymbols == false) {
            Toast.makeText(this, "Please select atLeast one checkBox !", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create bundle to pass
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.UPPER_CASE, upperCase);
        bundle.putBoolean(Constants.LOWER_CASE, lowerCase);
        bundle.putBoolean(Constants.DIGITS, digits);
        bundle.putBoolean(Constants.MATH_OPERATIONS, mathOperations);
        bundle.putBoolean(Constants.OTHER_SYMBOLS, otherSymbols);

        //Pass bundle
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);     //(REQUEST,REQUEST_OK,SEND_BACK)

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            String str = data.getStringExtra(Constants.SEND_BACK);

            //Show data
            b.inputText.setText("Input is : " + str);
            b.inputText.setVisibility(View.VISIBLE);
        }
    }

}