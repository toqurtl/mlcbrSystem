package ga;

import cbr.CBRmodule;
import mlcbrUtils.Util1;
import java.util.ArrayList;


public class Evolution {

    public static void initialize(Optimization opti, CBRmodule cbr, int k) {
        Generation gene = initialSet(opti.iniSize, cbr.numAttributes-2, new ArrayList<double[]>());
        gene.cal(cbr, k, 4);
        opti.add(gene);
    }


    public static Generation initialSet(int iniSize, int length, ArrayList<double[]> weightList){
        Generation gene = new Generation();
        gene.add(weightList);
        int check = 0;
        while(gene.size()<iniSize) {
            Chromosomeset chro = new Generic().mutation2(length);
            if(!gene.hasChro(chro)) {
                gene.add(chro);
            }
            Util1.inifiniteCheck(check++);
        }
        return gene;
    }
    public static void performEvolution(Optimization opti, CBRmodule db, int k) {
        initialize(opti, db, k);
        opti.bestRMEs.add(opti.lastGeneration().getbestMRE());
        for(int i=0;i<opti.numGeneration;i++) {
            System.out.println(i+"th generation : ");
            opti.lastGeneration().printChromoList(0);
            opti.add(Evolution.nextGeneration(opti, db, k));
            opti.bestRMEs.add(opti.lastGeneration().getbestMRE());
        }
        db.bestWeight = opti.lastGeneration().get(0).chromosome.clone();
    }

    public static Generation nextGeneration(Optimization opti, CBRmodule db, int k){
        Generation gene = opti.lastGeneration();
        Generation newge = new Generation();
        for(int i=0;i<opti.setting.get(2);i++)
            newge.add(new Generic().elite(i,gene));

        addChromosomes(newge, opti.setting.get(0), (x)->x.selection(gene));
        addChromosomes(newge, opti.setting.get(1), (x)->x.crossover(x.selection(gene), x.selection(gene)));
        addChromosomes(newge, opti.setting.get(3), (x)->x.mutation(gene, opti.setting.get(3)));
        addChromosomes(newge, opti.setting.get(4), (x)->x.mutation2(gene.get(0).chromosome.length));
        newge.cal(db, k, 4.0);
        return newge;
    }

    public static void addChromosome(Generation newge, genericOperation ope){
        newge.add(ope.getChromosome(new Generic()));
    }
    public static void addChromosomes(Generation newge, int limit, genericOperation ope){
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

    @FunctionalInterface
    public interface genericOperation{
        public Chromosomeset getChromosome(Generic gene);
    }
}
