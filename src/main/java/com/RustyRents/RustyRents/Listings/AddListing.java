package com.RustyRents.RustyRents.Listings;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.Tokens.SoftwareTokenEmailPopUp;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.io.File;

@Component
public class AddListing extends JFrame implements ActionListener {

    private final FrameNavigator frameNavigator;
   private int amountOfPics = 0;
   private String[] imagePath;

    JLabel titleLabel, cityLabel, listingTypeLabel, priceLabel, neighbourhoodLabel, streetLabel, streetNumberLabel,
    floorLabel, roomNumberLabel, qSizeLabel, phoneNumberLabel, imageLabel1, imageLabel2, imageLabel3, imageLabel4,
            imageLabel5, imageLabel6, imageLabel7, imageLabel8, imageLabel9;

    JLabel[] imageLabels = new JLabel[9];

    JTextField titleInput, cityInput, listingTypeInput, priceInput, neighbourhoodInput, streetInput, streetNumberInput,
    floorInput, roomNumberInput, qSizeInput, phoneNumberInput;

    ImageIcon[] images;
    ImageIcon defaultIcon;
    Image imageScale;

    File selectedFile;

    JButton backButton, secondBackButton, nextButton, finishButton, selectImageButton;

    JFileChooser fileChooser;

    FileNameExtensionFilter fileNameExtensionFilter;

    JLayeredPane firstPanel, secondPanel;

    JPanel  subPanel1_1, subPanel2_1, subPanel3_1, subPanel4_1, subPanel5_1,
            subPanel6_1, subPanel7_1, subPanel8_1, subPanel9_1, subPanel10_1, subPanel11_1, subPanel12_1,
            subPanel1_2, subPanel2_2,subPanel3_2, subPanel4_2, subPanel5_2, subPanel6_2, subPanel7_2, subPanel8_2,
            subPanel9_2, subPanel10_2, subPanel11_2, subPanel12_2, subPanel1_3, subPanel2_3, subPanel3_3, subPanel4_3,
            subPanel5_3, subPanel6_3, subPanel7_3, subPanel8_3, subPanel9_3, subPanel10_3, subPanel11_3, subPanel12_3,
            ImagesSubPanel, buttonsSubPanel;


    public AddListing(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        innitSubPanels();
        innitPanels();

        this.setTitle("Добавяне на обява");
      //  this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(248,240,255));
        this.setForeground(Color.WHITE);
        this.setResizable(false);
        //this.setSize(550,550);
        this.setLayout(new FlowLayout());
        this.add(firstPanel);
        this.pack();
        this.setLocationRelativeTo(null);

    }

