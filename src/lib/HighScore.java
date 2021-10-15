package lib;
/*
 * Highscore.java
 * Перезаписывает новые данные о рекорде
 */

import java.io.*;
import java.util.Scanner;

public class HighScore {

	private static final String FILE_PATH = "src\\lib\\resources\\dataScore\\highscore.dat";
	private static File dataFile = new File(FILE_PATH);
	private int bestScore;

	public HighScore() {
		try (Scanner dataScanner = new Scanner(dataFile)){
			bestScore = Integer.parseInt(dataScanner.nextLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int bestScore () {
		return bestScore;
	}

	public void setNewBest (int newBest) {

		bestScore = newBest;

		try (PrintWriter dataWriter = new PrintWriter(FILE_PATH, "UTF-8")){
			dataWriter.println(newBest);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Could not set new highscore!");
		}
	}

}
