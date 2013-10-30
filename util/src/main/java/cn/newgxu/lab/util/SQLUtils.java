/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过方法调用来生成查询语句，这样比一味的使用封装要来得直接并且好用。
 * 可以直接将生成的查询语句(部分语句)返回给mybatis就好
 *
 * @User longkai
 * @Date 13-7-30
 * @Mail im.longkai@gmail.com
 */
public class SQLUtils {

    public static final String ID_DESC_ORDER_BY = "id DESC";

    public static final String columns(String... columns) {
        if (columns == null || columns.length == 0) {
            return "*";
        }

        StringBuilder seletion = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            seletion.append(columns[i]).append(",");
        }
        return seletion.substring(0, seletion.length() - 1);
    }

    public static final String where(String where, Object... args) {
        if (where == null || where.length() == 0) {
            return null;
        }
        if (args == null || args.length == 0) {
            return where;
        }

        String[] strings = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            strings[i] = injectArg(args[i]);
        }
        return TextUtils.replace(where, strings);
    }

    public static final String where(String where, Map<String, Object> map) {
        if (TextUtils.isEmpty(where)) {
            return null;
        }
        if (map == null || map.size() == 0) {
            return where;
        }

        Map<String, String> m = new HashMap<String, String>(map.size());
        for (String key : map.keySet()) {
            m.put(key, injectArg(map.get(key)));
        }
        return TextUtils.replace(where, m);
    }

    public static final String like(String like, String... args) {
        if (TextUtils.isEmpty(like)) {
            return null;
        }
        if (args == null || args.length == 0) {
            return like;
        }

        return TextUtils.replace(like, args);
    }

    public static final String like(String like, Map<String, String> args) {
        if (TextUtils.isEmpty(like)) {
            return null;
        }
        if (args == null || args.size() == 0) {
            return like;
        }
        return TextUtils.replace(like, args);
    }

    public static final String injectArg(Object arg) {
        if (arg == null) {
            return "null";
        }

        if (arg instanceof Number) {
            return arg.toString();
        }

        if (arg instanceof Boolean) {
            boolean b = (Boolean) arg;
            return b ? "1" : "0";
        }

        StringBuilder value = new StringBuilder("'");
        if (arg instanceof Date) {
//            DEFAULT yyyy-MM-dd hh:mm:ss
            value.append(DateTime.format((Date) arg));
        } else {
            value.append(arg.toString());
        }
        return value.append("'").toString();
    }

    public static final String[] queryWithCount(String table, String[] columns,
                                       String where, Object[] args, String groupBy,
                                       String having, String orderBy, String limit) {
        StringBuilder query = selectFromWhere(table, columns, where, args);
        String[] queries = new String[2];
        queries[1] = "SELECT COUNT(1) FROM" + query.toString().split("FROM")[1];

        queries[0] = groupByHavingOrderByLimit(query, groupBy, having, orderBy, limit).toString();
        return queries;
    }


    public static final String query(String table, String[] columns,
                    String where, Object[] args, String groupBy,
                    String having, String orderBy, String limit) {
        StringBuilder query = selectFromWhere(table, columns, where, args);
        return groupByHavingOrderByLimit(query, groupBy, having, orderBy, limit).toString();
    }

    public static final String set(String[] columns, Object[] values) {
        if (columns == null || values == null
                || columns.length == 0 || columns.length != values.length) {
            throw new IllegalArgumentException("UPDATE columns and values NOT MATCHED or NOT SET!");
        }

        StringBuilder set = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            set.append(columns[i]).append("=").append(injectArg(values[i])).append(",");
        }
        return set.substring(0, set.length() - 1);
    }

    public static final String update(String table, String[] columns, Object[] values, String where, Object[] args) {
        StringBuilder update = new StringBuilder("UPDATE ");
        update.append(table).append(" SET ").append(set(columns, values));

        where = where(where, args);
        if (where != null) {
            update.append(" WHERE ").append(where);
        }

        return update.toString();
    }

    public static final String delete(String table, String where, Object... atrs) {
        where = where(where, atrs);
        if (where == null) {
            throw new SecurityException("NOT ALLOWED to delete the whole table!");
        }
        StringBuilder delete = new StringBuilder("DELETE FROM ");
        delete.append(table).append(" WHERE ").append(where);
        return delete.toString();
    }

    @Deprecated
    public static final String toSelectCount(String query) {
        String[] strs = query.split("(?i)FROM")[1].split("(?i)WHERE");

        if (strs.length > 2) {
            throw new IllegalArgumentException("INVALID SQL! --> " + query);
        }

        StringBuilder selection = new StringBuilder("SELECT COUNT(1) FROM");

        selection.append(strs[0]);

//		has 'where' clause.
        if (strs.length == 2) {
            String where = strs[1].split("(?i)LIMIT")[0].split("(?i)ORDER BY")[0]
                    .split("(?i)HAVING")[0].split("(?i)GROUP BY")[0];
            selection.append("WHERE").append(where);
        }
        return selection.toString();
    }

    public static final String selectCount(String table, String where, Object[] args) {
        StringBuilder selectCount = new StringBuilder("SELECT COUNT(1) FROM ").append(table);
        where = where(where, args);
        if (where != null) {
            selectCount.append(" WHERE ").append(where);
        }
        return selectCount.toString();
    }

    private static StringBuilder selectFromWhere(String table, String[] columns, String where, Object[] args) {
        StringBuilder query = new StringBuilder("SELECT ");

        query.append(columns(columns)).append(" FROM ").append(table);

        where = where(where, args);
        if (where != null) {
            query.append(" WHERE ").append(where);
        }
        return query;
    }

    private static StringBuilder groupByHavingOrderByLimit(StringBuilder query,
            String groupBy, String having, String orderBy, String limit) {
        if (groupBy != null) {
            query.append(" GROUP BY ").append(groupBy);
        }

        if (having != null) {
            query.append(" HAVING ").append(having);
        }

        if (orderBy != null) {
            query.append(" ORDER BY ").append(orderBy);
        }

        if (limit != null) {
            query.append(" LIMIT ").append(limit);
        }
        return query;
    }

    public static final boolean isWildCard(String string) {
        return string == null ? false : string.equals("*");
    }
}
