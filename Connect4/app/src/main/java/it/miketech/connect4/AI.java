package it.miketech.connect4;

import android.util.Log;

import java.util.List;

import it.miketech.connect4.Board;
import it.miketech.connect4.MainActivity;


/**
 * Created by mike on 1/18/16.
 */
public class AI {


    int difficulty=3;
    private static int SCORE_WIN=10;
    private static int SCORE_FAIL=-10;
    private static int SCORE_DRAW=0;

    private static int CONTROLOR_AI=2;
    private static int CONTROLOR_PLAYER=1;

    private  AImove score(Board game){
        int score ;
        switch (game.getGameResult()){
            case 0:score=0; break;
            case 1:score=1; break;
            case 2:score=-1; break;
            default: score =0; break;
        }
        //Log.d("Score",score+"");
        return new AImove(score);
    }

    public AImove miniMax(Board game,int depth,int maxDepth){

        game.getGameResult();
        if (game.gameOver || depth==maxDepth){
            //Log.d("miniMax","Game Over");
            return score(game);
        }else {

            int thisScore;
            int bestScore;
            int bestMove=0;

            Board gameCopy;

            if (game.getCurrentPlayer()==1){
                bestScore=-1000;
            }else {
                bestScore=1000;
            }

            List<Integer> moves=game.getAvailableSlots();
            for (int i=0;i<moves.size();i++){

                gameCopy=game.copy();
                gameCopy.placePiece(moves.get(i));

                thisScore=miniMax(gameCopy,depth+1,maxDepth).score;

                if (game.getCurrentPlayer()==CONTROLOR_PLAYER){ //For player

                    if (thisScore > bestScore){
                        bestScore=thisScore;//Max
                        bestMove=moves.get(i);
                    }

                }else {  // For CPU AI
                    if (thisScore < bestScore){
                        bestScore=thisScore;//Min
                        bestMove=moves.get(i);

                    }
                }

            }
            //Log.d("miniMax","Return Ok! bestScore:"+bestScore+ "bestMove:"+bestMove);
            return new AImove(bestScore,bestMove);
        }

    }

    public AImove ABNegaMax(Board game,int depth,int maxDepth,int alpha,int beta){

        game.getGameResult();
        if (game.gameOver || depth==maxDepth){
            //Log.d("miniMax","Game Over");
            return score(game);
        }else {

            int thisScore;
            int bestScore;
            int bestMove=0;

            Board gameCopy;

            if (game.getCurrentPlayer()==1){
                bestScore=-1000;
            }else {
                bestScore=1000;
            }

            List<Integer> moves=game.getAvailableSlots();
            for (int i=0;i<moves.size();i++){

                gameCopy=game.copy();
                gameCopy.placePiece(moves.get(i));

                thisScore=ABNegaMax(gameCopy,depth+1,maxDepth,-beta,-Math.max(alpha,bestScore)).score;

                if (game.getCurrentPlayer()==CONTROLOR_PLAYER){ //For player

                    if (thisScore > bestScore){
                        bestScore=thisScore;//Max
                        bestMove=moves.get(i);
                    }

                }else {  // For CPU AI
                    if (thisScore < bestScore){
                        bestScore=thisScore;//Min
                        bestMove=moves.get(i);

                        //early loop exit
                        if (bestScore >= beta){
                            return  new AImove(bestScore,bestMove);
                        }

                    }
                }

            }
            //Log.d("miniMax","Return Ok! bestScore:"+bestScore+ "bestMove:"+bestMove);
            return new AImove(bestScore,bestMove);
        }

    }

    class AImove{

        int position = 0;
        int score = 0;

        public AImove(int score, int position){
            this.score=score;
            this.position=position;
        }

        public AImove(int score){
            this.score=score;
        }

    }

}
