package algos;

import static algos.Grille.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the Turning Grille transposition cipher
 * @author Mauricio Mejía Castro
 *
 */
public class GrilleCipher {
	public static String SLOT = "*";
	public static String PAD = "X";
	public static String UNMARKED = "-";

	private String plaintext;
	private int plaintextLength;
	private int[][] keyPositions;

	public GrilleCipher(String plaintext) { // This needs enhancement
		this.plaintext = plaintext.toUpperCase();
		System.out.println("Initial plaintext: " + getPlaintext() + "\nL = " + getPlaintext().length());
		this.plaintextLength = plaintext.length();
		this.fixPlaintext();
		System.out.println("Fixed  plainttext: " + getPlaintext() + "\nL = " + getPlaintext().length());
	}

	/**
	 * Gets the plaintext.
	 * @return the plaintext
	 */
	public String getPlaintext() {
		return plaintext;
	}

	/**
	 * Sets the plaintext
	 * @param plaintext the plaintext to set
	 */
	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}

	/**
	 * Gets the length of the plaintext.
	 * @return the length of the plaintext
	 */
	public int getPlaintextLength() {
		return plaintextLength;
	}

	/**
	 * Sets the length of the plaintext. It is not very useful.
	 * @param plaintextLength the length of the plaintext
	 * @deprecated
	 */
	@Deprecated
	public void setPlaintextLength(int plaintextLength) {
		this.plaintextLength = plaintextLength;
	}

	/**
	 * Returns the slot positions of the key as a 2-dimensional array.
	 * @return a 2-dimensional array with the slot positions of the key
	 */
	public int[][] getKeyPositions() {
		return keyPositions;
	}

	/**
	 * Sets the slot positions of the key as a 2-dimensional array.
	 * @param keyPositions the key positions to set
	 */
	public void setKeyPositions(int[][] keyPositions) {
		this.keyPositions = keyPositions;
	}

	/**
	 * Generates a random key for encrypting and decrypting. Inspired by:
	 * http://everything2.com/title/Turning+grill+transposition+cipher
	 * @return the random key
	 */
	public Grille generateRandomKey() {
		int n = (int) Math.sqrt(getPlaintextLength());
		Grille key = new Grille(n);
		
		key.initialize();
		
		Grille section1 = key.getSubgrille(SECTION1);
		section1.initialize();
		Grille section2 = section1.rightRotation();
		Grille section3 = section2.rightRotation();
		Grille section4 = section3.rightRotation();
		
		key.setSubgrille(section1, SECTION1);
		key.setSubgrille(section2, SECTION2);
		key.setSubgrille(section3, SECTION3);
		key.setSubgrille(section4, SECTION4);

		int randomI, randomJ;
		List<String> marked = new ArrayList<>();
		int N = key.getN();
		keyPositions = new int[N * N / 4][2];
		int k = 0;
		while (k < N * N / 4) {
			randomI = (int) (Math.random() * N);
			randomJ = (int) (Math.random() * N);
			if (key.get(randomI, randomJ).equals(SLOT)) {
				randomI = (int) (Math.random() * N);
				randomJ = (int) (Math.random() * N);
			} else if (!marked.contains(key.get(randomI, randomJ))) {
				marked.add(key.get(randomI, randomJ));
				key.set(randomI, randomJ, SLOT);
				keyPositions[k][0] = randomI;
				keyPositions[k][1] = randomJ;
				k++;
			}
		}
		return key;
	}

	/**
	 * Determines if a number is square.
	 * @param n the number to test
	 * @return if the number is square or not
	 */
	public static boolean isSquare(int n) {
		int i;
		for (i = 0; i <= n; ++i) {
			if (n == i * i)
				return true;
		}
		return false;
	}

	/**
	 * Calculates the next square number of the current n;
	 * @param n the input number
	 * @return the next sqaure of n
	 */
	public static int nextSquare(int n) {
		int i;
		for (i = n;; i++) {
			if (isSquare(i))
				return i;
		}
	}

	/**
	 * Completes the plaintext to obtain a square length plaintext.
	 */
	public void fixPlaintext() {
		if (!isSquare(getPlaintextLength())) {
			StringBuilder fixedPlaintext = new StringBuilder(plaintext);
			System.out.println("next sqr " + nextSquare(plaintextLength));
			for (int i = getPlaintextLength() - 1; i < nextSquare(getPlaintextLength()) - 1; ++i) {
				fixedPlaintext.append(PAD);
			}
			this.plaintext = fixedPlaintext.toString();
			this.plaintextLength = fixedPlaintext.toString().length();
		}
	}

	/**
	 * Encrypts the plaintext to obtain the ciphertext.
	 * @return a ciphertext
	 */
	public String encrypt(Grille key) {
		//Grille randomKey = generateRandomKey();
		int N = key.getN();
		String[] plaintextGroups = separatePlaintext(getPlaintext());
		StringBuilder ciphertext = new StringBuilder();
		Grille cipherMatrix = new Grille(N);
		cipherMatrix.initializeDefault();
		
		System.out.println("\nKey:");
		key.display();

		int count = 0;
		for (int g = 0; g < 4; ++g) {
			System.out.println("\nEmpty cipher matrix:");
			cipherMatrix.display();
			System.out.println("\nGroup #" + (g+1));
			for (int i = 0; i < N; ++i) {
				for (int j = 0; j < N; ++j) {
					if (key.get(i, j).equals(SLOT)) {
						cipherMatrix.set(i, j, plaintextGroups[g].charAt(count)+"");
						count++;
					}
				}
			}
			cipherMatrix.display();
			cipherMatrix.rotateLeft();
			count = 0;
		}
		System.out.println("\nFinal cipher matrix:");
		cipherMatrix.display();

		// Build the ciphertext
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				ciphertext.append(cipherMatrix.get(i, j));
			}
		}
		return ciphertext.toString();
	}
	
	public String decrypt(Grille key) {
		return "";
	}

	/**
	 * Displays the slot positions of the key in a listed format
	 */
	public void displayKeyPositions() {
		System.out.println("-- Grille Key Positions --");
		for (int i = 0; i < keyPositions.length; ++i) {
			System.out.print("(" + keyPositions[i][0] + ", " + keyPositions[i][1] + "), ");
		}
	}

	/**
	 * Separates the plaintext in four groups of length N * N / 4
	 * @param plaintext the plaintext to separate
	 * @return an array containing four strings
	 */
	public String[] separatePlaintext(String plaintext) {
		int N = getPlaintextLength();
		int N0 = N / 4, N1 = N / 2, N2 = 3 * N / 4;
		String[] groups = new String[4];
		groups[0] = plaintext.substring(0, N0);
		groups[1] = plaintext.substring(N0, N1);
		groups[2] = plaintext.substring(N1, N2);
		groups[3] = plaintext.substring(N2, N);
		return groups;
	}

}
