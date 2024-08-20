package br.com.edu.unicid.quizdasbandeiras;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etNome, etRGM;
    private Button btnIniciarQuiz, btnEncerrarQuiz, btnCarregarFoto;
    private ImageView ivFotoAluno;
    private Uri fotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.etNome);
        etRGM = findViewById(R.id.etRGM);
        btnIniciarQuiz = findViewById(R.id.btnIniciarQuiz);
        btnEncerrarQuiz = findViewById(R.id.btnEncerrarQuiz);
        btnCarregarFoto = findViewById(R.id.btnCarregarFoto);
        ivFotoAluno = findViewById(R.id.ivFotoAluno);

        btnIniciarQuiz.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etNome.addTextChangedListener(textWatcher);
        etRGM.addTextChangedListener(textWatcher);

        btnCarregarFoto.setOnClickListener(v -> openGallery());

        btnIniciarQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("nome", etNome.getText().toString());
            intent.putExtra("rgm", etRGM.getText().toString());
            if (fotoUri != null) {
                intent.putExtra("fotoUri", fotoUri.toString());
            }
            startActivity(intent);
        });

        btnEncerrarQuiz.setOnClickListener(v -> finish());
    }

    private void checkFields() {
        btnIniciarQuiz.setEnabled(!etNome.getText().toString().trim().isEmpty() &&
                !etRGM.getText().toString().trim().isEmpty() &&
                fotoUri != null);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fotoUri = result.getData().getData();
                    ivFotoAluno.setImageURI(fotoUri);
                    checkFields();
                }
            });

}
