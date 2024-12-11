package com.example.jingziqi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput; // 用户名和密码输入框
    private SharedPreferences sharedPreferences; // 用于存储用户数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化控件
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        // 获取 SharedPreferences 实例
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);

        // 登录按钮点击事件
        loginButton.setOnClickListener(v -> handleLogin());

        // 注册按钮点击事件
        registerButton.setOnClickListener(v -> handleRegister());
    }

    /**
     * 处理登录逻辑
     */
    private void handleLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // 验证输入的用户名和密码
        String storedPassword = sharedPreferences.getString(username, null);
        if (!TextUtils.isEmpty(storedPassword) && storedPassword.equals(password)) {
            // 登录成功，跳转到开始游戏页面
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
            finish(); // 结束当前活动 
        } else {
            // 提示用户名或密码错误
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理注册逻辑
     */
    private void handleRegister() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // 检查输入是否为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查用户名是否已存在
        if (sharedPreferences.contains(username)) {
            Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
        } else {
            // 保存用户名和密码到 SharedPreferences
            sharedPreferences.edit().putString(username, password).apply();
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();

            // 自动填充用户名并清空密码框
            usernameInput.setText(username);
            passwordInput.setText("");
        }
    }
}
