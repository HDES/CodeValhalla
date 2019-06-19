package my_bot.valhalla;

import java.util.ArrayList;
import java.util.List;

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
	/*	//estructura futura
	final map<String, String> comandos = new HashMap<String, String>();
	comandos.put("ping", "ping"); // Añade un elemento al Map
	comandos.put("limpiarPrefijo", "cleanPrefix");
	comandos.put("limpiarBot", "cleanBot");
	comandos.put("limpiarTodo", "cleanAll");
	comandos.put("prefijo", "prefix");
	*/
	final String comando1 = prefijo.concat("ping");
	final String comando2 = prefijo.concat("limpiarPrefijo");	//limpiarPrefijo [prefijo]   			//Borra los mensajes que empizan por prefijo
	final String comando2 = prefijo.concat("limpiarBot");		//limpiarBot [nombreBot] [numeroMensajes]    	//Borra numeroMensajes del bot nombreBot
	final String comando2 = prefijo.concat("limpiarTodo");		//limpiarTodo [numeroMensajes]   		//Borra numeroMensajes sin discriminar
	final String comando4 = prefijo.concat("prefijo");		//prefijo [prefijo]				//Devuelve el prefijo actual. Si recibe un parámetro, cambia el prefijo al nuevo dado

	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if (event.getAuthor().isBot()) return;		//No reacciona a mensajes de otros bots (incluido él mismo)
		
		Message mensaje = event.getMessage();		//Recoge el mensaje que activa el evento
		String content = message.getContentRaw();	//Transforma el mensaje a cadena
		/*ALTERNATIVA PARA PROBAR*/ 
		//String mensaje = event.getMessage().getContentRaw();		//Recoge el mensaje que activa el evento y lo transforma a cadena
		
		// getContentRaw() is an atomic getter
		// getContentDisplay() is a lazy getter which modifies the content for e.g.
		// console view (strip discord formatting)
		if (content.equals(comando1)) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("Holi").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

		} else if (content.equals(comando2)) {
			limpiarPrefijo(event);
			
		} else if (content.equals(comando2)) {
			limpiarBot(event);
			
		} else if (content.equals(comando3)) {
			limpiarTodo(event);
			
		} else if(content.equals(comando4)) {
			
		}
		
	}
	
	
	private void limpiarPrefijo(MessageReceivedEvent evento) 
	{
		TextChannel canal = evento.getTextChannel();	//Recoge el canal de texto donde debe actuar
		int i = 10;		//Contador para salir del bucle (número de mensajes a borrar)
		
		//Para ganar eficiencia, primero guardamos todos los mensajes en una Lista de Message 
		//y luego llamamos a deleteMessages(listaMensajes) para borrarlos todos de una vez
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) {
			if (mensaje.getContentRaw().substring(0, tamPrefijo).equals(prefijo))	//Si un mensaje empieza por prefijo, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (i-- < 0) break;	//Aberración contra natura
		}
		channel.deleteMessages(listaMensajes).queue();		//Borra todos los mensajes especificados en listaMensajes
	}
	
	private void limpiarBot(MessageReceivedEvent evento) 
	{
		TextChannel canal = evento.getTextChannel();	//Recoge el canal de texto donde debe actuar
		int i = 10;		//Contador para salir del bucle (número de mensajes a borrar)
		
		//Para ganar eficiencia, primero guardamos todos los mensajes en una Lista de Message 
		//y luego llamamos a deleteMessages(listaMensajes) para borrarlos todos de una vez
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) {
			if (mensaje.getAuthor().getId().equals(botClientID))	//Si es un mensaje de este bot, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (i-- < 0) break;	//Aberración contra natura
		}
		channel.deleteMessages(listaMensajes).queue();		//Borra todos los mensajes especificados en listaMensajes
	}

	private void limpiarTodo(MessageReceivedEvent evento) 
	{
		TextChannel canal = evento.getTextChannel();	//Recoge el canal de texto donde debe actuar
		int i = 10;		//Contador para salir del bucle (número de mensajes a borrar)
		
		//Para ganar eficiencia, primero guardamos todos los mensajes en una Lista de Message 
		//y luego llamamos a deleteMessages(listaMensajes) para borrarlos todos de una vez
		List<Message> listaMensajes = new ArrayList();	
		for( Message mensaje : canal.getIterableHistory())	//Borra i mensajes incondicionalmente
		{
			listaMensajes.add(mensaje);
			if (i-- < 0) break;	//Aberración contra natura
		}		
		channel.deleteMessages(listaMensajes).queue();		//Borra todos los mensajes especificados en listaMensajes
	}

	//Post: borra los mensajes cuyos cad1 sean iguales a cad2
	private void limpiarGenericaCondicional(MessageReceivedEvent evento, String cad1, String cad2) 
	{
		TextChannel canal = evento.getTextChannel();	//Recoge el canal de texto donde debe actuar
		int i = 10;		//Contador para salir del bucle (número de mensajes a borrar)
		
		//Para ganar eficiencia, primero guardamos todos los mensajes en una Lista de Message 
		//y luego llamamos a deleteMessages(listaMensajes) para borrarlos todos de una vez
		List <Message> listaMensajes = new ArrayList();
		for (Message mensaje : canal.getIterableHistory()) {
			if (cad1.equals(cad2))		//Si cad1 == cad2, se borra
			{
		        	listaMensajes.add(mensaje);
			} 
			if (i-- < 0) break;	//Aberración contra natura
		}
		channel.deleteMessages(listaMensajes).queue();		//Borra todos los mensajes especificados en listaMensajes
	}
	

}
