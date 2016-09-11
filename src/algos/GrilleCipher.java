package algos;

import static algos.Grille.*;

import java.util.ArrayList;
import java.util.List;

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

	public int[][] getKeyPositions() {
		return keyPositions;
	}

	public void setKeyPositions(int[][] keyPositions) {
		this.keyPositions = keyPositions;
	}

	/**
	 * Generates a random key for encrypting and decrypting. Inspired by:
	 * http://everything2.com/title/Turning+grill+transposition+cipher
	 * 
	 * @return the random key
	 */
	public Grille generateRandomKey() {
		int n = (int) Math.sqrt(getPlaintextLength());
		// System.out.println("n = " + n);
		Grille key = new Grille(n);
		key.initialize();
		// System.out.println("Init");
		// key.display();
		Grille section1 = key.getSubgrille(SECTION1);
		section1.initialize();
		Grille section2 = section1.rotateRight();
		Grille section3 = section2.rotateRight();
		Grille section4 = section3.rotateRight();
		key.setSubgrille(section1, SECTION1);
		key.setSubgrille(section2, SECTION2);
		key.setSubgrille(section3, SECTION3);
		key.setSubgrille(section4, SECTION4);
		// System.out.println("subgrilles");
		// key.display();

		int randomI, randomJ;
		List<String> marked = new ArrayList<>();
		int N = key.getN();
		keyPositions = new int[N * N / 4][2];
		// System.out.println("Generating the random key");
		// System.out.println("N = " + N);
		// System.out.println("N * N / 4 = " + (N * N / 4));
		int k = 0;
		while (k < N * N / 4) {
			randomI = (int) (Math.random() * N);
			randomJ = (int) (Math.random() * N);
			// System.out.println(randomI + ", " + randomJ);
			if (key.get(randomI, randomJ).equals(SLOT)) {
				randomI = (int) (Math.random() * N);
				randomJ = (int) (Math.random() * N);
			} else if (!marked.contains(key.get(randomI, randomJ))) {
				// System.out.println("Accepted: " + key.get(randomI, randomJ));
				marked.add(key.get(randomI, randomJ));
				key.set(randomI, randomJ, SLOT);
				keyPositions[k][0] = randomI;
				keyPositions[k][1] = randomJ;
				k++;
			}
		}
		// System.out.println("--Random key--");
		// key.display();
		// System.out.println(k);
		return key;
	}

	public static boolean isSquare(int n) {
		int i;
		for (i = 0; i <= n; ++i) {
			if (n == i * i)
				return true;
		}
		return false;
	}

	public static int nextSquare(int n) {
		int i;
		for (i = n;; i++) {
			if (isSquare(i))
				return i;
		}
	}

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

	public String encrypt() {
		Grille randomKey = generateRandomKey();
		int N = randomKey.getN();
		String[] plaintextGroups = separatePlaintext(getPlaintext());
		StringBuilder ciphertext = new StringBuilder();

		System.out.println("We are gonna encrypt: " + getPlaintext());
		Grille cipherMatrix = new Grille(N);
		cipherMatrix.initializeDefault();
		
		System.out.println("Random key");
		randomKey.display();

		int count = 0;
		for (int g = 0; g < 4; ++g) {
			System.out.println("cipher matrix");
			cipherMatrix.display();
			System.out.println("Group #" + (g+1));
			for (int i = 0; i < N; ++i) {
				for (int j = 0; j < N; ++j) {
					if (randomKey.get(i, j).equals(SLOT)) {
						cipherMatrix.set(i, j, plaintextGroups[g].charAt(count)+"");
						count++;
					}
				}
			}
			cipherMatrix.display();
			cipherMatrix.rotateLeft();
			count = 0;
		}
		System.out.println("final cipher matrix");
		cipherMatrix.display();

		// System.out.println(plaintextGrouped[0] + ", " + plaintextGrouped[1]);
		// System.out.println("encrypt():\n");
		// randomKey.display();

		return ciphertext.toString();
	}

	public void displayKeyPositions() {
		System.out.println("-- Grille Key Positions --");
		for (int i = 0; i < keyPositions.length; ++i) {
			System.out.print("(" + keyPositions[i][0] + ", " + keyPositions[i][1] + "), ");
		}
	}

	public String[] separatePlaintext(String plaintext) {
		int N = getPlaintextLength();
		int N0 = N / 4, N1 = N / 2, N2 = 3 * N / 4;
		// System.out.println("separate(): N = " + N + ", N_ = " +
		// plaintext.length());
		// System.out.println(N0 + "," + N1 + "," + N2);
		String[] groups = new String[4];
		groups[0] = plaintext.substring(0, N0);
		groups[1] = plaintext.substring(N0, N1);
		groups[2] = plaintext.substring(N1, N2);
		groups[3] = plaintext.substring(N2, N);
		return groups;
	}

}
