package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Pion;
import junit.framework.TestCase;
import org.junit.Before;

/**
 * Test la classe Damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestDamier extends TestCase {
    private Damier damier;

    @Before
    public void setUp() {
        damier = new Damier();
    }

    public void testCreer() {

        Pion pionBlanc = new Pion();
        damier.ajouterPion(38, pionBlanc);
        assertEquals(pionBlanc, damier.getPion(38));
        assertEquals(1, damier.getNbPions());

        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.ajouterPion(50, pionNoir);
        assertEquals(2, damier.getNbPions());

        assertEquals(Pion.Couleur.NULL, damier.getPion(1).getCouleur());

        assertNull(damier.getPion(-1));
    }

    public void testInitialiser() {
        damier.initialiser();

        assertEquals(40, damier.getNbPions());
    }
}
