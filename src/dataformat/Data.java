package dataformat;

import mlcbrUtils.Util1;
import java.io.Serializable;
import java.util.ArrayList;


public class Data extends ArrayList<Double> implements Serializable {
    public ArrayList<String> attributes = new ArrayList<>();
    public double[] analysisValues;
    public int weightLength;
    public int numAttributes;
    public int classAttri;
    public int IDAttri;

    public Data(Data d) {
        this.attributes.clear();
        d.attributes.forEach(x->this.attributes.add(x));
        d.forEach(x->this.add(x));
        numAttributes = attributes.size();
        classAttri = attributes.size()-1;
        weightLength = attributes.size()-2;
        IDAttri = 0;
        analysisValues = valueArray();
    }

    //emptyData
    public Data(ArrayList<String> attributes) {
        this.attributes.clear();
        attributes.forEach(x->this.attributes.add(x));
        numAttributes = attributes.size();
        classAttri = attributes.size()-1;
        weightLength = attributes.size()-2;
        IDAttri = 0;
    }

    public Data(ArrayList<String> attributes, double[] alldata) {
        this.attributes.clear();
        attributes.forEach(x->this.attributes.add(x));
        for(double d : alldata)
            this.add(d);
        numAttributes = attributes.size();
        classAttri = attributes.size()-1;
        weightLength = attributes.size()-2;
        IDAttri = 0;
        analysisValues = valueArray();
    }

    public int getNumAttributes(){return attributes.size();}
    public int getNumAnalysisValues(){return analysisValues.length;}

    public double ID(){
        return this.get(0);
    }
    public double classValue() {
        return this.get(classAttri);
    }
    public double[] valueArray() {
        double[] tempArray = new double[numAttributes-2];
        int check = 0;

        for(int i=0;i<numAttributes;i++) {
            if(i!=classAttri && i!=IDAttri) {
                tempArray[check] = this.get(i);
                check++;
            }
        }
        return tempArray;
    }

    public Data deleteAttribute(int num) {
        ArrayList<String> sList = new ArrayList<>();
        for(int i=0;i<numAttributes;i++) {
            if(num!=i) {
                sList.add(attributes.get(i));
            }
        }
        Data d = new Data(sList);

        for(int i=0;i<numAttributes;i++) {
            if(num!=i) {
                d.add(this.get(i));
            }
        }
        d.analysisValues = d.valueArray();
        return d;
    }

    public void printData() {
        Util1.printList(this);
        System.out.println();
    }

    public Data clone() {
        return new Data(this);
    }

    public boolean isFittedData(Data d){
        return DataUtils.isFitData(this, d);
    }



}
