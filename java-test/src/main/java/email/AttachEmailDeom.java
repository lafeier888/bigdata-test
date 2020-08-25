package email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.net.MalformedURLException;
import java.net.URL;

public class AttachEmailDeom {
    public static void main(String[] args) throws MalformedURLException, EmailException {
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Apache logo");
        attachment.setName("Apache logo");

        // Create the email message
        MultiPartEmail email = new MultiPartEmail();

        //认证
        email.setHostName("smtp.126.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("xushengchen@126.com", "cxs960517"));
        email.setSSLOnConnect(true);

        //收发件人
        email.addTo("812747475@qq.com", "lafeier");
        email.setFrom("xushengchen@126.com", "Me");

        //内容
        email.setSubject("The logo");
        email.setMsg("Here is Apache's logo");
        //内容
        email.attach(attachment);

        // send the email
        email.send();
    }
}
