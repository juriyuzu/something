package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    static AudioInputStream audioIn;
    static Clip clip;

    public Sound() {
//        try {
//            // Open an audio input stream
//            audioIn = AudioSystem.getAudioInputStream(new File("sound/s.wav"));
//            // Get a sound clip resource
//            clip = AudioSystem.getClip();
////            // Open audio clip and load samples from the audio input stream
////            clip.open(audioIn);
////            clip.start();
////            // Wait for the clip to finish playing
////            Thread.sleep(clip.getMicrosecondLength() / 1000);
////            // Clean up resources
////            clip.stop();
////            clip.close();
////            audioIn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    Sound(String filePath) {
//        Thread soundThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // Open an audio input stream
//                    audioIn = AudioSystem.getAudioInputStream(new File(filePath));
//
//                    // Get a sound clip resource
//                    clip = AudioSystem.getClip();
//                    // Open audio clip and load samples from the audio input stream
//                    clip.open(audioIn);
//                    clip.start();
//                    // Wait for the clip to finish playing
////                    Thread.sleep(clip.getMicrosecondLength() / 1000);
////                    // Clean up resources
////                    clip.stop();
////                    clip.close();
////                    audioIn.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        soundThread.start();
//    }

//    volatile boolean isPlaying;
//    Sound(String filePath, boolean b) {
//        Thread soundThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    isPlaying = true;
//                    while (isPlaying) {
//                        // Open an audio input stream
//                        audioIn = AudioSystem.getAudioInputStream(new File(filePath));
//
//                        // Get a sound clip resource
//                        clip = AudioSystem.getClip();
//                        // Open audio clip and load samples from the audio input stream
//                        clip.open(audioIn);
//                        clip.start();
//                        // Wait for the clip to finish playing
//                        Thread.sleep(clip.getMicrosecondLength() / 1000);
//                        // Clean up resources
//                        clip.close();
//                        audioIn.close();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        soundThread.start();
//    }
//
//    void stopSound() {
//        isPlaying = false;
//        if (clip != null) {
//            clip.stop();
//            clip.close();
//        }
//    }

    public static void play(String filePath) {
        Thread soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Open an audio input stream
                    audioIn = AudioSystem.getAudioInputStream(new File(filePath));

                    // Get a sound clip resource
                    clip = AudioSystem.getClip();
                    // Open audio clip and load samples from the audio input stream
                    clip.open(audioIn);
                    clip.start();
//                    // Wait for the clip to finish playing
//                    Thread.sleep(clip.getMicrosecondLength() / 1000);
//                    // Clean up resources
//                    clip.stop();
//                    clip.close();
//                    audioIn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        soundThread.start();}
}
