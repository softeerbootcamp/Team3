package lightning.gathergo.controller;

import lightning.gathergo.dto.RegionDto;
import lightning.gathergo.exception.CustomGlobalException;
import lightning.gathergo.exception.ErrorResponse;
import lightning.gathergo.mapper.RegionDtoMapper;
import lightning.gathergo.model.Region;
import lightning.gathergo.service.RegionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping(value = "/regions", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class RegionController {
    private final Logger logger = LoggerFactory.getLogger(RegionController.class);
    private final RegionService regionService;
    private final RegionDtoMapper regionDtoMapper;
    @Autowired
    public RegionController(RegionService regionService, ModelMapper modelMapper, RegionDtoMapper regionDtoMapper) {
        this.regionService = regionService;
        this.regionDtoMapper = regionDtoMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getRegions() {
        System.out.println(System.getProperty("user.home"));
        logger.info(System.getProperty("user.home"));
        List<RegionDto.Response> regions = regionDtoMapper.toRegionResponseList(regionService.getRegions());

        Map<String,Object> result = new HashMap<>();
        result.put("data",regions);
        result.put("count",regions.size());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{regionId}")
    public ResponseEntity<?> getRegionById(@PathVariable Integer regionId) {
        RegionDto.Response regionResponse;
        regionResponse = regionDtoMapper.toRegionResponse(regionService.getRegionById(regionId).get());
        return new ResponseEntity<RegionDto.Response>(regionResponse, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<RegionDto.Response> createRegion(@RequestBody RegionDto.CreateRequest in) {
        Region region = regionService.createRegion(in.getName()).get();
        RegionDto.Response response = regionDtoMapper.toRegionResponse(region);
        return ResponseEntity.ok().body(response);
    }


    @PutMapping
    public ResponseEntity<RegionDto.Response> modifyRegion(@RequestBody RegionDto.ModifyRequest in) {
        Region region = regionDtoMapper.toRegion(in);
        Optional<Region> modified = regionService.updateRegion(in.getId(), in.getName());
        RegionDto.Response response = regionDtoMapper.toRegionResponse(modified.get());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{regionId}")
    public ResponseEntity<String> deleteRegion(@PathVariable Integer regionId) {
        regionService.deleteRegionById(regionId);
        return ResponseEntity.ok().body("deleted");
    }


    @DeleteMapping
    public ResponseEntity<String> deleteRegions() {
        regionService.deleteRegions();
        return ResponseEntity.ok().body("all deleted");
    }

}
