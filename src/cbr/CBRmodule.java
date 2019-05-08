package cbr;

import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;

import mlcbrUtils.Util1;

import dataformat.*;
import ga.*;

public class CBRmodule implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArrayList<String> attributes = new ArrayList<>();
    public Dataset dataset;
    public Dataset instances;

    public int numAttributes;
    public int classAttri;
    public int IDAttri;

    public double[] bestWeight;

    public CBRmodule(Dataset db) {
        for(String s : db.attributes) {
            attributes.add(s);
        }
        this.numAttributes = db.numAttributes;
        this.classAttri = db.classAttri;
        this.IDAttri = db.IDAttri;
        dataset = new Dataset(db);
        instances = DataUtils.normalizedData(dataset, dataset);
    }





    public ArrayList<caseCompare> retrieveInternal(int k, int num, double []weight){
        ArrayList<caseCompare> retrivedData = new ArrayList<>();
        instances.stream()
                .filter(x->Double.compare(x.get(0),num*1.0)!=0)
                .map(x->new caseCompare(x, CBRUtils.distance(x, instances.get(num), weight)))
                .sorted(getComparator())
                .limit(k)
                .forEach(x->retrivedData.add(x));
        return retrivedData;
    }

    public ArrayList<caseCompare> retrieve(int k, Data newd, double[] weight){
        Data prenewd = DataUtils.normalizedData(dataset, newd);
        ArrayList<caseCompare> retrievedData = new ArrayList<>();
        instances.stream().map(x->new caseCompare(x, CBRUtils.distance(x, prenewd, weight))).sorted(getComparator()).limit(k).forEach(x->retrievedData.add(x));
        return retrievedData;
    }

    public double reuse(int k, Data newd, double[] weight) {
        ArrayList<caseCompare> retrievedData = retrieve(k, newd,weight);
        return retrievedData.stream().map(x->x.d.classValue()).reduce(0.0, (x,y)->x+y)/k;
    }

    public double reuseInteranal(int k, int num, double[] weight){
        ArrayList<caseCompare> retrievedData = retrieveInternal(k, num ,weight);
        return retrievedData.stream().map(x->x.d.classValue()).reduce(0.0, (x,y)->x+y)/k;
    }

    public double trainError(int k, double[] weight) {
        double sum = 0;
        for(int i=0;i<instances.size();i++)
            sum+=errorRate(k, i, weight);
        return sum/instances.size();
    }

    public double testError(int k, double[] weight, Dataset testSet) {
        double sum = 0;
        for(Data d : testSet)
            sum+=errorRate(k, d, weight);
        return sum/testSet.size();
    }

    public double errorRate(int k, int num, double[] weight){
        return Math.abs(reuseInteranal(k, num, weight)-classValue(num))/classValue(num);
    }


    public double errorRate(int k, int num, Chromosomeset chro) {
        return errorRate(k,num,chro.chromosome);
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

    public void setBestWeight(double[] bestWeight){
        this.bestWeight = bestWeight.clone();
    }
    public void setBestWeight(Optimization opti, int k) {
        Evolution.performEvolution(opti, this, k);
    }


    public Comparator<caseCompare> getComparator(){
        return (c1, c2) -> Double.compare(c1.distance, c2.distance);
    }

    public boolean isIDContains(Data d){
        boolean check = false;
        for(Data dd: this.dataset){
            if(Double.compare(dd.get(0), d.get(0))==0){
                check = true;
                break;
            }
        }
        return check;
    }

    private double getError(Data d, double predict){
        return Math.abs((predict-d.classValue())/d.classValue());
    }


    private double classValue(int num){
        return instances.get(num).classValue();
    }


}
