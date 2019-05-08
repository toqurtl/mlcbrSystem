package dataformat;

import mlcbrUtils.Util1;

import java.io.IOException;

public class DatasetFile {

    private String dataID;
    private String dataName;
    private String dataDescription;
    private Dataset dataset;

    public DatasetFile(Builder builder){
        this.dataID = builder.dataID;
        this.dataName = builder.dataName;
        this.dataDescription = builder.dataDescription;
        this.dataset = builder.dataset;
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
            this.dataID = dataName;
            return this;
        }

        public Builder dataDescriptoin(String dataDescription){
            this.dataID = dataDescription;
            return this;
        }
    }
}