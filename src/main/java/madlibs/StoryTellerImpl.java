package madlibs;

import java.io.PrintStream;

public class StoryTellerImpl implements StoryTeller {

	private PrintStream printStream;

	public StoryTellerImpl(PrintStream printStream) {
		this.printStream = printStream;
	}

	public void tell(String story) {
		printStream.println(story);
	}

}
