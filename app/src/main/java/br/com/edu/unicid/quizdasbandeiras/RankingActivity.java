package br.com.edu.unicid.quizdasbandeiras;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {
    private TextView tvNome, tvAcertos;
    private Button btnResponderNovamente, btnTelaInicial;
    private ImageView ivFotoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        tvNome = findViewById(R.id.tvNome);
        tvAcertos = findViewById(R.id.tvAcertos);
        ivFotoAluno = findViewById(R.id.ivFotoAluno);
        btnResponderNovamente = findViewById(R.id.btnResponderNovamente);
        btnTelaInicial = findViewById(R.id.btnTelaInicial);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        int acertos = intent.getIntExtra("acertos", 0);
        String fotoUriString = intent.getStringExtra("fotoUri");

        tvNome.setText("Nome: " + nome);
        tvAcertos.setText("Acertos: " + acertos);

        // Verifica se fotoUriString não é nulo ou vazio antes de tentar usá-lo
        if (fotoUriString != null && !fotoUriString.isEmpty()) {
            Uri fotoUri = Uri.parse(fotoUriString);
            ivFotoAluno.setImageURI(fotoUri);
        } else {
            ivFotoAluno.setImageResource(R.drawable.logo); // Imagem padrão caso não haja foto
        }

        btnResponderNovamente.setOnClickListener(v -> {
            Intent intentQuiz = new Intent(RankingActivity.this, QuizActivity.class);
            intentQuiz.putExtra("nome", nome);
            intentQuiz.putExtra("fotoUri", fotoUriString);
            startActivity(intentQuiz);
            finish();
        });

        btnTelaInicial.setOnClickListener(v -> {
            Intent intentMain = new Intent(RankingActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        });
    }
}
