package myBot.comandos.generales;

import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ComandoOpciones extends Comando {
	// Constructor
	public ComandoOpciones(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoOpciones(int id, int level, boolean quiet, Set<String> names, int param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		print(evento);
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso) {
			String cadena = "```"; // Formato Discord: texto estilo código

			cadena += "Borrar mensajes anclados => ";
			if (MyListener.estado.borrarAnclados)
				cadena += "ON";
			else
				cadena += "OFF";
			cadena += "\n";

			cadena += "Número de mensajes borrados => ";
			cadena += Integer.toString(MyListener.estado.numMensajes);
			cadena += "\n";

			cadena += "Modo silencioso => ";
			if (MyListener.estado.silencioso)
				cadena += "ON";
			else
				cadena += "OFF";
			cadena += "\n";

			cadena += "```"; // Formato Discord: texto estilo código

			evento.getChannel().sendMessage(cadena).queue();
		}
	}

	{
	}
}
