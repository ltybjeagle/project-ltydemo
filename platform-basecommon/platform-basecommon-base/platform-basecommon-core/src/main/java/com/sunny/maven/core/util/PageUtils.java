package com.sunny.maven.core.util;

import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 分页工具
 * @create: 2022-01-25 22:57
 */
public class PageUtils {
    /**
     * 当前页码
     */
    public static String PAGE_NUM = "pagenum";
    /**
     * 每页数据条数
     */
    public static String PAGE_SIZE = "pagesize";
    /**
     * 当前页数据条数
     */
    public static String SIZE = "size";
    /**
     * 由第几条开始
     */
    public static String START_ROW = "startrow";
    /**
     * 到第几条结束
     */
    public static String END_ROW = "endrow";
    /**
     * 总页数
     */
    public static String PAGES = "pages";
    /**
     * 上一页
     */
    public static String PRE_PAGE = "prepage";
    /**
     * 下一页
     */
    public static String NEXT_PAGE = "nextpage";
    /**
     * 是否为首页
     */
    public static String IS_FIRST_PAGE = "isfirstpage";
    /**
     * 是否为尾页
     */
    public static String IS_LAST_PAGE = "islastpage";
    /**
     * 是否有上一页
     */
    public static String HAS_PREVIOUS_PAGE = "haspreviouspage";
    /**
     * 是否有下一页
     */
    public static String HAS_NEXT_PAGE = "hasnextpage";
    /**
     * 导航页码数，默认值显示8个
     */
    public static String NAVIGATE_PAGES = "navigatepages";
    /**
     * 所有导航页号
     */
    public static String NAVIGATE_PAGE_NUMS = "navigatepagenums";
    /**
     * 导航条上的首页
     */
    public static String NAVIGATE_FIRST_PAGE = "navigatefirstpage";
    /**
     * 导航条上的尾页
     */
    public static String NAVIGATE_LAST_PAGE = "navigatelastpage";
    /**
     * 数据总数
     */
    public static String TOTAL = "total";
    /**
     * 数据
     */
    public static String DATA = "list";

    public static <T> Map<String, Object> lowerKeyPageInfo(final PageInfo<T> pageInfo) {
        Map<String, Object> r = new HashMap<>();
        r.put(PAGE_NUM, pageInfo.getPageNum());
        r.put(PAGE_SIZE, pageInfo.getPageSize());
        r.put(SIZE, pageInfo.getSize());
        r.put(START_ROW, pageInfo.getStartRow());
        r.put(END_ROW, pageInfo.getEndRow());
        r.put(PAGES, pageInfo.getPages());
        r.put(PRE_PAGE, pageInfo.getPrePage());
        r.put(NEXT_PAGE, pageInfo.getNextPage());
        r.put(IS_FIRST_PAGE, pageInfo.isIsFirstPage());
        r.put(IS_LAST_PAGE, pageInfo.isIsLastPage());
        r.put(HAS_PREVIOUS_PAGE, pageInfo.isHasPreviousPage());
        r.put(HAS_NEXT_PAGE, pageInfo.isHasNextPage());
        r.put(NAVIGATE_PAGES, pageInfo.getNavigatePages());
        r.put(NAVIGATE_PAGE_NUMS, pageInfo.getNavigatepageNums());
        r.put(NAVIGATE_FIRST_PAGE, pageInfo.getNavigateFirstPage());
        r.put(NAVIGATE_LAST_PAGE, pageInfo.getNavigateLastPage());
        r.put(TOTAL, pageInfo.getTotal());
        List<T> tList = pageInfo.getList();
        if (tList!=null && tList.size()>0) {
            r.put(DATA, tList);
        } else {
            r.put(DATA, new ArrayList<>());
        }
        return r;
    }
}
