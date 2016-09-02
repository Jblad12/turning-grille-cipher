package ui;

import algos.Grille;
import algos.GrilleCipher;

public class MainProgram {

	public static void main(String[] args) {
		// Testing the grille
		Grille grille = new Grille(6);
		grille.initialize();
		grille.display();
		grille.randomFill();
		grille.display();
		System.out.println("---------------------------");
		grille = grille.rotateRight();
		grille.display();
		System.out.println("--------------Sub grid 4-------------");
		Grille subgrille = grille.getSubgrille(Grille.SECTION3);
		subgrille.display();
		
	}

}
