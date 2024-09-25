package ca.cstjean.mobile.dames;

import junit.framework.TestCase;
import org.junit.Before;

public class TestConsole extends TestCase {
    private Damier damier;
    private Graphiques graphiques;

    @Before
    public void setUp() {
        damier = new Damier();
        graphiques = new Graphiques();
    }

    public void testAffichage() {
        damier.initialiser();

        String representation = graphiques.getRepresentation(damier);

        assertEquals("-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "-P-P-P-P-P\n" +
                "P-P-P-P-P-\n" +
                "----------\n" +
                "----------\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n" +
                "-p-p-p-p-p\n" +
                "p-p-p-p-p-\n", representation);

        System.out.println(representation);
    }
}
