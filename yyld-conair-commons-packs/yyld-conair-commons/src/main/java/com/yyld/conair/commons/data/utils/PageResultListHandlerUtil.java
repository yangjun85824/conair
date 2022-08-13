package com.yyld.conair.commons.data.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PageResultListHandlerUtil
 * @Description 根据参数获取返回列表对应条目数
 * @Author yangj
 * @Date 2022/5/24 10:02
 * @Vresion 1.0
 **/
public final class PageResultListHandlerUtil {

    public static <T> List<T> pageList(List<T> list, String startRowType, int startRow, int amount) {

        if (list == null) {

            return null;

        }

        List<T> resultList = new ArrayList<>();

        if (list.size() <= amount || amount == 0 || amount < 0) {

            return list;
        }

        int size = list.size();

        if (startRow > size) {

            return resultList;

        }

        if (startRowType == null || "".equals(startRowType)) {

            startRowType = "0";

        }

        if (startRow < 0) {

            startRow = 0;
        }

        if ("0".equals(startRowType)) {

            int count = ((startRow + amount) > size) ? size : (startRow + amount);

            for (int i = startRow; i < count; i++) {

                resultList.add(list.get(i));
            }

            return resultList;

        }

        if ("1".equals(startRowType)) {

            int count = 0;

            startRow = startRow == 0 ? size - 1 : startRow - 1;

            res:
            for (int i = startRow; i >= 0; i--) {

                if (count == amount) {
                    break res;
                }
                resultList.add(list.get(i));
                count++;
            }

            List<T> result = new ArrayList<>();

            for (int i = resultList.size() - 1; i >= 0; i--) {

                result.add(resultList.get(i));
            }

            return result;
        }

        return resultList;

    }

}
