/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Name:Adrian West
email:awest@stu.ncu.edu.jm
 */
package Interface;


public interface PersonInterface {
    String getFirstName();
    String getLastName();
    String getGender();
    String getDOB();
    String getID();
    String getEmail();
    
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setGender(String gender);
    void setDOB(String dob);
    void setID(String Id);
    void setEmail(String email);
    
}
