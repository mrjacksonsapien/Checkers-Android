package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;

/**
 * Test la classe Damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestDamier extends TestCase {

    /**
     * Le damier.
     */
    private Damier damier;

    /**
     * Créer le damier avant de débuter les tests.
     */
    public void setUp() {
        damier = new Damier();
    }

    /**
     * Méthode pour tester la création du damier.
     */
    public void testCreer() {

        Pion pionBlanc = new Pion();
        damier.ajouterPion(38, pionBlanc);
        Assert.assertEquals(pionBlanc, damier.getPion(38));
        Assert.assertEquals(1, damier.getNbPions());

        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.ajouterPion(50, pionNoir);
        Assert.assertEquals(2, damier.getNbPions());
        Assert.assertNull(damier.getPion(-1));
    }

    /**
     * Méthode pour tester l'initialisation du damier.
     */
    public void testInitialiser() {
        damier.initialiser();

        Assert.assertEquals(40, damier.getNbPions());
    }

    public void testDeplacementValide() {
        damier.initialiser();
        Graphiques graph = new Graphiques();
        System.out.println(graph.getRepresentation(damier));
        damier.deplacerPion(31, 26);
        System.out.println(graph.getRepresentation(damier));
    }
}