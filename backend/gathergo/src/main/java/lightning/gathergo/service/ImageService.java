package lightning.gathergo.service;

import lightning.gathergo.dto.ImageDto;
import lightning.gathergo.repository.AmazonS3ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    private final AmazonS3ResourceRepository amazonS3ResourceRepository;
    @Autowired
    public ImageService(AmazonS3ResourceRepository amazonS3ResourceRepository) {
        this.amazonS3ResourceRepository = amazonS3ResourceRepository;
    }

    public ImageDto save(UUID userId, MultipartFile multipartFile) {
        ImageDto fileDetail = ImageDto.multipartOf(userId,multipartFile);
        System.out.println("path: "+fileDetail.getPath());
        System.out.println("format: "+fileDetail.getFormat()); //format: svg+xml
        logger.info(fileDetail.toString());
        amazonS3ResourceRepository.save(fileDetail.getPath(), multipartFile);
        return fileDetail;
    }

}
