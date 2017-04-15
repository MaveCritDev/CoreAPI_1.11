package de.mavecrit.coreAPI.NoteblockAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mavecrit.coreAPI.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SongPlayer {

	 static Song song;
     static boolean playing = false;
     static  short tick = -1;
     static  ArrayList<String> playerList = new ArrayList<String>();
     static  boolean autoDestroy = false;
     static boolean destroyed = false;
     static Thread playerThread;
     static byte fadeTarget = 100;
     static byte volume = 100;
     static byte fadeStart = volume;
     static int fadeDuration = 60;
     static int fadeDone = 0;
     static FadeType fadeType = FadeType.FADE_LINEAR;

    public SongPlayer(Song song) {
        SongPlayer.song = song;
        createThread();
    }

    public FadeType getFadeType() {
        return fadeType;
    }

    public void setFadeType(FadeType fadeType) {
        SongPlayer.fadeType = fadeType;
    }

    public byte getFadeTarget() {
        return fadeTarget;
    }

    public void setFadeTarget(byte fadeTarget) {
        SongPlayer.fadeTarget = fadeTarget;
    }

    public byte getFadeStart() {
        return fadeStart;
    }

    public void setFadeStart(byte fadeStart) {
        SongPlayer.fadeStart = fadeStart;
    }

    public int getFadeDuration() {
        return fadeDuration;
    }

    public void setFadeDuration(int fadeDuration) {
        SongPlayer.fadeDuration = fadeDuration;
    }

    public int getFadeDone() {
        return fadeDone;
    }

    public void setFadeDone(int fadeDone) {
        SongPlayer.fadeDone = fadeDone;
    }

    protected void calculateFade() {
        if (fadeDone == fadeDuration) {
            return; // no fade today
        }
        double targetVolume = Interpolator.interpLinear(new double[]{0, fadeStart, fadeDuration, fadeTarget}, fadeDone);
        setVolume((byte) targetVolume);
        fadeDone++;
    }

    protected void createThread() {
        playerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!destroyed) {
                    long startTime = System.currentTimeMillis();
                    synchronized (SongPlayer.this) {
                        if (playing) {
                            calculateFade();
                            tick++;
                            if (tick > song.getLength()) {
                                playing = false;
                                tick = -1;
                                SongEndEvent event = new SongEndEvent(SongPlayer.this);
                                Bukkit.getPluginManager().callEvent(event);
                                if (autoDestroy) {
                                    destroy();
                                    return;
                                }
                            }
                            for (String s : playerList) {
                                @SuppressWarnings("deprecation")
								Player p = Bukkit.getPlayerExact(s);
                                if (p == null) {
                                    // offline...
                                    continue;
                                }
                                playTick(p, tick);
                            }
                        }
                    }
                    long duration = System.currentTimeMillis() - startTime;
                    float delayMillis = song.getDelay() * 50;
                    if (duration < delayMillis) {
                        try {
                            Thread.sleep((long) (delayMillis - duration));
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                }
            }
        });
        playerThread.setPriority(Thread.MAX_PRIORITY);
        playerThread.start();
    }

    public List<String> getPlayerList() {
        return Collections.unmodifiableList(playerList);
    }

    public void addPlayer(Player p) {
        synchronized (this) {
            if (!playerList.contains(p.getName())) {
                playerList.add(p.getName());
                ArrayList<SongPlayer> songs = Main.playingSongs
                        .get(p.getName());
                if (songs == null) {
                    songs = new ArrayList<SongPlayer>();
                }
                songs.add(this);
                Main.playingSongs.put(p.getName(), songs);
            }
        }
    }

    public boolean getAutoDestroy() {
        synchronized (this) {
            return autoDestroy;
        }
    }

    public void setAutoDestroy(boolean value) {
        synchronized (this) {
            autoDestroy = value;
        }
    }

    public abstract void playTick(Player p, int tick);

    public void destroy() {
        synchronized (this) {
            SongDestroyingEvent event = new SongDestroyingEvent(this);
            Bukkit.getPluginManager().callEvent(event);
            //Bukkit.getScheduler().cancelTask(threadId);
            if (event.isCancelled()) {
                return;
            }
            destroyed = true;
            playing = false;
            setTick((short) -1);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        SongPlayer.playing = playing;
        if (!playing) {
            SongStoppedEvent event = new SongStoppedEvent(this);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    public short getTick() {
        return tick;
    }

    public void setTick(short tick) {
        SongPlayer.tick = tick;
    }

    public void removePlayer(Player p) {
        synchronized (this) {
            playerList.remove(p.getName());
            if (Main.playingSongs.get(p.getName()) == null) {
                return;
            }
            ArrayList<SongPlayer> songs = new ArrayList<SongPlayer>(
            		Main.playingSongs.get(p.getName()));
            songs.remove(this);
            Main.playingSongs.put(p.getName(), songs);
            if (playerList.isEmpty() && autoDestroy) {
                SongEndEvent event = new SongEndEvent(this);
                Bukkit.getPluginManager().callEvent(event);
                destroy();
            }
        }
    }

    public byte getVolume() {
        return volume;
    }

    public void setVolume(byte volume) {
        SongPlayer.volume = volume;
    }

    public static Song getSong() {
        return song;
    }
}
