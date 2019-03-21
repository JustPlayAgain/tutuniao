package com.tutuniao.tutuniao.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RedisClusterUtil {
    private static String CLUSTER = "97.64.36.211:7011";
    public static class JedisClusterMultiton{

        private static Map<String, JedisCluster> jedisClusterMap = new ConcurrentHashMap<>();

        public static JedisCluster getInstance(String cluster){
            if(cluster == null ){
                cluster = CLUSTER;
            }
            if(jedisClusterMap.get(cluster) == null){
                synchronized(JedisClusterMultiton.class){
                    if(jedisClusterMap.get(cluster) == null) {

                        String[] servers = cluster.split(";");

                        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
                        poolConfig.setMaxTotal(500);
                        poolConfig.setMaxIdle(500);

                        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
                        for (String server : servers) {
                            String[] serverFields = server.split(":");
                            String host = serverFields[0];
                            int port = Integer.parseInt(serverFields[1]);
                            jedisClusterNodes.add(new HostAndPort(host, port));

                        }
                        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
                        jedisClusterMap.put(cluster, jedisCluster);
                    }
                }
            }
            return jedisClusterMap.get(cluster);
        }


    }

    public static class RedisClusterException extends Exception {
        public RedisClusterException(Exception e) {
            super(e);
        }
    }
    public static String hget(String cluster, String key,String field) throws RedisClusterException {
        if(key == null){
            return null;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            String value = jedisCluster.hget(key,field);
            return value;
        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }
    public static void hset(String cluster, String key,String field, String value) throws RedisClusterException {
        if(key == null){
            return ;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
             jedisCluster.hset(key,field,value);
        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }
    public static void expire(String cluster, String key,int secondes) throws RedisClusterException {
        if(key == null){
            return ;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            jedisCluster.expire(key,secondes);
        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }

    public static String get(String cluster, String key) throws RedisClusterException {
        if(key == null){
            return null;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            String value = jedisCluster.get(key);
            return value;
        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }

    public static String get(String key) {
        String value = null ;
        if(key != null){
            JedisCluster jedisCluster = JedisClusterMultiton.getInstance(null);
            try{
                if ( jedisCluster.exists(key)){
                    value = jedisCluster.get(key);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return value;
    }

    public static void set(String cluster, String key,String value) throws RedisClusterException {
        if(key == null){
            return ;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            jedisCluster.set(key, value);

        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }

    public static void set(String key,String value) {
        if(key == null){
            return ;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(null);
        try{
            jedisCluster.set(key, value);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Long lpush(String cluster, String key,String value) throws RedisClusterException {
        if(key == null){
            return 0L;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            return jedisCluster.lpush(key, value);
        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }
    public static void lset(String cluster, String key,int index,String value) throws RedisClusterException {
        if(key == null){
            return ;
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            jedisCluster.lset(key,index,value);

        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }

    public static List<String> getZRange(String cluster, String key) throws RedisClusterException {
        if(key == null){
            return new LinkedList<>();
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
            Set<String> set = jedisCluster.zrange(key, 0, -1);
            List<String> list = new LinkedList<String>(set);
            return list;
        }catch(Exception e){
            throw new RedisClusterException(e);
        }
    }

    public static List<Tuple> getZRangeWithScore(String cluster, String key) throws RedisClusterException {
        if(key == null){
            return new LinkedList<>();
        }
        JedisCluster jedisCluster = JedisClusterMultiton.getInstance(cluster);
        try{
//            Set<Tuple> set = jedisCluster.zrangeWithScores(key, 0, -1);
//            Set<Tuple> set = jedisCluster.zrangeByScoreWithScores(key,"-inf","+inf");
            Set<String> set = jedisCluster.zrange(key, 0, -1);
            LinkedList<Tuple> list = new LinkedList<>();
            for (String item : set) {
                list.add(new Tuple(item, 0.0));
            }
//            List<Tuple> list = new LinkedList<Tuple>(set);
            return list;
        }catch(Exception e){
            throw new RedisClusterException(e);
        }

    }
}
