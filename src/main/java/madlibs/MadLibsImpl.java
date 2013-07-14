package madlibs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class MadLibsImpl implements MadLibs {

	private Prompter prompter;
	private InputReceiver inputReceiver;
	private StoryTeller storyTeller;

	public MadLibsImpl(Prompter prompter, InputReceiver inputReceiver, StoryTeller storyTeller) {
		this.prompter = prompter;
		this.inputReceiver = inputReceiver;
		this.storyTeller = storyTeller;
	}

	public static void main(String[] args) {
		Prompter prompter = new PrompterImpl(System.out);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		InputReceiver inputReceiver = new InputReceiverImpl(reader);
		StoryTeller storyTeller = new StoryTellerImpl(System.out);
		String token1 = "noun", token2 = "verb", token3 = "adjective";
		String story = "The " + createToken(token1) + " was going to " + createToken(token2) + " very " + createToken(token3);
		MadLibs madLibs = new MadLibsImpl(prompter, inputReceiver, storyTeller);
		madLibs.run(story);
	}
	
	public void run(String story) {
		String newStory = story;
		for (String token : getTokens(story)) {
			prompter.prompt(token);
			String userEnteredToken = inputReceiver.receive(token);
			newStory = StringUtils.replace(newStory, "<" + token + ">", userEnteredToken);
		}
		storyTeller.tell(newStory);
	}

	private Set<String> getTokens(String story) {
		String[] tokens = StringUtils.substringsBetween(story, "<", ">");
		return tokens == null ? new HashSet<String>() : new LinkedHashSet<String>(Arrays.asList(tokens));
	}

	public static String createToken(String token) {
		return "<" + token + ">";
	}
}
