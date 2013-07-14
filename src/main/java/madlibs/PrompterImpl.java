package madlibs;

import java.io.PrintStream;

public class PrompterImpl implements Prompter {

	private PrintStream out;

	public PrompterImpl(PrintStream out) {
		this.out = out;
	}

	public void prompt(String token) {
		out.println("Please enter " + token + ":");
	}

}
