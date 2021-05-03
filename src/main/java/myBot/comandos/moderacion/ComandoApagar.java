package myBot.comandos.moderacion;

import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ComandoApagar extends Comando {

	// Constructor
	public ComandoApagar(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet = Boolean.FALSE, name, param);
	}

	public ComandoApagar(int id, int level, boolean quiet, Set<String> names, int param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		// HAY QUE AÑADIR UNA FORMA DE QUE SOLO PUEDA EJECUTARSE TENIENDO PERMISOS O
		// METIENDO UNA "CONTRASEÑA" COMO PARÁMETRO
		print(evento);
		System.exit(0);
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			evento.getChannel().sendMessage("Apagando...").queue();
	}
	
}
