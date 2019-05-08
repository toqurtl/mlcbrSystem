package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DBUtil {
    public static String cbradd = "cbrDB.db";
    public static String dataadd = "dataDB.db";
    public static CbrDB getCbrDB() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(cbradd);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (CbrDB) ois.readObject();
    }

    public static DataDB getDataDB() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(dataadd);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (DataDB) ois.readObject();
    }

    public String getCBRadd(){
        return cbradd;
    }
    public String getDataadd(){
        return dataadd;
    }
}
