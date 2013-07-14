package madlibs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

public class MadLibsTest {

	private MadLibs madLibs;
	private Prompter prompter;
	private InputReceiver inputReceiver;
	private StoryTeller storyTeller;


	@Test
	public void run_GivenStoryWithInvalidToken_ShouldNotPromptForInput() {
		madLibs.run("Token");
		
		verify(prompter, never()).prompt(anyString());
	}
	
	@Test
	public void run_GivenStoryWithToken_ShouldPromptForInput() {
		madLibs.run("<Token>");
		
		verify(prompter).prompt(anyString());
	}
	
	@Test
	public void run_GivenStoryWithoutToken_ShouldNotPromptForInput() {
		madLibs.run("");
		
		verify(prompter, never()).prompt(anyString());
	}
	
	@Test
	public void run_GivenStoryWithTokenTwice_ShouldPromptForInputOnce() {
		madLibs.run("<Token> <Token>");
		
		verify(prompter).prompt(anyString());
		verifyNoMoreInteractions(prompter);
	}
	
	@Test
	public void run_GivenStoryWithTwoDifferentTokens_ShouldPromptForBothTokens() {
		madLibs.run("<a:Token> <b:Token>");
		
		verify(prompter, times(2)).prompt(anyString());
	}
	
	@Test
	public void run_GivenStoryWithInstanceTokenTwice_ShouldPromptForInputOnce() {
		madLibs.run("<a:Token> <a:Token>");
		
		verify(prompter, times(1)).prompt(anyString());
	}
	
	@Test
	public void run_GivenStoryWithToken_ShouldPromptUsingThatToken() {
		String token = "Token";
		String story = createToken(token);
		
		madLibs.run(story);
		
		verify(prompter).prompt(token);
	}
	
	
	@Test
	public void run_GivenStoryWithTokenAndVerb_ShouldPromptForBoth() {
		String verb = "verb";
		String token = "Token";
		String story = createToken(verb) + createToken(token);
		
		madLibs.run(story);
		
		verify(prompter).prompt(token);
		verify(prompter).prompt(verb);
	}

	@Test
	public void run_GivenStoryWithToken_ShouldReceiveToken() {
		String token = "Token";
		String story = createToken(token);
		
		madLibs.run(story);
		
		verify(inputReceiver).receive(token);
	}
	
	@Test
	public void run_GivenStoryWithTokenAndUserEnteredAToken_ShouldReplaceTokenWithUserTokenInStory() {
		// given
		String token = "noun";
		String story = createToken(token);
		String userToken = "church";
		given(inputReceiver.receive(token)).willReturn(userToken);
		
		// when
		madLibs.run(story);
		
		ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);

		// then
		verify(storyTeller).tell(argCaptor.capture());
		assertThat(argCaptor.getValue()).contains(userToken);
		assertThat(argCaptor.getValue()).doesNotContain(token);
	}
	
	
	@Test
	public void run_GivenStoryWithTwoTokensAndUserEnteredTwoTokens_ShouldReplaceTokensWithUserTokensInStory() {
		// given
		String token1 = "noun";
		String token2 = "verb";
		String story = "The " + createToken(token1) + " was going to " + createToken(token2);
		String userToken1 = "church";
		String userToken2 = "build";
		given(inputReceiver.receive(token1)).willReturn(userToken1);
		given(inputReceiver.receive(token2)).willReturn(userToken2);
		
		// when
		madLibs.run(story);
		
		ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);

		// then
		verify(storyTeller).tell(argCaptor.capture());
		assertThat(argCaptor.getValue()).contains(userToken1);
		assertThat(argCaptor.getValue()).doesNotContain(token1);
		assertThat(argCaptor.getValue()).contains(userToken2);
		assertThat(argCaptor.getValue()).doesNotContain(token2);
	}
	
	
	@Test
	public void run_GivenStoryWithThreeTokensAndUserEnteredAllTokens_ShouldReplaceTokensWithUserTokensInStory() {
		// given
		String token1 = "noun";
		String token2 = "verb";
		String token3 = "adjective";
		String story = "The " + createToken(token1) + " was going to " + createToken(token2) + " very " + createToken(token3);
		String userToken1 = "church";
		String userToken2 = "build";
		String userToken3 = "quickly";
		given(inputReceiver.receive(token1)).willReturn(userToken1);
		given(inputReceiver.receive(token2)).willReturn(userToken2);
		given(inputReceiver.receive(token3)).willReturn(userToken3);
		
		// when
		madLibs.run(story);
		
		// then
		ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);
		verify(storyTeller).tell(argCaptor.capture());
		String madLibsStory = "The " + userToken1 + " was going to " + userToken2 + " very " + userToken3;
		assertThat(argCaptor.getValue()).isEqualTo(madLibsStory);
	}
	
	@Test
	public void main_oGivenStory_ShouldRunTillUserEntersAllTokensAndNewStoryIsOutput() {
		// given
		String token1 = "noun";
		String token2 = "adjective";
		String story = "The " + createToken(token1) + " was going to " + createToken(token2);
		String userToken1 = "church";
		String userToken2 = "build";
		given(inputReceiver.receive(token1)).willReturn(userToken1);
		given(inputReceiver.receive(token2)).willReturn(userToken2);
		
		// when
		madLibs.run(story);
		
		// then
		String madLibsStory = "The " + userToken1 + " was going to " + userToken2;
		verify(prompter, times(2)).prompt(anyString());
		verify(inputReceiver, times(2)).receive(anyString());
		verify(storyTeller).tell(madLibsStory);
	}
	
	
	@Before
	public void setup() {
		prompter = mock(Prompter.class);
		inputReceiver = mock(InputReceiver.class);
		storyTeller = mock(StoryTeller.class);
		madLibs = new MadLibsImpl(prompter, inputReceiver, storyTeller);
	}	
	
	private String createToken(String token) {
		return "<" + token + ">";
	}
}
