package ce326.hw1;

public class Trie {
    TrieNode root = new TrieNode(false);

    //Methods
    public int insert(String word) {
        TrieNode curr = root;
        int i = 0;

        // search for the given word, if we find it we dont insert it
        if(find(word) == 1)
            return 0;

        // this while allow us to iterate in the tree without hitting null nodes 
        while(i < word.length() && curr.children[word.charAt(i) - 'a'] != null) {
            // index of the next node 
            int index = word.charAt(i) - 'a', j = 0;
            StringBuilder holder = curr.children[index].value;

            // traverse the current's node value and compairing it with the word
            while(j < holder.length() && i < word.length() && holder.charAt(j) == word.charAt(i)) {
                i++;
                j++;
            }
            // traverse to the child
            if(j == holder.length()) 
                curr = curr.children[index];
            else {
                // when we insert a word that is a prefix of an existing one
                if(i == word.length()) {
                    TrieNode oldChild = curr.children[index];
                    TrieNode newChild = new TrieNode(true);
                    StringBuilder remholder = strCopy(holder, j);

                    holder.setLength(j);
                    curr.children[index] = newChild;
                    curr.children[index].value = holder;
                    newChild.children[remholder.charAt(0) - 'a'] = oldChild;
                    newChild.children[remholder.charAt(0) - 'a'].value = remholder;

                }
                // when we insert a word that has partial match with an existing word
                else {
                    StringBuilder remholder = strCopy(holder, j);
                    TrieNode newChild = new TrieNode(false);
                    StringBuilder remWord = strCopy(word, i);
                    TrieNode oldChild = curr.children[index];
                    
                    holder.setLength(j);
                    
                    curr.children[index] = newChild;
                    curr.children[index].value = holder;
                    newChild.children[remholder.charAt(0) - 'a'] = oldChild;
                    newChild.children[remholder.charAt(0) - 'a'].value = remholder;                                   
                    newChild.children[remWord.charAt(0) - 'a'] = new TrieNode(true);
                    newChild.children[remWord.charAt(0) - 'a'].value = remWord;
                             
                }
                return 1;
            }
        }

        // create a new node as a leaf
        if(i < word.length()) {
            curr.children[word.charAt(i) - 'a'] = new TrieNode(true);
            curr.children[word.charAt(i) - 'a'].value = strCopy(word, i);
        }
        // when the node is already in the tree, we simply make it a terminal node
        else
            curr.isEnd = true;
        return 1;
    }
    // a private method that allow us to copy a string from a certain index until the end
    private StringBuilder strCopy(CharSequence str, int index) {
        StringBuilder result = new StringBuilder(100);
         
        while (index != str.length()) {
            result.append(str.charAt(index++));
        }
         
        return result;
    }

    public int remove(String word) {
        int i = 0, index=word.charAt(i) - 'a';
 
        TrieNode curr = root;
        TrieNode prev = root;
        TrieNode prevPrev = root;

        // this while allow us to iterate in the tree without hitting null nodes 
        while (i < word.length()  && curr.children[word.charAt(i) - 'a'] != null) {
            // index of the next node
            index = word.charAt(i) - 'a';
            StringBuilder label = curr.children[index].value;
            int j = 0;

            // traverse the current's node value and compairing it with the word
            while (i < word.length() && j < label.length()) {

                // if they dont match it means the word isnt in tree so remove has failed
                if (word.charAt(i) != label.charAt(j)) 
                    return 0;
 
                i++;
                j++;
            }
            // iterate to the next node and update the prev-prevPrev variables that helps us for removing the node
            if (j == label.length() && i <= word.length()) {
                prevPrev = prev;
                prev = curr;
                curr = curr.children[index];
            }
            
            else {
            // another case that we cant find the word so remove fails
                return 0;
            }
        }
        int j;
        int childCounter = 0; // variable to count how many child does the TrieNode we are removing has
        int onlyChild = 0; // variable to show us the index of the only child of the TrieNode

        // reaching here means the word has been found and we will remove it
        if( i == word.length() && curr.isEnd) {
            // for loop to count the number of node's children and store the first one
            for(j = 0; j < 26; j++) {
                if(curr.children[j] != null) {
                    childCounter++;
                    onlyChild = j;
                }
            }
            // it is a leaf 
            if(childCounter == 0) {
                prev.children[curr.value.charAt(0) - 'a'] = null;
                
            }
            // has one child, so unite it with his grantparent
            else if(childCounter == 1) {
                prev.children[curr.value.charAt(0) - 'a'] = curr.children[onlyChild];
                StringBuilder holder = new StringBuilder(curr.value);
                holder.append(curr.children[onlyChild].value);
                curr.children[onlyChild].value = holder;
            }
            // has more that 1 child so just making it no terminal node
            else {
                curr.isEnd = false;
            }
            //check parent and do the same with him 
            if(prev.isEnd == false && prev != root) {

                childCounter = 0;
                for(j = 0; j < 26; j++) {
                    if(prev.children[j] != null) {
                        childCounter++;
                        onlyChild = j;
                    }
                }

                if(childCounter == 1) {
                    prevPrev.children[prev.value.charAt(0) - 'a'] = prev.children[onlyChild];

                    StringBuilder holder = new StringBuilder(prev.value);
                    holder.append(prev.children[onlyChild].value);
                    prev.children[onlyChild].value = holder;
                }
            }
            return 1;
        }
            
        else 
            return 0;
    }

