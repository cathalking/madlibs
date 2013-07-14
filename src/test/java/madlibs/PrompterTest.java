package madlibs;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.fest.assertions.api.Assertions.*;

import java.io.PrintStream;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class PrompterTest {

	@Test
	public void prompt_GivenNoun_ShouldSendPromptMessageUsingNoun() {
		// given
		String noun = "noun";
		PrintStream out = mock(PrintStream.class);
		Prompter prompter = new PrompterImpl(out);
		
		// when
		prompter.prompt(noun);
		
		// then
		ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);
		verify(out).println(argCaptor.capture());
		
		assertThat(argCaptor.getValue()).contains(noun);
	}
	
}
