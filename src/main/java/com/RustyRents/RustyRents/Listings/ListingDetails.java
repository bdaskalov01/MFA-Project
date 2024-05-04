/*package com.RustyRents.RustyRents.Listings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListingDetails extends JFrame implements ActionListener {

    // Variables
    JLabel name,city,neighborhood,streetName,numberOfStreet,floor,appNumber,type,m2,price,phoneNumber,listingReq;
    JLabel nameData,cityData,neighborhoodData,streetNameData,numberOfStreetData,floorData,appNumberData,typeData,m2Data,priceData,phoneNumberData;
    JButton backButton, apiButton;
    JLayeredPane layeredPane;
    ImageIcon backIcon, appIcon;
    String temp = "PLACEHOLDER";

    // JLabel positions and dimensions
    int lx = 50;
    int lxData = 225;
    int spaceBetweenRows = 40;
    int y1 = 60;
    int y2 = y1 + spaceBetweenRows;
    int y3 = y2 + spaceBetweenRows;
    int y4 = y3 + spaceBetweenRows;
    int y5 = y4 + spaceBetweenRows;
    int y6 = y5 + spaceBetweenRows;
    int y7 = y6 + spaceBetweenRows;
    int y8 = y7 + spaceBetweenRows + 15;
    int y9 = y8 + spaceBetweenRows;
    int y10 = y9 + spaceBetweenRows;
    int y11 = y10 + spaceBetweenRows;
    int lwidth = 170;
    int lheight = 15;

    //JButton positions and dimensions
    int bx1 = 175;
    int by = y11 + spaceBetweenRows + 20;
    int bwidth = 150;
    int bheight = 50;

    private final FrameNavigator frameNavigator;
    public ListingDetails(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        backIcon = new ImageIcon("BackIcon.png");
        appIcon = new ImageIcon("RustyRentsIcon.png");

        name = new JLabel("Име на обявата: ");
        name.setBounds(lx, y1, lwidth, lheight);

        city = new JLabel("Град: ");
        city.setBounds(lx,y2, lwidth, lheight);

        neighborhood = new JLabel("Квартал на града: ");
        neighborhood.setBounds(lx,y3, lwidth, lheight);

        streetName = new JLabel("Име на улица: ");
        streetName.setBounds(lx,y4, lwidth, lheight);

        numberOfStreet = new JLabel("Номер на улица: ");
        numberOfStreet.setBounds(lx,y5, lwidth, lheight);

        floor = new JLabel("Етаж (по избор): ");
        floor.setBounds(lx,y6, lwidth, lheight);

        appNumber = new JLabel("<html>Номер на стая/апартамент: <br/>(по избор)</html>");
        appNumber.setBounds(lx,y7, lwidth, lheight + 15);

        type = new JLabel("Вид на имота: ");
        type.setBounds(lx,y8, lwidth, lheight);

        m2 = new JLabel("Квадратура: ");
        m2.setBounds(lx,y9, lwidth, lheight);

        price = new JLabel("Цена (в лв.): ");
        price.setBounds(lx,y10, lwidth, lheight);

        phoneNumber = new JLabel("Телефон за връзка: ");
        phoneNumber.setBounds(lx,y11, lwidth, lheight);

        listingReq = new JLabel("Моля, въведете данните за вашата обява: ");
        listingReq.setBounds(120,20,300,15);
        listingReq.setForeground(Color.magenta);

        nameData = new JLabel(Database.getPropertyTitle());
        nameData.setAlignmentX(java.awt.Component.TOP_ALIGNMENT);
        nameData.setAlignmentY(java.awt.Component.LEFT_ALIGNMENT);
        //  btnViewProperty.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        //btnViewProperty.setAlignmentX(Component.RIGHT_ALIGNMENT);

        cityData = new JLabel(Database.getPropertyCity());
        cityData.setBounds(lxData,y2, lwidth, lheight);

        neighborhoodData = new JLabel(Database.getPropertyNeighbourhood());
        neighborhoodData.setBounds(lxData,y3, lwidth, lheight);

        streetNameData = new JLabel(Database.getPropertyStreet());
        streetNameData.setBounds(lxData,y4, lwidth, lheight);

        numberOfStreetData = new JLabel(Database.getPropertyStreetNumber());
        numberOfStreetData.setBounds(lxData,y5, lwidth, lheight);

        floorData = new JLabel(Database.getPropertyFloor());
        floorData.setBounds(lxData,y6, lwidth, lheight);

        appNumberData = new JLabel(Database.getPropertyRoomNumber());
        appNumberData.setBounds(lxData,y7, lwidth, lheight);

        typeData = new JLabel(Database.getPropertyType());
        typeData.setBounds(lxData,y8, lwidth, lheight);

        m2Data = new JLabel(Database.getPropertyQSize());
        m2Data.setBounds(lxData,y9, lwidth, lheight);

        priceData = new JLabel(Database.getPropertyPrice());
        priceData.setBounds(lxData,y10, lwidth, lheight);

        phoneNumberData = new JLabel(Database.getPropertyPhoneNumber());
        phoneNumberData.setBounds(lxData,y11, lwidth, lheight);


        backButton = new JButton(backIcon);
        backButton.setBounds(5, 5, 50,50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);

        apiButton = new JButton("Виж на картата");
        apiButton.setBounds(bx1,by,bwidth,bheight);
        apiButton.setBackground(new Color(139,0,139));
        apiButton.setForeground(Color.WHITE);
        apiButton.setFocusable(false);
        apiButton.addActionListener(this);


        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,500,650);
        layeredPane.setVisible(true);
        layeredPane.add(name, Integer.valueOf(0));
        layeredPane.add(city, Integer.valueOf(1));
        layeredPane.add(neighborhood, Integer.valueOf(2));
        layeredPane.add(streetName, Integer.valueOf(3));
        layeredPane.add(numberOfStreet, Integer.valueOf(4));
        layeredPane.add(floor, Integer.valueOf(5));
        layeredPane.add(appNumber, Integer.valueOf(6));
        layeredPane.add(type, Integer.valueOf(7));
        layeredPane.add(m2, Integer.valueOf(8));
        layeredPane.add(price, Integer.valueOf(9));
        layeredPane.add(nameData, Integer.valueOf(10));
        layeredPane.add(cityData, Integer.valueOf(11));
        layeredPane.add(neighborhoodData, Integer.valueOf(12));
        layeredPane.add(streetNameData, Integer.valueOf(13));
        layeredPane.add(numberOfStreetData, Integer.valueOf(14));
        layeredPane.add(floorData, Integer.valueOf(15));
        layeredPane.add(appNumberData, Integer.valueOf(16));
        layeredPane.add(typeData, Integer.valueOf(17));
        layeredPane.add(m2Data, Integer.valueOf(18));
        layeredPane.add(priceData, Integer.valueOf(19));
        layeredPane.add(phoneNumber, Integer.valueOf(20));
        layeredPane.add(phoneNumberData, Integer.valueOf(21));
        layeredPane.add(backButton, Integer.valueOf(22));
        layeredPane.add(apiButton, Integer.valueOf(23));
        layeredPane.add(listingReq, Integer.valueOf(24));

        this.setTitle("Детайли на обява");
        this.setSize(500,650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(layeredPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backButton) {
            Database.setCurrentListingId(0);
            this.dispose();
        }

        if (e.getSource()==apiButton) {
            //API Jframe
        }
    }
}
 */
