package db;

import cbr.CBRModel;
import dataformat.DatasetFile;
import mlcbrUtils.Util1;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class CbrDB implements Serializable {
    String dbName = "cbrDB.db";
    LinkedHashMap<Integer, CBRModel> db = new LinkedHashMap<>();
    public CbrDB(){
    }

    public void saveStatus() throws IOException {
        Util1.saveFile(dbName, this);
    }

    public void addNewModel(CBRModel cbr){
        if(db.size()==0){
            cbr.setID(0);
            db.put(0,cbr);
        }else{
            cbr.setID(db.size());
            db.put(db.size(), cbr);
        }
    }

    public void deleteModel(int id){
        if(isContainID(id)){
            int size = db.size();
            db.remove(id);
            for(int i=id+1;i<size;i++)
                db.put(i-1, db.get(i));
            db.remove(db.size()+1);
        }else{
            System.out.println("there is no dataset file with id " +id);

        }
    }

    public int getSize(){
        return db.size();
    }

    public ArrayList<ArrayList<String>> getDatabaseInformation(int id){
        if(isContainID(id)){
            CBRModel df = db.get(id);
            return new ArrayList<>(db.keySet().stream().map(x->db.get(x).getInfo()).collect(Collectors.toList()));
        }else{
            System.out.println("there is no dataset file with id " +id);
            return null;
        }
    }

    public CBRModel getDatasetFile(int id){
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