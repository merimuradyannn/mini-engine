package com.tonir.demo.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.HdpiMode;
import com.tonir.demo.DemoGame;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new DemoGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        float[] res = new float[]{1440, 2560}; // default
//		float[] res = new float[]{1024, 1366}; // iPad Pro 12 (2021)
//		float[] res = new float[]{1170, 2532}; // iphone 12

        float scale = 0.3f;

        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode((int)(res[0] * scale), (int)(res[1] * scale));
        config.setForegroundFPS(60);
        config.setHdpiMode(HdpiMode.Pixels);
        config.setTitle("Tonir");
        return config;
    }
}
