package com.example.simplecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult;
    private ImageButton btnThemeToggle, btnRotate;
    private Button btnToggleAdvanced;
    private LinearLayout layoutAdvanced;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.##########");
    
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_IS_NIGHT_MODE = "is_night_mode";
    private static final String KEY_IS_ADVANCED_VISIBLE = "is_advanced_visible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean(KEY_IS_NIGHT_MODE, true);
        
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
        btnRotate = findViewById(R.id.btnRotate);
        btnToggleAdvanced = findViewById(R.id.btnToggleAdvanced);
        layoutAdvanced = findViewById(R.id.layoutAdvanced);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnPercent = findViewById(R.id.btnPercent);
        Button btnSquare = findViewById(R.id.btnSquare);
        Button btnSqrt = findViewById(R.id.btnSqrt);
        Button btnPower = findViewById(R.id.btnPower);
        Button btnMod = findViewById(R.id.btnMod);
        Button btnClear = findViewById(R.id.btnClear);

        // Load advanced visibility state
        boolean isAdvancedVisible = sharedPreferences.getBoolean(KEY_IS_ADVANCED_VISIBLE, false);
        setAdvancedVisibility(isAdvancedVisible);

        updateThemeIcon(isNightMode);

        // Listeners
        btnAdd.setOnClickListener(v -> performCalculation('+'));
        btnSubtract.setOnClickListener(v -> performCalculation('-'));
        btnMultiply.setOnClickListener(v -> performCalculation('*'));
        btnDivide.setOnClickListener(v -> performCalculation('/'));
        btnPercent.setOnClickListener(v -> performCalculation('%'));
        btnSquare.setOnClickListener(v -> performCalculation('s'));
        btnSqrt.setOnClickListener(v -> performCalculation('r'));
        btnPower.setOnClickListener(v -> performCalculation('p'));
        btnMod.setOnClickListener(v -> performCalculation('m'));
        
        btnClear.setOnClickListener(v -> clearFields());
        btnThemeToggle.setOnClickListener(v -> toggleTheme());
        btnRotate.setOnClickListener(v -> toggleOrientation());
        btnToggleAdvanced.setOnClickListener(v -> {
            boolean current = layoutAdvanced.getVisibility() == View.VISIBLE;
            setAdvancedVisibility(!current);
            sharedPreferences.edit().putBoolean(KEY_IS_ADVANCED_VISIBLE, !current).apply();
        });
    }

    private void setAdvancedVisibility(boolean visible) {
        layoutAdvanced.setVisibility(visible ? View.VISIBLE : View.GONE);
        btnToggleAdvanced.setText(visible ? R.string.btn_hide_advanced : R.string.btn_show_advanced);
    }

    private void toggleOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void toggleTheme() {
        boolean isNightMode = sharedPreferences.getBoolean(KEY_IS_NIGHT_MODE, true);
        boolean newMode = !isNightMode;
        sharedPreferences.edit().putBoolean(KEY_IS_NIGHT_MODE, newMode).apply();
        
        if (newMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    private void updateThemeIcon(boolean isNightMode) {
        btnThemeToggle.setImageResource(isNightMode ? R.drawable.ic_theme_light : R.drawable.ic_theme_dark);
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

        if (input1.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_enter_numbers), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean needsInput2 = !(operator == 's' || operator == 'r');

        if (needsInput2 && input2.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_enter_numbers), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(input1);
            double num2 = needsInput2 ? Double.parseDouble(input2) : 0;
            double result;

            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '*': result = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        handleError(R.string.msg_divide_by_zero);
                        return;
                    }
                    result = num1 / num2;
                    break;
                case '%': result = num1 * (num2 / 100); break;
                case 's': result = num1 * num1; break;
                case 'r':
                    if (num1 < 0) {
                        handleError(R.string.msg_invalid_input);
                        return;
                    }
                    result = Math.sqrt(num1);
                    break;
                case 'p': result = Math.pow(num1, num2); break;
                case 'm':
                    if (num2 == 0) {
                        handleError(R.string.msg_divide_by_zero);
                        return;
                    }
                    result = num1 % num2;
                    break;
                default: result = 0;
            }

            tvResult.setText(decimalFormat.format(result));

        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.msg_invalid_input), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(int stringResId) {
        tvResult.setText(getString(R.string.error_text));
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        etNumber1.setText("");
        etNumber2.setText("");
        tvResult.setText(getString(R.string.result_placeholder));
        etNumber1.requestFocus();
    }
}
