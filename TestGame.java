import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;


import javax.swing.*;
import javafx.scene.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Scanner;

import javafx.scene.control.Button;
import javafx.util.Duration;

public class TestGame extends Application implements Map{
    Operation operation = new Operation();
    int a = 1;//1春，2冬
    boolean b = false;
    int level = 1;
    int heroDir = 2;//1,2,3,4
    String gameMusicString = TestGame.class.getResource("Song/2.mp3").toString();
    MediaPlayer gameMusic = new MediaPlayer(new Media(gameMusicString));
    String winMusicString = TestGame.class.getResource("Song/win.mp3").toString();
    MediaPlayer winMusic = new MediaPlayer(new Media(winMusicString));
    Timeline monMoveAni;
    ImageView showAfter;
    ImageView show = new ImageView();
    Timeline refresh;

    ImageView mshow = new ImageView();
    ImageView mshow2 = new ImageView();
    ImageView mshow3 = new ImageView();

    int isThemeChanged = 0;
    Timeline keyboardEnter;
    Timeline mM;
    KeyCode lastOption = KeyCode.D;
    //PathTransition heroMovePT;
    boolean canSave = true;

    //Pane pMove = new Pane();
    //StackPane mazeAndOther = new StackPane();
    //GridPane maze = new GridPane();
    //BorderPane mazepane = new BorderPane();
    //MyMenuBar myMenuBar = new MyMenuBar(this);




    @Override
    public void start(Stage primaryStage) throws Exception {
        String startMusicString = TestGame.class.getResource("Song/1.mp3").toString();
        MediaPlayer startmusic = new MediaPlayer(new Media(startMusicString));

        //开始界面gif
        primaryStage.setTitle("MazeGame");
        StackPane startpane = new StackPane();
        //Pane startpane = new Pane();

        Scene sceneStart = new Scene(startpane,960,768);
        Image startImg = new Image("Img/start.png");
        startpane.getChildren().add(new ImageView(startImg));
        //开始界面按钮
        Image story1 = new Image("Img/storymode-1.png");
        Image sandbox1 = new Image("Img/sandbox-1.png");
        Button story = new Button("",new ImageView("Img/storymode-1.png"));
        Button sandbox = new Button("",new ImageView("Img/sandbox-1.png"));
        HBox hBox1 = new HBox(214);
        hBox1.setPadding(new Insets(470,107,343,107));
        hBox1.getChildren().addAll(story,sandbox);
        hBox1.setAlignment(Pos.CENTER);
        startpane.getChildren().addAll(hBox1);

        primaryStage.setScene(sceneStart);
        primaryStage.show();
        startmusic.play();

        story.setOnMousePressed(event -> {
            canSave = true;
            StackPane pane = new StackPane();
            pane.getChildren().add(new ImageView(startImg));
            Scene sceneChooseTheme = new Scene(pane,960,768);
            HBox hBoxCT = new HBox(214);
            hBoxCT.setPadding(new Insets(470,107,343,107));
            Button spring = new Button("",new ImageView("Img/spring.png"));
            Button winter = new Button("",new ImageView("Img/winter.png"));
            //Button returnbt = new Button("",new ImageView("return.png"));
            hBoxCT.getChildren().addAll(spring,winter);
            pane.getChildren().add(hBoxCT);
            primaryStage.setScene(sceneChooseTheme);
            primaryStage.show();

            spring.setOnMousePressed(event1 -> {
                canSave = true;
                startmusic.pause();
                //playGameMusic(1);
                durGame(primaryStage,1,1,1);
                //mazeGame(primaryStage,1,1,1);
            });
            winter.setOnMousePressed(event1 -> {
                canSave = true;
                a = 2;
                startmusic.pause();
                //playGameMusic(1);
                durGame(primaryStage,1,1,1);
                //mazeGame(primaryStage,1,1,1);
            });
        });

        sandbox.setOnMousePressed(event -> {
            canSave = false;
            StackPane pane2 = new StackPane();
            pane2.getChildren().add(new ImageView(startImg));
            Scene sceneChooseLevel = new Scene(pane2,960,768);
            VBox vBoxCL = new VBox(50);
            vBoxCL.setPadding(new Insets(200));
            Button level1 = new Button("第一关");
            Button level2 = new Button("第二关");
            Button level3 = new Button("第三关");
            Button level4 = new Button("第四关");
            vBoxCL.getChildren().addAll(level1,level2,level3,level4);
            vBoxCL.setAlignment(Pos.CENTER);
            pane2.getChildren().add(vBoxCL);
            primaryStage.setScene(sceneChooseLevel);
            primaryStage.show();

            level1.setOnMousePressed(event1 -> {
                canSave = false;
                startmusic.pause();
                mazeGame(primaryStage,1,2,1);
            });
            level2.setOnMousePressed(event1 -> {
                canSave = false;
                startmusic.pause();
                mazeGame(primaryStage,2,2,1);
            });
            level3.setOnMousePressed(event1 -> {
                canSave = false;
                startmusic.pause();
                mazeGame(primaryStage,3,2,1);
            });
            level4.setOnMousePressed(event1 -> {
                canSave = false;
                startmusic.pause();
                mazeGame(primaryStage,4,2,1);
            });
        });
    }

