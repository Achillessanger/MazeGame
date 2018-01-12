# MazeGame
MazeGame
设计：

接口：
Map:interface 用于存放地图常量数组

类：
Operation：class 存放会改变坐标的方法，即迷宫的核心算法
TestGame:class 存放start/main函数，及所有会对面板造成改变的方法
MyMenuBar:class 用于设置游戏界面中最上面的菜单栏

类关系：
由TestGame类进入游戏界面，经过选择进入迷宫后通过Operation类中的方法完成人物移动和动作，及怪物的移动和动作，并将信息反馈至TestGame类中改变面板信息。MyMenuBar类用于设定菜单栏信息，点击后反馈至调用TestGame类中的相应方法。

迷宫界面构成：
MyMenuBar：MenuBar 菜单栏
titlrPane:StackPane 血条攻击力界面
Maze：GridPane 包含墙，空，宝箱的迷宫地图
pMove:Pane 人物移动（用于实现动画）
mMove:Pane 怪物移动（用于实现动画）

迷宫游戏实现：
void durGame(Stage primaryStage,int no) 实现过场动画，no为关卡数

↓调用

void mazeGame（Stage primaryStage，int no,int storyOrSand）
读取键盘输入→→Operation类中调取相应heroMove（KeyCode x,int no）和monsterMove（int[] userNewPos,int no）方法获取新地图和新坐标→→TestGame类中
printMap()用于刷新地图
printP（）用于播放人物走动动画同时体现人物新坐标
printM（）播放怪兽动画并体现其新坐标
printPK（）播放人物攻击动画

↓判断到达每层迷宫终点（通过人物坐标判断）

调用下一层迷宫的durGame（）、mazeGame（）方法或者调用showFinishScene()结束游戏



功能：
必做功能（全）：
地图设计、人物移动（wasd）、人物碰到怪物掉血至死亡、怪物自动独立移动、拾取宝箱（p）、攻击怪物（k）、实现足迹、后退（b）、进入下一层迷宫及判断胜利、查看玩家信息（c）、查看帮助信息（h/MenuBar）、实现沙盒模式、UI美观、音效（游戏背景乐、走路、攻击、拾取、胜利、失败音效）、排行榜、文档
p.s.菜单栏处可额外切换沙盒模式和故事模式

加分功能（已做）：
1.怪物追踪玩家（比较简陋，只大致判断人和怪物的位置使怪物向人方向移动）
2.玩家与怪物的攻防模式设计（血条，攻击力）p.s.玩家在离怪物两格之内时可以看见怪物血条
3.使用故事模式串联关卡模式（每一关之前会出现一段文字动画）
4.存档读档
5.切换主题
6.动作动画（对人物走动、攻击，怪物走动动作附加了动画表现）
