package lightning.gathergo.dto;

import lightning.gathergo.Utils.MultipartUtil;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 업로드 파일에 대한 핵심 속성을 가지는 데이터 클래스
 *
 * fileId는 36자리의 UUID 형태로 생성해주도록
 *
 * format은 해당 파일의 확장자에 대한 값. ex) .jpg, .png, .pdf, .xls 등등
 *
 * name은 파일 업로드 시점의 파일명(사용자 아이디)
 *
 * path는 파일의 실제 물리적 경로값
 */

public class ImageDto {
    private String id;
    private String name;
    private String format;
    private String path;
    private long bytes;

    private LocalDateTime createdAt;

    public static ImageDto multipartOf(UUID userId, MultipartFile multipartFile) {
        final String fileId = String.valueOf(userId);
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        return new ImageDto(fileId,multipartFile.getOriginalFilename(),format,MultipartUtil.createFileNameFrom(fileId, format),multipartFile.getSize());
    }

    public ImageDto(String id, String name, String format, String path, long bytes) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.path = path;
        this.bytes = bytes;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
