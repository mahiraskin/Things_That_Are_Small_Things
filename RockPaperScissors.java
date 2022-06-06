import java.util.*;
/*
* After two swaps everything goes back to the initial state
* That is a design problem
*/
public class RockPaperScissors {
   public static void main(String[] args) {
      Game one = new Game("Asteriks", "Oburiks");
   }
}


class Game {
   private boolean winCon = false;
   private Element Rock;
   private Element Paper;
   private Element Scissors;
   private Scanner gir;
   private Player curPlayer;
   private Player notCurPlayer;
   
   public Game(String name1, String name2) {
      Rock = new Element("Rock");
      Paper = new Element("Paper");
      Scissors = new Element("Scissors");
      gir = new Scanner(System.in);
      curPlayer = new Player(name1);
      notCurPlayer = new Player(name2);
      Rock.setInf(Scissors);
      Paper.setInf(Rock);
      Scissors.setInf(Paper);
      turn();
   }
   
   public void turn() {
      Element first;
      Element second;
      Player temp;
      temp = curPlayer;
      curPlayer = notCurPlayer;
      notCurPlayer = temp;
      System.out.println("1: Rock \n2: Paper \n3: Scissors \n");
      System.out.print(curPlayer.getName() + "Change two:");
      change(getInputs(gir), getInputs(gir));
      System.out.println("1: Rock \n2: Paper \n3: Scissors \n");
      System.out.print(curPlayer.getName() + "Choose:");
      curPlayer.setElement(getInputs(gir));
      System.out.println("1: Rock \n2: Paper \n3: Scissors \n");
      System.out.print(notCurPlayer.getName() + "Choose:");
      notCurPlayer.setElement(getInputs(gir));
      roundRes(curPlayer, notCurPlayer);
      turn();
   }
   
   public Element getInputs(Scanner gir) {
      String getter = gir.nextLine();
      if (getter.equals("rock"))
         return this.Rock;  
      if (getter.equals("paper"))
         return this.Paper; 
      if (getter.equals("scissors"))
         return this.Scissors;
      return Rock; 
   }
   
   public void change(Element first, Element second) {
      Element temp;
      temp = first.getInf();
      first.setInf(second.getInf());
      second.setInf(temp); 
   }
   
   public void roundRes(Player p1, Player p2) {
      if (p1.getElement().getInf() == p2.getElement()) {
         p1.round();
         System.out.println("\n" + p1.getName() + " wins this round.");
      }
      
      else if (p1.getElement() == p2.getElement()) {
         System.out.println("\nDraw");
      }
      
      else {
         p2.round();
         System.out.println("\n" + p1.getName() + " wins this round.");
      }
   }
}


class Element {
   private String name;
   private Element inferior;

   public Element(String name) {
      this.name = name;
   }
   
   public void setInf(Element inferior) {
      this.inferior = inferior;
   }
   
   public Element getInf() {
      return this.inferior;
   }
}

class Player {
   private String name;
   private int points = 0;
   private Element curChoice;
   
   public Player(String name) {
      this.name = name;
   }
   
   public void round() {
      points++;
      if (points > 3) {
         System.out.println(this.name + "Wins");
         System.exit(1);  
      } 
   }
   public String getName() {
      return this.name;
   }
   public Element getElement() {
     return this.curChoice;
   }
   public void setElement(Element newElement) {
      this.curChoice = newElement;
   }
}