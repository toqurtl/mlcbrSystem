package dataformat;

import java.util.List;
import java.util.stream.Collectors;

public class Stats {



    public static double getAvg(List<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
    }

    public static double getMax(List<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
    }

    public static double getMin(List<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
    }

    public static double getDevi(List<Double> list) {
        double avg = getAvg(list);
        double sum = list.stream().mapToDouble(Double::doubleValue).map(x -> {
            return Math.pow(x - avg, 2);
        }).sum();
        return Math.sqrt(sum / avg);
    }

    public static double upperExtreme(List<Double> list, double point){
        return quantile(list,point);
    }

    public static double lowerExtreme(List<Double> list, double point){
        return quantile(list,1-point);
    }


    public static double quantile(List<Double> list, double point){
        List<Double> temp = list.stream().sorted((x,y)->y.compareTo(x)).collect(Collectors.toList());
        return temp.get(getOrder(point, temp.size()));
    }

    public static int getOrder(double point, int size){
        int value = 0;
        if(Double.compare(point, 1.0)==0){
            value = size-1;
        }else{
            for(int i=0;i<size;i++){
                if(Double.compare(point,((i+1.0)/size))==-1){
                    value = i;
                    break;
                }
            }
        }
        return value;
    }

}
