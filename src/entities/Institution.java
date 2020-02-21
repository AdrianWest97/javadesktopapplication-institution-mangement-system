/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Name:Adrian West
email:awest@stu.ncu.edu.jm
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author Adrian West
 */
public class Institution implements Serializable, Comparable<Institution>  {

    private String InstitutionName;
    private String InstitutionID;
    private ArrayList<String> facultyID;
    private String Admin;
    private static final long serialVersionUID = -4237764823151802217L;

    /**
     * @return the InstitutionName
     */
    public String getInstitutionName() {
        return InstitutionName;
    }

    /**
     * @param InstitutionName the InstitutionName to set
     */
    public void setInstitutionName(String InstitutionName) {
        this.InstitutionName = InstitutionName;
    }

    /**
     * @return the InstitutionID
     */
    public String getInstitutionID() {
        return InstitutionID;
    }

    /**
     * @param InstitutionID the InstitutionID to set
     */
    public void setInstitutionID(String InstitutionID) {
        this.InstitutionID = InstitutionID;
    }

    /**
     * @return the Admin
     */
    public String getAdmin() {
        return Admin;
    }

    /**
     * @param Admin the Admin to set
     */
    public void setAdmin(String Admin) {
        this.Admin = Admin;
    }

    /**
     * @return the facultyID
     */
    public ArrayList<String> getFacultyID() {
        return facultyID;
    }

    /**
     * @param facultyID the facultyID to set
     */
    public void setFacultyID(ArrayList<String> facultyID) {
        this.facultyID = facultyID;
    }

    @Override
    public int compareTo(Institution o) {
        return this.InstitutionName.compareTo(o.getInstitutionName());
    }

}
