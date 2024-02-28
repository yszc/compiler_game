package com.laotie.sqlparser;

import java.util.Set;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class Lineage {
    public static void main(String[] args) {
        // TODO: support: insert/insert overwrite/upsert/replace/update/merge/forien key
        // String sqlStr = "-- insert into c1\r\n" + //
        //                 "select * from(\r\n" + //
        //                 "\tselect * from db1.a1 \r\n" + //
        //                 "\tunion \r\n" + //
        //                 "\tselect * from db1.a2\r\n" + //
        //                 "\tunion \r\n" + //
        //                 "\tselect * from db1.a3\r\n" + //
        //                 ") aa left join db2.b1 as bb1 on a1.id = bb1.aid\r\n" + //
        //                 "-- left join db2.b2 bb2 on a1.id = bb2.aid\r\n" + //
        //                 "where bi.aid is not null;\r\n";
        String sqlStr = "SELECT Column1, Column2, Column3  INTO NewTable FROM OldTable WHERE condition;";

        Statement statHandle;
        Select select;
        Insert insert;
        try {
            statHandle = (Statement) CCJSqlParserUtil.parse(sqlStr);
            if (statHandle instanceof Insert){
                insert = (Insert) statHandle;
                parseLineage(insert);
            }
            if (statHandle instanceof Select){
                select = (Select) statHandle;
                System.out.println("well done.");
            }
            System.out.println("well done.");
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    private static void parseLineage(Insert insert){
        System.out.println("Target:"+insert.getTable());
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        Set<String> tableList = tablesNamesFinder.getTables((Statement)insert.getSelect());
        System.out.println("Source:");
        for (String table : tableList) {
            System.out.println(table);
        }

    }

}