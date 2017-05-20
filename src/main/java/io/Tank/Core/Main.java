package io.Tank.Core;

import io.fantasia.lobby.AbstractMainFactory;

public class Main {
    private static short PORT = 3939;

    public static void main(String[] args) {
        AbstractMainFactory.setInstance(new MainFactory());

        Lobby lobby = new Lobby(PORT);
        lobby.start();
    }
}
