package pt.lsts.imc4j.msg;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.nio.ByteBuffer;
import pt.lsts.imc4j.annotations.FieldType;
import pt.lsts.imc4j.annotations.IMCField;
import pt.lsts.imc4j.def.Boolean;
import pt.lsts.imc4j.def.SpeedUnits;
import pt.lsts.imc4j.def.ZUnits;
import pt.lsts.imc4j.util.SerializationUtils;
import pt.lsts.imc4j.util.TupleList;

/**
 * A "Sample" is a maneuver specifying a movement of the vehicle to a
 * target waypoint. The waypoint is described by the WGS-84
 * waypoint coordinate and target Z reference.
 * Mandatory parameters defined for a "Goto" are
 * timeout, speed and speed units.
 */
public class Sample extends Maneuver {
	public static final int ID_STATIC = 489;

	/**
	 * The amount of time the maneuver is allowed to run.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT16,
			units = "s"
	)
	public int timeout = 0;

	/**
	 * WGS-84 Latitude of target waypoint.
	 */
	@FieldType(
			type = IMCField.TYPE_FP64,
			max = 1.5707963267948966,
			min = -1.5707963267948966,
			units = "rad"
	)
	public double lat = 0;

	/**
	 * WGS-84 Longitude of target waypoint.
	 */
	@FieldType(
			type = IMCField.TYPE_FP64,
			max = 3.141592653589793,
			min = -3.141592653589793,
			units = "rad"
	)
	public double lon = 0;

	/**
	 * Target reference in the z axis. Use z_units to specify
	 * whether z represents depth, altitude or other.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32,
			units = "m"
	)
	public float z = 0f;

	/**
	 * Units of the z reference.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT8,
			units = "Enumerated"
	)
	public ZUnits z_units = ZUnits.values()[0];

	/**
	 * Maneuver speed reference.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32
	)
	public float speed = 0f;

	/**
	 * Speed units.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT8,
			units = "Enumerated"
	)
	public SpeedUnits speed_units = SpeedUnits.values()[0];

	/**
	 * True when sampling with servo 0.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT8,
			units = "Enumerated"
	)
	public Boolean syringe0 = Boolean.values()[0];

	/**
	 * True when sampling with servo 1.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT8,
			units = "Enumerated"
	)
	public Boolean syringe1 = Boolean.values()[0];

	/**
	 * True when sampling with servo 2.
	 */
	@FieldType(
			type = IMCField.TYPE_UINT8,
			units = "Enumerated"
	)
	public Boolean syringe2 = Boolean.values()[0];

	/**
	 * Custom settings for maneuver.
	 */
	@FieldType(
			type = IMCField.TYPE_PLAINTEXT,
			units = "TupleList"
	)
	public TupleList custom = new TupleList("");

	public String abbrev() {
		return "Sample";
	}

	public int mgid() {
		return 489;
	}

	public byte[] serializeFields() {
		try {
			ByteArrayOutputStream _data = new ByteArrayOutputStream();
			DataOutputStream _out = new DataOutputStream(_data);
			_out.writeShort(timeout);
			_out.writeDouble(lat);
			_out.writeDouble(lon);
			_out.writeFloat(z);
			_out.writeByte((int)(z_units != null? z_units.value() : 0));
			_out.writeFloat(speed);
			_out.writeByte((int)(speed_units != null? speed_units.value() : 0));
			_out.writeByte((int)(syringe0 != null? syringe0.value() : 0));
			_out.writeByte((int)(syringe1 != null? syringe1.value() : 0));
			_out.writeByte((int)(syringe2 != null? syringe2.value() : 0));
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
			lat = buf.getDouble();
			lon = buf.getDouble();
			z = buf.getFloat();
			z_units = ZUnits.valueOf(buf.get() & 0xFF);
			speed = buf.getFloat();
			speed_units = SpeedUnits.valueOf(buf.get() & 0xFF);
			syringe0 = Boolean.valueOf(buf.get() & 0xFF);
			syringe1 = Boolean.valueOf(buf.get() & 0xFF);
			syringe2 = Boolean.valueOf(buf.get() & 0xFF);
			custom = new TupleList(SerializationUtils.deserializePlaintext(buf));
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
