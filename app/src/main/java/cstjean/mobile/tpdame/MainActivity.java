package cstjean.mobile.tpdame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * L'activité principal de l'application.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AccueilFragment())
                .commit();
    }
}