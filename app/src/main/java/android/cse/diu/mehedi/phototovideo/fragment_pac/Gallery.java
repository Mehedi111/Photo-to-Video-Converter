package android.cse.diu.mehedi.phototovideo.fragment_pac;


import android.cse.diu.mehedi.phototovideo.BitmapEditor;
import android.cse.diu.mehedi.phototovideo.GettingImages;
import android.cse.diu.mehedi.phototovideo.adapter_class.GridAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.cse.diu.mehedi.phototovideo.R;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jcodec.api.android.AndroidSequenceEncoder;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Rational;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Gallery extends Fragment {

    private Toolbar mtoolbar;
    private ImageView backImg, nextImg;
    private GridView gridView;
    private ArrayList<String> imagesList;
    private GettingImages imagesObj;
    private GridAdapter gridAdapter;
    private ArrayList<Bitmap> choosenImage = new ArrayList<>();
    private ProgressBar mProgressBar;
    private TextView mProgressBarText;
    private BitmapEditor bitmapEditor;


    public Gallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mtoolbar = view.findViewById(R.id.toolbar);
        backImg = view.findViewById(R.id.backButton);
        nextImg = view.findViewById(R.id.nextButton);
        gridView = view.findViewById(R.id.grid_items);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBarText = view.findViewById(R.id.progress_bar_text);

        bitmapEditor = new BitmapEditor();

        ///getting Images from gallery to list

        imagesList = new GettingImages().getAllShownImagesPath(getActivity());
        gridAdapter = new GridAdapter(getActivity(), imagesList);
        gridView.setAdapter(gridAdapter);

        ////choosing photo

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                if (choosenImage.size() >= 3) {
                    Toast.makeText(getActivity(), "You Can Only Select 3 Images", Toast.LENGTH_SHORT).show();
                } else {

                    convertToBitmap(imagesList.get(position));
                    Toast.makeText(getActivity(), ""+choosenImage.size()+" Image Selected", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ///back to home
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHomePage();
            }
        });

        ///next to video page
        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextToVideoPage();
            }
        });

        return view;
    }

    private void convertToBitmap(String s) {

        File imgFile = new  File(s);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap rBitmaps = bitmapEditor.getResizedBitmap(myBitmap);
            choosenImage.add(rBitmaps);

        }else {
            Toast.makeText(getActivity(), "No DIR found", Toast.LENGTH_SHORT).show();
        }
    }



    private void nextToVideoPage(){


        if (choosenImage.size()==3){

           new ProgressTask().execute();
        }else {
            Toast.makeText(getActivity(), "Select At Least 3 Photos", Toast.LENGTH_LONG).show();
        }

    }


    private void backToHomePage() {
        CreatePage createPage = new CreatePage();
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, createPage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public class ProgressTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

           // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            choosenImage = bitmapEditor.getTextInVideo(choosenImage);
            FileChannelWrapper out = null;
            File dir = new File(Environment.getExternalStorageDirectory() + "/" );
            File file = new File(dir, "test.mp4");

            try {
                try {
                    out = NIOUtils.writableFileChannel(file.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                AndroidSequenceEncoder encoder = new AndroidSequenceEncoder(out, Rational.R(25, 1));
                int j =1;
                publishProgress(j);
                    for (int i = 1; i < 100; i++) {
                        encoder.encodeImage(choosenImage.get(0));
                        j=3;
                        publishProgress(j);
                    }
                    for (int i = 100; i < 200; i++) {
                        encoder.encodeImage(choosenImage.get(1));
                        j=6;
                        publishProgress(j);
                    }
                    for (int i = 200; i < 300; i++) {
                        encoder.encodeImage(choosenImage.get(2));
                        j=8;
                        publishProgress(j);
                    }

                publishProgress(10);
                encoder.finish();

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                NIOUtils.closeQuietly(out);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            mProgressBar.setProgress(values[0]);
            mProgressBarText.setText("Please wait...Coverting to video "+values[0]+"0%");

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getActivity(), "Video is Created", Toast.LENGTH_LONG).show();

            VideoFromPhoto videoFromPhoto = new VideoFromPhoto();

            FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, videoFromPhoto);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }



}

