package com.example.jingziqi;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.drawerlayout.widget.DrawerLayout;

public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private DrawerLayout drawerLayout;
    private SharedPreferences sharedPreferences;
    private ImageView userAvatar;
    private EditText nicknameInput;

    // 背景视图
    private View background;
    // 游戏标题文本
    private TextView gameTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // 初始化背景和标题视图
        background = findViewById(R.id.animated_background);
        gameTitle = findViewById(R.id.game_title);

        // 初始化按钮
        Button startButton = findViewById(R.id.start_button); // 开始游戏按钮
        Button descriptionButton = findViewById(R.id.description_button); // 游戏介绍按钮
        Button exitloginButton = findViewById(R.id.exit_login_button); // 退出登录按钮
        Button exitButton = findViewById(R.id.exit_button); // 退出游戏按钮
        ImageButton userCenterButton = findViewById(R.id.user_center_button); // 用户中心按钮
        Button suggestionButton = findViewById(R.id.suggestion_button); //玩家论坛按钮

        // 开始页面背景渐变动画
        startBackgroundAnimation();

        // 标题动画
        startTitleAnimation();

        // 为每个按钮添加浮动动画
        addFloatingEffect(startButton);
        addFloatingEffect(descriptionButton);
        addFloatingEffect(exitloginButton);
        addFloatingEffect(exitButton);
        addFloatingEffect(suggestionButton);

        // 设置“开始游戏”按钮点击事件
        startButton.setOnClickListener(v -> {
            // 跳转到游戏主界面
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 设置“游戏介绍”按钮点击事件
        descriptionButton.setOnClickListener(v -> {
            // 跳转到游戏介绍界面
            Intent intent = new Intent(StartActivity.this, GameDescriptionActivity.class);
            startActivity(intent);
        });

        // 设置“玩家论坛”按钮点击事件
        suggestionButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, CommentActivity.class);
            startActivity(intent);
        });

        // 设置“退出登录”按钮点击事件
        exitloginButton.setOnClickListener(v -> {
            // 跳转到登录界面
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // 设置“退出游戏”按钮点击事件
        exitButton.setOnClickListener(v -> {
            // 弹出确认退出对话框
            new AlertDialog.Builder(StartActivity.this)
                    .setTitle("退出游戏") // 对话框标题
                    .setMessage("勇者大人，您真的要离开吗？")
                    .setPositiveButton("我意已决", (dialog, which) -> {
                        finish(); // 结束当前活动
                        System.exit(0); // 退出应用进程
                    })
                    .setNegativeButton("考虑一下", null) // 点击不执行操作
                    .show();
        });

        // 设置“用户中心”按钮点击事件
        userCenterButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, UserCenterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 背景渐变动画方法
     */
    private void startBackgroundAnimation() {
        // 创建渐变背景
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR, // 从左上到右下的渐变方向
                new int[]{0xFFB3E5FC, 0xFFFDE7E7, 0xFFFFF9C4}
        );
        background.setBackground(gradient); // 设置背景为渐变背景

        // 使用 ValueAnimator 动态更改渐变颜色
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(10000); // 动画持续时间为 10 秒
        animator.setRepeatCount(ValueAnimator.INFINITE); // 无限循环
        animator.setRepeatMode(ValueAnimator.REVERSE); // 循环时反转动画
        animator.addUpdateListener(animation -> {
            // 动态更新渐变
            float fraction = animation.getAnimatedFraction();
            gradient.setColors(new int[]{
                    adjustColorAlpha(0xFFB3E5FC, fraction), // 动态调整背景颜色
                    adjustColorAlpha(0xFFFDE7E7, fraction),
                    adjustColorAlpha(0xFFFFF9C4, 1 - fraction)
            });
        });
        animator.start(); // 启动动画
    }

    /**
     * 调整颜色透明度
     *
     * @param color 原始颜色值
     * @param alphaFactor 透明度因子（0-1）
     * @return 新的颜色值
     */
    private int adjustColorAlpha(int color, float alphaFactor) {
        int alpha = Math.round((color >> 24 & 0xFF) * alphaFactor);
        return (alpha << 24) | (color & 0x00FFFFFF);
    }

    /**
     * 标题动画方法
     */
    private void startTitleAnimation() {
        // 创建一个淡入动画
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(gameTitle, "alpha", 0f, 1f);
        fadeIn.setDuration(2000); // 动画持续时间为 2 秒
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator()); // 设置动画插值器
        fadeIn.start(); // 启动动画
        gameTitle.setVisibility(View.VISIBLE); // 设置标题为可见
    }

    /**
     * 按钮浮动动画方法
     * @param button 要添加动画的按钮视图
     */
    private void addFloatingEffect(View button) {
        // 创建一个上下浮动动画
        ObjectAnimator floatAnimator = ObjectAnimator.ofFloat(button, "translationY", 0, 20);
        floatAnimator.setDuration(2000); // 动画持续时间为 2 秒
        floatAnimator.setRepeatCount(ObjectAnimator.INFINITE); // 无限循环
        floatAnimator.setRepeatMode(ObjectAnimator.REVERSE); // 循环时反转动画
        floatAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // 设置动画插值器
        floatAnimator.start();
    }

}
