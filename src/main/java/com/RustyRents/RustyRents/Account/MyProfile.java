package com.RustyRents.RustyRents.Account;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Database.Database;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MyProfile extends JFrame implements ActionListener {

    JButton backButton;

    JButton changeEmailButton = new JButton();
    JButton changePasswordButton = new JButton();

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

        changePasswordButton.setBounds(25,600,175,50);
        changePasswordButton.setText("Change Password");
        changePasswordButton.addActionListener(this);
        changePasswordButton.setBackground(new Color(139,0,139));
        changePasswordButton.setForeground(Color.white);
        changePasswordButton.setFocusable(false);

        changeEmailButton.setBounds(275,600,175,50);
        changeEmailButton.setText("Change Email");
        changeEmailButton.addActionListener(this);
        changeEmailButton.setBackground(new Color(139,0,139));
        changeEmailButton.setForeground(Color.white);
        changeEmailButton.setFocusable(false);


        JLabel username = new JLabel("Username: ");
        username.setBounds(135, 270, 130, 15);

        JLabel email = new JLabel("Email: ");
        email.setBounds(135, 320, 130, 15);


        JLabel usernameData = new JLabel(Database.getUsername());
        usernameData.setBounds(205, 270, 200, 15);

        JLabel emailData = new JLabel(Database.getEmail());
        emailData.setBounds(175, 320, 200, 15);


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

        this.setTitle("Renter MyProfile");
        this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,700);
        this.setLocationRelativeTo(null);
        this.add(layeredPane);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==changeEmailButton) {

        }

        else if (e.getSource()==changePasswordButton) {

        }

        else if (e.getSource() == backButton) {
            frameNavigator.showFrame(MainMenu.class);
        }
    }
}
