package ga;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import cbr.CBRmodule;

public class Generation extends ArrayList<Chromosomeset> implements Serializable{
    double badfit;
    double goodfit;

    public Generation(){}
    //for deepcopy
    public Generation(Generation gene) {
        gene.forEach(x->this.add(x));
    }

    public void sorting(){
        this.sort((x,y)->Double.compare(x.errorMean,y.errorMean));
    }

    public void cal(CBRmodule db, int k, double pressure){
        calculateError(db, k);
        calculateFitness(pressure);
        sorting();
    }

    public double getbestMRE(){
        return this.stream().sorted((x,y)->Double.compare(x.errorMean, y.errorMean)).collect(Collectors.toList()).get(0).errorMean;
    }

    private void calculateError(CBRmodule db, int k) {
        this.forEach(x-> {
            x.setErrorMean(db, k);
        });
        setBadFit();
        setGoodFit();
    }

    private void calculateFitness(double pressure) {
        this.forEach(x->x.setFitness(calculateFitness(x, pressure)));
    }

    private double calculateFitness(Chromosomeset chro, double pressure){
        return ((getBadFit()-chro.errorMean)+(getBadFit()-getGoodFit()))/(pressure-1);
    }

    public ArrayList<Chromosomeset> getSortedChromoList(){
        return new ArrayList<Chromosomeset>(
                this.stream()
                .sorted((x,y)->Double.compare(x.errorMean, y.errorMean))
                .collect(Collectors.toList())
        );
    }

    public double getBadFit(){ return badfit;}
    public double getGoodFit(){return goodfit;}


    public ArrayList<Chromosomeset> getBestChromoList(int num) {
        return new ArrayList<Chromosomeset>(this.stream()
                .sorted((x, y) -> Double.compare(x.errorMean, y.errorMean))
                .limit(num)
                .collect(Collectors.toList()));
    }

    public void printChromoList(int num) {
        this.get(0).printChromosome();
    }

    public void setBadFit(){
        badfit = getSortedChromoList().get(this.size()-1).errorMean;
    }

    public void setGoodFit(){
        goodfit =  getSortedChromoList().get(0).errorMean;
    }

    public boolean hasChro(Chromosomeset newchro) {
        boolean temp = false;
        for(Chromosomeset chro : this){
            if(newchro.compareTo(chro)){
                temp = true;
                break;
            }
        }
        return temp;
    }

    public void add(ArrayList<double[]> weightList){
        weightList.forEach(x->this.add(new Chromosomeset(x)));
    }
}
