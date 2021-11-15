package dao;

import models.DepartmentNews;
import models.Departments;
import models.News;
import models.Users;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oDepartmentsDaoTest {
    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oUsersDao sql2oUsersDao;
    private static Sql2oNewsDao sql2oNewsDao;
    private static Connection conn;

    @Before
    public void setUp() {

        //local server
        String connectionString = "jdbc:postgresql://localhost:5432/rest_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "password");

        sql2oDepartmentsDao = new Sql2oDepartmentsDao(sql2o);
        sql2oUsersDao = new Sql2oUsersDao(sql2o);
        sql2oNewsDao = new Sql2oNewsDao(sql2o);
        System.out.println("database connection established");
        conn = sql2o.open();
    }

    @After
    public void tearDown() {
        sql2oDepartmentsDao.clearAll();
        sql2oUsersDao.clearAll();
        sql2oNewsDao.clearAll();
        System.out.println("test records cleared");
    }
    @AfterClass
    public static void shutDown() {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void idSetForAddedDepartment() {
        Departments department = createNewDepartment();
        int originalId = department.getId();
        sql2oDepartmentsDao.add(department);
        assertNotEquals(originalId,department.getId());
    }

    @Test
    public void addUserToDepartment() {
        Departments department = createNewDepartment();
        sql2oDepartmentsDao.add(department);
        Users user = createNewUser();
        Users otherUser = new Users("Brian","intern","printing and compiling.");
        sql2oUsersDao.add(user);
        sql2oUsersDao.add(otherUser);
        sql2oDepartmentsDao.addUserToDepartment(user,department);
        sql2oDepartmentsDao.addUserToDepartment(otherUser,department);
        assertEquals(2,sql2oDepartmentsDao.getAllUsersInDepartment(department.getId()).size());
        assertEquals(2,sql2oDepartmentsDao.findById(department.getId()).getSize());
    }

    @Test
    public void getAll() {
        Departments department = createNewDepartment();
        Departments otherDepartment = new Departments("printing","printing of books");
        sql2oDepartmentsDao.add(department);
        sql2oDepartmentsDao.add(otherDepartment);
        assertEquals(department,sql2oDepartmentsDao.getAll().get(0));
        assertEquals(otherDepartment,sql2oDepartmentsDao.getAll().get(1));
    }

    @Test
    public void correctDepartmentReturnedFindById() {
        Departments department = createNewDepartment();
        Departments otherDepartment = new Departments("printing","printing of journals");
        sql2oDepartmentsDao.add(department);
        sql2oDepartmentsDao.add(otherDepartment);
        assertEquals(department,sql2oDepartmentsDao.findById(department.getId()));
        assertEquals(otherDepartment,sql2oDepartmentsDao.findById(otherDepartment.getId()));
    }

    @Test
    public void getAllUsersInDepartmentWorkCorrectly() {
        Departments department=createNewDepartment();
        sql2oDepartmentsDao.add(department);
        Users user=createNewUser();
        Users otherUser = new Users("Brian","intern","printing and compiling.");
        sql2oUsersDao.add(user);
        sql2oUsersDao.add(otherUser);
        sql2oDepartmentsDao.addUserToDepartment(user,department);
        sql2oDepartmentsDao.addUserToDepartment(otherUser,department);
        assertEquals(2,sql2oDepartmentsDao.getAllUsersInDepartment(department.getId()).size());
        assertEquals(2,sql2oDepartmentsDao.findById(department.getId()).getSize());
    }
    @Test
    public void getDepartmentNewsWorksCorrectly() {
        Users users = createNewUser();
        sql2oUsersDao.add(users);
        Departments departments = createNewDepartment();
        sql2oDepartmentsDao.add(departments);
        DepartmentNews department_news = new DepartmentNews("Community","plan regarding community support",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        News news=new News("Hike","update on staff hike",users.getId());
        sql2oNewsDao.addNews(news);
        assertEquals(department_news.getTitle(),sql2oDepartmentsDao.getDepartmentNews(department_news.getId()).get(0).getTitle());
    }

    //helper
    private Departments createNewDepartment() {
        return new Departments("Interns","train new employees");
    }
    private Users createNewUser() {
        return new Users("Brian Koech","Treasurer","Record Cheques and Receipts");
    }
}