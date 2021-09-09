package animal_chess.core;

import animal_chess.core.model.Color;
import animal_chess.core.model.Player;

public class App {
  public void run() {

  }

  public static void main(String[] args) {
    System.out.println("started");

    Player player = new Player(Color.BLACK);
    new App().run();
    System.out.println("done");
  }
}
