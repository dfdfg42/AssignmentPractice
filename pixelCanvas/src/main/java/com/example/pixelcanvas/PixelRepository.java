package com.example.pixelcanvas;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class PixelRepository {

    //concurrent hash map
    //유저 - List<픽셀>

    ConcurrentHashMap<Pixel, PixelInfo > userMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String , Instant> lastUpdateMap = new ConcurrentHashMap<>();
    //uuid  - > 유저 조회
    ConcurrentHashMap<String , User> userRepo = new ConcurrentHashMap<>();


    //유저의 픽셀 업데이트 - 유저의 마지막 업뎃이 1분 이상이여야함
    //어드민이면 쿨타임 존재 x

    public boolean put(String uuid, Pixel pixel, PixelInfo pixelInfo) {

        //쿨타임 제기
        Instant userLast = lastUpdateMap.get(uuid);
        User user = userRepo.get(uuid);
        Instant oneMinuteAgo = userLast.minus(1, ChronoUnit.MINUTES);
        Duration duration = Duration.between(oneMinuteAgo, Instant.now());
        //1분전 아니면 false  return , 이거나 어드민
        if(duration.toMinutes() > 1 || user.isAdmin() ){

            return false;
        }

        //맵은 이미 빈값들 16*16 넣어놔서 비어있지 않음
        //compute 연산 해야함
        userMap.compute(pixel, (k, existing) -> {
            existing = pixelInfo;
            return existing;
        });

        //리스트 아니라 원자성 지켜짐 , put 연산하는데 요청이 한번 더오면 ?
        lastUpdateMap.put(uuid, Instant.now());


        return true;

    }


    //픽셀 정보 확인
    //업뎃 안됬으면 빈 유저정보와 검은색 반환
    public PixelInfo get(Pixel pixel) {


        PixelInfo pixelInfo = userMap.get(pixel);

        if(pixelInfo.getLastUpdate() == null){

            //빈 유저정보와 검은색
            pixelInfo.setUserName(null);
            pixelInfo.setColor("black");
        }

        //uuid 보고 유저 조회해야함
        User u = userRepo.get(pixelInfo.getUuid());

        return pixelInfo;

    }


    //랭킹
    //16* 16 전체를 훑어서 랭킹 비교
    public List<User> getRank(){

        HashMap<String , Integer> rankMap = new HashMap();

        //하드코딩으로 전부 줄세워서 반환
        for(int i=1; i<=16 ;i++){
            for(int j=1; j<=16; j++){

                Pixel pixel = new Pixel(i,j);
                PixelInfo pixelInfo = userMap.get(pixel);

                //uuid 사용해야함
                String userUuid = pixelInfo.

            }
        }

        //람다로 값 순으로 정렬해서 반환
        return new ArrayList<>(rankMap.values().);
    }


}
