package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.connection();

    public UserDaoJDBCImpl() {
    }

    public UserDaoJDBCImpl(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createUsersTable() {
        String CREATE_TABLE_SQL = "CREATE TABLE `my_db`.`users` ("
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`name` VARCHAR(45) NULL,"
                + "`lastName` VARCHAR(45) NULL,"
                + "`age` INT NULL,"
                + "PRIMARY KEY (`id`))"
                + "COMMENT = 'Java Mentor course';";
        try (PreparedStatement stm = connection.prepareStatement(CREATE_TABLE_SQL)) {
            stm.executeUpdate();
            System.out.println("Table created");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE `my_db`.`users`;";
        try (PreparedStatement stm = connection.prepareStatement(sqlDrop)) {
            stm.executeUpdate();
            System.out.println("Table deleted");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT INTO `my_db`.`users` (`name`, `lastName`, `age`) VALUES (?, ?, ?);";
                //"('" + name + "','" + lastName + "','" + age + "');";

        try (PreparedStatement stm = connection.prepareStatement(sqlSaveUser)) {
            connection.setAutoCommit(false);
            stm.setString(1, name);
            stm.setString(2, lastName);
            stm.setByte(3, age);
            stm.executeUpdate();
            connection.commit();
            System.out.println("Data inserted");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables1) {
                throwables1.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String deleteBiID = "DELETE FROM `my_db`.`users` WHERE (`id` = ?);";
       // WHERE (`id` = '" + id + "');";
        try (PreparedStatement stm = connection.prepareStatement(deleteBiID)) {
            connection.setAutoCommit(false);
            stm.setLong(1, id);
            stm.executeUpdate();
            connection.commit();
            System.out.println("Data deleted by id  ----- " + id);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables1) {
                throwables1.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        String sqlGetAllData = "SELECT * FROM my_db.users;";
        List<User> list = new ArrayList<>();
        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        //String DB_URL = "jdbc:mysql://localhost:3306/my_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        //String username = "root";
        //String password = "Kazakhstan1!";
        //Connection conn = null;
        //Statement stmt = null;


        try (PreparedStatement stmt = connection.prepareStatement(sqlGetAllData)) {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //conn = DriverManager.getConnection(DB_URL, username, password);
            //stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetAllData);
            while (rs.next()) {
                //Retrieve by column name
                long id = rs.getInt("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                User u = new User();
                u.setId(id);
                u.setName(name);
                u.setLastName(lastName);
                u.setAge((byte) age);
                list.add(u);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String deleteAll = "DELETE FROM my_db.users;";

        try (PreparedStatement stm = connection.prepareStatement(deleteAll)) {
            stm.executeUpdate();
            System.out.println("All datata deleted");
        } catch (Exception throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables1) {
                throwables1.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
