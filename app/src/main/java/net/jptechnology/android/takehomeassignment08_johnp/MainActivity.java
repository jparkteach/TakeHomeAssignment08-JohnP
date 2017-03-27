package net.jptechnology.android.takehomeassignment08_johnp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

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
        uploadButton = (Button) findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(new UploadOnClickListener());
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        downloadUrl = (TextView) findViewById(R.id.download_url);
        downloadUrl.setVisibility(View.INVISIBLE);
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

    private class UploadOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            imageContainer.setDrawingCacheEnabled(true);
            imageContainer.buildDrawingCache();
            Bitmap bitmap = imageContainer.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            imageContainer.setDrawingCacheEnabled(false);
            byte[] data = baos.toByteArray();

            String path = "userpics/" + UUID.randomUUID() + ".png";
            StorageReference userpicRef = storage.getReference(path);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCustomMetadata("text", overlayText.getText().toString())
                    .build();

            progressBar.setVisibility(View.VISIBLE);
            uploadButton.setEnabled(false);

            UploadTask uploadTask = userpicRef.putBytes(data, metadata);
            uploadTask.addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    uploadButton.setEnabled(true);

                    Uri url = taskSnapshot.getDownloadUrl();
                    downloadUrl.setText(url.toString());
                    downloadUrl.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}