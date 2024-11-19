package ca.cstjean.mobile.tp_dame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEcranJeu} factory method to
 * create an instance of this fragment.
 */
public class FragmentEcranJeu extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecran_jeu, container, false);
        Jeu jeu = new Jeu(new Damier(), false);
        jeu.getDamier().initialiser();
        GridLayout interfaceDamier = view.findViewById(R.id.damier);

        for (int i = 0; i < 10; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i % 2 == 0) {
                    inflater.inflate(R.layout.case_blanche, interfaceDamier);

                    Button caseNoire = (Button) inflater.inflate(R.layout.case_noire, interfaceDamier, false);
                    int id = (i * 5) + j;
                    caseNoire.setId(id);
                    interfaceDamier.addView(caseNoire);
                } else {
                    Button caseNoire = (Button) inflater.inflate(R.layout.case_noire, interfaceDamier, false);
                    int id = (i * 5) + j;
                    caseNoire.setId(id);
                    interfaceDamier.addView(caseNoire);

                    inflater.inflate(R.layout.case_blanche, interfaceDamier);
                }
            }
        }

        return view;
    }
}