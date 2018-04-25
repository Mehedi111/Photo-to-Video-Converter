package android.cse.diu.mehedi.phototovideo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import org.jcodec.api.SequenceEncoder;
import org.jcodec.api.android.AndroidSequenceEncoder;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Rational;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BitmapEditor {


    public Bitmap getResizedBitmap(Bitmap myBitmap) {
        Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 800, 800, true);
        return resized;
    }


    public ArrayList<Bitmap> getTextInVideo(ArrayList<Bitmap> choosenImage) {

        ///1ST IMAGE
        setText(choosenImage.get(0), "WELCOME", 250, 100, 60);
        setText(choosenImage.get(0), "RISE TOGETHER", 300, 450, 55);
        setText(choosenImage.get(0), "START BEFORE YOU'RE READY", 150, 700, 50);

        ////2ND IMAGE
        setText(choosenImage.get(1), "THE WORLD IS", 250, 100, 50);
        setText(choosenImage.get(1), "beautiful", 300, 200, 70);
        setText(choosenImage.get(1), "SAVE THE PLANET", 250, 700, 50);

        ///3RD IMAGE

        setText(choosenImage.get(2), "Just Breathe", 200, 100, 60);
        setText(choosenImage.get(2), "STAY CLOSE TO NATURE", 100, 450, 55);
        setText(choosenImage.get(2), "Nature is pleased With simplicity", 150, 700, 50);

        return choosenImage;
    }

    private void setText(Bitmap bitmap, String text, int x, int y, int textSize) {
        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (textSize));
        paint.setFakeBoldText(true);
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

        // draw text to the Canvas center

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        /*int x = (bitmap.getWidth() - bounds.width())/6;
        int y = (bitmap.getHeight() + bounds.height())/5;*/

        canvas.drawText(text, x, y, paint);
    }

}