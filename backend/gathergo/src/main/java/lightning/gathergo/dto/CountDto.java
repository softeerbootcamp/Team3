package lightning.gathergo.dto;

public class CountDto {
    public static class Request{
        private String articleId;
        private Integer count;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
