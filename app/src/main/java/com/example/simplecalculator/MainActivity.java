// UI Design by Ambrose Kipkemoi - Group Assignment
package com.example.simplecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;
=======
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Display views
    private TextView tvResult;
    private TextView tvExpression;
    private ImageButton btnThemeToggle;

    // Calculator state
    private String currentInput = "";
    private String pendingOperator = "";
    private double firstNumber = 0;
    private boolean justCalculated = false;
    private boolean operatorPressed = false;

    private final DecimalFormat decimalFormat = new DecimalFormat("0.##########");

    // Theme preferences (kept from team)
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_IS_NIGHT_MODE = "is_night_mode";
>>>>>>> main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load theme preference (team's code - kept exactly)
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean(KEY_IS_NIGHT_MODE, true);

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
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
=======
        // Initialize display views
        tvResult = findViewById(R.id.tvResult);
        tvExpression = findViewById(R.id.tvExpression);
        btnThemeToggle = findViewById(R.id.btnThemeToggle);

        // Theme toggle (team's code - kept exactly)
        updateThemeIcon(isNightMode);
        btnThemeToggle.setOnClickListener(v -> toggleTheme());

        // ── Number buttons ──────────────────────────
        setNumberButton(R.id.btn0, "0");
        setNumberButton(R.id.btn1, "1");
        setNumberButton(R.id.btn2, "2");
        setNumberButton(R.id.btn3, "3");
        setNumberButton(R.id.btn4, "4");
        setNumberButton(R.id.btn5, "5");
        setNumberButton(R.id.btn6, "6");
        setNumberButton(R.id.btn7, "7");
        setNumberButton(R.id.btn8, "8");
        setNumberButton(R.id.btn9, "9");

        // ── Decimal point ───────────────────────────
        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if (justCalculated) { currentInput = "0"; justCalculated = false; }
            if (currentInput.isEmpty()) currentInput = "0";
            if (!currentInput.contains(".")) {
                currentInput += ".";
                tvResult.setText(currentInput);
            }
        });

        // ── AC - clear everything ───────────────────
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = "";
            pendingOperator = "";
            firstNumber = 0;
            justCalculated = false;
            operatorPressed = false;
            tvResult.setText(getString(R.string.result_placeholder));
            tvExpression.setText("");
        });

        // ── +/- toggle sign ─────────────────────────
        findViewById(R.id.btnPlusMinus).setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
                tvResult.setText(currentInput);
            }
        });

        // ── % percent ───────────────────────────────
        findViewById(R.id.btnPercent).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                double val = Double.parseDouble(currentInput) / 100;
                currentInput = decimalFormat.format(val);
                tvResult.setText(currentInput);
            }
        });

        // ── Operator buttons ────────────────────────
        setOperatorButton(R.id.btnAdd, "+");
        setOperatorButton(R.id.btnSubtract, "−");
        setOperatorButton(R.id.btnMultiply, "×");
        setOperatorButton(R.id.btnDivide, "÷");

        // ── Equals ──────────────────────────────────
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            if (!pendingOperator.isEmpty() && !currentInput.isEmpty()) {
                double secondNumber = Double.parseDouble(currentInput);
                double result = 0;
                boolean error = false;

                switch (pendingOperator) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "−": result = firstNumber - secondNumber; break;
                    case "×": result = firstNumber * secondNumber; break;
                    case "÷":
                        if (secondNumber == 0) {
                            tvResult.setText(getString(R.string.error_text));
                            Toast.makeText(this, getString(R.string.msg_divide_by_zero), Toast.LENGTH_SHORT).show();
                            tvExpression.setText("");
                            currentInput = "";
                            pendingOperator = "";
                            error = true;
                        } else {
                            result = firstNumber / secondNumber;
                        }
                        break;
                }

                if (!error) {
                    String resultStr = decimalFormat.format(result);
                    tvExpression.setText(decimalFormat.format(firstNumber) + " " + pendingOperator + " " + decimalFormat.format(secondNumber) + " =");
                    tvResult.setText(resultStr);
                    currentInput = resultStr;
                    pendingOperator = "";
                    justCalculated = true;
                    operatorPressed = false;
                }
            }
        });
    }

    // ── Helpers ──────────────────────────────────────

    private void setNumberButton(int id, String digit) {
        findViewById(id).setOnClickListener(v -> {
            if (justCalculated) { currentInput = ""; justCalculated = false; }
            if (digit.equals("0") && currentInput.equals("0")) return;
            if (currentInput.equals("0") && !digit.equals(".")) currentInput = "";
            currentInput += digit;
            tvResult.setText(currentInput);
            operatorPressed = false;
        });
    }

    private void setOperatorButton(int id, String op) {
        findViewById(id).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                if (!pendingOperator.isEmpty() && !operatorPressed) {
                    // Chain calculation
                    double secondNumber = Double.parseDouble(currentInput);
                    switch (pendingOperator) {
                        case "+": firstNumber = firstNumber + secondNumber; break;
                        case "−": firstNumber = firstNumber - secondNumber; break;
                        case "×": firstNumber = firstNumber * secondNumber; break;
                        case "÷":
                            if (secondNumber != 0) firstNumber = firstNumber / secondNumber;
                            break;
                    }
                    tvResult.setText(decimalFormat.format(firstNumber));
                } else {
                    firstNumber = Double.parseDouble(currentInput);
                }
                pendingOperator = op;
                tvExpression.setText(decimalFormat.format(firstNumber) + " " + op);
                currentInput = "";
                justCalculated = false;
                operatorPressed = true;
            }
        });
    }

    // ── Theme methods (team's code - kept exactly) ──

    private void toggleTheme() {
        boolean isNightMode = sharedPreferences.getBoolean(KEY_IS_NIGHT_MODE, true);
        boolean newMode = !isNightMode;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_NIGHT_MODE, newMode);
        editor.apply();
        if (newMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    private void updateThemeIcon(boolean isNightMode) {
        if (isNightMode) {
            btnThemeToggle.setImageResource(R.drawable.ic_theme_light);
        } else {
            btnThemeToggle.setImageResource(R.drawable.ic_theme_dark);
        }
    }

    // ── Save/restore state ──────────────────────────

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", tvResult.getText().toString());
        outState.putString("expression", tvExpression.getText().toString());
        outState.putString("currentInput", currentInput);
        outState.putString("pendingOperator", pendingOperator);
        outState.putDouble("firstNumber", firstNumber);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvResult.setText(savedInstanceState.getString("result"));
        tvExpression.setText(savedInstanceState.getString("expression"));
        currentInput = savedInstanceState.getString("currentInput", "");
        pendingOperator = savedInstanceState.getString("pendingOperator", "");
        firstNumber = savedInstanceState.getDouble("firstNumber", 0);
>>>>>>> main
    }
}
