/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Name:Adrian West
email:awest@stu.ncu.edu.jm
 */
package entities;

import readWrite.DataSource;
import Interface.FileInterface;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Adrian West
 */
public class Programme implements Serializable, Comparable<Programme>{

    private String progName;
    private String progID;
    private String facultyID;
    private static final long  serialVersionUID = -3345887634440824160L;

    /**
     * @return the progName
     */
    public String getProgName() {
        return progName;
    }

    /**
     * @param progName the progName to set
     */
    public void setProgName(String progName) {
        this.progName = progName;
    }

    /**
     * @return the progID
     */
    public String getProgID() {
        return progID;
    }

    /**
     * @param progID the progID to set
     */
    public void setProgID(String progID) {
        this.progID = progID;
    }

    public boolean addPrograme(String progID, String progName, String facultyID) {
        Programme p = new Programme();
        p.setProgID(progID);
        p.setProgName(progName);
        p.setFacultyID(facultyID);

        ArrayList<Programme> aList = null;
        if (new DataSource().readObject(FileInterface.PROGRAMMES) != null) {
            aList = (ArrayList<Programme>) new DataSource().readObject(FileInterface.PROGRAMMES);

        } else {
            aList = new ArrayList();
        }
        aList.add(p);
        //insert object
        return new DataSource().writeObject(aList, FileInterface.PROGRAMMES);

    }

    /**
     * @return the facultyID
     */
    public String getFacultyID() {
        return facultyID;
    }

    /**
     * @param facultyID the facultyID to set
     */
    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    @Override
    public int compareTo(Programme o) {
        return this.progName.compareTo(o.getProgName());
    }
}
