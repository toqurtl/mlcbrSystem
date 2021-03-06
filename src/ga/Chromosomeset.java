package ga;

import cbr.CBRmodule;
import mlcbrUtils.Util1;

public class Chromosomeset {

    public double[] chromosome;
    public double errorMean;
    public double fitness;
    public boolean isCalculated = false;

    public Chromosomeset(double[] chromosome) {
        this.chromosome = chromosome.clone();
    }

    public Chromosomeset(Chromosomeset newchro) {
        this.chromosome = newchro.chromosome.clone();
        this.errorMean = newchro.errorMean;
        this.fitness = newchro.fitness;
        this.isCalculated = newchro.isCalculated;
    }

    public void setErrorMean(CBRmodule cbr, int k){
        this.errorMean = getErrorMean(cbr, k);
    }
    public double getErrorMean(CBRmodule cbr, int k){
        return cbr.trainError(k, this.chromosome);
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    public void printChromosome() {
        Util1.printDoubleArray(chromosome, 5);
        System.out.print(" error : "+errorMean+" fitness : "+fitness);
        System.out.println();
    }
    public boolean compareTo(Chromosomeset newchro) {
        return GaUtils.compareTo(this.chromosome, newchro.chromosome);
    }
    public void printInfo() {
        printChromosome();
        System.out.println("  errorRate : "+Util1.pointPrint(errorMean, 4)+"   fitness : "+Util1.pointPrint(fitness,4)+" ");
    }


}
