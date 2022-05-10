package pers.yiran.housekeeper.services;

import pers.yiran.housekeeper.dao.SortDao;
import pers.yiran.housekeeper.domain.Sort;

import java.util.List;

public class SortService {
    private SortDao sortDao = new SortDao();
    public List<Object> querySortNameAll(){
        return sortDao.querySortNameAll();
    }
    public List<Object> querySortNameByParent(String parent){
        return sortDao.querySortNameByParent(parent);
    }
    public int addSort(Sort sort){
        return sortDao.addSort(sort);
    }
    public int editSort(Sort sort){
        return sortDao.editSort(sort);
    }
    public int deleteSort(Sort sort){
        return new SortDao().deleteSort(sort);
    }
    public List<Sort> querySortAll(){
        return sortDao.querySortAll();
    }
}
