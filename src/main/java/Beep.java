package main.java;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Beep {

	public Beep() {
		// TODO Auto-generated constructor stub
	}
	
	public static void miBeep() {
		try {
			File beep = new File("beep.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(beep);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
