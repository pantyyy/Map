
//350. 两个数组的交集II
//给定两个数组，编写一个函数来计算它们的交集。
//
//        示例 1:
//
//        输入: nums1 = [1,2,2,1], nums2 = [2,2]
//        输出: [2,2]
//        示例 2:
//
//        输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//        输出: [4,9]

import java.util.ArrayList;
import java.util.TreeMap;

public class Solution350 {
    public int[] intersect(int[] nums1, int[] nums2) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        //把nums1中的数字放入map中
        for(int num : nums1){
            //判断是否已经在map中 , 如果不在 , 则添加 , 如果在 , 则更新计数
            if(!map.containsKey(num)){
                map.put(num , 1);                   //不存在 , 添加
            }else{
                map.put(num , map.get(num) + 1);    //存在 , 更新计数
            }
        }

        ArrayList<Integer> res = new ArrayList<>();

        //map中的数字和nums2中的数字进行比较 , 判断是否有相同的
        //如果有 , 放入list中 , 并把map中对应的计数减1 , 如果计数为0 , 则从map中移除数字
        for(int num : nums2){
            if (map.containsKey(num)){
                res.add(num);
                map.put(num , map.get(num) - 1);
                if(map.get(num) == 0){
                    map.remove(num);
                }
            }
        }

        int[] ret = new int[res.size()];
        for(int i = 0 ; i < res.size() ; i ++)
            ret[i] = res.get(i);

        return ret;
    }

}
