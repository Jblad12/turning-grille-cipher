package algos;

public class Grille {
	public static final int SECTION1 = 1;
	public static final int SECTION2 = 2;
	public static final int SECTION3 = 3;
	public static final int SECTION4 = 4;
	
	private int n;
	private String[][] elements;

	public Grille(int n) {
		this.n = n;
		if (isEven(n)) {
			elements = new String[n][n];
		}
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String[][] getElements() {
		return elements;
	}

	public void setElements(String[][] elements) {
		this.elements = elements;
	}

	public static boolean isEven(int n) {
		return n % 2 == 0;
	}

	public Grille rotateRight() {
		Grille rightGrille = new Grille(n);
		String[][] rightRotatedMatrix = new String[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				rightRotatedMatrix[i][j] = elements[n - j - 1][i];
			}
		}
		//elements = rightRotatedMatrix;
		rightGrille.setElements(rightRotatedMatrix);
		return rightGrille;
	}

	public void rotateLeft() {
		String[][] leftRotatedMatrix = new String[n][n];
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
				if (j == n - 1)
					System.out.print("\n");
			}
		}
	}

	public void randomFill() {
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				elements[i][j] = "" + (char)((int) (Math.random() * 26) + 65);

			}
		}
	}

	public void initialize() {
		int count = 0;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				elements[i][j] = count + "";
				count++;
			}
		}
	}
	
	public Grille getSubgrille(int section) {
		Grille subgrille = new Grille(n/2);
		String[][] subelements = new String[n/2][n/2];
		switch(section) {
		case SECTION1:
			for (int i = 0; i < n/2; ++i)
				for (int j = 0; j < n/2; ++j)
					subelements[i][j] = elements[i][j];
			break;
		case SECTION2:
			for (int i = 0; i < n/2; ++i)
				for (int j = n/2, y = 0; j < n; ++j, ++y)
					subelements[i][y] = elements[i][j];
			break;
		case SECTION3:
			for (int i = n/2, x = 0; i < n; ++i, ++x)
				for (int j = 0; j < n/2; ++j)
					subelements[x][j] = elements[i][j];
			break;
			
		case SECTION4:
			for (int i = n/2, x = 0; i < n; ++i, ++x)
				for (int j = n/2, y = 0; j < n; ++j, ++y)
					subelements[x][y] = elements[i][j];
			break;
		default:
			System.out.println("[Error] Invalid section number");
			break;
		}
		subgrille.setElements(subelements);
		return subgrille;
	}

}
