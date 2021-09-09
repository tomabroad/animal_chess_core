package animal_chess.core.model;

/**
 * immutable
 *
 */
public class Square implements Comparable<Square> {
  private int i, j;

  public Square(int i, int j) {
    this.i = i;
    this.j = j;
  }

  public int getI() {
    return i;
  }

  public int getJ() {
    return j;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + i;
    result = prime * result + j;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Square other = (Square) obj;
    if (i != other.i)
      return false;
    if (j != other.j)
      return false;
    return true;
  }

  @Override
  public int compareTo(Square o) {
    return (i == o.i) ? (j - o.j) : (i - o.i);
  }

  @Override
  public String toString() {
    return "[" + i + "," + j + "]";
  }
}
