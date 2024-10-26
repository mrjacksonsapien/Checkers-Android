package ca.cstjean.mobile.dames;

import junit.framework.TestSuite;

/**
 * Regroupe tous les tests dans une suite.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class TestComplet {
    /**
     * Suite qui contient tous les tests.
     *
     * @return La suite des tests.
     */
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestPion.class);
        suite.addTestSuite(TestDamier.class);
        suite.addTestSuite(TestConsole.class);
        suite.addTestSuite(TestDame.class);
        suite.addTestSuite(TestJeu.class);
        return suite;
    }
}
