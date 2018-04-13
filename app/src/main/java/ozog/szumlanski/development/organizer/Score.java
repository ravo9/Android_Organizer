package ozog.szumlanski.development.organizer;

import android.graphics.Color;
import android.util.Log;

import java.util.List;

/**
 * Created by Przemek on 07/04/2018.
 */

public class Score {
    private int score;
    private int perfectscore;
    private Database db = new Database(MainWindow.c);
    List<Task> tasks;
    Color myWhite;

    public int getScore() {
        score = 0;
        tasks = db.getDoneTasks();
        for(Task task : tasks) {
            score = score + task.getPriority();
        }
        Log.d("Score: ", Integer.toString(score));
        return score;

    }

    private int getPerfectScore() {
        perfectscore = 0;
        tasks = db.getAllArchiveTasks();
        for(Task task : tasks) {
            perfectscore = perfectscore + task.getPriority();
        }
        Log.d("Perfect score: ", Integer.toString(perfectscore));
        return perfectscore;
    }

    public int ratingColor() {

        int color = Color.rgb(233, 223, 233);
        Log.d("percentage: ", Integer.toString(percentage()));
        Log.d("Color int: ", Integer.toString(color));
         return 1;
    }

    public int percentage() {
        int percentage;
        percentage = getScore() / getPerfectScore();
        percentage *= 100;
        return percentage;
    }
}
