package cstjean.mobile.tpdame;

import static org.junit.Assert.assertEquals;

import cstjean.mobile.tpdame.pions.Pion;
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
        Pion pionNoir = creerPion(Pion.Couleur.NOIR);
        Pion pionBlanc = creerPion(Pion.Couleur.BLANC);

        assertEquals(Pion.Couleur.NOIR, pionNoir.getCouleur());
        assertEquals(Pion.Couleur.BLANC, pionBlanc.getCouleur());
    }

    /**
     * Méthode pour créer une instance de pion.
     *
     * @param couleur La couleur du pion.
     * @return Une nouvelle instance Pion.
     */
    protected Pion creerPion(Pion.Couleur couleur) {
        return new Pion(couleur);
    }
}
