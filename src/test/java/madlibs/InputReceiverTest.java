package madlibs;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.Test;

import static org.mockito.BDDMockito.*;
import static org.fest.assertions.api.Assertions.*;

public class InputReceiverTest {

	@Test
	public void receive_GivenToken_ShouldWaitForUserInput() throws IOException {
		// given
		BufferedReader reader = mock(BufferedReader.class);
		InputReceiver inputReceiver = new InputReceiverImpl(reader);
		String token = "noun";
		
		// when
		inputReceiver.receive(token);
		
		// then
		verify(reader).readLine();
	}
	
	@Test
	public void receive_GivenTokenAndUserInput_ShouldReturnUserInput() throws IOException {
		// given
		BufferedReader reader = mock(BufferedReader.class);
		InputReceiver inputReceiver = new InputReceiverImpl(reader);
		String token = "noun";
		String expected = "church";
		given(reader.readLine()).willReturn(expected);
		
		// when
		String input = inputReceiver.receive(token);
		
		// then
		assertThat(input).isEqualTo(expected);
	}

}
