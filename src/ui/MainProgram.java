package ui;

import algos.Grille;
import algos.GrilleCipher;

public class MainProgram {

	/** The main program for turning grille cipher
	 * @param args
	 */
	public static void main(String[] args) {
	
		GrilleCipher cipher = new GrilleCipher("jimattacksatdawnnowinthemorning");
		cipher.encrypt();
		//key.display();
		//cipher.displayKeyPositions();
		//System.out.println(cipher.encrypt());
		
	}

}
