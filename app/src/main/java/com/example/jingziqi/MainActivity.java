package com.example.jingziqi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int BOARD_SIZE = 3; // 棋盘大小
    private boolean player1Turn = true; // 玩家回合标志
    private int roundCount = 0; // 回合计数
    private boolean vsComputer = true; // 默认对战模式为电脑对战

    private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE]; // 棋盘按钮数组
    private TextView statusText; // 游戏状态提示文本
    private View boardLayout; // 棋盘容器视图

    private int player1Score = 0; // 玩家 1 得分
    private int player2Score = 0; // 玩家 2 得分
    private TextView player1ScoreText; // 玩家 1 分数显示
    private TextView player2ScoreText; // 玩家 2 分数显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化组件
        statusText = findViewById(R.id.status_text);
        player1ScoreText = findViewById(R.id.player1_score);
        player2ScoreText = findViewById(R.id.player2_score);
        boardLayout = findViewById(R.id.board_layout);

        // 设置默认棋盘颜色
        updateBoardColor();

        // 初始化棋盘按钮
        initializeButtons();

        // 模式切换按钮
        Button modeToggleButton = findViewById(R.id.mode_toggle_button);
        modeToggleButton.setOnClickListener(v -> {
            vsComputer = !vsComputer; // 切换模式
            String modeText = vsComputer ? "切换到玩家对战" : "切换到电脑对战";
            modeToggleButton.setText(modeText);
            resetBoard();
        });

        // 设置重置按钮
        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> resetBoard());

        // 设置重置分数按钮
        Button resetScoreButton = findViewById(R.id.reset_score_button);
        resetScoreButton.setOnClickListener(v -> resetScores());

        // 设置返回标题按钮
        Button backToTitleButton = findViewById(R.id.back_to_title_button);
        backToTitleButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        });

    }

    //初始化棋盘
    private void initializeButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                //给棋盘按钮设置监听器
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }
    }

    // 按钮点击事件
    private void onButtonClick(View v) {
        Button button = (Button) v;

        // 如果按钮已被占用，则直接返回
        if (!button.getText().toString().isEmpty()) {
            return;
        }

        // 根据当前玩家设置棋子
        if (player1Turn) {
            button.setText("X");
            statusText.setText("卡奥斯 的回合 (O)");
        } else {
            button.setText("O");
            statusText.setText("玩家 艾丽娅 的回合 (X)");
        }

        roundCount++;

        // 检查胜利或平局
        if (checkForWin()) {
            if (player1Turn) {
                showWinner("玩家 艾丽娅 胜利！");
            } else {
                showWinner("卡奥斯 胜利！");
            }
            return;
        } else if (roundCount == BOARD_SIZE * BOARD_SIZE) {
            showWinner("平局啦~");
            return;
        }

        // 切换玩家回合
        player1Turn = !player1Turn;
        updateBoardColor(); // 更新棋盘颜色

        // 如果是电脑模式并且轮到电脑下棋
        if (vsComputer && !player1Turn) {
            computerMove();
        }

    }

    // 电脑移动逻辑
    private void computerMove() {
        // 使用 Handler 延迟执行电脑的动作
        new android.os.Handler().postDelayed(() -> {
            Random random = new Random();
            int i, j;

            // 随机选择未被占用的格子
            do {
                i = random.nextInt(BOARD_SIZE);
                j = random.nextInt(BOARD_SIZE);
            } while (!buttons[i][j].getText().toString().isEmpty());

            // 模拟电脑下棋
            buttons[i][j].setText("O");
            roundCount++;

            // 检查游戏状态
            if (checkForWin()) {
                showWinner("卡奥斯 胜利！");
            } else if (roundCount == BOARD_SIZE * BOARD_SIZE) {
                showWinner("平局啦！");
            } else {
                player1Turn = true; // 切换回玩家
                updateBoardColor(); // 更新棋盘颜色
            }
        }, 1000); // 延迟 1 秒
    }

    //更新棋盘颜色
    private void updateBoardColor() {
        // 根据当前玩家更新棋盘背景颜色
        int boardColor = player1Turn ? getResources().getColor(R.color.player1_color) :
                getResources().getColor(R.color.player2_color);
        boardLayout.setBackgroundColor(boardColor);

    }

    // 游戏胜利或平局判断
    private boolean checkForWin() {
        String[][] field = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            // 检查行
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].isEmpty()) {
                return true;
            }
            // 检查列
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].isEmpty()) {
                return true;
            }
        }

        // 检查对角线
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].isEmpty()) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].isEmpty()) {
            return true;
        }

        return false;
    }

    private void showWinner(String message) {
        // 创建对话框构造器
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.game_result, null);

        // 设置对局结果文本
        TextView resultText = dialogView.findViewById(R.id.result_text);
        resultText.setText(message);

        // 创建对话框实例
        builder.setView(dialogView);
        builder.setCancelable(false);
        androidx.appcompat.app.AlertDialog dialog = builder.create(); // 声明并创建 dialog 变量

        // 更新分数
        if (message.contains("玩家 艾丽娅")) {
            player1Score++; // 玩家1胜利
            player1ScoreText.setText("艾丽娅得分 : " + player1Score);
        } else if (message.contains("卡奥斯")) {
            player2Score++; // 玩家2胜利
            player2ScoreText.setText("卡奥斯得分 : " + player2Score);
        }

        // 绑定重置按钮
        Button resetButton = dialogView.findViewById(R.id.reset_button);
        if (resetButton == null) {
            throw new IllegalStateException("没有找到重置按钮!");
        }

        //防止重复点击按钮
        final boolean[] isClicked = {false};
        resetButton.setOnClickListener(v -> {
            if (!isClicked[0]) {
                isClicked[0] = true; // 标记为已点击
                dialog.dismiss(); // 立即关闭对话框
                // 使用 Handler 延迟执行逻辑，确保对话框关闭后再处理
                new android.os.Handler().postDelayed(this::resetBoard, 200);
            }
        });

        builder.setView(dialogView);
        builder.setCancelable(false);

        // 设置淡入动画
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        // 显示对话框
        dialog.show();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(""); // 清空每个按钮的文本
                buttons[i][j].setAlpha(0.0f); // 设置初始透明度
                buttons[i][j].animate().alpha(1.0f).setDuration(300).start(); // 添加淡入动画
            }
        }

        roundCount = 0; // 重置回合计数
        player1Turn = true; // 重新开始时默认玩家1先手
        statusText.setText("玩家 艾丽娅 的回合 (X)"); // 更新提示信息
    }

    /**
     * 重置分数
     * 将玩家 1 和玩家 2 的分数重置为 0，并更新界面显示。
     */
    private void resetScores() {
        // 将得分变量重置为 0
        player1Score = 0;
        player2Score = 0;

        // 更新显示的分数
        player1ScoreText.setText("玩家艾丽娅得分: 0");
        player2ScoreText.setText("卡奥斯得分: 0");

        // 提示用户分数已重置
        Toast.makeText(this, "分数已重置", Toast.LENGTH_SHORT).show();
    }
}

