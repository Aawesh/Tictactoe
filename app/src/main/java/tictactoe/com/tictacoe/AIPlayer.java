package tictactoe.com.tictacoe;

import android.widget.Button;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.util.*;

public class AIPlayer {
    private String currentState;
    private int moveIndex;

    public AIPlayer(){
        this.currentState = "";
        this.moveIndex = 0;
    }

    public int makeLearnedMove(Game g,boolean turn){
        double [] actionValues;
        do{
            this.currentState = g.getBoard();
            AIActivity.qTable.saveState(this.currentState);

            actionValues = AIActivity.qTable.getActionValueArray(this.currentState);

            this.moveIndex = getAIMove(actionValues,this.currentState,g);
        }while(!g.isValidMove(this.moveIndex));
        g.updateBoard(moveIndex,false);

        return moveIndex;
    }

    /**
     *
     * @return index of movement
     */
    public static int getAIMove(double [] actionValues,String state,Game g){
        List<Integer> indexList = new ArrayList();
        List<Double> qValueList = new ArrayList();

        char[] st = state.toCharArray();
        for ( int i = 0; i < actionValues.length; i++ ){
            if(st[i] == ' '){ //Exclude filled position for selection of next move
                indexList.add(i);
                qValueList.add(actionValues[i]);
            }
        }

        if(indexList.size() == 1){
            return indexList.get(0);
        }else{
            List<Double> pDistribution = new ArrayList();
            double pSum = 0.0;
            for ( int i = 0; i < qValueList.size(); i++ ){
                pDistribution.add(i,Math.exp(qValueList.get(i)/g.getTemperature()));
                pSum += pDistribution.get(i);
            }

            for ( int i = 0; i < qValueList.size(); i++ ){
                pDistribution.set(i,pDistribution.get(i)/pSum);
            }

            return getRandomSample(pDistribution,indexList);
        }
    }

    static int getRandomSample(List<Double> pDistribution, List<Integer> indexList){
        int[] ind = new int[pDistribution.size()];
        double p[] = new double[pDistribution.size()];

        for(int i = 0;i<pDistribution.size(); i++){
            ind[i] = i;
            p[i] = pDistribution.get(i);
        }
        EnumeratedIntegerDistribution dist = new EnumeratedIntegerDistribution(ind,p);

        int idx = dist.sample();

        return indexList.get(idx);
    }
}
