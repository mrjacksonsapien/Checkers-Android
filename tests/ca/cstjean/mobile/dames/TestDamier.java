package ca.cstjean.mobile.dames;

import junit.framework.TestCase;

/**
 * Test la classe Damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestDamier extends TestCase {
    /**
     * Test les méthodes du damier.
     */
    public void testCreer() {
        Damier damier = new Damier();

        Pion pionBlanc = new Pion();
        damier.ajouterPion(38, pionBlanc);
        assertEquals(pionBlanc, damier.getPion(38));
        assertEquals(1, damier.getNbPions());

        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.ajouterPion(50, pionNoir);
        assertEquals(2, damier.getNbPions());
    }
}
