package ms.luna.biz.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
	/**
	 * 以文本格式发送邮件
	 */
	public boolean sendTextMail(MailProperty mailInfo) {
		// 判断是否需要身份认证
		Authentication authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new Authentication(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session MailPropertySession = Session.getDefaultInstance(pro, authenticator);
		try {
			Message mailMessage = new MimeMessage(MailPropertySession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 */
	public boolean sendHtmlMail(MailProperty mailInfo) {
		// 判断是否需要身份认证
		Authentication authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			authenticator = new Authentication(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session MailPropertySession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(MailPropertySession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, to); // to改为数组可以做到群发，但这里的群发好像是单线程的。对于大批量发送不适合，那时应该用多线程
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public class MailProperty {
		// 发送邮件的服务器的IP和端口
		private String mailServerHost;
		private String mailServerPort = "25";
		private String fromAddress;
		private String toAddress;
		private String userName;
		private String password;
		private boolean validate = false;
		private String subject;
		private String content;
		private String[] attachFileNames;

		/**
		 * 获得邮件会话属性
		 */
		public Properties getProperties() {
			Properties p = new Properties();
			p.put("mail.smtp.host", this.mailServerHost);
			p.put("mail.smtp.port", this.mailServerPort);
			p.put("mail.smtp.auth", validate ? "true" : "false");
			p.put("mail.smtp.localhost", "127.0.0.1");// 在项目里面使用javamail在window环境正常,放在服务器上面的时候抛出异常
													  // javax.mail.MessagingException:
													  // 501 Syntax: HELO hostname,原因是在linux无法解析邮件服务器名称为ip地址
			return p;
		}

		public String getMailServerHost() {
			return mailServerHost;
		}

		public void setMailServerHost(String mailServerHost) {
			this.mailServerHost = mailServerHost;
		}

		public String getMailServerPort() {
			return mailServerPort;
		}

		public void setMailServerPort(String mailServerPort) {
			this.mailServerPort = mailServerPort;
		}

		public boolean isValidate() {
			return validate;
		}

		public void setValidate(boolean validate) {
			this.validate = validate;
		}

		public String[] getAttachFileNames() {
			return attachFileNames;
		}

		public void setAttachFileNames(String[] fileNames) {
			this.attachFileNames = fileNames;
		}

		public String getFromAddress() {
			return fromAddress;
		}

		public void setFromAddress(String fromAddress) {
			this.fromAddress = fromAddress;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getToAddress() {
			return toAddress;
		}

		public void setToAddress(String toAddress) {
			this.toAddress = toAddress;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String textContent) {
			this.content = textContent;
		}
	}

	public class Authentication extends Authenticator {
		String username = null;
		String password = null;

		public Authentication() {
		}

		public Authentication(String username, String password) {
			this.username = username;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			PasswordAuthentication pa = new PasswordAuthentication(username, password);
			return pa;
		}
	}
}