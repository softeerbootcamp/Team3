package lightning.gathergo.controller;

import lightning.gathergo.dto.ImageDto;
import lightning.gathergo.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/image", produces = APPLICATION_JSON_VALUE)
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ImageDto> uploadImage( @RequestPart("file") MultipartFile multipartFile) {
        ImageDto fileDetail = ImageDto.multipartOf(multipartFile);
        imageService.save(multipartFile);
        return ResponseEntity.ok(fileDetail);
    }
}
