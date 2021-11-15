package dao;

import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {

    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oUsersDao sql2oUsersDao;
    private static Connection conn;

    @Before
    public void setUp() {

        //local server
        String connectionString = "jdbc:postgresql://localhost:5432/rest_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "password");

        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);
        System.out.println("database connection established");
        conn = sql2o.open();

    }

    @After
    public void tearDown() {
        sql2oDepartmentsDao.clearAll();
        sql2oUsersDao.clearAll();
        System.out.println("test records deleted");
    }
    @AfterClass
    public static void shutDown() {
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void addingUserToDbSetsUserId() {
        Users user = createNewUser();
        int originalId= user.getId();
        sql2oUsersDao.add(user);
        assertNotEquals(originalId,user.getId());
    }

    @Test
    public void addedUserIsReturnedCorrectly() {
        Users user = createNewUser();
        sql2oUsersDao.add(user);
        assertEquals(user.getName(),sql2oUsersDao.findById(user.getId()).getName());
    }

    @Test
    public void allInstancesAreReturned() {

        Users users = createNewUser();
        Users otherUser = new Users("Brian Koech","intern","compile documents");
        sql2oUsersDao.add(users);
        sql2oUsersDao.add(otherUser);
        assertEquals(users.getName(),sql2oUsersDao.getAll().get(0).getName());
        assertEquals(otherUser.getName(),sql2oUsersDao.getAll().get(1).getName());
    }

    @Test
    public void getDepartmentsUserIsIn() {
        Departments department = createNewDepartment();
        Departments otherDepartment = new Departments("printing","printing of books");
        sql2oDepartmentsDao.add(department);
        sql2oDepartmentsDao.add(otherDepartment);
        Users user=createNewUser();
        Users otherUser= new Users("Brian","intern","Paper work");
        sql2oUsersDao.add(user);
        sql2oUsersDao.add(otherUser);
        sql2oDepartmentsDao.addUserToDepartment(user,department);
        sql2oDepartmentsDao.addUserToDepartment(otherUser,department);
        sql2oDepartmentsDao.addUserToDepartment(user,otherDepartment);
        assertEquals(2,sql2oUsersDao.getAllUserDepartments(user.getId()).size());
        assertEquals(1,sql2oUsersDao.getAllUserDepartments(otherUser.getId()).size());
    }

    //helper
    private Users createNewUser() {
        return new Users("Brian Koech","Treasurer","Keep money related records");
    }
    private Departments createNewDepartment() {
        return new Departments("Editing","editing of newspaper");
    }
}