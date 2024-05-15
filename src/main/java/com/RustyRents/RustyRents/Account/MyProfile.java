package com.RustyRents.RustyRents.Account;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Database.Database;
import com.mysql.cj.log.Log;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.logging.log4j.Logger;

@Component
public class MyProfile extends JFrame implements ActionListener {



    JButton backButton, createTokenButton, removeTokenButton;

    JLabel usernameData, emailData, username, email, twoFAOptions;

    JButton changeEmailButton = new JButton();
    JButton changePasswordButton = new JButton();


    JCheckBox softwareCheck, hardwareCheck, secretQCheck, emailCheck;

    private final Logger logger = LogManager.getLogger(MyProfile.class.getName());
    private final FrameNavigator frameNavigator;
    public MyProfile(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;
        ImageIcon appIcon = new ImageIcon("RustyRentsIcon.png");
        ImageIcon backIcon = new ImageIcon("BackIcon.png");

        JPanel background = new JPanel();
        background.setBounds(0,0,500,700);
        background.setBackground(new Color(248,240,255));
        background.setVisible(true);

        backButton = new JButton(backIcon);
        backButton.setBounds(5,5,50,50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);

        changePasswordButton.setBounds(40,600,175,50);
        changePasswordButton.setText("Change Password");
        changePasswordButton.addActionListener(this);
        changePasswordButton.setBackground(new Color(139,0,139));
        changePasswordButton.setForeground(Color.white);
        changePasswordButton.setFocusable(false);

        changeEmailButton.setBounds(260,600,175,50);
        changeEmailButton.setText("Change Email");
        changeEmailButton.addActionListener(this);
        changeEmailButton.setBackground(new Color(139,0,139));
        changeEmailButton.setForeground(Color.white);
        changeEmailButton.setFocusable(false);

        createTokenButton = new JButton();
        createTokenButton.setBounds(40,530,175,50);
        createTokenButton.setText("Create hardware token");
        createTokenButton.addActionListener(this);
        createTokenButton.setBackground(new Color(139,0,139));
        createTokenButton.setForeground(Color.white);
        createTokenButton.setFocusable(false);

        removeTokenButton = new JButton();
        removeTokenButton.setBounds(260,530,175,50);
        removeTokenButton.setText("Remove hardware token");
        removeTokenButton.addActionListener(this);
        removeTokenButton.setBackground(new Color(139,0,139));
        removeTokenButton.setForeground(Color.white);
        removeTokenButton.setFocusable(false);

        final int LABEL_X = 200;
       // 135;
        //70;
        //40;

        username = new JLabel("Username: ");
        username.setBounds(LABEL_X, 90, 130, 15);

        email = new JLabel("Email: ");
        email.setBounds(LABEL_X, 140, 130, 15);

        twoFAOptions = new JLabel("2FA Options: ");
        twoFAOptions.setBounds(LABEL_X, 190, 130, 15);


        usernameData = new JLabel(Database.getUsername());
        usernameData.setBounds(LABEL_X + 70, 90, 200, 15);

        emailData = new JLabel(Database.getEmail());
        emailData.setBounds(LABEL_X + 40, 140, 200, 15);

        softwareCheck = new JCheckBox("Software Token");
        softwareCheck.addActionListener(this);
        softwareCheck.setBounds(LABEL_X - 130, 240, 130, 15);

        emailCheck = new JCheckBox("Email");
        emailCheck.addActionListener(this);
        emailCheck.setBounds(LABEL_X, 290, 130, 15);

        hardwareCheck = new JCheckBox("Hardware Token");
        hardwareCheck.addActionListener(this);
        hardwareCheck.setBounds(LABEL_X, 240, 130, 15);

        secretQCheck = new JCheckBox("Secret Question");
        secretQCheck.addActionListener(this);
        secretQCheck.setBounds(LABEL_X + 130, 240, 130, 15);


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 700);
        layeredPane.add(background, Integer.valueOf(0));
        layeredPane.add(backButton, Integer.valueOf(1));
        layeredPane.add(username, Integer.valueOf(2));
        layeredPane.add(email, Integer.valueOf(3));
        layeredPane.add(usernameData, Integer.valueOf(4));
        layeredPane.add(emailData, Integer.valueOf(5));
        layeredPane.add(changeEmailButton, Integer.valueOf(6));
        layeredPane.add(changePasswordButton, Integer.valueOf(7));
        layeredPane.add(twoFAOptions, Integer.valueOf(8));
        layeredPane.add(softwareCheck, Integer.valueOf(9));
        layeredPane.add(hardwareCheck, Integer.valueOf(10));
        layeredPane.add(secretQCheck, Integer.valueOf(11));
        layeredPane.add(emailCheck, Integer.valueOf(12));
        layeredPane.add(createTokenButton, Integer.valueOf(13));
        layeredPane.add(removeTokenButton, Integer.valueOf(14));

        this.setTitle("My profile");
        this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,700);
        this.setLocationRelativeTo(null);
        this.add(layeredPane);

    }

    public void refreshUIData() {
        usernameData.setText(Database.getUsername());
        emailData.setText(Database.getEmail());

        if (Database.isSoftwareEnabled()) {
            softwareCheck.setSelected(true);
        }

        if (Database.isHardwareEnabled()) {
            hardwareCheck.setSelected(true);
        }

        if (Database.isSecretQEnabled()) {
            secretQCheck.setSelected(true);
        }

        if (Database.isEmailEnabled()) {
            emailCheck.setSelected(true);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==changeEmailButton) {
            frameNavigator.showFrame(ChangeEmail.class);
        }

        else if (e.getSource()==changePasswordButton) {
            frameNavigator.showFrame(ChangePassword.class);
        }

        else if (e.getSource() == createTokenButton) {
            Database.saveHardwareToken(RandomStringUtils.randomAlphanumeric(10));
        }

        else if (e.getSource() == removeTokenButton) {
            Database.deleteHardwareToken(Database.getCurrentUserId());
        }

        else if (e.getSource() == backButton) {
            frameNavigator.showFrame(MainMenu.class);

            if (!softwareCheck.isSelected() && !hardwareCheck.isSelected() && !secretQCheck.isSelected() && !emailCheck.isSelected()) {
                JOptionPane.showMessageDialog(null, "Must have atleast one factor enabled!");
            }

            else {
                if (softwareCheck.isSelected()) {
                    Database.enableSoftware();
                }

                else {
                    Database.disableSoftware();
                }

                if (hardwareCheck.isSelected()) {
                    Database.enableHardware();
                }

                else {
                    Database.disableHardware();
                }

                if (secretQCheck.isSelected()) {
                    Database.enableSecretQuestion();
                }

                else {
                    Database.disableSecretQuestion();
                }

                if (emailCheck.isSelected()) {
                    Database.enableEmail();
                }

                else {
                    Database.disableEmail();
                }
            }

        }
    }
}
