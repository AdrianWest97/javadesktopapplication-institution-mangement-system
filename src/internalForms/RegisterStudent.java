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
import entities.Student;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class RegisterStudent extends javax.swing.JInternalFrame {

    private String fileLocation = "";
    private BufferedImage image;
    private File imageFile;
    private String extension = "";

    /**
     * Creates new form RegisterStudent
     */
    public RegisterStudent() {
        initComponents();
        // remove northPane
        Container pane = ((BasicInternalFrameUI) this.getUI()).getNorthPane();
        pane.remove(0);
        pane.getComponent(0).setVisible(false);
        loadData();
        universityListSelected();
        facultyListSelected();

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
        if (text.length() > 0) {
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
                comboBoxModel.removeAllElements();
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

    public final void facultyListSelected() {
        facultyList.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String a = facultyList.getSelectedItem().toString();
                //search list
                HashMap<String, String> hashMap = null;
                String Fkey = "";
                if (new DataSource().readObject(FileInterface.FACULTY) != null) {
                    hashMap = (HashMap<String, String>) new DataSource().readObject(FileInterface.FACULTY);
                    for (Map.Entry<String, String> key : hashMap.entrySet()) {
                        if (key.getValue().toLowerCase().equals(a.toLowerCase())) {
                            Fkey = key.getKey();
                        }
                    }
                }
                ArrayList<Programme> al = null;
                DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
                programList.setModel(boxModel);

                if (new DataSource().readObject(FileInterface.PROGRAMMES) != null) {
                    al = (ArrayList<Programme>) new DataSource().readObject(FileInterface.PROGRAMMES);
                    ListIterator itr = al.listIterator();
                    while (itr.hasNext()) {
                        Programme p = (Programme) itr.next();
                        if (p.getFacultyID().toLowerCase().equals(Fkey.toLowerCase())) {
                            boxModel.addElement("(" + p.getProgID() + ") - " + p.getProgName());
                        }

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

        genderGroup = new javax.swing.ButtonGroup();
        regStudentBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        universityList = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        facultyList = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        programList = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        studFname = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        male = new javax.swing.JRadioButton();
        female = new javax.swing.JRadioButton();
        studID = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        photoLabel = new javax.swing.JLabel();
        Upload = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        studLname = new javax.swing.JTextField();
        dob = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(244, 244, 244));
        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setTitle("Setudent Registration");

        regStudentBtn.setText("Register");
        regStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regStudentBtnActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(244, 244, 244), 3), "Student Faculty and Course Information"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("University");

        universityList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                universityListActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Faculty");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Program");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(facultyList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(universityList, 0, 471, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(programList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(universityList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(facultyList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(programList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addStud.png"))); // NOI18N
        jLabel7.setText("Student Registration");

        jPanel3.setBackground(new java.awt.Color(244, 244, 244));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(244, 244, 244)), "Basic Information"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Last Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("First Name");

        studFname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Student ID");

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 247, 247), 3), "Gender"));

        genderGroup.add(male);
        male.setSelected(true);
        male.setText("Male");
        male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleActionPerformed(evt);
            }
        });

        genderGroup.add(female);
        female.setText("Female");
        female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(male)
                .addGap(58, 58, 58)
                .addComponent(female)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(male)
                    .addComponent(female))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        studID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250)));
        studID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studIDActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(244, 244, 244), 3), "Select a Profile photo to Student"));

        photoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/photo-camera.png"))); // NOI18N
        photoLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250)));

        Upload.setText("Upload");
        Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Upload)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(Upload)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("D.O.B");

        studLname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250)));
        studLname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studLnameActionPerformed(evt);
            }
        });

        dob.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 250)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studID, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(studFname, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studLname, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(302, 302, 302)
                            .addComponent(jLabel9)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(studFname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(studLname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(studID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(61, 61, 61)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel9))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(regStudentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(regStudentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void regStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regStudentBtnActionPerformed
        // TODO add your handling code here:
        String fid = "";
        String uid = "";
        String pid = "";
        String gen = "";
        String msg = "";
        int age = 0;
        //caculate student age
        GregorianCalendar calendar = new GregorianCalendar();
        GregorianCalendar now = new GregorianCalendar();
        if (male.isSelected()) {
            gen = "Male";
        } else {
            gen = "Female";
        }
        boolean flag = false;
        if (dob.getDate() != null) {
            calendar.setTime(dob.getDate());
            age = now.get(GregorianCalendar.YEAR) - calendar.get(GregorianCalendar.YEAR);
            if (age < 16) {
                flag = true;
            }
        }
        if (studFname.getText().equals("") || studLname.getText().equals("")
                || dob.getDate() == null || studID.getText().equals("")
                || flag) {
            msg = "Please fill out all fields,Upload photo \n ensure student is older than 15.";
        } else {
            //getSelected university
            uid = returnKey(universityList.getSelectedItem().toString());
            //get Faculty ID
            String facultyName = facultyList.getSelectedItem().toString();
            HashMap<String, String> map = null;
            if (new DataSource().readObject(FileInterface.FACULTY) != null) {
                map = (HashMap<String, String>) new DataSource().readObject(FileInterface.FACULTY);
                for (Map.Entry<String, String> m : map.entrySet()) {
                    if (m.getValue().toLowerCase().equals(facultyName.toLowerCase())) {
                        fid = m.getKey();
                    }
                }
            }
            //get program ID
            pid = returnKey(programList.getSelectedItem().toString());
            //register student

            if (!pid.equals("")) {
                Student stud = new Student();
                stud.setFirstName(studFname.getText());
                stud.setLastName(studLname.getText());
                stud.setFacultyID(fid);
                stud.setProgID(pid);
                stud.setIntID(uid);
                stud.setID(studID.getText());
                stud.setDOB(dob.getDate().toString());
                stud.setGender(gen);
                saveFile(image, getExtension(), getImageFile());
                stud.setPhoto(getFileLocation());

                //check if studentID exits
                if (!stud.studentExits(studID.getText().toLowerCase())) {

                    if (stud.RegisterStudent(stud)) {
                        msg = "Student Registered";
                        //add image to path
                        //save image

                        getRootPane().getParent().setVisible(false);
                    }
                } else {
                    msg = "Student with ID: " + studID.getText() + " already exits";
                }
            } else {
                msg = "Select a program to register student in.";
            }
        }

        JOptionPane.showMessageDialog(rootPane, msg);
    }//GEN-LAST:event_regStudentBtnActionPerformed

    private void maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maleActionPerformed

    private void femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_femaleActionPerformed

    private void universityListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_universityListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_universityListActionPerformed

    private void studLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studLnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studLnameActionPerformed

    private void studIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studIDActionPerformed

    private void UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadActionPerformed
        try {
            // TODO add your handling code here:

            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setFileFilter(fileFilter);
            fileChooser.showOpenDialog(this);
            File file = fileChooser.getSelectedFile();
            //save location
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image photo = icon.getImage();
            photo = photo.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH);
            BufferedImage image = ImageIO.read(file.getAbsoluteFile());
            String imagePath = FilePath(file.getAbsolutePath());
            File toWrite = new File(imagePath);
            int i = imagePath.lastIndexOf('.');
            String ext = "";
            //set the image location
            if (i >= 0) {
                ext = imagePath.substring(i + 1);
            }
            setImageFile(toWrite);
            setImage(image);
            setExtension(ext);
            photoLabel.setIcon(new ImageIcon(photo));
        } catch (IOException ex) {
            Logger.getLogger(RegisterStudent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_UploadActionPerformed

    //save the image file
    private boolean saveFile(BufferedImage image, String ext, File toWrite) {
        if(toWrite!=null){
        if (!toWrite.exists()) {
            try {
                toWrite.getAbsoluteFile().getParentFile().mkdir();
                toWrite.createNewFile();
                ImageIO.write(image, ext, toWrite);
                setFileLocation(toWrite.toString());
                return true;
            } catch (IOException ex) {
                Logger.getLogger(RegisterStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Select a student image");
        }
        }
        return false;
    }

    private String FilePath(String path) {
        File f = new File(FileInterface.STUDENTS);
        String filep = f.getAbsolutePath();
        String strNew = filep.replace(FileInterface.STUDENTS, "\\");
        String ext = "";
        int i = path.lastIndexOf('.');
        if (i >= 0) {
            ext = path.substring(i + 1);
        }

        return strNew + " images\\" + studID.getText() + "." + ext;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Upload;
    private com.toedter.calendar.JDateChooser dob;
    private javax.swing.JComboBox<String> facultyList;
    private javax.swing.JRadioButton female;
    private javax.swing.ButtonGroup genderGroup;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton male;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JComboBox<String> programList;
    private javax.swing.JButton regStudentBtn;
    private javax.swing.JTextField studFname;
    private javax.swing.JTextField studID;
    private javax.swing.JTextField studLname;
    private javax.swing.JComboBox<String> universityList;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the fileLocation
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * @param fileLocation the fileLocation to set
     */
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * @return the imageFile
     */
    public File getImageFile() {
        return imageFile;
    }

    /**
     * @param imageFile the imageFile to set
     */
    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

}
