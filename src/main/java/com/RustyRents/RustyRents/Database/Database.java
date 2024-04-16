package com.RustyRents.RustyRents.Database;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;

@Component
public class Database {

    private static int currentUserId = 0;

    private static int currentSTUserID = 0;
    private static int currentListingId = 0;

    private static String currentGeneratedCode;
    private static Statement statement = null;

    private static String values, query;

    private Database() {}


    public static void establishConnection() {
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentsproject", "root", "admin");
            statement = connection.createStatement();

        } catch(Exception e) {System.out.println(e);}

    }
    public static void storeNewLogin(int userId, String password) {

        //String subquery = "(SELECT User_Id FROM Users WHERE User_Name = '" + username + "')";

        values = "'" + userId + "' , '" + password + "'";
        query = "INSERT INTO No_Hackers_Pls VALUES (" + values + ")";

        System.out.println("Database.storeNewLogin : Query to insert new login given userId and password.");
        System.out.println(query);

        try {

            statement.execute(query);

        } catch(Exception e) {System.out.println(e);}

    }

    private static void storeNewAnswerQuestion(int userId, String question, String answer) {
        values = "'" + userId + "' , '" + question + "' , '" + answer + "'";
        query = "INSERT INTO secretquestion VALUES (" + values + ")";

        System.out.println("Database.storeNewAnswerQuestion : Query to insert secret question given userid, question and answer.");
        System.out.println(question instanceof String);
        System.out.println(answer instanceof String);
        System.out.println(query);

        try {

            statement.execute("INSERT INTO secretquestion VALUES (" + "'" + userId + "' , '" + question + "' , '" + answer + "'" + ")");

        } catch(Exception e) {System.out.println(e);}
    }

    public static void addNewUser(String username, String password, String email, String question, String answer) {

        int newUserId;
        String test;

        try {
            if (!checkIfUsernameIsTaken(username) && !checkIfEmailIsTaken(email)) {
                values = "NULL, '" + username + "', '" + email + "'";
                query = "INSERT INTO users VALUES (" + values + ")";

                System.out.println("Database.addNewUser : Query to insert new user with username and email.");
                System.out.println(query);

                statement.execute(query);

                ResultSet rs = statement.executeQuery("SELECT MAX(user_id) FROM users");
                rs.next();
                newUserId = rs.getInt(1);
                storeNewLogin(newUserId, password);
                question = question.replace("'", "");
                answer = answer.replace("'", "");
                storeNewAnswerQuestion(newUserId, question, answer);

            }
        } catch(Exception e) {System.out.println(e);}

    }

    public static boolean isValidLogin(String username, String password) {

        query = "SELECT user_id FROM users WHERE username = '" + username + "'";

        System.out.println("Database.isValidLogin: Query for selecting the id of given username.");
        System.out.println(query);

        try {
            int userId = -1;
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                userId = rs.getInt(1);
            }
            else {
                System.out.println("No such username exists.");
                return false;
            }

            query = "SELECT password FROM No_Hackers_Pls WHERE user_id = " + userId;

            System.out.println("Database.isValidLogin: Query to select password of given username.");
            System.out.println(query);

            rs = statement.executeQuery(query);
            rs.next();
            String fetchedPass = rs.getString(1);
            if (!password.equals(fetchedPass)) {
                System.out.println("Invalid password for this user.");
                return false;
            }

            return true;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static int getUserId(String username) {

        query = "SELECT user_id FROM users WHERE username = '" + username + "'";

        System.out.println("Database.getUserId : Query to select user ID from given username.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getInt(1);

        } catch(Exception e) {System.out.println(e);}

        return -1;

    }

    public static boolean checkEmailMatch(String email) {

        query = "SELECT email FROM users WHERE user_id = " + currentUserId;

        System.out.println("Database.checkEmailMatch : Query to select user email given user ID.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(email))
                return true;

            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static void changeEmail(String newEmail) {

        query = "UPDATE users SET email = '" + newEmail + "' WHERE user_id = " + currentUserId;

        System.out.println("Database.changeEmail : Query to update email address of user with given ID.");
        System.out.println(query);

        try {

            statement.execute(query);

        } catch(Exception e) {System.out.println(e);}

    }

    public static boolean checkIfUsernameIsTaken(String username) {
        query = "Select username FROM users WHERE username = '" + username + "'";
        System.out.println("Database.checkIfUsernameIsTaken : Query to select username given username.");
        System.out.println(query);
        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if (rs.getString(1).equalsIgnoreCase(username)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkIfEmailIsTaken(String email) {
        query = "Select email FROM users WHERE email = '" + email + "'";
        System.out.println("Database.checkIfUsernameIsTaken : Query to select email given email.");
        System.out.println(query);
        try {
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            if (rs.getString(1).equalsIgnoreCase(email)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public static boolean checkPasswordMatch(String password) {

        query = "SELECT password FROM no_hackers_pls WHERE user_id = " + currentUserId;

        System.out.println("Database.checkPasswordMatch : Query to select user passcode given user ID.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(password))
                return true;

            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static boolean checkIfEmailCodeIsAlreadyGenerated() {
        query = "SELECT user_id FROM emailcodes WHERE user_id = " + currentUserId;

        System.out.println("Database.checkIfEmailCodeIsAlreadyGenerated: Query to select email code given user ID.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("Database.checkIfEmailCodeIsAlreadyGenerated: Email code is already generated.");
                return true;
            }
            System.out.println("Database.checkIfEmailCodeIsAlreadyGenerated: Email code isn't generated yet.");
            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static boolean checkEmailCodeMatch(String emailcode) {

        query = "SELECT generatedcode FROM emailcodes WHERE user_id = " + currentUserId;

        System.out.println("Database.checkEmailCodeMatch: Query to select email code given user ID.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(emailcode)) {
                System.out.println("Database.checkEmailCodeMatch: Email code matches.");
                return true;
            }

            System.out.println("Database.checkEmailCodeMatch: Email code doesn't match.");
            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }
    public static void changePassword(String newPassword) {

        query = "UPDATE no_hackers_pls SET password = '" + newPassword + "' WHERE user_id = " + currentUserId;

        System.out.println("Database.changePassword : Query to update passcode of user with given ID:");
        System.out.println(query);

        try {

            statement.execute(query);


        } catch(Exception e) {System.out.println(e);}

    }

    public static void generateEmailCode() {

        System.out.println("Database.generateEmailCode: Query to insert an email code.");
        System.out.println(query);

        try {
            if (checkIfEmailCodeIsAlreadyGenerated()) {
                System.out.println("Database.generateEmailCode: Email code is already generated.");
            }
            else if (!checkIfEmailCodeIsAlreadyGenerated()) {
                currentGeneratedCode = RandomStringUtils.randomAlphanumeric(10);
                System.out.println("Database.generateEmailcode: Generating email code.");
                statement.execute("INSERT INTO emailcodes VALUES ('" + currentUserId + "' , '" + currentGeneratedCode + "')");
            }

        } catch(Exception e) {System.out.println(e);}

    }

    public static void removeEmailCode() {
        query = "DELETE FROM emailcodes WHERE user_id = " + currentUserId;

        try {
            System.out.println("Database.removeEmailCode: Query to delete email codes given user ID.");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }

    public static void removeAllEmailCodes() {
        query = "DELETE FROM emailcodes";
        try {
            System.out.println("Database.removeAllEmailCodes: Query to delete all email codes.");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }
    public static void generateSoftwareCode() {
        currentGeneratedCode = RandomStringUtils.randomAlphanumeric(10);

        values = "'" + currentUserId + "' , '" + currentGeneratedCode + "'";
        query = "INSERT INTO softwaretokencodes VALUES (" + values + ")";

        System.out.println("Database.generateSoftwareCode: Query to insert software code given user ID.");
        System.out.println(query);

        try {
            if (checkIfSoftwareCodeIsAlreadyGenerated()) {
                System.out.println("Database.generateSoftwareCode: Software code is already generated.");
            }
            else if (!checkIfSoftwareCodeIsAlreadyGenerated()) {
                System.out.println("Database.generateSoftwareCode: Generating software code.");
                statement.execute("INSERT INTO softwaretokencodes VALUES ('" + currentUserId + "' , '" + currentGeneratedCode + "')");
            }

        } catch(Exception e) {System.out.println(e);}

    }

    public static void removeSoftwareCode() {
        query = "DELETE FROM softwaretokencodes WHERE user_id = " + currentUserId;

        try {
            System.out.println("Database.removeSoftwareCode: Query to delete software codes given user ID.");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }

    public static void removeAllSoftwareCodes() {
        query = "DELETE FROM softwaretokencodes";
        try {
            System.out.println("Database.removeAllSoftwareCodes: Query to delete all software tokens.");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }

    public static boolean checkIfSoftwareCodeIsAlreadyGenerated() {
        query = "SELECT user_id FROM softwaretokencodes WHERE user_id = " + currentUserId;

        System.out.println("Database.checkIfSoftwareCodeIsAlreadyGenerated: Query to select software code given user ID.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("Database.checkIfSoftwareCodeIsAlreadyGenerated: Software code is already generated.");
                return true;
            }
            System.out.println("Database.checkIfSoftwareCodeIsAlreadyGenerated: Software code isn't generated yet.");
            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static boolean checkSoftwareCodeMatch(String softwaretokencode) {

        query = "SELECT generatedcode FROM softwaretokencodes WHERE user_id = " + currentUserId;

        System.out.println("Database.checkSoftwareCodeMatch: Query to select software code given user ID.");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(softwaretokencode)) {
                System.out.println("Database.checkSoftwareCodeMatch: Software code matches.");
                return true;
            }

            System.out.println("Database.checkSoftwareCodeMatch: Software code doesn't match.");
            return false;

        } catch(Exception e) {System.out.println(e);}

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

    public static void setCurrentGeneratedCode(String currentGeneratedCode) {
        Database.currentGeneratedCode = currentGeneratedCode;
    }
    public static void setCurrentListingId(int currentListingId) {
        Database.currentListingId = currentListingId;
    }


    public static ResultSet getProperties() {

        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM listings";
        query = Select + " " + From;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

    }

    public static ResultSet getAllProperties(int listingId) {

        String Select = "SELECT title, city, listing_type, price, neighbourhood, street, street_number, floor, room_number, q_size, phone_number";
        String From = "FROM listings";
        String Where = "WHERE listing_id = '" + listingId + "'";
        query = Select + " " + From + " " + Where;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

    }

    public static ResultSet getProperties(int userId) {

        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM listings";
        String Where = "WHERE user_id = '" + userId + "'";
        query = Select + " " + From + " " + Where;

        ResultSet rs = null;

        try {
            rs = statement.executeQuery(query);
            System.out.println(query);

        } catch (Exception e) {System.out.println(e);};

        return rs;

    }

    public static void deleteProperty(int listingId) {

        query = "DELETE FROM listings WHERE listing_id = '" + listingId + "'";

        try {
            statement.execute(query);
            System.out.println(query);

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


    public static ResultSet getFilteredProperties(String title, String city, String type, int price) {

        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM Listings";
        String Where = "WHERE title = '" + title + "' AND city = '" + city + "' AND listing_type = '" + type + "' AND price <= " + price;

        query = Select + " " + From + " " + Where;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

    }

    public static ResultSet getFilteredProperties(String title, String city, String type, int price, int currentUserId) {

        String Select = "SELECT listing_id, title, city, listing_type, price";
        String From = "FROM Listings";
        String Where = "WHERE title = '" + title + "' AND city = '" + city + "' AND listing_type = '" + type + "' AND price <= " + price + "' AND user_id = '" + currentUserId;

        query = Select + " " + From + " " + Where;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

    }

    public static ResultSet getCity() {
        String Select = "SELECT city";
        String From = "FROM Listings";
        query = Select + " " + From;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

    }

    public static ResultSet getPType() {

        String Select = "SELECT listing_type";
        String From = "FROM Listings";
        query = Select + " " + From;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

    }

    public static String getUsername() {
        query = "SELECT username FROM users WHERE user_id = " + currentUserId;
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

    public static String getEmail() {
        query = "SELECT email FROM users WHERE user_id = " + currentUserId;
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

    public static String getPropertyTitle() {
        query = "SELECT title FROM listings WHERE listing_id = " + currentListingId;
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
    public static String getPropertyCity() {
        query = "SELECT city FROM listings WHERE listing_id = " + currentListingId;
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
    public static String getPropertyType() {
        query = "SELECT listing_type FROM listings WHERE listing_id = " + currentListingId;
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
    public static String getPropertyPrice() {
        query = "SELECT price FROM listings WHERE listing_id = " + currentListingId;
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

    public static String getPropertyNeighbourhood() {
        query = "SELECT neighbourhood FROM listings WHERE listing_id = " + currentListingId;
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

    public static String getPropertyStreet() {
        query = "SELECT street FROM listings WHERE listing_id = " + currentListingId;
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

    public static String getPropertyStreetNumber() {
        query = "SELECT street_number FROM listings WHERE listing_id = " + currentListingId;
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

    public static String getPropertyFloor() {
        query = "SELECT floor FROM listings WHERE listing_id = " + currentListingId;
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

    public static String getPropertyRoomNumber() {
        query = "SELECT street_number FROM listings WHERE listing_id = " + currentListingId;
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
    public static String getPropertyQSize() {
        query = "SELECT q_size FROM listings WHERE listing_id = " + currentListingId;
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
    public static String getPropertyPhoneNumber() {
        query = "SELECT phone_number FROM listings WHERE listing_id = " + currentListingId;
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

    public static ResultSet getListingImage() {
        query = "SELECT imagePath FROM listingimages WHERE listing_id = " + currentListingId;
        ResultSet rs = null;
        try {
            System.out.println("VZEMI TIQ SNIMKI DE");
            rs = statement.executeQuery(query);
            return rs;
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static void insertNewListing(String title, String city, String listing_type, String price, String neighbourhood,
                                         String street, String streetNumber, String floor, String roomNumber, String qSize, String phoneNumber) {
        values = "NULL, '" + title + "' , '" + city + "' , '" + listing_type + "' , '" + price + "' , '" + neighbourhood + "' , '" + street + "' , '" + streetNumber + "' , '" + floor + "' , '" + roomNumber + "' , '" + qSize + "' , '" + phoneNumber + "' , '" + currentUserId + "'";
        query = "INSERT INTO listings VALUES (" + values + ")";

        try {
            System.out.println("Database.insertNewListing : Query to insert new property.");
            System.out.println(query);

            statement.execute(query);

            ResultSet rs = statement.executeQuery("SELECT MAX(listing_id) FROM listings");
            rs.next();
            currentListingId = rs.getInt(1);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void addListingImages(String imagePath) {
        values = "'" + currentListingId + "', '" + imagePath + "'";
        query = "INSERT INTO listingimages VALUES (" + values + ")";

        try{
            System.out.println("Database.addListingImages: Query to insert images into listingimages table.");
            System.out.println(query);

            statement.execute(query);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String injectionProtection(String input) {

        //Symbol whitelist
        input = input.replaceAll("[^a-zA-Z0-9!_@#$%^&*]", " ");
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
            System.out.println(e);
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
}
