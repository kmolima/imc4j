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
 * Desired Pitch angle reference value for the control layer.
 */
public class DesiredPitch extends ControlCommand {
	public static final int ID_STATIC = 404;

	/**
	 * The value of the desired pitch angle.
	 */
	@FieldType(
			type = IMCField.TYPE_FP64,
			units = "rad"
	)
	public double value = 0;

	public String abbrev() {
		return "DesiredPitch";
	}

	public int mgid() {
		return 404;
	}

	public byte[] serializeFields() {
		try {
			ByteArrayOutputStream _data = new ByteArrayOutputStream();
			DataOutputStream _out = new DataOutputStream(_data);
			_out.writeDouble(value);
			return _data.toByteArray();
		}
		catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public void deserializeFields(ByteBuffer buf) throws IOException {
		try {
			value = buf.getDouble();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
