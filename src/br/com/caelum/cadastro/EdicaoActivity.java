package br.com.caelum.cadastro;

import br.com.caelum.modelo.Aluno;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EdicaoActivity extends FormularioActivity 
{
	protected Aluno aluno;
	
//------------------------------------------------------------------------------	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.aluno = (Aluno) getIntent().getSerializableExtra("aluno");
		
		helper.colocaNoFormulario(this.aluno);
	}

//------------------------------------------------------------------------------	

	/**
	 * Define o comportamento do botao de cadastro
	 */
	@Override
	protected void definirAcaoBotao() 
	{
		Button botao = (Button) findViewById(R.id.botao);
		
		botao.setText("Editar");
		
		botao.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// guardo uma copia do id para garantir 
				// que nao sera sobrescrita
				int id = aluno.getId();
				
				aluno = helper.pegaAlunoDoFormulario();
				
				aluno.setId(id);
				
				aluno.editar(EdicaoActivity.this);
				
				String msg = String.format("Aluno %s alterado", aluno.getNome());
				
				Toast.makeText(EdicaoActivity.this, msg, Toast.LENGTH_SHORT).show();
				
				// Volta para tela anterior
				finish();
			}
		});
	}	
	
//------------------------------------------------------------------------------	

}