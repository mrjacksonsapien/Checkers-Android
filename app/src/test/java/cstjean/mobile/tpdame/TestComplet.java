package cstjean.mobile.tpdame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Regroupe tous les tests dans une suite.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
    TestConsole.class,
    TestDame.class,
    TestDamier.class,
    TestJeu.class,
    TestPion.class
})
public class TestComplet {
}