package com.RustyRents.RustyRents.Listings;

import com.RustyRents.RustyRents.Database.Database;
import com.RustyRents.RustyRents.FrameNavigator.FrameNavigator;
import com.RustyRents.RustyRents.MainMenu.MainMenu;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.sql.ResultSet;


@org.springframework.stereotype.Component
public class ViewListings extends JFrame implements ActionListener {


    private static final long serialVersionUID = 1L;
    JLayeredPane layeredPane;
    JTable table;
    Object[] columns, row;
    DefaultTableModel model;
    JScrollPane pane;
    JButton backButton, btnViewProperty;

    // labels
    private JLabel lblListingNameFilter;
    private JLabel lblQuarterFilter;
    private JLabel lblCityNameFilter;
    private JLabel lblPropertyTypeFilter;
    private JLabel lblMaxPriceFilter;

    // combo boxes
    private JComboBox<String> cbCityNameFilter;
    private JComboBox<String> cbPropertyTypeFilter;

    // text fields
    private JTextField tfListingNameFilter;
    private JTextField tfMaxPriceFilter;

    private final int LABELS_FONT_SIZE = 15;
    private final int LABELS_POSITION_Y = 40;
    private final int LABELS_WIDTH = 100;
    private final int LABELS_HEIGHT = 50;

