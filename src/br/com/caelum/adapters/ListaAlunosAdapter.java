package br.com.caelum.adapters;

import java.util.List;

import br.com.caelum.cadastro.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.caelum.modelo.Aluno;

public class ListaAlunosAdapter extends BaseAdapter
{
	private final List<Aluno> alunos;
	private final Activity activity;
	
	public ListaAlunosAdapter(Activity activity, List<Aluno> alunos) 
	{
		this.alunos   = alunos;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int posicao) {
		return alunos.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return alunos.get(posicao).getId();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) 
	{
		View view = activity.getLayoutInflater().inflate(R.layout.item, null);
		
		if(posicao % 2 == 0)
		{
			view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
		}
		
		Aluno aluno = alunos.get(posicao);
		
		TextView nome 	= (TextView)  view.findViewById(R.id.nome);
		ImageView foto 	= (ImageView) view.findViewById(R.id.foto);
		
		nome.setText(aluno.toString());
		
		Bitmap bm;
		
		if(aluno.getFoto() != null)
		{
			bm = BitmapFactory.decodeFile(aluno.getFoto());
		}
		else
		{
			bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
		}
		
		bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
		
		foto.setImageBitmap(bm);
		
		return view;
	}
}
