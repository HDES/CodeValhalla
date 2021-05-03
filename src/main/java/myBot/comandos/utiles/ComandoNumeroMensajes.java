package myBot.comandos.utiles;

import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ComandoNumeroMensajes extends Comando {
	// Constructor
	public ComandoNumeroMensajes(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoNumeroMensajes(int id, int level, boolean quiet, Set<String> names, int param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+"); // Se divide el mensaje por
																						// espacios en distintas
																						// subcadenas

		if (!numeroParametros(evento.getMessage().getContentRaw(),
				MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))))
			/* LANZAR EXCEPCION */ return; // Se comprueba que el número de parámetros sea correcto

		if (mensajeDiv.length == 1)
			print(evento);
		else {
			MyListener.estado.numMensajes = Integer.valueOf(mensajeDiv[1]);
			print(evento);
		}
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			evento.getChannel().sendMessage("Número de mensajes borrados => " + MyListener.estado.numMensajes).queue();
	}

	{
	}
}
