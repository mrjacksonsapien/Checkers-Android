package cstjean.mobile.tpdame;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
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
public class FragmentEcranJeu extends Fragment {
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

        if (savedInstanceState != null) {
            jeu = (Jeu) savedInstanceState.getSerializable("jeu");
        } else {
            jeu = new Jeu(new Damier(), false);
            jeu.getDamier().initialiser();
            jeu.commencer();
        }

        pionSelectionne = null;
        mouvementsPossiblesPionSelectionne = new ArrayList<>();

        genererInterfaceDamier();
        rafraichirInterfaceDamier();

        rootView.findViewById(R.id.rewind).setOnClickListener(v -> {
            jeu.retournerEnArriere();
            if (pionSelectionne != null) {
                deselectionnerCase(pionSelectionne);
            }
            rafraichirInterfaceDamier();
        });

        return rootView;
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

        interfaceDamier.addView(caseNoire);

        // Fonction clique case noire
        caseNoire.setOnClickListener(v -> {
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

                if (jeu.estTerminee()) {
                    Toast.makeText(rootView.getContext(), "Fini!", Toast.LENGTH_LONG).show();
                }
            }
        });
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
                if (pion instanceof Dame) {
                    if (pion.getCouleur() == Pion.Couleur.NOIR) {
                        boutton.setForeground(
                                ContextCompat.getDrawable(rootView.getContext(), R.drawable.dame_noire)
                        );
                    } else {
                        boutton.setForeground(
                                ContextCompat.getDrawable(rootView.getContext(), R.drawable.dame_blanche)
                        );
                    }
                } else {
                    if (pion.getCouleur() == Pion.Couleur.NOIR) {
                        boutton.setForeground(
                                ContextCompat.getDrawable(rootView.getContext(), R.drawable.pion_noir)
                        );
                    } else {
                        boutton.setForeground(
                                ContextCompat.getDrawable(rootView.getContext(), R.drawable.pion_blanc)
                        );
                    }
                }
            } else {
                boutton.setForeground(null);
            }
        }
    }
}