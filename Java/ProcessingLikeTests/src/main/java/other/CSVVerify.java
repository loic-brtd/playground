package other;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.PApplet;

public class CSVVerify extends PApplet {

  String[] file;

  public void setup() {
    noCanvas();
    file = loadStrings("res/routes.dat");
    Stream<String> s = Stream.of(file)
//        .filter(e -> e.split(",")[8].equals("N"))
        ;
    List<String> l = s.collect(Collectors.toList());
    Set<String> set = new HashSet<>();
    for (String line : l) {
      String[] champs = line.split(",");
      String champ = champs[1] + '|' + champs[3] + '|' + champs[5];
      if (set.contains(champ)) {
        println(champs);
        println(champ);
        println();
      } else {
        set.add(champ);
      }
    }
    println("fin");
    
    
    
//    file = loadStrings("res/airports.dat");
//    Set<String> set = new HashSet<>();
//    for (String line : file) {
//      String[] champs = line.split(",");
//      String champ = champs[4];
//      if (set.contains(champ)) {
//        println(champs);
//        println(champ + " already exists");
//        println();
//      } else {
//        set.add(champ);
//      }
//    }
//    println("fin");
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}
