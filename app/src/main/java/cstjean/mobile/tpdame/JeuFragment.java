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

        Bundle arguments = getArguments();
        String nomJoueur1 = "";
        String nomJoueur2 = "";

        if (arguments != null) {
            nomJoueur1 = arguments.getString("nomJoueur1");
            nomJoueur2 = arguments.getString("nomJoueur2");
        }

        rootView.findViewById(R.id.rewind).setOnClickListener(v -> {
            jeu.retournerEnArriere();
            if (pionSelectionne != null) {
                deselectionnerCase(pionSelectionne);
            }
            rafraichirInterfaceDamier();
            setTourJoueurInterface(jeu.getTourJoueur1());
        });
        TextView player1NameView = rootView.findViewById(R.id.plr1_name_game);
        player1NameView.setText(nomJoueur1);
        TextView player2NameView = rootView.findViewById(R.id.plr2_name_game);
        player2NameView.setText(nomJoueur2);
        setTourJoueurInterface(jeu.getTourJoueur1());
        genererInterfaceDamier();

        rafraichirInterfaceDamier();

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
        caseNoire.setOnClickListener(v -> caseNoireOnClickListener(position));
    }

    private void caseNoireOnClickListener(int position) {
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

            Pion.Couleur couleur = jeu.estTerminee();
            if (couleur != null) {
                Toast.makeText(rootView.getContext(), "Fini!", Toast.LENGTH_LONG).show();
                setTourJoueurInterface(null);
            } else {
                setTourJoueurInterface(jeu.getTourJoueur1());
            }
        }
    }

    private void setTourJoueurInterface(Boolean tourJoueur1) {
        TextView textTourJoueur1 = rootView.findViewById(R.id.plr1_turn_text);
        TextView textTourJoueur2 = rootView.findViewById(R.id.plr2_turn_text);

        int visibiltyTextTourJoueur1;
        int visibiltyTextTourJoueur2;

        if (tourJoueur1 != null) {
            visibiltyTextTourJoueur1 = tourJoueur1 ? View.VISIBLE : View.GONE;
            visibiltyTextTourJoueur2 = tourJoueur1 ? View.GONE : View.VISIBLE;
        } else {
            visibiltyTextTourJoueur1 = View.GONE;
            visibiltyTextTourJoueur2 = View.GONE;
        }

        textTourJoueur1.setVisibility(visibiltyTextTourJoueur1);
        textTourJoueur2.setVisibility(visibiltyTextTourJoueur2);
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
                    int foreground = pion.getCouleur() == Pion.Couleur.NOIR ? R.drawable.dame_noire :
                            R.drawable.dame_blanche;
                    boutton.setForeground(ContextCompat.getDrawable(rootView.getContext(), foreground));
                } else {
                    int foreground = pion.getCouleur() == Pion.Couleur.NOIR ? R.drawable.pion_noir :
                            R.drawable.pion_blanc;
                    boutton.setForeground(ContextCompat.getDrawable(rootView.getContext(), foreground));
                }
            } else {
                boutton.setForeground(null);
            }
        }
    }
}