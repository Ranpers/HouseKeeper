package pers.yiran.housekeeper.domain;

public class QueryForm {
    private String begin;
    private String end;
    private String parent;
    private String son;

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getParent() {
        return parent;
    }

    public String getSon() {
        return son;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setSon(String son) {
        this.son = son;
    }
    public QueryForm(){}
    public QueryForm(String begin,String end,String parent,String son){
        this.begin=begin;
        this.end=end;
        this.parent=parent;
        this.son=son;
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
