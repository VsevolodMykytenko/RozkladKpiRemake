package es.rozkladkpi.remake.ui.teachers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeachersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TeachersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Розклад з активностями в розробці");
    }

    public LiveData<String> getText() {
        return mText;
    }
}