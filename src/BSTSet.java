public class BSTSet<E extends Comparable<E>> implements Set<E> {

    //底层使用的数据结构
    private BST<E> bst; //二分搜索树


    public BSTSet(){
        //新建一个二分搜索树对象
        bst = new BST<>();
    }



    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contain(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public int getSize() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
