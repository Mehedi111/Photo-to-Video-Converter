package android.cse.diu.mehedi.phototovideo;


import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.cse.diu.mehedi.phototovideo.fragment_pac.CreatePage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///Loading Fragment
       /* CreatePage createPage = new CreatePage();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, createPage);
        fragmentTransaction.commit();*/
        CreatePage createPage = new CreatePage();
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, createPage);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
