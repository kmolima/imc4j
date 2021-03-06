package pt.lsts.imc4j.msg;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.String;
import java.nio.ByteBuffer;
import pt.lsts.imc4j.annotations.FieldType;
import pt.lsts.imc4j.annotations.IMCField;
import pt.lsts.imc4j.util.SerializationUtils;
import pt.lsts.imc4j.util.TupleList;

/**
 * A "Dislodge" is a maneuver ordering the vehicle to attempt a
 * series of thruster operations that will hopefully get it
 * unstuck from an entangled condition.
 * Parameters are RPMs for the motor when attempting dislodge and
 * and a flag specifying whether the thrust burst should be attempted
 * forward, backward or auto (letting the vehicle decide).
 */
public class Dislodge extends Maneuver {
	public static final int ID_STATIC = 483;

	/**
	 * The amount of time the maneuver is allowed to run.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT16,
			units = "s"
	)
	public int timeout = 0;

	/**
	 * Maneuver RPM reference.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32
	)
	public float rpm = 0f;

	/**
	 * Direction to which the vehicle should attempt to unstuck.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT8,
			units = "Enumerated"
	)
	public DIRECTION direction = DIRECTION.values()[0];

	/**
	 * Custom settings for maneuver.
	 */
	@FieldType(
			type = IMCField.TYPE_PLAINTEXT,
			units = "TupleList"
	)
	public TupleList custom = new TupleList("");

	public String abbrev() {
		return "Dislodge";
	}

	public int mgid() {
		return 483;
	}

	public byte[] serializeFields() {
		try {
			ByteArrayOutputStream _data = new ByteArrayOutputStream();
			DataOutputStream _out = new DataOutputStream(_data);
			_out.writeShort(timeout);
			_out.writeFloat(rpm);
			_out.writeByte((int)(direction != null? direction.value() : 0));
			SerializationUtils.serializePlaintext(_out, custom == null? null : custom.toString());
			return _data.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public void deserializeFields(ByteBuffer buf) throws IOException {
		try {
			timeout = buf.getShort() & 0xFFFF;
			rpm = buf.getFloat();
			direction = DIRECTION.valueOf(buf.get() & 0xFF);
			custom = new TupleList(SerializationUtils.deserializePlaintext(buf));
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	public enum DIRECTION {
		DIR_AUTO(0l),

		DIR_FORWARD(1l),

		DIR_BACKWARD(2l);

		protected long value;

		DIRECTION(long value) {
			this.value = value;
		}

		long value() {
			return value;
		}

		public static DIRECTION valueOf(long value) throws IllegalArgumentException {
			for (DIRECTION v : DIRECTION.values()) {
				if (v.value == value) {
					return v;
				}
			}
			throw new IllegalArgumentException("Invalid value for DIRECTION: "+value);
		}
	}
}
