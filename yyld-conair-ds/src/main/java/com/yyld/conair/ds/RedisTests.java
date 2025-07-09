package com.yyld.conair.ds;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import java.util.HashSet;
import java.util.Set;

public class RedisTests {

    //"redis://10.58.240.51:26379","redis://10.58.240.52:26380","redis://10.58.240.53:26381"
    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.58.240.51:26379");
        sentinels.add("10.58.240.52:26380");
        sentinels.add("10.58.240.53:26381");
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(100);
        poolConfig.setMaxWaitMillis(10000);
        poolConfig.setTestOnBorrow(true);
        int connectionTimeout = 5000;
        int soTimeout = 5000;
        String password = "M3E5sW7vy&";
        int database = 0;
        try (JedisSentinelPool jspool = new JedisSentinelPool("mymaster", sentinels, poolConfig,
                connectionTimeout, soTimeout, password, database)) {
            Jedis jedis = jspool.getResource();
            jedis.mset("a", "AAA", "b", "BBB");
            System.out.println(jedis.get("a"));
            System.out.println(jedis.get("b"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
