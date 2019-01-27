import java.util.ArrayList;


//傲慢与偏见 , 双城记 , 词汇量统计
//使用集合去重

public class Main {

    //测试不同底层数据结构的集合
    private static double testSet(Set<String> set , String filename){

        //开始时间
        long startTime = System.nanoTime();

        System.out.println(filename);
        //准备添加的元素
        ArrayList<String> words = new ArrayList<>();
        FileOperation.readFile(filename, words);
        System.out.println("Total words: " + words.size());

        //向集合中添加元素
        for(String word : words){
            set.add(word);
        }

        System.out.println("Total different words: " + set.getSize());

        //结束时间
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;

    }


    public static void main(String[] args) {
        String filename = "pride-and-prejudice.txt";

        //测试二分搜索树集合
        BSTSet<String> bstSet = new BSTSet<>();
        double time1 = testSet(bstSet , filename);
        System.out.println("BST Set : " + time1 + "s");

        System.out.println();

        //测试链表集合
        LinkedListSet linkedListSet = new LinkedListSet();
        double time2 = testSet(linkedListSet , filename);
        System.out.println("LinkdedList Set : " + time2 + "s");


    }
}
