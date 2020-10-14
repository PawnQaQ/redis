package com.liu.cache;



import com.liu.Utils.applicationUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


public class RedisCache implements Cache {
    //当前放入缓存的mapper的namespace
    private final String id ;

    //必须存在构造方法
    public RedisCache(String id) {
        System.out.println("id:=====================> " + id); //com.liu.dao.UserMapper
        this.id = id;
    }

    //返回cache唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        //使用redis hash作为缓存的模型 放入数据到redis中
        getRedisTemplate().opsForHash().put(id.toString(),key.toString(),value);

        System.out.println("==========");
        System.out.println(key.toString());
        //这个输出的key就是下面这个
        //-2050927523:2343771495:com.liu.dao.UserMapper.findAll:0:2147483647:select id,name,age,bir from t_user ;:SqlSessionFactoryBean
    }

    //从redis中获取数据
    @Override
    public Object getObject(Object key) {
        return getRedisTemplate().opsForHash().get(id.toString(),key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {
        System.out.println("只会执行这个....");
        //清空namespace
        getRedisTemplate().delete(id.toString());

    }

    @Override
    public int getSize() {
        return 0;
    }

    //封装redisTemplate
    private RedisTemplate getRedisTemplate(){
        //通过application工具类获取redisTemplate
        RedisTemplate redisTemplate = (RedisTemplate) applicationUtils.getBean("redisTemplate");//规则就是小写
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
