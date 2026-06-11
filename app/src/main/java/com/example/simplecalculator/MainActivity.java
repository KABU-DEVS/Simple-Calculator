// UI Design by Ambrose Kipkemoi - Group Assignment
package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        tvResult  = findViewById(R.id.tvResult);

        Button btnAdd      = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide   = findViewById(R.id.btnDivide);
        Button btnClear    = findViewById(R.id.btnClear);

        btnAdd.setOnClickListener(v      -> calculate("+"));
        btnSubtract.setOnClickListener(v -> calculate("-"));
        btnMultiply.setOnClickListener(v -> calculate("×"));
        btnDivide.setOnClickListener(v   -> calculate("÷"));
        btnClear.setOnClickListener(v    -> clearFields());
    }

    private void calculate(String operator) {
        String s1 = etNumber1.getText().toString().trim();
        String s2 = etNumber2.getText().toString().trim();

        if (s1.isEmpty() || s2.isEmpty()) {
            tvResult.setText("Please enter both numbers");
            return;
        }

        double num1, num2;
        try {
            num1 = Double.parseDouble(s1);
            num2 = Double.parseDouble(s2);
        } catch (NumberFormatException e) {
            tvResult.setText("Invalid input");
            return;
        }

        double result;
        switch (operator) {
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            case "×": result = num1 * num2; break;
            case "÷":
                if (num2 == 0) {
                    tvResult.setText("Cannot divide by zero");
                    return;
                }
                result = num1 / num2;
                break;
            default: return;
        }

        // Show whole number if no decimal part
        if (result == (long) result) {
            tvResult.setText(String.valueOf((long) result));
        } else {
            tvResult.setText(String.valueOf(result));
        }
    }

    private void clearFields() {
        etNumber1.setText("");
        etNumber2.setText("");
        tvResult.setText("—");
        etNumber1.requestFocus();
    }
}
