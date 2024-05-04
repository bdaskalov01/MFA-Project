package com.RustyRents.RustyRents.Database;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.RandomStringUtils;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class Database {

    private static int currentUserId = 0;

    private static int currentSTUserID = 0;

    private static int selectedUserId = 0;
    private static int currentListingId = 0;

    private static String currentGeneratedCode;
    private static Statement statement = null;
    private static Connection connection = null;

    private static String values, query;

    private static String[] queryArray;

    private static Logger logger = LogManager.getLogger(Database.class.getName());

    private Database() {}


    public static void establishConnection() {
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentsproject", "root", "admin");
            statement = connection.createStatement();

        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    public static void closeConnection() {
        try {
            connection.close();
            statement.close();
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }


    public static void storeNewLogin(int userId, String password) {

        //String subquery = "(SELECT User_Id FROM Users WHERE User_Name = '" + username + "')";

        values = "'" + userId + "' , '" + password + "'";
        query = "INSERT INTO No_Hackers_Pls VALUES (" + values + ")";

        logger.info("storeNewLogin : Query to insert new login given userId and password.");
        logger.debug(query);

        try {

            statement.execute(query);

        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    private static void storeNewAnswerQuestion(int userId, String question, String answer) {
        values = "'" + userId + "' , '" + question + "' , '" + answer + "'";
        query = "INSERT INTO secretquestion VALUES (" + values + ")";

        logger.info("storeNewAnswerQuestion : Query to insert secret question given userid, question and answer.");
        logger.debug(query);

        try {

            statement.execute("INSERT INTO secretquestion VALUES (" + "'" + userId + "' , '" + question + "' , '" + answer + "'" + ")");

        } catch(Exception e) {logger.fatal(e.getMessage());}
    }

    public static void addNewUser(String username, String password, String email, String question, String answer) {

        int newUserId;
        String test;

        try {
            if (!checkIfUsernameIsTaken(username) && !checkIfEmailIsTaken(email)) {
                values = "NULL, '" + username + "', '" + email + "'";
                query = "INSERT INTO users VALUES (" + values + ")";

                logger.info("addNewUser : Query to insert new user with username and email.");
                logger.debug(query);

                statement.execute(query);

                ResultSet rs = statement.executeQuery("SELECT MAX(user_id) FROM users");
                rs.next();
                newUserId = rs.getInt(1);
                storeNewLogin(newUserId, password);
                question = question.replace("'", "");
                answer = answer.replace("'", "");
                storeNewAnswerQuestion(newUserId, question, answer);

            }
        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    public static boolean isValidLogin(String username, String password) {

        query = "SELECT user_id FROM users WHERE username = '" + username + "'";

        logger.info("isValidLogin: Query for selecting the id of given username.");
        logger.debug(query);

        try {
            int userId = -1;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                userId = rs.getInt(1);
            }
            else {
                logger.info("No such username exists.");
                return false;
            }

            query = "SELECT password FROM No_Hackers_Pls WHERE user_id = " + userId;

            logger.info("isValidLogin: Query to select password of given username.");
            logger.debug(query);

            rs = statement.executeQuery(query);
            rs.next();
            String fetchedPass = rs.getString(1);
            if (!password.equals(fetchedPass)) {
                logger.info("Invalid password for this user.");
                return false;
            }

            return true;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }

    public static int getUserId(String username) {

        query = "SELECT user_id FROM users WHERE username = '" + username + "'";

        logger.info("getUserId : Query to select user ID from given username.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getInt(1);

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return -1;

    }

    public static ResultSet getUserDetails() {

        query = "SELECT A.username, A.email, B.password, C.question, C.answer, D.generatedcode\n" +
                "FROM users as A\n" +
                "LEFT JOIN no_hackers_pls as B\n" +
                "on A.user_id = B.user_id\n" +
                "LEFT JOIN secretquestion as C\n" +
                "on A.user_id = C.user_id\n" +
                "LEFT JOIN softwaretokencodes as D\n" +
                "on A.user_id = D.user_id";

        logger.info("getUserDetails: Query to select all users and get their credentials and info.");
        logger.debug(query);

        try {
            ResultSet rs = statement.executeQuery(query);
            return rs;
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }
        return null;
    }

    public static int getUserIdByUsername(String username) {
        query = "SELECT * FROM users WHERE username = '" + username + "'";

        logger.info("checkEmailMatch : Query to select user ID given username.");
        logger.debug(query);

        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return 0;
    }

    public static boolean checkEmailMatch(String email) {

        query = "SELECT email FROM users WHERE user_id = " + currentUserId;

        logger.info("checkEmailMatch : Query to select user email given user ID.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(email))
                return true;

            return false;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }

    public static void changeEmail(String newEmail) {

        query = "UPDATE users SET email = '" + newEmail + "' WHERE user_id = " + currentUserId;

        logger.info("changeEmail : Query to update email address of user with given ID.");
        logger.debug(query);

        try {

            statement.execute(query);

        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    public static boolean checkIfUsernameIsTaken(String username) {
        query = "Select username FROM users WHERE username = '" + username + "'";
        logger.info("checkIfUsernameIsTaken : Query to select username given username.");
        logger.debug(query);
        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if (rs.getString(1).equalsIgnoreCase(username)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
        return false;
    }

    public static boolean checkIfEmailIsTaken(String email) {
        query = "Select email FROM users WHERE email = '" + email + "'";
        logger.info("checkIfUsernameIsTaken : Query to select email given email.");
        logger.debug(query);
        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if (rs.getString(1).equalsIgnoreCase(email)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
        return false;
    }
    public static boolean checkPasswordMatch(String password) {

        query = "SELECT password FROM no_hackers_pls WHERE user_id = " + currentUserId;

        logger.info("checkPasswordMatch : Query to select user passcode given user ID.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(password))
                return true;

            return false;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }

    public static boolean checkIfEmailCodeIsAlreadyGenerated() {
        query = "SELECT user_id FROM emailcodes WHERE user_id = " + currentUserId;

        logger.info("checkIfEmailCodeIsAlreadyGenerated: Query to select email code given user ID.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                logger.info("checkIfEmailCodeIsAlreadyGenerated: Email code is already generated.");
                return true;
            }
            logger.info("checkIfEmailCodeIsAlreadyGenerated: Email code isn't generated yet.");
            return false;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }

    public static boolean checkEmailCodeMatch(String emailcode) {

        query = "SELECT generatedcode FROM emailcodes WHERE user_id = " + currentUserId;

        logger.info("checkEmailCodeMatch: Query to select email code given user ID.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(emailcode)) {
                logger.info("Database.checkEmailCodeMatch: Email code matches.");
                return true;
            }

            logger.info("checkEmailCodeMatch: Email code doesn't match.");
            return false;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }
    public static void changePassword(String newPassword) {

        query = "UPDATE no_hackers_pls SET password = '" + newPassword + "' WHERE user_id = " + currentUserId;

        logger.info("changePassword : Query to update passcode of user with given ID:");
        logger.debug(query);

        try {

            statement.execute(query);


        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    public static void generateEmailCode() {

        logger.info("generateEmailCode: Query to insert an email code.");
        logger.debug(query);

        try {
            if (checkIfEmailCodeIsAlreadyGenerated()) {
                logger.info("generateEmailCode: Email code is already generated.");
            }
            else if (!checkIfEmailCodeIsAlreadyGenerated()) {
                currentGeneratedCode = RandomStringUtils.randomAlphanumeric(10);
                logger.info("generateEmailcode: Generating email code.");
                statement.execute("INSERT INTO emailcodes VALUES ('" + currentUserId + "' , '" + currentGeneratedCode + "')");
            }

        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    public static void removeEmailCode() {
        query = "DELETE FROM emailcodes WHERE user_id = " + currentUserId;

        try {
            logger.info("removeEmailCode: Query to delete email codes given user ID.");
            logger.debug(query);
            statement.execute(query);
        }
        catch(Exception e ) {
            logger.fatal(e.getMessage());
        }
    }

    public static void removeAllEmailCodes() {
        query = "DELETE FROM emailcodes";
        try {
            logger.info("removeAllEmailCodes: Query to delete all email codes.");
            logger.debug(query);
            statement.execute(query);
        }
        catch(Exception e ) {
            logger.fatal(e.getMessage());
        }
    }
    public static void generateSoftwareCode(int userId) {
        currentGeneratedCode = RandomStringUtils.randomAlphanumeric(10);

        values = "'" + userId + "' , '" + currentGeneratedCode + "'";
        query = "INSERT INTO softwaretokencodes VALUES (" + values + ")";

        logger.info("generateSoftwareCode: Query to insert software code given user ID.");
        logger.debug(query);

        try {
            if (checkIfSoftwareCodeIsAlreadyGenerated()) {
                logger.info("generateSoftwareCode: Software code is already generated.");
            }
            else if (!checkIfSoftwareCodeIsAlreadyGenerated()) {
                logger.info("generateSoftwareCode: Generating software code.");
                statement.execute("INSERT INTO softwaretokencodes VALUES ('" + userId + "' , '" + currentGeneratedCode + "')");
            }

        } catch(Exception e) {logger.fatal(e.getMessage());}

    }

    public static String getSoftwareCode(int userId) {
        query = "SELECT generatedcode FROM softwaretokencodes where user_id = " + userId;
        logger.info("getSoftwareCode: Query to get software code given user ID.");
        logger.debug(query);

        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String generatedCode = rs.getString(1);
            return generatedCode;
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static void removeSoftwareCode(int userId) {
        query = "DELETE FROM softwaretokencodes WHERE user_id = " + userId;

        try {
            logger.info("removeSoftwareCode: Query to delete software codes given user ID.");
            logger.debug(query);
            statement.execute(query);
        }
        catch(Exception e ) {
            logger.fatal(e.getMessage());
        }
    }

    public static void removeAllSoftwareCodes() {
        query = "DELETE FROM softwaretokencodes";
        try {
            logger.info("removeAllSoftwareCodes: Query to delete all software tokens.");
            logger.debug(query);
            statement.execute(query);
        }
        catch(Exception e ) {
            logger.fatal(e.getMessage());
        }
    }

    public static boolean checkIfSoftwareCodeIsAlreadyGenerated() {
        query = "SELECT user_id FROM softwaretokencodes WHERE user_id = " + currentUserId;

        logger.info("checkIfSoftwareCodeIsAlreadyGenerated: Query to select software code given user ID.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                logger.info("checkIfSoftwareCodeIsAlreadyGenerated: Software code is already generated.");
                return true;
            }
            logger.info("checkIfSoftwareCodeIsAlreadyGenerated: Software code isn't generated yet.");
            return false;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }

    public static boolean checkSoftwareCodeMatch(String softwaretokencode) {

        query = "SELECT generatedcode FROM softwaretokencodes WHERE user_id = " + currentUserId;

        logger.info("checkSoftwareCodeMatch: Query to select software code given user ID.");
        logger.debug(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(softwaretokencode)) {
                logger.info("checkSoftwareCodeMatch: Software code matches.");
                return true;
            }

            logger.info("checkSoftwareCodeMatch: Software code doesn't match.");
            return false;

        } catch(Exception e) {logger.fatal(e.getMessage());}

        return false;

    }
    public static void deleteUser(int userId) {
        //TODO add deleting data from DB after implementing future features

        boolean shouldUserBeDeleted = false;
            establishConnection();
            query = "DELETE FROM users WHERE user_id = " + userId;
            logger.info("deleteUser: Query to delete a user.");

            try {
                    logger.debug(query);
                    statement.execute(query);
            }
            catch (Exception e) {
                logger.fatal(e.getMessage());
            }
    }

    public static ResultSet getProperties() {

        logger.info("getProperties: Query to get specific columns from all rows from the listings table.");
        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM listings";
        query = Select + " " + From;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getAllProperties(int listingId) {

        logger.info("getAllProperties: Query to get all columns and rows from the listings  table.");
        String Select = "SELECT title, city, listing_type, price, neighbourhood, street, street_number, floor, room_number, q_size, phone_number";
        String From = "FROM listings";
        String Where = "WHERE listing_id = '" + listingId + "'";
        query = Select + " " + From + " " + Where;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getProperties(int userId) {

        logger.info("getProperties: Query to get specific columns and rows from the listings table.");
        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM listings";
        String Where = "WHERE user_id = '" + userId + "'";
        query = Select + " " + From + " " + Where;
        logger.debug(query);

        ResultSet rs = null;

        try {
            rs = statement.executeQuery(query);
            logger.debug(query);

        } catch (Exception e) {logger.fatal(e.getMessage());};

        return rs;

    }

    public static void deleteProperty(int listingId) {

        logger.info("deleteProperty: Query to delete specific row from the listings table");
        query = "DELETE FROM listings WHERE listing_id = '" + listingId + "'";

        try {
            statement.executeUpdate(query);
            logger.debug(query);

        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }


    public static ResultSet getFilteredProperties(String title, String city, String type, int price) {

        logger.info("getFilteredProperties: Query to get specific rows and columns from the listings table");
        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM Listings";
        String Where = "WHERE title = '" + title + "' AND city = '" + city + "' AND listing_type = '" + type + "' AND price <= " + price;

        query = Select + " " + From + " " + Where;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getFilteredProperties(String title, String city, String type, int price, int currentUserId) {

        logger.info("getFilteredProperties: Query to get specific rows and columns from the listings table.");
        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM Listings";
        String Where = "WHERE title = '" + title + "' AND city = '" + city + "' AND listing_type = '" + type + "' AND price <= " + price + "' AND user_id = '" + currentUserId;

        query = Select + " " + From + " " + Where;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getCity() {
        logger.info("getCity: Query to select specific column from all rows in the listings table.");
        String Select = "SELECT city";
        String From = "FROM Listings";
        query = Select + " " + From;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getCity(int userId) {
        logger.info("getCity: Query to select specific column and rows from the listings table.");
        String Select = "SELECT city";
        String From = "FROM Listings";
        String Where = "WHERE user_id = '" + userId + "'";
        query = Select + " " + From + " " + Where;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getPType() {

        logger.info("getPType: Query to select specific column from all rows in the listings table.");
        String Select = "SELECT listing_type";
        String From = "FROM Listings";
        query = Select + " " + From;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static ResultSet getPType(int userId) {

        logger.info("getPType: Query to select specific columns and rows from the listings table.");
        String Select = "SELECT listing_type";
        String From = "FROM Listings";
        String Where = "WHERE user_id = '" + userId + "'";
        query = Select + " " + From + " " + Where;
        logger.debug(query);

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {logger.fatal(e.getMessage());}

        return rs;

    }

    public static String getUsername() {
        logger.info("getUsername: Query to get the username column from the users table given the user id.");
        query = "SELECT username FROM users WHERE user_id = " + currentUserId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getUsername(int userId) {
        logger.info("getUsername: Query to get the username column from the users table given the user id.");
        query = "SELECT username FROM users WHERE user_id = " + userId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getEmail() {
        logger.info("getEmail: Query to get the email column from the users table given the user id.");
        query = "SELECT email FROM users WHERE user_id = " + currentUserId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getPropertyTitle() {
        logger.info("getPropertyTitle: Query to get the title column from the listings table given listing_id.");
        query = "SELECT title FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }
    public static String getPropertyCity() {
        logger.info("getPropertyCity: Query to get the city column from the listings table given listing id.");
        query = "SELECT city FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }
    public static String getPropertyType() {
        logger.info("getPropertyType: Query to get the listing_type column from the listings table given listing id.");
        query = "SELECT listing_type FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }
    public static String getPropertyPrice() {
        logger.info("getPropertyPrice: Query to get the price column from the listings table given listing id.");
        query = "SELECT price FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getPropertyNeighbourhood() {
        logger.info("GetPropertyNeighbourhood: Query to get the neighbourhood column from the listings table given listing id.");
        query = "SELECT neighbourhood FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getPropertyStreet() {
        logger.info("getPropertyStreet: Query to get the street column from the listings table given listing id.");
        query = "SELECT street FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getPropertyStreetNumber() {
        logger.info("getPropertyStreetNumber: Query to get the street_number column from the listings table given listing id.");
        query = "SELECT street_number FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getPropertyFloor() {
        logger.info("getPropertyFloor: Query to get the floor column from the listings table given listing id.");
        query = "SELECT floor FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static String getPropertyRoomNumber() {
        logger.info("getPropertyRoomNumber: Query to get the street_number column from the listings table given listing id.");
        query = "SELECT street_number FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }
    public static String getPropertyQSize() {
        logger.info("getPropertyQSize: Query to get the q_size column from the listings table given listing id.");
        query = "SELECT q_size FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }
    public static String getPropertyPhoneNumber() {
        logger.info("getPropertyPhoneNumber: Query to get the phone_number column from the listings table given listing id.");
        query = "SELECT phone_number FROM listings WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static ResultSet getListingImage() {
        logger.info("getListingImage: Query to get the imagePath column from the listingimages table given listing id.");
        query = "SELECT imagePath FROM listingimages WHERE listing_id = " + currentListingId;
        logger.debug(query);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            return rs;
        }
        catch(Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static void insertNewListing(String title, String city, String listing_type, String price, String neighbourhood,
                                         String street, String streetNumber, String floor, String roomNumber, String qSize, String phoneNumber) {
        values = "NULL, '" + title + "' , '" + city + "' , '" + listing_type + "' , '" + price + "' , '" + neighbourhood + "' , '" + street + "' , '" + streetNumber + "' , '" + floor + "' , '" + roomNumber + "' , '" + qSize + "' , '" + phoneNumber + "' , '" + currentUserId + "'";
        query = "INSERT INTO listings VALUES (" + values + ")";

        try {
            logger.info("insertNewListing : Query to insert new property.");
            logger.debug(query);

            statement.execute(query);

            ResultSet rs = statement.executeQuery("SELECT MAX(listing_id) FROM listings");
            rs.next();
            currentListingId = rs.getInt(1);
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }

    public static void updateListing(String title, String city, String listing_type, String price, String neighbourhood,
                                        String street, String streetNumber, String floor, String roomNumber, String qSize, String phoneNumber) {
        values = "NULL, '" + title + "' , '" + city + "' , '" + listing_type + "' , '" + price + "' , '" + neighbourhood + "' , '" + street + "' , '" + streetNumber + "' , '" + floor + "' , '" + roomNumber + "' , '" + qSize + "' , '" + phoneNumber + "' , '" + currentUserId + "'";
        query = "UPDATE listings SET title = '" + title + "', city = '" + city + "', listing_type = '" + listing_type +
                "', price = " + price + ", neighbourhood = '" + neighbourhood + "', street = '" + street + "', street_number = " + streetNumber +
                ", floor = " + floor + ", room_number = " + roomNumber + ", q_size = " + qSize + ", phone_number = '" + phoneNumber +
                "' WHERE listing_id = " + currentListingId;


        try {
            logger.info("updateListing : Query to edit property.");
            logger.debug(query);

            statement.executeUpdate(query);
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }

    public static void addListingImages(String imagePath) {
        values = "'" + currentListingId + "', '" + imagePath + "'";
        query = "INSERT INTO listingimages VALUES (" + values + ")";

        try{
            logger.info("Database.addListingImages: Query to insert images into listingimages table.");
            logger.debug(query);

            statement.execute(query);
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }

    public static ResultSet getListingImages(String imagePath) {
        query = "SELECT imagePath FROM listingiamges WHERE listing_id = " + currentListingId;

        try{
            logger.info("Database.addListingImages: Query to insert images into listingimages table.");
            logger.debug(query);

            ResultSet rs = statement.executeQuery(query);
            return rs;
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static void removeListingImages() {
        query = "DELETE FROM listingimages WHERE listing_id = " + currentListingId;

        try{
            logger.info("Database.addListingImages: Query to delete images from the listingimages table.");
            logger.debug(query);

            statement.execute(query);
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }

    public static String injectionProtection(String input) {

        //Symbol whitelist
        input = input.replaceAll("[^a-zA-Z0-9!_.@#$%^&*]", " ");
        //Remove ' to avoid query errors.
        input = input.replace("'", "");
        //Remove possible SQL injection triggers
        input = input.replaceAll("union", "");
        input = input.replaceAll("select", "");
        input = input.replaceAll("drop", "");
        input = input.replaceAll("delete", "");
        input = input.replaceAll("--", "");

        return input;
    }

    public static int listingImagesCount() throws SQLException {
        query = "SELECT COUNT(imagePath) FROM listingimages WHERE listing_id = " + currentListingId;
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e) {
            logger.fatal(e.getMessage());
        }
        finally {
            rs.close();
        }

        return 0;
    }

    public static String imagePathDBInput(String input) {

        input = input.replace("\\" , "$");

        return input;
    }

    public static String imagePathDBOutput(String input) {

        input = input.replace("$", "\\");

        return input;
    }

    public static String getSecretQuestion() {
        logger.info("getSecretQuestion: Query to get the question column from the secretquestion table given user id.");
        query = "SELECT question FROM secretquestion WHERE user_id = '" + currentUserId + "'";
        logger.debug(query);

        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    public static boolean checkIfAnswerIsValid(String answer) {
        logger.info("checkIfAnswerIsValid: Query to get the answer column from the secretquestion table given user id.");
        query = "SELECT answer FROM secretquestion WHERE user_id = '" + currentUserId + "'";
        logger.debug(query);

        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if (answer.equals(rs.getString(1))) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            logger.fatal(e.getMessage());
        }

        return false;
    }


    public static void setCurrentUserId(int user) {
        currentUserId = user;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static int getCurrentListingId() {
        return currentListingId;
    }

    public static String getCurrentGeneratedCode() {
        return currentGeneratedCode;
    }

    public static int getSelectedUserId() {
        return selectedUserId;
    }

    public static void setSelectedUserId(int selectedUserId) {
        Database.selectedUserId = selectedUserId;
    }

    public static void setCurrentGeneratedCode(String currentGeneratedCode) {
        Database.currentGeneratedCode = currentGeneratedCode;
    }
    public static void setCurrentListingId(int currentListingId) {
        Database.currentListingId = currentListingId;
    }

    //SQL INJECTION EXAMPLES
    /*
    public static String getUsernameVulnerable() {
        query = "SELECT username FROM vulnerabletable WHERE user_id = " + currentUserId;
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static int isValidLoginVulnerable(String username, String password) {

        query = "SELECT * FROM vulnerabletable WHERE username = '" + username + "' AND password = '" + password + "'";

        System.out.println("Database.isValidLogin: Query for selecting the id of given username.");
        System.out.println(query);

        try {
            int userId = -1;
            ResultSet rs = statement.executeQuery(query);
            System.out.println(rs);
            if (rs.next()) {
                System.out.println("test");
                userId = rs.getInt(1);
                System.out.println(userId);
                return userId;
            }
            else {
                System.out.println("No such account exists.");
                return 0;
            }

        } catch(Exception e) {System.out.println(e);}

        return 0;

    }

     */

}
