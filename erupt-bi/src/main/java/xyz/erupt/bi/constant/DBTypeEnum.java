//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.constant;

public enum DBTypeEnum {
    MySQL("select * from (@sql) t limit @size offset @skip"),
    PostgreSQL("select * from (@sql) t limit @size offset @skip"),
    Oracle("SELECT t.* FROM (  \nSELECT ROWNUM RN,temp.*                               \nFROM(@sql) temp                               \nWHERE ROWNUM < @skip + @size + 1) t   \nWHERE RN > @skip"),
    SQLServer2012("SELECT * from (@sql) t\nOFFSET @skip ROWS\nFETCH NEXT @size ROWS ONLY"),
    Other((String)null);

    public static final String OTHER = Other.name();
    public static final String $SQL = "@sql";
    public static final String $SIZE = "@size";
    public static final String $SKIP = "@skip";
    public static final String GENERAL_LIMIT = "select * from (@sql) t limit @size offset @skip";
    private static final String ORACLE_LIMIT = "SELECT t.* FROM (  \nSELECT ROWNUM RN,temp.*                               \nFROM(@sql) temp                               \nWHERE ROWNUM < @skip + @size + 1) t   \nWHERE RN > @skip";
    private static final String SQL_SERVER_2012_LIMIT = "SELECT * from (@sql) t\nOFFSET @skip ROWS\nFETCH NEXT @size ROWS ONLY";
    private final String limitSql;

    private DBTypeEnum(String limitSql) {
        this.limitSql = limitSql;
    }

    public String getLimitSql() {
        return this.limitSql;
    }
}