package com.RustyRents.RustyRents.Listings;

import com.RustyRents.RustyRents.Admin.ListingsAdmin;
import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLOutput;

@Component
public class ListingDetails extends JFrame implements ActionListener {

    private static final Logger logger = LogManager.getLogger(ListingsAdmin.class.getName());
    private final FrameNavigator frameNavigator;
    private int currentPhoto = 0;
    private String[] imagePath;

    private ResultSet dbImages, rs;

    JLabel titleLabel, cityLabel, listingTypeLabel, priceLabel, neighbourhoodLabel, streetLabel, streetNumberLabel,
            floorLabel, roomNumberLabel, qSizeLabel, phoneNumberLabel, imageLabel1, imageLabel2, imageLabel3, imageLabel4,
            imageLabel5, imageLabel6, imageLabel7, imageLabel8, imageLabel9, titleLabelOutput, cityLabelOutput, listingTypeLabelOutput,
            priceLabelOutput, neighbourhoodLabelOutput, streetLabelOutput, streetNumberLabelOutput, floorLabelOutput, roomNumberLabelOutput,
            qSizeLabelOutput, phoneNumberLabelOutput;

    JLabel[] imageLabels = new JLabel[9];

    JTextField titleInput, cityInput, listingTypeInput, priceInput, neighbourhoodInput, streetInput, streetNumberInput,
            floorInput, roomNumberInput, qSizeInput, phoneNumberInput;

