package ca.cstjean.mobile.dames;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import ca.cstjean.mobile.dames.pions.Dame;
import ca.cstjean.mobile.dames.pions.Pion;
import org.junit.Before;
import org.junit.Test;

/**
 * Test la classe Damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestDamier {

    /**
     * Le damier.
     */
    private Damier damier;

    /**
     * Créer le damier avant de débuter les tests.
     */
    @Before
    public void setUp() {
        damier = new Damier();
    }

    /**
     * Méthode pour tester la création du damier.
     */
    @Test
    public void testCreer() {
        Pion pionBlanc = new Pion();
        damier.ajouterPion(38, pionBlanc);
        assertEquals(pionBlanc, damier.getPion(38));
        assertEquals(1, damier.getNbPions());

        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.ajouterPion(50, pionNoir);
        assertEquals(2, damier.getNbPions());
        assertNull(damier.getPion(-1));
    }

    /**
     * Méthode pour tester l'initialisation du damier.
     */
    @Test
    public void testInitialiser() {
        damier.initialiser();
        assertEquals(40, damier.getNbPions());
    }

    /**
     * Méthode pour tester le déplacement valide d'un pion.
     */
    @Test
    public void testDeplacementValidePion() {
        Graphiques graphiques = new Graphiques();
        damier.initialiser();
        System.out.println(graphiques.getRepresentation(damier));
        damier.deplacerPion(17, 22);
        System.out.println(graphiques.getRepresentation(damier));
    }

    /**
     * Méthode pour tester un déplacement non valide d'une dame.
     */
    @Test
    public void testDeplacementInvalideDame() {
        Graphiques graphiques = new Graphiques();
        Dame dame = new Dame();
        damier.ajouterPion(23, dame);
        damier.deplacerPion(23, 37);
        System.out.println(graphiques.getRepresentation(damier));
        damier.ajouterPion(46, new Pion());
        try {
            damier.deplacerPion(37, 46);
        } catch (Exception e) {
            assertEquals(dame, damier.getPion(37));
        }

        try {
            damier.deplacerPion(37, 51);
        } catch (Exception e) {
            assertEquals(dame, damier.getPion(37));
        }
    }

    /**
     * Méthode pour tester une prise valide.
     */
    @Test
    public void testPriseValide() {
        Graphiques graphiques = new Graphiques();
        damier.ajouterPion(23, new Pion(Pion.Couleur.NOIR));
        damier.ajouterPion(28, new Pion());

        System.out.println(graphiques.getRepresentation(damier));
        damier.deplacerPion(23, 32);
        System.out.println(graphiques.getRepresentation(damier));
    }

    /**
     * Méthode pour tester la promotion d'un pion blanc en dame.
     */
    @Test
    public void testPromotionEnDameBlanc() {
        Graphiques graphiques = new Graphiques();
        damier.ajouterPion(10, new Pion());
        System.out.println(graphiques.getRepresentation(damier));
        assertThrows(NullPointerException.class, () -> {
            damier.deplacerPion(4, 10);
        });
        damier.deplacerPion(10, 4);
        System.out.println(graphiques.getRepresentation(damier));
    }

    /**
     * Méthode pour tester la promotion d'un pion noir en dame.
     */
    @Test
    public void testPromotionEnDameNoire() {
        Graphiques graphiques = new Graphiques();
        damier.ajouterPion(44, new Pion(Pion.Couleur.NOIR));
        System.out.println(graphiques.getRepresentation(damier));
        assertThrows(NullPointerException.class, () -> {
            damier.deplacerPion(50, 44);
        });
        damier.deplacerPion(44, 50);
        System.out.println(graphiques.getRepresentation(damier));
    }

    /**
     * Premier test sur le retour en arrière utilisant l'historique des déplacements.
     */
    @Test
    public void testRetourEnArriere1() {
        Graphiques graphiques = new Graphiques();
        damier.initialiser();
        System.out.println(graphiques.getRepresentation(damier));

        damier.deplacerPion(31, 26);
        System.out.println(graphiques.getRepresentation(damier));
        damier.deplacerPion(26, 21);
        System.out.println(graphiques.getRepresentation(damier));

        damier.retournerEnArriere();
        System.out.println(graphiques.getRepresentation(damier));

        damier.retournerEnArriere();
        System.out.println(graphiques.getRepresentation(damier));
    }

    /**
     * Deuxième test sur le retour en arrière utilisant l'historique des déplacements.
     */
    @Test
    public void testRetourEnArriere2() {
        Pion pionBlanc = new Pion();
        damier.ajouterPion(27, pionBlanc);
        Pion pionNoir = new Pion(Pion.Couleur.NOIR);
        damier.ajouterPion(22, pionNoir);
        damier.deplacerPion(27, 18);

        assertNull(damier.getPion(22));
        assertNull(damier.getPion(27));
        assertEquals(pionBlanc, damier.getPion(18));

        damier.retournerEnArriere();

        assertEquals(pionNoir, damier.getPion(22));
        assertEquals(pionBlanc, damier.getPion(27));
    }

    /**
     * Méthode pour tester la position d'un pion.
     */
    @Test
    public void testPosition() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            damier.verifiePositionPion(51);
        });
    }
}