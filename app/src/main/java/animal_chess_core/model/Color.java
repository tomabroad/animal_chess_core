package model;

public enum Color {
  BLACK, WHITE;

  public static Color opposite(Color c) {
    return (c == BLACK) ? WHITE : BLACK;
  }
}
