package tictactoe.com.tictacoe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class QTable {
    private HashMap<String,double []> qMap;

    public QTable(){
        qMap = loadQTable();

    }

    /**
     * Update the Q table with the latest value for current action
     * @param state
     * @param actionIndex
     */
    public void updateQtable(String state, int actionIndex, double reward,String who){
        double [] actionValues = qMap.get(state);

        char[] st = state.toCharArray();
        if(st[actionIndex] == ' '){
            actionValues[actionIndex] = reward;
        }else{
            System.out.println("Trying to update invalid move");
        }

        qMap.put(state,actionValues);
    }


    public HashMap<String, double []> loadQTable(){
        HashMap<String, double []> mMap = new HashMap<>();

        String sdcard = Environment.getExternalStorageDirectory() + File.separator + "Tictactoe";

        File file = new File(sdcard,"qtable.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                double [] values = new double[9];
                String[] data = line.split(":");
                String[] array = data[1].split(",");
                for(int i= 0;i<9;i++){
                    values[i] = Double.parseDouble(array[i]);
                }

                mMap.put(data[0],values);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }



        return mMap;
    }

    public HashMap<String, double []> getQTable(){
        return this.qMap;
    }

    public double[] getActionValueArray(String key){
        if(key != null){
            if(qMap.containsKey(key)){
                return qMap.get(key);
            }
        }
        return null;
    }


    public void saveState(String state){
        if(!qMap.containsKey(state)){
            double[] actionValue = new double[9];
            Arrays.fill(actionValue,0);
            qMap.put(state,actionValue);
        }
    }

    public void display(){
        for (String key: qMap.keySet()){
            System.out.println(key + " = " + Arrays.toString(qMap.get(key)));
        }
    }

    public void save(){
        try {
            File file = new File("qtable.txt");

            if (file.exists()){
                file.delete();
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        String data = "";
        for (String state : qMap.keySet()) {
            data+= state +":"+Arrays.toString(qMap.get(state)).replace("[","").replace("]","")+"\n";
        }
        writeToFile(data,"qtable");
    }

    public void writeToFile(String data,String filename) {

        String path = Environment.getExternalStorageDirectory() + File.separator + "Tictactoe";
        // Create the folder.
        File folder = new File(path);
        if (!folder.exists()) {
            // Make it, if it doesn't exit
            folder.mkdirs();
        }

        // Create the file.
        File file = new File(folder, filename+".txt");

        try {
            if(!file.exists()){
                file.createNewFile();
            }else{
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(file,true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);


            Log.d("TAG"," Successful data write ");
            myOutWriter.close();

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
