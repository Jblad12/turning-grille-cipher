package ui;

import algos.Grille;
import algos.GrilleCipher;

public class MainProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		GrilleCipher cipher = new GrilleCipher("jimattacksatdawnnowinthemorning");
		Grille key = cipher.generateRandomKey();
		//key.display();
		//cipher.displayKeyPositions();
		System.out.println(cipher.encrypt());
		
	}

}
