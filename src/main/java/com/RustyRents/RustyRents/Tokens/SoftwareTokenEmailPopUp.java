package com.RustyRents.RustyRents.Tokens;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.LogIn;
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
public class SoftwareTokenEmailPopUp extends JFrame implements ActionListener {

    private FrameNavigator frameNavigator;

    @Autowired
    private EmailSenderService emailSend;
    JLabel enterCodeLabel, errorLabel;

    JTextField codeTF;

    JButton verifyButton, resendCodeButton;

    JPanel headerPanel, bodyPanel, footerPanel;

    public SoftwareTokenEmailPopUp(FrameNavigator frameNavigator) {
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

        resendCodeButton = new JButton("Resend Code");
        resendCodeButton.setBackground(new Color(139,0,139));
        resendCodeButton.setForeground(Color.WHITE);
        resendCodeButton.addActionListener(this);
        footerPanel.add(resendCodeButton);


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

        if (e.getSource() == verifyButton) {
            if (Database.checkEmailCodeMatch(codeTF.getText())) {
                frameNavigator.showFrame(SoftwareToken.class);
                codeTF.setText("");
                errorLabel.setVisible(false);
                this.dispose();
            }
            else {
                errorLabel.setText("Email code is invalid.");
                errorLabel.setVisible(true);
            }
        }

        if (e.getSource() == resendCodeButton) {
            errorLabel.setText("Resending code");
            errorLabel.setVisible(true);
            Database.removeEmailCode();
            Database.generateEmailCode();
            emailSend.sendEmail("bdaskalov02@gmail.com", "Rusty Rents Authentication Code", Database.getCurrentGeneratedCode());
        }
    }
}
