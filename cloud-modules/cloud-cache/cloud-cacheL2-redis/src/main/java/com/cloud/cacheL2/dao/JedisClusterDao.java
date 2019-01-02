/**
 * <p>文件名称: JedisClusterDao.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1.0</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.cacheL2.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.cacheL2.exception.RedisDBException;
import com.cloud.common.constant.Constants;
import com.cloud.common.utils.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * 通过此类操作集群redis
 * 
 * @author yyh
 * @version   3.1.0 2018年7月17日
 * @see       
 * @since  3.1.0
 */
public class JedisClusterDao implements JedisCommands
{
    @Autowired
    private JedisCluster jedisCluster;
    
    private static final Logger log = LoggerFactory.getLogger(JedisClusterDao.class);

    /**
     * 获取单个值
     * 
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public String get(String key) throws RedisDBException {
        String result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }

        try {
            result = jedisCluster.get(key);

        } catch (Exception e) {
            log.error("[Jedis] get error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    
    /**
     * 获取单个值
     * 
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public byte[] get(byte[] key) throws RedisDBException {
        byte[] result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }

        try {
            result = jedisCluster.get(key);
        } catch (Exception e) {
            log.error("[Jedis] get error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /*@Override
    public Set<String> keys(String pattern) throws RedisDBException{  
        TreeSet<String> keys = new TreeSet<>();  
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();  
        for(String k : clusterNodes.keySet()){  
            JedisPool jp = clusterNodes.get(k);  
            Jedis connection = jp.getResource();  
            try {  
                keys.addAll(connection.keys(pattern));  
            } catch(Exception e){  
                log.error("[Jedis] keys error [pattern:"+pattern+"]:"+e.getMessage(), e);
            } finally{  
                connection.close();
            }  
        }  
        return keys;  
    }*/
    
    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return 成功则返回OK
     * @throws RedisDBException 
     */
    @Override
    public String set(String key, String value) throws RedisDBException 
    {
        String result = null;

        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.set(key, value);
        } catch (Exception e) {
            log.error("[Jedis] set error [key:"+ key +" value:"+ value +"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return
     * @throws RedisDBException 
     */
    @Override
    public String set(byte[] key, byte[] value) throws RedisDBException 
    {
        String result = null;

        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.set(key, value);
        } catch (Exception e) {
            log.error("[Jedis] set error [key:"+ key +" value:"+ value +"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 设置数据失效(时间段，seconds秒之后失效)
     * 
     * @param key
     * @param seconds
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    @Override
    public Long expire(String key, int seconds) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.expire(key, seconds);

        } catch (Exception e) {
            log.error("[Jedis] expire error [key:" + key + " seconds:" + seconds + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 设置数据失效(时间段，seconds秒之后失效)
     * 
     * @param key
     * @param seconds
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    @Override
    public Long expire(byte[] key, int seconds) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.expire(key, seconds);
        } catch (Exception e) {
            log.error("[Jedis] expire error [key:" + key + " seconds:" + seconds + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }

    /**
     * 设置数据失效(时间点，unixTime之后失效)
     * 
     * @param key
     * @param unixTime UNIX时间戳,自 1970 年 1 月 1 日(00:00:00 GMT)以来的秒数
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    @Override
    public Long expireAt(String key, long unixTime) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.expireAt(key, unixTime);
        } catch (Exception e) {
            log.error("[Jedis] expireAt error [key:" + key + " unixTime:" + unixTime + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 设置数据失效(时间点，unixTime之后失效)
     * 
     * @param key
     * @param unixTime UNIX时间戳,自 1970 年 1 月 1 日(00:00:00 GMT)以来的秒数
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    @Override
    public Long expireAt(byte[] key, long unixTime) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.expireAt(key, unixTime);

        } catch (Exception e) {
            log.error("[Jedis] expireAt error [key:" + key + " unixTime:" + unixTime + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     * 
     * @param key
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key 的剩余生存时间。
     * @throws RedisDBException 
     */
    @Override
    public Long getTimeToLive(String key) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.ttl(key);

        } catch (Exception e) {
            log.error("[Jedis] expireAt error [key:" + key + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     * 
     * @param key
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key 的剩余生存时间。
     * @throws RedisDBException 
     */
    @Override
    public Long getTimeToLive(byte[] key) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.ttl(key);

        } catch (Exception e) {
            log.error("[Jedis] expireAt error [key:" + key + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 移除给定 key 的生存时间
     * 
     * @param key
     * @return 当生存时间移除成功时，返回 1 。如果 key 不存在或 key 没有设置生存时间，返回 0 。
     * @throws RedisDBException 
     */
    @Override
    public Long persist(byte[] key) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.persist(key);

        } catch (Exception e) {
            log.error("[Jedis] expireAt error [key:" + key + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 移除给定 key 的生存时间
     * 
     * @param key
     * @return 当生存时间移除成功时，返回 1 。如果 key 不存在或 key 没有设置生存时间，返回 0 。
     * @throws RedisDBException 
     */
    @Override
    public Long persist(String key) throws RedisDBException {
        Long result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.persist(key);

        } catch (Exception e) {
            log.error("[Jedis] expireAt error [key:" + key + "]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 检查给定 key 是否存在
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Boolean exists(String key) throws RedisDBException {
        Boolean result = false;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.exists(key);
        } catch (Exception e) {
            log.error("[Jedis] exists error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 检查给定 key 是否存在
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Boolean exists(byte[] key) throws RedisDBException {
        Boolean result = false;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.exists(key);
        } catch (Exception e) {
            log.error("[Jedis] exists error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @return 执行 incr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    @Override
    public Long incr(String key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.incr(key);
        } catch (Exception e) {
            log.error("[Jedis] incr error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @return 执行 incr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    @Override
    public Long incr(byte[] key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.incr(key);
        } catch (Exception e) {
            log.error("[Jedis] incr error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 将 key 中储存的数字值减一
     * @param key
     * @return 执行 decr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    @Override
    public Long decr(String key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.decr(key);
        } catch (Exception e) {
            log.error("[Jedis] decr error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 将 key 中储存的数字值减一
     * @param key
     * @return 执行 decr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    @Override
    public Long decr(byte[] key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.decr(key);
        } catch (Exception e) {
            log.error("[Jedis] decr error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        } finally {
            try
            {
                jedisCluster.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 删除给定 key
     * @param key
     * @return 被删除 key 的数量。
     * @throws RedisDBException 
     */
    @Override
    public Long del(String key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.del(key);
        } catch (Exception e) {
            log.error("[Jedis] del error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除给定 key
     * @param key
     * @return 被删除 key 的数量。
     * @throws RedisDBException 
     */
    @Override
    public Long del(byte[] key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.del(key);
        } catch (Exception e) {
            log.error("[Jedis] del error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 取出给定列表
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public List<String> getList(String key) throws RedisDBException {
        List<String> result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lrange(key, 0, -1);
        } catch (Exception e) {
            log.error("[Jedis] getList(lrange) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 取出给定列表
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public List<byte[]> getList(byte[] key) throws RedisDBException {
        List<byte[]> result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lrange(key, 0, -1);
        } catch (Exception e) {
            log.error("[Jedis] getList(lrange) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    @Override
    public Long addList(String key, String... value) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lpush(key, value);
        } catch (Exception e) {
            log.error("[Jedis] addList(lpush) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    @Override
    public Long addList(byte[] key, byte[]... value) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lpush(key, value);
        } catch (Exception e) {
            log.error("[Jedis] addList(lpush) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @param valueList
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    @Override
    public Long addList(String key, List<String> valueList) throws RedisDBException {
        Long result = 0L;
        for(String s : valueList){
            result = this.addList(key, s);
        }
        return result;
    }
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @param valueList
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    @Override
    public Long addList(byte[] key, List<byte[]> valueList) throws RedisDBException {
        Long result = 0l;
        for(byte[] s : valueList){
            result = this.addList(key, s);
        }
        return result;
    }
    
    /**
     * 获取列表长度
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    @Override
    public Long countList(String key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.llen(key);
        } catch (Exception e) {
            log.error("[Jedis] countList(llen) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取列表长度
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    @Override
    public Long countList(byte[] key) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.llen(key);
        } catch (Exception e) {
            log.error("[Jedis] countList(llen) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /** 
     * 返回指定List的指定部分
     * @param key  
     * @param start 起始位置    0:左边第一个元素   -1:右边的第一个元素
     * @param end 结束位置  0:左边第一个元素   -1:右边的第一个元素
     * @return 
     * @throws RedisDBException 
     */  
    @Override
    public List<String> getList(String key, long start, long end) throws RedisDBException {
        List<String> result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lrange(key, start, end);
        } catch (Exception e) {
            log.error("[Jedis] getList(lrange) error [key:"+key+" start:"+start+" end:"+end+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /** 
     * 返回指定List的指定部分
     * @param key  
     * @param start 起始位置    0:左边第一个元素   -1:右边的第一个元素
     * @param end 结束位置  0:左边第一个元素   -1:右边的第一个元素
     * @return 
     * @throws RedisDBException 
     */  
    @Override
    public List<byte[]> getList(byte[] key, long start, long end) throws RedisDBException {
        List<byte[]> result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lrange(key, start, end);
        } catch (Exception e) {
            log.error("[Jedis] getList(lrange) error [key:"+key+" start:"+start+" end:"+end+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素
     * @param key
     * @param count:
     *        count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     *        count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     *        count = 0 : 移除表中所有与 VALUE 相等的值。
     * @param value
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Long removeListValue(String key,long count,String value) throws RedisDBException {
        Long result = 0L;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lrem(key,count, value);
        } catch (Exception e) {
            log.error("[Jedis] removeListValue(lrem) error [key:"+key+" count:"+count+" value:"+value+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素
     * @param key
     * @param count:
     *        count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     *        count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     *        count = 0 : 移除表中所有与 VALUE 相等的值。
     * @param value
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Long removeListValue(byte[] key,long count,byte[] value) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.lrem(key,count, value);
        } catch (Exception e) {
            log.error("[Jedis] removeListValue(lrem) error [key:"+key+" count:"+count+" value:"+value+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 将字段加入指定对象中，会覆盖已存在字段，如果对象不存在则创建对象
     * @param key
     * @param map
     * @return
     * @throws RedisDBException 
     */
    @Override
    public String setMap(String key, Map<String, String> map) throws RedisDBException {
        String result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.hmset(key, map);
        } catch (Exception e) {
            log.error("[Jedis] setMap(hmset) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 将字段加入指定对象中，会覆盖已存在字段，如果对象不存在则创建对象
     * @param key
     * @param map
     * @return
     * @throws RedisDBException 
     */
    @Override
    public String setMap(byte[] key, Map<byte[], byte[]> map) throws RedisDBException {
        String result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.hmset(key, map);
        } catch (Exception e) {
            log.error("[Jedis] setMap(hmset) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取对象
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Map<String, String> getMap(String key) throws RedisDBException {
        Map<String, String> result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.hgetAll(key);
        } catch (Exception e) {
            log.error("[Jedis] getMap(hgetAll) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取对象
     * @param key
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Map<byte[], byte[]> getMap(byte[] key) throws RedisDBException {
        Map<byte[], byte[]> result = null;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.hgetAll(key);
        } catch (Exception e) {
            log.error("[Jedis] getMap(hgetAll) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取对象
     * @param key
     * @param field
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Long delMapField(String key, String... field) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.hdel(key, field);
        } catch (Exception e) {
            log.error("[Jedis] delMapField(hdel) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }
    
    /**
     * 获取对象
     * @param key
     * @param field
     * @return
     * @throws RedisDBException 
     */
    @Override
    public Long delMapField(byte[] key, byte[]... field) throws RedisDBException {
        Long result = 0l;
        if (jedisCluster == null) {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try {
            result = jedisCluster.hdel(key, field);
        } catch (Exception e) {
            log.error("[Jedis] delMapField(hdel) error [key:"+key+"]:"+e.getMessage(), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean setNX(String key, String value) throws RedisDBException
    {
        if (jedisCluster == null) 
        {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try 
        {
            Long result = jedisCluster.setnx(key, value);
            if(result > 0)
            {
                return true;
            }
        } catch (Exception e) 
        {
            log.error("setNX redis error, key : {}", key, e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
        return false;
    }

    @Override
    public String getSet(String key, String value) throws RedisDBException
    {
        if (jedisCluster == null) 
        {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try 
        {
            return jedisCluster.getSet(key, value);
        } catch (Exception e) 
        {
            log.error("getSet redis error, key : {}", key, e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
    }


    /**
     * 通过 scan 模式匹配
     *
     * @param pattern
     * @return
     * @throws RedisDBException
     */
    @Override
    public Set<String> scan(String pattern) throws RedisDBException
    {
        TreeSet<String> results = new TreeSet<>();
        if (jedisCluster == null)
        {
            throw new RedisDBException(
                    Constants.REDIS_CONNECTION_ERROR_CODE,
                    Constants.REDIS_CONNECTION_ERROR_MSG);
        }

        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String k : clusterNodes.keySet())
        {
            JedisPool jp = clusterNodes.get(k);
            Jedis jedis = jp.getResource();

            try
            {
                // 通过游标遍历
                ScanParams scanParams = new ScanParams();
                scanParams.match(pattern);
                scanParams.count(Integer.MAX_VALUE);

                String cursor = "0";
                do
                {
                    ScanResult<String> scanResult = jedis.scan(cursor,
                            scanParams);
                    results.addAll(scanResult.getResult());
                    cursor = scanResult.getStringCursor();
                } while (!"0".equalsIgnoreCase(cursor));

            } catch (Exception e)
            {
                log.error("[Jedis] keys error [pattern:" + pattern + "]:"
                        + e.getMessage(), e);
            } finally
            {
                jedis.close();
            }
        }
        return results;
    }


    @Override
    public String set(String key, String value, String nxxx, String expx,
            long time) throws RedisDBException
    {

        if (jedisCluster == null) 
        {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try 
        {
            String result = jedisCluster.set(key, value, nxxx, expx, time);
            return result;
        } catch (Exception e) 
        {
            log.error("set redis error, key : {}", key, e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
    }


    @Override
    public Object eval(String script, List<String> keys, List<String> args) throws RedisDBException
    {
        if (jedisCluster == null) 
        {
            throw new RedisDBException(Constants.REDIS_CONNECTION_ERROR_CODE, Constants.REDIS_CONNECTION_ERROR_MSG);
        }
        try 
        {
            Object result = jedisCluster.eval(script, keys, args);
            return result;
        } catch (Exception e) 
        {
            log.error("eval redis error, key : {}", JsonUtil.beanToJson(keys), e);
            throw new RedisDBException(Constants.REDIS_EXCUTE_ERROR_CODE, Constants.REDIS_EXCUTE_ERROR_MSG + ": " + e.getMessage());
        }
    }

}
