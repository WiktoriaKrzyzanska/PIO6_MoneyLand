package model.views;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
public class GameAudioManager {
    private static Music backgroundMusic;

    public static void initialize() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("GameShowAudio.mp3"));
        backgroundMusic.setLooping(true);
    }

    public static void playBackgroundMusic() {
        if (backgroundMusic!=null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public static void pauseBackgroundMusic() {
        if (backgroundMusic!=null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    public static boolean backgroundMusicIsPlaying(){
        return backgroundMusic.isPlaying();
    }

    public static void dispose() {
        backgroundMusic.dispose();
    }
}

