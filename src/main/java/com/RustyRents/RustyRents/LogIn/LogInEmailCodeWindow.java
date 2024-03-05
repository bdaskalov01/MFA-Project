package com.RustyRents.RustyRents.LogIn;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.RustyRents.RustyRents.MainMenu.MainMenu;
import org.springframework.stereotype.Component;
@Component
public class LogInEmailCodeWindow extends JFrame implements ActionListener {

    JButton backButton, confirmButton;
    JLabel codeLabel, codeIsWrong;
    JTextField codeText;
    JPanel background;
    JLayeredPane pane;

    ImageIcon logo;
    private final FrameNavigator frameNavigator;
    public LogInEmailCodeWindow(FrameNavigator frameNavigator) {

        this.frameNavigator = frameNavigator;

        logo = new ImageIcon("RustyRentsIcon.png");


        background = new JPanel();
        background.setBounds(0,0,500,700);
        background.setBackground(new Color(248,240,255));
        background.setVisible(true);


        codeLabel = new JLabel("Код:");
        codeLabel.setBounds(140, 45, 120, 15);

        codeIsWrong = new JLabel("Паролите не съвпадат.");
        codeIsWrong.setBounds(170,70,150,30);
        codeIsWrong.setForeground(Color.RED);
        codeIsWrong.setVisible(false);


        codeText = new JTextField();
        codeText.setBounds(170, 40, 195, 30);


        confirmButton = new JButton("Потвърди");
        confirmButton.setBounds(70, 130, 150, 50);
        confirmButton.setFocusable(false);
        confirmButton.addActionListener(this);
        confirmButton.setBackground(new Color(139,0,139));
        confirmButton.setForeground(Color.WHITE);

        backButton = new JButton("Отказ");
        backButton.setBounds(260, 130, 150 , 50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setBackground(new Color(139,0,139));
        backButton.setForeground(Color.WHITE);


        pane = new JLayeredPane();
        pane.setBounds(0, 0, 500, 300);
        pane.add(background, Integer.valueOf(0));
        pane.add(codeLabel, Integer.valueOf(1));
        pane.add(codeText, Integer.valueOf(2));
        pane.add(confirmButton, Integer.valueOf(3));
        pane.add(backButton, Integer.valueOf(4));
        pane.add(codeIsWrong, Integer.valueOf(5));

        this.setTitle("Email");
        this.setIconImage(logo.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,250);
        this.add(pane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==backButton) {
            frameNavigator.showFrame(LogIn.class);
            Database.setCurrentGeneratedCode(null);
        }

        else if (e.getSource()==confirmButton) {
            if (Database.checkEmailCodeMatch(codeText.getText())) {
                frameNavigator.showFrame(MainMenu.class);
            }
            else if (!Database.checkEmailCodeMatch(codeText.getText())) {
                codeIsWrong.setVisible(true);
            }
        }
    }
}
