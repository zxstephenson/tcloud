/**
 * <p>文件名称: JedisCommands.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1.0</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.cacheL2.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloud.cacheL2.exception.RedisDBException;

/**
 * redis命令接口
 * 
 * @author yyh
 * @version   3.1 2018年3月7日
 * @see       
 * @since  3.1.0
 */
public interface JedisCommands
{
    /**
     * 获取值
     * 
     * @param key 
     * @return String类型返回
     * @throws RedisDBException
     */
    String get(String key) throws RedisDBException;
    
    
    /**
     * 获取单个值
     * 
     * @param key
     * @return
     * @throws RedisDBException 
     */
    byte[] get(byte[] key) throws RedisDBException;
    
    /**
     * 获取匹配的所有键
     * 
     * @param pattern 
     * @return
     * @throws RedisDBException
     */
    /*Set<String> keys(String pattern) throws RedisDBException;*/
    
    /**
     * 通过 scan 模式匹配
     *
     * @param pattern
     * @return
     * @throws RedisDBException
     */
    Set<String> scan(String pattern) throws RedisDBException;
    
    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return 成功则返回OK
     * @throws RedisDBException 
     */
    String set(String key, String value) throws RedisDBException;
    
    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return
     * @throws RedisDBException 
     */
    String set(byte[] key, byte[] value) throws RedisDBException;
    
    String set(final String key, final String value, final String nxxx, 
            final String expx, final long time) throws RedisDBException;
    /**
     * 设置数据失效(时间段，seconds秒之后失效)
     * 
     * @param key
     * @param seconds
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    Long expire(String key, int seconds) throws RedisDBException;
    
    /**
     * 设置数据失效(时间段，seconds秒之后失效)
     * 
     * @param key
     * @param seconds
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    Long expire(byte[] key, int seconds) throws RedisDBException;

    /**
     * 设置数据失效(时间点，unixTime之后失效)
     * 
     * @param key
     * @param unixTime UNIX时间戳,自 1970 年 1 月 1 日(00:00:00 GMT)以来的秒数
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    Long expireAt(String key, long unixTime) throws RedisDBException;
    
    /**
     * 设置数据失效(时间点，unixTime之后失效)
     * 
     * @param key
     * @param unixTime UNIX时间戳,自 1970 年 1 月 1 日(00:00:00 GMT)以来的秒数
     * @return 设置成功返回 1 。当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
     * @throws RedisDBException 
     */
    Long expireAt(byte[] key, long unixTime) throws RedisDBException;
    
    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     * 
     * @param key
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key 的剩余生存时间。
     * @throws RedisDBException 
     */
    Long getTimeToLive(String key) throws RedisDBException;
    
    /**
     * 以秒为单位，返回给定 key 的剩余生存时间
     * 
     * @param key
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key 的剩余生存时间。
     * @throws RedisDBException 
     */
    Long getTimeToLive(byte[] key) throws RedisDBException;
    
    /**
     * 移除给定 key 的生存时间
     * 
     * @param key
     * @return 当生存时间移除成功时，返回 1 。如果 key 不存在或 key 没有设置生存时间，返回 0 。
     * @throws RedisDBException 
     */
    Long persist(byte[] key) throws RedisDBException;
    
    /**
     * 移除给定 key 的生存时间
     * 
     * @param key
     * @return 当生存时间移除成功时，返回 1 。如果 key 不存在或 key 没有设置生存时间，返回 0 。
     * @throws RedisDBException 
     */
    Long persist(String key) throws RedisDBException;
    
    /**
     * 检查给定 key 是否存在
     * @param key
     * @return
     * @throws RedisDBException 
     */
    Boolean exists(String key) throws RedisDBException;
    
    /**
     * 检查给定 key 是否存在
     * @param key
     * @return
     * @throws RedisDBException 
     */
    Boolean exists(byte[] key) throws RedisDBException;
    
    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @return 执行 incr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    Long incr(String key) throws RedisDBException;
    
    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @return 执行 incr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    Long incr(byte[] key) throws RedisDBException;
    
