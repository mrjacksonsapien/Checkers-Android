package ca.cstjean.mobile.dames.pions;

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

    public Dame() {
        super();
    }

    public Dame(Couleur couleur) {
        super(couleur);
    }
}
