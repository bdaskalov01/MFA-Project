package com.RustyRents.RustyRents.Account;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.Options.Options;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class ChangePassword extends JFrame implements ActionListener {

    JButton changePassword, cancelOperation;
    Boolean isCurrentPasswordCorrect, isConfirmationSuccessful;
    JPanel background;
    JLabel currentPassword, newPassword, confirmPassword, passwordsNotMatching;
    JPasswordField currentPasswordTextField, newPasswordTextField, confirmNewPasswordTextField;
    String getNewPassword, getConfirmPassword;
    JLayeredPane layeredPane;
    ImageIcon appIcon;

    private final FrameNavigator frameNavigator;
    public ChangePassword(FrameNavigator frameNavigator){
        this.frameNavigator = frameNavigator;
        appIcon = new ImageIcon("RustyRentsIcon.png");


        background = new JPanel();
        background.setBounds(0,0,500,700);
        background.setBackground(new Color(248,240,255));
        background.setVisible(true);


        currentPassword = new JLabel("Current password: ");
        currentPassword.setBounds(110, 21, 120, 15);

        newPassword = new JLabel("New password: ");
        newPassword.setBounds(130, 80, 120, 15);

        confirmPassword = new JLabel("Confirm new password: ");
        confirmPassword.setBounds(85, 140, 150, 15);

        passwordsNotMatching = new JLabel("Passwords don't match.");
        passwordsNotMatching.setBounds(170,170,200,30);
        passwordsNotMatching.setForeground(Color.RED);
        passwordsNotMatching.setVisible(false);


        currentPasswordTextField = new JPasswordField();
        currentPasswordTextField.setBounds(220, 16, 195, 30);

        newPasswordTextField = new JPasswordField();
        newPasswordTextField.setBounds(220, 75, 195, 30);

        confirmNewPasswordTextField = new JPasswordField();
        confirmNewPasswordTextField.setBounds(220, 135, 195, 30);


        changePassword = new JButton("Change password");
        changePassword.setBounds(70, 200, 150, 50);
        changePassword.setFocusable(false);
        changePassword.addActionListener(this);
        changePassword.setBackground(new Color(139,0,139));
        changePassword.setForeground(Color.WHITE);

        cancelOperation = new JButton("Cancel");
        cancelOperation.setBounds(260, 200, 150 , 50);
        cancelOperation.setFocusable(false);
        cancelOperation.addActionListener(this);
        cancelOperation.setBackground(new Color(139,0,139));
        cancelOperation.setForeground(Color.WHITE);


        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 300);
        layeredPane.add(background, Integer.valueOf(0));
        layeredPane.add(currentPassword, Integer.valueOf(1));
        layeredPane.add(newPassword, Integer.valueOf(2));
        layeredPane.add(confirmPassword, Integer.valueOf(3));
        layeredPane.add(currentPasswordTextField, Integer.valueOf(4));
        layeredPane.add(newPasswordTextField, Integer.valueOf(5));
        layeredPane.add(confirmNewPasswordTextField, Integer.valueOf(6));
        layeredPane.add(changePassword, Integer.valueOf(7));
        layeredPane.add(cancelOperation, Integer.valueOf(8));
        layeredPane.add(passwordsNotMatching, Integer.valueOf(9));


        this.setTitle("Change password");
        this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.add(layeredPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== changePassword){

            getNewPassword = new String(newPasswordTextField.getPassword());
            getConfirmPassword = new String(confirmNewPasswordTextField.getPassword());

            isConfirmationSuccessful = (getNewPassword.equals(getConfirmPassword));

            if (isConfirmationSuccessful && Database.checkPasswordMatch(new String(currentPasswordTextField.getPassword()))) {
                Database.changePassword(getNewPassword);
                passwordsNotMatching.setVisible(false);
                frameNavigator.showFrame(MyProfile.class);
            }
            else if (!Database.checkPasswordMatch(new String(currentPasswordTextField.getPassword()))){
                passwordsNotMatching.setText("Current password doesn't match");
                passwordsNotMatching.setVisible(true);
            }

            else if (!isConfirmationSuccessful) {
                passwordsNotMatching.setText("Passwords don't match");
                passwordsNotMatching.setVisible(true);
            }
            else {
                passwordsNotMatching.setVisible(false);
            }
        }
        else if(e.getSource()==cancelOperation){
            frameNavigator.showFrame(MyProfile.class);
            passwordsNotMatching.setVisible(false);
        }
    }
}