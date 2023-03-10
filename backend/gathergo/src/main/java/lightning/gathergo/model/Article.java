package lightning.gathergo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table("article")
public class Article {

    // id, hostId, title, thumbnail, curr,
    // total, isClosed, content, meetingDay, location,
    // regionId, categoryId, uuid
    // orm : 1대 다 관계에서 1쪽이 다 엔티티를 컬렉션으로 가질 수 있도록 구현

    // TODO : 주석처리된 객체들이 구현되면 주석 풀고 게터세터 만들기
    @Id
    private Integer id;
    @Column("hostid")
    private Integer hostId;
    private String title;
    private int total;
    @Column("isclosed")
    private Boolean isClosed = false;
    private String content;
    @Column("meetingday")
    private Timestamp meetingDay;
    private String location;
    @Column("regionid")
    private int regionId;
    @Column("categoryid")
    private int categoryId;
    //private List<Comment> comments;
    private String uuid;

    public Article(){
        isClosed = false;
    }

    public Article(Integer hostId, String title, int total, Boolean isClosed, String content, Timestamp meetingDay, String location, int regionId, int categoryId, String uuid) {
        this.hostId = hostId;
        this.title = title;
        this.total = total;
        this.isClosed = isClosed;
        this.content = content;
        this.meetingDay = meetingDay;
        this.location = location;
        this.regionId = regionId;
        this.categoryId = categoryId;
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getMeetingDay() {
        return meetingDay;
    }

    public void setMeetingDay(Timestamp meetingDay) {
        this.meetingDay = meetingDay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", title='" + title + '\'' +
                ", total=" + total +
                ", isClosed=" + isClosed +
                ", content='" + content + '\'' +
                ", meetingDay=" + meetingDay +
                ", location='" + location + '\'' +
                ", regionId=" + regionId +
                ", categoryId=" + categoryId +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
