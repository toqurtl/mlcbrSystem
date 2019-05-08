package cbr;
import java.lang.Math;
import java.util.Random;

import dataformat.Data;
import dataformat.DataUtils;
import dataformat.Dataset;
import ga.Chromosomeset;


public class CBRUtils {

    public static double distance(Data d1, Data d2){
        double dis = 0.0;
        for(int i=0;i<d1.weightLength;i++)
            dis+=Math.pow(d1.analysisValues[i]-d2.analysisValues[i],2);
        return Math.sqrt(dis/(d1.getNumAnalysisValues()));
    }

    public static double distance(Data d1, Data d2, double[] weight) {
        double dis = 0.0;
        for(int i=0;i<d1.weightLength;i++)
            dis+=weight[i]*Math.pow(d1.analysisValues[i]-d2.analysisValues[i],2);
        return Math.sqrt(dis/(d1.getNumAnalysisValues()));
    }
    public double distance(Data d1, Data d2, Chromosomeset chro){
        return distance(d1, d2, chro.chromosome);
    }
    public static double similarity(Data d1, Data d2, double[] weight) {
        return 1-CBRUtils.distance(d1, d2, weight);
    }
    public static double similarity(Data d1, Data d2, Chromosomeset chro) {
        return 1-CBRUtils.distance(d1, d2, chro.chromosome);
    }

    private static Dataset preData(Dataset dataset){
        return DataUtils.normalizedData(dataset, dataset);
    }

    public static double[] getRandomWeight(int length){
        double[] weight = new double[length];
        for(int i=0;i<length;i++){
            weight[i] = new Random().nextDouble();
        }
        return makesum1(weight);
    }

    public void saveCBRModel(String filename, CBRmodule cbr, int k, double trainError){

    }

    public static double[] makesum1(double[] weight) {
        int length = weight.length;
        double[] newWeight = new double[length];
        double sum = 0;
        for(int i=0;i<length;i++) {
            sum += weight[i];
        }
        for(int i=0;i<length;i++) {
            newWeight[i] = weight[i]/sum;
        }
        return newWeight;
    }

}
