package app.chess.advanced.service;

import java.io.IOException;

public interface PlayerNotifier {

    void notifyPlayer(Object message) throws IOException;

    boolean isOnline();

}
