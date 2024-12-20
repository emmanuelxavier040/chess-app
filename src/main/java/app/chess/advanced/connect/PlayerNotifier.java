package app.chess.advanced.connect;

import java.io.IOException;

public interface PlayerNotifier {

    void notifyPlayer(Object message) throws IOException;

    boolean isOnline();

}
