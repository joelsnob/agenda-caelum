package br.com.caelum.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import br.com.caelum.cadastro.FormularioActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.modelo.Aluno;

public class FormularioHelper 
{
	private Aluno aluno = new Aluno();
	
	private EditText  nome;
	private EditText  endereco;
	private EditText  telefone;
	private EditText  site;
	private RatingBar nota;
	private ImageView foto;

//----------------------------------------------------------------------------	
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}	
	
//----------------------------------------------------------------------------	
	
	public ImageView getFoto() {
		return foto;
	}

//----------------------------------------------------------------------------	
	
	/**
	 * Atribui os campos do formulario aos atributos do helper
	 * @param formulario
	 */
	public FormularioHelper(FormularioActivity formulario) 
	{
		this.nome 		= (EditText)  formulario.findViewById(R.id.nome);
		this.endereco 	= (EditText)  formulario.findViewById(R.id.endereco);
		this.telefone  	= (EditText)  formulario.findViewById(R.id.telefone);
		this.site 		= (EditText)  formulario.findViewById(R.id.site);
		this.nota 		= (RatingBar) formulario.findViewById(R.id.nota);
		this.foto 		= (ImageView) formulario.findViewById(R.id.foto);
	}

//----------------------------------------------------------------------------	

	/**
	 * Atribui os dados do formulario ao objeto de negocio 
	 * 
	 * @return Aluno
	 */
	public Aluno pegaAlunoDoFormulario()
	{
		Aluno aluno = new Aluno();
		
		aluno.setNome		(this.nome.getText().toString());
		aluno.setEndereco	(this.endereco.getText().toString());
		aluno.setTelefone	(this.telefone.getText().toString());
		aluno.setSite		(this.site.getText().toString());
		aluno.setNota		(this.nota.getRating());
		
		return aluno;
	}

//----------------------------------------------------------------------------	

	/**
	 * Atribui os dados do objeto de negocio ao formulario 
	 * 
	 * @return Aluno
	 */
	public void colocaNoFormulario(Aluno aluno)
	{
		this.nome		.setText	(aluno.getNome());
		this.endereco	.setText	(aluno.getEndereco());
		this.telefone	.setText	(aluno.getTelefone());
		this.site		.setText	(aluno.getSite());
		this.nota		.setRating	(aluno.getNota());
		
		this.aluno = aluno;
		
		if(aluno.getFoto() != null)
		{
			this.exibirFotoCapturada(aluno.getFoto());
		}
	}	

//----------------------------------------------------------------------------	

	/**
	 * Exibe a imagem capturada no imageview do formulario
	 * 
	 * @param String local Caminho onde a imagem foi capturada.
	 */
	public void exibirFotoCapturada(String local)
	{
		// Carrega o bitmap a partir do caminho especificado
		Bitmap bmp = BitmapFactory.decodeFile(local);
		
		// Redimensiona
		Bitmap bmpReduzido = Bitmap.createScaledBitmap(bmp, 100, 100, true);
		
		// Seta o bitmap no imageview para exibicao
		this.foto.setImageBitmap(bmpReduzido);
	}
	
//----------------------------------------------------------------------------	
	
}
