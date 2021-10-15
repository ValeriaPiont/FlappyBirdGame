package lib;
/*
 * Audio.java
 * Проигрывает звуки во время игры
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

	private void playSound (String sound) {
		String path = "src\\lib\\resources\\sound\\" + sound + ".wav";
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
			Clip clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void up () {
		playSound("up");
	}


	public void point () {
		playSound("point");
	}


	public void hit () {
		playSound("hit");
	}

}

