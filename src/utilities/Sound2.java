package utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound2 {

    private Clip clip;

    public Sound2(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't load the sound file, genius!");
        }
    }

    public void playOnLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } else {
            throw new IllegalStateException("Clip is not initialized, dumbface!");
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        } else {
            throw new IllegalStateException("Clip is not playing, moron!");
        }
    }

    public static void main(String[] args) {
        // Example usage
        Sound2 player = new Sound2("path_to_your_sound_file.wav");
        player.playOnLoop();

        // To stop the sound
        // player.stop();
    }
}
