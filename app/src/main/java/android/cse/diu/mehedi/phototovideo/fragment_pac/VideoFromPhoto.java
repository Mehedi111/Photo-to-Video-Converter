package android.cse.diu.mehedi.phototovideo.fragment_pac;




import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.cse.diu.mehedi.phototovideo.R;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFromPhoto extends Fragment {
    private VideoView videoView;
    private Button saveVideo, createNew;


    public VideoFromPhoto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video_from_photo, container, false);

        videoView = v.findViewById(R.id.videoView);
        saveVideo = v.findViewById(R.id.saveBtn);
        createNew = v.findViewById(R.id.createNew);

        videoPlay();

        saveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Video is saved", Toast.LENGTH_SHORT).show();
            }
        });

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File myDir = new File(Environment.getExternalStorageDirectory()+"/"+"test.mp4");
                myDir.delete();
                CreatePage createPage = new CreatePage();
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, createPage);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    public void videoPlay() {
        String videoPath = Environment.getExternalStorageDirectory() + "/test.mp4";
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();
    }
}