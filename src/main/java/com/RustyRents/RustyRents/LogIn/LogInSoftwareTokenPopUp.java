package com.RustyRents.RustyRents.LogIn;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import com.RustyRents.RustyRents.Services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Component
public class LogInSoftwareTokenPopUp extends JFrame implements ActionListener {

    JButton backButton, verifyButton;
    JLabel enterCodeLabel, errorLabel;
    JTextField codeTF;
    JPanel headerPanel, bodyPanel, footerPanel;

    ImageIcon logo;
    private final FrameNavigator frameNavigator;

    public LogInSoftwareTokenPopUp(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        headerPanel = new JPanel();

        enterCodeLabel = new JLabel("Enter software token code");
        enterCodeLabel.setPreferredSize(new Dimension(100,20));
        headerPanel.add(enterCodeLabel);

        bodyPanel = new JPanel();

        codeTF = new JTextField();
        codeTF.setPreferredSize(new Dimension(160,20));
        bodyPanel.add(codeTF);
        errorLabel = new JLabel("Software token code is invalid.");
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
            else{
                errorLabel.setVisible(true);
            }
        }
    }
}
