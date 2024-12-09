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
public class TestDame extends TestPion {
    /**
     * Méthode pour créer une instance de dame.
     *
     * @param couleur La couleur du pion.
     * @return Une nouvelle instance Dame.
     */
    @Override
    protected Dame creerPion(Dame.Couleur couleur) {
        return new Dame(couleur);
    }
}
