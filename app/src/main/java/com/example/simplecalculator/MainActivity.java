package com.example.simplecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber1, etNumber2;
    private TextView tvResult, tvEmptyHistory;
    private ImageButton btnThemeToggle, btnRotate;
    private Button btnToggleAdvanced, btnClearHistory;
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;
    private List<HistoryItem> historyList;
    
    private LinearLayout layoutAdvanced;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.##########");
    
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_IS_NIGHT_MODE = "is_night_mode";
    private static final String KEY_IS_ADVANCED_VISIBLE = "is_advanced_visible";
    private static final String KEY_HISTORY = "calculation_history";

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
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);
        btnThemeToggle = findViewById(R.id.btnThemeToggle);
        btnRotate = findViewById(R.id.btnRotate);
        btnToggleAdvanced = findViewById(R.id.btnToggleAdvanced);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        layoutAdvanced = findViewById(R.id.layoutAdvanced);
        rvHistory = findViewById(R.id.rvHistory);

        // Setup History
        loadHistory();
        historyAdapter = new HistoryAdapter(historyList);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);
        updateHistoryVisibility();

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
        btnClearHistory.setOnClickListener(v -> clearHistory());
        btnThemeToggle.setOnClickListener(v -> toggleTheme());
        btnRotate.setOnClickListener(v -> toggleOrientation());
        btnToggleAdvanced.setOnClickListener(v -> {
            boolean current = layoutAdvanced.getVisibility() == View.VISIBLE;
            setAdvancedVisibility(!current);
            sharedPreferences.edit().putBoolean(KEY_IS_ADVANCED_VISIBLE, !current).apply();
        });
    }

    private void loadHistory() {
        String historyJson = sharedPreferences.getString(KEY_HISTORY, null);
        if (historyJson != null) {
            Type type = new TypeToken<ArrayList<HistoryItem>>() {}.getType();
            historyList = new Gson().fromJson(historyJson, type);
        } else {
            historyList = new ArrayList<>();
        }
    }

    private void saveHistory() {
        String historyJson = new Gson().toJson(historyList);
        sharedPreferences.edit().putString(KEY_HISTORY, historyJson).apply();
    }

    private void clearHistory() {
        historyList.clear();
        saveHistory();
        historyAdapter.notifyDataSetChanged();
        updateHistoryVisibility();
        Toast.makeText(this, R.string.msg_history_cleared, Toast.LENGTH_SHORT).show();
    }

    private void updateHistoryVisibility() {
        if (historyList.isEmpty()) {
            rvHistory.setVisibility(View.GONE);
            tvEmptyHistory.setVisibility(View.VISIBLE);
        } else {
            rvHistory.setVisibility(View.VISIBLE);
            tvEmptyHistory.setVisibility(View.GONE);
        }
    }

    private void addToHistory(String expression, String result) {
        String timestamp = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(new Date());
        historyList.add(0, new HistoryItem(expression, result, timestamp));
        if (historyList.size() > 50) { // Limit to 50 items
            historyList.remove(historyList.size() - 1);
        }
        saveHistory();
        historyAdapter.notifyItemInserted(0);
        rvHistory.scrollToPosition(0);
        updateHistoryVisibility();
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
        hideKeyboard();
        String input1 = etNumber1.getText().toString().trim();
        String input2 = etNumber2.getText().toString().trim();

        if (input1.isEmpty()) {
            handleError(R.string.msg_enter_numbers);
            return;
        }

        boolean needsInput2 = !(operator == 's' || operator == 'r');

        if (needsInput2 && input2.isEmpty()) {
            handleError(R.string.msg_enter_numbers);
            return;
        }

        try {
            double num1 = Double.parseDouble(input1);
            double num2 = needsInput2 ? Double.parseDouble(input2) : 0;
            double result;
            String expression;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    expression = decimalFormat.format(num1) + " + " + decimalFormat.format(num2);
                    break;
                case '-':
                    result = num1 - num2;
                    expression = decimalFormat.format(num1) + " - " + decimalFormat.format(num2);
                    break;
                case '*':
                    result = num1 * num2;
                    expression = decimalFormat.format(num1) + " × " + decimalFormat.format(num2);
                    break;
                case '/':
                    if (num2 == 0) {
                        handleError(R.string.msg_divide_by_zero);
                        return;
                    }
                    result = num1 / num2;
                    expression = decimalFormat.format(num1) + " ÷ " + decimalFormat.format(num2);
                    break;
                case '%':
                    result = num1 * (num2 / 100);
                    expression = decimalFormat.format(num2) + "% of " + decimalFormat.format(num1);
                    break;
                case 's':
                    result = num1 * num1;
                    expression = decimalFormat.format(num1) + "²";
                    break;
                case 'r':
                    if (num1 < 0) {
                        handleError(R.string.msg_invalid_input);
                        return;
                    }
                    result = Math.sqrt(num1);
                    expression = "√" + decimalFormat.format(num1);
                    break;
                case 'p':
                    result = Math.pow(num1, num2);
                    expression = decimalFormat.format(num1) + "^" + decimalFormat.format(num2);
                    break;
                case 'm':
                    if (num2 == 0) {
                        handleError(R.string.msg_divide_by_zero);
                        return;
                    }
                    result = num1 % num2;
                    expression = decimalFormat.format(num1) + " MOD " + decimalFormat.format(num2);
                    break;
                default:
                    result = 0;
                    expression = "";
            }

            String resultStr = decimalFormat.format(result);
            tvResult.setText(resultStr);
            addToHistory(expression, resultStr);

        } catch (NumberFormatException e) {
            handleError(R.string.msg_invalid_input);
        }
    }

    private void handleError(int stringResId) {
        tvResult.setText(getString(stringResId));
        Toast.makeText(this, getString(stringResId), Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        hideKeyboard();
        etNumber1.setText("");
        etNumber2.setText("");
        tvResult.setText("");
        etNumber1.requestFocus();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    // --- History Helper Classes ---

    public static class HistoryItem {
        private final String expression;
        private final String result;
        private final String timestamp;

        public HistoryItem(String expression, String result, String timestamp) {
            this.expression = expression;
            this.result = result;
            this.timestamp = timestamp;
        }

        public String getExpression() { return expression; }
        public String getResult() { return result; }
        public String getTimestamp() { return timestamp; }
    }

    private static class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
        private final List<HistoryItem> historyList;

        public HistoryAdapter(List<HistoryItem> historyList) {
            this.historyList = historyList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HistoryItem item = historyList.get(position);
            holder.tvExpression.setText(item.getExpression());
            holder.tvResult.setText(item.getResult());
            holder.tvTimestamp.setText(item.getTimestamp());
        }

        @Override
        public int getItemCount() {
            return historyList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvExpression, tvResult, tvTimestamp;

            ViewHolder(View itemView) {
                super(itemView);
                tvExpression = itemView.findViewById(R.id.tvHistoryExpression);
                tvResult = itemView.findViewById(R.id.tvHistoryResult);
                tvTimestamp = itemView.findViewById(R.id.tvHistoryTimestamp);
            }
        }
    }
}
