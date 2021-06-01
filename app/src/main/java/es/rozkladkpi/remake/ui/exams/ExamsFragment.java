package es.rozkladkpi.remake.ui.exams;

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

public class ExamsFragment extends Fragment {

    private ExamsViewModel examsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        examsViewModel =
                new ViewModelProvider(this).get(ExamsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exams, container, false);

        final TextView getTeachersView = root.findViewById(R.id.text_exams_1);
        getTeachersView.setText("Введіть групу::");

        final TextView entryField = root.findViewById(R.id.entry_field_exams);
        entryField.setText("");

        Button teachersButton = root.findViewById(R.id.button_exams);
        teachersButton.setText("Знайти розклад сесії");

        teachersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupeName = entryField.getText().toString();
                getTeachersView.setText("Данна функція в розробці");
                entryField.setVisibility(View.GONE);
                teachersButton.setVisibility(View.GONE);
            }
        });
        return root;
    }
}