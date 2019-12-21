/*
* Data Structure_ Suffix Trie
* Name: Kyung Tae Hwan
* Student ID#: 2012155038
*/
//lets using standard trie
public class Substr
{
    trie t;
    String str;
    public Substr(String str)
    {
        /* Input: the string you will find the patterns over. */
        this.str = str;
        t = new trie(str);
    }
    
    public int countPattern(String pattern)
    {
        /*
        * Input: the pattern to find
        * Output: the number of occurrences of the pattern
        */
        return cpRecur(pattern, t.root);
    }
    
    public int cpRecur(String pattern, Node n) {
        if(n == null || n.child == null) return 0;
        else {
            if(n.child[(int)pattern.charAt(0)] == null) return 0;
            
            else if(pattern.length() == 1) return n.child[pattern.charAt(0)].num;
            else {//pattern.length()>1
                n = n.child[(int)pattern.charAt(0)];    
                
                if((n.end-n.start+1) >= pattern.length()) {
                    if(str.substring(n.start, n.start+pattern.length()).equals(pattern)) return n.num;
                    else return 0;
                }           
                else {//(n.end-n.start+1) < pattern.length()
                    if(str.substring(n.start, n.end+1).equals(pattern.substring(0, n.end-n.start+1)))
                        return cpRecur(pattern.substring(n.end-n.start+1),n);
                    else return 0;
                }
            }
        }
    }
}

class trie{     
    Node root = new Node();
    String str;
    int l;//length of String
    public trie(String str) {
        this.str = str;
        l = str.length();
        
        for(int i = l-1; i >= 0; i--) {
            insert(root, str.substring(i,l), i, l-1);
        }
    }
    void insert(Node parent, String s, int start, int end) {//headIndex = (int)s.charAt(0)
        if(s.length() == 0) {
            return;
        } 
        int headIndex = (int)s.charAt(0);//short term
        
        if(parent.child == null) {
            parent.child = new Node[128];//if we create already it is really waste so we create it here
            parent.child[headIndex] = new Node(start, end, 0);
        }
        else {          
            if(parent.child[headIndex] == null) parent.child[headIndex] = new Node(start, end, 0);
            else{
                Node curNode = parent.child[headIndex];//short term
                            
                if(curNode.start == -1) {
                    curNode.start = start; curNode.end = end; 
                }
                else if(curNode.start == curNode.end){
                    s = s.substring(1);
                    insert(curNode, s, start+1, end);
                    curNode.start = start; curNode.end = start;
                }
                else{
                    int i = 1;
                    while(str.length() > (curNode.start+i) &&
                        s.length() > i &&
                        str.charAt(curNode.start+i) == s.charAt(i)) {
                        i++; 
                    }
                    int len = curNode.end - curNode.start;
                    if(len+1 != i) {
                        String s2 = str.substring(curNode.start+i);
                        int preStart = curNode.start; int preEnd = curNode.end;
                        curNode.start = start; curNode.end = start+i-1;
                        insert(curNode, s2, preStart+i, preEnd);
                        s = s.substring(i);                     
                        insert(curNode, s, start+i, end);
                    }
                }
            }
        }
            parent.child[headIndex].num++;
    }
}

class Node{
    Node[] child;
    int num, start, end;//-1, -1 for leaf node
    
    public Node() {
        start = -1; end = -1;
        num = 0;
        }
    public Node(int start, int end, int num) {
        this.start = start; this.end = end; this.num = num;
        }
}
