package ozog.szumlanski.development.organizer;

/**
 * Created by Przemek on 10/03/2018.
 */

public class Task {


    private String title;
    private int id;

    public Task(){
    }

    public Task(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
