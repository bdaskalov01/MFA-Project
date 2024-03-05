package com.RustyRents.RustyRents.LogIn;

import com.RustyRents.RustyRents.Account.ChangePassword;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Register.Register;
import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.Services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class LogIn extends JFrame implements ActionListener {
    JPanel headerPanel, bodyPanel, footerPanel;
    JButton logInButton, registerButton;
    JLabel headerImage, usernameLabel, passwordLabel;
    ImageIcon appIcon, logInIcon;
    Image rustyRentsLogo, newing;
    JTextField usernameTextField;
    JPasswordField passwordTextField;

    @Autowired
    private EmailSenderService emailSend;

    private final FrameNavigator frameNavigator;

    public LogIn(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;
        ImageIcon appIcon = new ImageIcon ("RustyRentsIcon.png");
        ImageIcon logInIcon=new ImageIcon("RustyRentsLogo.png");
        Image rustyRentsLogo=logInIcon.getImage();
        Image newing = rustyRentsLogo.getScaledInstance(100,100,Image.SCALE_SMOOTH);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(appIcon.getImage());
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        logInIcon= new ImageIcon(newing);

        JPanel headerPanel=new JPanel();
        JPanel bodyPanel=new JPanel();
        JPanel footerPanel=new JPanel();

        headerPanel.setPreferredSize(new Dimension(100,100));
        bodyPanel.setPreferredSize(new Dimension(100,100));
        footerPanel.setPreferredSize(new Dimension(100,70));
        headerPanel.setBackground(new Color(248,240,255));
        bodyPanel.setBackground(new Color(248,240,255));
        footerPanel.setBackground(new Color(248,240,255));


        this.add(headerPanel,BorderLayout.NORTH);
        this.add(bodyPanel,BorderLayout.CENTER);
        this.add(footerPanel,BorderLayout.SOUTH);
        this.setSize(270,500);
        this.setResizable(false);

        JLabel headerImage = new JLabel(logInIcon);
        headerPanel.add(headerImage,BorderLayout.CENTER);
        headerImage.setPreferredSize(new Dimension(100,100));

        JLabel usernameLabel=new JLabel("Username: ");
        usernameLabel.setPreferredSize(new Dimension(100,100));
        bodyPanel.add(usernameLabel,BorderLayout.CENTER);

        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(100,20));
        bodyPanel.add(usernameTextField);

        JLabel passwordLabel=new JLabel("Password: ");
        passwordLabel.setPreferredSize(new Dimension(100,100));
        bodyPanel.add(passwordLabel);

        passwordTextField=new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(100,20));
        passwordTextField.setEchoChar('*');
        bodyPanel.add(passwordTextField);

        logInButton = new JButton("Log In");
        logInButton.addActionListener(this);
        logInButton.setToolTipText("Log into your existing account");
        logInButton.setBackground(new Color(139,0,139));
        logInButton.setForeground(Color.WHITE);
        footerPanel.add(logInButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setToolTipText("Create a account for our platform");
        registerButton.setBackground(new Color(139,0,139));
        registerButton.setForeground(Color.WHITE);
        footerPanel.add(registerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==registerButton){
            frameNavigator.showFrame(Register.class);
        }
        if(e.getSource()==logInButton){
            // Username and Password combination exist in Database i.e. Login is successful
            System.out.println(Database.getCurrentUserId());
            if (Database.isValidLogin(usernameTextField.getText(), new String(passwordTextField.getPassword()))) {

                Database.setCurrentUserId(Database.getUserId(usernameTextField.getText()));
                try {
                    Database.generateEmailCode();
                    emailSend.sendEmail("bdaskalov02@gmail.com", "Rusty Rents Authentication Code", Database.getCurrentGeneratedCode());
                    frameNavigator.showFrame(LogInEmailCodeWindow.class);
                }
              catch (Exception b) {
                  System.out.println(b);
              }


            }
            else {
                // TODO SWING : Red label for failed login attempt ("Неправилно въведени данни")
            }
        }
    }
}