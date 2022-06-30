package com.pending.game3.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameMusic extends Sound{
    private final Clip audioClip;

    public GameMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File audioFile = new File("resources/universe-space-sounds-3595.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-5.0f);
    }

    public void playMusic() {
        audioClip.start();
        audioClip.loop(audioClip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        audioClip.stop();
    }
}