    /**
     * 将 key 中储存的数字值减一
     * @param key
     * @return 执行 decr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    Long decr(String key) throws RedisDBException;
    
    /**
     * 将 key 中储存的数字值减一
     * @param key
     * @return 执行 decr 命令之后 key 的值。
     * @throws RedisDBException 
     */
    Long decr(byte[] key) throws RedisDBException;
    
    /**
     * 删除给定 key
     * @param key
     * @return 被删除 key 的数量。
     * @throws RedisDBException 
     */
    Long del(String key) throws RedisDBException;
    
    /**
     * 删除给定 key
     * @param key
     * @return 被删除 key 的数量。
     * @throws RedisDBException 
     */
    Long del(byte[] key) throws RedisDBException;
    
    /**
     * 取出给定列表
     * @param key
     * @return
     * @throws RedisDBException 
     */
    List<String> getList(String key) throws RedisDBException;
    
    /**
     * 取出给定列表
     * @param key
     * @return
     * @throws RedisDBException 
     */
    List<byte[]> getList(byte[] key) throws RedisDBException;
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    Long addList(String key, String... value) throws RedisDBException;
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    Long addList(byte[] key, byte[]... value) throws RedisDBException;
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @param valueList
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    Long addList(String key, List<String> valueList) throws RedisDBException;
    
    /**
     * 往指定列表中添加数据
     * @param key
     * @param valueList
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    Long addList(byte[] key, List<byte[]> valueList) throws RedisDBException;
    
    /**
     * 获取列表长度
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    Long countList(String key) throws RedisDBException;
    
    /**
     * 获取列表长度
     * @param key
     * @return 执行命令后，列表的长度
     * @throws RedisDBException 
     */
    Long countList(byte[] key) throws RedisDBException;
    
    /** 
     * 返回指定List的指定部分
     * @param key  
     * @param start 起始位置    0:左边第一个元素   -1:右边的第一个元素
     * @param end 结束位置  0:左边第一个元素   -1:右边的第一个元素
     * @return 
     * @throws RedisDBException 
     */  
    List<String> getList(String key, long start, long end) throws RedisDBException;
    
    /** 
     * 返回指定List的指定部分
     * @param key  
     * @param start 起始位置    0:左边第一个元素   -1:右边的第一个元素
     * @param end 结束位置  0:左边第一个元素   -1:右边的第一个元素
     * @return 
     * @throws RedisDBException 
     */  
    List<byte[]> getList(byte[] key, long start, long end) throws RedisDBException;
    
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
    Long removeListValue(String key,long count,String value) throws RedisDBException;
    
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
    Long removeListValue(byte[] key,long count,byte[] value) throws RedisDBException;
    
    /**
     * 将字段加入指定对象中，会覆盖已存在字段，如果对象不存在则创建对象
     * @param key
     * @param map
     * @return
     * @throws RedisDBException 
     */
    String setMap(String key, Map<String, String> map) throws RedisDBException;
    
    /**
     * 将字段加入指定对象中，会覆盖已存在字段，如果对象不存在则创建对象
     * @param key
     * @param map
     * @return
     * @throws RedisDBException 
     */
    String setMap(byte[] key, Map<byte[], byte[]> map) throws RedisDBException;
    
    /**
     * 获取对象
     * @param key
     * @return
     * @throws RedisDBException 
     */
    Map<String, String> getMap(String key) throws RedisDBException;
    
    /**
     * 获取对象
     * @param key
     * @return
     * @throws RedisDBException 
     */
    Map<byte[], byte[]> getMap(byte[] key) throws RedisDBException;
    
    /**
     * 获取对象
     * @param key
     * @param field
     * @return
     * @throws RedisDBException 
     */
    Long delMapField(String key, String... field) throws RedisDBException;
    
    /**
     * 获取对象
     * @param key
     * @param field
     * @return
     * @throws RedisDBException 
     */
    Long delMapField(byte[] key, byte[]... field) throws RedisDBException;
    
    boolean setNX(final String key, final String value) throws RedisDBException;
    
    String getSet(final String key, final String value) throws RedisDBException;
    
    Object eval(String script, List<String> keys, List<String> args) throws RedisDBException;
}
