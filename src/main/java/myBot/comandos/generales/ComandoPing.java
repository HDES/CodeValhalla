package myBot.comandos.generales;

import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ComandoPing extends Comando {
	// Constructor
	public ComandoPing(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoPing(int id, int level, boolean quiet, Set<String> names, int param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		print(evento);
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			evento.getChannel().sendMessage("Apagando...").queue(); // Important to call .queue() on the RestAction
																	// returned by sendMessage(...)
	}

	{

	}
}
