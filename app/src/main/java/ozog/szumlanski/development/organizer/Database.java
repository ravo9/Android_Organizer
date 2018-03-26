package ozog.szumlanski.development.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_ARCHIVE = "archive";

    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    //private static final String KEY_NOTIFDATE = "notifdate";
    private static final String KEY_CREATE_DATE = "creation_date";
    private static final String KEY_STATUS = "status";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creates database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "("
                + KEY_ID + " INT PRIMARY KEY," + KEY_CONTENT + " TEXT," + KEY_CREATE_DATE + " TEXT,"
                + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_ARCHIVE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ARCHIVE + "("
                + KEY_ID + " INT PRIMARY KEY," + KEY_CONTENT + " TEXT," + KEY_CREATE_DATE + " TEXT,"
                + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_ARCHIVE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARCHIVE);
        onCreate(db);
    }

    public static int newId() {
        int lastId = 0;
        List<Task> allTasks = MainWindow.db.getAllTasks();
        for(Task task : allTasks) {
            if(task.getId() >= lastId)
                lastId = task.getId() + 1;
        }
        return lastId;
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_CONTENT, task.getContent());
        values.put(KEY_CREATE_DATE, task.getCreateDate());
        //values.put(KEY_NOTIFDATE, task.getNotifDate().toString());
        values.put(KEY_STATUS, task.getStatus());
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Get all tasks from the "tasks" table
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    task.setId(Integer.parseInt(cursor.getString(0)));
                    task.setContent(cursor.getString(1));
                    task.setCreateDate(cursor.getString(2));
                    //task.setNotifDate(cursor.getString(3));
                    task.setStatus(cursor.getString(3));
                    taskList.add(task);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch(Exception e) {}

        return taskList;
    }

    // Remove table "tasks" from the database
    public void dropTasksTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_TASKS);
        db.close();
    }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    // Remove single task from "tasks" table
    public void removeTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    // Count how many tasks are stored in the "tasks" table
    public int getTaskCount() {
        String countQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Fetch single task stored in the "tasks" table
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, new String[] { KEY_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Task task = new Task(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));

        return task;
    }


    // METHODS FOR ARCHIVE TABLE

    public void archiveTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_CONTENT, task.getContent());
        values.put(KEY_CREATE_DATE, task.getCreateDate());
        values.put(KEY_STATUS, task.getStatus());
        db.insert(TABLE_ARCHIVE, null, values);
        db.close();
    }

    public void dropArchiveTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_ARCHIVE);
        db.close();
    }

    public List<Task> getAllArchiveTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ARCHIVE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setContent(cursor.getString(1));
                task.setCreateDate(cursor.getString(2));
                task.setStatus(cursor.getString(3));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    public List<Task> getDoneTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ARCHIVE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setContent(cursor.getString(1));
                task.setCreateDate(cursor.getString(2));
                task.setStatus(cursor.getString(3));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    public int getArchivedTaskCount() {
        String countQuery = "SELECT * FROM " + TABLE_ARCHIVE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
