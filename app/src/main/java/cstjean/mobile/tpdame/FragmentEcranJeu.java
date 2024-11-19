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
import cstjean.mobile.tpdame.pions.Dame;
import cstjean.mobile.tpdame.pions.Pion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEcranJeu} factory method to
 * create an instance of this fragment.
 */
public class FragmentEcranJeu extends Fragment {
    private Integer pionSelectionne;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecran_jeu, container, false);
        GridLayout interfaceDamier = view.findViewById(R.id.damier);

        Jeu jeu = new Jeu(new Damier(), false);
        jeu.getDamier().initialiser();

        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i % 2 == 0) {
                    inflater.inflate(R.layout.case_blanche, interfaceDamier);
                    createCaseNoire(interfaceDamier, (i * 5) + j, inflater, jeu.getDamier());
                } else {
                    createCaseNoire(interfaceDamier, (i * 5) + j, inflater, jeu.getDamier());

                    inflater.inflate(R.layout.case_blanche, interfaceDamier);
                }
            }
        }

        rafraichirDamier(interfaceDamier, jeu.getDamier());
        pionSelectionne = null;

        return view;
    }

    private void createCaseNoire(GridLayout interfaceDamier, int position, LayoutInflater inflater, Damier damier) {
        Button caseNoire = (Button) inflater.inflate(R.layout.case_noire, interfaceDamier, false);
        caseNoire.setId(position);
        interfaceDamier.addView(caseNoire);

        caseNoire.setOnClickListener(v -> {
            if (damier.getPion(position) != null) {
                selectionnePion(position, interfaceDamier);
            }
        });
    }

    private void selectionnePion(int position, GridLayout interfaceDamier) {
        if (pionSelectionne != null) {
            Button dernierBouttonSelectionne = interfaceDamier.findViewById(pionSelectionne);
            dernierBouttonSelectionne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C58C54")));
        }
        Button bouttonSelectionne = interfaceDamier.findViewById(position);
        bouttonSelectionne.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFDB4D")));
        pionSelectionne = position;
        System.out.println(pionSelectionne);
    }

    private void rafraichirDamier(GridLayout interfaceDamier, Damier damier) {
        for (int i = 1; i <= damier.getCasesClone().length; i++) {
            Button boutton = interfaceDamier.findViewById(i);
            Pion pion = damier.getPion(i);

            if (pion != null) {
                if (pion instanceof Dame) {
                    if (pion.getCouleur() == Pion.Couleur.NOIR) {
                        boutton.setForeground(ContextCompat.getDrawable(interfaceDamier.getContext(), R.drawable.dame_noire));
                    } else {
                        boutton.setForeground(ContextCompat.getDrawable(interfaceDamier.getContext(), R.drawable.dame_blanche));
                    }
                } else {
                    if (pion.getCouleur() == Pion.Couleur.NOIR) {
                        boutton.setForeground(ContextCompat.getDrawable(interfaceDamier.getContext(), R.drawable.pion_noir));
                    } else {
                        boutton.setForeground(ContextCompat.getDrawable(interfaceDamier.getContext(), R.drawable.pion_blanc));
                    }
                }
            } else {
                boutton.setForeground(null);
            }
        }
    }
}