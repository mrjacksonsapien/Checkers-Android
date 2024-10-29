package ca.cstjean.mobile.dames;

import static org.junit.Assert.assertThrows;

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
     * Méthode pour tester si un pion est au milieu lors de l'initialisation du damier.
     */
    public void testPositionMilieuInadequate() {
        jeu.getDamier().initialiser();
        assertTrue(jeu.damierEstAdequat());
        jeu.getDamier().ajouterPion(27, new Pion());
        assertFalse(jeu.damierEstAdequat());
    }

    /**
     * Méthode pour tester si un pion noir est initialiser du côté blanc.
     */
    public void testPositionBlancInadequate() {
        jeu.getDamier().initialiser();
        assertTrue(jeu.damierEstAdequat());
        jeu.getDamier().ajouterPion(47, new Pion(Pion.Couleur.NOIR));
        assertFalse(jeu.damierEstAdequat());
    }

    /**
     * Méthode de test débutant avec un damier adéquat pour vérifier que la partie n'est pas terminée.
     */
    public void testPartieNonTerminee() {
        jeu.getDamier().initialiser();
        jeu.commencer();
        assertFalse(jeu.estTerminee());
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

    /**
     * Méthode pour tester les exceptions du déplacement des pions.
     */
    public void testDeplacerPionException() {
        jeu.getDamier().initialiser();
        jeu.commencer();

        assertThrows(NullPointerException.class, () -> {
            jeu.deplacerPion(27, 21);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            jeu.deplacerPion(18, 22);
        });
    }

    /**
     * Méthode pour tester le changement de tour.
     */
    public void testChangementTourJoueur() {
        jeu.getDamier().initialiser();
        jeu.commencer();
        jeu.getDamier().ajouterPion(33, new Pion(Pion.Couleur.BLANC));
        boolean tourAvantDeplacement = jeu.getTourJoueur1();
        jeu.deplacerPion(33, 29);
        assertEquals(tourAvantDeplacement, !jeu.getTourJoueur1());
    }
}
