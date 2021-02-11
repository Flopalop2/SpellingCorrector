package spell;

public class TrieNode implements INode{
    private int count;
    TrieNode[] children = new TrieNode[26];

    public TrieNode() {
        this.count = 0;
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
