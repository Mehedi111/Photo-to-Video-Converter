package android.cse.diu.mehedi.phototovideo.activity;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.cse.diu.mehedi.phototovideo.R;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.cse.diu.mehedi.phototovideo.fragment_pac.CreatePage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        CreatePage createPage = new CreatePage();
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, createPage);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void checkPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED ){
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
}
