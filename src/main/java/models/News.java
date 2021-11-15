package models;

import java.util.Objects;

@SuppressWarnings("unused")
public class News {

    private int id;
    private final String news_type;
    private final int department_id;
    private final int user_id;
    private final String title;
    private final String description;
    private final String TYPE_OF_NEWS="general";



    public News(String title, String description, int user_id) {
        this.title = title;
        this.description = description;
        this.user_id=user_id;
        this.news_type=TYPE_OF_NEWS;
        this.department_id=0;
    }
    @SuppressWarnings("unused")
    public News(String title, String description,int department_id, int user_id){
        this.title = title;
        this.description = description;
        this.user_id=user_id;
        this.department_id = department_id;
        this.news_type="department";
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getNews_type() {
        return news_type;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        News news = (News) object;
        return id == news.id && department_id == news.department_id && user_id == news.user_id && Objects.equals(news_type, news.news_type) && Objects.equals(title, news.title) && Objects.equals(description, news.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, news_type, department_id, user_id, title, description, TYPE_OF_NEWS);
    }
}
