package lightning.gathergo.controller;

import lightning.gathergo.dto.ImageDto;
import lightning.gathergo.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/image", produces = APPLICATION_JSON_VALUE)
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ImageDto> uploadImage(@PathVariable UUID userId, @RequestPart("file") MultipartFile multipartFile) {
        ImageDto fileDetail = ImageDto.multipartOf(userId,multipartFile);
        imageService.save(userId,multipartFile);
        return ResponseEntity.ok(fileDetail);
    }
}
