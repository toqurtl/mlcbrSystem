package ga;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import mlcbrUtils.Util1;

public class Optimization extends ArrayList<Generation> implements Serializable {
    public ArrayList<Double> bestRMEs = new ArrayList<Double>();
    int iniSize;
    int numGeneration;
    LinkedHashMap<Integer, Integer> setting = new LinkedHashMap<>();

    private Optimization(GaBuilder builder) {
        evolSetting(builder.iniSize, builder.numGeneration);
        gaSetting(builder.numSelect, builder.numCross, builder.numElite, builder.numMutate, builder.numMutate2);
    }

    public Generation lastGeneration(){
        return this.get(this.size()-1);
    }

    public void saveGenerations(String filename) throws FileNotFoundException, IOException {
        Util1.saveFile(filename, this);
    }

    protected void evolSetting(int iniSize, int numGeneration){
        this.iniSize = iniSize;
        this.numGeneration = numGeneration;
    }

    protected void gaSetting(double numSelect, double numCross, double numElite, double numMutate, double numMutate2) {
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

    public static class GaBuilder{
        private double numSelect = 0.1;
        private double numCross = 0.3;
        private double numElite = 0.1;
        private double numMutate = 0.3;
        private double numMutate2 = 0.2;
        private int iniSize;
        private int numGeneration;

        public GaBuilder(int iniSize, int numGeneration){
            this.iniSize = iniSize;
            this.numGeneration = numGeneration;
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
        public Optimization build(){
            return new Optimization(this);
        };
    }



}
