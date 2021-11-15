package dao;

import models.DepartmentNews;
import models.News;

import java.util.List;

public interface NewsDao {

    //create
    void addNews(News news);
    void addDepartmentNews(DepartmentNews department_news);

    //read
    List<News> getAll();
    News findById(int id);

    //delete
    void clearAll();
}
