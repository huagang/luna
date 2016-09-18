package ms.luna.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/***
 * 分页控件
 */
public class MsHref extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String href;
	private String value;
	private Boolean cssClass;

	/***
	 * javascript:void(0);是去掉#很有必要
	 */
	public int doStartTag() throws JspException {
		StringBuffer buffer=new StringBuffer();
		try {
			JspWriter out = pageContext.getOut();
			buffer.append("<dd class=\"menu-item ");
			if (cssClass != null && cssClass.booleanValue()) {
				buffer.append("selected");
			}
			buffer.append(" \">");
			buffer.append("<a href=\"")
			.append(href)
			.append("\"");
			buffer.append(">")
			.append(value)
			.append("</a>");
			
			buffer.append("</dd>");
			
			out.append(buffer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the cssClass
	 */
	public Boolean getCssClass() {
		return cssClass;
	}

	/**
	 * @param cssClass the cssClass to set
	 */
	public void setCssClass(Boolean cssClass) {
		this.cssClass = cssClass;
	}
}