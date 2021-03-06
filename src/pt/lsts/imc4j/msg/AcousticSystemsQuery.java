package pt.lsts.imc4j.msg;

import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.nio.ByteBuffer;

/**
 * Request a list of known underwater acoustic systems. The
 * recipient of this message shall reply with an AcousticSystems
 * message.
 */
public class AcousticSystemsQuery extends Message {
	public static final int ID_STATIC = 212;

	public String abbrev() {
		return "AcousticSystemsQuery";
	}

	public int mgid() {
		return 212;
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
