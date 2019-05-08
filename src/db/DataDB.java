package db;
import dataformat.DatasetFile;
import mlcbrUtils.Util1;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class DataDB implements Serializable {
    String dbName = "dataDB.db";
    LinkedHashMap<Integer, DatasetFile> db = new LinkedHashMap<>();
    public DataDB(){
    }

    public void saveStatus() throws IOException {
        Util1.saveFile(dbName, this);
    }

    public void addNewDataset(DatasetFile dataset) throws IOException{
        if(db.size()==0){
            dataset.setID(0);
            db.put(0,dataset);
        }else{
            dataset.setID(db.size());
            db.put(db.size(), dataset);
        }
        saveStatus();
    }

    public void deleteDataset(int id) throws IOException{
        if(isContainID(id)){
            int size = db.size();
            db.remove(id);
            for(int i=id+1;i<size;i++)
                db.put(i-1, db.get(i));
            db.remove(db.size()+1);
        }else{
            System.out.println("there is no dataset file with id " +id);
        }
        saveStatus();
    }

    public int getSize(){
        return db.size();
    }

    public ArrayList<ArrayList<String>> getDatabaseInformation(int id){
        if(isContainID(id)){
            DatasetFile df = db.get(id);
            return new ArrayList<>(db.keySet().stream().map(x->db.get(x).getInfo()).collect(Collectors.toList()));
        }else{
            System.out.println("there is no dataset file with id " +id);
            return null;
        }
    }

    public DatasetFile getDatasetFile(int id){
        if(isContainID(id)){
            return db.get(id);
        }else{
            System.out.println("there is no dataset file with id " +id);
            return null;
        }
    }

    public boolean isContainID(int id){
        return getIDList().contains(id);
    }

    private ArrayList<Integer> getIDList(){
        return new ArrayList<Integer>(db.keySet().stream().collect(Collectors.toList()));
    }
}

