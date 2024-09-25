package ca.cstjean.mobile.dames;

/**
 * Permet la représentation graphique du damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 * @author Reaven Riquoir
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
                    sb.append("-").append(damier.getPion((i * 5) + j).getRepresentation());
                } else {
                    sb.append(damier.getPion(((i * 5) - 5) + j).getRepresentation()).append("-");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
