import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//二分搜索树中的每个元素必须具有可比较性
public class BST<E extends Comparable<E>> {
    private class Node{
        public E e;
        public Node left;
        public Node right;
        int depth;  //节点的深度
        int count = 1;  //重复节点计数

        public Node(E e){
            this.e = e;
            left = null;
            right = null;
        }
    }

    //二叉树的根节点
    private Node root;
    //树中的有效元素个数
    private int size;

    public BST(){
        root = null;
        size = 0;
    }


    public int size(){return size;}
    public boolean isEmpty(){return size == 0;}


    //向二叉树中添加元素
    public void add(E e){
        //判断是否是空的二叉树
        if(root == null){
            root = new Node(e);
            size++;
            root.depth = 0;
        }else
            //向root为根节点的数插入元素 , 初始的高度是0
            //因为 , 如果root==null , 那么插入的元素就是根节点 , 根的深度为0
            add(root , e , 0);
    }

    //向以node为根节点的二叉搜索树添加元素
//    private void add(Node node , E e){
//        //递归结束条件(最基本的模式)
//        //1.相等 , 2.左节点为空 , 3.右节点为空
//        if(e.equals(node.e)){
//            return;
//        //插入左节点
//        }else if(e.compareTo(node.e) < 0 && node.left == null){
//            node.left = new Node(e);
//            size++;
//            return;
//        }
//        //插入右节点
//        else if(e.compareTo(node.e) > 0 && node.right == null){
//            node.right = new Node(e);
//            size++;
//            return;
//        }
//
//
//        //由变小的规模解决当前问题
//        //更小的规模就是往左子树或者右子树中插入
//        if(e.compareTo(node.e) < 0 )
//            add(node.left , e);
//        else
//            add(node.right , e);
//    }

    //改进上面的添加函数
    //向以node为根节点的二叉搜索树中插入元素 , 并返回这颗树的根节点
    private Node add(Node node , E e , int depth){
        //递归结束的条件 : 当传入的node为null时 , 就需要创建节点了
        if(node == null){
            //一个节点可以看做是一颗二叉搜索树 , 所以返回这颗树的根节点
            size++;
            //就是返回node
            Node temp = new Node(e);
            temp.depth = depth;
            return temp;
        }

        //更小的规模
        //判断是插入左子树还是右子树
        if(e.compareTo(node.e) < 0)
            //因为add函数返回的是以node.left为节点的二叉树
            //所以需要接在当前的树上
            //因为是插入子树 , 所以depth的深度需要加1
            node.left = add(node.left , e , depth + 1);
            //因为有等于的情况没有判断 , 所以不能直接使用else
            //会把等于的情况包括进去
        else if(e.compareTo(node.e) > 0)
            //因为是插入子树 , 所以depth的深度需要加1
            node.right = add(node.right , e , depth + 1);
        else{
            //相等 , 当前元素的count加1
            node.count += 1;
        }

        return node;
    }


    //查看二分搜索树中是否包含指定元素
    public boolean contain(E e){
        return contain(root , e);
    }

    //在以node为根节点的树中寻找元素e
    private boolean contain(Node node , E e){
        //递归结束条件
        if(node == null){
            return false;
        }

        if(e.compareTo(node.e) == 0){
            return true;    //找到了目标元素
        }else if(e.compareTo(node.e) < 0){
            //向左子树中寻找
            return contain(node.left , e);
        }else
            return contain(node.right , e);
    }

    //寻找指定的元素
    public Node findNode(E e){return findNode(root , e);}

    //以node为根节点 , 寻找值为e的节点
    private Node findNode(Node node , E e){

        //结束条件
        //找到了
        if(e.compareTo(node.e) == 0){
            return node;
        }

        //没有找到
        if(node == null){
            return null;
        }

        //当前解
        if(e.compareTo(node.e) < 0){
            //从当前节点的左子树中寻找
            return findNode(node.left , e);
        }else{
            return findNode(node.right , e);
        }
    }

