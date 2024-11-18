package ca.cstjean.mobile.tp_dame;

import static org.junit.Assert.assertEquals;

import ca.cstjean.mobile.tp_dame.pions.Pion;
import org.junit.Test;

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
