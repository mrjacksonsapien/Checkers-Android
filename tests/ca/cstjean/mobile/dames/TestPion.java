package ca.cstjean.mobile.dames;

import junit.framework.TestCase;

/**
 * Test la classe Pion.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestPion extends TestCase {
    /**
     * Test la valeur de la couleur assigné aux pions.
     */
    public void testCreer() {
        Pion pionNoir = new Pion("Noir");
        Pion pionBlanc = new Pion();

        assertEquals("Noir", pionNoir.getCouleur());
        assertEquals("Blanc", pionBlanc.getCouleur());
    }
}
