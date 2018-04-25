package android.cse.diu.mehedi.phototovideo.adapter_class;

import android.app.Activity;
import android.content.Context;
import android.cse.diu.mehedi.phototovideo.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dustu on 4/24/2018.
 */

public class GridAdapter extends BaseAdapter {

   Context context;
   ArrayList<String> images = new ArrayList<>();

    public GridAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }


    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        ImageView picturesView;
        if (convertView == null) {
            picturesView = new ImageView(context);
            picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            picturesView
                    .setLayoutParams(new GridView.LayoutParams(270, 270));

        } else {
            picturesView = (ImageView) convertView;
        }

        Glide.with(context).load(images.get(position))
                .placeholder(R.drawable.image_placeholder).centerCrop()
                .into(picturesView);

        return picturesView;

    }


}
