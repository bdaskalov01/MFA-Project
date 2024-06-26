package com.RustyRents.RustyRents.Register;

import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.LogIn;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class PopUpWindow extends JFrame implements ActionListener {

    JButton backToLogInButton;

    private final FrameNavigator frameNavigator;
    public PopUpWindow(FrameNavigator framenavigator) {
        this.frameNavigator = framenavigator;
        ImageIcon logo = new ImageIcon("RustyRentsIcon.png");

        backToLogInButton = new JButton();
        backToLogInButton.setBounds(200, 60, 100, 50);
        backToLogInButton.setText("ОК");
        backToLogInButton.addActionListener(this);
        backToLogInButton.setFocusable(false);
        backToLogInButton.setBackground(new Color(139,0,139));
        backToLogInButton.setForeground(Color.WHITE);

        JLabel text = new JLabel("Върнете се обратно в Log In менюто! Сега се log-нете във вашия акаунт.");
        text.setBounds(10, 0,500,30);

        JPanel background = new JPanel();
        background.setBounds(0,0,500,160);
        background.setBackground(new Color(248,240,255));
        background.setVisible(true);

        JLayeredPane pain = new JLayeredPane();
        pain.setBounds(0, 0, 470, 300);
        pain.add(background, Integer.valueOf(0));
        pain.add(text, Integer.valueOf(1));
        pain.add(backToLogInButton, Integer.valueOf(2));
        pain.setOpaque(false);

        this.setIconImage(logo.getImage());
        this.setTitle("test                                                    PHP <3");
        this.setResizable(true);
        this.setSize(500, 160);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(pain);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToLogInButton) {

        }
    }
}
