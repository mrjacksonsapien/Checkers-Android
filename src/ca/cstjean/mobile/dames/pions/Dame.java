package ca.cstjean.mobile.dames.pions;

/**
 * Classe de dames.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 * @author Reaven Riquoir
 */
public class Dame extends Pion {
    @Override
    public char getRepresentation() {
        char caractere;

        switch (getCouleur()) {
            case NOIR -> caractere = 'D';
            case BLANC -> caractere = 'd';
            default -> caractere = '-';
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
