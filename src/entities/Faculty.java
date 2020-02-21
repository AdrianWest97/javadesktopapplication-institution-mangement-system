/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Name:Adrian West
email:awest@stu.ncu.edu.jm
 */
package entities;

/**
 *
 * @author Adrian West
 */

import readWrite.DataSource;
import Interface.FileInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Faculty implements Serializable, Comparable<Faculty> {

    private String FacultyName;
    private String FacultyID;
    private ArrayList<String> programID;

    /**
     * @return the FacultyName
     */
    public String getFacultyName() {
        return FacultyName;
    }

    /**
     * @param FacultyName the FacultyName to set
     */
    public void setFacultyName(String FacultyName) {
        this.FacultyName = FacultyName;
    }

    /**
     * @return the FacultyID
     */
    public String getFacultyID() {
        return FacultyID;
    }

    /**
     * @param FacultyID the FacultyID to set
     */
    public void setFacultyID(String FacultyID) {
        this.FacultyID = FacultyID;
    }

    public boolean addFaculty(String facultyID, String facultyName) {
        HashMap<String, String> map = null;
        if (new DataSource().readObject(FileInterface.FACULTY) != null) {
            map = (HashMap<String, String>) new DataSource().readObject(FileInterface.FACULTY);
            //get programID List

        } else {
            map = new HashMap();
        }
        map.put(facultyID.toLowerCase(), facultyName);
        //insert object
        if (new DataSource().writeObject(map, FileInterface.FACULTY)) {
            System.out.println("INSERTED");
            return true;
        }
        return false;

    }
    
  

    /**
     * @return the programID
     */
    public ArrayList<String> getProgramID() {
        return programID;
    }

    /**
     * @param programID the programID to set
     */
    public void setProgramID(ArrayList<String> programID) {
        this.programID = programID;
    }

    @Override
    public int compareTo(Faculty o) {
        return this.FacultyName.compareTo(o.getFacultyName());
    }

}
