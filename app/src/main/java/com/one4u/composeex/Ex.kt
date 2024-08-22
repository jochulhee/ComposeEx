package com.one4u.composeex

class Solution {

//    fun solution(want: Array<String>, number: IntArray, discount: Array<String>): Int {
//        var answer: Int = 0
//        val dummyList = mutableListOf<String>()
//        val wantList = mutableListOf<String>()
//        want.mapIndexed { idx, s ->
//            for (cnt in 0 until number[idx]) {
//                wantList.add(s)
//            }
//        }
//        wantList.sort()
//
//        // discount[n] ~ discount[n+10] 에서 want 의 원소가 number 의 갯수 만큼 포함 되어 있는 n의 갯수 체크 : 0 <= n <= discount.size - 10
//        val discountList = mutableListOf<String>()
//        for (i in 0 until  10) {
//            discountList.add(discount[i])
//        }
//
//        // check first
//        var cnt = 0
//        dummyList.clear()
//        dummyList.addAll(discountList)
//        dummyList.sort()
//
//        for (k in 0..9) {
//            if (dummyList[k] == wantList[k])
//                cnt++
//            else break
//        }
//        if (cnt == 10) answer ++
//
//        for (i in 10 until discount.size) {
//            discountList.add(discount[i])
//            discountList.removeAt(0)
//
//            // check another
//            cnt = 0
//            dummyList.clear()
//            dummyList.addAll(discountList)
//            dummyList.sort()
//            if (wantList.containsAll(dummyList)) System.out.println("containsAll : $i")
//
//            for (k in 0..9) {
//                if (dummyList[k] == wantList[k])
//                    cnt++
//                else break
//            }
//            if (cnt == 10) answer ++
//        }
//
//        return answer
//    }

//    fun solution(priorities: IntArray, location: Int): Int {
//        var answer = 0
//        val pri = priorities.toMutableList()
//        var curLoc = location
//
//        var loc = priorities.size - 1
//        while (true) {
//            answer++
//            if (curLoc == loc) {
//                break
//            }
//            loc--
//
//            val min = java.util.Collections.min(pri)
//            if (min < pri[loc]) {
//                pri.add(0, pri.removeAt(loc))
//                curLoc ++
//            } else {
//                pri.removeAt(loc)
//            }
//        }
//
//        return answer
//    }

//    fun solution(price: Int, money: Int, count: Int): Long {
//        var answer: Long = -1
//
//        // count sum sigma.
//        // 1 + 2 + 3 + ... + count-1 + count
//        val sumCnt:Long = count * (count + 1)/2L
//        val sum = price * sumCnt
//        answer = if (money < sumCnt) {
//            sumCnt - money
//        } else {
//            0
//        }
//
//        return answer
//    }

//    private fun quickSort(arr: IntArray, left: Int = 0, right: Int = arr.size - 1) {
//        if(left >= right) return
//
//        val pivot = partition(arr, left, right)
//
//        quickSort(arr, left, pivot - 1)
//        quickSort(arr, pivot + 1, right)
//    }
//
//    private fun partition(arr: IntArray, left: Int, right: Int): Int {
//        var low = left
//        var high = right
//        val pivot = arr[(left + right) / 2]
//
//        while (low <= high) {
//            while (arr[low] < pivot && low < high) low++
//            while (arr[high] > pivot && low < high) high--
//
//            if(low < high)
//                swap(arr, low, high)
//            else
//                break
//        }
//
//        return high
//    }
//
//    private fun swap(arr: IntArray, i: Int, j: Int) {
//        var temp = arr[i]
//        arr[i] = arr[j]
//        arr[j] = temp
//    }
//
//    fun solution(n: Int, k: Int, enemy: IntArray): Int {
//        var clrRound : IntArray
//        val mtClrRound = mutableListOf<Int>()
//        var soldier = n
//        var answer: Int = k
//
//        if (k >= enemy.size)// || enemy.sum() <= n)
//            return enemy.size
//
//        for (i in 0 until k) {
//            mtClrRound.add(enemy[i])
//        }
//        clrRound = mtClrRound.toIntArray()
//        quickSort(clrRound)
//        for (i in k until enemy.size) {
//            val minEnemy = clrRound[0]
//            if (minEnemy < enemy[i]) {
//                if (soldier >= minEnemy) {
//                    soldier -= minEnemy
//
//                    mtClrRound.remove(minEnemy)
//                    mtClrRound.add(enemy[i])
//                    clrRound = mtClrRound.toIntArray()
//                    quickSort(clrRound)
//
//                    answer++
//                } else {
//                    break
//                }
//            } else {
//                if (soldier >= enemy[i]) {
//                    soldier -= enemy[i]
//                    answer++
//                } else {
//                    break
//                }
//            }
//        }
//
//        return answer
//    }

//    fun solution(x: Int, y: Int, n: Int): Int {
//        var answer: Int = 0
//
//        // x -> y
//        // 1) x * 2
//        // 2) x * 3
//        // 3) x + n
//        // count minimum step, x to y. else -1
//
//
//
//        //
//        if (y % x == 0) {
//            if (y % 2 == 0) {
//                // y/2 ==?
//            }
//            else if (y % 3 == 0) {
//                // y/3 ==?
//            }
//        } else {
//            // x + n...
//
//        }
//
//        return answer
//    }

//    fun solution(friends: Array<String>, gifts: Array<String>): Int {
//        val nameMap = mutableMapOf<String, Int>()
//        val ptMap = mutableMapOf<String, Int>()
//        val giftMap = mutableMapOf<String, Int>()
//        friends.forEach { name ->
//            ptMap[name] = 0
//            giftMap[name] = 0
//        }
//
//        gifts.forEach { gift ->
//            // 선물 지수
//            val ptNameSend = gift.split(" ")[0]
//            val ptNameRcv = gift.split(" ")[1]
//            ptMap[ptNameSend] = ptMap[ptNameSend]!! + 1
//            ptMap[ptNameRcv] = ptMap[ptNameRcv]!! - 1
//
//            nameMap[gift] = (nameMap[gift] ?: 0)  + 1
//        }
//
//        for (i in 0 .. friends.size - 2) {
//            for (j in i until friends.size) {
//                // gift i to j
//                val send = "${friends[i]} ${friends[j]}"
//                val recv = "${friends[j]} ${friends[i]}"
//                val sendPt = nameMap[send] ?: 0
//                val recvPt = nameMap[recv] ?: 0
//                when {
//                    sendPt > recvPt -> {
//                        giftMap[friends[i]] = giftMap[friends[i]]!! + 1
//                    }
//                    sendPt < recvPt -> {
//                        giftMap[friends[j]] = giftMap[friends[j]]!! + 1
//                    }
//                    else -> {
//                        if (ptMap[friends[i]]!! > ptMap[friends[j]]!!) {
//                            giftMap[friends[i]] = giftMap[friends[i]]!! + 1
//                        }
//                        else if (ptMap[friends[i]]!! < ptMap[friends[j]]!!) {
//                            giftMap[friends[j]] = giftMap[friends[j]]!! + 1
//                        }
//                    }
//                }
//            }
//        }
//
//
//
//        return giftMap.maxOf { (_, value) ->
//            value
//        }.toInt()
//    }
}