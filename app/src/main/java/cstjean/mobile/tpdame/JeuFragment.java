package cstjean.mobile.tpdame;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import cstjean.mobile.tpdame.pions.Dame;
import cstjean.mobile.tpdame.pions.Pion;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment de l'écran de jeu avec le damier.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class JeuFragment extends Fragment {
    /**
     * La position du pion actuellement sélectionné.
     */
    private Integer pionSelectionne;
    /**
     * Les mouvements possibles du pions actuellement sélectionné.
     */
    private List<Integer> mouvementsPossiblesPionSelectionne;
    /**
     * La logique du jeu.
     */
    private Jeu jeu;
    /**
     * Le rootView actuel.
     */
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            jeu = (Jeu) savedInstanceState.getSerializable("jeu");
        } else {
            jeu = new Jeu(new Damier(), false);
            jeu.getDamier().initialiser();
            jeu.commencer();
        }

        pionSelectionne = null;
        mouvementsPossiblesPionSelectionne = new ArrayList<>();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("jeu", jeu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ecran_jeu, container, false);

        rootView.findViewById(R.id.rewind).setOnClickListener(v -> retournerEnArriere());
        rootView.findViewById(R.id.backstack_pop).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager().popBackStack()
        );

        genererInterfaceDamier();
        rafraichirInterfaceDamier();

        Bundle arguments = getArguments();
        if (arguments != null) {
            String nomJoueur1 = arguments.getString("nomJoueur1");
            String nomJoueur2 = arguments.getString("nomJoueur2");

            TextView player1NameView = rootView.findViewById(R.id.plr1_name_game);
            player1NameView.setText(nomJoueur1);
            TextView player2NameView = rootView.findViewById(R.id.plr2_name_game);
            player2NameView.setText(nomJoueur2);
        }

        rafraichirInterfaceJoueurs();

        return rootView;
    }

    private void retournerEnArriere() {
        jeu.retournerEnArriere();
        if (pionSelectionne != null) {
            deselectionnerCase(pionSelectionne);
        }
        rafraichirInterfaceDamier();
        rafraichirInterfaceJoueurs();
    }

    private void genererInterfaceDamier() {
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i % 2 == 0) {
                    inflater.inflate(R.layout.case_blanche, rootView.findViewById(R.id.damier));
                    createCaseNoire((i * 5) + j, inflater);
                } else {
                    createCaseNoire((i * 5) + j, inflater);

                    inflater.inflate(R.layout.case_blanche, rootView.findViewById(R.id.damier));
                }
            }
        }
    }

    private void createCaseNoire(int position, LayoutInflater inflater) {
        GridLayout interfaceDamier = rootView.findViewById(R.id.damier);
        Button caseNoire = (Button) inflater.inflate(R.layout.case_noire, interfaceDamier, false);
        caseNoire.setId(position);
        caseNoire.setOnClickListener(v -> caseNoireOnClickListener(position));
        interfaceDamier.addView(caseNoire);
    }

    private void caseNoireOnClickListener(int position) {
        if (jeu.isEnCours()) {
            Pion pion = jeu.getDamier().getPion(position);

            if (pion != null) { // Pion est sélectionné
                boolean pionValide = (pion.getCouleur() == Pion.Couleur.BLANC && jeu.getTourJoueur1()) ||
                        (pion.getCouleur() == Pion.Couleur.NOIR && !jeu.getTourJoueur1());

                if (pionValide) { // Si le joueur sélectionne un de ses propres pions
                    selectionnerPion(position);
                }
            } else if (pionSelectionne != null && mouvementsPossiblesPionSelectionne.contains(position)) {
                // Destination est sélectionné pour pion actuel sélectionné
                jeu.deplacerPion(pionSelectionne, position);
                deselectionnerCase(pionSelectionne);
                rafraichirInterfaceDamier();
                rafraichirInterfaceJoueurs();
            }
        }
    }

    private void rafraichirInterfaceJoueurs() {
        Pion.Couleur couleur = jeu.estTerminee();

        int visibiliteTextTourJoueur1;
        int visibiliteTextTourJoueur2;
        int visibiliteBouttonBackStack;

        TextView textTourJoueur1 = rootView.findViewById(R.id.plr1_turn_text);
        TextView textTourJoueur2 = rootView.findViewById(R.id.plr2_turn_text);

        if (couleur != null) {
            String winMessage = "Vous avez gagné!";
            String loseMessage = "Vous avez perdu.";

            if (couleur == Pion.Couleur.BLANC) {
                textTourJoueur1.setText(winMessage);
                textTourJoueur2.setText(loseMessage);
            } else {
                textTourJoueur1.setText(loseMessage);
                textTourJoueur2.setText(winMessage);
            }

            visibiliteTextTourJoueur1 = View.VISIBLE;
            visibiliteTextTourJoueur2 = View.VISIBLE;
            visibiliteBouttonBackStack = View.VISIBLE;
        } else {
            boolean tourJoueur1 = jeu.getTourJoueur1();

            textTourJoueur1.setText(R.string.turn_text);
            textTourJoueur2.setText(R.string.turn_text);

            visibiliteTextTourJoueur1 = tourJoueur1 ? View.VISIBLE : View.GONE;
            visibiliteTextTourJoueur2 = tourJoueur1 ? View.GONE : View.VISIBLE;
            visibiliteBouttonBackStack = View.GONE;
        }

        textTourJoueur1.setVisibility(visibiliteTextTourJoueur1);
        textTourJoueur2.setVisibility(visibiliteTextTourJoueur2);
        rootView.findViewById(R.id.backstack_pop).setVisibility(visibiliteBouttonBackStack);
        rootView.findViewById(R.id.backstackpop_button_desc).setVisibility(visibiliteBouttonBackStack);

        TextView viewHistorique1 = rootView.findViewById(R.id.history1);
        TextView viewHistorique2 = rootView.findViewById(R.id.history2);

        List<String> historique = jeu.getDamier().getHistoriqueAsList();

        viewHistorique1.setText(R.string.history_content_empty);
        viewHistorique2.setText(R.string.history_content_empty);

        if (!historique.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < historique.size(); i++) {
                if (i < 5) {
                    stringBuilder.append(historique.get(i)).append("\n");
                } else {
                    stringBuilder.append("...\n");
                    break;
                }
            }

            viewHistorique1.setText(stringBuilder.toString());
            viewHistorique2.setText(stringBuilder.toString());
        }
    }

    private void deselectionnerCase(int position) {
        Button bouttonSelectionne = rootView.findViewById(position);
        effacerMouvementsPossibles();
        bouttonSelectionne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C58C54")));
        pionSelectionne = null;
        mouvementsPossiblesPionSelectionne.clear();
    }

    private void selectionnerPion(int position) {
        // Affichage deselection pion precedemment selectionne
        if (pionSelectionne != null) {
            deselectionnerCase(pionSelectionne);
        }

        // Set nouvelles valeures pour pion selectionne
        pionSelectionne = position;
        mouvementsPossiblesPionSelectionne = jeu.getDamier().deplacements(position);

        // Affichage selection pour pion selectionne
        Button bouttonSelectionne = rootView.findViewById(position);
        bouttonSelectionne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#b7ff00")));
        afficherMouvementsPossibles();
    }

    private void afficherMouvementsPossibles() {
        for (Integer positionPossible : mouvementsPossiblesPionSelectionne) {
            Button boutton = rootView.findViewById(positionPossible);
            boutton.setForeground(
                    ContextCompat.getDrawable(rootView.getContext(), R.drawable.possibilite_case)
            );
        }
    }

    private void effacerMouvementsPossibles() {
        for (Integer positionPossible : mouvementsPossiblesPionSelectionne) {
            Button boutton = rootView.findViewById(positionPossible);
            boutton.setForeground(null);
        }
    }

    private void rafraichirInterfaceDamier() {
        for (int i = 1; i <= jeu.getDamier().getCasesClone().length; i++) {
            Button boutton = rootView.findViewById(i);
            Pion pion = jeu.getDamier().getPion(i);

            if (pion != null) {
                int foreground;
                if (pion instanceof Dame) {
                    foreground = pion.getCouleur() == Pion.Couleur.NOIR ? R.drawable.dame_noire :
                            R.drawable.dame_blanche;
                } else {
                    foreground = pion.getCouleur() == Pion.Couleur.NOIR ? R.drawable.pion_noir :
                            R.drawable.pion_blanc;
                }
                boutton.setForeground(ContextCompat.getDrawable(rootView.getContext(), foreground));
            } else {
                boutton.setForeground(null);
            }
        }
    }
}