package dataformat;

import mlcbrUtils.Util1;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class DatasetFile implements Serializable {

    private String dataID;
    private String dataName;
    private String dataDescription;
    private String numofData;
    private String numofAttributes;
    private String classAttri;
    private Dataset dataset;

    public DatasetFile(Builder builder){
        this.dataID = builder.dataID;
        this.dataName = builder.dataName;
        this.dataDescription = builder.dataDescription;
        this.dataset = builder.dataset;
        this.numofData = String.valueOf(builder.dataset.size());
        this.numofAttributes = String.valueOf((builder.dataset.attributes.size()-2));
        this.classAttri = builder.dataset.attributes.get(builder.dataset.classAttri);
    }

    public void setID(int id){
        this.dataID = String.valueOf(id);
    }
    public ArrayList<String> getInfo(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add(dataID);
        temp.add(dataName);
        temp.add(dataDescription);
        temp.add(numofData);
        temp.add(numofAttributes);
        temp.add(classAttri);
        return temp;
    }

    public String getID(){
        return dataID;
    }
    public String getName(){
        return dataName;
    }
    public String getDescription(){
        return dataDescription;
    }
    public Dataset getDataset(){
        return dataset;
    }

    public void saveFile(String filename) throws IOException {
        Util1.saveFile(filename+"data", this);
    }


    public static class Builder{
        private String dataID;
        private String dataName;
        private String dataDescription;
        private Dataset dataset;
        public Builder(Dataset dataset){
            this.dataset = dataset;
        }

        public Builder dataID(String dataID){
            this.dataID = dataID;
            return this;
        }

        public Builder dataName(String dataName){
            this.dataName = dataName;
            return this;
        }

        public Builder dataDescriptoin(String dataDescription){
            this.dataDescription = dataDescription;
            return this;
        }
    }
}
