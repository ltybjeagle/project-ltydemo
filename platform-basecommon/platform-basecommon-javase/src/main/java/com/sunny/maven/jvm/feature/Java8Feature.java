package com.sunny.maven.jvm.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: Java8特性
 * @create: 2021-03-08 12:05
 */
public class Java8Feature {
    private static List<Map<String, Object>> list = new ArrayList<>();
    static {
        list.add(new HashMap<>() {{
            put("colcode", "EXPECO");
            put("colcsid", "10609");
            put("colvalue", "30903");
            put("maintablecode", "PDM_T_0006");
        }});
        list.add(new HashMap<>() {{
            put("colcode", "EXPECO");
            put("colcsid", "10609");
            put("colvalue", "31005");
            put("maintablecode", "PDM_T_0008");
        }});
        list.add(new HashMap<>() {{
            put("colcode", "VCOL25");
            put("colcsid", "3200OGnt9qxv");
            put("colvalue", "02003");
            put("maintablecode", "PDM_T_0003");
        }});
        list.add(new HashMap<>() {{
            put("colcode", "VCOL25");
            put("colcsid", "3200OGnt9qxv");
            put("colvalue", "02001");
            put("maintablecode", "PDM_T_0002");
        }});
    }

    public static void main(String ...args) {
        Map<String, List<Map<String, Object>>> groupByColCode =
                list.stream().collect(Collectors.groupingBy(item -> String.valueOf(item.get("colcode"))));
        System.out.println(groupByColCode);
    }
}
