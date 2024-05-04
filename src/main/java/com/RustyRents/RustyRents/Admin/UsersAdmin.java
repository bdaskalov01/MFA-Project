package com.RustyRents.RustyRents.Admin;


import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.Listings.AddListing;
import com.RustyRents.RustyRents.Listings.ListingDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

@Component
public class UsersAdmin extends JFrame implements ActionListener {

    FrameNavigator frameNavigator;

    private static final Logger logger = LogManager.getLogger(UsersAdmin.class.getName());
    private static final long serialVersionUID = 1L;
    JLayeredPane layeredPane;
    JTable table;
    Object[] columns, row;
    DefaultTableModel model;
    JScrollPane pane;
    JButton backButton, editPropertyButton, deletePropertyButton;

    // Declare top row text fields
    private JTextField tfListingNameFilter;
    private JTextField tfQuarterFilter;
    private JTextField tfPropertyTypeFilter;
    private JTextField tfMinAreaFilter;
    private JTextField tfMaxPriceFilter;

    // Declare top row labels
    private JLabel lblListingNameFilter;
    private JLabel lblCityNameFilter;
    private JLabel lblPropertyTypeFilter;
    private JLabel lblMinAreaFilter;
    private JLabel lblMaxPriceFilter;

    private final int LABELS_FONT_SIZE = 15;
    private final int LABELS_POSITION_Y = 40;
    private final int LABELS_WIDTH = 100;
    private final int LABELS_HEIGHT = 50;

    public UsersAdmin(FrameNavigator frameNavigator) {
        this.frameNavigator = frameNavigator;

        setAutoRequestFocus(false);
        ImageIcon backIcon = new ImageIcon("BackIcon.png");
        ImageIcon appIcon = new ImageIcon("RustyRentsIcon.png");

        //TODO replace column objects with DB values
        columns = new Object[] {"Username", "Password", "Email", "Secret Question", "Hardware Token"};


        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        //TODO Connect DB with table
        table = new JTable();
        table.setModel(model);
        //table.setBackground(Color.MAGENTA);
        table.setForeground(Color.black);
        table.setSelectionBackground(Color.MAGENTA);
        table.setGridColor(Color.red);
        table.setSelectionForeground(Color.white);
        table.setFont(new Font("Tahoma", Font.PLAIN, 17));
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() > -1) {
                    try {
                        int selectedListingId = Database.getUserIdByUsername((table.getValueAt(table.getSelectedRow(), 0).toString()));
                        Database.setSelectedUserId(selectedListingId);
                    }
                    catch(Exception e) {
                        logger.fatal(e.getMessage());;
                    }
                }
            }
        });

        pane = new JScrollPane(table);
        pane.setBounds(18, 114, 705, 262);
        pane.setForeground(Color.RED);
        pane.setBackground(Color.WHITE);

        row = new Object[5];

        // Listing name label
        lblListingNameFilter = new JLabel();
        lblListingNameFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblListingNameFilter.setForeground(Color.BLACK);
        lblListingNameFilter.setBounds(54, LABELS_POSITION_Y, 250, LABELS_HEIGHT);
        getContentPane().add(lblListingNameFilter);

        // Listing name text field
        /*
        tfListingNameFilter = new JTextField();
        tfListingNameFilter.setBounds(44, 79, 200, 19);
        getContentPane().add(tfListingNameFilter);
        tfListingNameFilter.setColumns(10);
         */

        // City name filter label
        lblCityNameFilter = new JLabel();
        lblCityNameFilter.setForeground(Color.BLACK);
        lblCityNameFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblCityNameFilter.setBounds(260, LABELS_POSITION_Y, LABELS_WIDTH, LABELS_HEIGHT);
        getContentPane().add(lblCityNameFilter);


        // Property type filter label
        lblPropertyTypeFilter = new JLabel();
        lblPropertyTypeFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblPropertyTypeFilter.setForeground(Color.BLACK);
        lblPropertyTypeFilter.setBounds(410, LABELS_POSITION_Y, LABELS_WIDTH, LABELS_HEIGHT);
        getContentPane().add(lblPropertyTypeFilter);


        // Max price filter label
        lblMaxPriceFilter = new JLabel();
        lblMaxPriceFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblMaxPriceFilter.setForeground(Color.BLACK);
        lblMaxPriceFilter.setBounds(530, LABELS_POSITION_Y, LABELS_WIDTH, LABELS_HEIGHT);
        getContentPane().add(lblMaxPriceFilter);
        getContentPane().setLayout(null);

        // Max price filter text field
        /*
        tfMaxPriceFilter = new JTextField();
        tfMaxPriceFilter.setBounds(530, 79, 70, 19);
        getContentPane().add(tfMaxPriceFilter);
        tfMaxPriceFilter.setColumns(10);

         */


        // Back button
        backButton = new JButton(backIcon);
        backButton.setBounds(5,5,50,50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        getContentPane().add(backButton);

        // Delete user button
        deletePropertyButton = new JButton("Delete user");
        deletePropertyButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deletePropertyButton.setAlignmentY(java.awt.Component.BOTTOM_ALIGNMENT);
        deletePropertyButton.setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
        deletePropertyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        deletePropertyButton.setBounds(527, 395, 196, 51);
        deletePropertyButton.setBackground(new Color(139,0,139));
        deletePropertyButton.setForeground(Color.WHITE);
        deletePropertyButton.addActionListener(this);
        getContentPane().add(deletePropertyButton);


        // Edit user button
        editPropertyButton = new JButton("Edit user");
        editPropertyButton.setHorizontalTextPosition(SwingConstants.CENTER);
        editPropertyButton.setAlignmentY(java.awt.Component.BOTTOM_ALIGNMENT);
        editPropertyButton.setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
        editPropertyButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        editPropertyButton.setBounds(315, 395, 196, 51);
        editPropertyButton.setBackground(new Color(139,0,139));
        editPropertyButton.setForeground(Color.WHITE);
        editPropertyButton.addActionListener(this);
        getContentPane().add(editPropertyButton);


//        addPropertyButton
        this.setTitle("Users Admin Panel");
        this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(248,240,255));
        this.getContentPane().setForeground(Color.WHITE);
        this.setResizable(false);
        this.setBounds(100,100,757,500);
        this.getContentPane().add(pane);
        this.setLocationRelativeTo(null);
    }

    public void refreshUIData() {
        // Add properties from database
        ResultSet rs = Database.getUserDetails();
        try {
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);

                model.addRow(row);
            }
        } catch (Exception e) {logger.fatal(e.getMessage());}
    }

    public void onCloseInnit() {
        model.setRowCount(0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frameNavigator.showFrame(AdminPanel.class);
            Database.setSelectedUserId(-1);
        }

        else if (e.getSource() == editPropertyButton) {
            frameNavigator.showFrame(AddListing.class);
        }

        else if (e.getSource() == deletePropertyButton) {
            Database.deleteUser(Database.getSelectedUserId());
            onCloseInnit();
            refreshUIData();
        }
    }
}
