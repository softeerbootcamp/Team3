package lightning.gathergo.service;

import lightning.gathergo.repository.JdbcArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;

@Service
public class ArticleService {

    private JdbcArticleRepository repo;

    @Autowired
    ArticleService(DataSource dataSource) {
        repo = new JdbcArticleRepository(dataSource);
    }

    public byte[] convertMultipartFileToByte(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }
}
