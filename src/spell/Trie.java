package spell;

import java.util.Locale;
import java.util.Objects;

public class Trie implements ITrie{
    TrieNode root;
    int wordCount;
    int nodeCount;

    public Trie() {
        this.root = new TrieNode();
        wordCount = 0;
        nodeCount = 1;
    }


    @Override
    public void add(String word) {
        word = word.toLowerCase();

        INode curNode = root;

        for (int i = 0; i < word.length(); ++i) {
            int curLetter = word.charAt(i) - 'a';

            // initialize new node and up nodecount
            if (curNode.getChildren()[curLetter] == null) {
                curNode.getChildren()[curLetter] = new TrieNode();
                nodeCount++;
            }

            curNode = curNode.getChildren()[curLetter]; //set curnode to next node

            //if last letter add word
            if (i == word.length() -1) {
                curNode.incrementValue();
                if (curNode.getValue() == 1) {
                    wordCount++;
                }
            }

        }
    }

    @Override
    public INode find(String word) {
        word = word.toLowerCase();

        INode curNode = root; //current node to root

        //iterate through nodes
        for (int i = 0; i < word.length(); ++i) {
            int curLetter = word.charAt(i) - 'a';
            if (curNode.getChildren()[curLetter] != null) {
                curNode = curNode.getChildren()[curLetter];
            }
            else {
                return null;
            }

            if (i == word.length()-1) {
                if (curNode.getValue() > 0) {
                    return curNode;
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public boolean equals(Object o) {
        //is null
        if (o == null) {
            return false;
        }
        //is this
        if (this == o) {
            return true;
        }
        //is this class
        if (this.getClass() != o.getClass()) {
            return false;
        }

        Trie trie = (Trie) o;

        //counts the same
        if (wordCount != trie.wordCount || nodeCount != trie.nodeCount) {
            return false;
        }

        return equalsHelper(root, trie.root);
    }

    private boolean equalsHelper(INode curNode, INode objNode) {

        //recursively iterates through both nodes and compares values
        for (int i = 0; i < curNode.getChildren().length; ++i) {

            INode curNodeChild = curNode.getChildren()[i];
            INode objNodeChild = objNode.getChildren()[i];

            //both not null or null children
            if (curNodeChild != null) {
                if (objNodeChild != null) {
                    //same values
                    if (curNodeChild.getValue() != objNodeChild.getValue()) {
                        return false;
                    }

                    //recurse to run on children
                    if ((!equalsHelper(curNodeChild, objNodeChild))) {
                        return false;
                    }
                }
                else {
                    return false; //never called?
                }
            } else if (objNodeChild != null) {
                return false; //also never called?
                // intention is if curnodechild is null and objnodechild isnt return false
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        //unique hashcode based on first nonnull child and counts

        int i = 0;
        for (; i < root.getChildren().length; ++i) {
            if (root.getChildren()[i] != null) {
                break;
            }
        }

        return wordCount*nodeCount*i;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder curWord = new StringBuilder();

        toStringHelper(root, curWord, output);

        //String toReturn = output.toString();
        return output.toString();
    }

    private void toStringHelper(INode curNode, StringBuilder curWord, StringBuilder output) {

        //iterates through trie and finds all words and adds them to output alphabetically
        for (int i = 0; i < curNode.getChildren().length; ++i) {

            if (curNode.getChildren()[i] != null) {
                char toAppend = (char) ('a'+i);
                curWord.append(toAppend);

                if (curNode.getChildren()[i].getValue() > 0) {
                    output.append(curWord);
                    output.append('\n');
                }

                toStringHelper(curNode.getChildren()[i], curWord, output);

                curWord.deleteCharAt(curWord.length()-1);
            }
        }

        return;
    }
}
