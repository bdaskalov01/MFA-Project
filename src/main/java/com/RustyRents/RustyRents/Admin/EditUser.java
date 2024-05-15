package com.RustyRents.RustyRents.Admin;

import com.RustyRents.RustyRents.Admin.AdminPanel;
import com.RustyRents.RustyRents.Admin.ListingsAdmin;
import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;

@Component
public class EditUser extends JFrame implements ActionListener {
    private final FrameNavigator frameNavigator;
    private static final Logger logger = LogManager.getLogger(EditUser.class.getName());
    private int amountOfPics = 0;

    JLabel usernameLabel, passwordLabel, emailLabel, questionLabel, answerLabel, hardwareLabel;


    JTextField usernameInput, passwordInput, emailInput, questionInput, answerInput, hardwareInput;

    JCheckBox softwareCheck, hardwareCheck, secretQCheck, emailCheck;

    JButton backButton, nextButton, removeHardwareTokenButton;


    JLayeredPane firstPanel;

    JPanel  subPanel1_1, subPanel2_1, subPanel3_1, subPanel4_1, subPanel5_1,
            subPanel6_1, subPanel7_1, subPanel8_1, subPanel9_1, subPanel10_1, subPanel11_1, subPanel12_1,
            subPanel1_2, subPanel2_2,subPanel3_2, subPanel4_2, subPanel5_2, subPanel6_2, subPanel7_2, subPanel8_2,
            subPanel9_2, subPanel10_2, subPanel11_2, subPanel12_2, subPanel1_3, subPanel2_3, subPanel3_3, subPanel4_3,
            subPanel5_3, subPanel6_3, subPanel7_3, subPanel8_3, subPanel9_3, subPanel10_3, subPanel11_3, subPanel12_3,
            ImagesSubPanel, buttonsSubPanel;


