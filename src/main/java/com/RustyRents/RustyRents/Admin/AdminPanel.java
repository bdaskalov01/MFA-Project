package com.RustyRents.RustyRents.Admin;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Component
public class AdminPanel extends JFrame implements ActionListener {

    private FrameNavigator frameNavigator;

    JButton usersPanelButton, listingsPanelButton, backButton;

    JLayeredPane layeredPane;

    JPanel panel;


    public AdminPanel(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        panel = new JPanel();
        panel.setBackground(new Color(248,240,255));

        usersPanelButton = new JButton("Users Panel");
        usersPanelButton.addActionListener(this);
        usersPanelButton.setBackground(new Color(139,0,139));
        usersPanelButton.setForeground(Color.white);
        panel.add(usersPanelButton);

        listingsPanelButton = new JButton("Listings Panel");
        listingsPanelButton.addActionListener(this);
        listingsPanelButton.setBackground(new Color(139,0,139));
        listingsPanelButton.setForeground(Color.white);
        panel.add(listingsPanelButton);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(139,0,139));
        backButton.setForeground(Color.white);
        panel.add(backButton);

        this.setTitle("Admin panel");
        this.getContentPane().add(panel);
        this.setLocationRelativeTo(null);
        this.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == usersPanelButton) {
            frameNavigator.showFrame(UsersAdmin.class);
        }

        else if (e.getSource() == listingsPanelButton){
            frameNavigator.showFrame(ListingsAdmin.class);
        }

        else if (e.getSource() == backButton) {
            frameNavigator.showFrame(MainMenu.class);
        }
    }
}
