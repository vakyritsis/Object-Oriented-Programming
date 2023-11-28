package ce326.hw1;

import java.util.Scanner;

public class HW1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choice, word;
        Trie root = new Trie();
        
        while(sc.hasNext()) {
            System.out.println("?: ");
            choice = sc.next();

            switch(choice) {
                case "-i": {
                    word = sc.next();
                    if(root.insert(word) == 1)
                        System.out.println("ADD " + word + " OK"); 
                    else
                        System.out.println("ADD " + word + " NOK"); 
                    break;
                }
                case "-r": {
                    word = sc.next();
                    if(root.remove(word) == 1)
                        System.out.println("RMV " + word + " OK"); 
                    else
                        System.out.println("RMV " + word + " NOK"); 
                    break;
                }
                case "-f": {
                    word = sc.next();
                    if(root.find(word) == 1)
                        System.out.println("FIND " + word + " OK"); 
                    else
                        System.out.println("FIND " + word + " NOK"); 
                    break;
                }
                case "-p": {
                    System.out.print("PreOrder:");
                    root.printPreOrder(root.root);
                    System.out.println();
                    break;
                }
                case "-d": {
                    System.out.println("\n***** Dictionary *****");
                    root.printDictionary(root.root, new StringBuilder());
                    System.out.println();
                    break;
                }
                case "-w": {
                    word = sc.next();
                    String distance = sc.next();
                    System.out.println("\nDistant words of " + word + " (" + distance + "):");
                    root.sameDistance(root.root, new StringBuilder(), word.length(), Integer.parseInt(distance), word);
                    System.out.println();
                    break;
                }
                case "-s": {
                    word = sc.next();
                    System.out.println("\nWords with suffix " + word + ":");
                    root.findSuffix(root.root, new StringBuilder(), word);
                    System.out.println();

                    break;
                }
                case "-q": {
                    System.out.println("Bye bye!");
                    return;
                }
            }
        }
        sc.close();
    }
}
