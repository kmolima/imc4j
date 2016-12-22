package pt.lsts.imc4j.runtime.actors;

import java.util.concurrent.Future;

import pt.lsts.imc4j.msg.Message;
import pt.lsts.imc4j.net.IMCQuery;
import pt.lsts.imc4j.net.IMCRegistry;
import pt.lsts.imc4j.runtime.clock.ActorClock;

public interface ActorContext {

	/**
	 * Post a message to this context
	 * @param msg The message to be posted
	 */
	public void post(Message msg);
	
	/**
	 * Send a message to an external destination (asynchronously)
	 * @param msg The message to send
	 * @param destination The name of the destination
	 * @throws In case the remote end cannot be reached or it is unknown
	 */
	public void send(Message msg, String destination) throws Exception;
	
	/**
	 * Send a message reliably to destination
	 * @param msg The message to send
	 * @param destination Where to send the message
	 * @return A Future object that can be polled to check delivery result
	 */
	public Future<Boolean> deliver(Message msg, String destination);	
	
	/**
	 * Register an Actor in this context
	 * @param actor An actor
	 * @param name The name of the actor to be used in the context
	 * @return The entity id associated with the actor
	 */
	public int register(AbstractActor actor, String name);
	
	public void unregister(AbstractActor actor);
		
	/**
	 * @return Clock to use in this context
	 */
	public ActorClock clock();
	
	/**
	 * @return Registry used to resolve platform and entity names
	 */
	public IMCRegistry registry();

	public <T extends Message> IMCQuery<T> query(Class<T> clazz);
	
	public IMCQuery<Message> query(String abbrev);
	
	public void start();
	
	public void stop();
}