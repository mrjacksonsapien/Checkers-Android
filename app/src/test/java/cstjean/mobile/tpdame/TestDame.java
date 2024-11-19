package cstjean.mobile.tpdame;

import static org.junit.Assert.assertEquals;

import cstjean.mobile.tpdame.pions.Dame;
import cstjean.mobile.tpdame.pions.Pion;
import org.junit.Test;

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
