package com.example.jingziqi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {

    private LinearLayout forumContainer; // 消息广场容器
    private EditText inputNickname, inputMessage; // 昵称和消息输入框
    private Button postButton, backButton; // 发布和返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // 初始化控件
        forumContainer = findViewById(R.id.forum_container);
        inputNickname = findViewById(R.id.input_nickname);
        inputMessage = findViewById(R.id.input_message);
        postButton = findViewById(R.id.post_button);
        backButton = findViewById(R.id.back_button);

        // 发布消息按钮
        postButton.setOnClickListener(v -> {
            String nickname = inputNickname.getText().toString().trim();
            String message = inputMessage.getText().toString().trim();

            if (nickname.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "请输入昵称和消息内容", Toast.LENGTH_SHORT).show();
                return;
            }

            // 添加消息到广场
            addMessage(nickname, message);

            // 清空输入框
            inputNickname.setText("");
            inputMessage.setText("");
            Toast.makeText(this, "消息已发布！", Toast.LENGTH_SHORT).show();
        });

        // 返回按钮
        backButton.setOnClickListener(v -> finish());
    }

    // 添加消息到消息广场
    private void addMessage(String nickname, String message) {
        View messageView = getLayoutInflater().inflate(R.layout.comment_item, forumContainer, false);

        // 设置消息内容
        TextView nicknameText = messageView.findViewById(R.id.nickname_text);
        TextView messageText = messageView.findViewById(R.id.message_text);
        Button commentButton = messageView.findViewById(R.id.comment_button);

        nicknameText.setText(nickname);
        messageText.setText(message);

        // 评论功能
        commentButton.setOnClickListener(v -> {
            EditText commentInput = messageView.findViewById(R.id.comment_input);
            String comment = commentInput.getText().toString().trim();

            if (!comment.isEmpty()) {
                addComment(messageView, comment);
                commentInput.setText("");
            } else {
                Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
            }
        });

        forumContainer.addView(messageView, 0); // 将最新消息添加到顶部
    }

    // 添加评论
    private void addComment(View messageView, String comment) {
        LinearLayout commentContainer = messageView.findViewById(R.id.comment_container);

        // 创建评论文本
        TextView commentText = new TextView(this);
        commentText.setText(comment);
        commentText.setPadding(10, 12, 10, 12);
        commentText.setTextSize(14f); // 设置文本大小
        commentText.setTextColor(getResources().getColor(R.color.comment_text_color)); // 设置文本颜色
        commentText.setBackgroundResource(R.drawable.message_background);
        commentText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // 将评论添加到容器顶部
        commentContainer.addView(commentText, 0);
    }
}
