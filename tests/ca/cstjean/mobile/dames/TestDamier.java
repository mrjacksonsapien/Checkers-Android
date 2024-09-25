package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test la classe Damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 * @author Reaven Riquoir
 */
public class TestDamier {

    /**
     * Le damier.
     */
    private Damier damier;

    /**
     * Créer le damier avant de débuter les tests.
     */
    @Before
    public void setUp() {
        damier = new Damier();
    }

    @Test
    public void testCreer() {

        Pion pionBlanc = new Pion();
        damier.ajouterPion(38, pionBlanc);
        Assert.assertEquals(pionBlanc, damier.getPion(38));
        Assert.assertEquals(1, damier.getNbPions());

        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.ajouterPion(50, pionNoir);
        Assert.assertEquals(2, damier.getNbPions());

        Assert.assertEquals(Pion.Couleur.NULL, damier.getPion(1).getCouleur());

        Assert.assertNull(damier.getPion(-1));
    }

    @Test
    public void testInitialiser() {
        damier.initialiser();

        Assert.assertEquals(40, damier.getNbPions());
    }
}
