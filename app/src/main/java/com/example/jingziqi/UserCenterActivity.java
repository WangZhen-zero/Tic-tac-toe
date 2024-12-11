package com.example.jingziqi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class UserCenterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION_STORAGE = 100;
    private static final String AVATAR_FILE_NAME = "user_avatar.png";
    private static final String IMAGE_FILE_NAME = "display_image.png";

    private SharedPreferences sharedPreferences;
    private ImageView userAvatar,  displayImage;
    private EditText nicknameInput;
    private EditText signatureInput;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        // 获取控件
        userAvatar = findViewById(R.id.user_avatar);
        nicknameInput = findViewById(R.id.nickname_input);
        signatureInput = findViewById(R.id.signature_input);
        Button uploadAvatarButton = findViewById(R.id.upload_avatar_button);
        displayImage = findViewById(R.id.display_image);
        Button saveButton = findViewById(R.id.save_button);
        Button backButton = findViewById(R.id.back_button);

        // 初始化 SharedPreferences
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);

        // 加载用户信息
        loadUserInfo();

        // 上传头像按钮
        uploadAvatarButton.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestStoragePermission();
            } else {
                openImagePicker();
            }
        });

        // 保存按钮
        saveButton.setOnClickListener(v -> saveUserInfo());

        // 返回按钮
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                    userAvatar.setImageBitmap(selectedImage); // 显示头像
                    saveAvatarToStorage(selectedImage); // 保存头像
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "加载图片失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "无法获取图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
                openImagePicker();
            } else {
                Toast.makeText(this, "存储权限被拒绝，无法上传头像", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadUserInfo() {
//        // 加载昵称
//        String nickname = sharedPreferences.getString("nickname", "未知用户");
//        nicknameInput.setText(nickname);

        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        nicknameInput.setText(sharedPreferences.getString("nickname", "用户114514"));
        signatureInput.setText(sharedPreferences.getString("signature", "这个人很懒，什么都没写哦~"));

        // 加载头像
        File avatarFile = new File(getFilesDir(), AVATAR_FILE_NAME);
        if (avatarFile.exists()) {
            Bitmap avatarBitmap = BitmapFactory.decodeFile(avatarFile.getAbsolutePath());
            userAvatar.setImageBitmap(avatarBitmap);
        }

        // 加载展示图片
        File imageFile = new File(getFilesDir(), IMAGE_FILE_NAME);
        if (imageFile.exists()) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            displayImage.setImageBitmap(imageBitmap);
        }
    }

    private void saveUserInfo() {
        // 保存昵称
        String nickname = nicknameInput.getText().toString().trim();
        String signature = signatureInput.getText().toString().trim();

        if (nickname.isEmpty()) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nickname", nickname);
        editor.putString("signature", signature);
        editor.apply();

        Toast.makeText(this, "用户信息已保存", Toast.LENGTH_SHORT).show();
    }

    private void saveAvatarToStorage(Bitmap bitmap) {
        File avatarFile = new File(getFilesDir(), AVATAR_FILE_NAME);
        try (FileOutputStream outputStream = new FileOutputStream(avatarFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            Toast.makeText(this, "头像已保存", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "保存头像失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 打开图片选择器
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
    }
}
