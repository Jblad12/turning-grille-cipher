package algos;

import static algos.Grille.SECTION1;
import static algos.Grille.SECTION2;
import static algos.Grille.SECTION3;
import static algos.Grille.SECTION4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrilleCipher {
	public static char SPECIAL_CHAR = '+';
	public static char DEFAULT_CHAR = '0';

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
		//System.out.println("n = " + n);
		Grille key = new Grille(n);
		key.initialize();
		//System.out.println("Init");
		key.display();
		Grille section1 = key.getSubgrille(SECTION1);
		section1.initialize();
		Grille section2 = section1.rotateRight();
		Grille section3 = section2.rotateRight();
		Grille section4 = section3.rotateRight();
		key.setSubgrille(section1, SECTION1);
		key.setSubgrille(section2, SECTION2);
		key.setSubgrille(section3, SECTION3);
		key.setSubgrille(section4, SECTION4);
		//System.out.println("subgrilles");
		key.display();

		int randomI, randomJ;
		List<String> marked = new ArrayList<>();
		int N = key.getN();
		keyPositions = new int[N * N / 4][2];
//		System.out.println("Generating the random key");
//		System.out.println("N = " + N);
//		System.out.println("N * N / 4 = " + (N * N / 4));
		int k = 0;
		while (k < N * N / 4) {
			randomI = (int) (Math.random() * N);
			randomJ = (int) (Math.random() * N);
			//System.out.println(randomI + ", " + randomJ);
			if (key.get(randomI, randomJ).equals(SPECIAL_CHAR+"")) {
				randomI = (int) (Math.random() * N);
				randomJ = (int) (Math.random() * N);
			} else if (!marked.contains(key.get(randomI, randomJ))) {
				//System.out.println("Accepted: " + key.get(randomI, randomJ));
				marked.add(key.get(randomI, randomJ));
				key.set(randomI, randomJ, SPECIAL_CHAR+"");
				keyPositions[k][0] = randomI;
				keyPositions[k][1] = randomJ;
				k++;
			}
		}
		System.out.println("--Random key--");
		key.display();
		//System.out.println(k);
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
				fixedPlaintext.append(SPECIAL_CHAR);
			}
			this.plaintext = fixedPlaintext.toString();
			this.plaintextLength = fixedPlaintext.toString().length();
		}
	}

	public String encrypt() {
		Grille randomKey = generateRandomKey();
		int N = randomKey.getN();
		Grille[] grilles = new Grille[4];
		System.out.println("encrypt() n = " + randomKey.getN());
		grilles[0] = new Grille(N);
		grilles[1] = new Grille(N);
		grilles[2] = new Grille(N);
		grilles[3] = new Grille(N);
		grilles[0].initializeWithKey(randomKey);
		grilles[1].initializeWithKey(randomKey);
		grilles[2].initializeWithKey(randomKey);
		grilles[3].initializeWithKey(randomKey);
		
//		System.out.println("ecrypt(): " + getPlaintext());
		
		String[] groups = separatePlaintext(getPlaintext());
//		System.out.println("ecrypt(): " + groups[0]);
//		System.out.println("ecrypt(): " + groups[1]);
//		System.out.println("ecrypt(): " + groups[2]);
//		System.out.println("ecrypt(): " + groups[3]);
		
		// Allocates groups of the plaintext in four grilles
		int counter = 0; // counter of coordinates
		int index = 0;
		while (counter < 4) {
			for (int i = 0; i < grilles[counter].getN(); ++i) {
				for (int j = 0; j < grilles[counter].getN(); ++j) {
					if (randomKey.get(i, j).equals(SPECIAL_CHAR+"")) {
						grilles[counter].set(i, j, groups[counter].charAt(index)+"");
						index++;
					}
				}
			}
			counter++;
			index = 0;
		}
		
		System.out.println("\nGroup #1");
		grilles[0].display();
		System.out.println("\nGroup #2");
		grilles[1].display();
		System.out.println("\nGroup #3");
		grilles[2].display();
		System.out.println("\nGroup #4");
		grilles[3].display();
		
		//System.out.println("Test grilles[0]");
		Grille m00 = Grille.merge(grilles[0], grilles[0].rotateRight());
		//m00.display();
		Grille m01 = Grille.merge(m00, m00.rotateRight());
		//m01.display();
		Grille m02 = Grille.merge(m01, m01.rotateRight());
		System.out.println("\nFirst group encrypted");
		m02.display();
		
		Grille m10 = Grille.merge(grilles[1], grilles[1].rotateRight());
		//m00.display();
		Grille m11 = Grille.merge(m10, m10.rotateRight());
		//m01.display();
		Grille m12 = Grille.merge(m11, m11.rotateRight());
		System.out.println("\nSecond group encrypted");
		m12.display();
		
		Grille m20 = Grille.merge(grilles[2], grilles[2].rotateRight());
		//m00.display();
		Grille m21 = Grille.merge(m20, m20.rotateRight());
		//m01.display();
		Grille m22 = Grille.merge(m21, m21.rotateRight());
		System.out.println("\nThird group encrypted");
		m22.display();
		
		Grille m30 = Grille.merge(grilles[3], grilles[3].rotateRight());
		//m00.display();
		Grille m31 = Grille.merge(m30, m30.rotateRight());
		//m01.display();
		Grille m32 = Grille.merge(m31, m31.rotateRight());
		System.out.println("\nFourth group encrypted");
		m32.display();
		return m02.toString() + m12.toString() + m22.toString() + m32.toString();
	}

	public void displayKeyPositions() {
		System.out.println("-- Grille Key Positions --");
		for (int i = 0; i < keyPositions.length; ++i) {
			System.out.print("("+keyPositions[i][0] + ", " + keyPositions[i][1]+"), ");
		}
	}
	
	public String[] separatePlaintext(String plaintext) {
		int N = getPlaintextLength();
		int N0 = N/4, N1 = N/2, N2 = 3*N/4;
		//System.out.println("separate(): N = " + N + ", N_ = " + plaintext.length());
		//System.out.println(N0 + "," + N1 + "," + N2);
		String[] groups = new String[4];
		groups[0] = plaintext.substring(0, N0);
		groups[1] = plaintext.substring(N0, N1);
		groups[2] = plaintext.substring(N1, N2);
		groups[3] = plaintext.substring(N2, N);
		return groups;
	}

}
