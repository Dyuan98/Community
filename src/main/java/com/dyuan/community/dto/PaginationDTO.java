package com.dyuan.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *  封装页码信息
 * @author dyuan
 * @date 2020/1/31 16:33
 */

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;    // question列表对象，里面有所有数据库的问题属性，和user对象信息
    private boolean showPrevious; // 用于判断是否显示跳转前一页按钮
    private boolean showFirst;    // 展示第一页
    private boolean showNext;     // 展示下一页
    private boolean showEnd;      // 展示最后一页
    private Integer page; // 当前页面
    private List<Integer> pages = new ArrayList<>(); // 当前页面能直接点击的页面的列表
    private Integer totalPage;   // 总页数

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if (totalCount % size==0){
            totalPage = totalCount/size;
        } else{
            totalPage = totalCount/size+1;
        }
        if(page<1){
            page = 1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        this.page = page;
        // 判断此界面显示多少个页面标识
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {   // 添加当前页面前面几页，最多添加三页
                pages.add( 0,page - i);  // 往前查找，头部插入
            }
            if (page + i <= totalPage) {  // 添加当前页面后面几页，最多添加三页
                pages.add(page + i);
            }
        }
        // 判断是否展示上一页标识
        if(page == 1){showPrevious = false;}
        else{showPrevious = true;}
        // 判断是否展示下一页标识
        if (page == totalPage){showNext = false;}
        else {showNext = true;}

        // 判断是否展示第一页标识
        if(pages.contains(1)){
            showFirst = false;
        }
        else{showFirst = true;
        }
        // 判断是否展示最后一页标识
        if (pages.contains(totalPage)){
            showEnd = false;
        }else {
            showEnd = true;
        }


    }
}
