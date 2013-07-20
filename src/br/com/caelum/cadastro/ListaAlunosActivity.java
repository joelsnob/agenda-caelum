package br.com.caelum.cadastro;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.com.caelum.adapters.ListaAlunosAdapter;
import br.com.caelum.converter.AlunoConverter;
import br.com.caelum.modelo.Aluno;
import br.com.caelum.webclient.WebClient;

public class ListaAlunosActivity extends Activity {

	private ListView listaAlunos;
	private Aluno alunoSelecionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.listagem_alunos);
		
		this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
		
		// Defino que ao clicar e segurar um item da 
		// listagem sera executado um menu de contexto.
		// Veja o m[etodo onCreateContextMenu
		registerForContextMenu(this.listaAlunos);
		
		this.listar();
	
		this.listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			// Coringa do java <?>
			// Significa que eu nao sei o tipo do adapter
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				
				alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
				
				editar();				
			}
		});
		
		
		this.listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
			{
				alunoSelecionado = (Aluno) parent.getItemAtPosition(position);
				
				return false;
			}
		});
	}

//--------------------------------------------------------------------	
	
	@Override
	protected void onStart() {
		super.onStart();
	}

//--------------------------------------------------------------------	
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
//--------------------------------------------------------------------	

	@Override
	protected void onResume() {
		super.onResume();
		
		this.listar();
	}
	
//--------------------------------------------------------------------	

	@Override
	protected void onPause() 
	{
		super.onPause();
	}
	
//--------------------------------------------------------------------	
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
//--------------------------------------------------------------------	

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

//------------------------------------------------------------------------------	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.opcoes_listagem_alunos, menu);
	
		return true;
	}	

//--------------------------------------------------------------------	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case R.id.menu_novo 			: this.cadastrar(); 				break; 
			case R.id.menu_mapa 			: Log.i("INFO", "Mapa"); 			break; 
			case R.id.menu_enviar_alunos 	: this.enviar(); 					break; 
			case R.id.menu_receber_provas 	: Log.i("INFO", "Receber"); 		break; 
			case R.id.menu_preferencias 	: Log.i("INFO", "Preferencias"); 	break; 
		}
		
		return super.onOptionsItemSelected(item);
	}	

//------------------------------------------------------------------------------	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.opcoes_aluno, menu);
	}	
	
//--------------------------------------------------------------------	

	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
			case R.id.menu_aluno_deletar : this.deletar();     break;
			case R.id.menu_aluno_ligar   : this.ligar(item);   break;
			case R.id.menu_aluno_sms	 : this.sms(item);     break;
			case R.id.menu_aluno_site	 : this.navegar(item); break;
			case R.id.menu_aluno_mapa	 : this.localizar(item); break;
		}
				
		return super.onContextItemSelected(item);
	}

//--------------------------------------------------------------------------------

	/**
	 * Exibe a lista de alunos
	 */
	protected void listar() 
	{
		Aluno aluno = new Aluno();
		
		List<Aluno> alunos = aluno.listar(ListaAlunosActivity.this);
		
		//ArrayAdapter <Aluno> adaptador = new ArrayAdapter <Aluno> (this, android.R.layout.simple_list_item_1, alunos);
		ListaAlunosAdapter adaptador = new ListaAlunosAdapter(this, alunos);
		
		this.listaAlunos.setAdapter(adaptador);	
	}	
	
//--------------------------------------------------------------------	
	
	/**
	 * Exibe a tela de cadastro
	 */
	private void cadastrar()
	{
		Intent cadastro = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
		
		startActivity(cadastro);
	}

//--------------------------------------------------------------------------------

	/**
	 * Apaga o registro selecionado
	 * 
	 * @return void 
	 */
	protected void deletar() 
	{
		this.alunoSelecionado.deletar(ListaAlunosActivity.this, 
									  "FJ-57", 
									  null, 
								 	  1);
		
		this.listar();
	}

//--------------------------------------------------------------------------------

	/**
	 * Liga para o telefone do aluno
	 * 
	 * @return void 
	 */
	protected void ligar(MenuItem item) 
	{
		String numero = this.alunoSelecionado.getTelefone();
		
		Intent ligacao = new Intent(Intent.ACTION_CALL);
		
		ligacao.setData(Uri.parse("tel:" + numero));
		
		item.setIntent(ligacao);
	}	

//--------------------------------------------------------------------------------

	/**
	 * Envia mensagem sms para o telefone do aluno
	 * 
	 * @return void 
	 */
	protected void sms(MenuItem item) 
	{
		String numero = this.alunoSelecionado.getTelefone();
		
		Intent sms = new Intent(Intent.ACTION_VIEW);
		
		// Destinatario:
		sms.setData(Uri.parse("sms:" + numero));
		
		// Texto:
		sms.putExtra("sms_body", "envie VASCO para 999!");
		
		item.setIntent(sms);
	}	

//--------------------------------------------------------------------------------

	/**
	 * Abre o navegador padrao na url do site informado pelo aluno                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	 * 
	 * @return void 
	 */
	protected void navegar(MenuItem item) 
	{
		String url = this.alunoSelecionado.getSite();
		
		if(url.indexOf("www") < 0) 	   url = "www." + url; 
		if(url.indexOf("http://") < 0) url = "http://" + url; 
				
		Intent navegador = new Intent(Intent.ACTION_VIEW);
		
		// Destinatario:
		navegador.setData(Uri.parse(  url));
		
		item.setIntent(navegador);
	}	


//--------------------------------------------------------------------------------

	/**
	 * Abre o mapa com o endereco do aluno                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	 * 
	 * @return void 
	 */
	protected void localizar(MenuItem item) 
	{
		String endereco = this.alunoSelecionado.getEndereco();
		
		Intent mapa = new Intent(Intent.ACTION_VIEW);
		
		String geo = "geo: 0,0?z=14&q=" + endereco;
		
		// Destinatario:
		mapa.setData(Uri.parse(geo));
		
		item.setIntent(mapa);
	}	
	
//--------------------------------------------------------------------------------

	/**
	 * Edita o registro selecionado
	 * 
	 * @return void 
	 */
	protected void editar() 
	{
		Intent edicao = new Intent(ListaAlunosActivity.this, EdicaoActivity.class);
		
		edicao.putExtra("aluno", this.alunoSelecionado);
		
		startActivity(edicao);
	}
	
//--------------------------------------------------------------------	
	
	/**
	 * Sincroniza com o servidor
	 */
	private void enviar()
	{
		try
		{
			Aluno aluno = new Aluno();
			
			List<Aluno> alunos = aluno.listar(ListaAlunosActivity.this);
			
			String json = new AlunoConverter().toJSON(alunos);
			
			//Toast.makeText(this, json, Toast.LENGTH_LONG).show();
			
			WebClient cliente = new WebClient("http://www.caelum.com.br/mobile");
			
			String resposta = cliente.post(json);
			
			Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();
		}	
		catch(JSONException e)
		{
			Log.e(e.getClass().toString(), e.getMessage());
		}
	}	
	
}	
	
//--------------------------------------------------------------------	

