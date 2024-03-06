package com.RustyRents.RustyRents.Register;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.LogIn;
import com.RustyRents.RustyRents.Database.Database;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

@Component
public class Register extends JFrame implements ActionListener {

    JPanel panelContainer=new JPanel();
    JPanel firstPanel=new JPanel();
    JPanel secondPanel=new JPanel();
    JPanel thirdPanel=new JPanel();
    CardLayout cardLayout = new CardLayout();

    JButton customerButton=new JButton("Renter");
    JButton landlordButton=new JButton("Landlord");
    JButton backToLogInScreenButton=new JButton("Back to Log in");
    JButton nextStepButton=new JButton("Next");
    JButton backButton=new JButton("Back");
    JButton finalNextButton=new JButton("Next");

    JComboBox customerStatusComboBox;
    JComboBox preferredLocationComboBox;

    JTextField usernameTextField;
    JPasswordField passwordTextField;
    JPasswordField confirmPasswordTextField;
    JTextField emailTextField;

    private final FrameNavigator frameNavigator;

    public Register(FrameNavigator frameNavigator){
        this.frameNavigator = frameNavigator;
        ImageIcon appIcon = new ImageIcon ("RustyRentsIcon.png");
        ImageIcon registrationIcon=new ImageIcon("RustyRentsLogo.png");
        ImageIcon secondPanelLogo= new ImageIcon("RustyRentsLogo.png");
        ImageIcon thirdPanelLogo=new ImageIcon("RustyRentsLogo.png");
        Image rustyRentsLogo=registrationIcon.getImage();
        Image newing = rustyRentsLogo.getScaledInstance(100,100,Image.SCALE_SMOOTH);

        registrationIcon=new ImageIcon(newing);
        secondPanelLogo=new ImageIcon(newing);
        thirdPanelLogo=new ImageIcon(newing);

        panelContainer.setLayout(cardLayout);
        panelContainer.add(secondPanel,"1");
        cardLayout.show(panelContainer,"1");

        //firstPanel
        firstPanel.setLayout(new BorderLayout());
        JLabel headerImage= new JLabel(registrationIcon);
        headerImage.setPreferredSize(new Dimension(100,100));
        JLabel accountOptionLabel=new JLabel("Choose your preferred option");

        customerButton.setForeground(Color.white);
        customerButton.setFocusable(false);
        customerButton.setBackground(new Color(139,0,139));
        customerButton.addActionListener(this);

        landlordButton.setForeground(Color.white);
        landlordButton.setFocusable(false);
        landlordButton.setBackground(new Color(139,0,139));
        landlordButton.addActionListener(this);

        backToLogInScreenButton.setForeground(Color.white);
        backToLogInScreenButton.setFocusable(false);
        backToLogInScreenButton.setBackground(new Color(139,0,139));
        backToLogInScreenButton.addActionListener(this);

        //subPanels
        JPanel subPanelForFirstPanel1=new JPanel();
        JPanel subPanelForFirstPanel2=new JPanel();
        JPanel subPanelForFirstPanel3=new JPanel();

        initializeSubPanels(subPanelForFirstPanel1,subPanelForFirstPanel2,subPanelForFirstPanel3);

        secondPanel.setLayout(new BorderLayout());

        JLabel headerImageForSecondPanel=new JLabel(secondPanelLogo);
        JLabel usernameLabel= new JLabel("Username:");
        JLabel passwordLabel=new JLabel("Password:");
        JLabel confirmPasswordLabel=new JLabel("Confirm password:");
        JLabel emailLabel=new JLabel("E-mail:");

        usernameTextField= new JTextField();
        usernameTextField.setPreferredSize(new Dimension(70,20));
        passwordTextField=new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(70,20));
        passwordTextField.setEchoChar('*');
        confirmPasswordTextField=new JPasswordField();
        confirmPasswordTextField.setPreferredSize(new Dimension(70,20));
        confirmPasswordTextField.setEchoChar('*');
        emailTextField=new JTextField();
        emailTextField.setPreferredSize(new Dimension(70,20));

        nextStepButton.addActionListener(this);
        nextStepButton.setForeground(Color.white);
        nextStepButton.setBackground(new Color(139,0,139));
        backButton.addActionListener(this);
        backButton.setForeground(Color.white);
        backButton.setBackground(new Color(139,0,139));

        //subPanels
        JPanel subPanelForSecondPanel1=new JPanel();
        JPanel subPanelForSecondPanel2=new JPanel();
        JPanel subPanelForSecondPanel3=new JPanel();
        initializeSubPanels(subPanelForSecondPanel1,subPanelForSecondPanel2,subPanelForSecondPanel3);

        subPanelForSecondPanel1.add(headerImageForSecondPanel);

        subPanelForSecondPanel2.add(usernameLabel);
        subPanelForSecondPanel2.add(usernameTextField);
        subPanelForSecondPanel2.add(passwordLabel);
        subPanelForSecondPanel2.add(passwordTextField);
        subPanelForSecondPanel2.add(confirmPasswordLabel);
        subPanelForSecondPanel2.add(confirmPasswordTextField);
        subPanelForSecondPanel2.add(emailLabel);
        subPanelForSecondPanel2.add(emailTextField);;

        subPanelForSecondPanel3.add(nextStepButton);
        subPanelForSecondPanel3.add(backButton);

        secondPanel.add(subPanelForSecondPanel1,BorderLayout.NORTH);
        secondPanel.add(subPanelForSecondPanel2,BorderLayout.CENTER);
        secondPanel.add(subPanelForSecondPanel3,BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(appIcon.getImage());
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.add(panelContainer);
        this.pack();
    }


    public void innitUI(){
        this.setVisible(true);
    }

    public void closeUI() {
        this.setVisible(false);
    }

    private static void initializeSubPanels(JPanel panel1,JPanel panel2,JPanel panel3){
        panel1.setBackground(new Color(248,240,255));
        panel2.setBackground(new Color(248,240,255));
        panel3.setBackground(new Color(248,240,255));

        panel1.setPreferredSize(new Dimension(200,130));
        panel2.setPreferredSize(new Dimension(160,150));
        panel3.setPreferredSize(new Dimension(200,50));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backToLogInScreenButton){
            frameNavigator.showFrame(LogIn.class);
        }
        if(e.getSource()==backButton){
            frameNavigator.showFrame(LogIn.class);
        }
        if(e.getSource()==nextStepButton){

            // Case when there is an empty field
            if (usernameTextField.getText().equals("")
                    || passwordTextField.getPassword().length == 0
                    || confirmPasswordTextField.getPassword().length == 0
                    || emailTextField.getText().equals("")) {
                // TODO SWING : Label "Има непопълнени полета"
                System.out.println("Empty fields.");
            }
            // Case when Password and Confirm Password do not match
            else if (!Arrays.equals(passwordTextField.getPassword(), confirmPasswordTextField.getPassword())) {
                // TODO SWING : Label "Паролата не съвпада с горната"
                System.out.println("Password and Confirm password do not match.");
            }
            // Case when Username is taken
            else if (Database.checkIfUsernameIsTaken(usernameTextField.getText())) {
                // TODO SWING : Label "Имейлът се използва от друг потребител."
                System.out.println("Username is already taken.");
            }
            // Case when Email is taken
            else if (Database.checkIfEmailIsTaken(emailTextField.getText())) {
                // TODO SWING : Label "Името се използва от друг потребител."
                System.out.println("Email is already taken.");
            }
            else {
                Database.addNewUser(usernameTextField.getText(), new String(passwordTextField.getPassword()), emailTextField.getText());
                frameNavigator.showFrame(LogIn.class);
            }
        }
    }
}
