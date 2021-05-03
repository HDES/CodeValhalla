package myBot;

import javax.security.auth.login.LoginException;

import myBot.listeners.MyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

	public static void main( String[] args ) throws LoginException, InterruptedException {
    		System.out.println("CONECTANDO");
	    	//@SuppressWarnings("unused")
			//JDA api = new JDABuilder(Key.token).addEventListener(new MyListener()).build().awaitReady();
	    	//el método awaitReady hace "Blocking" garantizando que el JDA se cargará correctamente 
    		JDA jda = JDABuilder.createDefault(Key.token).build().awaitReady();
    		jda.addEventListener(new MyListener());
	    	System.out.println("HEMOS CONECTADO");
	    	
	   /* 	try 
	    	{  	
    	}
    	catch (LoginException e) 
    	{
    		// Esto avisará de error en caso de que haya fallo en el proceso de autentificación
    		e.printStackTrace();
    	}
    	
    	catch (InterruptedException e) 
    	{
    		// Controla el método awaitReady()
    		e.printStackTrace();
    	}*/
 
	}
}
