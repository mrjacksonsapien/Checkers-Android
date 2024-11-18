package ca.cstjean.mobile.tp_dame.pions;

/**
 * Classe de dames.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Dame extends Pion {
    @Override
    public char getRepresentation() {
        char caractere;

        if (getCouleur() == Couleur.NOIR) {
            caractere = 'D';
        } else {
            caractere = 'd';
        }

        return caractere;
    }

    /**
     * Constructeur par défaut.
     */
    public Dame() {
        super();
    }

    /**
     * Constructeur de dame.
     *
     * @param couleur Couleur de la dame.
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }
}
