package myBot.comandos.utiles;

import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ComandoBorrarAnclados extends Comando {

	// Constructor
	public ComandoBorrarAnclados(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoBorrarAnclados(int id, int level, boolean quiet, Set<String> names, int param) {
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
		else if (mensajeDiv[1].equals("ON"))
			MyListener.estado.borrarAnclados = true;
		else if (mensajeDiv[1].equals("OFF"))
			MyListener.estado.borrarAnclados = false;
		else
			return;/* LANZAR EXCEPCION */
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			if (MyListener.estado.borrarAnclados)
				evento.getChannel().sendMessage("Borrar mensajes anclados => ON").queue();
			else
				evento.getChannel().sendMessage("Borrar mensajes anclados => OFF").queue();
	}
}
