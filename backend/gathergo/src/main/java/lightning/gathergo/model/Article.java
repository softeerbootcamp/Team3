package lightning.gathergo.model;


import org.springframework.data.annotation.Id;

import java.sql.Date;
public class Article {
    @Id
    private Long id;
    private String title;
    private String imgPath;
    private Integer curr;
    private Integer total;
    private Boolean isClosed = false;
    private String content;
    private Date meetingDay;
    //private Region region;
    //private Category category;
    //private List<Comment> comments;
    // TODO : 위 객체들이 구현되면 주석 풀고 게터세터 만들기


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getCurr() {
        return curr;
    }

    public void setCurr(Integer curr) {
        this.curr = curr;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getMeetingDay() {
        return meetingDay;
    }

    public void setMeetingDay(Date meetingDay) {
        this.meetingDay = meetingDay;
    }

}
