package example;

import dataformat.DataUtils;
import dataformat.Dataset;
import dataformat.DatasetFile;
import db.DataDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DatasetSaveExample {
    public static String inputAdd = "D:\\inseok\\javaProject\\mlcbrSystem\\in\\";
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        Dataset dSet = new Dataset(inputAdd+"190315.csv");
        ArrayList<Dataset> sets = DataUtils.sampling(dSet, 0.8);
        Dataset trainset = sets.get(0);
        Dataset testset = sets.get(1);


        FileInputStream fis = new FileInputStream("dataDB.db");
        ObjectInputStream ois = new ObjectInputStream(fis);

        DataDB ddb1 = (DataDB) ois.readObject();
        ddb1.addNewDataset(new DatasetFile(new DatasetFile.Builder(trainset).dataName("n3").dataDescriptoin("d3")));
        ddb1.saveStatus();

        ddb1.getDatasetFile(4).getDataset().printDataset();

    }

}
