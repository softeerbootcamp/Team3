package lightning.gathergo.service;

import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.dto.GatheringDto;
import lightning.gathergo.model.Comment;
import lightning.gathergo.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    // 생성
    Comment createComment(Comment comment){
        comment.setUuid(gerneratedUuid());
        comment = commentRepository.save(comment);
        return comment;
    }

    // 수정
    Comment updateComment(String content, Timestamp date, String commentUuid){
        Comment comment = commentRepository.findByUuid(commentUuid);
        commentRepository.updateByUuid(content, date, commentUuid);
        comment = commentRepository.findByUuid(commentUuid);
        return comment;
    }

    // 삭제
    void deleteCommentByUuid(String commentUuid){
        commentRepository.deleteByUuid(commentUuid);
    }

    public void setIsMyComment(GatheringDto.ArticleDetailResponse data, String userId){
        List<CommentDto.Response> comments = data.getComments();
        comments.forEach(comment -> {
            if(comment.getUserId().equals(userId))
                comment.setIsMyComment(true);
        });
    }

    private String gerneratedUuid(){
        return UUID.randomUUID().toString();
    }
}
