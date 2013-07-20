package br.com.caelum.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import br.com.caelum.cadastro.R;
import br.com.caelum.modelo.Aluno;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context contexto, Intent intent) 
	{
		Bundle bundle = intent.getExtras();
		
		Object[] messages = (Object[]) bundle.get("pdus");
		
		byte[] message = (byte[]) messages[0];
		
		SmsMessage sms = SmsMessage.createFromPdu(message);
		
		String numero = sms.getDisplayOriginatingAddress();
		String texto  = sms.getMessageBody();
		
		Aluno aluno = new Aluno();
		
		boolean existe = aluno.isAluno(numero,
									   contexto);
		
		if(existe)
		{
			// Toca o tema da vitoria...
			MediaPlayer player = MediaPlayer.create(contexto, R.raw.msg);
			player.start();
			
			Toast.makeText(contexto, "Voce recebeu um sms de " + numero, Toast.LENGTH_LONG).show();
			Toast.makeText(contexto, texto, Toast.LENGTH_LONG).show();
		}	
	}
}
