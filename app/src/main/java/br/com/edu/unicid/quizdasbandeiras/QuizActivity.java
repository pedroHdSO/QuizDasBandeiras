package br.com.edu.unicid.quizdasbandeiras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private ImageView ivBandeira;
    private RadioGroup rgAlternativas;
    private Button btnResponder;
    private int perguntaAtual = 0;
    private int acertos = 0;
    private String nomeAluno;
    private String respostaCorretaAtual; // Armazena a resposta correta atual

    private String[][] perguntas = {
            {"brasil", "Brasil", "Canadá", "Estados Unidos", "Japão"},
            {"canada", "Canadá", "Austrália", "Alemanha", "França"},
            {"estados_unidos", "Estados Unidos", "Itália", "Espanha", "China"},
            {"japao", "Japão", "Índia", "Rússia", "Argentina"},
            {"australia", "Austrália", "África do Sul", "México", "Brasil"},
            {"alemanha", "Alemanha", "França", "Canadá", "Japão"},
            {"franca", "França", "Itália", "México", "China"},
            {"italia", "Itália", "Espanha", "Rússia", "Brasil"},
            {"espanha", "Espanha", "Argentina", "Alemanha", "Japão"},
            {"china", "China", "Canadá", "Rússia", "Brasil"},
            {"india", "Índia", "Estados Unidos", "Japão", "Argentina"},
            {"russia", "Rússia", "Alemanha", "França", "China"},
            {"argentina", "Argentina", "África do Sul", "México", "Austrália"},
            {"africa_do_sul", "África do Sul", "Brasil", "Rússia", "Espanha"},
            {"mexico", "México", "Alemanha", "França", "Itália"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ivBandeira = findViewById(R.id.ivBandeira);
        rgAlternativas = findViewById(R.id.rgAlternativas);
        btnResponder = findViewById(R.id.btnResponder);

        Intent intent = getIntent();
        nomeAluno = intent.getStringExtra("nome");

        carregarPergunta();

        rgAlternativas.setOnCheckedChangeListener((group, checkedId) -> btnResponder.setEnabled(true));

        btnResponder.setOnClickListener(v -> {
            int idSelecionado = rgAlternativas.getCheckedRadioButtonId();
            RadioButton rbSelecionado = findViewById(idSelecionado);
            String respostaSelecionada = rbSelecionado.getText().toString();

            if (respostaSelecionada.equals(respostaCorretaAtual)) {
                acertos++;
            }

            perguntaAtual++;

            if (perguntaAtual < perguntas.length) {
                carregarPergunta();
            } else {
                Intent intentRanking = new Intent(QuizActivity.this, RankingActivity.class);
                intentRanking.putExtra("acertos", acertos);
                intentRanking.putExtra("nome", nomeAluno);
                startActivity(intentRanking);
                finish();
            }
        });
    }


    private void carregarPergunta() {
        rgAlternativas.clearCheck();
        btnResponder.setEnabled(false);

        int resId = getResources().getIdentifier(perguntas[perguntaAtual][0], "drawable", getPackageName());
        ivBandeira.setImageResource(resId);

        String[] alternativas = Arrays.copyOfRange(perguntas[perguntaAtual], 1, perguntas[perguntaAtual].length);
        List<String> listaAlternativas = Arrays.asList(alternativas);
        Collections.shuffle(listaAlternativas);

        // Atualiza a resposta correta atual após o embaralhamento
        for (String alternativa : listaAlternativas) {
            if (alternativa.equals(perguntas[perguntaAtual][1])) {
                respostaCorretaAtual = alternativa;
                break;
            }
        }

        rgAlternativas.removeAllViews();

        for (String alternativa : listaAlternativas) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(alternativa);
            rgAlternativas.addView(radioButton);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
