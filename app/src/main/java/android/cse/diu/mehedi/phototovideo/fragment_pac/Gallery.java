package android.cse.diu.mehedi.phototovideo.fragment_pac;


import android.Manifest;
import android.content.pm.PackageManager;
import android.cse.diu.mehedi.phototovideo.controller_classes.BitmapEditor;
import android.cse.diu.mehedi.phototovideo.controller_classes.GettingImages;
import android.cse.diu.mehedi.phototovideo.adapter_class.GridAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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

        mProgressBar.setVisibility(View.INVISIBLE);

        ///getting Images from gallery to list
        checkPerMission();

        ////choosing photo

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (choosenImage.size() >= 3) {
                    Toast.makeText(getActivity(), "You Can Only Select 3 Images", Toast.LENGTH_SHORT).show();
                } else {
                    convertToBitmap(imagesList.get(position));
                    enableButtonNext();
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

    private void checkPerMission() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED ){
                Toast.makeText(getActivity(), "Allow permission for use this application", Toast.LENGTH_SHORT).show();

            }else {
                imagesList = new GettingImages().getAllShownImagesPath(getActivity());
                gridAdapter = new GridAdapter(getActivity(), imagesList);
                gridView.setAdapter(gridAdapter);
                Toast.makeText(getActivity(), "Select 3 Images", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableButtonNext() {
        if (choosenImage.size()==3){
            nextImg.setEnabled(true);
            nextImg.setVisibility(View.VISIBLE);
        }
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
        Toast.makeText(getActivity(), "Please Wait Few Minute..", Toast.LENGTH_LONG).show();
        mProgressBar.setVisibility(View.VISIBLE);
        new ProgressTask().execute();
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
            mProgressBarText.setText("Coverting to video "+values[0]+"0%");

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

