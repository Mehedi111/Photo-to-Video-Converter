package android.cse.diu.mehedi.phototovideo.fragment_pac;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.cse.diu.mehedi.phototovideo.R;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePage extends Fragment {

    private Button buttonCreate;

    public CreatePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_page, container, false);

        buttonCreate = view.findViewById(R.id.createButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ///Loading Gallery Fragment
                Gallery gallery = new Gallery();
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, gallery);
                fragmentTransaction.commit();
            }
        });

        return view;
    }




}
