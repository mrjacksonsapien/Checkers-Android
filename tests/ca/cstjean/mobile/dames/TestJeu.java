package ca.cstjean.mobile.dames;

import junit.framework.TestCase;

/**
 * Classe de test du jeu.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestJeu extends TestCase {
    /**
     * Le jeu.
     */
    private Jeu jeu;

    /**
     * Méthode à effectuer avant les testes.
     */
    public void setUp() {
        Damier damier = new Damier();
        jeu = new Jeu(damier);
        jeu.commencer();
    }

    /**
     * Méthode testant la position adéquate.
     */
    public void testPositionAdequate() {
        assertTrue(jeu.damierEstAdequat());
    }
}