    ImageIcon[] images;
    ImageIcon defaultIcon;
    Image imageScale;

    File selectedFile;

    JButton backButton, secondBackButton, nextButton, nextImage, previousImage;

    JFileChooser fileChooser;

    FileNameExtensionFilter fileNameExtensionFilter;

    JLayeredPane firstPanel, secondPanel;

    JPanel  subPanel1_1, subPanel2_1, subPanel3_1, subPanel4_1, subPanel5_1,
            subPanel6_1, subPanel7_1, subPanel8_1, subPanel9_1, subPanel10_1, subPanel11_1, subPanel12_1,
            subPanel1_2, subPanel2_2,subPanel3_2, subPanel4_2, subPanel5_2, subPanel6_2, subPanel7_2, subPanel8_2,
            subPanel9_2, subPanel10_2, subPanel11_2, subPanel12_2, subPanel1_3, subPanel2_3, subPanel3_3, subPanel4_3,
            subPanel5_3, subPanel6_3, subPanel7_3, subPanel8_3, subPanel9_3, subPanel10_3, subPanel11_3, subPanel12_3,
            ImagesSubPanel, buttonsSubPanel;


    public ListingDetails(FrameNavigator frameNavigator){
        this.frameNavigator = frameNavigator;

        innitSubPanels();
        innitPanels();

        this.setTitle("Детайли на обява");
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

    private void innitPanels(){

        firstPanel = new JLayeredPane();
        firstPanel.setPreferredSize(new Dimension(350,550));
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Title:");
        subPanel1_1.add(titleLabel);

        titleLabelOutput = new JLabel();
        subPanel1_1.add(titleLabelOutput);

        cityLabel = new JLabel("City:");
        subPanel2_1.add(cityLabel);

        cityLabelOutput = new JLabel();
        subPanel2_1.add(cityLabelOutput);

        listingTypeLabel = new JLabel("Listing Type:");
        subPanel3_1.add(listingTypeLabel);

        listingTypeLabelOutput = new JLabel();
        subPanel3_1.add(listingTypeLabelOutput);

        priceLabel = new JLabel("Price:");
        subPanel4_1.add(priceLabel);

        priceLabelOutput = new JLabel();
        subPanel4_1.add(priceLabelOutput);

        neighbourhoodLabel = new JLabel("Neighbourhood:");
        subPanel5_1.add(neighbourhoodLabel);

        neighbourhoodLabelOutput = new JLabel();
        subPanel5_1.add(neighbourhoodLabelOutput);

        streetLabel = new JLabel("Street:");
        subPanel6_1.add(streetLabel);

        streetLabelOutput = new JLabel();
        subPanel6_1.add(streetLabelOutput);

        streetNumberLabel = new JLabel("Street Number:");
        subPanel7_1.add(streetNumberLabel);

        streetNumberLabelOutput = new JLabel();
        subPanel7_1.add(streetNumberLabelOutput);

        floorLabel = new JLabel("Floor: ");
        subPanel8_1.add(floorLabel);

        floorLabelOutput = new JLabel();
        subPanel8_1.add(floorLabelOutput);

        roomNumberLabel = new JLabel("Room Number: ");
        subPanel9_1.add(roomNumberLabel);

        roomNumberLabelOutput = new JLabel();
        subPanel9_1.add(roomNumberLabelOutput);

        qSizeLabel = new JLabel("Q Size: ");
        subPanel10_1.add(qSizeLabel);

        qSizeLabelOutput = new JLabel();
        subPanel10_1.add(qSizeLabelOutput);

        phoneNumberLabel = new JLabel("Phone Number: ");
        subPanel11_1.add(phoneNumberLabel);

        phoneNumberLabelOutput = new JLabel();
        subPanel11_1.add(phoneNumberLabelOutput);

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

        secondPanel = new JLayeredPane();
        secondPanel.setPreferredSize(new Dimension(550,550));
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));

