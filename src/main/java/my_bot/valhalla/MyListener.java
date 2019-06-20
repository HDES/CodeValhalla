package my_bot.valhalla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MyListener extends ListenerAdapter {

	final String botClientID = "525273189287198721";	//ID del Cliente del bot en Discord
	
	/*final*/ String prefijo = "!!"; // Prefijo usado antes de los comandos
	/**PREFIJO**/
	//prefijo ya no es final para poder modificarlo
	final int tamPrefijo = prefijo.length(); // Longitud de la cadena del prefijo
	
	/**COMANDOS**/ 	
	Map<String, String> comandos = new HashMap<>();
	
	final String comando1 = prefijo.concat("ping");
	final String comando2 = prefijo.concat("limpiarPrefijo");	//limpiarPrefijo [prefijo]	//Borra los mensajes que empizan por prefijo
	final String comando3 = prefijo.concat("limpiarBot");		//limpiarBot [nombreBot] [numeroMensajes]	//Borra numeroMensajes del bot nombreBot
	final String comando4 = prefijo.concat("limpiarTodo");		//limpiarTodo [numeroMensajes]	//Borra numeroMensajes sin discriminar
	final String comando5 = prefijo.concat("prefijo");		//prefijo [prefijo]	//Devuelve el prefijo actual. Si recibe un parámetro, cambia el prefijo al nuevo dado
	final String comando6 = prefijo.concat("silencioso");		//quiet [ON|OFF]	//Si se activa borra siempre el mensaje del comando utilizado y el que muestre el bot(si ese comando muestra algo)

	MessageChannel canal;
	
	@Override
	public void onMessageReceived(MessageReceivedEvent evento)
	{	
		//if ( !controlEntrada(evento) ) return; 
			
		String mensaje = evento.getMessage().getContentRaw();		//Recoge el mensaje que activa el evento y lo transforma a cadena, atomic getter¿?
		canal = evento.getChannel();
		int i;
		for ( i = 0; i < mensaje.length() && mensaje.charAt(i) != ' '; i++); // comprueba que llegue hasta el final del mensaje o si detecta un espacio al final de este
		String mensajeCad = (i == mensaje.length()) ? mensaje : mensaje.substring(0, i-1); // 
		//comprueba lo que hay a la izquierda de ?, si es true devuelve lo que hay a la izquierda de : o si es false devuelve lo que hay a la derecha de : (operador ternario)	
		
		if ( mensajeCad.equals(comando1) ) 
		{
			canal.sendMessage("Holi").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
		} 
		else if (mensajeCad.equals(comando2) && numeroParametros(mensajeCad, 1)) 
		{
			canal.sendMessage("Holi").queue();
			String[] param = recogerParametros(mensajeCad, comando2);
			limpiarPrefijo(evento, param[0]);
		} 
		else if (mensajeCad.equals(comando3) ) 
		{
			limpiarBot(evento);
		} 
		else if (mensajeCad.equals(comando4) ) 
		{
			limpiarTodo(evento);
		}
		else if (mensajeCad.equals(comando5) ) 
		{
			//cambiar o mostrar prefijo
		}
		else if (mensajeCad.equals(comando6) ) 
		{
			//activar modo silencioso
		}
		
	}
	
	//limpiarPrefijo [prefijo]	//Borra los mensajes que empizan por prefijo
	private void limpiarPrefijo(MessageReceivedEvent evento, String param1) 
	{
		TextChannel canal = evento.getTextChannel();	//Recoge el canal de texto donde debe actuar
		int i = 10;		//Contador para salir del bucle (número de mensajes a borrar)
		
		//Para ganar eficiencia, primero guardamos todos los mensajes en una Lista de Message 
		//y luego llamamos a deleteMessages(listaMensajes) para borrarlos todos de una vez
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getContentRaw().substring(0, tamPrefijo).equals(param1))	//Si un mensaje empieza por prefijo, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (i-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		//Borra todos los mensajes especificados en listaMensajes
	}

	//limpiarBot [nombreBot] [numeroMensajes]	//Borra numeroMensajes del bot nombreBot
	private void limpiarBot(MessageReceivedEvent evento) 
	{
		TextChannel canal = evento.getTextChannel();	
		int i = 10;		
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (mensaje.getAuthor().getId().equals(botClientID))	//Si es un mensaje de este bot, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (i-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		
	}

	//limpiarTodo [numeroMensajes]	//Borra numeroMensajes sin discriminar
	private void limpiarTodo(MessageReceivedEvent evento) 
	{
		TextChannel canal = evento.getTextChannel();	
		int i = 10;		
		List<Message> listaMensajes = new ArrayList();	
		for( Message mensaje : canal.getIterableHistory())	//Borra i mensajes incondicionalmente
		{
			listaMensajes.add(mensaje);
			if (i-- < 0) break;	//Aberración contra natura
		}		
		canal.deleteMessages(listaMensajes).queue();	
	}

	//Post: borra los mensajes cuyos cad1 sean iguales a cad2
	private void limpiarGenericaCondicional(MessageReceivedEvent evento, String cad1, String cad2) 
	{
		TextChannel canal = evento.getTextChannel();	
		int i = 10;		
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) 
		{
			if (cad1.equals(cad2))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (i-- < 0) break;	//Aberración contra natura
		}
		canal.deleteMessages(listaMensajes).queue();		
	}
	
	private boolean controlEntrada(MessageReceivedEvent event)
	{
		boolean valida = true;	//Bandera para saber si la entrada es válida o no
		boolean finCadenas = true;
		
		/*COMPROBACIONES*/
		if ( event.getAuthor().isBot() ) valida = false;	//No reacciona a mensajes de otros bots (incluido él mismo)
		
		String mensaje = event.getMessage().getContentRaw();	//Recoge el mensaje que activa el evento y lo transforma a cadena
		
		String[] mensajeDiv = mensaje.trim().split("\\s+");	//Se divide el mensaje por espacios en distintas subcadenas
		int i = 0;
		while (finCadenas)
		{
			try { String basura = mensajeDiv[i]; ++i; } catch (ArrayIndexOutOfBoundsException e) { finCadenas = false; }
		}
		
		/*COMPROBACIONES*/
		if ( !mensajeDiv[0].equals(prefijo) ) valida = false;	//El mensaje no empieza por el prefijo actual
		if ( comandos.get(mensajeDiv[1]) == null ) valida = false;	//El comando no existe
		
		return valida;
	}

	//Devuelve true si 0 <= numeroParamentros <= maximo, false en caso contrario
	private boolean numeroParametros(String mensaje, int maximo)
	{
		canal.sendMessage("numParam").queue();
	
		boolean finCadenas = true;
		String[] mensajeDiv = mensaje.trim().split("\\s+");	//Se divide el mensaje por espacios en distintas subcadenas
		int i = 1;
		while ( finCadenas )
		{
			try { String basura = mensajeDiv[i]; ++i; } catch (ArrayIndexOutOfBoundsException e) { finCadenas = false; }
		}
		return (i-1 >= 0 && i-1 <= maximo);
	}
	
	private void inicializarComandosMap() {
		comandos.put("ping", "ping"); // Añade un elemento al Map
		comandos.put("limpiarPrefijo", "cleanPrefix");
		comandos.put("limpiarBot", "cleanBot");
		comandos.put("limpiarTodo", "cleanAll");
		comandos.put("prefijo", "prefix");
		comandos.put("silencioso", "quiet");
	}
	
	private String[] recogerParametros(String mensaje, String comando) {
		// 	Separamos parámetros que vienen después del comando
		//  Ejemplo: "!!limpiarBot Groovy 25" -> " Groovy 25"
		return mensaje.substring(comando.length()).trim().split("\\s+");	

	}

}
