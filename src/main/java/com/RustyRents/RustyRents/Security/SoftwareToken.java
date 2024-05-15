package com.RustyRents.RustyRents.Security;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.Services.EmailSenderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Lazy
@Component
public class SoftwareToken extends JFrame implements ActionListener {

    private final FrameNavigator frameNavigator;

    @Autowired
    private EmailSenderService emailSend;
    private static final String CREDENTIALS_FILE = "credentials.txt"; // File to store credentials
    private static final String SECRET_KEY = "YourSecretKey123"; // 16 characters for AES-128, 24 characters for AES-192, 32 characters for AES-256
    private static final String INIT_VECTOR = "YourInitVector12"; // 16 bytes IV for AES

    private static Logger logger = LogManager.getLogger(SoftwareToken.class.getName());
    int originalUserID;
    Timer timer;
    ImageIcon logo;
    JLayeredPane loggedPane = new JLayeredPane();
    JLayeredPane notLoggedPane = new JLayeredPane();
    JLabel remainingTimeLabel, generatedCodeLabel, usernameLabel, passwordLabel, currentUserLabel;
    JTextField usernameTF = new JTextField();
    JPasswordField passwordPF = new JPasswordField();
    JButton logInButton, logOutButton;

    private int count = 60;


    public SoftwareToken(FrameNavigator frameNavigator) throws InterruptedException {


        Path filepath = Paths.get(CREDENTIALS_FILE);

        if (Files.exists(filepath)) {
            logger.info("The file exists in the directory.");
        } else {
            logger.info("The file does not exist in the directory.");
            saveCredentials("test", "test");
        }


        this.frameNavigator = frameNavigator;

        currentUserLabel = new JLabel("Username: ");
        currentUserLabel.setBounds(90, 10, 200, 30);
        loggedPane.add(currentUserLabel, 1);

        remainingTimeLabel = new JLabel("Time left: ");
        remainingTimeLabel.setBounds(55, 60, 200, 30); // Set bounds for label
        loggedPane.add(remainingTimeLabel, 2);

        generatedCodeLabel = new JLabel("Generated code: ");
        generatedCodeLabel.setBounds(55, 90, 200, 30);
        loggedPane.add(generatedCodeLabel, 3);

        logOutButton = new JButton("Log out");
        logOutButton.setBounds(90, 150, 100, 40);
        logOutButton.addActionListener(this);
        logOutButton.setBackground(new Color(139,0,139));
        logOutButton.setForeground(Color.WHITE);
        loggedPane.add(logOutButton, 4);

        usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(110, 10, 200, 30);
        notLoggedPane.add(usernameLabel, 1);

        usernameTF.setBounds(45, 40, 200, 30);
        notLoggedPane.add(usernameTF, 2);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(110, 70, 200, 30);
        notLoggedPane.add(passwordLabel, 3);

        passwordPF.setBounds(45, 100, 200, 30);
        notLoggedPane.add(passwordPF,4);

        logInButton = new JButton("Log in");
        logInButton.setBounds(90, 150, 100, 40);
        logInButton.addActionListener(this);
        logInButton.setBackground(new Color(139,0,139));
        logInButton.setForeground(Color.WHITE);
        notLoggedPane.add(logInButton);


        Database.establishConnection();
        String[] credentials = readCredentials();
        String username = credentials[0];
        String password = credentials[1];


        timer = new Timer(1000 , new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count--;
                remainingTimeLabel.setText("Time left: " + count + " seconds");

                if (count <= 0) {
                    count = 60;
                    Database.removeSoftwareCode(Database.getUserId(username));
                    Database.generateSoftwareCode(Database.getUserId(username));
                    generatedCodeLabel.setText("Generated code: " + Database.getSoftwareCode(Database.getUserId(username)));
                }
            }
        });

        this.setTitle("Software token");
       //this.setIconImage(logo.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(300,250);



        if (Database.isValidLogin(username, password)) {
            //Database.setCurrentUserId(Database.getUserId(username));
           Database.generateSoftwareCode(Database.getUserId(username));
           this.add(loggedPane);
            Database.establishConnection();
            timer.start();
            currentUserLabel.setText("Username: " + Database.getUsername(Database.getUserId(username)));
            generatedCodeLabel.setText("Generated code: " + Database.getSoftwareCode(Database.getUserId(username)));

        }
        else if (!Database.isValidLogin(username, password)){
           this.add(notLoggedPane);
        }
    }

    public static void saveCredentials(String username, String password) {
        try {
            String encryptedCredentials = username + "=" + password; // Encrypt credentials if necessary
            encryptedCredentials = encrypt(encryptedCredentials);
            Files.write(Paths.get(CREDENTIALS_FILE), encryptedCredentials.getBytes()); // Write encrypted credentials to file
        } catch (IOException e) {
            logger.fatal(e.getMessage()); // Handle error
        }
    }

    public static void deleteCredentials() {
        try {
            Files.deleteIfExists(Paths.get(CREDENTIALS_FILE)); // Delete credentials file if it exists
        } catch (IOException e) {
            logger.fatal(e.getMessage()); // Handle error
        }
    }

    public static String[] readCredentials() {
        try {
            byte[] encodedCredentials = Files.readAllBytes(Paths.get(CREDENTIALS_FILE)); // Read all bytes from file
            String encryptedCredentials = new String(encodedCredentials); // Convert bytes to String
            encryptedCredentials = decrypt(encryptedCredentials);
            return encryptedCredentials.split("="); // Decrypt and split credentials
        } catch (IOException e) {
           logger.fatal(e.getMessage());
        }
        return null;
    }

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            logger.fatal(ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted.trim()));

            return new String(original, "UTF-8");
        } catch (IllegalArgumentException ex) {
            logger.fatal("Error: Input string is not a valid Base64-encoded string.");
        } catch (Exception ex) {
            logger.fatal(ex.getMessage());
        }

        return null;
    }

    public void setLoggedUI() {
        Database.setCurrentUserId(originalUserID);
        saveCredentials(usernameTF.getText(), new String(passwordPF.getPassword()));
        String[] credentials = readCredentials();
        String username = credentials[0];
        String password = credentials[1];
        logger.debug("Username: " + username);
        logger.debug("Password: " + password);
        currentUserLabel.setText("Username: " + Database.getUsername(Database.getUserId(username)));
        this.remove(notLoggedPane);
        this.add(loggedPane);
        this.revalidate();
        this.repaint();
        Database.generateSoftwareCode(Database.getUserId(username));
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==logInButton) {
            if (Database.isValidLogin(usernameTF.getText(), new String(passwordPF.getPassword()))) {
                originalUserID = Database.getCurrentUserId();
                Database.setCurrentUserId(Database.getUserId(usernameTF.getText()));
                Database.generateEmailCode();
                emailSend.sendEmail("bdaskalov02@gmail.com", "Rusty Rents Authentication Code", Database.getCurrentGeneratedCode());
                frameNavigator.showFrame(SoftwareTokenEmailPopUp.class);
            }
        }

        if (e.getSource()==logOutButton) {
            /*
            this.remove(loggedPane);
            this.add(notLoggedPane);
            this.revalidate();
            this.repaint();
            timer.stop();
            count = 60;
            Database.removeSoftwareCode();
            saveCredentials(null, null);
             */
            logger.fatal(Database.getCurrentUserId());
        }
    }
}
