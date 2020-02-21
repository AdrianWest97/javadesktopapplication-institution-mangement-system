/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalForms;

import readWrite.DataSource;
import readWrite.SignedIn;
import Interface.FileInterface;
import entities.Institution;
import entities.Programme;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class RegisterProgram extends javax.swing.JInternalFrame {

    /**
     * Creates new form RegisterProgram
     */
    public RegisterProgram() {
        initComponents();
          // remove northPane
        Container pane = ((BasicInternalFrameUI) this.getUI()).getNorthPane();
        pane.remove(0);
        pane.getComponent(0).setVisible(false);
        loadData();
        universityListSelected();
    }

    private void loadData() {
        HashMap<String, Institution> institution = null;
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("Select University");
        if (new DataSource().readObject(FileInterface.INSTITUTIONS_FILE) != null) {
            //populate fields
            institution = (HashMap<String, Institution>) new DataSource().readObject(FileInterface.INSTITUTIONS_FILE);

            universityList.setModel(boxModel);

            for (Map.Entry<String, Institution> key : institution.entrySet()) {
                if (key.getValue().getAdmin().equals(SignedIn.getAdmin().getEmail())) {
                    boxModel.addElement("(" + key.getKey().toUpperCase() + ") - " + key.getValue().getInstitutionName());
                }

            }
        }
    }

    public String returnKey(String text) {
        if (text != "") {
            text = text.substring(text.indexOf("(") + 1);
            text = text.substring(0, text.indexOf(")"));
            return text;
        }
        return null;
    }

    public final void universityListSelected() {
        universityList.addItemListener(new ItemListener() {
            String a;
            DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    facultyList.setModel(comboBoxModel);
                    if (!"Select University".equals(universityList.getSelectedItem().toString())) {
                        a = returnKey(universityList.getSelectedItem().toString());

                        //search list
                        HashMap<String, Institution> hashMap = null;
                        if (new DataSource().readObject(FileInterface.INSTITUTIONS_FILE) != null) {
                            hashMap = (HashMap<String, Institution>) new DataSource().readObject(FileInterface.INSTITUTIONS_FILE);

                            for (Map.Entry<String, Institution> key : hashMap.entrySet()) {
                                if (key.getKey().toLowerCase().equals(a.toLowerCase())) {
                                    //populate list of faculty

                                    //get all faculty id in university
                                    ArrayList<String> al = key.getValue().getFacultyID();

                                    Iterator itr = al.listIterator();
                                    //search faculty list
                                    HashMap<String, String> map = null;

                                    ArrayList<String> fidList = new ArrayList();

                                    if (new DataSource().readObject(FileInterface.FACULTY) != null) {
                                        map = (HashMap<String, String>) new DataSource().readObject(FileInterface.FACULTY);
                                        for (Map.Entry<String, String> key2 : map.entrySet()) {
                                            //get all faculty id's
                                            fidList.add(key2.getKey());
                                        }

                                        fidList.retainAll(al);

                                        for (int i = 0; i < fidList.size(); i++) {

                                            comboBoxModel.addElement(map.get(fidList.get(i)));
                                        }

                                    }
                                }

                            }
                        }
                    } else {
                        comboBoxModel.removeAllElements();
                    }
                }
            }

        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        universityList = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        facultyList = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        progID = new javax.swing.JTextField();
        progName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Add a programme");

        universityList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(246, 246, 246), 2));

        jLabel2.setText("Select University");

        jLabel3.setText("Select Faculty");

        facultyList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(246, 246, 246), 2));

        jLabel4.setText("Programme ID");

        progID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250), 2));

        progName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250), 2));

        jLabel5.setText("Programme Name");

        jButton1.setText("Add Programme");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(progName, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(universityList, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(facultyList, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(progID)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(universityList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(facultyList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(progID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(progName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String message = "";
        if (universityList.getSelectedItem().equals("Select University")) {
            System.out.println("Select a university and a faculty to add programm");
        } else if (progID.getText().equals("") || progName.getText().equals("")) {
            message = "Please add a program";
        } else {
            String selected = facultyList.getSelectedItem().toString();
            System.out.println(selected);
            //retrive Faculty ID
            HashMap<String, String> map = null;
            if (new DataSource().readObject(FileInterface.FACULTY) != null) {
                map = (HashMap<String, String>) new DataSource().readObject(FileInterface.FACULTY);
                for (Map.Entry<String, String> m : map.entrySet()) {
                    if (m.getValue().toLowerCase().equals(selected.toLowerCase())) {
                        //add program
                        Programme p = new Programme();
                        if (p.addPrograme(progID.getText(), progName.getText(), m.getKey())) {
                            message = "Programm added";
                            getRootPane().getParent().setVisible(false);
                        }

                    }
                }

            }
            JOptionPane.showMessageDialog(rootPane, message);
    }//GEN-LAST:event_jButton1ActionPerformed
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> facultyList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField progID;
    private javax.swing.JTextField progName;
    private javax.swing.JComboBox<String> universityList;
    // End of variables declaration//GEN-END:variables
}
