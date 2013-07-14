package madlibs;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.PrintStream;

import org.junit.Test;

public class StoryTellerTest {

	@Test
	public void tell_GivenStory_ShouldWriteStory() throws Exception {
		// given
		PrintStream printStream = mock(PrintStream.class);
		StoryTeller storyTeller = new StoryTellerImpl(printStream);
		String story = "Some story";
		
		// when
		storyTeller.tell(story);

		// then
		verify(printStream).println(story);
	}
}
