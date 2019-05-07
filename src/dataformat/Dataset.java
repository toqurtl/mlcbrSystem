package dataformat;

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.stream.Collectors;

import mlcbrUtils.Util1;

public class Dataset extends ArrayList<Data> implements Serializable {
    public ArrayList<String> attributes = new ArrayList<>();
    public int numAttributes;
    public int classAttri;
    public int IDAttri;
    public int weightLength;

    LinkedHashMap<String, ArrayList<Double>> statistics = new LinkedHashMap<>();
    public Dataset(String filename) throws IOException{
        initialize(filename);
    }
    //for deepcopy
    public Dataset(Dataset dataset) {
        dataset.attributes.forEach(x->this.attributes.add(x));
        dataset.forEach(x->this.add(x));
        this.numAttributes = attributes.size();
        this.IDAttri = 0;
        this.classAttri = attributes.size()-1;
        weightLength = attributes.size()-2;
    }

    public Dataset(ArrayList<String> attributes){
        attributes.forEach(x->this.attributes.add(x));
        this.numAttributes = attributes.size();
        this.IDAttri = 0;
        this.classAttri = attributes.size()-1;
        weightLength = attributes.size()-2;
    }

    public Dataset InterquartileRange(double OF, double EVF) {
        return null;
    }


    public List<Double> getColumnValuesList(int num){
        return this.stream().map(d->d.get(num)).collect(Collectors.toList());
    }

    private double getStats(int num, Statistics stat){
        return stat.getStats(this.getColumnValuesList(num));
    }
    public double getAvg(int num){
        return Stats.getAvg(getColumnValuesList(num));
    }
    public double getMax(int num){
        return Stats.getMax(getColumnValuesList(num));
    }
    public double getMin(int num){
        return Stats.getMin(getColumnValuesList(num));
    }
    public double getDeviation(int num){
        return Stats.getDevi(getColumnValuesList(num));
    }

    public void printDataset() {
        this.forEach(x->{
            x.printData();
            System.out.println();
        });
    }

    public void printData(int num) {
        this.get(num).printData();
    }

    public void saveFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName+".csv");
        BufferedWriter bw = new BufferedWriter(fw);
        for(String s : attributes) {
            bw.write(s);
            bw.write(",");
        }
        bw.write("\n");
        for(Data d : this) {
            for(double v : d) {
                bw.write(String.valueOf(v));
                bw.write(",");
            }
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }

    public Dataset clone() {
        return new Dataset(this);
    }


    private void initialize(String filename) throws IOException{

        BufferedReader bf = new BufferedReader(Files.newBufferedReader(Paths.get(filename)));
        String[] tempAttri = bf.readLine().split(",");
        attributes = Util1.arrToListStr(tempAttri);

        this.numAttributes = attributes.size();
        this.IDAttri = 0;
        this.classAttri = attributes.size()-1;

        int check = 0;
        String strLine;
        while((strLine = bf.readLine())!=null) {
            String[] tempArray = strLine.split(",");
            int numColumn = tempArray.length;
            double[] dd = new double[numColumn];
            for(int i=0;i<numColumn;i++) {
                try {
                    dd[i] = Double.parseDouble(tempArray[i]);
                }catch(NumberFormatException e) {
                    System.out.println(i+"th column value in "+check+"th row is not double value : "+tempArray[i]);
                }
            }
            this.add(new Data(attributes, dd));
            check++;
        }
    }

    public boolean isFitData(Data d){
        return DataUtils.isFitData(this, d);
    }

    public boolean isFitData(Dataset d){
        return DataUtils.isFitData(this, d);
    }

}
