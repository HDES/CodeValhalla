package myBot.comandos.generales;

import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ComandoAyuda extends Comando {
	// Constructor
	public ComandoAyuda(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoAyuda(Integer id, Integer level, Boolean quiet, Set<String> names, Integer param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+"); // Se divide el mensaje por
																						// espacios en distintas
																						// subcadenas

		if (!numeroParametros(evento.getMessage().getContentRaw(),
				MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))))
			/* LANZAR EXCEPCION */ return; // Se comprueba que el número de parámetros sea correcto

		print(evento);
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			evento.getChannel().sendMessage(MyListener.contenidoAyuda).queue();
			evento.getMember().getRoles();
	}

}
