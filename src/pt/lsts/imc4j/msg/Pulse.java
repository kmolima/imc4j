package pt.lsts.imc4j.msg;

import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.nio.ByteBuffer;

/**
 * Hardware pulse detection.
 */
public class Pulse extends Message {
	public static final int ID_STATIC = 277;

	public String abbrev() {
		return "Pulse";
	}

	public int mgid() {
		return 277;
	}

	public byte[] serializeFields() {
		return new byte[0];
	}

	public void deserializeFields(ByteBuffer buf) throws IOException {
		try {
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
