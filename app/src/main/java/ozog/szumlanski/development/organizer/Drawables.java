package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.IOException;
import java.io.InputStream;

public class Drawables {

    public static Drawable btnAdd;

    public static void uploadImages(Context game) {

        try {
            InputStream stream = game.getAssets().open("btn_add.png");
            btnAdd = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



