package dataformat;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Statistics {


    UpperExtreme(value->value.stream().sorted((x,y)->x.compareTo(y)).collect(Collectors.toList()).get(0)),
    Max(value -> value.stream().mapToDouble(Double::doubleValue).max().getAsDouble()),
    Min(value -> value.stream().mapToDouble(Double::doubleValue).min().getAsDouble()),
    Avg(value -> value.stream().mapToDouble(Double::doubleValue).average().getAsDouble()),
    Deviation(value -> Math.sqrt(
            value.stream().mapToDouble(Double::doubleValue).map(x-> {return Math.pow(x-mean(value),2);}).sum()
                    /value.stream().count()
    ));



    private Function<List<Double>, Double> expression;

    Statistics(Function<List<Double>, Double> expression){
        this.expression = expression;
    }

    public double getStats(List<Double> data){
        return expression.apply(data);
    }

    private static double mean(List<Double> data){
        return Statistics.Avg.getStats(data);
    }


}
