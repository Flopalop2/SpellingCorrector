package spell;

import javax.swing.*;
import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {

		//to run cmd: Main.java dictionarytxtfilename.txt testword
		String dictionaryFileName;
		String inputWord;

		if (args.length > 0) {
			try {
				dictionaryFileName = args[0];
				inputWord = args[1];
			}
			catch (Exception ex) {
				System.out.println("Input not valid");
				return;
			}
		}
		else {
			dictionaryFileName = JOptionPane.showInputDialog(
					null,
					"Please input the dictionary txt name: ",
					"Dictionary",
					JOptionPane.QUESTION_MESSAGE);

			inputWord = JOptionPane.showInputDialog(
					null,
					"Please input word: ",
					"Dictionary",
					JOptionPane.QUESTION_MESSAGE);
		}

		if (dictionaryFileName == null || inputWord == null) {
			JOptionPane.showMessageDialog(null, "Invalid input", "Sup",JOptionPane.ERROR_MESSAGE);
			System.out.println("Invalid input");
			return;
		}
		
		//
        //Create an instance of your corrector here
        //
		ISpellCorrector corrector = new SpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		JOptionPane.showMessageDialog(null, "Suggestion is: " + suggestion, "Sup",JOptionPane.PLAIN_MESSAGE); //change this to give 2 suggestions
		//System.out.println("Suggestion is: " + suggestion);
	}

}
