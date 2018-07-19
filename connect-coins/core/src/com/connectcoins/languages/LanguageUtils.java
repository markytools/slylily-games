package com.connectcoins.languages;

import java.util.LinkedHashSet;
import java.util.Set;

public class LanguageUtils {
	public static String filterSameCharacters(String characters){
		char[] chars = characters.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
		    charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
		    sb.append(character);
		}
		return sb.toString();
	}
}
