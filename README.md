# 小小井字棋 (Tic-Tac-Toe)

## 项目介绍
小小井字棋是一款以经典井字棋为主题的移动端游戏，适合所有年龄段的玩家。项目以 Android 平台为基础，结合冒险风格的动态效果和交互设计，旨在提供轻松、有趣的游戏体验。

## 功能特色
- **经典井字棋玩法**：玩家可以快速上手，与对手展开对决。
- **奇幻冒险主题动态效果**：
  - 渐变背景动画，粒子效果模拟魔法氛围。
  - 按钮悬浮动画和点击反馈，提升交互体验。
  - 游戏标题动态展示，增加趣味性。
- **多功能菜单界面**：
  - 开始游戏
  - 游戏介绍
  - 玩家论坛
  - 退出登录
  - 退出游戏
- **多页面导航**：包括游戏主界面、游戏介绍页面、玩家论坛页面、登录页面等。

## 技术栈
- **开发语言**：Java
- **开发工具**：Android Studio
- **UI 设计**：XML 布局，动态效果通过 ObjectAnimator 和 ValueAnimator 实现
- **动画框架**：Android 原生动画框架

## 项目结构
```
小小井字棋/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/jingziqi/   # Java 源代码
│   │   │   │   ├── StartActivity.java       # 主菜单界面逻辑
│   │   │   │   ├── MainActivity.java        # 游戏主界面逻辑
│   │   │   │   ├── GameDescriptionActivity.java  # 游戏介绍页面逻辑
│   │   │   │   └── LoginActivity.java        # 登录页面逻辑
│   │   │   ├── res/
│   │   │   │   ├── layout/                   # XML 布局文件
│   │   │   │   ├── drawable/                 # 绘制资源
│   │   │   │   └── values/                   # 字符串、颜色资源
├── README.md                                 # 项目说明
```

## 安装与运行

### 环境要求
- Android Studio 4.0 或更高版本
- Android SDK 29 或更高版本
- Java 8 或更高版本

### 安装步骤
1. 克隆项目到本地：
   ```bash
   git clone https://github.com/WangZhen-zero/jingziqi.git
   ```

2. 使用 Android Studio 打开项目。

3. 确保已下载对应的 Android SDK 版本。

4. 将应用部署到模拟器或连接的 Android 设备：
   - 点击 `Run` 按钮。
   - 选择运行设备。

5. 开始享受游戏！


### 报告问题
如果发现任何问题，请通过 [Issues](https://github.com/WangZhen-zero/jingziqi/issues) 页面提交问题。

## 许可证
该项目基于 [MIT License](LICENSE) 开源。

