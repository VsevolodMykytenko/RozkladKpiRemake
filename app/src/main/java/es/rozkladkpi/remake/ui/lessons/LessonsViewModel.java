package es.rozkladkpi.remake.ui.lessons;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LessonsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LessonsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Розклад занять");
    }

    public LiveData<String> getText() {
        return mText;
    }
}