package net.jptechnology.android.takehomeassignment08_johnp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View imageContainer;
    private TextView overlayText;
    private ProgressBar progressBar;
    private Button uploadButton;
    private TextView downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageContainer = findViewById(R.id.image_container);
        overlayText = (TextView) findViewById(R.id.overlay_text);
        overlayText.setText("");
        overlayText.setVisibility(View.INVISIBLE);
        EditText textInput = (EditText) findViewById(R.id.text_input);
        textInput.addTextChangedListener(new InputTextWatcher());
//        uploadButton = (Button) findViewById(R.id.upload_button);
//        uploadButton.setOnClickListener(new UploadOnClickListener());
//        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        progressBar.setVisibility(View.GONE);
//        downloadUrl = (TextView) findViewById(R.id.download_url);
    }

    private class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            overlayText.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            overlayText.setText(s.toString());
        }
    }
}