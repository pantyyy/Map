import java.util.ArrayList;

//key需要有比较性 , 所以k要实现Comparable接口
public class BSTMap<K extends Comparable<K> , V> implements Map<K , V> {


    //内部的节点类
    private class Node{
        private K key;
        private V value;
        private Node left , right;

        public Node(K key , V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }

    }


    //一颗树维护的属性有树根和树元素的个数
    private Node root;
    private int size;

    //无参构造方法
    public BSTMap(){
        root = null;
        size = 0;
    }









    @Override
    public void add(K key, V value) {
        root = add(root , key , value);
    }

    //向以node为根节点的树中添加元素 , 添加完成后 , 返回树的根
    private Node add(Node node , K key , V  value){

        //最简单的情况 , node等于null , 那么就创建节点 , 并返回节点的指针即可
        if (node == null){
            size++;
            return new Node(key , value);
        }

        //由更小规模的解 , 构造出当前问题的解
        //首先需要判断在哪颗子树进行元素的添加
        if (key.compareTo(node.key) < 0)
            //向左子树中添加 , 并把添加完成之后的树接到当前节点的左子树上
            node.left = add(node.left , key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right , key , value);
        else
            //如果key已经存在 , 更新value的值即可
            node.value = value;

        return node;
    }


    //根据key寻找node节点
    private Node getNode(Node node , K key){
        //最简单的情况 , 没有找到
        if(node == null)
            return null;

        if (key.equals(node.key))
            return node;
        //判断应该在那颗子树中进行寻找
        else if (key.compareTo(node.key) < 0)
            return getNode(node.left , key);
        else
            return getNode(node.right , key);
    }

    @Override
    public boolean contains(K key) {
        return getNode(root , key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root , key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V value) {
        //首先找到目标元素
        Node node = getNode(root , key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = value;
    }

    //返回以node为根的二叉树中的最小节点
    private Node minimum(Node node){
        if (node.left == null)
            //二分搜索树的最小节点就是最左边的节点
            return node;
        return minimum(node.left);
    }

    //删除以node为根的二分搜索树中的最小节点
    //返回删除结点后 , 新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            //删除掉node节点后 , node节点的右子树就是这颗树的根
            Node rightNode = node.right;
            //删除node节点和它右子树的联系
            node.right = null;
            size--;
            //返回树根
            return rightNode;
        }

        //当前解 = node.left的解 + 接到node的左子树上
        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public V remove(K key) {

        Node node = getNode(root , key);
        if(node != null){
            root = remove(root , key);
            return node.value;
        }

        return null;
    }


    //根据key , 删除以node为根节点树的节点 , 返回删除之后这棵树的根
    private Node remove(Node node , K key){
        if(node == null){
            return null;
        }

        if(key.compareTo(node.key) < 0){
            node.left = remove(node.left , key);
            return node;
        }else if(key.compareTo(node.key) > 0){
            node.right = remove(node.right , key);
            return node;
        }else
        {
            //需要删除的节点

            //待删除元素的左子树为空
            if (node.left == null){
                //因为左子树为空 , 所以 , 删除元素之后 , 直接把待删除元素的右子树直接接上去即可
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            //待删除元素的右子树为空
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            //左右子树均不为空 , 那么需要找到一个元素来代替根节点
            //找到比待删除节点大的最小节点 , 即待删除节点右子树的最小节点

            //1.找到待删除节点右子树的最小节点
            Node successor = minimum(node.right);
            //2.在node.right这颗树中删除successor , 并把删除成功之后的树设置为successor的右子树
            successor.right = removeMin(node.right);
            //3.node的左子树接到successor的左子树上
            successor.left = node.left;
            //4.node断掉自己的左右子树
            node.left = node.right = null;

            //返回树的根节点
            return successor;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            BSTMap<String, Integer> map = new BSTMap<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
