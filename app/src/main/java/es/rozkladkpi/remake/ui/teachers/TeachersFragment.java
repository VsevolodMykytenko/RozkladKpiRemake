package es.rozkladkpi.remake.ui.teachers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import es.rozkladkpi.remake.R;

public class TeachersFragment extends Fragment {

    private TeachersViewModel teachersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teachersViewModel =
                new ViewModelProvider(this).get(TeachersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_teachers, container, false);
        final TextView getTeachersView = root.findViewById(R.id.text_teachers_1);
        getTeachersView.setText("Введіть ПІБ викладача:");

        final TextView entryField = root.findViewById(R.id.entry_field_teachers);
        entryField.setText("");

        Button teachersButton = root.findViewById(R.id.button_teachers);
        teachersButton.setText("Знайти розклад викладача");

        teachersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupeName = entryField.getText().toString();
                getTeachersView.setVisibility(View.GONE);
                entryField.setVisibility(View.GONE);
                teachersButton.setVisibility(View.GONE);
            }
        });
        return root;
    }
}