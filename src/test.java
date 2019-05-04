

import cbr.CBRUtils;
import cbr.CBRmodule;
import dataformat.Data;
import dataformat.DataUtils;
import dataformat.Dataset;

import java.util.ArrayList;
import java.util.Iterator;

public class test {

    public static String inputAdd;
    public static int size = 20;
    public static int length = 10;


    public static void main(String[] args){
        Dataset dSet = DataUtils.randomDataSetGenerator(size, length);
        Data d = DataUtils.randomDataGenerator(length);
        double[] weight = CBRUtils.getRandomWeight(length-2);
        CBRmodule cbr = new CBRmodule(dSet);
        ArrayList dd = cbr.retrieve(5, dSet.get(0));


    }

    public static void UISaveCBRExample1(){

    }


}
