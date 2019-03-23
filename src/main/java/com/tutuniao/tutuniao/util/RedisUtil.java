package com.tutuniao.tutuniao.util;

import com.tutuniao.tutuniao.common.Constant;
import redis.clients.jedis.Jedis;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisUtil {


    private static Map<String, Jedis> jedisMap = new ConcurrentHashMap<>();
    public static Jedis getInstance(String cluster){
        if(cluster == null ){
            cluster = Constant.CLUSTER;
        }
        if(jedisMap.get(cluster) == null){
            String[] serverFields = Constant.CLUSTER.split(":");
            String host = serverFields[0];
            int port = Integer.parseInt(serverFields[1]);
            jedisMap.put(cluster,new Jedis(host,port));
        }

        return jedisMap.get(cluster);
    }

    public static String get(String key) {
        String value = null ;
        if(key != null){
            Jedis jedis = RedisUtil.getInstance(null);
            try{
                if ( jedis.exists(key)){
                    value = jedis.get(key);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return value;
    }

    public static void set(String key,String value) {
        if(key == null){
            return ;
        }
        Jedis jedis = RedisUtil.getInstance(null);
        try{
            jedis.set(key, value);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
