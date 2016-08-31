package algos;

import util.Grille;

public class GrilleCipher {
	public static char SPECIAL_CHAR = 'X';
	
	private String plaintext;
	private int plaintextLength;
	
	public GrilleCipher(String plaintext) {
		this.plaintext = plaintext;
		plaintextLength = plaintext.length();
	}
	
	public static Grille generateRandomKey() {
		return null;
	}
	
	public static boolean isSquare(int n) {
		int i;
		for (i = 0; i <=n; ++i) {
			if (n == i * i) return true;
		}
		return false;
	}
	
	public static int nextSquare(int n) {
		int i;
		for (i = n; ;i++) {
			if (isSquare(i)) return i - 1;
		}
	}
	
	public String fixPlaintext() {
		StringBuilder fixedPlaintext = new StringBuilder(plaintext);
		for (int i = plaintextLength - 1; i < nextSquare(plaintextLength); ++i) {
			fixedPlaintext.append(SPECIAL_CHAR);
		}
		return fixedPlaintext.toString();
	}
	
	

}
