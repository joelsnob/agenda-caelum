package br.com.caelum.cadastro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.caelum.helpers.FormularioHelper;
import br.com.caelum.modelo.Aluno;

public class FormularioActivity extends Activity 
{
	protected FormularioHelper helper;
	protected String localArquivoFoto;
	protected static final int CODIGO_CHAMADA_CAMERA = 123;
	
//------------------------------------------------------------------------------	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.formulario);
		
		this.helper = new FormularioHelper(this);
		
		ImageView foto = this.helper.getFoto();
		
		foto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				localArquivoFoto = Environment.getExternalStorageDirectory()
								 + "/"
								 + System.currentTimeMillis()
								 + ".jpg";
				
				Uri uri = Uri.fromFile(new File(localArquivoFoto));
				
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				
				startActivityForResult(camera, CODIGO_CHAMADA_CAMERA);
			}
		});
		
		this.definirAcaoBotao();
	}
	
//------------------------------------------------------------------------------	
	
	/**
	 * Recebe a resposta do aplicativo de camera
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == CODIGO_CHAMADA_CAMERA)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				helper.exibirFotoCapturada(localArquivoFoto);
			}
			else
			{
				localArquivoFoto = null;
			}
		}
	}
	
//------------------------------------------------------------------------------	
	
	/**
	 * Define o comportamento do botao de cadastro
	 */
	protected void definirAcaoBotao() 
	{
		Button botao = (Button) findViewById(R.id.botao);
				
		botao.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Aluno aluno = helper.pegaAlunoDoFormulario();
				
				aluno.setFoto(localArquivoFoto);
				
				aluno.cadastrar(FormularioActivity.this);
				
				String msg = String.format("Aluno %s cadastrado", aluno.getNome());
				
				Toast.makeText(FormularioActivity.this, msg, Toast.LENGTH_SHORT).show();
				
				// Volta para tela anterior
				finish();
			}
		});
	}	
	
//------------------------------------------------------------------------------	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opcoes_formulario, menu);
	
		return true;
	}

//------------------------------------------------------------------------------	

}