/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Name:Adrian West
email:awest@stu.ncu.edu.jm
 */
package entities;

import Interface.FileInterface;
import Superclass.Person;
import java.util.HashMap;
import readWrite.DataSource;

/**
 *
 * @author Adrian West
 */
public class Admin extends Person {

    private static final long serialVersionUID = -4256245022580969700L;

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    //register admin
    public boolean registerAdmin(String fn, String ln, String gender, String email, String pass) {
        HashMap<String, Admin> admin;
        Admin a = new Admin();
        a.setFirstName(fn);
        a.setLastName(ln);
        a.setGender(gender);
        //   a.setDOB(dob.toString());
        a.setEmail(email);
        a.setPassword(pass);
        boolean flag = false;

        if (new DataSource().readObject(FileInterface.CREDENTIALS_ADMIN) != null) {
            admin = (HashMap<String, Admin>) new DataSource().readObject(FileInterface.CREDENTIALS_ADMIN);
            //admin.put(email, a);
            //check if user exits
            if (admin.get(email) != null) {
                flag = true;
            }
        } else {
            admin = new HashMap<>();
        }

        if (!flag) {
            admin.put(email, a);
            return new DataSource().writeObject(admin, FileInterface.CREDENTIALS_ADMIN);
        }
        return false;
    }
}
