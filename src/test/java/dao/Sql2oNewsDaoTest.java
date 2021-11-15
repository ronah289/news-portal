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

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {

    private static Sql2oDepartmentsDao sql2oDepartmentsDao;
    private static Sql2oUsersDao sql2oUsersDao;
    private static Sql2oNewsDao sql2oNewsDao;
    private static Connection conn;

    @Before
    public void setUp() {

        //comment the two lines below to run locally
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
        System.out.println("test records deleted");
    }
    @AfterClass
    public static void shutDown() {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addNewsWorksCorrectly() {
        Users users = createNewUsers();
        sql2oUsersDao.add(users);
        Departments departments = createDepartment();
        sql2oDepartmentsDao.add(departments);
        News news = new News("Meeting","discuss company competition",users.getId());
        sql2oNewsDao.addNews(news);
        assertEquals(users.getId(),sql2oNewsDao.findById(news.getId()).getUser_id());
        assertEquals(news.getDepartment_id(),sql2oNewsDao.findById(news.getId()).getDepartment_id());
    }

    @Test
    public void addDepartmentNewsWorksCorrectly() {
        Users users = createNewUsers();
        sql2oUsersDao.add(users);
        Departments departments = createDepartment();
        sql2oDepartmentsDao.add(departments);
        DepartmentNews department_news = new DepartmentNews("Meeting","To nominate new treasurer",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        assertEquals(users.getId(),sql2oNewsDao.findById(department_news.getId()).getUser_id());
        assertEquals(department_news.getDepartment_id(),sql2oNewsDao.findById(department_news.getId()).getDepartment_id());
    }

    @Test
    public void getAllWorksCorrectly() {
        Users users=createNewUsers();
        sql2oUsersDao.add(users);
        Departments departments = createDepartment();
        sql2oDepartmentsDao.add(departments);
        DepartmentNews department_news = new DepartmentNews("Meeting","To nominate new treasurer",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        News news=new News("Meeting","discuss staff hike",users.getId());
        sql2oNewsDao.addNews(news);
        assertEquals(2,sql2oNewsDao.getAll().size());
    }

    //helper
    private Departments createDepartment() {
        return new Departments("Editing","editing of books");
    }
    private Users createNewUsers() {
        return new Users("Brian Koech","CEO","oversee all activities");
    }

}