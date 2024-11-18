package ca.cstjean.mobile.tp_dame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import ca.cstjean.mobile.tp_dame.pions.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test du jeu.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestJeu {
    /**
     * Le jeu.
     */
    private Jeu jeu;

    /**
     * Méthode à effectuer avant les testes.
     */
    @Before
    public void setUp() {
        Damier damier = new Damier();
        jeu = new Jeu(damier, true);
    }

    /**
     * Méthode testant la position adéquate.
     */
    @Test
    public void testPositionAdequate() {
        jeu.getDamier().initialiser();
        assertTrue(jeu.damierEstAdequat());
    }

    /**
     * Méthode testant une position inadéquate.
     */
    @Test
    public void testPositionInadequate() {
        jeu.commencer();
        assertFalse(jeu.damierEstAdequat());
    }

    /**
     * Méthode pour tester si un pion est au milieu lors de l'initialisation du damier.
     */
    @Test
    public void testPositionMilieuInadequate() {
        jeu.getDamier().initialiser();
        assertTrue(jeu.damierEstAdequat());
        jeu.getDamier().ajouterPion(27, new Pion());
        assertFalse(jeu.damierEstAdequat());
    }

    /**
     * Méthode pour tester si un pion noir est initialiser du côté blanc.
     */
    @Test
    public void testPositionBlancInadequate() {
        jeu.getDamier().initialiser();
        assertTrue(jeu.damierEstAdequat());
        jeu.getDamier().ajouterPion(47, new Pion(Pion.Couleur.NOIR));
        assertFalse(jeu.damierEstAdequat());
    }

    /**
     * Méthode de test débutant avec un damier adéquat pour vérifier que la partie n'est pas terminée.
     */
    @Test
    public void testPartieNonTerminee() {
        jeu.getDamier().initialiser();
        jeu.commencer();
        assertFalse(jeu.estTerminee());
    }

    /**
     * Méthode testant le déplacement d'un pion.
     */
    @Test
    public void testDeplacerPion() {
        jeu.getDamier().initialiser();
        jeu.deplacerPion(31, 26);
        assertNull(jeu.getDamier().getPion(26));
    }

    /**
     * Méthode pour tester si la partie est terminée.
     */
    @Test
    public void testPartieTerminee() {
        jeu.getDamier().ajouterPion(21, new Pion());
        assertTrue(jeu.estTerminee());
    }

    /**
     * Méthode pour tester les exceptions du déplacement des pions.
     */
    @Test
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
    @Test
    public void testChangementTourJoueur() {
        jeu.getDamier().initialiser();
        jeu.commencer();
        jeu.getDamier().ajouterPion(33, new Pion(Pion.Couleur.BLANC));
        boolean tourAvantDeplacement = jeu.getTourJoueur1();
        jeu.deplacerPion(33, 29);
        assertEquals(tourAvantDeplacement, !jeu.getTourJoueur1());
    }

    /**
     * Méthode pour tester quand la partie est terminé.
     */
    @Test
    public void testTermine() {
        assertTrue(jeu.estTerminee());
        jeu.getDamier().ajouterPion(48, new Pion());
        jeu.commencer();
        jeu.deplacerPion(48, 42);
        assertTrue(jeu.estTerminee());
    }
}
