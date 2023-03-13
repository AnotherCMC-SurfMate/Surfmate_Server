package cmc.surfmate.health;

import cmc.surfmate.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * HealthController.java
 *
 * @author jemlog
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<CommonResponse> getHealth()
    {
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,String> map2 = new HashMap<>();
        map2.put("inner","머식");
        map.put("outer",map2);
        return ResponseEntity.ok(new CommonResponse(200,"성공",map));
    }

    @GetMapping("/health2")
    public ResponseEntity<CommonResponse> getHealth2()
    {
        HashMap<String,String> map = new HashMap<>();
        HashMap<String,String> map2 = new HashMap<>();
        map2.put("first","머식");
        map.put("second","제민32131");
        List<HashMap<String, String>> result = List.of(map, map2);

        return ResponseEntity.ok(new CommonResponse(200,"성공",result));
    }

    @GetMapping("/token/check")
    public ResponseEntity<CommonResponse> tokenCheck()
    {
        return ResponseEntity.ok(new CommonResponse(200,"성공"));
    }

    @GetMapping("/")
    public String health()
    {
        return "health";
    }



}
