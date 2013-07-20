package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.caelum.modelo.Aluno;

public class AlunoDao extends SQLiteOpenHelper 
{
	private String table = "alunos";

//-----------------------------------------------------------------------------	
	
	/**
	 * Construtor
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public AlunoDao(Context context) 
	{
		super(context, "FJ-57", null, 1);		
	}

//-----------------------------------------------------------------------------	
	
	/**
	 * Rotinas a serem executadas durante a criacao 
	 * do banco na instalacao do aplicativo
	 * 
	 * @param SQLLiteDatabase base
	 */
	@Override
	public void onCreate(SQLiteDatabase base) 
	{
		String sql = "CREATE TABLE "
				   + this.table 
				   +" ( "
				   + "id INTEGER PRIMARY KEY, "
				   + "nome TEXT UNIQUE NOT NULL, "
				   + "endereco TEXT, "
				   + "telefone TEXT, "
				   + "site TEXT, "
				   + "nota REAL, "
				   + "foto TEXT "
				   + ") ";
		
		base.execSQL(sql);
	}

//-----------------------------------------------------------------------------	
	
	/**
	 * Rotinas a serem executadas durante a atualizacao do banco
	 * @param SQLLiteDatabase base
	 * @param int arg1
	 * @param int arg2
	 */
	@Override
	public void onUpgrade(SQLiteDatabase base, int arg1, int arg2) 
	{
		// Note que por ser um exemplo, nao estamos
		// fazendo backup dos dados da tabela.
		String sql = "DROP TABLE IF EXISTS alunos";
		
		base.execSQL(sql);
	}

//-----------------------------------------------------------------------------	

	/**
	 * Insere o registro no banco
	 * 
	 * @param Aluno
	 */
	public void cadastrar(Aluno aluno)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put("nome"		, aluno.getNome());
		cv.put("endereco"	, aluno.getEndereco());
		cv.put("telefone"	, aluno.getTelefone());
		cv.put("site"		, aluno.getSite());
		cv.put("nota"		, aluno.getNota());
		cv.put("foto"		, aluno.getFoto());
		
		db.insert(this.table, null, cv);
		
		db.close();
	}

//-----------------------------------------------------------------------------	

	/**
	 * Insere o registro no banco
	 * 
	 * @param Aluno
	 */
	public void editar(Aluno aluno)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put("nome"		, aluno.getNome());
		cv.put("endereco"	, aluno.getEndereco());
		cv.put("telefone"	, aluno.getTelefone());
		cv.put("site"		, aluno.getSite());
		cv.put("nota"		, aluno.getNota());
		cv.put("foto"		, aluno.getFoto());
		
		String[] args = {Integer.toString(aluno.getId())};
	
		db.update("alunos", cv, "id=?", args);
		
		db.close();
	}	
	
//-----------------------------------------------------------------------------	

	/**
	 * Insere o registro no banco
	 * 
	 * @param Aluno
	 */
	public void deletar(Aluno aluno)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		String[] args = {Integer.toString(aluno.getId())};
		
		db.delete("alunos", "id=?", args);
		
		db.close();
	}

//-----------------------------------------------------------------------------	

		/**
		 * Retorna os alunos cadastrados
		 * 
		 * @return List<Aluno>
		 */
		public List<Aluno> listar()
		{
			SQLiteDatabase db = getWritableDatabase();
			
			String [] colunas = {"id", "nome", "endereco", "telefone", "site", "nota", "foto"};
			
			Cursor cursor = db.query("alunos", colunas, null, null, null, null, null);
			
			List<Aluno> alunos = new ArrayList<Aluno>();
			
			while(cursor.moveToNext())
			{
				Aluno aluno = new Aluno();
				
				aluno.setId			(cursor.getInt(0));
				aluno.setNome		(cursor.getString(1));
				aluno.setEndereco	(cursor.getString(2));
				aluno.setTelefone	(cursor.getString(3));
				aluno.setSite		(cursor.getString(4));
				aluno.setNota		(cursor.getFloat(5));
				aluno.setFoto		(cursor.getString(6));
				
				alunos.add(aluno);
			}
					
			db.close();
			
			return alunos;
		}	

//-----------------------------------------------------------------------------	

		/**
		 * Verifica se o numero de telefone eh de um aluno cadastrado
		 * 
		 * @param String telefone
		 * @return boolean
		 */
		public boolean isAluno(String telefone)
		{
			SQLiteDatabase db = getWritableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT telefone FROM alunos WHERE telefone = ?", new String[]{telefone});
			
			int total = cursor.getCount();
			
			cursor.close();
			
			boolean existe = (total > 0);
			
			return existe;
		}		
		
//-----------------------------------------------------------------------------	
	
}
