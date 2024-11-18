package ca.cstjean.mobile.dames;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.cstjean.mobile.dames.pions.Dame;
import ca.cstjean.mobile.dames.pions.Pion;

/**
 * Test la classe Dame.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestDame {
    /**
     * Test les dames.
     */
    @Test
    public void testDame() {
        Dame dameNoire = new Dame(Pion.Couleur.NOIR);
        Dame dameBlanche = new Dame();

        assertEquals(Pion.Couleur.NOIR, dameNoire.getCouleur());
        assertEquals(Pion.Couleur.BLANC, dameBlanche.getCouleur());
        assertEquals('D', dameNoire.getRepresentation());
        assertEquals('d', dameBlanche.getRepresentation());
    }
}
