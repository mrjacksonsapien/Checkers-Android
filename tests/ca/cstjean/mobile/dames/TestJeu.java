package ca.cstjean.mobile.dames;

import junit.framework.TestCase;
import org.junit.Before;

public class TestJeu extends TestCase {
    private Jeu jeu;

    @Before
    public void setUp() {
        Damier damier = new Damier();
        jeu = new Jeu(damier);
        jeu.commencer();
    }

    public void testPositionAdequate() {
        assertTrue(jeu.damierEstAdequat());
    }

    public void testTourJoueur() {
        assertTrue(jeu.getTourJoueur());
        jeu.changerTour();
        assertFalse(jeu.getTourJoueur());
    }
}