    public EditUser(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        innitSubPanels();
        innitPanels();

        this.setTitle("Edit User Details");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(248,240,255));
        this.setForeground(Color.WHITE);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.add(firstPanel);
        this.pack();
        this.setLocationRelativeTo(null);

    }

    private void innitSubPanels(){
        subPanel1_1 = new JPanel();
        subPanel1_1.setBackground(new Color(248,240,255));
        subPanel2_1 = new JPanel();
        subPanel2_1.setBackground(new Color(248,240,255));
        subPanel3_1 = new JPanel();
        subPanel3_1.setBackground(new Color(248,240,255));
        subPanel4_1 = new JPanel();
        subPanel4_1.setBackground(new Color(248,240,255));
        subPanel5_1 = new JPanel();
        subPanel5_1.setBackground(new Color(248,240,255));
        subPanel6_1 = new JPanel();
        subPanel6_1.setBackground(new Color(248,240,255));
        subPanel7_1 = new JPanel();
        subPanel7_1.setBackground(new Color(248,240,255));
        subPanel8_1 = new JPanel();
        subPanel8_1.setBackground(new Color(248,240,255));
        subPanel9_1 = new JPanel();
        subPanel9_1.setBackground(new Color(248,240,255));
        subPanel10_1 = new JPanel();
        subPanel10_1.setBackground(new Color(248,240,255));
        subPanel11_1 = new JPanel();
        subPanel11_1.setBackground(new Color(248,240,255));
        subPanel12_1 = new JPanel();
        subPanel12_1.setBackground(new Color(248,240,255));
        subPanel1_2 = new JPanel();
        subPanel1_2.setBackground(new Color(248,240,255));
        subPanel2_2 = new JPanel();
        subPanel2_2.setBackground(new Color(248,240,255));
        subPanel3_2 = new JPanel();
        subPanel3_2.setBackground(new Color(248,240,255));
        subPanel4_2 = new JPanel();
        subPanel4_2.setBackground(new Color(248,240,255));
        subPanel5_2 = new JPanel();
        subPanel5_2.setBackground(new Color(248,240,255));
        subPanel6_2 = new JPanel();
        subPanel6_2.setBackground(new Color(248,240,255));
        subPanel7_2 = new JPanel();
        subPanel7_2.setBackground(new Color(248,240,255));
        subPanel8_2 = new JPanel();
        subPanel8_2.setBackground(new Color(248,240,255));
        subPanel9_2 = new JPanel();
        subPanel9_2.setBackground(new Color(248,240,255));
        subPanel10_2 = new JPanel();
        subPanel10_2.setBackground(new Color(248,240,255));
        subPanel11_2 = new JPanel();
        subPanel11_2.setBackground(new Color(248,240,255));
        subPanel12_2 = new JPanel();
        subPanel12_2.setBackground(new Color(248,240,255));
        subPanel1_3 = new JPanel();
        subPanel1_3.setBackground(new Color(248,240,255));
        subPanel2_3 = new JPanel();
        subPanel2_3.setBackground(new Color(248,240,255));
        subPanel3_3 = new JPanel();
        subPanel3_3.setBackground(new Color(248,240,255));
        subPanel4_3 = new JPanel();
        subPanel4_3.setBackground(new Color(248,240,255));
        subPanel5_3 = new JPanel();
        subPanel5_3.setBackground(new Color(248,240,255));
        subPanel6_3 = new JPanel();
        subPanel6_3.setBackground(new Color(248,240,255));
        subPanel7_3 = new JPanel();
        subPanel7_3.setBackground(new Color(248,240,255));
        subPanel8_3 = new JPanel();
        subPanel8_3.setBackground(new Color(248,240,255));
        subPanel9_3 = new JPanel();
        subPanel9_3.setBackground(new Color(248,240,255));
        subPanel10_3 = new JPanel();
        subPanel10_3.setBackground(new Color(248,240,255));
        subPanel11_3 = new JPanel();
        subPanel11_3.setBackground(new Color(248,240,255));
        subPanel12_3 = new JPanel();
        subPanel12_3.setBackground(new Color(248,240,255));
    }

    private void innitPanels() {
        firstPanel = new JLayeredPane();
        firstPanel.setPreferredSize(new Dimension(350,550));
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));

        usernameLabel = new JLabel("Username:");
        subPanel1_1.add(usernameLabel);

        usernameInput = new JTextField(15);
        subPanel1_1.add(usernameInput);

        passwordLabel = new JLabel("Password:");
        subPanel2_1.add(passwordLabel);

        passwordInput = new JTextField(15);
        subPanel2_1.add(passwordInput);

        emailLabel = new JLabel("Email:");
        subPanel3_1.add(emailLabel);

        emailInput = new JTextField(15);
        subPanel3_1.add(emailInput);

        questionLabel = new JLabel("Secret Question:");
        subPanel4_1.add(questionLabel);

        questionInput = new JTextField(15);
        subPanel4_1.add(questionInput);

        answerLabel = new JLabel("Answer:");
        subPanel5_1.add(answerLabel);

        answerInput = new JTextField(15);
        subPanel5_1.add(answerInput);

        softwareCheck = new JCheckBox("Software Token");
        softwareCheck.addActionListener(this);
        subPanel6_1.add(softwareCheck);

        emailCheck = new JCheckBox("Email");
        emailCheck.addActionListener(this);
        subPanel7_1.add(emailCheck);

        hardwareCheck = new JCheckBox("Hardware Token");
        hardwareCheck.addActionListener(this);
        subPanel8_1.add(hardwareCheck);

        secretQCheck = new JCheckBox("Secret Question");
        secretQCheck.addActionListener(this);
        subPanel9_1.add(secretQCheck);

        removeHardwareTokenButton = new JButton("Remove Hardware Token");
        removeHardwareTokenButton.addActionListener(this);
        removeHardwareTokenButton.setBackground(new Color(139,0,139));
        removeHardwareTokenButton.setForeground(Color.white);
        subPanel11_1.add(removeHardwareTokenButton, BorderLayout.WEST);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(139,0,139));
        backButton.setForeground(Color.white);
        subPanel12_1.add(backButton, BorderLayout.WEST);

        nextButton = new JButton("Confirm");
        nextButton.addActionListener(this);
        nextButton.setBackground(new Color(139,0,139));
        nextButton.setForeground(Color.white);
        subPanel12_1.add(nextButton, BorderLayout.EAST);

        firstPanel.add(subPanel1_1);
        firstPanel.add(subPanel2_1);
        firstPanel.add(subPanel3_1);
        firstPanel.add(subPanel4_1);
        firstPanel.add(subPanel5_1);
        firstPanel.add(subPanel6_1);
        firstPanel.add(subPanel7_1);
        firstPanel.add(subPanel8_1);
        firstPanel.add(subPanel9_1);
        firstPanel.add(subPanel10_1);
        firstPanel.add(subPanel11_1);
        firstPanel.add(subPanel12_1);

    }

    private void clearTextFields() {
        usernameInput.setText("");
        passwordInput.setText("");
        emailInput.setText("");
        questionInput.setText("");
        answerInput.setText("");
        hardwareInput.setText("");
    }

    public void refreshUI() {
        ResultSet rs = Database.getUserDetailsAdmin();
        try {
            rs.next();
            usernameInput.setText(rs.getString(1));
            passwordInput.setText(rs.getString(3));
            emailInput.setText(rs.getString(2));
            questionInput.setText(rs.getString(4));
            answerInput.setText(rs.getString(5));
            hardwareInput.setText(rs.getString(6));
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        if (Database.isSoftwareEnabledAdmin()) {
            softwareCheck.setSelected(true);
        }

        if (Database.isHardwareEnabledAdmin()) {
            hardwareCheck.setSelected(true);
        }

        if (Database.isSecretQEnabledAdmin()) {
            secretQCheck.setSelected(true);
        }

        if (Database.isEmailEnabledAdmin()) {
            emailCheck.setSelected(true);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {

                frameNavigator.showFrame(UsersAdmin.class);
        }

        else if (e.getSource() == nextButton) {

            if (!softwareCheck.isSelected() && !hardwareCheck.isSelected() && !secretQCheck.isSelected() && !emailCheck.isSelected()) {
                JOptionPane.showMessageDialog(null, "Must have atleast one factor enabled!");
            }

            else {
                if (softwareCheck.isSelected()) {
                    Database.enableSoftwareAdmin();
                }

                else {
                    Database.disableSoftwareAdmin();
                }

                if (hardwareCheck.isSelected()) {
                    Database.enableHardwareAdmin();
                }

                else {
                    Database.disableHardwareAdmin();
                }

                if (secretQCheck.isSelected()) {
                    Database.enableSecretQuestionAdmin();
                }

                else {
                    Database.disableSecretQuestionAdmin();
                }

                if (emailCheck.isSelected()) {
                    Database.enableEmailAdmin();
                }

                else {
                    Database.disableEmailAdmin();
                }
                Database.updateUser(usernameInput.getText().toString(), passwordInput.getText().toString(), emailInput.getText().toString(), questionInput.getText().toString(),
                        answerInput.getText().toString(), hardwareInput.getText().toString());
                frameNavigator.showFrame(UsersAdmin.class);
            }

        }

        else if (e.getSource() == removeHardwareTokenButton) {
            Database.deleteHardwareToken(Database.getSelectedUserId());
        }

    }
}
