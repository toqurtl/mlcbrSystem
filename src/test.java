

import cbr.CBRUtils;
import cbr.CBRmodule;
import dataformat.Data;
import dataformat.DataUtils;
import dataformat.Dataset;
import ga.*;
import mlcbrUtils.Util1;

import java.io.IOException;

public class test {

    public static String inputAdd = "D:\\inseok\\javaProject\\mlcbrSystem\\in\\";
    public static int size = 20;
    public static int length = 10;


    public static void main(String[] args) throws IOException {
        fileTest();
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
