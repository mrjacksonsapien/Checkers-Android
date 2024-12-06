package cstjean.mobile.tpdame;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.io.Serializable;

/**
 * L'activité principal de l'application.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class MainActivity extends AppCompatActivity {
    private Serializable currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getSerializable("currentFragment");
        } else {
            currentFragment = new AccueilFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, (Fragment) currentFragment)
                .commit();
    }

    public void setCurrentFragment(Serializable fragment) {
        currentFragment = fragment;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("currentFragment", currentFragment);
    }
}