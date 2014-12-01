package bth740.eventfly.View;

import android.graphics.Bitmap;
import java.util.ArrayList;

/**
 * Created by Wes on 2014-11-30.
 */
public class RowItem {
    public String title;
    Bitmap bitmap;

    public RowItem(String title, Bitmap bitmap) {
        this.title = title;
        this.bitmap = bitmap;
    }
}
