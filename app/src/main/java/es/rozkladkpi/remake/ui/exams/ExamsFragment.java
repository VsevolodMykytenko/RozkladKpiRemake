package es.rozkladkpi.remake.ui.exams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        final TextView textView = root.findViewById(R.id.text_gallery);
        examsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}