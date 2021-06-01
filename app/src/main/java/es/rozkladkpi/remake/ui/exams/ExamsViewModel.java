package es.rozkladkpi.remake.ui.exams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExamsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExamsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Розклад на 2 тижні в розробці");
    }

    public LiveData<String> getText() {
        return mText;
    }
}