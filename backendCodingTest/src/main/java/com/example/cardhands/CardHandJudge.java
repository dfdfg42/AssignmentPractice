package com.example.cardhands;

import java.util.*;

/**
 * 문제 1. 카드 조합 비교
 *
 * 핵심 아이디어:
 *  - 카드 5장의 숫자 빈도수(frequency)를 구하면 모든 조합을 판별할 수 있다.
 *  - 빈도수를 내림차순으로 정렬하면 패턴이 명확해진다.
 *    ex) [3,2] → 풀하우스, [4,1] → 포카드, [2,2,1] → 투페어
 *  - 스트레이트는 정렬 후 최대-최소 = 4 이고, 종류가 5가지면 성립
 *
 * 실행: main() 직접 실행 후 stdin 입력
 */
public class CardHandJudge {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        String[] parts = input.split(",");
        int[] deckOne = parseCards(parts[0].trim());
        int[] deckTwo = parseCards(parts[1].trim());

        HandRank rankOne = getRank(deckOne);
        HandRank rankTwo = getRank(deckTwo);

        int cmp = rankOne.compareTo(rankTwo); // ordinal 기준 비교 (선언 순서가 낮을수록 강함)
        if (cmp < 0)      System.out.println("First");
        else if (cmp > 0) System.out.println("Second");
        else              System.out.println("Draw");
    }

    // "3 2 1 4 5" → [3,2,1,4,5]
    static int[] parseCards(String s) {
        String[] tokens = s.split(" ");
        int[] cards = new int[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = Integer.parseInt(tokens[i]);
        }
        return cards;
    }

    static HandRank getRank(int[] deck) {
        // 1. 빈도수 계산
        Map<Integer, Integer> freq = new HashMap<>();
        for (int card : deck) freq.merge(card, 1, Integer::sum);

        List<Integer> counts = new ArrayList<>(freq.values());
        counts.sort(Collections.reverseOrder()); // 내림차순

        // 2. 스트레이트: 정렬 후 최대-최소=4, 종류=5
        int[] sorted = deck.clone();
        Arrays.sort(sorted);
        boolean straight = (freq.size() == 5) && (sorted[4] - sorted[0] == 4);

        // 3. 조합 판별 (순위 높은 것부터)
        if (counts.get(0) == 5)                                   return HandRank.FIVECARD;
        if (straight)                                             return HandRank.STRAIGHT;
        if (counts.get(0) == 4)                                   return HandRank.FOURCARD;
        if (counts.get(0) == 3 && counts.get(1) == 2)            return HandRank.FULLHOUSE;
        if (counts.get(0) == 2 && counts.get(1) == 2)            return HandRank.TWOPAIR;
        return HandRank.OTHER;
    }
}
