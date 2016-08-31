package util;

public class Grille {
	private int n;
	private char[][] elements;
	
	public Grille(int n) {
		this.n = n;
		if (isEven(n)) {
			elements = new char[n][n];
		}
	}
	
	
	public int getN() {
		return n;
	}



	public void setN(int n) {
		this.n = n;
	}



	public char[][] getElements() {
		return elements;
	}

	public void setElements(char[][] elements) {
		this.elements = elements;
	}

	public static boolean isEven(int n) {
		return n % 2 == 0;
	}
	
	public void rotateRight() {
		char[][] rightRotatedMatrix = new char[n][n]; 
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				rightRotatedMatrix[i][j] = elements[n - j - 1][i];
			}
		}
		elements = rightRotatedMatrix;
	}
	
	public void rotateLeft() {
		char[][] leftRotatedMatrix = new char[n][n]; 
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				leftRotatedMatrix[i][j] = elements[j][n - i - 1];
			}
		}
		elements = leftRotatedMatrix;
	}
	
	public void display() {
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				System.out.print(elements[i][j] + " ");
				if (j == n-1) System.out.print("\n");
			}
		}
	}
	
	public void randomFill() {
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				elements[i][j] = (char)((int)(Math.random() * 26) + 65);
				
			}
		}
	}

}
