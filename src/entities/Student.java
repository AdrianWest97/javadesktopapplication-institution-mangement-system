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
import Superclass.Person;
import java.util.HashMap;

/**
 *
 * @author Adrian West
 */

public class Student extends Person {

    private String intID;
    private String progID;
    private String FacultyID;
    private String photo;

    public boolean RegisterStudent(Student stud) {
        //check is student already exits
        HashMap<String, Student> studMap = null;
        if (new DataSource().readObject(FileInterface.STUDENTS) != null) {
            //add student
            studMap = (HashMap<String, Student>) new DataSource().readObject(FileInterface.STUDENTS);

        } else {
            studMap = new HashMap();
        }
        studMap.put(stud.getID(), stud);
        return new DataSource().writeObject(studMap, FileInterface.STUDENTS);
    }

    public boolean studentExits(String id) {
        //check is student already exits
        HashMap<String, Student> studMap = null;
        if (new DataSource().readObject(FileInterface.STUDENTS) != null) {
            //add student
            studMap = (HashMap<String, Student>) new DataSource().readObject(FileInterface.STUDENTS);
            if (studMap.get(id.toLowerCase()) != null) {
                return true;
            } else {
                return false;
            }
        }

        return false;
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

    /**
     * @return the intID
     */
    public String getIntID() {
        return intID;
    }

    /**
     * @param intID the intID to set
     */
    public void setIntID(String intID) {
        this.intID = intID;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
