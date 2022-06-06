/**
* @author Mahir Cem Askin
* @since 22.05.2021
*/
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class CpuEmulator {
   static File commands;

   static HashMap<Integer, HashMap<String, Integer>> execute;
   
   public static void main(String[] args) throws Exception {
      commands = new File(args[0]);
      Scanner gir = new Scanner(commands);
      execute = new HashMap<Integer, HashMap<String, Integer>>();
      getCommands(execute, gir);
      CPU cpu = new CPU(execute);
      cpu.executer();
   }

   public static void getCommands(
   HashMap<Integer, HashMap<String, Integer>> execute, Scanner gir) {
      for (int a = 0; gir.hasNextLine(); a++) {
         String[] temp = gir.nextLine().split(" ", 0);
         try {
            execute.put(a, new HashMap<String, Integer>());
            execute.get(a).put(temp[1], Integer.parseInt(temp[2]));
         } catch(ArrayIndexOutOfBoundsException e) {
            execute.get(a).put(temp[1], 0);
         }
      }
   }     
}

class CPU {
   private int AC;
   private int PC;
   private int flag;
   private int[] memo;
   private HashMap<Integer, HashMap<String, Integer>> execute;
   public CPU(HashMap<Integer, HashMap<String, Integer>> execute) {
      this.PC = 0;
      this.AC = 0;
      this.flag = 0;
      this.memo = new int[256];
      this.execute = execute;
   }
   
   public void executer() {
      String i;
      for (int a = 0; a < execute.keySet().size(); a++) {
         i = execute.get(a).keySet().toArray()[0].toString();
         switch (i.toUpperCase()) {
            case "START" -> start();
            case "LOAD" -> load(execute.get(a).get(i));
            case "LOADM" -> loadM(execute.get(a).get(i));
            case "STORE" -> store(execute.get(a).get(i));
            case "CMPM" -> cmpm(execute.get(a).get(i));
            case "CJMP" -> {
               if (flag > 0)
                  a = execute.get(a).get(i) - 1;
               continue;
            }
            case "JMP" -> {
               a = execute.get(a).get(i) - 1;
               continue;
            }
            case "ADD" -> add(execute.get(a).get(i));
            case "ADDM" -> addM(execute.get(a).get(i));
            case "SUBM" -> subM(execute.get(a).get(i));
            case "SUB" -> sub(execute.get(a).get(i));
            case "MUL" -> mul(execute.get(a).get(i));
            case "MULM" -> mulM(execute.get(a).get(i));
            case "DISP" -> disp();
            case "HALT" -> System.exit(0);
         }
      }
   }
   
   
   private void check(int in) {
      if (in > memo.length || in < 0) {
          System.out.println("This memory location does not exist");
          System.exit(1);
      }
    }
    
    private void start() {
       System.out.println("It's Alive");
    }

    private void load(int a) {
       this.AC = a;
    }

    private void loadM(int in) {
       check(in);
       this.AC = this.memo[in];
    }

    private void store(int in) {
       check(in);
       this.memo[in] = this.AC;
    }

    private void cmpm(int in) {
       check(in);
       if (this.AC > this.memo[in]) {
          this.flag = 1;
       }
       else if (this.AC == this.memo[in]) {
          this.flag = 0;
       }
       else {
          this.flag = -1;
       }     
    }

    private void add(int a) {
       this.AC += a;
    }

    private void addM(int in) {
       check(in);
       this.AC += this.memo[in];
    }

    private void subM(int in) {
       check(in);
       this.AC -= this.memo[in];
    }

    private void sub(int a) {
       this.AC -= a;
    }

    private void mul(int a) {
       this.AC *= a;
    }

    private void mulM(int in) {
       check(in);
       this.AC *= this.memo[in];
    }

    private void disp() {
       System.out.println("AC: " + this.AC);
    }
}
