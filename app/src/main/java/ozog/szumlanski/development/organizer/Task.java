package ozog.szumlanski.development.organizer;

public class Task {

    private int id;
    private String content;
    private String createDate;
    //private String notifDate;
    private String status;
    private int priority;

    public Task(){
    }

    public Task(int id, String content, String createDate, int priority) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.priority = priority;
    }
    public Task(int id, String content, String createDate, String status, int priority) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.status = status;
        this.priority = priority;

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

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateDate() {
        return createDate;
    }

    /*public void setNotifDate(String notifDate) {
        this.notifDate = notifDate;
    }
    public String getNotifDate() {
        return notifDate;
    }*/

    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() { return priority; }
}
