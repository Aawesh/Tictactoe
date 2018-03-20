package tictactoe.com.tictacoe;

import android.os.Environment;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class QTable {
    private HashMap<String,double []> qMap;

    public QTable(String filename){
        qMap = loadQTable(filename);

    }

    public HashMap<String, double []> loadQTable(String filename){
        HashMap<String, double []> mMap = new HashMap<>();

        String sdcard = Environment.getExternalStorageDirectory() + File.separator + "Tictactoe";

        File file = new File(sdcard,filename);

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
            e.printStackTrace();
        }

        return mMap;
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
}
