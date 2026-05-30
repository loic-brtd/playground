package other;

public class StrInduction {

  public static final String nil = "";

  public static int egal(String a, String b) {
    return a.equals(nil) && b.equals(nil) ? 1
      : head(a).equals(head(b)) && egal(tail(a), tail(b)) == 1 ? 1 : 0;
  }

  public static int longueur(String s) {
    return s.equals(nil) ? 0
      : 1 + longueur(tail(s));
  }

  public static String prefix(String a, String b) {
    return a.equals(nil) && b.equals(nil) ? nil
      : head(a).equals(head(b)) ? head(a) + prefix(tail(a), tail(b)) : nil;
  }

  public static String head(String s) {
    return s.length() == 0 ? nil : String.valueOf(s.charAt(0));
  }

  public static String tail(String s) {
    return s.length() <= 1 ? nil : s.substring(1);
  }

  public static void main(String[] args) {
    // System.out.println(egal("un", nil));
    // System.out.println(longueur("abcdefghijklmnopqrstuvwxyz"));
    System.out.println(prefix("loic", "lolo"));
  }

}
