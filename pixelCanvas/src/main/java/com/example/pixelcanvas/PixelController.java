package com.example.pixelcanvas;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PixelController {

    PixelRepository repo;

    public PixelController(PixelRepository repo) {
        this.repo = repo;
    }

    //픽셀 업데이트
    @PostMapping("/pixelUpdate")
    public ResponseEntity<String> pixelUpdate( @RequestBody Map<String, String> body) {

        return ResponseEntity.ok().build();

    }

    //픽셀 정보 확인
    @GetMapping("/pixelInfo")
    public ResponseEntity<String> pixelInfo( @RequestBody Map<String, String> body) {


        return ResponseEntity.ok().build();

    }

    //유저 랭킹
    @GetMapping("/ranking")
    public ResponseEntity<String> ranking() {

        return ResponseEntity.ok().build();

    }

    //전체 픽셀 불러오기
    @GetMapping("/allPixel")
    public ResponseEntity<String> allPixel() {

        return ResponseEntity.ok().build();

    }

}
