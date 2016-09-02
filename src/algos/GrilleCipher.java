package algos;

import static algos.Grille.*;

public class GrilleCipher {
	public static char SPECIAL_CHAR = 'X';
	
	private String plaintext;
	private int plaintextLength;
	
	public GrilleCipher(String plaintext) {
		this.plaintext = plaintext;
		this.plaintextLength = plaintext.length();
		this.fixPlaintext();
	}
	
	public String getPlaintext() {
		return plaintext;
	}

	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}



	public int getPlaintextLength() {
		return plaintextLength;
	}



	public void setPlaintextLength(int plaintextLength) {
		this.plaintextLength = plaintextLength;
	}



	/**
	 * Generates a random key for encrypting and decrypting.
	 * Inspired by: 
	 * http://everything2.com/title/Turning+grill+transposition+cipher
	 * @return the random key
	 */
	public Grille generateRandomKey() {
		int n = (int)Math.sqrt(getPlaintextLength());
		Grille key = new Grille(n);
		key.initialize();
		Grille section1 = key.getSubgrille(SECTION1);
		Grille section2 = section1.rotateRight();
		Grille section3 = section2.rotateRight();
		Grille section4 = section3.rotateRight();
		
		return key;
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
	
	public void fixPlaintext() {
		StringBuilder fixedPlaintext = new StringBuilder(plaintext);
		for (int i = plaintextLength - 1; i < nextSquare(plaintextLength); ++i) {
			fixedPlaintext.append(SPECIAL_CHAR);
		}
		this.plaintext = fixedPlaintext.toString();
	}
	
	

}
