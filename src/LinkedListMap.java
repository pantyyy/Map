import java.util.ArrayList;

public class LinkedListMap<K , V> implements Map<K , V> {

    //内部类 , 链表中的每个节点
    private class Node{

        public K key;
        public V value;
        public Node next;

        //构造方法 , 用户传入了键 , 值 , 和下一个节点的位置
        public Node(K key , V value , Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }


        //无参构造
        public Node(){
            //相当于传入的参数都是null
            this(null , null , null);
        }

        @Override
        public String toString(){
            return key.toString() + " : " + value.toString();
        }

    }



    //使用dummyhead , 虚拟的头结点 , 这样对于每个节点的逻辑都是一样的 , 而不用特殊照顾头结点了
    private Node dummyHead;
    private int size;

    //创建一个链表对象的时候 , 需要创建一个虚拟的头结点 , 同时size的大小设置为0
    public LinkedListMap(){
        dummyHead = new Node();
        size = 0;
    }


    //根据键获取一个node节点
    private Node getNode(K key){
        //移动的指针 , 指向一个node节点 , 初始化的时候指向的是第一个节点
        Node cur = dummyHead.next;
        while (cur != null){
            //比较键值是否相等
            if(cur.key.equals(key))
                return cur;
            cur = cur.next;
        }

        //如果没有找到 , 则返回null
        return null;
    }



    //添加一个节点
    @Override
    public void add(K key, V value) {
        //判断键是否已经存在
        Node node = getNode(key);
        if(node == null){
            //键不存在 , 创建一个新的节点放入链表中
            dummyHead.next = new Node(key , value , dummyHead.next);
            size++;
        }
        else
            //存在 , 更新值即可
            node.value = value;

    }

    //判断是否包含一个键
    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    //根据键获取值
    @Override
    public V get(K key) {
        //首先找到节点
        Node node = getNode(key);
        //判断有这个键 , 如果没有则返回null值 , 如果有 , 就返回节点中的值
        return node == null ? null : node.value;
    }


    //根据键 , 更新值
    @Override
    public void set(K key, V value) {
        //首先找到节点
        Node node = getNode(key);
        //判断是否找到了
        if (node == null)
            throw new IllegalArgumentException(key + "doesn't exist! ");
        node.value = value;
    }

    //根据一个键删除一个节点
    @Override
    public V remove(K key) {
        //要删除一个节点 , 关键是要找到被删除结点的前一个结点
        Node prev = dummyHead;

        //寻找前一个结点
        while(prev.next != null){//循环结束的条件是 , prev.next为null的时候就不应该循环了
            //也就是说prev.next指向链表最后一个元素的时候 , 循环就停止了

            //判断是否是前一个结点
            if(prev.next.key.equals(key)){
                break;
            }

            //移动指针
            prev = prev.next;
        }

        //判断是否找到了前一个结点
        //如果没有找到 , 那么prev.nest指向的一定是null
        if(prev.next != null){
            //找到了 , 开始删除结点

            //指向被删除结点的指针
            Node delNode = prev.next;

            prev.next = delNode.next;   //删除结点

            delNode.next = null;    //删除的结点和链表断开联系
            size--;
            return delNode.value;   //返回被删除结点的值
        }


        //前一个结点没有找到 , 返回null即可
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }



    //测试方法
    public static void main(String[] args){
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words : " + words.size());

            LinkedListMap<String , Integer> map = new LinkedListMap<>();
            for (String word : words)
            {
                //判断是否已经放入map中了
                if (map.contains(word))
                    //已经有了该键 , 值加1
                    map.set(word , map.get(word) + 1);
                else
                    map.add(word , 1);
            }


            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
