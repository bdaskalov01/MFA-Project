package com.RustyRents.RustyRents.Database;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;

@Component
public class Database {

    private static int currentUserId = 0;

    private static int currentListingId;

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

        System.out.println("Database.storeNewLogin : Query to insert new login given userId and password:");
        System.out.println(query);

        try {

            statement.execute(query);

        } catch(Exception e) {System.out.println(e);}

    }

    public static void addNewUser(String username, String password, String email) {

        int newUserId;
        String test;

        try {
            if (!checkIfUsernameIsTaken(username) && !checkIfEmailIsTaken(email)) {
                values = "NULL, '" + username + "', '" + email + "'";
                query = "INSERT INTO users VALUES (" + values + ")";

                System.out.println("Database.addNewUser : Query to insert new user with username and email:");
                System.out.println(query);

                statement.execute(query);

                ResultSet rs = statement.executeQuery("SELECT MAX(user_id) FROM users");
                rs.next();
                newUserId = rs.getInt(1);
                storeNewLogin(newUserId, password);
            }
        } catch(Exception e) {System.out.println(e);}

    }

    public static boolean isValidLogin(String username, String password) {

        query = "SELECT user_id FROM users WHERE username = '" + username + "'";

        System.out.println("Database.isValidLogin: Query for selecting the id of given username:");
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

            System.out.println("Database.isValidLogin: Query to select password of given username:");
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

        System.out.println("Database.getUserId : Query to select user ID from given username:");
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

        System.out.println("Database.checkEmailMatch : Query to select user email given user ID:");
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

        System.out.println("Database.changeEmail : Query to update email address of user with given ID:");
        System.out.println(query);

        try {

            statement.execute(query);

        } catch(Exception e) {System.out.println(e);}

    }

    public static boolean checkIfUsernameIsTaken(String username) {
        query = "Select username FROM users WHERE username = '" + username + "'";
        System.out.println("Database.checkIfUsernameIsTaken : Query to select username given username:");
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
        System.out.println("Database.checkIfUsernameIsTaken : Query to select email given email:");
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

        System.out.println("Database.checkPasswordMatch : Query to select user passcode given user ID:");
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

        System.out.println("checkings");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("exists");
                return true;
            }
            System.out.println("doesnt exist");
            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static boolean checkEmailCodeMatch(String emailcode) {

        query = "SELECT generatedcode FROM emailcodes WHERE user_id = " + currentUserId;

        System.out.println("checking matching email code");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(emailcode))
                return true;

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
        currentGeneratedCode = RandomStringUtils.randomAlphanumeric(10);

        values = "'" + currentUserId + "' , '" + currentGeneratedCode + "'";
        query = "INSERT INTO emailcodes VALUES (" + values + ")";

        System.out.println("GENERATE EMAILCODE");
        System.out.println(query);

        try {
            if (checkIfEmailCodeIsAlreadyGenerated()) {
                System.out.println("Code is already generated");
            }
            else if (!checkIfEmailCodeIsAlreadyGenerated()) {
                System.out.println("Generating code");
                statement.execute("INSERT INTO emailcodes VALUES ('" + currentUserId + "' , '" + currentGeneratedCode + "')");
            }

        } catch(Exception e) {System.out.println(e);}

    }

    public static void removeEmailCode() {
        query = "DELETE FROM emailcodes WHERE user_id = " + currentUserId;

        try {
            System.out.println("Removing emailcode");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }

    public static void removeAllEmailCodes() {
        query = "DELETE FROM emailcodes";
        try {
            System.out.println("Removing emailcode");
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

        System.out.println("GENERATE EMAILCODE");
        System.out.println(query);

        try {
            if (checkIfSoftwareCodeIsAlreadyGenerated()) {
                System.out.println("Code is already generated");
            }
            else if (!checkIfSoftwareCodeIsAlreadyGenerated()) {
                System.out.println("Generating code");
                statement.execute("INSERT INTO softwaretokencodes VALUES ('" + currentUserId + "' , '" + currentGeneratedCode + "')");
            }

        } catch(Exception e) {System.out.println(e);}

    }

    public static void removeSoftwareCode() {
        query = "DELETE FROM softwaretokencodes WHERE user_id = " + currentUserId;

        try {
            System.out.println("Removing softwaretokencode");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }

    public static void removeAllSoftwareCodes() {
        query = "DELETE FROM softwaretokencodes";
        try {
            System.out.println("Removing softwaretokencode");
            statement.execute(query);
        }
        catch(Exception e ) {
            System.out.println(e);
        }
    }

    public static boolean checkIfSoftwareCodeIsAlreadyGenerated() {
        query = "SELECT user_id FROM softwaretokencodes WHERE user_id = " + currentUserId;

        System.out.println("checkings");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("exists");
                return true;
            }
            System.out.println("doesnt exist");
            return false;

        } catch(Exception e) {System.out.println(e);}

        return false;

    }

    public static boolean checkSoftwareCodeMatch(String softwaretokencode) {

        query = "SELECT generatedcode FROM softwaretokencodes WHERE user_id = " + currentUserId;

        System.out.println("checking matching software token code");
        System.out.println(query);

        try {

            ResultSet rs = statement.executeQuery(query);
            rs.next();

            if (rs.getString(1).equalsIgnoreCase(softwaretokencode))
                return true;

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
        String From = "FROM Listings";
        query = Select + " " + From;

        ResultSet rs = null;

        try {

            rs = statement.executeQuery(query);

        } catch (Exception e) {System.out.println(e);}

        return rs;

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
}
