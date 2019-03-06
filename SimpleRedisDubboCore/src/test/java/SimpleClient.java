

import java.util.ResourceBundle;

import org.junit.Test;

import com.study.demo.RedisClient;
import com.study.demo.bean.City;

/**
 * 
*
* @Description: 测试独立redis 客户端
* @ClassName: SimpleClient 
* @author zhufj
* @date 2019年3月6日 上午10:06:06 
*
 */
public class SimpleClient {

    @Test
    public void userCache(){
        //向缓存中保存对象 
        City city = new City();
        city.setName("杭州2");
        city.setAddress("浙江");
        //调用方法处理
        boolean reusltCache = RedisClient.set("city1", city);
        if (reusltCache) {
            System.out.println("向缓存中保存对象成功。");
        }else{
            System.out.println("向缓存中保存对象失败。");
        }
    }


    @Test
    public void getUserInfo(){

        City city = RedisClient.get("city1", City.class);
        if (city != null) {
            System.out.println("从缓存中获取的对象，" + city.toString());
        }

    }



}
