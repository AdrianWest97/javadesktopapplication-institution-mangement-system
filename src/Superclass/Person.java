/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Name:Adrian West
email:awest@stu.ncu.edu.jm
 */
package Superclass;

import Interface.PersonInterface;
import java.io.Serializable;


public class Person implements PersonInterface,Serializable{
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String ID;
    private String email;

    /**
     * @return the firstName
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    @Override
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the dob
     */
    @Override
    public String getDOB() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    @Override
    public void setDOB(String dob) {
        this.dob = dob;
    }

    /**
     * @return the ID
     */
    @Override
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    @Override
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }



}
