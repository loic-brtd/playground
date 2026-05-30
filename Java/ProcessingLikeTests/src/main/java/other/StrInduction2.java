package other;

public class StrInduction2 {

  public static class Chaine {

    public char tete;
    public Chaine queue;

    public Chaine(String s) {
      if (s.length() == 0) {
        tete = '\0';
        queue = null;
      } else if (s.length() == 1) {
        tete = s.charAt(0);
        queue = new Chaine("");
      } else if (s.length() > 1) {
        tete = s.charAt(0);
        queue = new Chaine(s.substring(1));
      }
    }

    public boolean isNil() {
      return tete == '\0';
    }

    @Override
    public String toString() {
      return tete + (queue != null ? queue.toString() : "");
    }
  }

  public static Chaine concat(char c, Chaine chaine) {
    return new Chaine(c + chaine.toString());
  }

  public static int egal(Chaine a, Chaine b) {
    return a.isNil() && b.isNil() ? 1
      : a.tete == b.tete && egal(a.queue, b.queue) == 1 ? 1 : 0;
  }

  public static int longueur(Chaine s) {
    return s.isNil() ? 0 : 1 + longueur(s.queue);
  }

  public static Chaine prefix(Chaine a, Chaine b) {
    return a.isNil() && b.isNil() ? new Chaine("")
      : a.tete == b.tete ? concat(a.tete, prefix(a.queue, b.queue)) : new Chaine("");
  }

  public static void main(String[] args) {
    Chaine a = new Chaine("loic");
    Chaine b = new Chaine("lalala");
    Chaine c = new Chaine("loic");
    Chaine d = new Chaine("");
    Chaine e = new Chaine("lola");

    System.out.println(egal(a, b));
    System.out.println(egal(a, c));

    System.out.println(longueur(b));
    System.out.println(longueur(d));

    System.out.println(prefix(c, e));
  }

}
