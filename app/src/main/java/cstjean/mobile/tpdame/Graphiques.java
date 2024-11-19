package cstjean.mobile.tpdame;

import cstjean.mobile.tpdame.pions.Pion;

/**
 * Permet la représentation graphique du damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class Graphiques {

    /**
     * Fait la représentation graphique du damier.
     *
     * @param damier Damier.
     * @return Une string réprésentant le damier avec les pions.
     */
    public String getRepresentation(Damier damier) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i % 2 == 0) {
                    sb.append("-").append(getRepresentationPion(damier, (i * 5) + j));
                } else {
                    sb.append(getRepresentationPion(damier, (i * 5) + j)).append("-");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private char getRepresentationPion(Damier damier, int position) {
        Pion pion = damier.getPion(position);
        char representation;

        if (pion != null) {
            representation = pion.getRepresentation();
        } else {
            representation = '-';
        }

        return representation;
    }
}
