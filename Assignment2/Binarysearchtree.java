import java.util.*;

public class Binarysearchtree<Key extends Comparable<Key>, Value> {
    private Node root;             // root of binaryst
    int count;

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            left = null;
            right = null;
        }
    }


    /**
     * Initializes an empty symbol table.
     */
    public Binarysearchtree() {
        root = null;
    }


    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        if(size() == 0){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return root.size;
    }


    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if(key == null){
            throw new IllegalArgumentException("get() Key is null");
        }

        Node x = root;
        while(x.key != key){
            int comp = key.compareTo(x.key);

            if(comp < 0){
                x = x.left;
            }
            else if(comp > 0){
                x = x.right;
            }
        }

        if(x.key == key){
            return x.val;
        }
        return x.val;
    }


    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if(key == null){
            throw new IllegalArgumentException("put() Key is null");
        }
        else{
            Node newestNode = new Node(key, val, 1);

            if(root == null){
                root = newestNode;
            }
            else{
                Node x = root;
                Node parent;

                while(x != null){
                    parent = x;
                    int comp = key.compareTo(x.key);

                    if(comp < 0){
                        x = x.left;
                        if(x == null){
                            parent.left = newestNode;
                            root.size++;
                            return;
                        }
                        else if(x.key == key){
                            x.val = val;
                            return;
                        }
                    }
                    else if(comp > 0){
                        x = x.right;
                        if(x == null){
                            parent.right = newestNode;
                            root.size++;
                            return;
                        }
                        else if(x.key == key){
                            x.val = val;
                            return;
                        }
                    }
                }
            }
        }
    }


    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
       Node x = root;

        if(size() == 0){
            throw new NoSuchElementException("Symbol table is empty");
        }

        while(x.left != null){
            x = x.left;
        }
        return x.key;
    } 


    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        Node x = floor(root, key);
        Node comp = x;

        if(key == null){
            throw new IllegalArgumentException("Key is null");
        }
        else if(size() == 0){
            throw new NoSuchElementException("No Key!");
        }

        if(x == null){
            return null;
        }
        if(x.left != null){
            comp = x.left;

            while(comp.right != null){
                comp = comp.right;
            }
        }
        return comp.key;
    } 

    private Node floor(Node x, Key key){
        Node lowest = null;
        while(x != null){
            int comp = key.compareTo(x.key);

            if(comp == 0){
                return x;
            }
            if(comp > 0){
                lowest = x;
                x = x.right;
            }
            else{
                x = x.left;
            }
        }
        return lowest;
    }


    /**
     * Return the key in the symbol table whose rank is {@code k}.
     * This is the (k+1)st smallest key in the symbol table.
     *
     * @param  k the order statistic
     * @return the key in the symbol table of rank {@code k}
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>n</em>–1
     */
    public Key select(int k) {
        count = 0;

        if(k < 0 || k >= size()){
            throw new IllegalArgumentException("Rank is between 0 and its size");
        }
        return select(root, k+1).key;
    }

    // Return key of rank k
    private Node select(Node x, int k) {
        Stack<Node> stack = new Stack<Node>();

        Node p = root;
        while(!stack.empty() || p != null){
            if(p != null){
                stack.push(p);
                p = p.left;
            }
            else{
                p = stack.pop();
                count++;
                if(count == k)
                    break;
                p = p.right;
            }
        }
        return p;
    }

    
    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo} 
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if(lo == null || hi == null){
            throw new IllegalArgumentException("Argument to keys() is null");
        }
        
        Queue<Key> queue = new LinkedList<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x,Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) {
            return;
        }
        Node node1 = x;
        while (node1 != null){
            int comp = node1.key.compareTo(hi);
            int camp = node1.key.compareTo(lo);

            if (node1.left == null) {
                if (comp <= 0 && camp >= 0) queue.offer(node1.key);
                node1 = node1.right;
            }
            else {
                Node node2 = node1.left;
                while (node2.right != null && node2.right != node1)
                    node2= node2.right;
                if (node2.right == null) {
                    node2.right = node1;
                    node1 = node1.left;
                }
                else {
                    node2.right = null;
                    if (comp <= 0 && camp >= 0) queue.offer(node1.key);
                    node1 = node1.right;
                }
            }
        }
    }


    public static void main(String[] args) { 
        Binarysearchtree<String, Integer> binaryst = new Binarysearchtree<String, Integer>();

        binaryst.put("ABDUL", 1);
        System.out.println(binaryst.get("ABDUL"));
        binaryst.put("HRITHIK", 2);
        binaryst.put("SAI", 3);
        binaryst.put("SAMAL", 6);
        System.out.println(binaryst.get("SAI"));;
        binaryst.put("TASHI", 4);
        System.out.println("Size: " + binaryst.size());
        System.out.println("Minimum: " + binaryst.min());
        System.out.println("Floor: " + binaryst.floor("HRITHIK"));
        System.out.println("Floor: " + binaryst.floor("HAHA"));
        System.out.println("Select: " + binaryst.select(2));
        System.out.println("Keys: " + binaryst.keys("ABDUL", "TASHI"));
        binaryst.put("CHIMI", 5);
        binaryst.put("SAMAL", 4);
        System.out.println(binaryst.get("SAMAL"));
        binaryst.put("NIMA", 7);
        System.out.println("Size: " + binaryst.size());
        System.out.println(binaryst.get("CHIMI"));
        System.out.println("Floor: " + binaryst.floor("CHIMA"));
        binaryst.put("SONAM", 8);
        System.out.println("Keys: " + binaryst.keys("ABDUL", "TASHI"));   
    }
}