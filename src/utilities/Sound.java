package utilities;

import main.MainMenu;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Sound {
    private HashMap<String, Clip> sounds;
    MainMenu mainMenu;

    public Sound(MainMenu mainMenu) {
        sounds = new HashMap<>();
        if (mainMenu != null) this.mainMenu = mainMenu;

        String src = "src/assets/sounds/";
        loadSound("BLIP", src + "blip.wav");
        loadSound("YES", src + "Shikanokonokonokokoshitantan.wav");
    }

    // Method to load a sound file into the HashMap
    public void loadSound(String key, String filePath) {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            sounds.put(key, clip);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e.getMessage());
        }
    }

    // Method to play a sound from the HashMap
    public void play(String key) {
        if (mainMenu != null && mainMenu.mute) return;

        Clip clip = sounds.get(key);
        if (clip != null) {
            new Thread(() -> {
                if (clip.isRunning()) {
                    clip.stop(); // Stop the clip if it's already playing
                }
                clip.setFramePosition(0); // Rewind to the beginning
                clip.start();
            }).start();
        } else {
            System.err.println("Sound not found: " + key);
        }
    }

    // Method to play a sound on a loop
    public void playOnLoop(String key) {
        if (mainMenu != null && mainMenu.mute) return;

        Clip clip = sounds.get(key);
        if (clip != null) {
            new Thread(() -> {
                if (clip.isRunning()) {
                    clip.stop(); // Stop the clip if it's already playing
                }
                clip.setFramePosition(0); // Rewind to the beginning
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the clip
                clip.start();
            }).start();
        } else {
            System.err.println("Sound not found: " + key);
        }
    }

    // Method to stop a sound
    public void stop(String key) {
        Clip clip = sounds.get(key);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        } else {
            System.err.println("Sound not playing or not found: " + key);
        }
    }

    public void stopAll() {
        for (Clip clip : sounds.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    // Example main method to show usage
    public static void main(String[] args) {
        Sound sound = new Sound(null);
        sound.loadSound("test", "path_to_your_sound_file.wav");
        sound.play("test");

        // To stop the sound
        // sound.stop("test");

        // To play the sound on loop
        // sound.playOnLoop("test");
    }
}
