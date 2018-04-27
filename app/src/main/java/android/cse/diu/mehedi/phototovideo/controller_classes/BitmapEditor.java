package android.cse.diu.mehedi.phototovideo.controller_classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

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
        setText(choosenImage.get(0), "WELCOME", 250, 80, 60, "Lucida Console");
        setText(choosenImage.get(0), "RISE TOGETHER", 300, 450, 80,"Arial Black");
        setText(choosenImage.get(0), "START BEFORE YOU'RE READY", 100, 750, 50,"Arial Narrow");

        ////2ND IMAGE
        setText(choosenImage.get(1), "THE WORLD IS", 250, 80, 60,"Lucida Console");
        setText(choosenImage.get(1), "beautiful", 320, 160, 50,"Segoe Script");
        setText(choosenImage.get(1), "SAVE THE PLANET", 250, 750, 50, "Arial");

        ///3RD IMAGE

        setText(choosenImage.get(2), "Just Breathe", 150, 80, 90,"Impact");
        setText(choosenImage.get(2), "STAY CLOSE TO NATURE", 100, 450, 50,"Arial Black");
        setText(choosenImage.get(2), "Nature is pleased With simplicity", 60, 750, 50,"Tahoma");

        return choosenImage;
    }

    private void setText(Bitmap bitmap, String text, int x, int y, int textSize, String fontFamily) {
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
        paint.setTypeface(Typeface.create(fontFamily ,Typeface.BOLD));
        // draw text to the Canvas center

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText(text, x, y, paint);

    }


    void  setAnimationToText(){

    }


}