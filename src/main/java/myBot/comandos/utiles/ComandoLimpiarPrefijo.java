package myBot.comandos.utiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import myBot.comandos.Comando;
import myBot.listeners.MyListener;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ComandoLimpiarPrefijo extends Comando {
	// Constructor
	public ComandoLimpiarPrefijo(int id, int level, boolean quiet, String name, int param) {
		super(id, level, quiet, name, param);
	}

	public ComandoLimpiarPrefijo(int id, int level, boolean quiet, Set<String> names, int param) {
		super(id, level, quiet, names, param);
	}

	public void run(MessageReceivedEvent evento) {
		String[] mensajeDiv = evento.getMessage().getContentRaw().trim().split("\\s+"); // Se divide el mensaje por
																						// espacios en distintas
																						// subcadenas

		if (!numeroParametros(evento.getMessage().getContentRaw(),
				MyListener.comandosPar.get(mensajeDiv[0].substring(MyListener.tamPrefijo))))
			/* LANZAR EXCEPCION */ return; // Se comprueba que el número de parámetros sea correcto

		if (mensajeDiv.length == 1) {
			limpiarPrefijoAux(evento, MyListener.prefijo, MyListener.estado.numMensajes);
		} // No recibe parámetros. Borra usando el prefijo del bot con numMensajes por
			// defecto
		else if (mensajeDiv.length == 2) {
			limpiarPrefijoAux(evento, mensajeDiv[1], MyListener.estado.numMensajes);
		} // Recibe un prefijo. Borra numMensajes mensajes por defecto que empiecen por el
			// par1
		else if (mensajeDiv.length == 3) {
			try {
				limpiarPrefijoAux(evento, mensajeDiv[1], Integer.parseInt(mensajeDiv[2]));
			} catch (NumberFormatException e) {
				/* LANZAR EXCEPCION */}
		} // Recibe dos parámetros. Borra par2 mensajes que empiecen por par1
	}

	public void print(MessageReceivedEvent evento) {
		if (!isQuiet() && !MyListener.estado.silencioso)
			evento.getChannel().sendMessage("Mensajes eliminados: " + listaMensajes.size()).queue();
	}

	List<Message> listaMensajes = new ArrayList<Message>();

	// Post: borra par2 mensajes que empiecen por par1
	private void limpiarPrefijoAux(MessageReceivedEvent evento, String par1, Integer par2) {
		TextChannel canal = evento.getTextChannel();
		// for (MessagePaginationAction iter = canal.getIterableHistory(); par2-- < 0;
		// iter = iter.)
		for (Message mensaje : canal.getIterableHistory()) {
			if (mensaje.getContentRaw().substring(0, par1.length()).equals(par1)) // Si cad1 == cad2, se borra
			{
				listaMensajes.add(mensaje);
			}
			if (par2-- < 0)
				break; // Aberración contra natura
		}
		if (!listaMensajes.isEmpty())
			canal.deleteMessages(listaMensajes).queue();

		print(evento);
		listaMensajes.clear(); // Como listaMensajes es del ámbito de la clase habrá que vaciarla manualmente,
								// ya que no se vaciará automáticamente al terminar la función limpiarBotAux
	}

	{
	}
}
