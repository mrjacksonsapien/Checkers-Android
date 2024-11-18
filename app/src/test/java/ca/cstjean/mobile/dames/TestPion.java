package ca.cstjean.mobile.dames;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.cstjean.mobile.dames.pions.Pion;

/**
 * Test la classe Pion.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestPion {
    /**
     * Test la valeur de la couleur assigné aux pions.
     */
    @Test
    public void testCreer() {
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        Pion pionBlanc = new Pion();

        assertEquals(Pion.Couleur.NOIR, pionNoir.getCouleur());
        assertEquals(Pion.Couleur.BLANC, pionBlanc.getCouleur());
        assertEquals('P', pionNoir.getRepresentation());
        assertEquals('p', pionBlanc.getRepresentation());
    }
}
