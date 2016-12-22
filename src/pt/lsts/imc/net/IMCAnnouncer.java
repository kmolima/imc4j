package pt.lsts.imc.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import pt.lsts.imc.actors.ActorContext;
import pt.lsts.imc.actors.IMCActor;
import pt.lsts.imc.annotations.Periodic;
import pt.lsts.imc.annotations.Publish;
import pt.lsts.imc.msg.Announce;

/**
 * This module will send announces to multicast address every 10 seconds (as
 * required by the IMC protocol)
 * 
 * @author zp
 *
 */
public class IMCAnnouncer extends IMCActor {

	private ActorContext context;

	public IMCAnnouncer(ActorContext context) {
		super(context);
		this.context = context;
	}

	private static final String multicastAddress = "224.0.75.69";

	@Periodic(10000)
	@Publish(Announce.class)
	public void sendAnnounces() {
		Announce announce = context.registry().buildAnnounce();
		for (int port = 30100; port < 30105; port++) {
			try {
				byte[] data = announce.serialize();
				DatagramPacket pkt = new DatagramPacket(data, data.length,
						new InetSocketAddress(multicastAddress, port));
				DatagramSocket dsocket = new DatagramSocket();
				dsocket.send(pkt);
				dsocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			post(announce);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
