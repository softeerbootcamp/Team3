package lightning.gathergo.model;


import org.springframework.data.annotation.Id;

public class Region {
    @Id
    private Integer id;
    private String name;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region(){
        this.id=null;
        this.name=null;
    }

    public Region(String name) {
        this.name = name;
    }

    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
