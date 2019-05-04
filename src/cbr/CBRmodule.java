package cbr;

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mlcbrUtils.Util1;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import dataformat.*;
import ga.*;

public class CBRmodule implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArrayList<String> attributes = new ArrayList<>();
    public Dataset dataset;
    public Dataset instances;
    EuclideanDistance Edistnace = new EuclideanDistance();
    public int numInstances;
    public int numAttributes;
    public int classAttri;
    public int IDAttri;

    public double[] bestWeight;


    public static void main(String[] args){
        Dataset dSet = DataUtils.randomDataSetGenerator(20, 10);
        Data d = DataUtils.randomDataGenerator(10);
        Data d2 = DataUtils.normalizedData(dSet, dSet.get(0));
        double[] weight = CBRUtils.getRandomWeight(10-2);
        CBRmodule cbr = new CBRmodule(dSet);
        

    }

    public CBRmodule(Dataset db) {
        for(String s : db.attributes) {
            attributes.add(s);
        }
        this.numAttributes = db.numAttributes;
        this.numInstances = db.size();
        this.classAttri = db.classAttri;
        this.IDAttri = db.IDAttri;
        dataset = new Dataset(db);
        instances = DataUtils.normalizedData(dataset, dataset);
    }

    public ArrayList<caseCompare> retrieve(int k, Data newd){
        ArrayList<caseCompare> retrievedData = new ArrayList<>();
        instances.stream().map(x->new caseCompare(x, CBRUtils.distance(x, newd))).sorted(getComparator()).limit(k).forEach(x->retrievedData.add(x));
        return retrievedData;
    }


    public ArrayList<caseCompare> retrieve(int k, Data newd, double[] weight){
        ArrayList<caseCompare> retrievedData = new ArrayList<>();
        instances.stream().map(x->new caseCompare(x, CBRUtils.distance(x, newd, weight))).sorted(getComparator()).limit(k).forEach(x->retrievedData.add(x));
        return retrievedData;
    }

    public double reuse(int k, Data newd) {
        ArrayList<caseCompare> retrievedData = retrieve(k, newd);
        double predict = 0;
        for(caseCompare c : retrievedData)
            predict += c.d.classValue();
        return predict/k;
    }

    public double reuse(int k, Data newd, double[] weight) {
        ArrayList<caseCompare> retrievedData = retrieve(k, newd,weight);
        double predict = 0;
        for(caseCompare c : retrievedData)
            predict += c.d.classValue();
        return predict/k;
    }

    public double trainError(int k, int num, double[] weight) {
        ArrayList<caseCompare> retrievedData = retrieve(k+1, instances.get(num), weight);
        retrievedData.remove(0);
        double predict = 0;
        for(caseCompare c : retrievedData)
            predict += c.d.classValue();
        return predict/k;
    }

    public double trainError(int k, double[] weight) {
        double sum =0;
        for(int i=0;i<numInstances;i++) {
            sum+=trainError(k, i, weight);
        }
        return sum/numInstances;
    }

    public double testError(int k, double[] weight, Dataset testSet) {
        double sum = 0;
        for(Data d : testSet)
            sum+=errorRate(k, d, weight);
        return sum/testSet.size();
    }

    public double trainErrorRate(int k, int num){
        Dataset newdata = DataUtils.deleteData(instances, num);
        return 0.0;
    }

    public double errorRate(int k, Data newd) {
        return Math.abs(reuse(k,newd)-newd.classValue())/newd.classValue();
    }

    public double errorRate(int k, Data newd, double[] weight) {
        return Math.abs(reuse(k,newd, weight)-newd.classValue())/newd.classValue();
    }

    public double errorRate(int k, Data newd, Chromosomeset chro) {
        return errorRate(k,newd,chro.chromosome);
    }

    public void saveFile(String filePath) throws FileNotFoundException, IOException {
        Util1.saveFile(filePath, this);
    }

    public void setBestWeight(Optimization opti, int k) {
        Evolution.performEvolution(opti, this, k);
    }

    public double getError(Data d, double predict){
        return Math.abs((predict-d.classValue())/d.classValue());
    }

    public Comparator<caseCompare> getComparator(){
        return (c1, c2) -> Double.compare(c1.distance, c2.distance);
    }

    public class caseCompare{
        public Data d;
        public double distance;
        public caseCompare(Data d, double distance) {
            this.d = d;
            this.distance = distance;
        }
    }
}