    public void durGame(Stage primaryStage,int no,int storyOrSand,int DurOrNot){
        Timeline showDur1 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/durup.png"));
            BorderPane mazepane1 = new BorderPane();
            GridPane maze1 = new GridPane();

            StackPane titlePane1 = new StackPane();
            Image title1 = new Image("Img/ghp.png");
            if(a == 2) title1 = new Image("Img/shp.png");
            titlePane1.getChildren().add(new ImageView(title1));
            Image[] phpTitle1 = new Image[14];
            for(int i = 0; i < 14; i++){
                phpTitle1[i] = new Image("Img/php-"+i+".png");
            }
            titlePane1.getChildren().add(new ImageView(phpTitle1[operation.php]));
            mazepane1.setTop(durup);
            mazepane1.setCenter(titlePane1);
            mazepane1.setBottom(maze1);
            print(a,maze1,no,1);
            print(a,titlePane1,no);

            Image maze11 = new Image("Img/maze1.gif");
            Scene maze1S = new Scene(mazepane1);
            mazepane1.getChildren().add(new ImageView(maze11));
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,1,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,1,1,0);
            });
        }));
        Timeline showDur2 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/durup.png"));
            BorderPane mazepane1 = new BorderPane();
            GridPane maze1 = new GridPane();

            StackPane titlePane1 = new StackPane();
            Image title1 = new Image("Img/ghp.png");
            if(a == 2) title1 = new Image("Img/shp.png");
            titlePane1.getChildren().add(new ImageView(title1));
            Image[] phpTitle1 = new Image[14];
            for(int i = 0; i < 14; i++){
                phpTitle1[i] = new Image("Img/php-"+i+".png");
            }
            titlePane1.getChildren().add(new ImageView(phpTitle1[operation.php]));
            mazepane1.setTop(durup);
            mazepane1.setCenter(titlePane1);
            mazepane1.setBottom(maze1);
            print(a,maze1,no,1);
            print(a,titlePane1,no);

            Image maze11 = new Image("Img/maze2.gif");
            Scene maze1S = new Scene(mazepane1);
            mazepane1.getChildren().add(new ImageView(maze11));
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,2,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,2,1,0);
            });
        }));
        Timeline showDur3 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/durup.png"));
            BorderPane mazepane1 = new BorderPane();
            GridPane maze1 = new GridPane();

            StackPane titlePane1 = new StackPane();
            Image title1 = new Image("Img/ghp.png");
            if(a == 2) title1 = new Image("Img/shp.png");
            titlePane1.getChildren().add(new ImageView(title1));
            Image[] phpTitle1 = new Image[14];
            for(int i = 0; i < 14; i++){
                phpTitle1[i] = new Image("Img/php-"+i+".png");
            }
            titlePane1.getChildren().add(new ImageView(phpTitle1[operation.php]));
            mazepane1.setTop(durup);
            mazepane1.setCenter(titlePane1);
            mazepane1.setBottom(maze1);
            print(a,maze1,no,1);
            print(a,titlePane1,no);

            Image maze11 = new Image("Img/maze3.gif");
            Scene maze1S = new Scene(mazepane1);
            mazepane1.getChildren().add(new ImageView(maze11));
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,3,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,3,1,0);
            });
        }));
        Timeline showDur4 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/durup.png"));
            BorderPane mazepane1 = new BorderPane();
            GridPane maze1 = new GridPane();

            StackPane titlePane1 = new StackPane();
            Image title1 = new Image("Img/ghp.png");
            if(a == 2) title1 = new Image("Img/shp.png");
            titlePane1.getChildren().add(new ImageView(title1));
            Image[] phpTitle1 = new Image[14];
            for(int i = 0; i < 14; i++){
                phpTitle1[i] = new Image("Img/php-"+i+".png");
            }
            titlePane1.getChildren().add(new ImageView(phpTitle1[operation.php]));
            mazepane1.setTop(durup);
            mazepane1.setCenter(titlePane1);
            mazepane1.setBottom(maze1);
            print(a,maze1,no,1);
            print(a,titlePane1,no);

            Image maze11 = new Image("Img/maze4.gif");
            Scene maze1S = new Scene(mazepane1);
            mazepane1.getChildren().add(new ImageView(maze11));
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                System.out.println("pointed");
                mazeGame(primaryStage,4,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,4,1,0);
            });
        }));
        /*Timeline showDur1 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/dur1.png"));
            if(a == 2) durup = new ImageView(new Image("Img/sdur1.png"));
            ImageView maze11 = new ImageView(new Image("Img/maze1.gif"));
            StackPane durPane = new StackPane();
            durPane.getChildren().addAll(durup,maze11);
            Scene maze1S = new Scene(durPane);
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,1,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,1,1,0);
            });
        }));
        Timeline showDur2 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/dur2.png"));
            if(a == 2) durup = new ImageView(new Image("Img/sdur2.png"));
            ImageView maze11 = new ImageView(new Image("Img/maze2.gif"));
            StackPane durPane = new StackPane();
            durPane.getChildren().addAll(durup,maze11);
            Scene maze1S = new Scene(durPane);
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,2,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,2,1,0);
            });
        }));
        Timeline showDur3 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/dur3.png"));
            if(a == 2) durup = new ImageView(new Image("Img/sdur3.png"));
            ImageView maze11 = new ImageView(new Image("Img/maze3.gif"));
            StackPane durPane = new StackPane();
            durPane.getChildren().addAll(durup,maze11);
            Scene maze1S = new Scene(durPane);
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,3,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,3,1,0);
            });
        }));
        Timeline showDur4 = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
            ImageView durup = new ImageView(new Image("Img/dur3.png"));
            if(a == 2) durup = new ImageView(new Image("Img/sdur3.png"));
            ImageView maze11 = new ImageView(new Image("Img/maze4.gif"));
            StackPane durPane = new StackPane();
            durPane.getChildren().addAll(durup,maze11);
            Scene maze1S = new Scene(durPane);
            primaryStage.setScene(maze1S);
            primaryStage.show();
            maze1S.setOnMousePressed(event1 -> {
                mazeGame(primaryStage,4,1,0);
            });
            maze1S.setOnKeyPressed(event1 -> {
                mazeGame(primaryStage,4,1,0);
            });
        }));*/
        if(no == 1)showDur1.play();
        else if(no == 2)showDur2.play();
        else if(no == 3)showDur3.play();
        else if(no == 4)showDur4.play();
    }

    public void mazeGame(Stage primaryStage,int no,int storyOrSand,int DurOrNot) {

        gameMusic.play();
        gameMusic.setCycleCount(Timeline.INDEFINITE);

        //迷宫界面
        MyMenuBar myMenuBar = new MyMenuBar(this);
        myMenuBar.setStage(primaryStage);
        BorderPane mazepane = new BorderPane();
        StackPane mazeAndOther = new StackPane();
        GridPane maze = new GridPane();
        Pane pMove = new Pane();
        Pane mMove = new Pane();
        Pane mMove2 = new Pane();
        Pane mMove3 = new Pane();
        mazeAndOther.getChildren().add(maze);//mazeAndOther = maze + mMove + pMove
        mazeAndOther.getChildren().add(pMove);
        mazeAndOther.getChildren().addAll(mMove,mMove2,mMove3);


        //血条界面
        StackPane titlePane = new StackPane();
        Image title = new Image("Img/ghp.png");
        if (a == 2) title = new Image("Img/shp.png");
        titlePane.getChildren().add(new ImageView(title));
        Image[] phpTitle = new Image[14];
        for (int i = 0; i < 14; i++) {
            phpTitle[i] = new Image("Img/php-" + i + ".png");
        }
        titlePane.getChildren().add(new ImageView(phpTitle[operation.php]));


        //全部游戏界面上中下
        mazepane.setTop(myMenuBar);
        mazepane.setCenter(titlePane);
        mazepane.setBottom(mazeAndOther);

        //打印开场
        printMap(maze,no);
        printP(pMove,KeyCode.L);
        //printM(mMove,mMove2,mMove3,no);
        print(a, titlePane, no);

        Scene mazeScene = new Scene(mazepane);///960,768
        primaryStage.setScene(mazeScene);
        primaryStage.show();

        mazeScene.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.W||event.getCode() == KeyCode.A||event.getCode() == KeyCode.S||event.getCode() == KeyCode.D)
            lastOption = event.getCode();
            if (event.getCode() == KeyCode.H) {
                showHelpScene(a);
            }
            if (event.getCode() == KeyCode.C) {
                showScoreScene();
            }
            if (event.getCode() == KeyCode.K) {
                playKillMusic();
            }
            if (event.getCode() == KeyCode.P) {
                playPickMusic();
            }
            operation.heroMove(event.getCode(), no);
            if (operation.pPos2[0] != pPos[0] || operation.pPos2[1] != pPos[1]) {
                playWalkMusic();
            }

            printMap(maze,no);
            if(event.getCode() == KeyCode.K){
                printPK(pMove,lastOption);
            }else {
                printP(pMove,event.getCode());
            }
            print(a, titlePane, no);


            if (operation.isKilled) {
                if (!this.b) {
                    showDieScene(mazepane, primaryStage);
                }
            }
            if (pPos[0] == 13 && pPos[1] == 19) {
                level++;
                refresh.stop();
                if (storyOrSand == 1) {
                    operation.ifContinue[no] = true;
                    for (int i = 0; i < operation.treasure2.length; i++) {
                        for (int j = 0; j < operation.treasure2[0].length; j++) {
                            operation.treasure[i][j] = operation.treasure2[i][j];
                        }
                    }
                    for (int i = 0; i < operation.monster2.length; i++) {
                        for (int j = 0; j < operation.monster2[0].length; j++) {
                            operation.monster[i][j] = operation.monster2[i][j];
                        }
                    }
                    for (int i = 0; i < operation.addHp.length; i++) {
                        for (int j = 0; j < operation.addHp[0].length; j++) {
                            operation.addHp[i][j] = operation.addHp2[i][j];
                        }
                    }
                    pPos[0] = 1;
                    pPos[1] = 0;
                    operation.size = 0;
                    operation.record[0][0] = 1;
                    operation.record[0][1] = 0;
                    this.b = false;
                    if (no < 4) {
                            durGame(primaryStage, no + 1, 1, 1);
                    } else if (no == 4 || level == 5) {
                        gameMusic.stop();
                        showFinishSceneStory(primaryStage);
                    }
                } else if (storyOrSand == 2) {
                    showFinishSceneSandbox(primaryStage);
                }
            }
        });


        monMoveAni = new Timeline(new KeyFrame(Duration.seconds(0.7), event -> {
            if (operation.isKilled) {
                if (!this.b) {
                    showDieScene(mazepane, primaryStage);
                }
            }
            if (!operation.isKilled) {
                    if(operation.monster[0][0] < 0) mazeAndOther.getChildren().removeAll(mMove);
                    if(operation.monster[1][0] < 0) mazeAndOther.getChildren().removeAll(mMove2);
                    if(operation.monster[2][0] < 0) mazeAndOther.getChildren().removeAll(mMove3);

                operation.monsterMove(pPos, no);
                printM(mMove,mMove2,mMove3,no);
                print(a, titlePane, no);
            }
        }));
        if(no != 4){
            monMoveAni.stop();
        }
        if(no == 4){
            monMoveAni.setCycleCount(Timeline.INDEFINITE);
            monMoveAni.play();
        }

        refresh = new Timeline(new KeyFrame(Duration.seconds(0.1),event -> {
            if(isThemeChanged != 0){
                printMap(maze,no);
                print(a,maze,no,3);
                if(no == 3) printM(mMove,mMove2,mMove3,no);
                print(a,titlePane,no);
                isThemeChanged = 0;
            }
        }));
        refresh.setCycleCount(Timeline.INDEFINITE);
        refresh.play();
    }

    public void showFinishSceneSandbox(Stage primaryStage){
        gameMusic.stop();
        winMusic.play();
        winMusic.setCycleCount(Timeline.INDEFINITE);
        StackPane finishPane = new StackPane(new ImageView("/Img/beijing.jpg"));
        Scene finishScene = new Scene(finishPane);
        VBox vBox = new VBox(200);
        Text text = new Text("You Win!");
        text.setFont(Font.font("宋体",FontWeight.BOLD,70));
        Button button = new Button("结束");
        vBox.getChildren().addAll(text,button);
        vBox.setAlignment(Pos.CENTER);
        finishPane.getChildren().add(vBox);
        primaryStage.setScene(finishScene);
        primaryStage.show();

        button.setOnMousePressed(event -> System.exit(0));
    }

    public void showFinishSceneStory(Stage primaryStage){
        b = true;
        StackPane backPane = new StackPane(new ImageView("/Img/beijing.jpg"));
        VBox vBoxName = new VBox(40);
        vBoxName.setPadding(new Insets(150,200,200,200));
        Text score = new Text(""+(int)((((3*operation.treasurecount)+(10*operation.monstercount)+(3*operation.php)+(7*operation.dps))/(0.2*(operation.stepcount+5)))*100)+"!");
        score.setFont(Font.font("宋体",FontWeight.BOLD,70));

        Text yourName = new Text("你的名字");
        yourName.setFont(Font.font("宋体",FontWeight.BOLD,30));

        TextField nameTF = new TextField();
        //nameTF.setPrefColumnCount(1);

        Button button = new Button("确认");

        vBoxName.getChildren().addAll(score,yourName,nameTF,button);
        vBoxName.setAlignment(Pos.CENTER);
        backPane.getChildren().add(vBoxName);

        Scene finishSceneStory = new Scene(backPane);
        primaryStage.setScene(finishSceneStory);
        primaryStage.show();


        button.setOnMousePressed(event -> {
            operation.name = nameTF.getText();
            if(nameTF.getText().length() == 0){
                operation.name = "游客";
            }
            try {
                showRankScene(primaryStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public void playKillMusic(){
        String killMusicString = TestGame.class.getResource("Song/attack.mp3").toString();
        MediaPlayer killMusic = new MediaPlayer(new Media(killMusicString));
        killMusic.play();
    }

    public void playWalkMusic(){
        String walkMusicString = TestGame.class.getResource("Song/gfoot.mp3").toString();
        if(a == 2)walkMusicString = TestGame.class.getResource("Song/sfoot.mp3").toString();
        MediaPlayer walkMusic = new MediaPlayer(new Media(walkMusicString));
        walkMusic.play();
    }

    public void playPickMusic(){
        String pickMusicString = TestGame.class.getResource("Song/pick.mp3").toString();
        MediaPlayer pickMusic = new MediaPlayer(new Media(pickMusicString));
        pickMusic.play();
    }

    public void playFailMusic(){
        String failMusicString = TestGame.class.getResource("Song/fail.mp3").toString();
        MediaPlayer failMusic = new MediaPlayer(new Media(failMusicString));
        failMusic.play();
    }

    public void showDieScene(BorderPane mazepane,Stage primaryStage){
        playFailMusic();
        this.b = true;
        StackPane diePane = new StackPane();
        diePane.getChildren().add(mazepane);
        diePane.getChildren().add(new ImageView("Img/die2.png"));
        //HBox hBox = new HBox(50);
        VBox vBox = new VBox(300);
        vBox.setAlignment(Pos.CENTER);
        Text text = new Text("   ");
        //hBox.setPadding(new Insets(500));
        Button button = new Button("结束游戏");
        vBox.getChildren().addAll(text,button);
        diePane.getChildren().add(vBox);

        Scene dieScene = new Scene(diePane);
        primaryStage.setScene(dieScene);
        primaryStage.show();

        button.setOnMousePressed(event -> System.exit(0));
    }

    public void showHelpScene(int a){
        //帮助界面
        StackPane helpPane = new StackPane();
        Scene helpScene = new Scene(helpPane);
        Image help = new Image("Img/ghelp.png");
        if(a == 2) help = new Image("Img/shelp.png");

        helpPane.getChildren().add(new ImageView(help));


        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.setTitle("帮助界面");
        helpStage.show();

        helpScene.setOnMousePressed(event -> {
            helpStage.close();
        });
    }

    public void showScoreScene(){
        //得分界面
        StackPane scorePane = new StackPane();
        Scene scoreScene = new Scene(scorePane);
        Image setting = new Image("Img/background.png");
        scorePane.getChildren().add(new ImageView(setting));
        Text score = new Text(20,20,"当前拾取宝物  "+operation.treasurecount+"\n\n当前杀死追兵  "+operation.monstercount+"\n\n当前步数    "+operation.stepcount+"\n\n当前得分    "+(int)((((3*operation.treasurecount)+(10*operation.monstercount)+(3*operation.php)+(7*operation.dps))/(0.2*(operation.stepcount+5)))*100));
        scorePane.getChildren().add(score);
        score.setFont(Font.font("宋体",FontWeight.BOLD,20));

        Stage scoreStage = new Stage();
        scoreStage.setTitle("得分界面");
        scoreStage.setScene(scoreScene);
        scoreStage.show();

        scoreScene.setOnMousePressed(event -> {
            scoreStage.close();
        });
    }

    public void showRankScene(Stage primaryStage) throws FileNotFoundException {
        java.io.File readnumber = new java.io.File("number.txt");
        Scanner readnumberS = new Scanner(readnumber);
        int n = readnumberS.nextInt();
        readnumberS.close();
        PrintWriter readnumberP = new PrintWriter(readnumber);
        readnumberP.print(n+1);
        readnumberP.close();

        java.io.File nameNoRepeat = new java.io.File("scores.txt");
        Scanner nameNoRep = null;
        try {
            nameNoRep = new Scanner(nameNoRepeat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //String[] name0 = new String[1];
        while (nameNoRep.hasNext()) {
            String name0 = nameNoRep.next();
            while (name0.equals(operation.name)) {
                //JOptionPane.showMessageDialog(null,"用户名已存在!");
                operation.name += ""+(n+1);
                Text nameAlready = new Text("用户名已存在!");
                nameAlready.setFont(Font.font("宋体",15));
                //vBoxName.getChildren().add(nameAlready);

            }
            while ("".equals(operation.name)){
                Text nameNoVoid = new Text("用户名不可为空!");
                nameNoVoid.setFont(Font.font("宋体",15));
                //vBoxName.getChildren().add(nameNoVoid);
            }
            int rubbish = nameNoRep.nextInt();
            operation.playNumber++;
        }
        nameNoRep.close();


        Scanner nameRecord = null;
        try {
            nameRecord = new Scanner(nameNoRepeat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] namePre = new String[operation.playNumber + 1];//用户名数组
        for (int i = 0; i < operation.playNumber; i++) {
            namePre[i] = nameRecord.next();
            int rubbish = nameRecord.nextInt();
        }
        nameRecord.close();
        namePre[operation.playNumber] = operation.name;

        Scanner scoresPrevious = null;
        try {
            scoresPrevious = new Scanner(nameNoRepeat);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int[] scorePrevious = new int[operation.playNumber + 1];//分数数组
        for (int i = 0; i < operation.playNumber; i++) {
            String rubbish = scoresPrevious.next();
            scorePrevious[i] = scoresPrevious.nextInt();
        }
        scoresPrevious.close();
        scorePrevious[operation.playNumber] = (int)((((3*operation.treasurecount)+(10*operation.monstercount)+(3*operation.php)+(7*operation.dps))/(0.2*(operation.stepcount+5)))*100);

        //写入名字和分数
        BufferedWriter scores = null;
        try {
            scores = new BufferedWriter(new FileWriter(new File("scores.txt"), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            scores.append(operation.name + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            scores.append((int)((((3*operation.treasurecount)+(10*operation.monstercount)+(3*operation.php)+(7*operation.dps))/(0.2*(operation.stepcount+5)))*100) + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            scores.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] scorePaixu = new int[operation.playNumber + 1];
        System.arraycopy(scorePrevious, 0, scorePaixu, 0, operation.playNumber);
        scorePaixu[operation.playNumber] = (int)((((3*operation.treasurecount)+(10*operation.monstercount)+(3*operation.php)+(7*operation.dps))/(0.2*(operation.stepcount+5)))*100);
        Arrays.sort(scorePaixu);
        int[] scorePaixu2 = new int[scorePaixu.length]; //分数从大到小
        for (int j = 0; j < scorePaixu.length; j++) {
            scorePaixu2[j] = scorePaixu[scorePaixu.length - j - 1];
        }

        int rank = 1;
        int x = 0;
        Text paihangbang = new Text("排行榜");
        Text content = new Text();
        String content2 = "";
        //System.out.println("\n\n          排行榜      \n");
        for (int k = 0; k < operation.playNumber + 1; k++) {//scorePaixu2[]
            if (k + 1 > 10) break;//////////
            if (k >= 1 && scorePaixu2[k] == scorePaixu2[k - 1]) continue;
            int num = 0;
            for (int j = 0; j < operation.playNumber + 1; j++) {//scorePrevious[]
                if (scorePrevious[j] == scorePaixu2[k]) {
                    num = j;
                    String namename = String.format("%10s",namePre[num]);
                    content2 += "第"+( k + 1)+"名：    "+scorePrevious[num]+"     "+namename+"\n";
                    // content[x++].setText("第"+( k + 1)+"名：  "+namePre[num]+"  "+scorePrevious[num]+"\n");
                }
            }
        }
        content.setText(content2);
        for (int k = 0; k < operation.playNumber + 1; k++) {
            if (k >= 1 && scorePaixu2[k] == scorePaixu2[k - 1]) continue;
            if (scorePrevious[operation.playNumber] == scorePaixu2[k]) rank = k + 1;
        }
        Text ifShangbang = new Text();
        if (rank <= 10) {
            ifShangbang.setText("恭喜你！你上榜了");
            //playWinMusic();
            winMusic.play();
            winMusic.setCycleCount(Timeline.INDEFINITE);
        }
        else {
            ifShangbang.setText("很遗憾，你没有上榜！");
            playFailMusic();
        }
        Text yourRank = new Text("你的排名：   " + rank);
        Button exit = new Button("结束游戏");
        Button reborn = new Button("再来一局");
        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(exit,reborn);
        VBox vBox2 = new VBox(20);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(ifShangbang,paihangbang,content,yourRank,hBox);
        StackPane backPane2 = new StackPane(new ImageView("/Img/beijing.jpg"));
        backPane2.getChildren().add(vBox2);
        Scene paihangbangScene = new Scene(backPane2);
        primaryStage.setScene(paihangbangScene);
        primaryStage.show();

        exit.setOnMousePressed(event1 -> System.exit(0));
        reborn.setOnMousePressed(event -> {
            clear();
            //primaryStage.close();
            winMusic.stop();
            monMoveAni.stop();
            durGame(primaryStage,1,1,1);
        });

    }

    public void clear(){
        //operation.ifContinue[no] = true;
        for (int i = 0; i < operation.treasure2.length; i++) {
            for (int j = 0; j < operation.treasure2[0].length; j++) {
                operation.treasure[i][j] = operation.treasure2[i][j];
            }
        }
        for (int i = 0; i < operation.monster2.length; i++) {
            for (int j = 0; j < operation.monster2[0].length; j++) {
                operation.monster[i][j] = operation.monster2[i][j];
            }
        }
        for (int i = 0; i < operation.addHp.length; i++){
            for(int j = 0; j < operation.addHp[0].length; j++){
                operation.addHp[i][j] = operation.addHp2[i][j];
            }
        }
        operation.treasurecount = 0;
        operation.monstercount = 0;
        operation.stepcount = 0;
        operation.php = 2;
        operation.dps = 1;
        pPos[0] = 1;
        pPos[1] = 0;
        operation.size = 0;
        operation.isKilled = false;
        operation.record[0][0] = 1;
        operation.record[0][1] = 0;
        this.b = false;
    }

    public void printPK(Pane pMove,KeyCode x) {
        pMove.getChildren().removeAll(showAfter, show);
        show = new ImageView();
        pMove.getChildren().add(show);

        Image[][] move = new Image[4][4];
        for (int j = 0; j < 4; j++) {
            if (j == 0) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/melee-0-" + (i + 1) + ".png");
                }
            }
            if (j == 1) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/melee-1-" + (i + 1) + ".png");
                }
            }
            if (j == 2) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/melee-2-" + (i + 1) + ".png");
                }
            }
            if (j == 3) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/melee-3-" + (i + 1) + ".png");
                }
            }
        }
        if (x == KeyCode.W) showAfter = new ImageView(new Image("Img/hero-back-1.png"));
        if (x == KeyCode.D) showAfter = new ImageView(new Image("Img/hero-right-1.png"));
        if (x == KeyCode.S) showAfter = new ImageView(new Image("Img/hero-face-1.png"));
        if (x == KeyCode.A) showAfter = new ImageView(new Image("Img/hero-left-1.png"));
        showAfter.setX(pPos[1] * 48);
        showAfter.setY(pPos[0] * 48);

        show.setX(operation.pPos2[1] * 48);
        show.setY(operation.pPos2[0] * 48);
        Timeline heroAni = new Timeline(new KeyFrame(Duration.millis(40), event -> {
            if (x == KeyCode.W) {//上
                if (show.getImage() == move[0][0]) {
                    show.setImage(move[0][1]);
                } else if (show.getImage() == move[0][1]) {
                    show.setImage(move[0][2]);
                } else if (show.getImage() == move[0][2]) {
                    show.setImage(move[0][3]);
                } else {
                    show.setImage(move[0][0]);
                }
            } else if (x == KeyCode.D) {//右
                if (show.getImage() == move[1][0]) {
                    show.setImage(move[1][1]);
                } else if (show.getImage() == move[1][1]) {
                    show.setImage(move[1][2]);
                } else if (show.getImage() == move[1][2]) {
                    show.setImage(move[1][3]);
                } else {
                    show.setImage(move[1][0]);
                }
            } else if (x == KeyCode.S) {//下
                if (show.getImage() == move[2][0]) {
                    show.setImage(move[2][1]);
                } else if (show.getImage() == move[2][1]) {
                    show.setImage(move[2][2]);
                } else if (show.getImage() == move[2][2]) {
                    show.setImage(move[2][3]);
                } else {
                    show.setImage(move[2][0]);
                }
            } else if (x == KeyCode.A) {     //左
                if (show.getImage() == move[3][0]) {
                    show.setImage(move[3][1]);
                } else if (show.getImage() == move[3][1]) {
                    show.setImage(move[3][2]);
                } else if (show.getImage() == move[3][2]) {
                    show.setImage(move[3][3]);
                } else {
                    show.setImage(move[3][0]);
                }
            }
        }));
        heroAni.setOnFinished(event -> {
            pMove.getChildren().removeAll(show);
            try {
                pMove.getChildren().add(showAfter);
            } catch (IllegalArgumentException e) {

            }
        });

        heroAni.setCycleCount(4);
        heroAni.play();
    }

    public void printP(Pane pMove,KeyCode x){
        //pMove = new Pane();
        pMove.getChildren().removeAll(showAfter,show);
        show = new ImageView();
        pMove.getChildren().add(show);

        Image[][] move = new Image[4][4];
        for (int j = 0; j < 4; j++) {
            if (j == 0) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/hero-back-" + (i + 1) + ".png");
                }
            }
            if (j == 1) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/hero-right-" + (i + 1) + ".png");
                }
            }
            if (j == 2) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/hero-face-" + (i + 1) + ".png");
                }
            }
            if (j == 3) {
                for (int i = 0; i < 4; i++) {
                    move[j][i] = new Image("Img/hero-left-" + (i + 1) + ".png");
                }
            }
        }

        showAfter = new ImageView(move[2][0]);
            if (x == KeyCode.W) {
                showAfter.setImage(move[0][0]);
                showAfter.setX(pPos[1] * 48);
                showAfter.setY(pPos[0] * 48);
            }
            if (x == KeyCode.D) {
                showAfter.setImage(move[1][0]);
                showAfter.setX(pPos[1] * 48);
                showAfter.setY(pPos[0] * 48);
            }
            if (x == KeyCode.S) {
                showAfter.setImage(move[2][0]);
                showAfter.setX(pPos[1] * 48);
                showAfter.setY(pPos[0] * 48);
            }
            if (x == KeyCode.A) {
                showAfter.setImage(move[3][0]);
                showAfter.setX(pPos[1] * 48);
                showAfter.setY(pPos[0] * 48);
            }

            show.setX(operation.pPos2[1] * 48);
            show.setY(operation.pPos2[0] * 48);
        if(x == KeyCode.B){
            show = new ImageView(move[2][0]);
            pMove.getChildren().add(show);
            if(operation.pPos2[0] == pPos[0] && operation.pPos2[1] == pPos[1]){
                show.setX(operation.pPos2[1] * 48);
                show.setY(operation.pPos2[0] * 48);
            }else {
                PathTransition backPT = new PathTransition(Duration.millis(240), new Line(operation.pPos2[1] * 48 + 24, operation.pPos2[0] * 48 + 24, pPos[1] * 48 + 24, pPos[0] * 48 + 24), show);
                backPT.play();
            }
        }else {
            Timeline heroAni = new Timeline(new KeyFrame(Duration.millis(30), event -> {
                if (x == KeyCode.W) {//上
                    if (show.getImage() == move[0][0]) {
                        show.setImage(move[0][1]);
                        show.setY(show.getY() - 6);
                    } else if (show.getImage() == move[0][1]) {
                        show.setImage(move[0][2]);
                        show.setY(show.getY() - 6);
                    } else if (show.getImage() == move[0][2]) {
                        show.setImage(move[0][3]);
                        show.setY(show.getY() - 6);
                    } else {
                        show.setImage(move[0][0]);
                        show.setY(show.getY() - 6);
                    }
                } else if (x == KeyCode.D) {//右
                    if (show.getImage() == move[1][0]) {
                        show.setImage(move[1][1]);
                        show.setX(show.getX() + 6);
                    } else if (show.getImage() == move[1][1]) {
                        show.setImage(move[1][2]);
                        show.setX(show.getX() + 6);
                    } else if (show.getImage() == move[1][2]) {
                        show.setImage(move[1][3]);
                        show.setX(show.getX() + 6);
                    } else {
                        show.setImage(move[1][0]);
                        show.setX(show.getX() + 6);
                    }
                } else if (x == KeyCode.S) {//下
                    if (show.getImage() == move[2][0]) {
                        show.setImage(move[2][1]);
                        show.setY(show.getY() + 6);
                    } else if (show.getImage() == move[2][1]) {
                        show.setImage(move[2][2]);
                        show.setY(show.getY() + 6);
                    } else if (show.getImage() == move[2][2]) {
                        show.setImage(move[2][3]);
                        show.setY(show.getY() + 6);
                    } else {
                        show.setImage(move[2][0]);
                        show.setY(show.getY() + 6);
                    }
                } else if (x == KeyCode.A) {//左
                    if (show.getImage() == move[3][0]) {
                        show.setImage(move[3][1]);
                        show.setX(show.getX() - 6);
                    } else if (show.getImage() == move[3][1]) {
                        show.setImage(move[3][2]);
                        show.setX(show.getX() - 6);
                    } else if (show.getImage() == move[3][2]) {
                        show.setImage(move[3][3]);
                        show.setX(show.getX() - 6);
                    } else {
                        show.setImage(move[3][0]);
                        show.setX(show.getX() - 6);
                    }
                }
            }));
            heroAni.setOnFinished(event -> {
                pMove.getChildren().removeAll(show);
                try {
                    pMove.getChildren().add(showAfter);
                } catch (IllegalArgumentException e) {

                }
            });
            if ((operation.pPos2[0] != pPos[0] || operation.pPos2[1] != pPos[1])) {
                if(x == KeyCode.L){
                    showAfter.setImage(move[1][0]);
                    showAfter.setX(pPos[1] * 48);
                    showAfter.setY(pPos[0] * 48);
                    pMove.getChildren().add(showAfter);
                }else {
                    heroAni.setCycleCount(8);
                    heroAni.play();
                }
            } else {
                if (x == KeyCode.W) {
                    showAfter.setImage(move[0][0]);
                    showAfter.setX(pPos[1] * 48);
                    showAfter.setY(pPos[0] * 48);
                }
                if (x == KeyCode.D) {
                    showAfter.setImage(move[1][0]);
                    showAfter.setX(pPos[1] * 48);
                    showAfter.setY(pPos[0] * 48);
                }
                if (x == KeyCode.S) {
                    showAfter.setImage(move[2][0]);
                    showAfter.setX(pPos[1] * 48);
                    showAfter.setY(pPos[0] * 48);
                }
                if (x == KeyCode.A) {
                    showAfter.setImage(move[3][0]);
                    showAfter.setX(pPos[1] * 48);
                    showAfter.setY(pPos[0] * 48);
                } else {
                    showAfter.setImage(move[1][0]);
                    showAfter.setX(pPos[1] * 48);
                    showAfter.setY(pPos[0] * 48);
                }
                pMove.getChildren().add(showAfter);
            }
        }
    }

    public void printM(Pane mMove,Pane mMove2,Pane mMove3,int no){
        //mMove = new Pane();
        //mMove2 = new Pane();
        //mMove3 = new Pane();
        mMove.getChildren().removeAll(mshow);
        mMove2.getChildren().removeAll(mshow2);
        mMove3.getChildren().removeAll(mshow3);
        ImageView mshow = new ImageView();
        ImageView mshow2 = new ImageView();
        ImageView mshow3 = new ImageView();
        mMove.getChildren().addAll(mshow);
        mMove2.getChildren().addAll(mshow2);
        mMove3.getChildren().addAll(mshow3);
        Image[][] mmove = new Image[4][4];
        for (int j = 0; j < 4; j++) {
            if (j == 0) {
                for (int i = 0; i < 4; i++) {
                    mmove[j][i] = new Image("Img/monster-back-" + (i + 1) + ".png");
                }
            }
            if (j == 1) {
                for (int i = 0; i < 4; i++) {
                    mmove[j][i] = new Image("Img/monster-right-" + (i + 1) + ".png");
                }
            }
            if (j == 2) {
                for (int i = 0; i < 4; i++) {
                    mmove[j][i] = new Image("Img/monster-face-" + (i + 1) + ".png");
                }
            }
            if (j == 3) {
                for (int i = 0; i < 4; i++) {
                    mmove[j][i] = new Image("Img/monster-left-" + (i + 1) + ".png");
                }
            }
        }
        //mshow = new ImageView(mmove[2][0]);
        //mshow2 = new ImageView(mmove[2][0]);
        //mshow3 = new ImageView(mmove[2][0]);
        if(no != 4){
            mshow.setImage(mmove[2][0]);
            mshow.setX(operation.monster[0][1]*48);
            mshow.setY(operation.monster[0][0]*48);
            mshow2.setImage(mmove[2][0]);
            mshow2.setX(operation.monster[1][1]*48);
            mshow2.setY(operation.monster[1][0]*48);
            mshow3.setImage(mmove[2][0]);
            mshow3.setX(operation.monster[2][1]*48);
            mshow3.setY(operation.monster[2][0]*48);
        }else {
            mshow.setX(operation.result[0][3] * 48);
            mshow.setY(operation.result[0][2] * 48);

            mshow2.setX(operation.result[1][3] * 48);
            mshow2.setY(operation.result[1][2] * 48);

            mshow3.setX(operation.result[2][3] * 48);
            mshow3.setY(operation.result[2][2] * 48);

            Timeline monAni = new Timeline(new KeyFrame(Duration.millis(87.5), event -> {
                if (operation.mondir[0] == 1) {//上
                    if (mshow.getImage() == mmove[0][0]) {
                        mshow.setImage(mmove[0][1]);
                        mshow.setY(mshow.getY() - 6);
                    } else if (mshow.getImage() == mmove[0][1]) {
                        mshow.setImage(mmove[0][2]);
                        mshow.setY(mshow.getY() - 6);
                    } else if (mshow.getImage() == mmove[0][2]) {
                        mshow.setImage(mmove[0][3]);
                        mshow.setY(mshow.getY() - 6);
                    } else {
                        mshow.setImage(mmove[0][0]);
                        mshow.setY(mshow.getY() - 6);
                    }
                } else if (operation.mondir[0] == 2) {//右
                    if (mshow.getImage() == mmove[1][0]) {
                        mshow.setImage(mmove[1][1]);
                        mshow.setX(mshow.getX() + 6);
                    } else if (mshow.getImage() == mmove[1][1]) {
                        mshow.setImage(mmove[1][2]);
                        mshow.setX(mshow.getX() + 6);
                    } else if (mshow.getImage() == mmove[1][2]) {
                        mshow.setImage(mmove[1][3]);
                        mshow.setX(mshow.getX() + 6);
                    } else {
                        mshow.setImage(mmove[1][0]);
                        mshow.setX(mshow.getX() + 6);
                    }
                } else if (operation.mondir[0] == 3) {//下
                    if (mshow.getImage() == mmove[2][0]) {
                        mshow.setImage(mmove[2][1]);
                        mshow.setY(mshow.getY() + 6);
                    } else if (mshow.getImage() == mmove[2][1]) {
                        mshow.setImage(mmove[2][2]);
                        mshow.setY(mshow.getY() + 6);
                    } else if (mshow.getImage() == mmove[2][2]) {
                        mshow.setImage(mmove[2][3]);
                        mshow.setY(mshow.getY() + 6);
                    } else {
                        mshow.setImage(mmove[2][0]);
                        mshow.setY(mshow.getY() + 6);
                    }
                } else if (operation.mondir[0] == 4) {//左
                    if (mshow.getImage() == mmove[3][0]) {
                        mshow.setImage(mmove[3][1]);
                        mshow.setX(mshow.getX() - 6);
                    } else if (mshow.getImage() == mmove[3][1]) {
                        mshow.setImage(mmove[3][2]);
                        mshow.setX(mshow.getX() - 6);
                    } else if (mshow.getImage() == mmove[3][2]) {
                        mshow.setImage(mmove[3][3]);
                        mshow.setX(mshow.getX() - 6);
                    } else {
                        mshow.setImage(mmove[3][0]);
                        mshow.setX(mshow.getX() - 6);
                    }
                }
            }));
            monAni.setCycleCount(8);
            if(operation.monster[0][0] > 0) {
                monAni.play();
                monAni.setOnFinished(event -> {
                    mMove.getChildren().removeAll(mshow);
                });
            }

            Timeline monAni2 = new Timeline(new KeyFrame(Duration.millis(87.5), event -> {
                if (operation.mondir[1] == 1) {//上
                    if (mshow2.getImage() == mmove[0][0]) {
                        mshow2.setImage(mmove[0][1]);
                        mshow2.setY(mshow2.getY() - 6);
                    } else if (mshow2.getImage() == mmove[0][1]) {
                        mshow2.setImage(mmove[0][2]);
                        mshow2.setY(mshow2.getY() - 6);
                    } else if (mshow2.getImage() == mmove[0][2]) {
                        mshow2.setImage(mmove[0][3]);
                        mshow2.setY(mshow2.getY() - 6);
                    } else {
                        mshow2.setImage(mmove[0][0]);
                        mshow2.setY(mshow2.getY() - 6);
                    }
                } else if (operation.mondir[1] == 2) {//右
                    if (mshow2.getImage() == mmove[1][0]) {
                        mshow2.setImage(mmove[1][1]);
                        mshow2.setX(mshow2.getX() + 6);
                    } else if (mshow2.getImage() == mmove[1][1]) {
                        mshow2.setImage(mmove[1][2]);
                        mshow2.setX(mshow2.getX() + 6);
                    } else if (mshow2.getImage() == mmove[1][2]) {
                        mshow2.setImage(mmove[1][3]);
                        mshow2.setX(mshow2.getX() + 6);
                    } else {
                        mshow2.setImage(mmove[1][0]);
                        mshow2.setX(mshow2.getX() + 6);
                    }
                } else if (operation.mondir[1] == 3) {//下
                    if (mshow2.getImage() == mmove[2][0]) {
                        mshow2.setImage(mmove[2][1]);
                        mshow2.setY(mshow2.getY() + 6);
                    } else if (mshow2.getImage() == mmove[2][1]) {
                        mshow2.setImage(mmove[2][2]);
                        mshow2.setY(mshow2.getY() + 6);
                    } else if (mshow2.getImage() == mmove[2][2]) {
                        mshow2.setImage(mmove[2][3]);
                        mshow2.setY(mshow2.getY() + 6);
                    } else {
                        mshow2.setImage(mmove[2][0]);
                        mshow2.setY(mshow2.getY() + 6);
                    }
                } else if (operation.mondir[1] == 4) {//左
                    if (mshow2.getImage() == mmove[3][0]) {
                        mshow2.setImage(mmove[3][1]);
                        mshow2.setX(mshow2.getX() - 6);
                    } else if (mshow2.getImage() == mmove[3][1]) {
                        mshow2.setImage(mmove[3][2]);
                        mshow2.setX(mshow2.getX() - 6);
                    } else if (mshow2.getImage() == mmove[3][2]) {
                        mshow2.setImage(mmove[3][3]);
                        mshow2.setX(mshow2.getX() - 6);
                    } else {
                        mshow2.setImage(mmove[3][0]);
                        mshow2.setX(mshow2.getX() - 6);
                    }
                }
            }));
            monAni2.setCycleCount(8);
            if(operation.monster[1][0] > 0) {
                monAni2.play();
                monAni2.setOnFinished(event -> {
                    mMove2.getChildren().removeAll(mshow2);
                });
            }


            Timeline monAni3 = new Timeline(new KeyFrame(Duration.millis(87.5), event -> {
                if (operation.mondir[2] == 1) {//上
                    if (mshow3.getImage() == mmove[0][0]) {
                        mshow3.setImage(mmove[0][1]);
                        mshow3.setY(mshow3.getY() - 6);
                    } else if (mshow3.getImage() == mmove[0][1]) {
                        mshow3.setImage(mmove[0][2]);
                        mshow3.setY(mshow3.getY() - 6);
                    } else if (mshow3.getImage() == mmove[0][2]) {
                        mshow3.setImage(mmove[0][3]);
                        mshow3.setY(mshow3.getY() - 6);
                    } else {
                        mshow3.setImage(mmove[0][0]);
                        mshow3.setY(mshow3.getY() - 6);
                    }
                } else if (operation.mondir[2] == 2) {//右
                    if (mshow3.getImage() == mmove[1][0]) {
                        mshow3.setImage(mmove[1][1]);
                        mshow3.setX(mshow3.getX() + 6);
                    } else if (mshow3.getImage() == mmove[1][1]) {
                        mshow3.setImage(mmove[1][2]);
                        mshow3.setX(mshow3.getX() + 6);
                    } else if (mshow3.getImage() == mmove[1][2]) {
                        mshow3.setImage(mmove[1][3]);
                        mshow3.setX(mshow3.getX() + 6);
                    } else {
                        mshow3.setImage(mmove[1][0]);
                        mshow3.setX(mshow3.getX() + 6);
                    }
                } else if (operation.mondir[2] == 3) {//下
                    if (mshow3.getImage() == mmove[2][0]) {
                        mshow3.setImage(mmove[2][1]);
                        mshow3.setY(mshow3.getY() + 6);
                    } else if (mshow3.getImage() == mmove[2][1]) {
                        mshow3.setImage(mmove[2][2]);
                        mshow3.setY(mshow3.getY() + 6);
                    } else if (mshow3.getImage() == mmove[2][2]) {
                        mshow3.setImage(mmove[2][3]);
                        mshow3.setY(mshow3.getY() + 6);
                    } else {
                        mshow3.setImage(mmove[2][0]);
                        mshow3.setY(mshow3.getY() + 6);
                    }
                } else if (operation.mondir[2] == 4) {//左
                    if (mshow3.getImage() == mmove[3][0]) {
                        mshow3.setImage(mmove[3][1]);
                        mshow3.setX(mshow3.getX() - 6);
                    } else if (mshow3.getImage() == mmove[3][1]) {
                        mshow3.setImage(mmove[3][2]);
                        mshow3.setX(mshow3.getX() - 6);
                    } else if (mshow3.getImage() == mmove[3][2]) {
                        mshow3.setImage(mmove[3][3]);
                        mshow3.setX(mshow3.getX() - 6);
                    } else {
                        mshow3.setImage(mmove[3][0]);
                        mshow3.setX(mshow3.getX() - 6);
                    }
                }
            }));
            monAni3.setCycleCount(8);
            if(operation.monster[2][0] > 0) {
                monAni3.play();
                monAni3.setOnFinished(event -> {
                    mMove3.getChildren().removeAll(mshow3);
                });
            }
        }
    }

    public void printMap(GridPane pane,int no){
        Image space = new Image("Img/gspace.png");
        Image wall = new Image("Img/gwall.png");
        Image end = new Image("Img/gend.png");
        Image footprint = new Image("Img/gfootprint.png");
        Image treasure = new Image("Img/gtreasure.png");
        Image monsterf1 = new Image("Img/monster-face-1.png");
        Image add = new Image("Img/gadd.png");
        if(this.a == 2){
            space = new Image("Img/sspace.png");
            wall = new Image("Img/swall.png");
            end = new Image("Img/send.png");
            footprint = new Image("Img/sfootprint.png");
            treasure = new Image("Img/streasure.png");
            add = new Image("Img/sadd.png");
        }
        pane.setPadding(new Insets(0, 0, 0, 0));
        pane.setVgap(0);
        pane.setHgap(0);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1)
                    pane.add(new ImageView(wall), j, i);
                if (map[i][j] == 0)
                    pane.add(new ImageView(space), j, i);
                if (map[i][j] == 9)
                    pane.add(new ImageView(end), j, i);
                if (operation.size - 1 >= 0 && i == operation.record[operation.size - 1][0] && j == operation.record[operation.size - 1][1]) {
                    pane.add(new ImageView(footprint), j, i);
                }
                if (operation.size - 2 >= 0 && i == operation.record[operation.size - 2][0] && j == operation.record[operation.size - 2][1]) {
                    pane.add(new ImageView(footprint), j, i);
                }
                if (no != 1) {
                    if ((i == operation.treasure[0][0] && j == operation.treasure[0][1]) || (i == operation.treasure[1][0] && j == operation.treasure[1][1]) || (i == operation.treasure[2][0] && j == operation.treasure[2][1])) {
                        pane.add(new ImageView(treasure), j, i);
                    }
                }
                if (no == 3 || no == 4) {
                    if ((i == operation.addHp[0][0] && j == operation.addHp[0][1]) || (i == operation.addHp[1][0] && j == operation.addHp[1][1]) || (i == operation.addHp[2][0] && j == operation.addHp[2][1])) {
                        pane.add(new ImageView(add), j, i);
                    }
                }
                if (no == 3) {
                    if ((i == operation.monster[0][0] && j == operation.monster[0][1]) || (i == operation.monster[1][0] && j == operation.monster[1][1]) || (i == operation.monster[2][0] && j == operation.monster[2][1])) {
                        pane.add(new ImageView(monsterf1), j, i);
                    }
                }
            }
        }
    }

    public void print(int a,GridPane pane,int no,int noshowinfour) {
        Image space = new Image("Img/gspace.png");
        Image wall = new Image("Img/gwall.png");
        Image end = new Image("Img/gend.png");
        Image footprint = new Image("Img/gfootprint.png");
        Image treasure = new Image("Img/gtreasure.png");
        Image herof1 = new Image("Img/hero-face-1.png");
        Image monsterf1 = new Image("Img/monster-face-1.png");
        Image add = new Image("Img/gadd.png");
        if(this.a == 2){
            space = new Image("Img/sspace.png");
            wall = new Image("Img/swall.png");
            herof1 = new Image("Img/hero-face-1.png");
            end = new Image("Img/send.png");
            footprint = new Image("Img/sfootprint.png");
            treasure = new Image("Img/streasure.png");
            herof1 = new Image("Img/hero-face-1.png");
            monsterf1 = new Image("Img/monster-face-1.png");
            add = new Image("Img/sadd.png");
        }
        ImageView hero = new ImageView(herof1);
        //pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(0, 0, 0, 0));
        pane.setVgap(0);
        pane.setHgap(0);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1)
                    pane.add(new ImageView(wall), j, i);
                if (map[i][j] == 0)
                    pane.add(new ImageView(space), j, i);
                if (map[i][j] == 9)
                    pane.add(new ImageView(end), j, i);
                if (operation.size - 1 >= 0 && i == operation.record[operation.size-1][0] && j == operation.record[operation.size-1][1]) {
                    pane.add(new ImageView(footprint),j,i);
                }
                if (operation.size - 2 >= 0 && i == operation.record[operation.size-2][0] && j == operation.record[operation.size-2][1]) {
                    pane.add(new ImageView(footprint),j,i);
                }
                if(no != 1) {
                    if ((i == operation.treasure[0][0] && j == operation.treasure[0][1]) || (i == operation.treasure[1][0] && j == operation.treasure[1][1]) || (i == operation.treasure[2][0] && j == operation.treasure[2][1])) {
                        pane.add(new ImageView(treasure), j, i);
                    }
                }
                if(no == 3 || no == 4) {
                    if ((i == operation.addHp[0][0] && j == operation.addHp[0][1]) || (i == operation.addHp[1][0] && j == operation.addHp[1][1]) || (i == operation.addHp[2][0] && j == operation.addHp[2][1])) {
                        pane.add(new ImageView(add), j, i);
                    }
                }
            }
        }
        pane.add(new ImageView(herof1),pPos[1],pPos[0]);
        //PathTransition pt = new PathTransition(Duration.millis(1000),)
    }

    public void print(int a,StackPane titlePane,int no){
        titlePane.getChildren().removeAll();
        Image title = new Image("Img/ghp.png");
        if(this.a == 2) title = new Image("Img/shp.png");
        titlePane.getChildren().add(new ImageView(title));
        Image[] phpTitle = new Image[14];
        for(int i = 0; i < 14; i++){
            phpTitle[i] = new Image("Img/php-"+i+".png");
        }
        titlePane.getChildren().add(new ImageView(phpTitle[operation.php]));

        Text attack = new Text("攻击力："+ operation.dps);
        attack.setFont(Font.font("宋体",FontWeight.BOLD,20));
        titlePane.getChildren().add(attack);

        if(no == 3 || no == 4) {
            Image[] mhpTitle = new Image[8];
            for (int i = 0; i < 8; i++) {
                mhpTitle[i] = new Image("Img/mhp-" + i + ".png");
            }
            if ((pPos[0] - 2 == operation.monster[0][0] && pPos[1] - 2 == operation.monster[0][1]) || (pPos[0] - 2 == operation.monster[0][0] && pPos[1] - 1 == operation.monster[0][1]) || (pPos[0] - 2 == operation.monster[0][0] && pPos[1] == operation.monster[0][1]) || (pPos[0] - 2 == operation.monster[0][0] && pPos[1] + 1 == operation.monster[0][1]) || (pPos[0] - 2 == operation.monster[0][0] && pPos[1] + 2 == operation.monster[0][1]) || (pPos[0] - 1 == operation.monster[0][0] && pPos[1] - 2 == operation.monster[0][1]) || (pPos[0] - 1 == operation.monster[0][0] && pPos[1] - 1 == operation.monster[0][1]) || (pPos[0] - 1 == operation.monster[0][0] && pPos[1] == operation.monster[0][1]) || (pPos[0] - 1 == operation.monster[0][0] && pPos[1] + 1 == operation.monster[0][1]) || (pPos[0] - 1 == operation.monster[0][0] && pPos[1] + 2 == operation.monster[0][1]) || (pPos[0] == operation.monster[0][0] && pPos[1] - 2 == operation.monster[0][1]) || (pPos[0] == operation.monster[0][0] && pPos[1] - 1 == operation.monster[0][1]) || (pPos[0] == operation.monster[0][0] && pPos[1] == operation.monster[0][1]) || (pPos[0] == operation.monster[0][0] && pPos[1] + 1 == operation.monster[0][1]) || (pPos[0] == operation.monster[0][0] && pPos[1] + 2 == operation.monster[0][1]) || (pPos[0] + 1 == operation.monster[0][0] && pPos[1] - 2 == operation.monster[0][1]) || (pPos[0] + 1 == operation.monster[0][0] && pPos[1] - 1 == operation.monster[0][1]) || (pPos[0] + 1 == operation.monster[0][0] && pPos[1] == operation.monster[0][1]) || (pPos[0] + 1 == operation.monster[0][0] && pPos[1] + 1 == operation.monster[0][1]) || (pPos[0] + 1 == operation.monster[0][0] && pPos[1] + 2 == operation.monster[0][1]) || (pPos[0] + 2 == operation.monster[0][0] && pPos[1] - 2 == operation.monster[0][1]) || (pPos[0] + 2 == operation.monster[0][0] && pPos[1] - 1 == operation.monster[0][1]) || (pPos[0] + 2 == operation.monster[0][0] && pPos[1] == operation.monster[0][1]) || (pPos[0] + 2 == operation.monster[0][0] && pPos[1] + 1 == operation.monster[0][1]) || (pPos[0] + 2 == operation.monster[0][0] && pPos[1] + 2 == operation.monster[0][1])) {
                titlePane.getChildren().add(new ImageView(mhpTitle[operation.monster[0][2]]));
            } else if ((pPos[0] - 2 == operation.monster[1][0] && pPos[1] - 2 == operation.monster[1][1]) || (pPos[0] - 2 == operation.monster[1][0] && pPos[1] - 1 == operation.monster[1][1]) || (pPos[0] - 2 == operation.monster[1][0] && pPos[1] == operation.monster[1][1]) || (pPos[0] - 2 == operation.monster[1][0] && pPos[1] + 1 == operation.monster[1][1]) || (pPos[0] - 2 == operation.monster[1][0] && pPos[1] + 2 == operation.monster[1][1]) || (pPos[0] - 1 == operation.monster[1][0] && pPos[1] - 2 == operation.monster[1][1]) || (pPos[0] - 1 == operation.monster[1][0] && pPos[1] - 1 == operation.monster[1][1]) || (pPos[0] - 1 == operation.monster[1][0] && pPos[1] == operation.monster[1][1]) || (pPos[0] - 1 == operation.monster[1][0] && pPos[1] + 1 == operation.monster[1][1]) || (pPos[0] - 1 == operation.monster[1][0] && pPos[1] + 2 == operation.monster[1][1]) || (pPos[0] == operation.monster[1][0] && pPos[1] - 2 == operation.monster[1][1]) || (pPos[0] == operation.monster[1][0] && pPos[1] - 1 == operation.monster[1][1]) || (pPos[0] == operation.monster[1][0] && pPos[1] == operation.monster[1][1]) || (pPos[0] == operation.monster[1][0] && pPos[1] + 1 == operation.monster[1][1]) || (pPos[0] == operation.monster[1][0] && pPos[1] + 2 == operation.monster[1][1]) || (pPos[0] + 1 == operation.monster[1][0] && pPos[1] - 2 == operation.monster[1][1]) || (pPos[0] + 1 == operation.monster[1][0] && pPos[1] - 1 == operation.monster[1][1]) || (pPos[0] + 1 == operation.monster[1][0] && pPos[1] == operation.monster[1][1]) || (pPos[0] + 1 == operation.monster[1][0] && pPos[1] + 1 == operation.monster[1][1]) || (pPos[0] + 1 == operation.monster[1][0] && pPos[1] + 2 == operation.monster[1][1]) || (pPos[0] + 2 == operation.monster[1][0] && pPos[1] - 2 == operation.monster[1][1]) || (pPos[0] + 2 == operation.monster[1][0] && pPos[1] - 1 == operation.monster[1][1]) || (pPos[0] + 2 == operation.monster[1][0] && pPos[1] == operation.monster[1][1]) || (pPos[0] + 2 == operation.monster[1][0] && pPos[1] + 1 == operation.monster[1][1]) || (pPos[0] + 2 == operation.monster[1][0] && pPos[1] + 2 == operation.monster[1][1])) {
                titlePane.getChildren().add(new ImageView(mhpTitle[operation.monster[1][2]]));
            } else if ((pPos[0] - 2 == operation.monster[2][0] && pPos[1] - 2 == operation.monster[2][1]) || (pPos[0] - 2 == operation.monster[2][0] && pPos[1] - 1 == operation.monster[2][1]) || (pPos[0] - 2 == operation.monster[2][0] && pPos[1] == operation.monster[2][1]) || (pPos[0] - 2 == operation.monster[2][0] && pPos[1] + 1 == operation.monster[2][1]) || (pPos[0] - 2 == operation.monster[2][0] && pPos[1] + 2 == operation.monster[2][1]) || (pPos[0] - 1 == operation.monster[2][0] && pPos[1] - 2 == operation.monster[2][1]) || (pPos[0] - 1 == operation.monster[2][0] && pPos[1] - 1 == operation.monster[2][1]) || (pPos[0] - 1 == operation.monster[2][0] && pPos[1] == operation.monster[2][1]) || (pPos[0] - 1 == operation.monster[2][0] && pPos[1] + 1 == operation.monster[2][1]) || (pPos[0] - 1 == operation.monster[2][0] && pPos[1] + 2 == operation.monster[2][1]) || (pPos[0] == operation.monster[2][0] && pPos[1] - 2 == operation.monster[2][1]) || (pPos[0] == operation.monster[2][0] && pPos[1] - 1 == operation.monster[2][1]) || (pPos[0] == operation.monster[2][0] && pPos[1] == operation.monster[2][1]) || (pPos[0] == operation.monster[2][0] && pPos[1] + 1 == operation.monster[2][1]) || (pPos[0] == operation.monster[2][0] && pPos[1] + 2 == operation.monster[2][1]) || (pPos[0] + 1 == operation.monster[2][0] && pPos[1] - 2 == operation.monster[2][1]) || (pPos[0] + 1 == operation.monster[2][0] && pPos[1] - 1 == operation.monster[2][1]) || (pPos[0] + 1 == operation.monster[2][0] && pPos[1] == operation.monster[2][1]) || (pPos[0] + 1 == operation.monster[2][0] && pPos[1] + 1 == operation.monster[2][1]) || (pPos[0] + 1 == operation.monster[2][0] && pPos[1] + 2 == operation.monster[2][1]) || (pPos[0] + 2 == operation.monster[2][0] && pPos[1] - 2 == operation.monster[2][1]) || (pPos[0] + 2 == operation.monster[2][0] && pPos[1] - 1 == operation.monster[2][1]) || (pPos[0] + 2 == operation.monster[2][0] && pPos[1] == operation.monster[2][1]) || (pPos[0] + 2 == operation.monster[2][0] && pPos[1] + 1 == operation.monster[2][1]) || (pPos[0] + 2 == operation.monster[2][0] && pPos[1] + 2 == operation.monster[2][1])) {
                titlePane.getChildren().add(new ImageView(mhpTitle[operation.monster[2][2]]));
            }
        }
    }

    public void loadGame() throws FileNotFoundException {
        /*try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/1.save"));
            try {
                Operation operation = new Operation();
                operation.pPoss = (int[]) ois.readObject();
                operation.pPoss[0] = Map.pPos[0];
                operation.pPoss[1] = Map.pPos[1];
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        java.io.File file = new java.io.File("save.txt");
        Scanner saveout = new Scanner(file);
        operation.size = 0;
        level = saveout.nextInt();
        pPos[0] = saveout.nextInt();
        pPos[1] = saveout.nextInt();
        operation.record[0][0] = pPos[0];
        operation.record[0][1] = pPos[1];
        operation.treasurecount = saveout.nextInt();
        operation.monstercount = saveout.nextInt();
        operation.stepcount = saveout.nextInt();
        operation.php = saveout.nextInt();
        operation.dps = saveout.nextInt();
        for (int i = 0; i < maze1MonsterNum; i++) {
            operation.monster[i][0] = saveout.nextInt();
            operation.monster[i][1] = saveout.nextInt();
            operation.monster[i][2] = saveout.nextInt();
        }
        for (int i = 0; i < maze1TreasureNum; i++) {
            operation.treasure[i][0] = saveout.nextInt();
            operation.treasure[i][1] = saveout.nextInt();
        }
        for (int i = 0; i < maze1MonsterNum; i++) {
            operation.addHp[i][0] = saveout.nextInt();
            operation.addHp[i][1] = saveout.nextInt();
        }
        a = saveout.nextInt();
        saveout.close();
    }

    public void createAndShowGameView(Stage primaryStage){
        refresh.stop();
        mazeGame(primaryStage,level,1,3);
    }

    public void saveGame() throws FileNotFoundException {
        java.io.PrintWriter save = new java.io.PrintWriter("save.txt");
        PrintWriter outputMaze1 = new PrintWriter(save);
        outputMaze1.print(level + " ");
        outputMaze1.print(pPos[0] + " ");
        outputMaze1.print(pPos[1] + " ");
        outputMaze1.print(operation.treasurecount + " ");
        outputMaze1.print(operation.monstercount + " ");
        outputMaze1.print(operation.stepcount + " ");
        outputMaze1.print(operation.php + " ");
        outputMaze1.print(operation.dps + " ");
        for (int i = 0; i < maze1MonsterNum; i++) {
            outputMaze1.print(operation.monster[i][0] + " ");
            outputMaze1.print(operation.monster[i][1] + " ");
            outputMaze1.print(operation.monster[i][2] + " ");
        }
        for (int i = 0; i < maze1TreasureNum; i++) {
            outputMaze1.print(operation.treasure[i][0] + " ");
            outputMaze1.print(operation.treasure[i][1] + " ");
        }
        for (int i = 0; i < maze1MonsterNum; i++) {
            outputMaze1.print(operation.addHp[i][0] + " ");
            outputMaze1.print(operation.addHp[i][1] + " ");
        }
        outputMaze1.print(a+" ");
        outputMaze1.close();
    }

    public static void main(String[] args){
        launch(args);
    }
}