    private void innitSubPanels(){
        subPanel1_1 = new JPanel();
        subPanel1_1.setBackground(new Color(248,240,255));
        subPanel2_1 = new JPanel();
        subPanel2_1.setBackground(new Color(248,240,255));
        subPanel3_1 = new JPanel();
        subPanel3_1.setBackground(new Color(248,240,255));
        subPanel4_1 = new JPanel();
        subPanel4_1.setBackground(new Color(248,240,255));
        subPanel5_1 = new JPanel();
        subPanel5_1.setBackground(new Color(248,240,255));
        subPanel6_1 = new JPanel();
        subPanel6_1.setBackground(new Color(248,240,255));
        subPanel7_1 = new JPanel();
        subPanel7_1.setBackground(new Color(248,240,255));
        subPanel8_1 = new JPanel();
        subPanel8_1.setBackground(new Color(248,240,255));
        subPanel9_1 = new JPanel();
        subPanel9_1.setBackground(new Color(248,240,255));
        subPanel10_1 = new JPanel();
        subPanel10_1.setBackground(new Color(248,240,255));
        subPanel11_1 = new JPanel();
        subPanel11_1.setBackground(new Color(248,240,255));
        subPanel12_1 = new JPanel();
        subPanel12_1.setBackground(new Color(248,240,255));
        subPanel1_2 = new JPanel();
        subPanel1_2.setBackground(new Color(248,240,255));
        subPanel2_2 = new JPanel();
        subPanel2_2.setBackground(new Color(248,240,255));
        subPanel3_2 = new JPanel();
        subPanel3_2.setBackground(new Color(248,240,255));
        subPanel4_2 = new JPanel();
        subPanel4_2.setBackground(new Color(248,240,255));
        subPanel5_2 = new JPanel();
        subPanel5_2.setBackground(new Color(248,240,255));
        subPanel6_2 = new JPanel();
        subPanel6_2.setBackground(new Color(248,240,255));
        subPanel7_2 = new JPanel();
        subPanel7_2.setBackground(new Color(248,240,255));
        subPanel8_2 = new JPanel();
        subPanel8_2.setBackground(new Color(248,240,255));
        subPanel9_2 = new JPanel();
        subPanel9_2.setBackground(new Color(248,240,255));
        subPanel10_2 = new JPanel();
        subPanel10_2.setBackground(new Color(248,240,255));
        subPanel11_2 = new JPanel();
        subPanel11_2.setBackground(new Color(248,240,255));
        subPanel12_2 = new JPanel();
        subPanel12_2.setBackground(new Color(248,240,255));
        subPanel1_3 = new JPanel();
        subPanel1_3.setBackground(new Color(248,240,255));
        subPanel2_3 = new JPanel();
        subPanel2_3.setBackground(new Color(248,240,255));
        subPanel3_3 = new JPanel();
        subPanel3_3.setBackground(new Color(248,240,255));
        subPanel4_3 = new JPanel();
        subPanel4_3.setBackground(new Color(248,240,255));
        subPanel5_3 = new JPanel();
        subPanel5_3.setBackground(new Color(248,240,255));
        subPanel6_3 = new JPanel();
        subPanel6_3.setBackground(new Color(248,240,255));
        subPanel7_3 = new JPanel();
        subPanel7_3.setBackground(new Color(248,240,255));
        subPanel8_3 = new JPanel();
        subPanel8_3.setBackground(new Color(248,240,255));
        subPanel9_3 = new JPanel();
        subPanel9_3.setBackground(new Color(248,240,255));
        subPanel10_3 = new JPanel();
        subPanel10_3.setBackground(new Color(248,240,255));
        subPanel11_3 = new JPanel();
        subPanel11_3.setBackground(new Color(248,240,255));
        subPanel12_3 = new JPanel();
        subPanel12_3.setBackground(new Color(248,240,255));
    }

