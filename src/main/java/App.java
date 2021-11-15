import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import exceptions.ApiException;

import models.Departments;
import models.News;
import models.Users;

import org.sql2o.Sql2o;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;


public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        Sql2oNewsDao sql2oNewsDao;
        Sql2oUsersDao sql2oUsersDao;
        Sql2oDepartmentsDao sql2oDepartmentsDao;
        Gson gson = new Gson();

        //local server
        String connectionString = "jdbc:postgresql://localhost:5432/Rest_API";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "password");

        sql2oDepartmentsDao=new Sql2oDepartmentsDao(sql2o);
        sql2oNewsDao=new Sql2oNewsDao(sql2o);
        sql2oUsersDao=new Sql2oUsersDao(sql2o);


        //view users
        get("/users", "application/json", (request, response) -> {

            if(sql2oDepartmentsDao.getAll().size() > 0){
                return gson.toJson(sql2oUsersDao.getAll());
            }
            else {
                return "message: No Users In The Database";
            }
        });
        // view departments
        get("/departments","application/json",(request, response) -> {
            if(sql2oDepartmentsDao.getAll().size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAll());
            }
            else {
                return "message: No Departments In The Database";
            }
        });
        //view general news
        get("/news/general","application/json",(request, response) -> {
            if(sql2oNewsDao.getAll().size()>0){
                return gson.toJson(sql2oNewsDao.getAll());
            }
            else {
                return "message: No General News In The Database";
            }
        });

        //filters
        exception(ApiException.class, (exception, request, response) -> {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", exception.getStatusCode());
            jsonMap.put("errorMessage", exception.getMessage());
            response.type("application/json");
            response.status(exception.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });

        after((request, response) -> response.type("application/json"));
    }
}
