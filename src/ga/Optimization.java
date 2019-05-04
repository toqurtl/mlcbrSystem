package ga;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import cbr.CBRmodule;
import mlcbrUtils.Util1;

public class Optimization extends ArrayList<Generation> implements Serializable {
    public ArrayList<Double> bestRMEs = new ArrayList<Double>();

    int iniSize = 100;
    int numGeneration = 10;
    LinkedHashMap<Integer, Integer> setting = new LinkedHashMap<>();

    public Optimization(GaBuilder builder) {
        builder.build(this);
    }

    public void saveGenerations(String filename) throws FileNotFoundException, IOException {
        Util1.saveFile(filename, this);
    }

    private void evolSetting(int numGeneration, int iniSize){
        this.iniSize = iniSize;
        this.numGeneration = numGeneration;
    }

    private void gaSetting(double numSelect, double numCross, double numElite, double numMutate, double numMutate2) {
        LinkedHashMap<Integer, Double> portionSetting = new LinkedHashMap<>();
        portionSetting.put(0, numSelect);
        portionSetting.put(1, numCross);
        portionSetting.put(2, numElite);
        portionSetting.put(3, numMutate);
        portionSetting.put(4, numMutate2);
        int sum = 0;
        for(int i=0;i<4;i++) {
            int check = (int) Math.floor(portionSetting.get(i)*iniSize);
            sum+=check;
            setting.put(i, check);
        }
        setting.put(4, iniSize-sum);
    }




    public void printBestResult() {
        this.get(this.size()-1).printChromoList(1);
    }
    public void printGeneration(int num){
        this.get(num).printChromoList(this.size());
    }

    public class GaBuilder{
        private double numSelect = 0.1;
        private double numCross = 0.3;
        private double numElite = 0.1;
        private double numMutate = 0.3;
        private double numMutate2 = 0.2;
        private int iniSize = 50;
        private int numGeneration = 50;

        public GaBuilder(int iniSize, int numGeneration){
           evolSetting(numGeneration, iniSize);
        }

        public GaBuilder numSelect(double val){
            this.numSelect = val;
            return this;
        }
        public GaBuilder numCross(double val){
            this.numSelect = val;
            return this;
        }
        public GaBuilder numElite(double val){
            this.numSelect = val;
            return this;
        }
        public GaBuilder numMutate(double val){
            this.numSelect = val;
            return this;
        }
        public GaBuilder numMutate2(double val){
            this.numSelect = val;
            return this;
        }
        public void build(Optimization opti){
            opti.gaSetting(numSelect, numCross, numElite, numMutate, numMutate2);
        };
    }



}
