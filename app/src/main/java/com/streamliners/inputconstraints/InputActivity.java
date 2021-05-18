package com.streamliners.inputconstraints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.streamliners.inputconstraints.databinding.ActivityInputBinding;
import com.streamliners.inputconstraints.databinding.ActivityMainBinding;

public class InputActivity extends AppCompatActivity {
    boolean upperCase, lowerCase, digits, mathOperations, otherSymbols;
    private ActivityInputBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupHideErrorForEditText();
    }

    /**
     * Sending input back
     * @param view
     */
    public void sendInput(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            upperCase = bundle.getBoolean(Constants.UPPER_CASE, upperCase);
            lowerCase = bundle.getBoolean(Constants.LOWER_CASE, lowerCase);
            digits = bundle.getBoolean(Constants.DIGITS, digits);
            mathOperations = bundle.getBoolean(Constants.MATH_OPERATIONS, mathOperations);
            otherSymbols = bundle.getBoolean(Constants.OTHER_SYMBOLS, otherSymbols);

        }

        //Creating regrex
        StringBuilder regEx = new StringBuilder();
        regEx.append("^[");
        if (upperCase)
            regEx.append("A-Z");
        if (lowerCase)
            regEx.append("a-z");
        if (digits)
            regEx.append("0-9");
        if (mathOperations)
            regEx.append("+-/*%");
        if (otherSymbols)
            regEx.append("#$&@!");
        regEx.append("]*$");

        String input = b.dataField.getEditText().getText().toString().trim();
        if (!input.matches(regEx.toString())) {
            b.dataField.setError("Invalid Input");
            return;
        }

        //send the data
        Intent intent = new Intent();
        intent.putExtra(Constants.SEND_BACK, b.dataField.getEditText().getText().toString().trim());
        setResult(RESULT_OK, intent);

        //Close the activity
        finish();
    }

    /**
     * text watcher to hide error for data field
     */
    private void setupHideErrorForEditText() {
        TextWatcher myTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        b.dataField.getEditText().addTextChangedListener(myTextWatcher);
    }

    //utility function
    private void hideError() {
        b.dataField.setError(null);
    }

}