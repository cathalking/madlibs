package madlibs;

import java.io.BufferedReader;
import java.io.IOException;

public class InputReceiverImpl implements InputReceiver {

	private BufferedReader reader;

	public InputReceiverImpl(BufferedReader reader) {
		this.reader = reader;
	}

	public String receive(String token) {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
