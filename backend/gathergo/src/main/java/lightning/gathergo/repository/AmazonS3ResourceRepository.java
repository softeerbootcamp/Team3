package lightning.gathergo.repository;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lightning.gathergo.Utils.MultipartUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;

@Component
public class AmazonS3ResourceRepository {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    @Autowired
    public AmazonS3ResourceRepository(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public void save(String fullPath, MultipartFile multipartFile) {
        //MultipartFile을 File 객체의 형태로 변환
        File file = new File("/home/ubuntu/compose", fullPath);
        System.out.println("file path: "+ file.getPath());
        try {
            //위에서 만든 파일객체의 경로와 리네임으로 실제 업로드 하기위해transferTo()메서드로 업로드처리
            //multipartFile.transferTo(file);
            Path path = Paths.get(file.getPath()).toAbsolutePath();
            multipartFile.transferTo(path);
            //S3에 파일을 업로드할 때에는 해당 파일(S3에서는 객체)의 권한을 CannedAccessControlList.PublicRead 로 설정해주어야 누구나 파일에 접근이 가능
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException();
        } finally {
            //로컬(서비스가 구동하는 서버)에서 파일이 복사되어 임시파일과 같이 로컬에 저장이되는데 finally 구문에서 해당 파일을 제거해주도록
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