    private void innitPanels() {
        firstPanel = new JLayeredPane();
        firstPanel.setPreferredSize(new Dimension(350,550));
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Title:");
        subPanel1_1.add(titleLabel);

        titleInput = new JTextField(15);
        subPanel1_1.add(titleInput);

        cityLabel = new JLabel("City:");
        subPanel2_1.add(cityLabel);

        cityInput = new JTextField(15);
        subPanel2_1.add(cityInput);

        listingTypeLabel = new JLabel("Listing Type:");
        subPanel3_1.add(listingTypeLabel);

        listingTypeInput = new JTextField(15);
        subPanel3_1.add(listingTypeInput);

        priceLabel = new JLabel("Price:");
        subPanel4_1.add(priceLabel);

        priceInput = new JTextField(15);
        subPanel4_1.add(priceInput);

        neighbourhoodLabel = new JLabel("Neighbourhood:");
        subPanel5_1.add(neighbourhoodLabel);

        neighbourhoodInput = new JTextField(15);
        subPanel5_1.add(neighbourhoodInput);

        streetLabel = new JLabel("Street:");
        subPanel6_1.add(streetLabel);

        streetInput = new JTextField(15);
        subPanel6_1.add(streetInput);

        streetNumberLabel = new JLabel("Street Number:");
        subPanel7_1.add(streetNumberLabel);

        streetNumberInput = new JTextField(15);
        subPanel7_1.add(streetNumberInput);

        floorLabel = new JLabel("Floor: ");
        subPanel8_1.add(floorLabel);

        floorInput = new JTextField(15);
        subPanel8_1.add(floorInput);

        roomNumberLabel = new JLabel("Room Number: ");
        subPanel9_1.add(roomNumberLabel);

        roomNumberInput = new JTextField(15);
        subPanel9_1.add(roomNumberInput);

        qSizeLabel = new JLabel("Q Size: ");
        subPanel10_1.add(qSizeLabel);

        qSizeInput = new JTextField(15);
        subPanel10_1.add(qSizeInput);

        phoneNumberLabel = new JLabel("Phone Number: ");
        subPanel11_1.add(phoneNumberLabel);

        phoneNumberInput = new JTextField(15);
        subPanel11_1.add(phoneNumberInput);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBackground(new Color(139,0,139));
        backButton.setForeground(Color.white);
        subPanel12_1.add(backButton, BorderLayout.WEST);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setBackground(new Color(139,0,139));
        nextButton.setForeground(Color.white);
        subPanel12_1.add(nextButton, BorderLayout.EAST);

        firstPanel.add(subPanel1_1);
        firstPanel.add(subPanel2_1);
        firstPanel.add(subPanel3_1);
        firstPanel.add(subPanel4_1);
        firstPanel.add(subPanel5_1);
        firstPanel.add(subPanel6_1);
        firstPanel.add(subPanel7_1);
        firstPanel.add(subPanel8_1);
        firstPanel.add(subPanel9_1);
        firstPanel.add(subPanel10_1);
        firstPanel.add(subPanel11_1);
        firstPanel.add(subPanel12_1);

        fileChooser = new JFileChooser();
        fileNameExtensionFilter = new FileNameExtensionFilter("Image Files", "jpg", "png");
        fileChooser.setFileFilter(fileNameExtensionFilter);

        secondPanel = new JLayeredPane();
        secondPanel.setPreferredSize(new Dimension(550,550));
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));

        selectImageButton = new JButton("Select Image");
        selectImageButton.addActionListener(this);
        selectImageButton.setBackground(new Color(139,0,139));
        selectImageButton.setForeground(Color.white);


        ImagesSubPanel = new JPanel();
        ImagesSubPanel.setLayout(new GridLayout(3, 3,0,0));

        images = new ImageIcon[9];

        imagePath = new String[9];

        defaultIcon = new ImageIcon("defaulticon.png");
        imageScale = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        defaultIcon = new ImageIcon(imageScale);


        imageLabel1 = new JLabel();
        imageLabel1.setIcon(defaultIcon);
        imageLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel1.getParent();
                System.out.println(parent);
                if (!(imageLabel1.getIcon() == defaultIcon) && (amountOfPics == 1)) {
                    System.out.println("1");
                    imageLabel1.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[1] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel1);

        imageLabel2 = new JLabel();
        imageLabel2.setIcon(defaultIcon);
        imageLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel2.getParent();
                System.out.println(parent);
                if (!(imageLabel2.getIcon() == defaultIcon) && (amountOfPics == 2)) {
                    System.out.println("1");
                    imageLabel2.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[2] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel2);

        imageLabel3 = new JLabel();
        imageLabel3.setIcon(defaultIcon);
        imageLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel3.getParent();
                System.out.println(parent);
                if (!(imageLabel3.getIcon() == defaultIcon) && (amountOfPics == 3)) {
                    System.out.println("1");
                    imageLabel3.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[3] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel3);

        imageLabel4 = new JLabel();
        imageLabel4.setIcon(defaultIcon);
        imageLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel4.getParent();
                System.out.println(parent);
                if (!(imageLabel4.getIcon() == defaultIcon) && (amountOfPics == 4)) {
                    System.out.println("1");
                    imageLabel4.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[4] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel4);

        imageLabel5 = new JLabel();
        imageLabel5.setIcon(defaultIcon);
        imageLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel5.getParent();
                System.out.println(parent);
                if (!(imageLabel5.getIcon() == defaultIcon) && (amountOfPics == 5)) {
                    System.out.println("1");
                    imageLabel5.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[5] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel5);

        imageLabel6 = new JLabel();
        imageLabel6.setIcon(defaultIcon);
        imageLabel6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel6.getParent();
                System.out.println(parent);
                if (!(imageLabel6.getIcon() == defaultIcon) && (amountOfPics == 6)) {
                    System.out.println("1");
                    imageLabel6.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[6] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel6);

        imageLabel7 = new JLabel();
        imageLabel7.setIcon(defaultIcon);
        imageLabel7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel7.getParent();
                System.out.println(parent);
                if (!(imageLabel7.getIcon() == defaultIcon) && (amountOfPics == 7)) {
                    System.out.println("1");
                    imageLabel7.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[7] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel7);

        imageLabel8 = new JLabel();
        imageLabel8.setIcon(defaultIcon);
        imageLabel8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel8.getParent();
                System.out.println(parent);
                if (!(imageLabel8.getIcon() == defaultIcon) && (amountOfPics == 8)) {
                    System.out.println("1");
                    imageLabel8.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[8] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel8);

        imageLabel9 = new JLabel();
        imageLabel9.setIcon(defaultIcon);
        imageLabel9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = imageLabel9.getParent();
                System.out.println(parent);
                if (!(imageLabel9.getIcon() == defaultIcon) && (amountOfPics == 9)) {
                    System.out.println("1");
                    imageLabel9.setIcon(defaultIcon);
                    System.out.println(parent);
                    parent.revalidate();
                    parent.repaint();
                    amountOfPics--;
                    imagePath[9] = null;
                    System.out.println(amountOfPics);
                }
                else {
                    System.out.println("2");
                    System.out.println(amountOfPics);
                }
            }
        });
        ImagesSubPanel.add(imageLabel9);

        imageLabels[0] = imageLabel1;
        imageLabels[1] = imageLabel2;
        imageLabels[2] = imageLabel3;
        imageLabels[3] = imageLabel4;
        imageLabels[4] = imageLabel5;
        imageLabels[5] = imageLabel6;
        imageLabels[6] = imageLabel7;
        imageLabels[7] = imageLabel8;
        imageLabels[8] = imageLabel9;

        secondPanel.add(ImagesSubPanel);

        finishButton = new JButton("Finish");
        finishButton.addActionListener(this);
        finishButton.setBackground(new Color(139,0,139));
        finishButton.setForeground(Color.white);
        secondBackButton = new JButton("Back");
        secondBackButton.addActionListener(this);
        secondBackButton.setBackground(new Color(139,0,139));
        secondBackButton.setForeground(Color.white);

        buttonsSubPanel = new JPanel();
        buttonsSubPanel.add(secondBackButton);
        buttonsSubPanel.add(selectImageButton);
        buttonsSubPanel.add(finishButton);

        secondPanel.add(buttonsSubPanel);

    }

    private void clearTextFields() {
       titleInput.setText("");
       cityInput.setText("");
       listingTypeInput.setText("");
       priceInput.setText("");
       neighbourhoodInput.setText("");
       streetInput.setText("");
       streetNumberInput.setText("");
       floorInput.setText("");
       roomNumberInput.setText("");
       qSizeInput.setText("");
       phoneNumberInput.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {
                frameNavigator.showFrame(MyListings.class);
                clearTextFields();
        }

        else if (e.getSource() == nextButton) {
            this.remove(firstPanel);
            this.add(secondPanel);
            this.pack();
            this.revalidate();
            this.repaint();
        }

        else if (e.getSource() == secondBackButton) {
            this.remove(secondPanel);
            this.add(firstPanel);
            this.pack();
            this.revalidate();
            this.repaint();
        }

        else if (e.getSource() == finishButton) {
            frameNavigator.showFrame(MyListings.class);
            Database.insertNewProperty(Database.injectionProtection(titleInput.getText()), Database.injectionProtection(cityInput.getText()),
                    Database.injectionProtection(listingTypeInput.getText()), Database.injectionProtection(priceInput.getText()), Database.injectionProtection(neighbourhoodInput.getText()),
                    Database.injectionProtection(streetInput.getText()), Database.injectionProtection(streetNumberInput.getText()), Database.injectionProtection(floorInput.getText()),
                    Database.injectionProtection(roomNumberInput.getText()), Database.injectionProtection(qSizeInput.getText()), Database.injectionProtection(phoneNumberInput.getText()));
            this.remove(secondPanel);
            this.add(firstPanel);
            clearTextFields();
            this.pack();
            this.revalidate();
            this.repaint();
        }

        else if (e.getSource() == selectImageButton) {
            int result = fileChooser.showOpenDialog(null);

            if (result == fileChooser.APPROVE_OPTION) {
                selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());

                images[amountOfPics] = new ImageIcon(selectedFile.toString());
                imageScale = images[amountOfPics].getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                images[amountOfPics] = new ImageIcon(imageScale);
                imageLabels[amountOfPics].setIcon(images[amountOfPics]);
                amountOfPics++;
                imagePath[amountOfPics] = selectedFile.toString();
                System.out.println(imagePath[amountOfPics]);
                System.out.println("Added: " + amountOfPics);
                this.revalidate();
                this.repaint();

            }
        }
    }
}
