package it.miketech.connect4;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mike on 11/23/15.
 */
public class Board {

    private static int PLAYER_ONE_WIN=4;
    private static int PLAYER_TWO_WIN=20;

    private static int GAME_RESULT_WIN=1;
    private static int GAME_RESULT_FAIL=2;
    private static int GAME_RESULT_DRAW=0;

    public boolean gameOver=false;

    private int [][] gameBoardArr=new int[7][7];
    private int [] hightArr=new int[7];

    private int currentPlayer=1;

    public Board(){

    }

    public Board copy(){
        Board copy =new Board();

        if (this.gameOver){
            copy.gameOver=true;
        }else {
            copy.gameOver=false;
        }

        for (int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                copy.gameBoardArr[i][j]=this.gameBoardArr[i][j];
            }
        }

        for (int i=0;i<7;i++){
            copy.hightArr[i]=this.hightArr[i];
        }

        copy.currentPlayer=Integer.valueOf(this.currentPlayer);

        return copy;
    }

    public int getGameResult(){

        int gameResult=4;

        boolean hasZero=false;
        int rowInFourSum=0;
        int columnInFourSum=0;
        int diagonalSum=0;
        int anotherDiagonalSum=0;

        for (int i=0;i<=6;i++){
            for (int j=0;j<=6;j++){

                if (gameBoardArr[i][j]==0){
                    hasZero=true;
                }

                try{
                    anotherDiagonalSum=gameBoardArr[i][j]+gameBoardArr[i-1][j+1]+gameBoardArr[i-2][j+2] + gameBoardArr[i-3][j+3];
                }catch (Exception e){
                    //e.printStackTrace();
                }finally {
                    if (rowInFourSum == PLAYER_ONE_WIN||columnInFourSum==PLAYER_ONE_WIN||diagonalSum == PLAYER_ONE_WIN || anotherDiagonalSum==PLAYER_ONE_WIN){
                        gameResult= GAME_RESULT_WIN;
                    }else if(rowInFourSum == PLAYER_TWO_WIN||columnInFourSum==PLAYER_TWO_WIN||diagonalSum==PLAYER_TWO_WIN||anotherDiagonalSum==PLAYER_TWO_WIN){
                        gameResult= GAME_RESULT_FAIL;
                    }
                }


                try{
                    diagonalSum=gameBoardArr[i][j]+gameBoardArr[i+1][j+1]+gameBoardArr[i+2][j + 2] + gameBoardArr[i + 3][j + 3];
                }catch (Exception e){
                    //e.printStackTrace();
                }finally {
                    if (rowInFourSum == PLAYER_ONE_WIN||columnInFourSum==PLAYER_ONE_WIN||diagonalSum == PLAYER_ONE_WIN || anotherDiagonalSum==PLAYER_ONE_WIN){
                        gameResult=  GAME_RESULT_WIN;
                    }else if(rowInFourSum == PLAYER_TWO_WIN||columnInFourSum==PLAYER_TWO_WIN||diagonalSum==PLAYER_TWO_WIN||anotherDiagonalSum==PLAYER_TWO_WIN){
                        gameResult=  GAME_RESULT_FAIL;
                    }
                }


                try{
                    rowInFourSum=gameBoardArr[i][j]+gameBoardArr[i][j+1]+gameBoardArr[i][j+2]+gameBoardArr[i][j+3];
                }catch (Exception e){
                    //e.printStackTrace();
                }finally {
                    if (rowInFourSum == PLAYER_ONE_WIN||columnInFourSum==PLAYER_ONE_WIN||diagonalSum == PLAYER_ONE_WIN || anotherDiagonalSum==PLAYER_ONE_WIN){
                        gameResult=  GAME_RESULT_WIN;
                    }else if(rowInFourSum == PLAYER_TWO_WIN||columnInFourSum==PLAYER_TWO_WIN||diagonalSum==PLAYER_TWO_WIN||anotherDiagonalSum==PLAYER_TWO_WIN){
                        gameResult=  GAME_RESULT_FAIL;
                    }
                }


                try{
                    columnInFourSum=gameBoardArr[i][j]+gameBoardArr[i+1][j]+gameBoardArr[i+2][j]+gameBoardArr[i+3][j];
                }catch (Exception e){
                    //e.printStackTrace();
                }finally {
                    if (rowInFourSum == PLAYER_ONE_WIN||columnInFourSum==PLAYER_ONE_WIN||diagonalSum == PLAYER_ONE_WIN || anotherDiagonalSum==PLAYER_ONE_WIN){
                        gameResult=  GAME_RESULT_WIN;
                    }else if(rowInFourSum == PLAYER_TWO_WIN||columnInFourSum==PLAYER_TWO_WIN||diagonalSum==PLAYER_TWO_WIN||anotherDiagonalSum==PLAYER_TWO_WIN){
                        gameResult=  GAME_RESULT_FAIL;
                    }
                }


            }
        }

        if (!hasZero){
            gameResult=  GAME_RESULT_DRAW;
        }

        return gameResult;
    }

    public void placePiece(int position){

        if (!gameOver){
            int score=0;
            switch (getCurrentPlayer()){
                case 1:score=1;break;
                case 2:score=5;break;
            }

            switch (position){
                case 0: gameBoardArr[6-hightArr[0]][0]=score; hightArr[0]++; break;
                case 1: gameBoardArr[6-hightArr[1]][1]=score; hightArr[1]++; break;
                case 2: gameBoardArr[6-hightArr[2]][2]=score; hightArr[2]++; break;
                case 3: gameBoardArr[6-hightArr[3]][3]=score; hightArr[3]++; break;
                case 4: gameBoardArr[6-hightArr[4]][4]=score; hightArr[4]++; break;
                case 5: gameBoardArr[6-hightArr[5]][5]=score; hightArr[5]++; break;
                case 6: gameBoardArr[6-hightArr[6]][6]=score; hightArr[6]++; break;
                default: break;
            }

            if (getGameResult()!=4){
                gameOver=true;
               // Log.d("Board","Game Over +Player"+getCurrentPlayer()+"Wins");
            }

            currentPlayer++;
        }

    }

    public void placePiece(int position,int team){

        int score=0;
        switch (team){
            case 1:score=1; currentPlayer=1; break;
            case 2:score=5; currentPlayer=2; break;
        }

        switch (position){
            case 0: gameBoardArr[6-hightArr[0]][0]=score; hightArr[0]++; break;
            case 1: gameBoardArr[6-hightArr[1]][1]=score; hightArr[1]++; break;
            case 2: gameBoardArr[6-hightArr[2]][2]=score; hightArr[2]++; break;
            case 3: gameBoardArr[6-hightArr[3]][3]=score; hightArr[3]++; break;
            case 4: gameBoardArr[6-hightArr[4]][4]=score; hightArr[4]++; break;
            case 5: gameBoardArr[6-hightArr[5]][5]=score; hightArr[5]++; break;
            case 6: gameBoardArr[6-hightArr[6]][6]=score; hightArr[6]++; break;
            default: break;
        }

        currentPlayer++;
    }

    public int getCurrentPlayer() {
        int currentPlayer=0;
        if (this.currentPlayer%2==0)
            currentPlayer=2;
        else
            currentPlayer=1;
        return currentPlayer;
    }

    public void reset(){
        for (int i=0;i<=6;i++){
            for (int j=0;j<=6;j++){
                gameBoardArr[i][j]=0;
            }
        }

        for (int i=0;i<=6;i++){
            hightArr[i]=0;
        }

        currentPlayer=1;

        gameOver=false;
    }

    public int[][] getGameBoardArr() {
        return gameBoardArr;
    }

    public void cancelAction(int posotition){
        gameBoardArr[6-hightArr[posotition]][posotition]=0;
        hightArr[posotition]--;
        currentPlayer--;
    }

    public void setGameBoardArr(int[][] gameBoardArr) {
        this.gameBoardArr = gameBoardArr;
    }

    public int[] getHightArr() {
        return hightArr;
    }

    public void setHightArr(int[] hightArr) {
        this.hightArr = hightArr;
    }

    public ArrayList<Integer> getAvailableSlots(){
        ArrayList list=new ArrayList();

        for (int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                if (gameBoardArr[i][j]==0){
                    list.add(j);
                }
            }
        }

        //Log.d("getAvaliableSolt",list.toString());

        return list;
    }

}
