package pt.lsts.imc4j.msg;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.nio.ByteBuffer;
import pt.lsts.imc4j.annotations.FieldType;
import pt.lsts.imc4j.annotations.IMCField;

/**
 * Measurement of wind speed.
 */
public class WindSpeed extends Message {
	public static final int ID_STATIC = 271;

	/**
	 * Direction of the measured wind speed.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32,
			units = "rad"
	)
	public float direction = 0f;

	/**
	 * The value of the wind speed as measured by the sensor.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32,
			units = "m/s"
	)
	public float speed = 0f;

	/**
	 * Wind turbulence intensity.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32,
			units = "m/s"
	)
	public float turbulence = 0f;

	public String abbrev() {
		return "WindSpeed";
	}

	public int mgid() {
		return 271;
	}

	public byte[] serializeFields() {
		try {
			ByteArrayOutputStream _data = new ByteArrayOutputStream();
			DataOutputStream _out = new DataOutputStream(_data);
			_out.writeFloat(direction);
			_out.writeFloat(speed);
			_out.writeFloat(turbulence);
			return _data.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public void deserializeFields(ByteBuffer buf) throws IOException {
		try {
			direction = buf.getFloat();
			speed = buf.getFloat();
			turbulence = buf.getFloat();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
