package cstjean.mobile.tpdame;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.os.RemoteException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Classe de test du fragment AccueilFragment.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
@RunWith(AndroidJUnit4.class)
public class TestAccueilFragment {
    /**
     * Règle pour lancer l'activité MainActivity.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Méthode testant la saisie des noms.
     */
    @Test
    public void testSaisieNoms() {
        onView(withId(R.id.start)).check(matches(isNotEnabled()));

        onView(withId(R.id.plr1_name_menu)).perform(typeText("Player 1"));
        onView(withId(R.id.plr2_name_menu)).perform(typeText("Player 2"));

        onView(withId(R.id.start)).check(matches(isEnabled()));
    }

    /**
     * Méthode testant la saisie des noms avec rotation.
     */
    @Test
    public void testSaisieNomAvecRotation() throws RemoteException {
        onView(withId(R.id.start)).check(matches(isNotEnabled()));

        onView(withId(R.id.plr1_name_menu)).perform(typeText("Player 1"));

        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.setOrientationLeft();

        onView(withId(R.id.plr2_name_menu)).perform(typeText("Player 2"));

        onView(withId(R.id.start)).check(matches(isEnabled()));
    }
}