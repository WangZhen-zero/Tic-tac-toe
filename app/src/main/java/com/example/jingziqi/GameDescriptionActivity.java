package com.example.jingziqi;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class GameDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_description);

        // 定义长文本的分段内容
        List<String> pages = new ArrayList<>();
        pages.add("棋盘的传说与规则\n\n" +
                "在这片神秘的土地上，井字棋不仅是孩童的游戏，它更是决定大陆命运的神秘仪式。\n\n" +
                "• 棋盘与棋子：3x3的石质棋盘，由两位玩家——代表光明的骑士艾丽娅和象征黑暗的法师卡奥斯——轮流在棋盘的空格中放置自己的棋子。艾丽娅的“X”棋子如同璀璨的星辰，而卡奥斯的“O”棋子则如同夜空中的黑洞，深邃而神秘。\n\n" +
                "• 落子与胜利：玩家的目标是在横向、纵向或对角线上率先连成一线三个相同的标记。这不仅是战术上的胜利，更是光明与黑暗力量在大陆上的较量。\n\n" +
                "• 平局与平衡：若棋盘被填满而无人获胜，则象征着光明与黑暗的完美平衡，大陆将迎来和平与繁荣。");

        pages.add("勇者的智慧对决\n\n" +
                "在艾瑟兰大陆的一个清晨，两位勇者在命运之轮前相遇。艾丽娅，光明的骑士，手持闪耀的长剑，象征着正义与智慧；卡奥斯，暗影的法师，身着漆黑的斗篷，眼神深邃而神秘。他们决定通过井字棋的对决来决定谁更值得掌控大陆的命运。\n\n" +
                "艾丽娅首先在棋盘中心放置了她的“X”棋子，卡奥斯则在相邻的方格中放置了他的“O”棋子。游戏开始，每一步棋都充满了深意，每一局都可能改变大陆的未来。");

        pages.add("魔法与棋子的交织\n\n" +
                "在艾瑟兰大陆，井字棋的棋子不仅仅是简单的符号，它们蕴含着强大的魔法力量。每当一位玩家在棋盘上放置一个棋子，他们实际上是在召唤一个古老的魔法，这个魔法会与棋盘上的魔法领域产生共鸣，释放出惊人的能量。\n\n" +
                "• 光明与黑暗的交织：艾丽娅的“X”棋子代表着光明的力量，它们在棋盘上闪耀着温暖的光芒，为大陆带来希望和活力。卡奥斯的“O”棋子则代表着黑暗的力量，它们散发着冷静的光辉，为大陆上的生物提供庇护和神秘的力量。\n\n" +
                "• 魔法的增幅：随着游戏的进行，棋盘上的魔法力量会逐渐增强。当三个棋子连成一线时，它们之间的魔法力量会相互增幅，形成一个强大的魔法阵，影响着大陆的天气、季节甚至历史的进程。");

        pages.add("终极对决与命运的转折\n\n" +
                "当艾丽娅和卡奥斯的对决进入高潮，棋盘上的棋子已经形成了错综复杂的图案。艾瑟兰大陆的居民们聚集在命运之轮周围，屏息凝视着这场决定大陆未来的对决。\n\n" +
                "随着最后一颗棋子的落下，艾瑟兰大陆上的人们爆发出雷鸣般的欢呼。艾丽娅和卡奥斯的对决以平局结束，光明与黑暗的力量在命运之轮上达到了完美的平衡。这场奇幻的井字棋游戏，不仅让两位勇者获得了彼此的尊重，也让艾瑟兰大陆迎来了一个和平而繁荣的新时代。");

        // 设置 ViewPager2 适配器
        ViewPager2 viewPager = findViewById(R.id.game_description_pager);
        DescriptionPagerAdapter adapter = new DescriptionPagerAdapter(pages);
        viewPager.setAdapter(adapter);

        // 返回标题按钮
        Button backToTitleButton = findViewById(R.id.back_to_title_button);
        backToTitleButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameDescriptionActivity.this, StartActivity.class);
            startActivity(intent);
            finish(); // 结束当前活动，避免堆栈中保留
        });

    }
}
