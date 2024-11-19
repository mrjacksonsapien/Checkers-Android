package cstjean.mobile.tpdame;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cstjean.mobile.tpdame.pions.Dame;
import cstjean.mobile.tpdame.pions.Pion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEcranJeu} factory method to
 * create an instance of this fragment.
 */
public class FragmentEcranJeu extends Fragment {
    private Integer pionSelectionne;
    private List<Integer> mouvementsPossiblesPionSelectionne;
    private Jeu jeu;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ecran_jeu, container, false);
        GridLayout interfaceDamier = view.findViewById(R.id.damier);

        jeu = new Jeu(new Damier(), false);
        jeu.getDamier().initialiser();

        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i % 2 == 0) {
                    inflater.inflate(R.layout.case_blanche, interfaceDamier);
                    createCaseNoire(interfaceDamier, (i * 5) + j, inflater);
                } else {
                    createCaseNoire(interfaceDamier, (i * 5) + j, inflater);

                    inflater.inflate(R.layout.case_blanche, interfaceDamier);
                }
            }
        }

        rafraichirDamier();
        pionSelectionne = null;
        mouvementsPossiblesPionSelectionne = new ArrayList<>();

        jeu.commencer();

        return view;
    }

    private void createCaseNoire(GridLayout interfaceDamier, int position, LayoutInflater inflater) {
        Button caseNoire = (Button) inflater.inflate(R.layout.case_noire, interfaceDamier, false);
        caseNoire.setId(position);
        interfaceDamier.addView(caseNoire);

        caseNoire.setOnClickListener(v -> {
            Pion pion = jeu.getDamier().getPion(position);

            if (pion != null) {
                boolean pionValide = (pion.getCouleur() == Pion.Couleur.BLANC && jeu.getTourJoueur1()) ||
                        (pion.getCouleur() == Pion.Couleur.NOIR && !jeu.getTourJoueur1());
                if (pionValide) {
                    selectionnerCase(position);
                }
            } else if (pionSelectionne != null && mouvementsPossiblesPionSelectionne.contains(position)) {
                jeu.deplacerPion(pionSelectionne, position);
                rafraichirDamier();
                deselectionnerCase(pionSelectionne);
            }
        });
    }

    private void deselectionnerCase(int position) {
        Button bouttonSelectionne = view.findViewById(position);
        bouttonSelectionne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C58C54")));
    }

    private void selectionnerCase(int position) {
        if (pionSelectionne != null) {
            deselectionnerCase(pionSelectionne);
        }

        Button bouttonSelectionne = view.findViewById(position);
        bouttonSelectionne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#b7ff00")));
        pionSelectionne = position;
        mouvementsPossibles(position, true);
    }

    private void mouvementsPossibles(int position, boolean montrer) {
        mouvementsPossiblesPionSelectionne = jeu.getDamier().deplacements(position);

        for (Integer positionPossible : mouvementsPossiblesPionSelectionne) {
            Button boutton = view.findViewById(positionPossible);
            if (montrer) {
                boutton.setForeground(
                        ContextCompat.getDrawable(view.getContext(), R.drawable.possibilite_case)
                );
            } else {
                boutton.setForeground(null);
            }
        }
    }

    private void rafraichirDamier() {
        for (int i = 1; i <= jeu.getDamier().getCasesClone().length; i++) {
            Button boutton = view.findViewById(i);
            Pion pion = jeu.getDamier().getPion(i);

            if (pion != null) {
                if (pion instanceof Dame) {
                    if (pion.getCouleur() == Pion.Couleur.NOIR) {
                        boutton.setForeground(
                                ContextCompat.getDrawable(view.getContext(), R.drawable.dame_noire)
                        );
                    } else {
                        boutton.setForeground(
                                ContextCompat.getDrawable(view.getContext(), R.drawable.dame_blanche)
                        );
                    }
                } else {
                    if (pion.getCouleur() == Pion.Couleur.NOIR) {
                        boutton.setForeground(
                                ContextCompat.getDrawable(view.getContext(), R.drawable.pion_noir)
                        );
                    } else {
                        boutton.setForeground(
                                ContextCompat.getDrawable(view.getContext(), R.drawable.pion_blanc)
                        );
                    }
                }
            } else {
                boutton.setForeground(null);
            }
        }
    }
}