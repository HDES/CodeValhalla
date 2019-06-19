package my_bot.valhalla;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MyListener extends ListenerAdapter {

	final String prefijo = "!!"; // Prefijo usado antes de los comandos
	final int tamPrefijo = prefijo.length(); // Longitud de la cadena del prefijo
	final String comando1 = prefijo.concat("ping");
	final String comando2 = prefijo.concat("limpiar");
	final String comando3 = prefijo.concat("borrar");
	final String comando4 = prefijo.concat("prefix");
	final String botClientID = "525273189287198721";

	@Override
	public void onMessageReceived(MessageReceivedEvent event){
		if (event.getAuthor().isBot())
			return;
		// We don't want to respond to other bot accounts, including ourself
		Message message = event.getMessage();
		String content = message.getContentRaw();
		// getContentRaw() is an atomic getter
		// getContentDisplay() is a lazy getter which modifies the content for e.g.
		// console view (strip discord formatting)
		if (content.equals(comando1)) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("Holi").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

		} else if (content.equals(comando2)) {
			borrarMensajesBot(event);
			
		} else if (content.equals(comando3)) {
			borrarMensajes(event);
			
		} else if(content.equals(comando4)) {
			
		}
		
	}
	

	private void borrarMensajes(MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		int i = 10;
		List<Message> messageList = new ArrayList();
		for( Message message3 : channel.getIterableHistory()) {
			messageList.add(message3);
			if (i-- < 0)
				break;	
			}		
		channel.deleteMessages(messageList).queue();
	}

	private void borrarMensajesBot(MessageReceivedEvent event) {
		TextChannel channel = event.getTextChannel();
		int i = 10;
		List <Message> messageList = new ArrayList();
		for (Message message2 : channel.getIterableHistory()) {// substring lee desde la posicion 0, 1 carácter
			if (message2.getAuthor().getId().equals(botClientID) || message2.getContentRaw().substring(0, tamPrefijo).equals(prefijo)) {
		         messageList.add(message2);
			} 

			if (i-- < 0)
				break;
				
			}
		channel.deleteMessages(messageList).queue();
	} 

}
