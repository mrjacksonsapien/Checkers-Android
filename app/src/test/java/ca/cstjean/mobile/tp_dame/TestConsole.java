package ca.cstjean.mobile.tp_dame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test la classe Graphique.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestConsole {
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
    @Before
    public void setUp() {
        damier = new Damier();
        graphiques = new Graphiques();
    }

    /**
     * Méthode testant l'affichage du damier.
     */
    @Test
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
