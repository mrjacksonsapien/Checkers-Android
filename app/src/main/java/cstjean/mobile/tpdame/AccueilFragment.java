package cstjean.mobile.tpdame;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

/**
 * Fragment de l'écran d'accueil pour la saisie des noms.
 *
 * @author Martin Soltan
 * @author Tommy Desjardins
 */
public class AccueilFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accueil, container, false);

        Button bouttonStart = rootView.findViewById(R.id.start);
        EditText nomJoueur1View = rootView.findViewById(R.id.plr1_name_menu);
        EditText nomJoueur2View = rootView.findViewById(R.id.plr2_name_menu);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bouttonStart.setEnabled(
                        verifyTextInputs(nomJoueur1View.getText().toString(), nomJoueur2View.getText().toString())
                );
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        nomJoueur1View.addTextChangedListener(textWatcher);
        nomJoueur2View.addTextChangedListener(textWatcher);

        bouttonStart.setOnClickListener(v -> {
            String nomJoueur1 = nomJoueur1View.getText().toString();
            String nomJoueur2 = nomJoueur2View.getText().toString();

            if (!nomJoueur1.isEmpty() && !nomJoueur2.isEmpty()) {
                Bundle arguments = new Bundle();
                arguments.putString("nomJoueur1", nomJoueur1);
                arguments.putString("nomJoueur2", nomJoueur2);
                JeuFragment jeuFragment = new JeuFragment();
                jeuFragment.setArguments(arguments);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, jeuFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    private boolean verifyTextInputs(String nomJoueur1, String nomJoueur2) {
        return
                !nomJoueur1.isEmpty() &&
                        !nomJoueur2.isEmpty() &&
                        !nomJoueur1.equals(nomJoueur2);
    }
}