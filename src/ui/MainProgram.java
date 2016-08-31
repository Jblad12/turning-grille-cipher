package ui;

import algos.GrilleCipher;
import util.Grille;

public class MainProgram {

	public static void main(String[] args) {
		// Testing the grille
		Grille grille = new Grille(6);
		grille.randomFill();
		grille.display();
		System.out.println("---------------------------");
		grille.rotateRight();
		grille.display();
		
		// Testing the plaintext fixation
		GrilleCipher cipher = new GrilleCipher("ATACKNOWBOY");
		System.out.println(cipher.fixPlaintext());
		
	}

}
