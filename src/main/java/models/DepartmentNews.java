package models;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({"unused"})
public class DepartmentNews extends News {

    private final String news_type;
    private final int department_id;
    private final AtomicReference<String> TYPE_OF_NEWS = new AtomicReference<>("department");


    public DepartmentNews(String title, String description,int department_id, int user_id) {
        super(title, description, user_id);
        this.department_id = department_id;
        this.news_type = TYPE_OF_NEWS.get();
    }

    @Override
    public String getNews_type() {
        return news_type;
    }

    @Override
    public int getDepartment_id() {
        return department_id;
    }
}
