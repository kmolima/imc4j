package pt.lsts.imc.net;

import java.util.ArrayList;
import java.util.HashSet;

import com.squareup.otto.Subscribe;

import pt.lsts.imc.annotations.Periodic;
import pt.lsts.imc.msg.Announce;
import pt.lsts.imc.msg.Heartbeat;

/**
 * This module will send IMC Heartbeats to all peers.
 * A peer can be added by its name or automatically by using autoconnect().
 * @see IMCBeater#addRecipient(String)
 * @see IMCBeater#setAutoConnect(boolean)
 * @author zp
 *
 */
public class IMCBeater {

	private HashSet<String> recipients = new HashSet<>();
	private boolean autoConnect = true;
	
	/**
	 * Retrieve current autoconnect mode.
	 * @see #setAutoConnect(boolean)
	 */
	public final boolean isAutoConnect() {
		return autoConnect;
	}

	/**
	 * Whenever an announce arrives, start sending heartbeats to that source
	 */
	public final void setAutoConnect(boolean autoConnect) {
		this.autoConnect = autoConnect;
	}

	@Subscribe
	public void on(Announce ann) {
		if (isAutoConnect())
			addRecipient(ann.sys_name);
	}

	@Periodic(1000)
	public void sendHeartbeat() {
		synchronized (recipients) {
			for (String dst : recipients)
				try {
					IMCNetwork.sendUdp(new Heartbeat(), dst);
				} catch (Exception e) {
					
				}
		}
	}
	
	/**
	 * Start sending heartbeats to given peer
	 * @param peer The name of the system (matching a received {@link Announce#sys_name}). 
	 */
	public void addRecipient(String peer) {
		synchronized (recipients) {
			recipients.add(peer);
		}
	}
	
	/**
	 * Remove a peer from list of peers.
	 * @param peer The peer to be removed
	 */
	public void removeRecipient(String peer) {
		synchronized (recipients) {
			recipients.remove(peer);
		}
	}
	
	/**
	 * Retrieve current list of peers
	 * @return 
	 */
	public ArrayList<String> getRecipients() {
		ArrayList<String> ret = new ArrayList<>();
		synchronized (recipients) {
			ret.addAll(recipients);
		}		
		return ret;
	}
	
}