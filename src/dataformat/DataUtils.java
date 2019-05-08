package dataformat;

import mlcbrUtils.Util1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DataUtils {

    private static double standardize(double x, double avg, double dev){
        return (x-avg)/dev;
    }
    private static double normalize(double x, double min, double max){
        return (x-min)/(max-min);
    }

    public static ArrayList<Dataset> sampling(Dataset dSet, double point){
        try {
            int num = (int) Math.floor(dSet.size() * point);
            Dataset trainSet = new Dataset(dSet);
            trainSet.clear();
            Dataset testSet = new Dataset(trainSet);
            Collections.shuffle(dSet);
            for(int i = 0; i < num; i++)
                trainSet.add(dSet.get(i));
            for(int i = num; i< dSet.size() ; i++)
                testSet.add(dSet.get(i));
            ArrayList<Dataset> temp = new ArrayList<>();
            temp.add(trainSet);
            temp.add(testSet);
            return temp;
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println("point have to be double value from 0 to 1");
            return null;
        }
    }

    public static boolean isUpperExtreme(Dataset dSet, int num, double point, Data d){
        return d.get(num)>upperExtreme(dSet, num, point);
    }

    public static boolean isLowerExtreme(Dataset dSet, int num, double point, Data d){
        return d.get(num)<lowerExtreme(dSet, num, point);
    }

    public static double upperExtreme(Dataset dSet, int num, double point){
        return Stats.upperExtreme(dSet.getColumnValuesList(num), point);
    }

    public static double lowerExtreme(Dataset dSet, int num, double point){
        return Stats.lowerExtreme(dSet.getColumnValuesList(num), point);
    }

    public static Dataset removeOutliar(Dataset dSet, int num, double point){
        Dataset newSet = new Dataset(dSet);
        ArrayList<Double> outliarIndex = new ArrayList<>();
        for(Data d : dSet){
            if(isUpperExtreme(dSet, num, point, d) || isLowerExtreme(dSet, num, point, d))
                outliarIndex.add(d.ID());
        }
        for(double d : outliarIndex){
            deleteData(newSet, d);
        }
        return newSet;
    }

    public static Data randomDataGenerator(int length){
        ArrayList<String> attributes = new ArrayList<>();
        for(int i=0;i<length;i++)
            attributes.add("s"+i);
        double[] tempArray = new double[length];
        for(int j=0;j<length;j++){
            tempArray[j] = new Random().nextDouble()*100;
        }
        return new Data(attributes, tempArray);
    }

    public static Dataset randomDataSetGenerator(int size, int length){
        ArrayList<String> attributes = new ArrayList<>();
        for(int i=0;i<length;i++)
            attributes.add("s"+i);
        Dataset dset = new Dataset(attributes);
        for(int i=0;i<size;i++){
            double[] tempArray = new double[length];
            for(int j=0;j<length;j++){
                tempArray[j] = new Random().nextDouble()*100;
            }
            dset.add(new Data(attributes, tempArray));
        }
        return dset;
    }

    public static Data standardizedData(Dataset dataset, Data d){
        Data newd = new Data(dataset.attributes);
        for (int i = 0; i < dataset.numAttributes; i++) {
            if (i != dataset.classAttri && i != dataset.IDAttri) {
                double v = standardize(d.get(i), dataset.getAvg(i), dataset.getDeviation(i));
                newd.add(v);
            } else {
                newd.add(d.get(i));
            }
        }
        newd.analysisValues = newd.valueArray();
        newd.printData();
        return newd;
    }

    public static Dataset standardizedData(Dataset dataset, Dataset newdata) {
        Dataset tempData = new Dataset(newdata);
        tempData.clear();
        for(Data d : newdata)
            tempData.add(standardizedData(dataset, d));
        return tempData;
    }

    public static Data normalizedData(Dataset dataset, Data d){
        Data newd = new Data(dataset.attributes);
        for(int i=0;i<dataset.numAttributes;i++) {
            if(i!=dataset.classAttri && i!=dataset.IDAttri) {
                double v = normalize(d.get(i), dataset.getMin(i), dataset.getMax(i));
                newd.add(v);
            }else {
                newd.add(d.get(i));
            }
        }
        newd.analysisValues = newd.valueArray();
        return newd;
    }
    public static Dataset normalizedData(Dataset dataset, Dataset newdata) {
        Dataset tempData = new Dataset(newdata);
        tempData.clear();
        for(Data d : newdata)
            tempData.add(normalizedData(dataset, d));
        return tempData;
    }

    public static void deleteData(Dataset dataset, double ID) {
        int index = 0;
        for(Data d : dataset){
            if(Double.compare(d.ID(), ID)==0){
                dataset.remove(index);
                break;
            }
            index++;
        }
    }

    public static boolean isFitData(Data d1, Data d2){
        boolean check = true;
        for(int i=0;i<d1.numAttributes;i++){
            if(!d1.get(i).equals(d2.get(i))){
                check = false;
                break;
            }
        }
        return check;
    }

    public static boolean isFitData(Dataset d1, Data d2){
        boolean check = true;
        for(int i=0;i<d1.numAttributes;i++){
            if(!d1.get(i).equals(d2.get(i))){
                check = false;
                break;
            }
        }
        return check;
    }

    public static boolean isFitData(Dataset d1, Dataset d2){
        boolean check = true;
        for(int i=0;i<d1.numAttributes;i++){
            if(!d1.get(i).equals(d2.get(i))){
                check = false;
                break;
            }
        }
        return check;
    }

    public static void saveDataset(String filename, Dataset d1) throws IOException {
        Util1.saveFile(filename+"data", d1);
    }


}
