package com.RustyRents.RustyRents.LogIn;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.RustyRents.RustyRents.MainMenu.MainMenu;
import org.springframework.stereotype.Component;
@Component
public class LogInEmailCodeWindow extends JFrame implements ActionListener {

    JButton backButton, verifyButton;
    JLabel enterCodeLabel, errorLabel;
    JTextField codeTF;
    JPanel headerPanel, bodyPanel, footerPanel;

    ImageIcon logo;
    private final FrameNavigator frameNavigator;
 /*   public void LogInEmailCodeWindow(FrameNavigator frameNavigator) {

        this.frameNavigator = frameNavigator;

        logo = new ImageIcon("RustyRentsIcon.png");


        background = new JPanel();
        background.setBounds(0,0,500,700);
        background.setBackground(new Color(248,240,255));
        background.setVisible(true);


        codeLabel = new JLabel("Код:");
        codeLabel.setBounds(140, 45, 120, 15);

        codeIsWrong = new JLabel("Грешен код.");
        codeIsWrong.setBounds(210,70,150,30);
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
        this.setLocationRelativeTo(null);
        this.setSize(500,250);
        this.add(pane);
    }

  */

    public LogInEmailCodeWindow(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        headerPanel = new JPanel();

        enterCodeLabel = new JLabel("Enter email code");
        enterCodeLabel.setPreferredSize(new Dimension(100,20));
        headerPanel.add(enterCodeLabel);

        bodyPanel = new JPanel();

        codeTF = new JTextField();
        codeTF.setPreferredSize(new Dimension(160,20));
        bodyPanel.add(codeTF);
        errorLabel = new JLabel("Email code is invalid.");
        errorLabel.setPreferredSize(new Dimension(130,20));
        errorLabel.setVisible(false);
        bodyPanel.add(errorLabel);

        footerPanel = new JPanel();

        verifyButton = new JButton("Verify");
        verifyButton.setBackground(new Color(139,0,139));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.addActionListener(this);
        footerPanel.add(verifyButton);

        backButton = new JButton("Back");
        backButton.setBackground(new Color(139,0,139));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        footerPanel.add(backButton);


        this.setTitle("Software token verification");
        //this.setIconImage(logo.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //  frameNavigator.showFrame(LogIn.class);
            }
        });
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setSize(300,150);
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(bodyPanel, BorderLayout.CENTER);
        this.add(footerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==backButton) {
            frameNavigator.showFrame(LogIn.class);
            Database.setCurrentGeneratedCode("");
            Database.removeAllEmailCodes();
        }

        else if (e.getSource()==verifyButton) {
            if (Database.checkEmailCodeMatch(codeTF.getText())) {
                frameNavigator.showFrame(MainMenu.class);
                errorLabel.setVisible(false);
                this.dispose();
            }
            else if (!Database.checkEmailCodeMatch(codeTF.getText())) {
                errorLabel.setVisible(true);
            }
        }
    }
}
