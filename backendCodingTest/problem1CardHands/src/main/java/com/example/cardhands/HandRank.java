package com.example.cardhands;

public enum HandRank {

    FIVECARD(1),STRAIGHT(2),FOURCARD(3),FULLHOUSE(4),TWOPAIR(5),OTHER(6);

    private int rank;

    HandRank(int rank){
        this.rank = rank;
    }

    public int getRank(){
        return rank;
    }



}
