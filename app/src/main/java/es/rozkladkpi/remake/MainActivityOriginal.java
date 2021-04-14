package es.rozkladkpi.remake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivityOriginal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_original);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        TextView underImageText = findViewById(R.id.under_logo_text);
        underImageText.setText("Департамент організації освітнього процесу");

        TextView topicText = findViewById(R.id.topic_text);
        topicText.setText("Розклад занять для вашої групи:");

        LinearLayout button = findViewById(R.id.Button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityOriginal.this, TestActivity.class);
            startActivity(intent);
        });
    }
}