/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readWrite;

import entities.Admin;


public class SignedIn {
    private static Admin admin;

    /**
     * @return the admin
     */
    public static Admin getAdmin() {
        return admin;
    }

    /**
     * @param aAdmin the admin to set
     */
    public static void setAdmin(Admin aAdmin) {
        admin = aAdmin;
    }
}
