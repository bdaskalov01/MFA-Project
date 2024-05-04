package com.RustyRents.RustyRents.LogIn;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Register.Register;
import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.Security.SecretQuestion;
import com.RustyRents.RustyRents.Services.EmailSenderService;
import com.RustyRents.RustyRents.Security.SoftwareToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class LogIn extends JFrame implements ActionListener {
    JPanel headerPanel, bodyPanel, footerPanel;
    JButton logInButton, registerButton;
    JLabel headerImage, usernameLabel, passwordLabel, errorlabel;
    ImageIcon appIcon, logInIcon;
    Image rustyRentsLogo, newing;
    static JTextField usernameTextField;
    static JPasswordField passwordTextField;

    private static final Logger logger = LogManager.getLogger(LogIn.class.getName());

    JRadioButton emailCodeRB, softwareTokenRB, secretQuestionRB;

    @Autowired
    private EmailSenderService emailSend;

    private final FrameNavigator frameNavigator;

    public LogIn(FrameNavigator frameNavigator) {

        this.frameNavigator = frameNavigator;

        appIcon = new ImageIcon ("RustyRentsIcon.png");
        logInIcon = new ImageIcon("RustyRentsLogo.png");
        rustyRentsLogo = logInIcon.getImage();
        newing = rustyRentsLogo.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        logInIcon = new ImageIcon(newing);


        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(100,150));
        headerPanel.setBackground(new Color(248,240,255));

        bodyPanel = new JPanel();
        bodyPanel.setPreferredSize(new Dimension(100,100));
        bodyPanel.setBackground(new Color(248,240,255));

        footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(100,70));
        footerPanel.setBackground(new Color(248,240,255));

        headerImage = new JLabel(logInIcon);
        headerImage.setPreferredSize(new Dimension(100,100));
        headerPanel.add(headerImage,BorderLayout.CENTER);

        usernameLabel=new JLabel("Username: ");
        usernameLabel.setPreferredSize(new Dimension(100,40));
        bodyPanel.add(usernameLabel, BorderLayout.CENTER);

        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(100,20));
        bodyPanel.add(usernameTextField, BorderLayout.CENTER);

        passwordLabel=new JLabel("Password: ");
        passwordLabel.setPreferredSize(new Dimension(100,40));
        bodyPanel.add(passwordLabel, BorderLayout.CENTER);

        passwordTextField=new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(100,20));
        passwordTextField.setEchoChar('*');
        bodyPanel.add(passwordTextField, BorderLayout.CENTER);

        emailCodeRB = new JRadioButton("Email Code");
        emailCodeRB.addActionListener(this);
        bodyPanel.add(emailCodeRB, BorderLayout.SOUTH);

        softwareTokenRB = new JRadioButton("Software Token Code");
        softwareTokenRB.addActionListener(this);
        bodyPanel.add(softwareTokenRB, BorderLayout.SOUTH);

        secretQuestionRB = new JRadioButton("Secret Question");
        secretQuestionRB.addActionListener(this);
        bodyPanel.add(secretQuestionRB, BorderLayout.SOUTH);

        logInButton = new JButton("Log In");
        logInButton.addActionListener(this);
        logInButton.setToolTipText("Log into your existing account");
        logInButton.setBackground(new Color(139,0,139));
        logInButton.setForeground(Color.WHITE);
        footerPanel.add(logInButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setToolTipText("Create an account for our platform");
        registerButton.setBackground(new Color(139,0,139));
        registerButton.setForeground(Color.WHITE);
        footerPanel.add(registerButton);

        this.setSize(270,450);
        this.setTitle("LogIn");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(appIcon.getImage());
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.add(headerPanel,BorderLayout.NORTH);
        this.add(bodyPanel,BorderLayout.CENTER);
        this.add(footerPanel,BorderLayout.SOUTH);
    }

    public static void clearFields() {
        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // SQL Injection Protected
        String username = Database.injectionProtection(usernameTextField.getText());
        String password = Database.injectionProtection(new String(passwordTextField.getPassword()));

        // SQL Injection Vulnerable
        // String username = usernameTextField.getText();
        // String password = new String(passwordTextField.getPassword());

        if(e.getSource() == emailCodeRB) {
            softwareTokenRB.setSelected(false);
            secretQuestionRB.setSelected(false);
        }

        else if(e.getSource() == softwareTokenRB) {
            emailCodeRB.setSelected(false);
            secretQuestionRB.setSelected(false);
        }

        else if(e.getSource() == secretQuestionRB) {
            emailCodeRB.setSelected(false);
            softwareTokenRB.setSelected(false);
        }

        else if(e.getSource()==registerButton){
            frameNavigator.showFrame(Register.class);
        }

        else if(e.getSource()==logInButton){
            // NOT VULNERABLE
            if (Database.isValidLogin(username, password)) {

                Database.setCurrentUserId(Database.getUserId(username));
                if (emailCodeRB.isSelected()) {
                    try {
                        Database.generateEmailCode();
                        emailSend.sendEmail(Database.getEmail(), "Rusty Rents Authentication Code", Database.getCurrentGeneratedCode());
                        frameNavigator.showFrame(LogInEmailCodeWindow.class);
                        this.dispose();
                    } catch (Exception b) {
                        logger.fatal(b.getMessage());
                    }
                }

                else if (softwareTokenRB.isSelected()) {
                    try {
                        frameNavigator.showFrame(SoftwareToken.class);
                    } catch (Exception b) {
                        logger.fatal(b.getMessage());
                    }
                }

                else if (secretQuestionRB.isSelected()) {
                    try {
                        frameNavigator.showFrame(SecretQuestion.class);
                    } catch (Exception b) {
                        logger.fatal(b.getMessage());
                    }
                }

            }
            else {
                // TODO SWING : Red label for failed login attempt ("Неправилно въведени данни")
            }



            // VULNERABLE EXAMPLE

            /*
                Database.setCurrentUserId(Database.isValidLoginVulnerable(username, password));
                if (emailCodeRB.isSelected()) {
                    try {
                        Database.generateEmailCode();
                        emailSend.sendEmail(Database.getEmail(), "Rusty Rents Authentication Code", Database.getCurrentGeneratedCode());
                        frameNavigator.showFrame(LogInEmailCodeWindow.class);
                        this.dispose();
                    } catch (Exception b) {
                              logger.fatal(b.getMessage());
                    }
                }

                else if (softwareTokenRB.isSelected()) {
                    try {
                        frameNavigator.showFrame(SoftwareToken.class);
                    } catch (Exception b) {
                              logger.fatal(b.getMessage());
                    }
                }

                else if (secretQuestionRB.isSelected()) {
                    try {
                        frameNavigator.showFrame(MainMenu.class);
                    } catch (Exception b) {
                              logger.fatal(b.getMessage());
                    }
                }


        }

        if (e.getSource() == emailCodeRB) {
            softwareTokenRB.setSelected(false);
            secretQuestionRB.setSelected(false);
        }

        if (e.getSource() == softwareTokenRB) {
            emailCodeRB.setSelected(false);
            secretQuestionRB.setSelected(false);
        }

        if (e.getSource() == secretQuestionRB) {
            emailCodeRB.setSelected(false);
            softwareTokenRB.setSelected(false);
        }
             */

    }


}
}