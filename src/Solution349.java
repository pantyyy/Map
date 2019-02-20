//349. 两个数组的交集
//给定两个数组，编写一个函数来计算它们的交集。
//
//        示例 1:
//
//        输入: nums1 = [1,2,2,1], nums2 = [2,2]
//        输出: [2]
//        示例 2:
//
//        输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//        输出: [9,4]

import java.util.ArrayList;
import java.util.TreeSet;


public class Solution349 {
    public int[] intersection(int[] nums1, int[] nums2) {
        //返回的数值没有重复的 , 所以使用集合(set)可以达到去重的效果
        TreeSet<Integer> set = new TreeSet<>();
        //对num1进行去重
        for(int num : nums1)
            set.add(num);

        ArrayList<Integer> list = new ArrayList<>();

        for(int num : nums2){
            //判断是否是重复的元素 , 如果是 , 放入list数组中
            if(set.contains(num)){
                list.add(num);
                //删除集合中的元素 , 防止下一次重复比较
                set.remove(num);
            }
        }

        //创建返回的数组
        int[] res = new int[list.size()];
        for(int i = 0 ; i < list.size() ; i++)
            res[i] = list.get(i);
        return res;
    }
}