    public int find(String word) {
        int i = 0;
 
        TrieNode curr = root;

        // this while allow us to iterate in the tree without hitting null nodes 
        while (i < word.length()  && curr.children[word.charAt(i) - 'a'] != null) {
            // find  the next node's index
            int index = word.charAt(i) - 'a';
            StringBuilder label = curr.children[index].value;
            int j = 0;

            // traverse the current's node value and compairing it with the word
            while (i < word.length() && j < label.length()) {

                // if they dont match it means the word isnt in tree so find has failed               
                if (word.charAt(i) != label.charAt(j)) 
                    return 0;
 
                i++;
                j++;
            }

            // iterate to the next node
            if (j == label.length() && i <= word.length()) 
                 curr = curr.children[index];
            
            else {
                //miss match so find has fail
                return 0;
            }
        }
 
        // check i and if the node is terminal
        if( i == word.length() && curr.isEnd)
            return 1;
        else 
            return 0;
        
    }   

    public void printPreOrder(TrieNode node) {
        if(node == null)
            return;
        else if(node.isEnd)
            System.out.print(node.value + "# ");
        else
            System.out.print(node.value + " ");

        for(int i = 0; i < 26; i++)
            printPreOrder(node.children[i]);

    }

    public void printDictionary(TrieNode node, StringBuilder holder) {
        if(node.isEnd)
            System.out.println(holder);

        for(int i = 0; i < 26; i++) {
            if(node.children[i] != null) {
                int length = holder.length();

                holder = holder.append(node.children[i].value);
                printDictionary(node.children[i], holder);
                holder = holder.delete(length, holder.length());
            }
        }
    }

    public void sameDistance(TrieNode node, StringBuilder holder, int wordLength, int distance, String word) {
        if(node.isEnd && holder.length() == wordLength && distanceB2eenStr(holder, word) == distance) 
            System.out.println(holder);
          
        for(int i = 0; i < 26; i++) {
            if(node.children[i] != null) {
                int length = holder.length();

                holder = holder.append(node.children[i].value);
                sameDistance(node.children[i], holder, wordLength, distance, word);
                holder = holder.delete(length, holder.length());
            }
        }
    }

    private int distanceB2eenStr(StringBuilder str1, String str2) { // for this case we assume str1 and str2 are the same length, in other cases we must find the best length to iterate
        int i;
        int counter = 0;

        for(i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != str2.charAt(i))
                counter++;
        }
        return counter;
    }

    public void findSuffix(TrieNode node, StringBuilder holder, String suffix) {
        int  end = holder.length(), start = end - suffix.length();

        if(node.isEnd ) {
            if( holder.toString().endsWith(suffix) )
                System.out.println(holder);
        }

        for(int i = 0; i < 26; i++) {
            if(node.children[i] != null) {
                int length = holder.length();

                holder = holder.append(node.children[i].value);
                findSuffix(node.children[i], holder, suffix);
                holder = holder.delete(length, holder.length());
            }
        }
    }
}