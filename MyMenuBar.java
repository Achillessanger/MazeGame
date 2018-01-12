

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.FileNotFoundException;

public class MyMenuBar extends MenuBar {
    Stage stage = new Stage();
    void setStage(Stage primaryStage){
        stage = primaryStage;
    }
    MyMenuBar(TestGame testGame){

        Menu menuFile = new Menu("File");
        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(event -> {
            try {
                testGame.loadGame();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            testGame.createAndShowGameView(stage);
        });

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(event -> {
            try {
                if(testGame.canSave ) testGame.saveGame();
                else {
                    JOptionPane.showMessageDialog(null,"沙盒模式不能存档");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        menuFile.getItems().addAll(loadItem, saveItem);

        // add settings menu
        Menu menuSetting = new Menu("Settings");
        Menu themeMenu = new Menu("Theme");
        ToggleGroup themeGroup = new ToggleGroup();
        RadioMenuItem snowTheme = new RadioMenuItem("snow");
        snowTheme.setToggleGroup(themeGroup);
        RadioMenuItem grassTheme = new RadioMenuItem("grass");
        grassTheme.setToggleGroup(themeGroup);
        themeMenu.getItems().addAll(snowTheme, grassTheme);


        Menu sandboxMenu = new Menu("Sandbox mode");
        ToggleGroup levelGroup = new ToggleGroup();
        RadioMenuItem level1 = new RadioMenuItem("maze-1");
        level1.setToggleGroup(levelGroup);
        RadioMenuItem level2 = new RadioMenuItem("maze-2");
        level2.setToggleGroup(levelGroup);
        RadioMenuItem level3 = new RadioMenuItem("maze-3");
        level3.setToggleGroup(levelGroup);
        RadioMenuItem level4 = new RadioMenuItem("maze-4");
        level4.setToggleGroup(levelGroup);
        sandboxMenu.getItems().addAll(level1,level2,level3,level4);

        Menu storyMenu = new Menu("Story mode");
        ToggleGroup newStart = new ToggleGroup();
        RadioMenuItem startFromMaze1 = new RadioMenuItem("start from maze-1");
        startFromMaze1.setToggleGroup(newStart);
        storyMenu.getItems().addAll(startFromMaze1);

        menuSetting.getItems().addAll(themeMenu,sandboxMenu,storyMenu);

        // add help menu
        Menu menuHelp = new Menu("Help");
        MenuItem howToPlay = new MenuItem("How to Play");
        menuHelp.getItems().add(howToPlay);
        this.getMenus().addAll(menuFile, menuSetting, menuHelp);

        howToPlay.setOnAction(event -> {
            testGame.showHelpScene(testGame.a);
        });
        snowTheme.setOnAction(event -> {
            testGame.a = 2;
            testGame.isThemeChanged = 1;
        });
        grassTheme.setOnAction(event -> {
            testGame.a = 1;
            testGame.isThemeChanged = 1;
        });
        level1.setOnAction(event -> {
            testGame.refresh.stop();
            testGame.canSave = false;
            testGame.monMoveAni.stop();
            testGame.clear();
            testGame.mazeGame(stage,1,2,3);
        });
        level2.setOnAction(event -> {
            testGame.refresh.stop();
            testGame.canSave = false;
            testGame.monMoveAni.stop();
            testGame.clear();
            testGame.mazeGame(stage,2,2,3);
        });
        level3.setOnAction(event -> {
            testGame.refresh.stop();
            testGame.canSave = false;
            testGame.monMoveAni.stop();
            testGame.clear();
            testGame.mazeGame(stage,3,2,3);
        });
        level4.setOnAction(event -> {
            testGame.refresh.stop();
            testGame.canSave = false;
            testGame.monMoveAni.stop();
            testGame.clear();
            testGame.mazeGame(stage,4,2,3);
        });
        startFromMaze1.setOnAction(event -> {
            testGame.refresh.stop();
            testGame.canSave = true;
            testGame.clear();
            testGame.monMoveAni.stop();
            testGame.durGame(stage,1,1,3);
        });
    }
}