    //前序遍历
    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node node){
        //结束条件
        if(node == null){
            return;
        }

        //前序遍历 , 在第一次访问节点的时候就访问该元素
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }


    //前序遍历的非递归实现
    private void preOrderNR(){
        //辅助用的栈结构
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        //每个节点进栈 , 然后出栈 , 并把节点的左右节点压入栈中
        //如果栈为空 , 则表示结束
        while(!stack.isEmpty()){
            Node temp = stack.pop();
            System.out.println(temp.e);

            //因为需要先访问左节点 , 而且使用的结构是栈
            //所以需要把右节点先入栈 , 然后左节点再入栈
            //这样左节点才在右节点的上方
            if(temp.right != null)
                stack.push(temp.right);
            if(temp.left != null)
                stack.push(temp.left);

        }
    }

    //中序遍历
    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node node){
        if(node == null)
            return;

        //中序遍历是访问节点的第2次才访问其中的值
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node){
        if(node == null){
            return;
        }

        //后序遍历是第二次才访问元素
        postOrder(node.left);
        postOrder(node.right);

        System.out.println(node.e);
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateString(root , res , 0);
        return res.toString();
    }


    //生成以node为根节点的字符串描述
    private void generateString(Node node , StringBuilder res , int depth){
        //使用先序遍历的方法遍历元素
        if(node == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.e + "\n");
        generateString(node.left , res , depth + 1);
        generateString(node.right , res , depth + 1);
    }

    //根据对应的层数 , 生成对应个数的--
    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i++){
            res.append("--");
        }
        return res.toString();
    }


    //层序遍历
    public void levelOrder(){
        Queue<Node> q = new LinkedList<>();
        //根节点入队
        q.add(root);

        //判断队列是否为空
        //队列不为空的时候 , 循环执行
        while(!q.isEmpty()){
            //出队元素 , 进行访问
            Node temp = q.remove();
            System.out.println(temp.e);

            //此节点的左右节点依次入队 , 为下次循环做好准备
            if(temp.left != null)
                q.add(temp.left);
            if(temp.right != null)
                q.add(temp.right);

        }
    }

    //寻找二分搜索树中的最小值
    public E minimum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty!!!");
        return minimum(root).e;
    }

    //递归实现
    private Node minimum(Node node){
        if(node.left == null)
            return node;

        return minimum(node.left);
    }

    //非递归实现
    private E minimumNR(){
        Node temp = root;
        while(temp.left != null){
            temp = temp.left;
        }

        return temp.e;
    }


    //求最大值
    public E maximum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty!!!");
        return maximum(root).e;
    }

    //递归实现
    private Node maximum(Node node){
        if(node.right == null)
            return node;

        return maximum(node.right);
    }

    //非递归实现
    private E maximumNR(){
        Node temp = root;
        while(temp.right != null){
            temp = temp.right;
        }

        return temp.e;
    }


    //删除最小值
    public E removeMin(){
        //找到最小节点
        E ret = minimum();

        //删除最小节点 , 并更新root
        root = removeMin(root);

        return ret;
    }

    private Node removeMin(Node node){

        if(node.left == null){
            //删除最小节点
            //保存最小节点的右节点
            Node temp = node.right;
            node.right = null;
            //有效元素减1
            size--;
            return temp;
        }

        node.left = removeMin(node.left);
        return node;
    }

    //删除最大节点
    public E removeMax(){
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    //删除以node为根节点的二叉搜索树中的最大节点
    //并返回删除后的根节点
    private Node removeMax(Node node){
        //结束的条件
        //当node的右节点为空时 , 代表当前就是需要删除的节点
        if(node.right == null){
            //删除当前的节点
            //删除当前节点 , 并返回删除之后的树的根节点
            //由因为是最大值 , 所以当前节点必定没有右子树
            //所以返回当前节点的左子树即可

            //使用指针指向当前节点的右子树
            Node temp = node.left;
            //断开当前节点
            node.left = null;
            //有效元素减1
            size--;
            //返回右节点的地址
            return temp;
        }


        //当前的解 = 当前节点的左指针 +(挂上) 当前节点的右子树删除最大元素之后的树
        node.right = removeMax(node.right);
        //返回当前树的根节点
        return node;

    }

    //删除树中的某个节点
    public void remove(E e){
        root = remove(root , e);
    }

    //删除树中某个指定的元素
    //返回删除元素之后的树根
    private Node remove(Node node , E e){

        //结束的条件
        //找到元素 或者 没有找到元素

        //没有找到元素
        if(node == null){
            return null;
        }

        //找到了元素 , 删除元素
        if(e.compareTo(node.e) == 0){
            //情况一 : 当前节点没有左子树
            if(node.left == null){
                Node temp = node.right;

                node.right = null;
                size--;
                return temp;
                //情况二 : 当前节点没有右子树
            }else if(node.right == null){
                Node temp = node.left;
                node.left = null;
                size--;
                return temp;
            }
            //情况三 : 左右子树都不为空
            else{
                //首先找到以node.right为根 , 这个树中的最小值successor
                //把successor作为替代的根即可
                Node successor = minimum(node.right);
                E temp = successor.e;
                Node rootTemp = new Node(temp);
                rootTemp.depth = successor.depth;
                rootTemp.count = successor.count;

                Node nodeRight = removeMin(node.right);
                rootTemp.left = node.left;
                rootTemp.right = nodeRight;

                return rootTemp;
            }

            //return null;
        }

        //首先判断e往哪边走
        if(e.compareTo(node.e) < 0){
            //往左边走
            //当前解 = node.left + remove(node.left , e)
            node.left = remove(node.left , e);
            return node;
        }else{
            //往右边走
            node.right = remove(node.right , e);
            return node;
        }

        //return null;
    }

    //更新树的深度 , 因为 , 删除节点 , 可能导致树的深度发生变化
    public void updateDepth(Node node , int depth){
        if (node == null)
            return;

        //判断是否是叶子节点
        if(node.right == null && node.left == null){
            node.depth = depth;
            return;
        }



        //更新右子树的深度
        updateDepth(node.right , depth + 1);
        //更新左子树的深度
        updateDepth(node.left , depth + 1);

        //当前解 = 更新当前节点的深度 + 更新左子树的深度 + 更新右子树的深度
        node.depth = depth;
    }



    public static int sum(int n){

        if(n == 0){
            return 0;
        }
        int temp = n + sum(n - 1);
        return temp;
    }

    public static void main(String[] args) {

        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for(int num: nums)
            bst.add(num);

        bst.add(5);
        /////////////////
        //      5      //
        //    /   \    //
        //   3    6    //
        //  / \    \   //
        // 2  4     8  //
        /////////////////
        //bst.preOrder();
        bst.remove(5);
        bst.remove(3);

        bst.updateDepth(bst.root , 0);
        System.out.println(bst.findNode(6).depth);
        //bst.levelOrder();
    }
























}
