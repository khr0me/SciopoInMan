package sciopoinman.desktopmodule;

import com.jme3.system.AppSettings;

import sciopoinman.game.SciopoInMan;

/**
 * Used to launch a jme application in desktop environment
 *
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        final SciopoInMan game = new SciopoInMan();

        final AppSettings appSettings = new AppSettings(true);
        appSettings.setFullscreen(false);
        appSettings.setResolution(1920, 1080);

        game.setSettings(appSettings);
        game.setShowSettings(false);
        game.start();
    }
}
