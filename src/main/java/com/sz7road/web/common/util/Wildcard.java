/**
 * $Header: /data/cvs/chinasiteplatform/src/java/com/gs/sitecore/common/file/Wildcard.java,v 1.4 2008/12/04 06:18:01 mzheng Exp $ 
 * $Revision: 1.4 $ 
 * $Date: 2008/12/04 06:18:01 $ 
 * 
 * ==================================================================== 
 * 
 * Copyright (c) 2006 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 * 
 */

package com.sz7road.web.common.util;

/**
 *  <p>
 *  Check whether a string matches a wildcard.
 *  </p>
 *  <p>
 *  A wildcard is same as dos/unix file wildcard.
 *  </p>
 *  <blockquote>
 *     *  any zero or more characters
 *     ?  any one character
 *     c  character c
 *  </blockquote>
 *  Note: not the most efficient algorithm.
 *  
 * @author Jansen Wang
 * @version $Id: Wildcard.java,v 1.4 2008/12/04 06:18:01 mzheng Exp $
 */
public class Wildcard {

	/**
	 * Wildcard separator
	 */
	public static final String WILDCARD_SEPARATOR = ";";

	/**
	 *  Check if pattern string matches text string.<br>
	 *  At the beginning of iteration i of main loop<br>
	 *     old[j]    = true if pattern[0..j] matches text[0..i-1]<br> 
	 *  By comparing pattern[j] with text[i], the main loop computes<br>
	 *     states[j] = true if pattern[0..j] matches text[0..i] 
	 *@param pattern the wildcard pattern
	 *@param str the string to be check.
	 *@return true if str matches pattern, otherwise return false.
	 */
	private static boolean checkMatches(String pattern, String str) {
		//If str is null, then return false.
		if (str == null)
			return false;
		// add sentinel so don't need to worry about *'s at end of pattern
		str += '\0';
		pattern += '\0';

		int N = pattern.length();

		boolean[] states = new boolean[N + 1];
		boolean[] old = new boolean[N + 1];
		old[0] = true;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			states = new boolean[N + 1]; // initialized to false
			for (int j = 0; j < N; j++) {
				char p = pattern.charAt(j);

				// hack to handle *'s that match 0 characters
				if (old[j] && (p == '*'))
					old[j + 1] = true;

				if (old[j] && (p == c))
					states[j + 1] = true;
				if (old[j] && (p == '?'))
					states[j + 1] = true;
				if (old[j] && (p == '*'))
					states[j] = true;
				if (old[j] && (p == '*'))
					states[j + 1] = true;
			}
			old = states;
		}
		return states[N];
	}

	/**
	 * Check whether <code>fileName</code> matches <code>wildcard</code>.
	 * @param wildcard The wildcard
	 * @param str The String to be check, e.g. file name
	 * @param The delimiter of string
	 * @return Whether the string matches the wildcard
	 */
	public static boolean matches(String wildcard, String str, String delimiter) {
		String[] eachWildcard = wildcard.split(delimiter);
		for (int i = 0; i < eachWildcard.length; i++) {
			if (checkMatches(eachWildcard[i], str))
				return true;
		}
		return false;
	}

	/**
	 * Check whether <code>fileName</code> matches <code>wildcard</code>.
	 * But case-insensitive.
	 * @param wildcard The wildcard
	 * @param str The String to be check, e.g. file name
	 * @param The delimiter of string
	 * @return Whether the string matches the wildcard
	 */
	public static boolean matchesIgnoreCase(String wildcard, String str, String delimiter) {
		return matches(wildcard.toLowerCase(), str.toLowerCase(), delimiter);
	}

	/**
	 * Check whether <code>fileName</code> matches <code>wildcard</code> using default
	 * demiliter ";".
	 * @param wildcard The wildcard
	 * @param str The String to be check, e.g. file name
	 * @return Whether the string matches the wildcard
	 */
	public static boolean matches(String wildcard, String str) {
		return matches(wildcard, str, WILDCARD_SEPARATOR);
	}

	/**
	 * Check whether <code>fileName</code> matches <code>wildcard</code> using default
	 * demiliter ";".
	 * But case-insensitive.
	 * @param wildcard The wildcard
	 * @param str The String to be check, e.g. file name
	 * @return Whether the string matches the wildcard
	 */
	public static boolean matchesIgnoreCase(String wildcard, String str) {
		return matches(wildcard.toLowerCase(), str.toLowerCase(), WILDCARD_SEPARATOR);
	}

}
