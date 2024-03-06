package com.RustyRents.RustyRents.Options;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class Options extends JFrame implements ActionListener {
    JButton resolutionButton, termsAndConditionsButton, changeEmailButton, changePasswordButton, changeThemeButton, backButton;
    JLayeredPane layeredPane;
    ImageIcon optionsIcon, backIcon;
    private final FrameNavigator frameNavigator;

    public Options(FrameNavigator framenavigator){
        this.frameNavigator = framenavigator;

        // TODO : Rework

        optionsIcon=new ImageIcon("RustyRentsLogo.png");
        backIcon = new ImageIcon("BackIcon.png");

        resolutionButton = new JButton();
        resolutionButton.setBounds(120,50,250,50);
        resolutionButton.setText("Resolution");
        resolutionButton.setBackground(new Color(139,0,139));
        resolutionButton.setForeground(Color.white);
        resolutionButton.setFocusable(false);

        changeThemeButton = new JButton();
        changeThemeButton.setBounds(120,130,250,50);
        changeThemeButton.setText("Change Theme");
        changeThemeButton.setBackground(new Color(139,0,139));
        changeThemeButton.setForeground(Color.white);
        changeThemeButton.setFocusable(false);

        changeEmailButton = new JButton();
        changeEmailButton.setBounds(120,210,250,50);
        changeEmailButton.setText("Change Email");
        changeEmailButton.addActionListener(this);
        changeEmailButton.setBackground(new Color(139,0,139));
        changeEmailButton.setForeground(Color.white);
        changeEmailButton.setFocusable(false);

        changePasswordButton = new JButton();
        changePasswordButton.setBounds(120,290,250,50);
        changePasswordButton.setText("ChangePassword");
        changePasswordButton.addActionListener(this);
        changePasswordButton.setBackground(new Color(139,0,139));
        changePasswordButton.setForeground(Color.white);
        changePasswordButton.setFocusable(false);

        termsAndConditionsButton = new JButton();
        termsAndConditionsButton.setBounds(120,370,250,50);
        termsAndConditionsButton.setText("Terms and Conditions");
        termsAndConditionsButton.setBackground(new Color(139,0,139));
        termsAndConditionsButton.setForeground(Color.white);
        termsAndConditionsButton.setFocusable(false);

        backButton = new JButton(backIcon);
        backButton.setBounds(0,0,50,50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);

        layeredPane = new JLayeredPane();
        layeredPane.add(backButton,0);
        layeredPane.add(resolutionButton,1);
        layeredPane.add(changeThemeButton, 2);
        layeredPane.add(changeEmailButton, 3);
        layeredPane.add(changePasswordButton, 4);
        layeredPane.add(termsAndConditionsButton, 5);
        layeredPane.setBounds(0,0,500,500);
        layeredPane.setBackground(new Color(248,240,255));
        layeredPane.setVisible(true);
        layeredPane.setOpaque(true);

        this.add(layeredPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(optionsIcon.getImage());
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==changeEmailButton) {

        }

        else if (e.getSource()==changePasswordButton) {

        }

        else if (e.getSource() == backButton) {

        }
    }
}