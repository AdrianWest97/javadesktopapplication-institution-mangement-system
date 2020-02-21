/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readWrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource<T> {

    public boolean writeObject(T obj, String filename) {
        ObjectOutputStream out = null;
        File toWrite = new File(filename);
        //check if the file exits
        if (!toWrite.exists()) {
            try {
                toWrite.getAbsoluteFile().getParentFile().mkdir();
                toWrite.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            out = new ObjectOutputStream(new FileOutputStream(toWrite));
            out.writeObject(obj);
            out.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    //read data from a file
    public T readObject(String filename) {
        ObjectInputStream ois = null;
        File toRead = new File(filename);
        if (toRead.exists() && toRead.length() > 0) {
            try {
                ois = new ObjectInputStream(new FileInputStream(filename));
            } catch (IOException ex) {
                Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
            T obj;
            try {
                obj = (T) ois.readObject();
                ois.close();
                return obj;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
        return null;
    }

}
