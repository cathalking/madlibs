package madlibs;

import java.io.PrintStream;
import java.io.PrintWriter;

public class StoryTellerImpl implements StoryTeller {

	private PrintStream printStream;

	public StoryTellerImpl(PrintStream printStream) {
		this.printStream = printStream;
	}

	public void tell(String story) {
		printStream.println(story);
	}

}
