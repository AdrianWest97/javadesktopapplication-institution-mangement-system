/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalForms;

import readWrite.DataSource;
import readWrite.SignedIn;
import Interface.FileInterface;
import entities.Faculty;
import entities.Institution;
import entities.Programme;
import entities.Student;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sort.Sort;

public class MainPanel extends javax.swing.JInternalFrame {

    /**
     * Creates new form MainPanel
     */
    private final Object[] columnNames = {"Student ID", "First Name", "Last Name", "Gender"};
    private final Object[][] rowData = null;
    private ArrayList<Student> students = new ArrayList();
    private Sort s = new Sort();

    public MainPanel() {

        initComponents();
        // remove northPane
        Container pane = ((BasicInternalFrameUI) this.getUI()).getNorthPane();
        pane.remove(0);
        pane.getComponent(0).setVisible(false);
        loadData();
        if (SignedIn.getAdmin() == null) {
            AdminButtonsPanel.setVisible(false);
        }

        universityListSelected();
        facultyListSelected();
        programmeSelected();
        exitBtn.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }

    private DefaultTableModel tblModel = new DefaultTableModel(rowData, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private void loadData() {
        Map<String, Institution> institution = null;

        try {
            DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
            if (new DataSource().readObject(FileInterface.INSTITUTIONS_FILE) != null) {

                //populate fields
                institution = (Map<String, Institution>) new DataSource().readObject(FileInterface.INSTITUTIONS_FILE);

                universityList.setModel(boxModel);
                TreeMap<String, Institution> sorted = new TreeMap();
                sorted.putAll(institution);

                //
                for (Map.Entry<String, Institution> key : sorted.entrySet()) {
                    if (SignedIn.getAdmin() != null) {
                        if (key.getValue().getAdmin().equals(SignedIn.getAdmin().getEmail())) {
                            boxModel.addElement("(" + key.getKey().toUpperCase() + ") - " + key.getValue().getInstitutionName());
                        }
                    } else {
                        boxModel.addElement("(" + key.getKey().toUpperCase() + ") - " + key.getValue().getInstitutionName());

                    }
                }

            }
        } catch (Exception e) {
            //log error
        }
    }

    public final void universityListSelected() {
        universityList.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String a = returnKey(universityList.getSelectedItem().toString());

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
                            HashMap<String, Faculty> map = null;

                            ArrayList<String> fidList = new ArrayList();

                            if (new DataSource().readObject(FileInterface.FACULTY) != null) {
                                map = (HashMap<String, Faculty>) new DataSource().readObject(FileInterface.FACULTY);
                                for (Map.Entry<String, Faculty> key2 : map.entrySet()) {
                                    //get all faculty id's
                                    fidList.add(key2.getKey());
                                }
                                Collections.sort(fidList);
                                fidList.retainAll(al);
                                DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
                                facultyList.setModel(comboBoxModel);
                                for (int i = 0; i < fidList.size(); i++) {

                                    comboBoxModel.addElement(map.get(fidList.get(i)));
                                }

                            }
                        }
                    }
                }
            }
        });

    }

    public final void facultyListSelected() {

        facultyList.addItemListener((ItemEvent e) -> {
            String Fkey = "";
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String a = facultyList.getSelectedItem().toString();
                //search list
                HashMap<String, String> hashMap = null;
                Fkey = "";
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
                listProgrammes.setModel(boxModel);
                
                if (new DataSource().readObject(FileInterface.PROGRAMMES) != null) {
                    al = (ArrayList<Programme>) new DataSource().readObject(FileInterface.PROGRAMMES);
                    Collections.sort(al);
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

    public final void programmeSelected() {
        listProgrammes.addListSelectionListener(new ListSelectionListener() {
            String pid;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                studentTable.setModel(tblModel);
                if (e.getValueIsAdjusting()) {
                    students.clear();
                    //get program id
                    pid = returnKey(listProgrammes.getSelectedValue());
                    //searh student list for id
                    HashMap<String, Student> hashMap = null;

                    if (new DataSource().readObject(FileInterface.STUDENTS) != null) {
                        hashMap = (HashMap<String, Student>) new DataSource().readObject(FileInterface.STUDENTS);

                        for (Map.Entry<String, Student> m : hashMap.entrySet()) {
                            if (m.getValue().getProgID().toLowerCase().equals(pid.toLowerCase())
                                    && m.getValue().getIntID().toLowerCase().equals(returnKey(universityList.getSelectedItem().toString().toLowerCase()))) {
                                students.add(m.getValue());
                            }
                            for (int i = 0; i < tblModel.getRowCount(); i++) {
                                tblModel.removeRow(i);
                            }
                        }

                        for (int i = 0; i < students.size(); i++) {
                            Object[] data;
                            data = new Object[]{students.get(i).getID(), students.get(i).getFirstName(),
                                students.get(i).getLastName(), students.get(i).getGender()};
                            tblModel.insertRow(i, data);
                        }

                    }
                }
            }
        });
    }

    public String returnKey(String text) {
        text = text.substring(text.indexOf("(") + 1);
        text = text.substring(0, text.indexOf(")"));
        return text;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listProgrammes = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        facultyList = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        universityList = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        studPhoto = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        AdminButtonsPanel = new javax.swing.JPanel();
        ImageIcon icon = new ImageIcon((getClass().getResource("/images/addStud.png")));
        AddStudent = new javax.swing.JButton("",icon);
        ImageIcon icon2 = new ImageIcon((getClass().getResource("/images/open-book.png")));
        AddProgramme = new javax.swing.JButton("",icon2);
        ImageIcon icon3 = new ImageIcon((getClass().getResource("/images/university.png")));
        AddUniversity = new javax.swing.JButton("",icon3);
        ImageIcon icon4 = new ImageIcon((getClass().getResource("/images/addFaculty.png")));
        AddFaculty = new javax.swing.JButton("",icon4);
        ImageIcon icon5 = new ImageIcon((getClass().getResource("/images/logout.png")));
        exitBtn = new javax.swing.JButton("",icon5);
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(239, 242, 244));
        jPanel1.setForeground(new java.awt.Color(56, 76, 95));

        jPanel3.setBackground(new java.awt.Color(248, 248, 248));
        jPanel3.setForeground(new java.awt.Color(220, 220, 220));
        jPanel3.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(105, 105, 105));
        jLabel3.setText("programmes");

        jScrollPane1.setViewportView(listProgrammes);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(105, 105, 105));
        jLabel4.setText("Student");

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "First Name", "Last Name", "Gender"
            }
        ));
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                studentTableMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                studentTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(studentTable);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(105, 105, 105));
        jLabel2.setText("Faculty");

        facultyList.setBackground(new java.awt.Color(245, 245, 245));
        facultyList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        facultyList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 245, 245)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(105, 105, 105));
        jLabel1.setText("Institutiton");

        universityList.setBackground(new java.awt.Color(245, 245, 245));
        universityList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        universityList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 245, 245)));
        universityList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                universityListActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        studPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/placeholder.png"))); // NOI18N
        studPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(105, 105, 105));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        jLabel5.setText("Click here to reload window");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        jLabel7.setForeground(new java.awt.Color(105, 105, 105));
        jLabel7.setText("NB. If Universities or faculties not displayin, hit the refresh icon above.");

        AdminButtonsPanel.setBackground(new java.awt.Color(245, 245, 245));

        AddStudent.setBackground(new java.awt.Color(227, 233, 237));
        AddStudent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddStudent.setForeground(new java.awt.Color(56, 76, 95));
        AddStudent.setText("Add Student");
        AddStudent.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 2, true));
        AddStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStudentActionPerformed(evt);
            }
        });

        AddProgramme.setBackground(new java.awt.Color(227, 233, 237));
        AddProgramme.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddProgramme.setForeground(new java.awt.Color(56, 76, 95));
        AddProgramme.setText("Add programme");
        AddProgramme.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 2, true));
        AddProgramme.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddProgramme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProgrammeActionPerformed(evt);
            }
        });

        AddUniversity.setBackground(new java.awt.Color(227, 233, 237));
        AddUniversity.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddUniversity.setForeground(new java.awt.Color(56, 76, 95));
        AddUniversity.setText("Add University");
        AddUniversity.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 2, true));
        AddUniversity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddUniversity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUniversityActionPerformed(evt);
            }
        });

        AddFaculty.setBackground(new java.awt.Color(227, 233, 237));
        AddFaculty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddFaculty.setForeground(new java.awt.Color(56, 76, 95));
        AddFaculty.setText("Add Faculty");
        AddFaculty.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 2, true));
        AddFaculty.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddFaculty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFacultyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdminButtonsPanelLayout = new javax.swing.GroupLayout(AdminButtonsPanel);
        AdminButtonsPanel.setLayout(AdminButtonsPanelLayout);
        AdminButtonsPanelLayout.setHorizontalGroup(
            AdminButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminButtonsPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(AddStudent, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(AddProgramme, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(AddUniversity, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddFaculty, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        AdminButtonsPanelLayout.setVerticalGroup(
            AdminButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdminButtonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AdminButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddProgramme, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddUniversity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddFaculty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        exitBtn.setBackground(new java.awt.Color(239, 9, 41));
        exitBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        exitBtn.setForeground(new java.awt.Color(56, 76, 95));
        exitBtn.setText("Exit");
        exitBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 245, 245), 2, true));
        exitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        jLabel8.setText("(A-Z)");

        jLabel9.setForeground(new java.awt.Color(105, 105, 105));
        jLabel9.setText("(A-Z)");

        jLabel10.setText("(A-Z)");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(105, 105, 105));
        jLabel11.setText("School Mangement System");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8)
                                        .addGap(123, 123, 123)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(facultyList, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(universityList, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel5)
                                        .addGap(29, 60, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(studPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AdminButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(universityList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(facultyList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(40, 40, 40)
                        .addComponent(studPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(AdminButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("School System", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddUniversityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUniversityActionPerformed
        // TODO add your handling code here:
        //add new university
        RegisterUniversity university = new RegisterUniversity();
        getDesktopPane().add(university);
        university.show();
    }//GEN-LAST:event_AddUniversityActionPerformed

    private void AddFacultyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFacultyActionPerformed
        // TODO add your handling code here:
        //add a new faculty to the university
        //check if user registered a university
        boolean found = false;
        HashMap<String, Institution> universityMap = null;
        if (new DataSource().readObject(FileInterface.INSTITUTIONS_FILE) != null) {
            universityMap = (HashMap<String, Institution>) new DataSource().readObject(FileInterface.INSTITUTIONS_FILE);
            for (Map.Entry<String, Institution> key : universityMap.entrySet()) {
                if (key.getValue().getAdmin().equals(SignedIn.getAdmin().getEmail())) {
                    //user registered a university
                    //do something
                    //show add faculty form
                    found = true;
                } else {
                    //show add university form
                    JOptionPane.showMessageDialog(this, "Add a university before you can add faculties");
                }
            }
        }
        if (found) {
            RegisterFaculty registerFaculty = new RegisterFaculty();
            getDesktopPane().add(registerFaculty);
            registerFaculty.show();
        }
    }//GEN-LAST:event_AddFacultyActionPerformed


    private void AddProgrammeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProgrammeActionPerformed
        // TODO add your handling code here:
        //open add programme frame
        RegisterProgram prog = new RegisterProgram();
        getDesktopPane().add(prog);
        prog.show();
    }//GEN-LAST:event_AddProgrammeActionPerformed

    private void AddStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStudentActionPerformed
        // TODO add your handling code here:
        RegisterStudent studForm = new RegisterStudent();
        getDesktopPane().add(studForm);
        studForm.show();
    }//GEN-LAST:event_AddStudentActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_exitBtnActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        loadData();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void universityListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_universityListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_universityListActionPerformed

    private void studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseClicked

    }//GEN-LAST:event_studentTableMouseClicked

    private void studentTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_studentTableMouseEntered

    private void studentTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMousePressed
        // TODO add your handling code here:
        // TODO add your handling code here:
        int index = studentTable.getSelectedRow();
        TableModel model = studentTable.getModel();
        String id = (String) model.getValueAt(index, 0);
        //search for photo
        if (new DataSource().readObject(FileInterface.STUDENTS) != null) {
            HashMap<String, Student> stud = (HashMap<String, Student>) new DataSource().readObject(FileInterface.STUDENTS);
            if (stud.get(id) != null) {
                //System.out.println(stud.get(id).getPhoto());
                File f = new File(stud.get(id).getPhoto());
                ImageIcon icon = new ImageIcon(f.getAbsolutePath());
                Image photo = icon.getImage();
                photo = photo.getScaledInstance(studPhoto.getWidth(), studPhoto.getHeight(), Image.SCALE_SMOOTH);
                studPhoto.setIcon(new ImageIcon(photo));
            }
        }
    }//GEN-LAST:event_studentTableMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFaculty;
    private javax.swing.JButton AddProgramme;
    private javax.swing.JButton AddStudent;
    private javax.swing.JButton AddUniversity;
    private javax.swing.JPanel AdminButtonsPanel;
    private javax.swing.JButton exitBtn;
    private javax.swing.JComboBox<String> facultyList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listProgrammes;
    private javax.swing.JLabel studPhoto;
    private javax.swing.JTable studentTable;
    private javax.swing.JComboBox<String> universityList;
    // End of variables declaration//GEN-END:variables
}
