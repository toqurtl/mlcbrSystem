package cbr;

import dataformat.Data;
import mlcbrUtils.Util1;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CBRModel implements Serializable {
    CBRmodule cbr;
    int k;
    String modelName=" ";
    String modelID=" ";
    String modelDescription =" ";
    double trainError;
    double testError;
    double[] bestWeight;
    private String numofData;
    private String numofAttributes;
    private String classAttri;

    public CBRModel(Builder builder){
        this.cbr = builder.cbr;
        this.k = builder.k;
        this.trainError = builder.trainError;
        this.modelID = builder.modelID;
        this.modelName = builder.modelName;
        this.modelDescription = builder.modelDescription;
        this.bestWeight = builder.bestWeight;
        this.numofData = String.valueOf(builder.cbr.dataset.size());
        this.numofAttributes = String.valueOf((builder.cbr.dataset.weightLength));
        this.classAttri = builder.cbr.dataset.attributes.get(builder.cbr.dataset.classAttri);
    }

    public CBRmodule getCBR(){
        return cbr;
    }

    public void setID(int ID){
        this.modelName = String.valueOf(ID);
    }

    public void saveFile(String filename) throws IOException {
        Util1.saveFile(filename+".cbr", this);
    }

    public double reuse(Data d){
        if(isFittedFormat(d)){
            return cbr.reuse(this.k, d, this.bestWeight);
        }else{
            System.out.println("input data is not fit with dataset in the model");
            return 0.0;
        }
    }

    public ArrayList<Data> retrieve(Data d){
        if(isFittedFormat(d)){
            return new ArrayList<Data>(cbr.retrieve(this.k, d, this.bestWeight).stream().map(x->x.d).collect(Collectors.toList()));
        }else{
            System.out.println("input data is not fit with dataset in the model");
            return null;
        }

    }

    public ArrayList<String> getInfo(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add(modelID);
        temp.add(modelName);
        temp.add(modelDescription);
        temp.add(String.valueOf(trainError));
        temp.add(String.valueOf(k));
        temp.add(numofData);
        temp.add(numofAttributes);
        temp.add(classAttri);
        return temp;
    }

    public boolean isFittedFormat(Data d){
        return cbr.dataset.isFitData(d);
    }

    public static class Builder{
        CBRmodule cbr;
        int k;
        double trainError;
        double[] bestWeight;

        String modelName=" ";
        String modelID=" ";
        String modelDescription =" ";

        public Builder(CBRmodule cbr, int k, double trainError){
            this.cbr = cbr;
            this.k = k;
            this.trainError = trainError;
            this.bestWeight = cbr.bestWeight;
        }
        public Builder modelID(String modelID){
            this.modelName = modelID;
            return this;
        }

        public Builder modelName(String modelName){
            this.modelName = modelName;
            return this;
        }
        public Builder modelDescription(String modelDescription){
            this.modelName = modelDescription;
            return this;
        }

    }
}
