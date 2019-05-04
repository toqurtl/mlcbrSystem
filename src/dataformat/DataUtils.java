package dataformat;

import java.util.ArrayList;
import java.util.Random;

public class DataUtils {

    private static double standardize(double x, double avg, double dev){
        return (x-avg)/dev;
    }
    private static double normalize(double x, double min, double max){
        return (x-min)/(max-min);
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

    public static Dataset deleteData(Dataset dataset, int num) {
        Dataset tempData = new Dataset(dataset);
        for(int i=0;i<dataset.size();i++) {
            if(num!=i)
                tempData.add(dataset.get(i));
        }
        return tempData;
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



}
