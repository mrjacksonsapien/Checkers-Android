package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Test la classe Graphique.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestConsole extends TestCase {

    /**
     * Le damier.
     */
    private Damier damier;

    /**
     * La représentation graphique du damier.
     */
    private Graphiques graphiques;

    /**
     * Créer le damier avant de débuter les tests.
     */
    public void setUp() {
        damier = new Damier();
        graphiques = new Graphiques();
    }

    /**
     * Méthode testant l'affichage du damier.
     */
    public void testAffichage() {
        damier.initialiser();

        String representation = graphiques.getRepresentation(damier);

        Assert.assertEquals("-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "----------\n" +
                "----------\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n", representation);

        System.out.println(representation);
    }
}
