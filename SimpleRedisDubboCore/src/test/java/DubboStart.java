import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * Dubbo服务启动 
 * @author guoyx 
 * @date 2015-9-22下午03:27:13
 */
public class DubboStart {
  
	public static void main(String[] args) throws Exception{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{ "applicationContext.xml"});
		context.start();  
		System.out.println("Please print any key ....");  
		System.in.read();  
	}
}
