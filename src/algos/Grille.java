package algos;

import static algos.GrilleCipher.*;

/**
 * A class that represents a grille
 * @author Mauricio Mejía Castro
 *
 */
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

	public String get(int i, int j) {
		return elements[i][j];
	}

	public void set(int i, int j, String element) {
		elements[i][j] = element;
	}

	public Grille rightRotation() {
		// Don't touch it, it is perfect
		Grille rightGrille = new Grille(n);
		String[][] rightRotatedMatrix = new String[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				rightRotatedMatrix[i][j] = elements[n - j - 1][i];
			}
		}
		rightGrille.setElements(rightRotatedMatrix);
		return rightGrille;
	}
	
	public Grille leftRotation() {
		Grille leftGrille = new Grille(n);
		String[][] leftRotatedMatrix = new String[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				leftRotatedMatrix[i][j] = elements[j][n - i - 1];
			}
		}
		leftGrille.setElements(leftRotatedMatrix);
		return leftGrille;
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
	
	public void rotateRight() {
		String[][] rightRotatedMatrix = new String[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; j++) {
				rightRotatedMatrix[i][j] = elements[n - j - 1][i];
			}
		}
		elements = rightRotatedMatrix;
	}

	public void display() {
		System.out.println();
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
				elements[i][j] = "" + (char) ((int) (Math.random() * 26) + 65);

			}
		}
	}

	public void initialize() {
		int count = 1;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				elements[i][j] = count + "";
				count++;
			}
		}
	}

	public Grille getSubgrille(int section) {
		Grille subgrille = new Grille(n / 2);
		String[][] subelements = new String[n / 2][n / 2];
		switch (section) {
		case SECTION1:
			for (int i = 0; i < n / 2; ++i)
				for (int j = 0; j < n / 2; ++j)
					subelements[i][j] = elements[i][j];
			break;
		case SECTION2:
			for (int i = 0; i < n / 2; ++i)
				for (int j = n / 2, y = 0; j < n; ++j, ++y)
					subelements[i][y] = elements[i][j];
			break;
		case SECTION3:
			for (int i = n / 2, x = 0; i < n; ++i, ++x)
				for (int j = 0; j < n / 2; ++j)
					subelements[x][j] = elements[i][j];
			break;

		case SECTION4:
			for (int i = n / 2, x = 0; i < n; ++i, ++x)
				for (int j = n / 2, y = 0; j < n; ++j, ++y)
					subelements[x][y] = elements[i][j];
			break;
		default:
			System.out.println("[Error] Invalid section number");
			break;
		}
		subgrille.setElements(subelements);
		return subgrille;
	}

	public void setSubgrille(Grille subgrille, int section) {
		switch (section) {
		case SECTION1:
			for (int i = 0; i < n / 2; ++i)
				for (int j = 0; j < n / 2; ++j)
					elements[i][j] = subgrille.getElements()[i][j];
			break;
		case SECTION2:
			for (int i = 0; i < n / 2; ++i)
				for (int j = n / 2, y = 0; j < n; ++j, ++y)
					elements[i][j] = subgrille.getElements()[i][y];
			break;
		case SECTION3:
			for (int i = n / 2, x = 0; i < n; ++i, ++x)
				for (int j = n / 2, y = 0; j < n; ++j, ++y)
					elements[i][j] = subgrille.getElements()[x][y];
			break;
		case SECTION4:
			for (int i = n / 2, x = 0; i < n; ++i, ++x)
				for (int j = 0; j < n / 2; ++j)
					elements[i][j] = subgrille.getElements()[x][j];
			break;
		default:
			System.out.println("[Error] Invalid section number");
			break;
		}
	}

	public void initializeWithKey(Grille key) {
		int N = key.getN();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (key.get(i, j).equals(SLOT)) {
					this.set(i, j, SLOT);
				} else {
					this.set(i, j, UNMARKED);
				}
			}
		}
	}
	
	public void initializeDefault() {
		for (int i = 0; i < getN(); i++) {
			for (int j = 0; j < getN(); j++) {
				this.set(i, j, UNMARKED);
			}
		}
	}
	
	public static Grille merge(Grille first, Grille second) {
		if (first.getN() == second.getN()) {
			int N = first.getN();
			Grille merged = new Grille(N);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (first.get(i, j).equals(UNMARKED) && !second.get(i, j).equals(UNMARKED))
						merged.set(i, j, second.get(i, j));
					else if (!first.get(i, j).equals(UNMARKED) && second.get(i, j).equals(UNMARKED))
						merged.set(i, j, first.get(i, j));
					else if (first.get(i, j).equals(UNMARKED) && second.get(i, j).equals(UNMARKED))
						merged.set(i, j, UNMARKED);
					else if (!first.get(i, j).equals(UNMARKED) && !second.get(i, j).equals(UNMARKED))
						merged.set(i, j, Math.random() > 0.5 ? first.get(i, j) : second.get(i, j));//merged.set(i, j, first.get(i, j));
				}
			}
			return merged;
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < getN(); i++) {
			for (int j = 0; j < getN(); j++) {
				str.append(elements[i][j]);
			}
		}
		return str.toString();
	}

}
