package ozog.szumlanski.development.organizer;

import java.util.List;

/**
 * Created by Przemek on 07/04/2018.
 */

public class Score {
    Database db = new Database(MainWindow.c);
    private static List<Task> allArchivedTasks;
    private int score = 0;

    public int getScore() {
        allArchivedTasks = db.getAllArchiveTasks();
        for(Task task : allArchivedTasks) {
            if(task.getStatus() == "done") {
                score += task.getPriority();
            }
        }
        return score;
    }

    private int getPerfectScore() {
        allArchivedTasks = db.getAllArchiveTasks();
        for(Task task : allArchivedTasks) {
            score += task.getPriority();
        }
        return score;
    }

    public int ratingColor() {
        if(getScore() > (getPerfectScore() * (3 / 4))) {
            return 1;
        } else if(getScore() > (getPerfectScore() * (1 / 4))) {
            return 2;
        } else {
            return 3;
        }
    }
}
