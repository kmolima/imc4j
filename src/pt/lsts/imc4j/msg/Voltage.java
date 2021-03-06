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
 * Report of electrical voltage.
 */
public class Voltage extends Message {
	public static final int ID_STATIC = 251;

	/**
	 * The value of the internal electrical voltage as measured by
	 * the sensor.
	 */
	@FieldType(
			type = IMCField.TYPE_FP32,
			units = "V"
	)
	public float value = 0f;

	public String abbrev() {
		return "Voltage";
	}

	public int mgid() {
		return 251;
	}

	public byte[] serializeFields() {
		try {
			ByteArrayOutputStream _data = new ByteArrayOutputStream();
			DataOutputStream _out = new DataOutputStream(_data);
			_out.writeFloat(value);
			return _data.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public void deserializeFields(ByteBuffer buf) throws IOException {
		try {
			value = buf.getFloat();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
