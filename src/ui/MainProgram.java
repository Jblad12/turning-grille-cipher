package ui;

import algos.Grille;
import algos.GrilleCipher;

public class MainProgram {

	/** The main program for turning grille cipher
	 * @param args
	 */
	public static void main(String[] args) {
		String plaintext = "jimattacksatdawnnowinthemorning";
		GrilleCipher cipher = new GrilleCipher(plaintext);
		Grille key = cipher.generateRandomKey();
		String ciphertext = cipher.encrypt(key);
		System.out.println("Ciphertext: " + ciphertext);
		//key.display();
		//cipher.displayKeyPositions();
		//System.out.println(cipher.encrypt());
		
	}

}