    private final FrameNavigator framenavigator;
    public ViewListings(FrameNavigator framenavigator) {
        this.framenavigator = framenavigator;
        setAutoRequestFocus(false);
        ImageIcon backIcon = new ImageIcon("BackIcon.png");
        ImageIcon appIcon = new ImageIcon("RustyRentsIcon.png");

        //TODO replace column objects with DB values
        columns = new Object[] {"Номер на обява", "Име на обява", "Град", "Вид имот", "Цена"};


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
                        int selectedListingId = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                        Database.setCurrentListingId(selectedListingId);
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        pane = new JScrollPane(table);
        pane.setBounds(18, 114, 705, 262);
        pane.setForeground(Color.RED);
        pane.setBackground(Color.WHITE);

        row = new Object[5];

        //
        // Top row of window
        //

        // Listing name label
        lblListingNameFilter = new JLabel("Име на обявата");
        lblListingNameFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblListingNameFilter.setForeground(Color.BLACK);
        lblListingNameFilter.setBounds(54, LABELS_POSITION_Y, 250, LABELS_HEIGHT);
        getContentPane().add(lblListingNameFilter);

        // Listing name text field
        tfListingNameFilter = new JTextField();
        tfListingNameFilter.setBounds(44, 79, 200, 19);
        getContentPane().add(tfListingNameFilter);
        tfListingNameFilter.setColumns(10);

        // City name filter label
        lblCityNameFilter = new JLabel("Град");
        lblCityNameFilter.setForeground(Color.BLACK);
        lblCityNameFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblCityNameFilter.setBounds(260, LABELS_POSITION_Y, LABELS_WIDTH, LABELS_HEIGHT);
        getContentPane().add(lblCityNameFilter);

        // City name filter combo box
        cbCityNameFilter = new JComboBox<String>();
        cbCityNameFilter.setBounds(255, 79, 130, 19);
        cbCityNameFilter.insertItemAt("", 0);
        ResultSet rsCity = Database.getCity();
        try {
            while (rsCity.next()) {
                cbCityNameFilter.addItem(rsCity.getString(1));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        cbCityNameFilter.setSelectedIndex(-1);
        getContentPane().add(cbCityNameFilter);

        // Property type filter label
        lblPropertyTypeFilter = new JLabel("Вид имот");
        lblPropertyTypeFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblPropertyTypeFilter.setForeground(Color.BLACK);
        lblPropertyTypeFilter.setBounds(410, LABELS_POSITION_Y, LABELS_WIDTH, LABELS_HEIGHT);
        getContentPane().add(lblPropertyTypeFilter);

        // Property type filter combo box
        cbPropertyTypeFilter = new JComboBox<String>();
        cbPropertyTypeFilter.setBounds(403, 79, 115, 19);
        cbPropertyTypeFilter.insertItemAt("", 0);
        ResultSet rsType = Database.getPType();
        try {
            while (rsType.next()) {
                cbPropertyTypeFilter.addItem(rsType.getString(1));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        cbPropertyTypeFilter.setSelectedIndex(-1);
        getContentPane().add(cbPropertyTypeFilter);

        // Max price filter label
        lblMaxPriceFilter = new JLabel("Max. цена");
        lblMaxPriceFilter.setFont(new Font("Tahoma", Font.PLAIN, LABELS_FONT_SIZE));
        lblMaxPriceFilter.setForeground(Color.BLACK);
        lblMaxPriceFilter.setBounds(530, LABELS_POSITION_Y, LABELS_WIDTH, LABELS_HEIGHT);
        getContentPane().add(lblMaxPriceFilter);
        getContentPane().setLayout(null);

        // Max price filter text field
        tfMaxPriceFilter = new JTextField();
        tfMaxPriceFilter.setBounds(530, 79, 70, 19);
        getContentPane().add(tfMaxPriceFilter);
        tfMaxPriceFilter.setColumns(10);


        // Search button
        JButton btnFilterResults = new JButton("Търси");
        btnFilterResults.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnFilterResults.setBounds(616, 72, 107, 25);
        btnFilterResults.setBackground(new Color(139,0,139));
        btnFilterResults.setForeground(Color.WHITE);
        btnFilterResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);

                String titleFilter = tfListingNameFilter.getText();
                String cityFilter = cbCityNameFilter.getSelectedItem().toString();
                String typeFilter = cbPropertyTypeFilter.getSelectedItem().toString();
                String priceFilter = tfMaxPriceFilter.getText().toString();
                int price = Integer.parseInt(priceFilter);

                System.out.println(typeFilter);

                ResultSet rs = Database.getFilteredProperties(titleFilter, cityFilter, typeFilter, price);

                try {
                    while (rs.next()) {
                        row[0] = rs.getInt(1);
                        row[1] = rs.getString(2);
                        row[2] = rs.getString(3);
                        row[3] = rs.getString(4);
                        row[4] = rs.getInt(5);

                        model.addRow(row);
                    }
                } catch (Exception exc) {System.out.println(exc);}

            }
        });

        getContentPane().add(btnFilterResults);

        // Back button
        backButton = new JButton(backIcon);
        backButton.setBounds(5,5,50,50);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        getContentPane().add(backButton);

        // View property button
        btnViewProperty = new JButton("Преглед на имот");
        btnViewProperty.setHorizontalTextPosition(SwingConstants.CENTER);
        btnViewProperty.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        btnViewProperty.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnViewProperty.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnViewProperty.setBounds(527, 395, 196, 51);
        btnViewProperty.setBackground(new Color(139,0,139));
        btnViewProperty.setForeground(Color.WHITE);
        btnViewProperty.addActionListener(this);
        getContentPane().add(btnViewProperty);
        /* btnViewProperty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
         */

        this.setTitle("Преглед на обяви˜");
        this.setIconImage(appIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(248,240,255));
        this.getContentPane().setForeground(Color.WHITE);
        this.setResizable(false);
        this.setBounds(100,100,757,500);
        this.getContentPane().add(pane);
        this.setLocationRelativeTo(null);

        // TODO this.add(layeredPane);
    }

    public void refreshUIData() {
        // Add properties from database
        ResultSet rs = Database.getProperties();
        try {
            while (rs.next()) {
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);

                model.addRow(row);
            }
        } catch (Exception e) {System.out.println(e);}

    }

    public void onCloseInnit() {
        model.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== backButton) {
            framenavigator.showFrame(MainMenu.class);
        }

        else if (e.getSource()== btnViewProperty) {
            framenavigator.showFrame(ListingDetails.class);
        }

    }
}
