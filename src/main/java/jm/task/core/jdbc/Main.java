package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDaoJDBCImpl udji = new UserDaoJDBCImpl();
        //udji.createUsersTable();                      закоментировал временно. перед отправкой отменю.
        //udji.dropUsersTable();                        закоментировал временно. перед отправкой отменю.
        User u1 = new User("Marie","Curie", (byte) 33);
        udji.saveUser(u1.getName(),u1.getLastName(),u1.getAge());

        User u2 = new User("Pierre","Curie", (byte) 45);
        udji.saveUser(u2.getName(),u2.getLastName(),u2.getAge());

        User u3 = new User("Isaav","Newton", (byte) 46);
        udji.saveUser(u3.getName(),u3.getLastName(),u3.getAge());

        User u4 = new User("Erwin","Schr?dinger", (byte) 23);
        udji.saveUser(u4.getName(),u4.getLastName(),u4.getAge());

        udji.removeUserById(1);

        List<User> list = udji.getAllUsers();
        System.out.println(Arrays.deepToString(list.toArray()));
        //udji.cleanUsersTable();
        //udji.dropUsersTable();

    }
}
