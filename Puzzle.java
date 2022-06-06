import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Puzzle {
   public static void main(String[] args) {
	   Summatic help = new Summatic();
   }
}

class Summatic {
	ArrayList<Character> first = new ArrayList<Character>();
	ArrayList<Character> second = new ArrayList<Character>();
	ArrayList<Character> last = new ArrayList<Character>();
	HashMap<Character, Long> berserk = new HashMap<Character, Long>();
   boolean[] goBack = {true};
   long[] b = new long[1];
   String[] elements;
   int marker = 0;
   Scanner gir;
	
	public Summatic() {
      gir = new Scanner(System.in);
		setUp(gir);
	}
   
   public void setUp(Scanner gir) {
      System.out.print("\nEnter 1st input value : ");
	   String firstS = gir.nextLine();
      System.out.print("Enter 2nd input value : ");
	   String secondS = gir.nextLine();
      System.out.print("Enter Output value : ");
	   String lastS = gir.nextLine();
	   String[] elements = {firstS, secondS, lastS};
      berserk.clear();
      this.first.clear();
      this.second.clear();
      this.last.clear();
	   sendProblem(elements);
   }
	
	public void sendProblem(String[] elements) {
      int turn = 1;
      boolean setUpAgain = false;
      this.elements = elements;
		for(String current : elements) {
         if(current.length() < 2 || current.length() > 6)
            setUpAgain = true;
		   else if (turn == 1) {
		      for(int a = 0; a < current.length(); a++) { 
			      this.first.add(current.charAt(a));
			      berserk.put(current.charAt(a), 99L);
		      }
		   }
		   else if (turn == 2) {
			  for(int a = 0; a < current.length(); a++) {
				  this.second.add(current.charAt(a));
				  berserk.put(current.charAt(a), 99L);
			  }
		   }
		   else if (turn == 3) {
			  for(int a = 0; a < current.length(); a++) { 
				  this.last.add(current.charAt(a));
			      berserk.put(current.charAt(a), 99L);
			  }
		   }
		   turn++;
		}
      if(berserk.keySet().size() > 10)
         setUpAgain = true;
      if(setUpAgain)
         setUp(gir);
      else
         gir.close();
      long[] values = new long[berserk.keySet().size()];
      b[0] = powOfTen(berserk.keySet().size() - 2);
      force(b, values, berserk.keySet().size(), goBack);
	}

	public void force(long[] b, long[] values, int place, boolean[] goBack) {
      do {
         long c = b[0];
         values[berserk.keySet().size()-place] = (c / powOfTen(place-1)) % 10;
         if (isUnique(values) && place == 1) {
            for(int a = 0; a < values.length; a++) {
               berserk.put(berserk.keySet().toArray()[a].toString().charAt(0),
               values[a]);
            }
            check();
         }
         if(place == 1) {
            b[0]+=1;
            goBack[0] = false;
         }
         else
            force(b, values, place - 1, goBack);
         if (place == berserk.keySet().size()) {
            goBack[0] = true;
         }
      } while(b[0] < powOfTen(berserk.keySet().size()) && goBack[0]);
	}
   
   public boolean isUnique(long[] values) {
      for (int i = 0; i < values.length-1; i++) {
        for (int j = i+1; j < values.length; j++) {
             if (values[i] == values[j])
                 return false;
        }
    }              
    return true;   
   }
   
   public long powOfTen(int power) {
      long ten = 1;
      for(int a = 0; a < power; a++)
         ten*=10;
      return ten; 
   }
	
	public void check() {
	   int sum = 0;
	   int result = 0;
     
	   for(int a = 1; a <= first.size(); a++) {
		  sum += berserk.get(first.get(a-1)) * powOfTen(first.size() - a);
	   }
	   for(int a = 1; a <= second.size(); a++) {
		  sum += berserk.get(second.get(a-1)) * powOfTen(second.size() - a);
	   }
     
	   for(int a = 1; a <= last.size(); a++) {
		  result += berserk.get(last.get(a-1)) * powOfTen(last.size() - a);
	   }
      
	   if (result == sum) {
         System.out.print("\n" + elements[0] + ": ");
         for(int a = 1; a <= first.size(); a++)
		      System.out.print(berserk.get(first.get(a-1)));
         System.out.print("\n" + elements[1] + ": ");
         for(int a = 1; a <= second.size(); a++)
		      System.out.print(berserk.get(second.get(a-1)));
         System.out.print("\n" + elements[2] + ": ");
         for(int a = 1; a <= last.size(); a++)
		      System.out.print(berserk.get(last.get(a-1)));
         System.out.print("\n");
	   }
   }
}

