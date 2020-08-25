package email;

import org.apache.commons.mail.*;

import java.net.MalformedURLException;
import java.net.URL;

public class SimpleEmailDemo {
    public static void main(String[] args) throws EmailException, MalformedURLException {




        Email email = new SimpleEmail();
        //认证
        email.setHostName("smtp.126.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("xushengchen@126.com", "cxs960517"));
        email.setSSLOnConnect(true);
        //收发件人
        email.setFrom("xushengchen@126.com");
        email.addTo("812747475@qq.com");
        //内容
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");



        email.send();
    }
}
