package com.example.simplecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;
    private ImageButton btnThemeToggle;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.##########");
    
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_IS_NIGHT_MODE = "is_night_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load theme preference before calling super.onCreate or setContentView
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean(KEY_IS_NIGHT_MODE, true); // Default to dark mode
        
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etNumber1 = findViewById(R.id.etNumber1);
        etNumber2 = findViewById(R.id.etNumber2);
        tvResult = findViewById(R.id.tvResult);
        btnThemeToggle = findViewById(R.id.btnThemeToggle);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnClear = findViewById(R.id.btnClear);

        // Set toggle icon
        updateThemeIcon(isNightMode);

        // Set click listeners
        btnAdd.setOnClickListener(v -> performCalculation('+'));
        btnSubtract.setOnClickListener(v -> performCalculation('-'));
        btnMultiply.setOnClickListener(v -> performCalculation('*'));
        btnDivide.setOnClickListener(v -> performCalculation('/'));
        btnClear.setOnClickListener(v -> clearFields());
        
        btnThemeToggle.setOnClickListener(v -> toggleTheme());
    }

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
        
        // Recreating the activity is handled by setDefaultNightMode in most cases
        recreate();
    }

    private void updateThemeIcon(boolean isNightMode) {
        if (isNightMode) {
            btnThemeToggle.setImageResource(R.drawable.ic_theme_light); // Show sun when in dark mode
        } else {
            btnThemeToggle.setImageResource(R.drawable.ic_theme_dark); // Show moon when in light mode
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", tvResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvResult.setText(savedInstanceState.getString("result"));
    }

    private void performCalculation(char operator) {
        String input1 = etNumber1.getText().toString().trim();
        String input2 = etNumber2.getText().toString().trim();

        if (input1.isEmpty() || input2.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_enter_numbers), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(input1);
            double num2 = Double.parseDouble(input2);
            double result;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        tvResult.setText(getString(R.string.error_text));
                        Toast.makeText(this, getString(R.string.msg_divide_by_zero), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = num1 / num2;
                    break;
                default:
                    result = 0;
            }

            tvResult.setText(decimalFormat.format(result));

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.msg_invalid_input), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etNumber1.setText("");
        etNumber2.setText("");
        tvResult.setText(getString(R.string.result_placeholder));
        etNumber1.requestFocus();
    }