package com.example.pixelcanvas;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PixelCanvasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PixelCanvasApplication.class, args);


		//픽셀 16*16 세팅
		@PostConstruct

	}


	// 유저 , 픽셀

	//유저 , uuid , 닉네임 , 어드민 여부

	//픽셀 좌표 , 색상 , 업데이트 된 시간 , 소유한 유저
	//업데이트 안됬으면 빈 유저정보 , 검은색
	//어드민 유저면 빈 유저정보 , 업데이트 된 시간 x

	//유저가 픽셀 소지

	//repository
	// 유저 - 픽셀

	//랭킹 - 픽셀수 개수 top 100 , 어드민 제외 -> 람다로


}
