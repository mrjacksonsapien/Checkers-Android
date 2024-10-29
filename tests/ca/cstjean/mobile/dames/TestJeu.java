package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;
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
    }

    /**
     * Méthode testant la position adéquate.
     */
    public void testPositionAdequate() {
        jeu.getDamier().initialiser();
        assertTrue(jeu.damierEstAdequat());
    }

    /**
     * Méthode testant une position inadéquate.
     */
    public void testPositionInadequate() {
        jeu.commencer();
        assertFalse(jeu.damierEstAdequat());
    }

    /**
     * Méthode testant le déplacement d'un pion.
     */
    public void testDeplacerPion() {
        jeu.getDamier().initialiser();
        jeu.deplacerPion(31, 26);
        assertNull(jeu.getDamier().getPion(26));
    }

    /**
     * Méthode pour tester si la partie est terminée.
     */
    public void testPartieTerminee() {
        jeu.getDamier().ajouterPion(21, new Pion());
        assertTrue(jeu.estTerminee());

    }
}
