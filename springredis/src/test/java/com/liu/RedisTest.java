package com.liu;


import com.liu.test.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes =SpringredisApplication.class )
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//用于操作字符串
    @Test //测试字符串
    public void testString(){
        stringRedisTemplate.opsForValue().set("names","重庆");
        stringRedisTemplate.opsForValue().set("ages","22");
        stringRedisTemplate.opsForValue().set("dates","2022-2-2");
        stringRedisTemplate.opsForValue().set("works","搬砖");
        stringRedisTemplate.opsForValue().set("hobby","睡觉");
        //查看所有的key值
        Set<String> keys = stringRedisTemplate.keys("*");
        //查看key对应的类型
        DataType type = stringRedisTemplate.type("ages");
        System.out.println(type);
        System.out.println(keys);
        System.out.println("===============");

        //判断key是否存在
        Boolean aBoolean = stringRedisTemplate.hasKey("works");
        Boolean bBoolean = stringRedisTemplate.hasKey("age");
        System.out.println(aBoolean);
        System.out.println(bBoolean);
        System.out.println("==================");

        //删除指定的key
        stringRedisTemplate.delete("ages");
        //修改名字
        stringRedisTemplate.rename("names","areas");
        //设置key的超时时间
        //设置key超时时间 参数1:设置key名 参数2:时间 参数3:时间的单位
        stringRedisTemplate.expire("areas",10, TimeUnit.HOURS);
        //移动key
        stringRedisTemplate.move("hobby",1);
        //set 设置超时时间 用于短信验证  append 追加消息
    }


    @Test
    public void testList(){
        stringRedisTemplate.opsForList().leftPushAll("lists","嘻嘻","西西","洗洗");
        List<String> range = stringRedisTemplate.opsForList().range("lists", 0, -1);
        range.forEach(value-> System.out.println(value));
    }

    @Test
    public void testSet(){
        stringRedisTemplate.opsForSet().add("sets","小黑","小白","小绿");
        Set<String> sets = stringRedisTemplate.opsForSet().members("sets");
        sets.forEach(value-> System.out.println(value));
    }

    @Test
    public void testZSet(){
        stringRedisTemplate.opsForZSet().add("zsets","啊啊啊",1);
        stringRedisTemplate.opsForZSet().add("zsets","zzz",2);
        Set<String> zsets = stringRedisTemplate.opsForZSet().range("zsets", 0, -1);
        zsets.forEach(value-> System.out.println(value));
        System.out.println("=============");
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().rangeByScoreWithScores("zsets", 0, 3);

        tuples.forEach(value-> System.out.println(value.getScore()));
        tuples.forEach(value-> System.out.println(value.getValue()));

    }

    @Test
    public void testHash(){
        stringRedisTemplate.opsForHash().put("maps","color","green");
        stringRedisTemplate.opsForHash().put("maps","cloth","shoes");

        //放入多个值 首先要建立一个map
        Map<String,String> map=new HashMap<String, String>();
        map.put("age","23");
        map.put("date","2222-22-2");
        stringRedisTemplate.opsForHash().putAll("maps",map);

        Object o = stringRedisTemplate.opsForHash().get("maps", "cloth");
        System.out.println(o);
        System.out.println("=============");
        //获取所有的keys
        Set<Object> keys = stringRedisTemplate.opsForHash().keys("maps");
        //获取所有的values
        List<Object> values = stringRedisTemplate.opsForHash().values("maps");
        keys.forEach(k-> System.out.println(k));
        values.forEach(v-> System.out.println(v));
        System.out.println("======");
        //获取多个值
        List<Object> maps = stringRedisTemplate.opsForHash().multiGet("maps", Arrays.asList("age","date"));
        maps.forEach(v-> System.out.println(v));


    }


//============================== RedisTemplate 封装对象
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
   public void testObj(){
        //修改key的序列化方式 保持key值是String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
       redisTemplate.opsForValue().set("user1",new User("21","哈哈",24,new Date()));
       User user = (User)redisTemplate.opsForValue().get("user1");
        System.out.println(user);
    }
    @Test
    public void test01(){
        //
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        User user2 = new User("33", "hehe", 44, new Date());
        //修改hash里面的key的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForHash().put("demo","demooo",user2);
        User u = (User) redisTemplate.opsForHash().get("demo", "demooo");
        System.out.println(u);
    }

    @Test
    public void testBound(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("user1");
        boundValueOperations.append("还行AAA。。");
        User u1 =(User) boundValueOperations.get();
        System.out.println(u1);

    }

    @Test
    public void testBound1(){
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        BoundValueOperations<String, String> stringStringBoundValueOperations = stringRedisTemplate.boundValueOps("areas");
        stringStringBoundValueOperations.append("kkkk");
        String s = stringStringBoundValueOperations.get();
        System.out.println(s);
    }

}
