package my_bot.valhalla;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Bot 
{
    public static void main( String[] args )
    {
    	try 
    	{  		
	    	@SuppressWarnings("unused")
			JDA api = new JDABuilder(Key.token).addEventListener(new MyListener()).build().awaitReady();
	    	//el método awaitReady hace "Blocking" garantizando que el JDA se cargará correctamente
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
    	}
    	
    }
    
}
