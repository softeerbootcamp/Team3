package lightning.gathergo.controller;

import lightning.gathergo.dto.CountDto;
import lightning.gathergo.service.CountService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/counts", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CountController {

    private final CountService countService;

    public CountController(CountService countService) {
        this.countService = countService;
    }


    @GetMapping
    public ResponseEntity<JSONObject> getRegions() {
        JSONObject ob = new JSONObject(countService.getCounts());
        return ResponseEntity.ok().body(ob);
    }

    @GetMapping("/{articleUuid}")
    public ResponseEntity<?> getRegionById(@PathVariable String articleUuid) {
        JSONObject ob = new JSONObject();
        ob.put("articleUuid",articleUuid);
        ob.put("count",countService.getCount(articleUuid));
        return new ResponseEntity<JSONObject>(ob, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createCount(@RequestBody CountDto.Request in){
        countService.createCount(in.getArticleId(), in.getCount());
        JSONObject ob = new JSONObject();
        ob.put("articleUuid",in.getArticleId());
        ob.put("count",in.getCount());
        return ResponseEntity.ok().body(ob);
    }

    @PutMapping
    public ResponseEntity<?> modifyCount(@RequestBody CountDto.Request in) {
        JSONObject ob = new JSONObject();
        ob.put("articleUuid",in.getArticleId());
        ob.put("count",countService.modifyCount(in.getArticleId(),in.getCount()));
        return ResponseEntity.ok().body(ob);
    }

    @DeleteMapping("/{articleUuid}")
    public ResponseEntity<?> deleteRegion(@PathVariable String articleUuid) {
        countService.deleteCount(articleUuid);
        return ResponseEntity.ok().body("deleted");
    }

}
