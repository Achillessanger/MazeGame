import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.util.Scanner;

public class Operation implements Map {

    int treasurecount = 0;
    int monstercount = 0;
    int stepcount = 0;
    int php = 2;
    int dps = 1;
    int[][] monster = {{13,1,5},{4,7,3},{7,17,7}};
    int[][] treasure = {{8,2},{11,6},{4,11}};
    int[][] record = {{1,0}};
    int[][] addHp = {{-5,-5},{-5,-5},{-5,-5}};
    int size = 0;
    boolean isKilled = false;
    boolean[] ifContinue = {false,false,false,false,false,false};
    int[][] treasure2 = {{8,2},{11,6},{4,11}};
    int[][] monster2 = {{13,1,5},{4,7,3},{7,17,7}};
    int[][] addHp2 = {{-5,-5},{-5,-5},{-5,-5}};
    int[] pPos2 = {1, 0};/////
    int playNumber = 0;
    String name = "";
    int isMonMoved = 0;
    int[][] result = new int[maze1MonsterNum][4];
    int[] mondir = {0,0,0};
    int mazeFourMonKilled = 0;

    void heroMove(KeyCode x,int no) {
        pPos2[0] = pPos[0];
        pPos2[1] = pPos[1];
        if(x == KeyCode.W || x == KeyCode.S || x == KeyCode.A || x == KeyCode.D || x == KeyCode.B) {
            boolean ifKnocked = false;
            boolean isBacked = false;
            boolean isBackKnocked = false;

            int row = pPos[0];
            int col = pPos[1];
            switch (x) {
                case W:
                    pPos[0]--;
                    break;
                case A:
                    pPos[1]--;
                    break;
                case S:
                    pPos[0]++;
                    break;
                case D:
                    pPos[1]++;
                    break;
                case B:
                    if(size - 1 >= 0){
                        pPos[0] = record[size - 1][0];
                        pPos[1] = record[size - 1][1];
                        size--;
                        isBacked = true;
                        stepcount -= 2;
                    }else {
                        isBackKnocked = true;
                    }
                    break;
            }
            if (pPos[0] < 0 || pPos[0] > 14 || pPos[1] < 0 || pPos[1] > 19 || map[pPos[0]][pPos[1]] == 1) {
                pPos[0] = row;
                pPos[1] = col;
                ifKnocked = true;
            }
            if(no == 3 || no ==4) {
                for (int i = 0; i < maze1MonsterNum; i++) {
                    if (monster[i][0] >= 0 && monster[i][0] == pPos[0] && monster[i][1] == pPos[1]) {
                        php--;
                        this.monster[i][2]--;
                        if (php == 0) {
                            pPos[0] = -1;
                            pPos[1] = -1;
                            isKilled = true;
                        }
                        if (this.monster[i][2] <= 0) {
                            addHp[i][0] = monster[i][0];
                            addHp[i][1] = monster[i][1];
                            monster[i][0] = -5;
                            monster[i][1] = -5;
                            monstercount++;
                            if(no == 4){
                                mazeFourMonKilled++;
                            }
                        }
                    }
                }
            }
            if (size + 1 >= record.length) {
                int[][] temp = new int[record.length * 2][2];
                for (int i = 0; i < record.length; i++) {
                    temp[i][0] = record[i][0];
                    temp[i][1] = record[i][1];
                }
                this.record = temp;
            }
            if(!isBacked) {
                record[++this.size][0] = pPos[0];
                record[this.size][1] = pPos[1];
            }
            if(isBackKnocked){
                this.size--;
            }
            if(ifKnocked) size--;
            if(!ifKnocked) stepcount++;
        }
        if(x == KeyCode.P || x == KeyCode.K || x == KeyCode.Q || x == KeyCode.X){
            switch (x) {
                case Q:
                case X:
                    int isExit = JOptionPane.showConfirmDialog(null, "是否要退出", "退出", JOptionPane.YES_NO_OPTION);
                    if (isExit == JOptionPane.YES_OPTION) System.exit(0);
                    break;
                case P:
                    int canPickTrea = 0;
                    int canPickAdd = 0;
                    if(no != 1) {
                        for (int n = 0; n < 5; n++) {
                            switch (n) {
                                case 0:
                                    for (int i = 0; i < maze1TreasureNum; i++) {
                                        if (pPos[0] - 1 >= 0 && treasure[i][0] == pPos[0] - 1 && treasure[i][1] == pPos[1]) {
                                            treasure[i][0] = -1;
                                            treasure[i][1] = -1;
                                            treasurecount++;
                                            canPickTrea++;
                                        }
                                    }
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[0] - 1 >= 0 && addHp[i][0] == pPos[0] - 1 && addHp[i][1] == pPos[1]) {
                                            addHp[i][0] = -1;
                                            addHp[i][1] = -1;
                                            treasurecount++;
                                            canPickAdd++;
                                        }
                                    }
                                    break;
                                case 1:
                                    for (int i = 0; i < maze1TreasureNum; i++) {
                                        if (pPos[0] + 1 < map.length && treasure[i][0] == pPos[0] + 1 && treasure[i][1] == pPos[1]) {
                                            treasure[i][0] = -1;
                                            treasure[i][1] = -1;
                                            treasurecount++;
                                            canPickTrea++;
                                        }
                                    }
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[0] + 1 < map.length && addHp[i][0] == pPos[0] + 1 && addHp[i][1] == pPos[1]) {
                                            addHp[i][0] = -1;
                                            addHp[i][1] = -1;
                                            treasurecount++;
                                            canPickAdd++;
                                        }
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < maze1TreasureNum; i++) {
                                        if (pPos[1] - 1 >= 0 && treasure[i][0] == pPos[0] && treasure[i][1] == pPos[1] - 1) {
                                            treasure[i][0] = -1;
                                            treasure[i][1] = -1;
                                            treasurecount++;
                                            canPickTrea++;
                                        }
                                    }
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[1] - 1 >= 0 && addHp[i][0] == pPos[0] && addHp[i][1] == pPos[1] - 1) {
                                            addHp[i][0] = -1;
                                            addHp[i][1] = -1;
                                            treasurecount++;
                                            canPickAdd++;
                                        }
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < maze1TreasureNum; i++) {
                                        if (pPos[1] + 1 < map[0].length && treasure[i][0] == pPos[0] && treasure[i][1] == pPos[1] + 1) {
                                            treasure[i][0] = -1;
                                            treasure[i][1] = -1;
                                            treasurecount++;
                                            canPickTrea++;
                                        }
                                    }
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[1] + 1 < map[0].length && addHp[i][0] == pPos[0] && addHp[i][1] == pPos[1] + 1) {
                                            addHp[i][0] = -1;
                                            addHp[i][1] = -1;
                                            treasurecount++;
                                            canPickAdd++;
                                        }
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < maze1TreasureNum; i++) {
                                        if (treasure[i][0] == pPos[0] && treasure[i][1] == pPos[1]) {
                                            treasure[i][0] = -1;
                                            treasure[i][1] = -1;
                                            treasurecount++;
                                            canPickTrea++;
                                        }
                                    }
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (addHp[i][0] == pPos[0] && addHp[i][1] == pPos[1]) {
                                            addHp[i][0] = -1;
                                            addHp[i][1] = -1;
                                            treasurecount++;
                                            canPickAdd++;
                                        }
                                    }
                                    break;
                            }
                        }
                        for (int i = 0; i < canPickTrea; i++) {
                            switch ((int) (Math.random() * 7)) {
                                case 0:
                                    dps++;
                                    break;
                                case 1:
                                    php++;
                                    break;
                                default:
                                    break;
                            }
                        }
                        for (int i = 0; i < canPickAdd; i++) {
                            switch ((int) (Math.random() * 2)) {
                                case 0:
                                    dps++;
                                    break;
                                case 1:
                                    php++;
                                    break;
                            }
                        }
                    }
                    break;
                case K:
                    if(no == 3 || no ==4) {
                        for (int n = 0; n < 5; n++) {
                            switch (n) {
                                case 0:
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[0] - 1 >= 0 && monster[i][0] == pPos[0] - 1 && monster[i][1] == pPos[1]) {
                                            this.monster[i][2] -= dps;
                                            if (this.monster[i][2] <= 0) {
                                                addHp[i][0] = monster[i][0];
                                                addHp[i][1] = monster[i][1];
                                                monster[i][0] = -5;
                                                monster[i][1] = -5;
                                                monstercount++;
                                                if(no == 4){
                                                    mazeFourMonKilled++;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case 1:
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[0] + 1 < map.length && monster[i][0] == pPos[0] + 1 && monster[i][1] == pPos[1]) {
                                            this.monster[i][2] -= dps;
                                            if (this.monster[i][2] <= 0) {
                                                addHp[i][0] = monster[i][0];
                                                addHp[i][1] = monster[i][1];
                                                monster[i][0] = -5;
                                                monster[i][1] = -5;
                                                monstercount++;
                                                if(no == 4){
                                                    mazeFourMonKilled++;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[1] - 1 >= 0 && monster[i][0] == pPos[0] && monster[i][1] == pPos[1] - 1) {
                                            this.monster[i][2] -= dps;
                                            if (this.monster[i][2] <= 0) {
                                                addHp[i][0] = monster[i][0];
                                                addHp[i][1] = monster[i][1];
                                                monster[i][0] = -5;
                                                monster[i][1] = -5;
                                                monstercount++;
                                                if(no == 4){
                                                    mazeFourMonKilled++;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (pPos[1] + 1 < map[0].length && monster[i][0] == pPos[0] && monster[i][1] == pPos[1] + 1) {
                                            this.monster[i][2] -= dps;
                                            if (this.monster[i][2] <= 0) {
                                                addHp[i][0] = monster[i][0];
                                                addHp[i][1] = monster[i][1];
                                                monster[i][0] = -5;
                                                monster[i][1] = -5;
                                                monstercount++;
                                                if(no == 4){
                                                    mazeFourMonKilled++;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < maze1MonsterNum; i++) {
                                        if (monster[i][0] == pPos[0] && monster[i][1] == pPos[1]) {
                                            this.monster[i][2] -= dps;
                                            if (this.monster[i][2] <= 0) {
                                                addHp[i][0] = monster[i][0];
                                                addHp[i][1] = monster[i][1];
                                                monster[i][0] = -5;
                                                monster[i][1] = -5;
                                                monstercount++;
                                                if(no == 4){
                                                    mazeFourMonKilled++;
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                    break;
            }
        }
    }

    void monsterMove(int[] userNewPos,int no) {
        if (no == 4) {
            isMonMoved = 1;
            for (int i = 0; i < maze1MonsterNum; i++) {
                result[i][2] = monster[i][0];
                result[i][3] = monster[i][1];
            }
            for (int i = 0; i < maze1MonsterNum; i++) {
                int monsterMove = (int) (Math.random() * 4);
                int[] directionsCanMove = {0, 0, 0, 0};//左，右，上，下
                if (monster[i][0] < 0) continue;
                if (monster[i][0] >= 0 && monster[i][1] >= 0 && monster[i][1] - 1 >= 0 && monster[i][1] - 1 < map[0].length && map[monster[i][0]][monster[i][1] - 1] != 1) {
                    directionsCanMove[0] = 1;
                    if (pPos[0] >= 0) {
                        if (monster[i][1] > pPos[1]) directionsCanMove[0] = 2;
                    }
                }
                if (monster[i][0] >= 0 && monster[i][1] >= 0 && monster[i][1] + 1 >= 0 && monster[i][1] + 1 < map[0].length && map[monster[i][0]][monster[i][1] + 1] != 1) {
                    directionsCanMove[1] = 1;
                    if (pPos[0] >= 0) {
                        if (monster[i][1] < pPos[1]) directionsCanMove[1] = 2;
                    }
                }
                if (monster[i][0] >= 0 && monster[i][1] >= 0 && monster[i][0] - 1 >= 0 && monster[i][0] - 1 < map.length && map[monster[i][0] - 1][monster[i][1]] != 1) {
                    directionsCanMove[2] = 1;
                    if (pPos[0] >= 0) {
                        if (monster[i][0] > pPos[0]) directionsCanMove[2] = 2;
                    }
                }
                if (monster[i][0] >= 0 && monster[i][1] >= 0 && monster[i][0] + 1 >= 0 && monster[i][0] + 1 < map.length && map[monster[i][0] + 1][monster[i][1]] != 1) {
                    directionsCanMove[3] = 1;
                    if (pPos[0] >= 0) {
                        if (monster[i][0] < pPos[0]) directionsCanMove[3] = 2;
                    }
                }
                if (directionsCanMove[0] == 0 && directionsCanMove[1] == 0 && directionsCanMove[2] == 0 && directionsCanMove[3] == 0)
                    monsterMove = 4;
                else if (directionsCanMove[0] == 2 || directionsCanMove[1] == 2 || directionsCanMove[2] == 2 || directionsCanMove[3] == 2) {
                    while (directionsCanMove[monsterMove] != 2) monsterMove = (int) (Math.random() * 4);
                } else {
                    while (directionsCanMove[monsterMove] == 0) {
                        monsterMove = (int) (Math.random() * 4);
                    }
                }
                switch (monsterMove) {
                    case 0:
                        monster[i][1]--;
                        mondir[i] = 4;
                        break;
                    case 1:
                        monster[i][1]++;
                        mondir[i] = 2;
                        break;
                    case 2:
                        monster[i][0]--;
                        mondir[i] = 1;
                        break;
                    case 3:
                        monster[i][0]++;
                        mondir[i] = 3;
                        break;
                    case 4:
                        break;
                }
                if (monster[i][0] == userNewPos[0] && monster[i][1] == userNewPos[1]) {
                    php--;
                    this.monster[i][2]--;
                    if (this.monster[i][2] <= 0) {
                        addHp[i][0] = monster[i][0];
                        addHp[i][1] = monster[i][1];
                        monster[i][0] = -5;
                        monster[i][1] = -5;
                        monstercount++;
                        if(no == 4){
                            mazeFourMonKilled++;
                        }
                    }
                    if (php == 0) {
                        pPos[0] = -1;
                        pPos[1] = -1;
                        isKilled = true;
                    }
                }
            }
            //int[][] result = new int[monster.length][monster[0].length];
            for (int i = 0; i < maze1MonsterNum; i++) {
                result[i][0] = monster[i][0];
                result[i][1] = monster[i][1];
            }
        }
    }
}