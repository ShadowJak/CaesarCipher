// Adrian Melendez
// A1540936
// Cipher


public class CaesarCipher {
	
	// Frequency list for English letters.
	static double[] table = {8.2, 1.5, 2.8, 4.3, 12.7, 			// a, b, c, d, e, 
							 2.2, 2.0, 6.1, 7.0,  0.2, 			// f, g, h, i, j, 
							 0.8, 4.0, 2.4, 6.7,  7.5, 			// k, l, m, n, o, 
							 1.9, 0.1, 6.0, 6.3,  9.1, 			// p, q, r, s, t, 
							 2.8, 1.0, 2.4, 0.2,  2.0, 0.1};	// u, v, w, x, y, z 

	public static void main(String[] args) {
		// Here is the final answer.
		System.out.println(crack("myxqbkdevkdsyxc yx mywzvodsxq dro ohkw!"));
	}

	// Turns a lower case character into an int. a = 0, ... z = 25
	// Cast the chars as int and subtracts 97 to get the new value.
	static int let2nat(char c) {
		if ((int) c < 97 || (int) c > 122) {
			return (int) c;
		} else {
			return ((int) c - 97);
		}
	}
	
	// Turns an int from 0 to 25 to a char between a and z
	// Reverses the let2nat process
	static char nat2let(int code) {
		if (code >= 0 && code < 26) {
			return (char) (code + 97);
		} else {
			return (char) code;
		}
	}
	
	// Applies a shift factor to lowercase letters to change their value.
	//   Wraps around to the begining when going past z
	static char shift(int shftAmt, char c) {
		if ((int) c < 97 || (int) c > 122) {
			return c;
		} else {
			return nat2let((let2nat(c) + shftAmt) % 26);
		}
	}
	
	// Applies shift to each character in a string.
	static String encode(int shftAmt, String str) {
		String temp = "";
		for (int i = 0; i < str.length(); i++) {
			temp = temp + shift(shftAmt, str.charAt(i));
		}
		return temp;
	}
	
	// Undoes encoding by shifting the character back to starting positions
	static String decode(int shftAmt, String str) {
		return encode(26 - shftAmt, str);
	}
	
	// Counts the number of lowercase letters in a string
	static int lowers(String str) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if ((int) str.charAt(i) >= 97 && (int) str.charAt(i) < 123) {
				counter++;
			}
		}
		return counter;
	}
	
	// Counts the number of times c is in str
	static int count(char c, String str) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (c == str.charAt(i)) {
				counter++;
			}
		}
		return counter;
	}
	
	// Simple percent calculation
	static double percent(int num1, int num2) {
		return 100 * (double) num1 / (double) num2; 
	}
	
	// Finds the frequency of each letter in str
	static double[] freqs(String str) {
		double[] temp = new double[26];
		int len = lowers(str);
		for (int i = 0; i < 26; i++) {
			temp[i] = percent(count(nat2let(i), str), len);
		}
		return temp;
	}
	
	// Rotates the items of an array by n. Wraps around to the beginning when the end is reached.
	static double[] rotate(int n, double[] list) {
		double[] temp = new double[list.length];
		for (int i = 0; i < list.length; i++) {
			temp[i] = list[(i + n) % list.length];
		}
		return temp;
	}
	
	// Finding the ChiSquare of the frequency array relative to the expected frequencies
	static double chisqr(double[] os) {
		double result= 0;
		for (int i = 0; i < os.length; i++) {
			result += ((os[i] - table[i]) * (os[i] - table[i])) / table[i];
		}
		
		return result;
	}
	
	// Finds the position of a in list.
	static int position(double a, double[] list) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == a) {
				return i;
			}
		}
		return -1;
	}
	
	// Cracks the code but finding the best chisqr value.
	static String crack(String str) {
		double minChi = Double.MAX_VALUE;
		double[] freqsList = freqs(str);
		int minIndex = -1;
		for (int i = 0; i < table.length; i++) {
			double tempMin = chisqr(rotate(i, freqsList));
			if (minChi > tempMin) {
				minChi = tempMin;
				minIndex = i;
			}
		}
		return decode(minIndex, str);
	}
}


