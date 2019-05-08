package example;

import cbr.CBRModel;
import cbr.CBRmodule;
import dataformat.DataUtils;
import dataformat.Dataset;
import db.CbrDB;
import db.DBUtil;
import ga.*;
import mlcbrUtils.Util1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class basicProcess {
    public static String inputAdd = "D:\\inseok\\javaProject\\mlcbrSystem\\in\\";
    public static int size = 20;
    public static int length = 10;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Dataset dSet = new Dataset(inputAdd+"190315.csv");
        ArrayList<Dataset> sets = DataUtils.sampling(dSet, 0.8);
        Dataset trainset = sets.get(0);
        Dataset testset = sets.get(1);

        CBRmodule cbr = new CBRmodule(trainset);

        Optimization opti = new Optimization.GaBuilder(50, 5)
                .numCross(0.3)
                .numElite(0.1)
                .numMutate(0.2)
                .numMutate2(0.2)
                .numSelect(0.2)
                .build();

        Evolution.performEvolution(opti, cbr, 5);
    }

    public static Chromosomeset addChromosome(Evolution.genericOperation ope){
        return ope.getChromosome(new Generic());
    }
    public static void addChromosomes(Generation newge, int limit, Evolution.genericOperation ope){
        int check = 0;
        int infiniteCheck = 0;
        while(check<limit) {
            Chromosomeset chro = ope.getChromosome(new Generic());
            if(!newge.hasChro(chro)) {
                newge.add(chro);
                check++;
            }
            Util1.inifiniteCheck(infiniteCheck++);
        }
    }

    public static void randomTest(){
        Dataset dSet = DataUtils.randomDataSetGenerator(size, length);

        CBRmodule cbr = new CBRmodule(dSet);
        Optimization opti = new Optimization.GaBuilder(100, 100)
                .numCross(0.3)
                .numElite(0.1)
                .numMutate(0.2)
                .numMutate2(0.2)
                .numSelect(0.2)
                .build();

        Evolution.performEvolution(opti, cbr, 5);
        System.out.println(opti.lastGeneration().size());
        opti.lastGeneration().forEach(x->x.printChromosome());
    }

    public static void fileTest() throws IOException{
        Dataset dSet = new Dataset(inputAdd+"190315.csv");
        CBRmodule cbr = new CBRmodule(dSet);
        Optimization opti = new Optimization.GaBuilder(50, 100)
                .numCross(0.3)
                .numElite(0.1)
                .numMutate(0.2)
                .numMutate2(0.2)
                .numSelect(0.2)
                .build();
        Evolution.performEvolution(opti, cbr, 5);
        System.out.println(opti.lastGeneration().size());
        opti.lastGeneration().forEach(x->x.printChromosome());
    }

}