        ImagesSubPanel = new JPanel();
       // ImagesSubPanel.setLayout(new GridLayout(3, 3,0,0));

        images = new ImageIcon[9];

        imagePath = new String[9];

        defaultIcon = new ImageIcon("defaulticon.png");
        imageScale = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        defaultIcon = new ImageIcon(imageScale);


        imageLabel1 = new JLabel();
        ImagesSubPanel.add(imageLabel1);
        secondPanel.add(ImagesSubPanel);



        nextImage = new JButton("Next image");
        nextImage.addActionListener(this);
        nextImage.setBackground(new Color(139,0,139));
        nextImage.setForeground(Color.white);

        previousImage = new JButton("Previous Image");
        previousImage.addActionListener(this);
        previousImage.setBackground(new Color(139,0,139));
        previousImage.setForeground(Color.white);

        secondBackButton = new JButton("Back");
        secondBackButton.addActionListener(this);
        secondBackButton.setBackground(new Color(139,0,139));
        secondBackButton.setForeground(Color.white);

        buttonsSubPanel = new JPanel();
        buttonsSubPanel.add(secondBackButton);
        buttonsSubPanel.add(previousImage);
        buttonsSubPanel.add(nextImage);

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

    public void refreshUIData() {
        try {
            rs = Database.getAllProperties(Database.getCurrentListingId());

            rs.next();
            
            titleLabelOutput.setText(rs.getString(1));
            cityLabelOutput.setText(rs.getString(2));
            listingTypeLabelOutput.setText(rs.getString(3));
            priceLabelOutput.setText(rs.getString(4));
            neighbourhoodLabelOutput.setText(rs.getString(5));
            streetLabelOutput.setText(rs.getString(6));
            streetNumberLabelOutput.setText(rs.getString(7));
            floorLabelOutput.setText(rs.getString(8));
            roomNumberLabelOutput.setText(rs.getString(9));
            qSizeLabelOutput.setText(rs.getString(10));
            phoneNumberLabelOutput.setText(rs.getString(11));

            rs.close();

        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }

        try {
            dbImages = Database.getListingImage();
            for (int i = 0; i < imagePath.length; i++) {
                if (dbImages.next()) {
                    imagePath[i] = Database.imagePathDBOutput(dbImages.getString(1));
                }
            }
            for (int i = 0; i < images.length; i++) {
                if (imagePath[i] != null) {
                    images[i] = new ImageIcon(imagePath[i]);
                    imageScale = images[i].getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    images[i] = new ImageIcon(imageScale);
                }
            }

            imageLabel1.setIcon(images[0]);
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }

    }

    private int getPhotosCount() {
        int result = 0;

        for (int i = 0; i < images.length; i++) {
            if (images[i] != null) {
                result++;
            }
        }

        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {
            frameNavigator.showFrame(ViewListings.class);
           // clearTextFields();
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

        else if (e.getSource() == nextImage) {
            if (currentPhoto < getPhotosCount()) {
                if (images[currentPhoto] == null) {
                    currentPhoto = 0;
                    imageLabel1.setIcon(images[currentPhoto]);
                }
                else {
                    if (images[currentPhoto + 1] == null) {
                        currentPhoto = 0;
                    }
                    else {
                        currentPhoto++;
                    }

                    imageLabel1.setIcon(images[currentPhoto]);
                }
            }
            else {
                currentPhoto = 0;
                imageLabel1.setIcon(images[currentPhoto]);
            }
            this.revalidate();
            this.repaint();
        }

        else if (e.getSource() == previousImage) {

                if (currentPhoto > 0 && currentPhoto < getPhotosCount()) {
                    if (images[currentPhoto] == null) {
                        currentPhoto = getPhotosCount() - 1;
                        imageLabel1.setIcon(images[currentPhoto]);
                    }
                    else {
                        if (images[currentPhoto - 1] == null) {
                            currentPhoto = getPhotosCount() - 1;
                        }
                        else {
                            currentPhoto--;
                        }

                        imageLabel1.setIcon(images[currentPhoto]);
                    }
                }
                else if (currentPhoto == 0){
                    currentPhoto = getPhotosCount() - 1;
                    imageLabel1.setIcon(images[currentPhoto]);
                }
                this.revalidate();
                this.repaint();
        }
    }
}