package app.chess.service;

import java.io.IOException;

public interface PlayerNotifier {

    void notifyPlayer(Object message) throws IOException;

    boolean isOnline();

}
