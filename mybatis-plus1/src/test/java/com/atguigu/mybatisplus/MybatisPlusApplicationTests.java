package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.Mapper.UserMapper;
import com.atguigu.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {


    @Autowired
     private UserMapper userMapper;

    @Test
    public void findAll(){
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
         users.forEach(System.out::println);
    }




    @Test
    public void insert() {
        User  user = new User();

        user.setAge(23);
        user.setName("我想要成功2020");
        user.setEmail("55317332@qq.com");
        int i = userMapper.insert(user);

    }


    @Test
    public void update() {
        User  user = new User();
        user.setId(1l);
        user.setAge(200);
        int i = userMapper.updateById(user);
    }
    /**
     * 测试 乐观锁插件
     */
    @Test
    public void testOptimisticLocker() {
           //查询
        User user = userMapper.selectById(1l);

        //修改
        user.setName("heleeeee");
        user.setEmail("2222222222222222222");
        userMapper.updateById(user);

    }
    /**
     * 测试乐观锁插件 失败
     */
    @Test
    public void testOptimisticLockerFail() {

        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");

        //模拟取出数据后，数据库中version实际数据比取出的值大，即已被其它线程修改并更新了version
        user.setVersion(user.getVersion() - 1);

        //执行更新
        userMapper.updateById(user);
    }


    @Test
    public void testSelectById(){

        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds(){

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);

    }

    @Test
    public void testSelectByMap(){

        Map map = new HashMap();
        map.put("name","Jack");
        map.put("age","20");
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }
    @Test
    public void testDeleteById() {
        userMapper.deleteById(1261894880158932994l);

    }

    @Test
    public void testDeleteBatchIds() {

        userMapper.deleteBatchIds(Arrays.asList(3,4,5));
    }

    @Test
    public void testDeleteByMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);

        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {

        userMapper.deleteById(2l);

    }
    /**
     * 测试 逻辑删除后的查询：
     * 不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSelect() {
        User user = new User();
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    
}
