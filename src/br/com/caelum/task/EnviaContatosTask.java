package br.com.caelum.task;

import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import br.com.caelum.converter.AlunoConverter;
import br.com.caelum.modelo.Aluno;
import br.com.caelum.webclient.WebClient;

public class EnviaContatosTask extends AsyncTask<Object, Object, String> 
{
	private Context context;
	private final String endereco = "http://www.caelum.com.br/mobile";
	
	public EnviaContatosTask(Context context) 
	{
		this.context = context;
	}
	
	@Override
	protected String doInBackground(Object... params)
	{
		try
		{
			Aluno aluno = new Aluno();
			
			List<Aluno> lista = aluno.listar(context);
			
			String listaJson = new AlunoConverter().toJSON(lista);
			
			String JsonResposta = new WebClient(endereco).post(listaJson);
			
			return JsonResposta;
		}	
		catch(JSONException e)
		{
			throw new RuntimeException(e);
		}	
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		//progress.dismiss();
		
		//Toast.makeText(context, result, Toast.LENGTH_LONG);
	}
}
