package ce326.hw1;

public class TrieNode {
    // this class is the node of the compressed trie
    TrieNode[] children = new TrieNode[26]; // it has 26 children 
    StringBuilder value = new StringBuilder(); // the value of the node (Stringbuilder class is more eficient then string)
    boolean isEnd; // boolean varialbe that shows us if the node is terminal

    public TrieNode(boolean isEnd) {
        this.isEnd = isEnd;
    }
}