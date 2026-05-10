package com.duongtanminh.k234111eapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    Button btnCalculate;
    TextView txtMC, txtMR, txtMPlus, txtMMinus, txtMS, txtM;
    View.OnClickListener click_m_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        btnCalculate = findViewById(R.id.btnCalculate);
        txtMC        = findViewById(R.id.txtMC);
        txtMR        = findViewById(R.id.txtMR);
        txtMPlus     = findViewById(R.id.txtMPlus);
        txtMMinus    = findViewById(R.id.txtMMinus);
        txtMS        = findViewById(R.id.txtMS);
        txtM         = findViewById(R.id.txtM);
    }

    private void addEvents() {
        // Anonymous listener cho nút =
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFormular();
            }
        });

        // Variable as listener cho các nút nhớ
        click_m_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.equals(txtM)) {
                    // xử lý sau
                } else if (view.equals(txtMC)) {
                    // xử lý sau
                }
                // không được dùng view == txtMC vì so sánh địa chỉ RAM
            }
        };
        txtM.setOnClickListener(click_m_listener);
        txtMC.setOnClickListener(click_m_listener);
        txtMMinus.setOnClickListener(click_m_listener);
        txtMR.setOnClickListener(click_m_listener);
        txtMPlus.setOnClickListener(click_m_listener);
        txtMS.setOnClickListener(click_m_listener);
    }

    private void processFormular() {
        // Bước 1: Lấy công thức từ EditText
        EditText edtFormular = findViewById(R.id.edtFormular);
        String formular = edtFormular.getText().toString();

        // Bước 2: Tính toán công thức
        String result = "";
        try {
            result = String.valueOf(evaluate(formular));
        } catch (Exception e) {
            result = "Error";
        }

        // Bước 3: Hiển thị kết quả
        edtFormular.setText(result);
    }

    // Hàm tính toán biểu thức đơn giản (cộng, trừ, nhân, chia)
    private double evaluate(String expression) {
        expression = expression.trim();

        // Tìm dấu + hoặc - cuối cùng (ưu tiên thấp nhất)
        int lastPlus  = expression.lastIndexOf('+');
        int lastMinus = expression.lastIndexOf('-');
        int lastAdd   = Math.max(lastPlus, lastMinus);

        if (lastAdd > 0) {
            double left  = evaluate(expression.substring(0, lastAdd));
            double right = evaluate(expression.substring(lastAdd + 1));
            return (expression.charAt(lastAdd) == '+') ? left + right : left - right;
        }

        // Tìm dấu * hoặc :
        int lastMul = expression.lastIndexOf('*');
        int lastDiv = expression.lastIndexOf(':');
        int lastMulDiv = Math.max(lastMul, lastDiv);

        if (lastMulDiv > 0) {
            double left  = evaluate(expression.substring(0, lastMulDiv));
            double right = evaluate(expression.substring(lastMulDiv + 1));
            if (expression.charAt(lastMulDiv) == '*') return left * right;
            if (right == 0) throw new ArithmeticException("Chia cho 0");
            return left / right;
        }

        // Số đơn lẻ
        return Double.parseDouble(expression);
    }

    // onClick XML: nút số và toán tử gọi hàm này
    public void processChooseValue(View view) {
        Button btn = (Button) view;
        EditText edtFormular = findViewById(R.id.edtFormular);
        String oldValue = edtFormular.getText().toString();
        String newValue = btn.getText().toString();
        edtFormular.setText(oldValue + newValue);
    }

    // onClick XML: nút backspace
    public void processBackspace(View view) {
        EditText edtFormular = findViewById(R.id.edtFormular);
        String oldValue = edtFormular.getText().toString();
        if (oldValue.length() > 1) {
            edtFormular.setText(oldValue.substring(0, oldValue.length() - 1));
        } else {
            edtFormular.setText("");
        }
    }
}
