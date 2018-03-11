package ozog.szumlanski.development.organizer;

import java.util.Date;

/**
 * Created by Przemek on 10/03/2018.
 */

public class Task {


    private String title;
    private String content;
    private String notifDate;
    private String status;
    private int id;


    public Task(){
    }

    public Task(String title, int id, String content, String notifDate, String status) {
        this.title = title;
        this.id = id;
        this.content = content;
        this.notifDate = notifDate;
        this.status = "current";
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

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setNotifDate(String notifDate) {
        this.notifDate = notifDate;
    }
    public String getNotifDate() {
        return notifDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() { return status; }
}
