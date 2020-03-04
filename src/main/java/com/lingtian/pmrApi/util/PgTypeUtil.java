package com.lingtian.pmrApi.util;

public class PgTypeUtil {
    public static String kafka_type = "kafka";
    public static String infludb_type = "infludb";
    public static String mysql_type = "mysql";
    public static String oracle_type = "oracle";
    public static String flink_type = "flink";
    public static String spark_type = "spark";
    public static String redis_type = "redis";
    public static String emr_type = "emr";
    public static String rediscluster_type = "rediscluster";


    /**
     * 获取应用市场类型
     *
     * @return
     */
    public static String getMarketType(String marketName) {
        if (marketName.contains(kafka_type)) {
            return kafka_type;
        } else if (marketName.contains(infludb_type)) {
            return infludb_type;
        } else if (marketName.contains(mysql_type)) {
            return mysql_type;
        } else if (marketName.contains(oracle_type)) {
            return oracle_type;
        } else if (marketName.contains(flink_type)) {
            return flink_type;
        } else if (marketName.contains(spark_type)) {
            return spark_type;
        } else if (marketName.contains(redis_type)) {
            return redis_type;
        } else if (marketName.contains(rediscluster_type)) {
            return rediscluster_type;
        } else if (marketName.contains(emr_type)) {
            return emr_type;
        } else {
            return null;
        }

    }
}
