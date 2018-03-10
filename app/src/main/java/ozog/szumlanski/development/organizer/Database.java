package ozog.szumlanski.development.organizer;

/**
 * Created by Przemek on 10/03/2018.
 */

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

    private static final String KEY_TITLE = "title";
    private static final String KEY_ID = "id"; // AUTOINCREMENT functionality from SQL used

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLE_TASKS + "("
                + KEY_TITLE + " TEXT," + KEY_ID + " INT PRIMARY KEY AUTOINCREMENT," + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    //Adds single task to "tasks" table
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_ID, task.getId());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    //Get all tasks from the "tasks" table
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setTitle(cursor.getString(0));
                task.setId(Integer.parseInt(cursor.getString(1)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    //Remove table "tasks" from the database
    public void dropTasksTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_TASKS);
        db.close();
    }

    public void createTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    //Remove single task from "tasks" table
    public void removeTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    //Count how many tasks are stored in the "tasks" table
    public int getTaskCount() {
        String countQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Fetch single task stored in the "tasks" table
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, new String[] { KEY_TITLE, KEY_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Task task = new Task(cursor.getString(0), Integer.parseInt(cursor.getString(1)));

        return task;
    }

}
