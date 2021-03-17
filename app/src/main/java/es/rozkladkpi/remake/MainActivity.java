package es.rozkladkpi.remake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        TextView underImageText = findViewById(R.id.under_logo_text);
        underImageText.setText("Департамент організації освітнього процесу");

        TextView topicText = findViewById(R.id.topic_text);
        topicText.setText("Розклад занять для вашої групи:");
    }
}