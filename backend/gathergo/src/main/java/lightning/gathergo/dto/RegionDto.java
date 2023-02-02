package lightning.gathergo.dto;

public class RegionDto {

    public static class Response{
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CreateRequest{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ModifyRequest{
        private Integer Id;
        private String name;

        public Integer getId() {
            return Id;
        }

        public String getName() {
            return name;
        }

        public void setId(Integer id) {
            Id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
