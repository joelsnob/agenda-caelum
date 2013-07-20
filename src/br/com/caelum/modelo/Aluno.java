package br.com.caelum.modelo;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import br.com.caelum.dao.AlunoDao;

public class Aluno implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int    id;
	private String nome;
	private String endereco;
	private String telefone;
	private String site;
	private String foto;
	private float  nota;

	public int getId() {
		return id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

//-----------------------------------------------------------------------------
	
	/**
	 * Construtor	
	 */
	public Aluno() {
		// TODO Auto-generated constructor stub
	}

//-----------------------------------------------------------------------------
		
	@Override
	public String toString() 
	{
		return this.nome;
	}
	
//-----------------------------------------------------------------------------

	/**
	 * Insere o registro no banco
	 * 
	 * @param aluno
	 */
	public void cadastrar(Context context)
	{
		AlunoDao dao = new AlunoDao(context);
		
		dao.cadastrar(this);
	}
	
//-----------------------------------------------------------------------------	

	/**
	 * Retorna os alunos cadastrados
	 * 
	 * @return List<Aluno>
	 */
	public List<Aluno> listar(Context context)
	{
		AlunoDao dao = new AlunoDao(context);
		
		List<Aluno> alunos = dao.listar();
		
		return alunos;
	}	

	
//-----------------------------------------------------------------------------

	/**
	 * Edita o registro no banco
	 * 
	 * @param aluno
	 */
	public void editar(Context context)
	{
		AlunoDao dao = new AlunoDao(context);
		
		dao.editar(this);
	}	
	
//-----------------------------------------------------------------------------

	/**
	 * Insere o registro no banco
	 * 
	 * @param aluno
	 */
	public void deletar(Context context, 
					    String name, 
					    CursorFactory factory, 
					    int version)
	{
		AlunoDao dao = new AlunoDao(context);
		
		dao.deletar(this);
	}	

	
//-----------------------------------------------------------------------------

	/**
	 * Verifica se o numero informado pertence a um aluno
	 * 
	 */
	public boolean isAluno(String telefone,
						   Context context)
	{
		AlunoDao dao = new AlunoDao(context);
		
		return dao.isAluno(telefone);
	}	
	
//-----------------------------------------------------------------------------
	
}
