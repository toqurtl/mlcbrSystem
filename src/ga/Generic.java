package ga;

import mlcbrUtils.Util1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generic implements Serializable {

    public Generic(){
    };


    public Chromosomeset selection(Generation gene) {
        double sumofFitness = 0;
        ArrayList<Chromosomeset> tempList = new ArrayList<Chromosomeset>(gene);
        for(Chromosomeset chro : tempList) {
            sumofFitness += chro.fitness;
        }
        double point = new Random(System.nanoTime()).nextDouble()*sumofFitness;
        double sum =0;
        int check = 0;
        while(sum<point) {
            sum+=tempList.get(check).fitness;
            check++;
        }
        try {
            return tempList.get(check);
        }catch(IndexOutOfBoundsException e) {
            return tempList.get(check-1);
        }
    }
    public Chromosomeset crossover(Generation gene){
        Chromosomeset chr1 = selection(gene);
        Chromosomeset chr2 = selection(gene);
        Integer infinitecheck = 0;
        while(chr1.compareTo(chr2)){
            chr2 = selection(gene);
            Util1.inifiniteCheck(infinitecheck);
        }
        return this.crossover(chr1, chr2);
    }

    public Chromosomeset elite(int num, Generation gene) {
        return gene.get(num);
    }

    public Chromosomeset mutation(Generation gene, int num){
        return mutation(gene.get(num));
    }

    public Chromosomeset mutation2(int num) {
        double[] cc = new double[num];
        double sum = 0;
        for(int i=0;i<num;i++) {
            cc[i] = new Random(System.nanoTime()).nextDouble();
            sum+=cc[i];
        }
        for(int i=0;i<num;i++) {
            cc[i] = cc[i]/sum;
        }
        return new Chromosomeset(cc);
    }

    public Chromosomeset crossover(Chromosomeset chr1, Chromosomeset chr2) {
        int check = chr1.chromosome.length;
        double[] newWeight = new double[check];
        for(int i=0;i<check;i++) {
            newWeight[i] = (chr1.chromosome[i]+chr2.chromosome[i])/2;
        }
        Chromosomeset newchro = new Chromosomeset(makesum1(newWeight));
        return newchro;
    }
    public Chromosomeset crossover(Chromosomeset chr1, Chromosomeset chr2, Random rand) {
        int check = chr1.chromosome.length;
        double prob = rand.nextDouble();
        double[] newWeight = new double[check];
        for(int i=0;i<check;i++) {
            if(new Random(System.nanoTime()).nextBoolean()) {
                newWeight[i] = chr1.chromosome[i]*prob+chr2.chromosome[i]*(1-prob);
            }else {
                newWeight[i] = chr1.chromosome[i];
            }

        }
        Chromosomeset newchro = new Chromosomeset(makesum1(newWeight));
        return newchro;
    }






    public Chromosomeset mutation(Chromosomeset chro) {
        int size = chro.chromosome.length;
        double[] cc = new double[size];
        for(int i=0;i<size;i++) {
            cc[i] = chro.chromosome[i];
        }
        for(int i=0;i<size;i++) {
            boolean isMutate = new Random().nextBoolean();
            if(isMutate) {
                if(new Random().nextBoolean()) {
                    cc[i] = cc[i]+0.0001;
                }else {
                    cc[i] = Math.abs(cc[i]-0.0001);
                }
            }
        }
        return new Chromosomeset(makesum1(cc));
    }




    public double[] makesum1(double[] weight) {
        int length = weight.length;
        double[] newWeight = new double[length];
        double sum = 0;
        for(int i=0;i<length;i++) {
            sum += weight[i];
        }
        for(int i=0;i<length;i++) {
            newWeight[i] = weight[i]/sum;
        }
        return newWeight;
    }

}

