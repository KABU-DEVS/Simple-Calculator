// UI Design by Ambrose Kipkemoi - Group Assignment
package com.example.simplecalculator;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;
    private EditText currentlyFocusedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        tvResult  = findViewById(R.id.tvResult);

        // Prevent system keyboard from appearing but keep cursor functional
        etNumber1.setShowSoftInputOnFocus(false);
        etNumber2.setShowSoftInputOnFocus(false);

        // Track focus
        currentlyFocusedEditText = etNumber1;
        etNumber1.requestFocus();

        etNumber1.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) currentlyFocusedEditText = etNumber1;
        });
        etNumber2.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) currentlyFocusedEditText = etNumber2;
        });

        setupKeyboard();
    }

    private void setupKeyboard() {
        // Numeric keys
        int[] numericKeys = {
            R.id.key0, R.id.key1, R.id.key2, R.id.key3, R.id.key4,
            R.id.key5, R.id.key6, R.id.key7, R.id.key8, R.id.key9, R.id.keyDot
        };

        View.OnClickListener numericClickListener = v -> {
            Button b = (Button) v;
            insertText(b.getText().toString());
        };

        for (int id : numericKeys) {
            findViewById(id).setOnClickListener(numericClickListener);
        }

        // Functional keys
        findViewById(R.id.keyBackspace).setOnClickListener(v -> deleteText());
        findViewById(R.id.keyAC).setOnClickListener(v -> clearFields());

        // Operators
        findViewById(R.id.keyAdd).setOnClickListener(v -> calculate("+"));
        findViewById(R.id.keySubtract).setOnClickListener(v -> calculate("-"));
        findViewById(R.id.keyMultiply).setOnClickListener(v -> calculate("×"));
        findViewById(R.id.keyDivide).setOnClickListener(v -> calculate("÷"));
        
        // Equals / Calculate Total
        findViewById(R.id.keyCalculate).setOnClickListener(v -> calculate("+"));
    }

    private void insertText(String text) {
        if (currentlyFocusedEditText != null) {
            int start = Math.max(currentlyFocusedEditText.getSelectionStart(), 0);
            int end = Math.max(currentlyFocusedEditText.getSelectionEnd(), 0);
            currentlyFocusedEditText.getText().replace(Math.min(start, end), Math.max(start, end), text);
        }
    }

    private void deleteText() {
        if (currentlyFocusedEditText != null) {
            Editable editable = currentlyFocusedEditText.getText();
            int start = currentlyFocusedEditText.getSelectionStart();
            int end = currentlyFocusedEditText.getSelectionEnd();
            if (start == end) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            } else {
                editable.delete(Math.min(start, end), Math.max(start, end));
            }
        }
    }

    private void calculate(String operator) {
        String s1 = etNumber1.getText().toString().trim();
        String s2 = etNumber2.getText().toString().trim();

        if (s1.isEmpty() || s2.isEmpty()) {
            tvResult.setText(getString(R.string.msg_enter_numbers));
            return;
        }

        double num1, num2;
        try {
            num1 = Double.parseDouble(s1);
            num2 = Double.parseDouble(s2);
        } catch (NumberFormatException e) {
            tvResult.setText(getString(R.string.msg_invalid_input));
            return;
        }

        double result;
        switch (operator) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "×": result = num1 * num2; break;
            case "÷":
                if (num2 == 0) {
                    tvResult.setText(getString(R.string.msg_divide_by_zero));
                    return;
                }
                result = num1 / num2;
                break;
            default: return;
        }

        if (result == (long) result) {
            tvResult.setText(String.valueOf((long) result));
        } else {
            tvResult.setText(String.valueOf(result));
        }
    }

    private void clearFields() {
        etNumber1.setText("");
        etNumber2.setText("");
        tvResult.setText(getString(R.string.result_placeholder));
        etNumber1.requestFocus();
    }
}
