package pers.yiran.housekeeper.domain;

public class QueryForm {
    private String begin;
    private String end;
    private String parent;
    private String son;
    private int page;

    public QueryForm() {
    }

    public QueryForm(String begin, String end, String parent, String son, int page) {
        this.begin = begin;
        this.end = end;
        this.parent = parent;
        this.son = son;
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }

    @Override
    public String toString() {
        return "QueryForm{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", parent='" + parent + '\'' +
                ", son='" + son + '\'' +
                '}';
    }
}
