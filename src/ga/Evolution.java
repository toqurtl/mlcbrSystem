package ga;

import cbr.CBRmodule;
import mlcbrUtils.Util1;

import java.util.ArrayList;
import java.util.Random;

public class Evolution {

    public static void initialize(Optimization opti, CBRmodule cbr, int k) {
        Generation gene = initialSet(opti.iniSize, cbr.numAttributes-2, new ArrayList<double[]>());
        gene.cal(cbr, k, 4);
        opti.add(gene);
    }


    public static Generation initialSet(int iniSize, int length, ArrayList<double[]> weightList){
        Generation gene = new Generation();
        gene.add(weightList);
        Integer check =0;
        while(gene.size()<iniSize) {
            Chromosomeset chro = new Generic().mutation2(length);
            if(gene.hasChro(chro)) {
                gene.add(chro);
                Util1.inifiniteCheck(check);
            }
        }
        return gene;
    }
    public static void performEvolution(Optimization opti, CBRmodule db, int k) {
        for(int i=0;i<opti.numGeneration;i++) {
            Generation newge = new Generation(opti.get(opti.size()-1));
            System.out.println(i+"th generation : ");
            newge.printChromoList(1);
            opti.add(Evolution.nextGeneration(opti, newge, db, k));
        }
        db.bestWeight = opti.get(opti.size()-1).get(0).chromosome.clone();
    }

    public static Generation nextGeneration(Optimization opti, Generation gene, CBRmodule db, int k){
        Generation newge = new Generation();
        Generic ge = new Generic();
        gene.sorting();

        addChromosomes(newge, opti.setting.get(0), (x)->x.selection(gene));
        addChromosomes(newge, opti.setting.get(1), (x)->x.crossover(x.selection(gene), x.selection(gene)));
        addChromosomes(newge, opti.setting.get(2), (x)->x.elite(opti.setting.get(2), gene));
        addChromosomes(newge, opti.setting.get(3), (x)->x.mutation(gene, 10));
        addChromosomes(newge, opti.setting.get(4), (x)->x.mutation2(gene.get(0).chromosome.length));

        newge.cal(db, k, 4);
        return newge;
    }

    public static void addChromosomes(Generation newge, int num, genericOperation ope){
        int check = 0;
        Integer infiniteCheck = 0;
        while(check<num) {
            Chromosomeset chro = ope.getChromosome(new Generic());
            if(!newge.hasChro(chro)) {
                newge.add(chro);
                check++;
            }
            Util1.inifiniteCheck(infiniteCheck);
        }
    }

    @FunctionalInterface
    public interface genericOperation{
        public Chromosomeset getChromosome(Generic gene);
    }
}
