package com.RustyRents.RustyRents.Security;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.LogIn.LogIn;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class SecretQuestion extends JFrame implements ActionListener {


    FrameNavigator frameNavigator;

    JLayeredPane layeredPane;

    JPanel panel1, panel2, panel3, panel4;

    static JLabel enterTextLabel;
    JLabel errorLabel;

    JTextField inputTf;

    JButton backButton, finishButton;

    public SecretQuestion (FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.Y_AXIS));

        panel1 = new JPanel();
        panel1.setBackground(new Color(248,240,255));
        panel2 = new JPanel();
        panel2.setBackground(new Color(248,240,255));
        panel3 = new JPanel();
        panel3.setBackground(new Color(248,240,255));
        panel4 = new JPanel();
        panel4.setBackground(new Color(248,240,255));

        // TODO: replace "Enter secret question" with the question from the DB.
        enterTextLabel = new JLabel();
        panel1.add(enterTextLabel);

        inputTf = new JTextField(20);
        panel2.add(inputTf);

        errorLabel = new JLabel();
        panel3.add(errorLabel);

        backButton = new JButton("Back");
        backButton.setBackground(new Color(139,0,139));
        backButton.setForeground(Color.white);
        backButton.addActionListener(this);
        panel4.add(backButton);

        finishButton = new JButton("Enter");
        finishButton.setBackground(new Color(139,0,139));
        finishButton.setForeground(Color.white);
        finishButton.addActionListener(this);
        panel4.add(finishButton);

        layeredPane.add(panel1);
        layeredPane.add(panel2);
        layeredPane.add(panel3);
        layeredPane.add(panel4);

        this.setTitle("Secret question");
        //this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(248,240,255));
        this.getContentPane().setForeground(Color.WHITE);
        this.setResizable(false);
        this.getContentPane().add(layeredPane);
        this.setLocationRelativeTo(null);
        this.pack();
    }

    public void refreshUIData() {
        enterTextLabel.setText(Database.getSecretQuestion());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {
            inputTf.setText("");
            frameNavigator.showFrame(LogIn.class);
        }
        else if (e.getSource() == finishButton) {
            if (Database.checkIfAnswerIsValid(inputTf.getText())) {
                frameNavigator.showFrame(MainMenu.class);
                inputTf.setText("");
                errorLabel.setText("");
                this.pack();
            }
            else {
                errorLabel.setText("Wrong answer. Please try again.");
                errorLabel.setForeground(Color.red);
                this.pack();
            }
        }
    }
}
