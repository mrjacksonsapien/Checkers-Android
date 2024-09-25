package ca.cstjean.mobile.dames;

import ca.cstjean.mobile.dames.pions.Dame;
import ca.cstjean.mobile.dames.pions.Pion;
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
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        Pion pionBlanc = new Pion();
        Dame dameNoire = new Dame(Pion.Couleur.NOIR);
        Dame dameBlanche = new Dame();

        assertEquals(Pion.Couleur.NOIR, pionNoir.getCouleur());
        assertEquals(Pion.Couleur.BLANC, pionBlanc.getCouleur());
        assertEquals('P', pionNoir.getRepresentation());
        assertEquals('p', pionBlanc.getRepresentation());

        assertEquals(Pion.Couleur.NOIR, dameNoire.getCouleur());
        assertEquals(Pion.Couleur.BLANC, dameBlanche.getCouleur());
        assertEquals('D', dameNoire.getRepresentation());
        assertEquals('d', dameBlanche.getRepresentation());
    }
}
