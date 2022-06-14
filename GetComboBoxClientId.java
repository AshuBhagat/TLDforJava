//javax.servlet.jsp.jar file download and abc.tld file entry required
package com.abc.tldclasses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.dbutils.DbUtils;
import com.abc.service.MyConn;
public class GetComboBoxClientId extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String name;
    private static Connection conn;
    private static PreparedStatement stmt;
    private static ResultSet rs;
    private int clientCode;
    private String className;
    private String style;
    public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int getClientCode() {
		return clientCode;
	}
	public void setClientCode(int clientCode) {
		this.clientCode = clientCode;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int doStartTag() throws JspException {
        try{
                conn=MyConn.getConnection();
                stmt=conn.prepareStatement("select clientid,firstname,lastname from clienttable",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=stmt.executeQuery();
                int clientid;
                String firstname;
                String lastname;
                pageContext.getOut().write("<select name="+name+" class='"+className+"' style='"+style+"'>");
                while(rs.next())
                {
                	clientid=rs.getInt("clientid");
                	firstname=rs.getString("firstname");
                	lastname=rs.getString("lastname");
                	if(clientid==clientCode)
                	{
                		pageContext.getOut().write("<option value='"+clientid+"'>"+firstname+" "+lastname);
                   	}
                }
			/*
			 * conn=MyConn.getConnection();
			 * ps=con.prepareStatement("select * from clienttable"); rs=ps.executeQuery();
			 */
                rs.beforeFirst();
                while(rs.next())
                {
                    clientid=rs.getInt("clientid");
                    firstname=rs.getString("firstname");
                    lastname=rs.getString("lastname");
                    if(clientid==clientCode)
                    {	
                    	continue;
                    }
                    else
                    {
                    		pageContext.getOut().write("<option value='"+clientid+"'>"+firstname+" "+lastname);
                    }
                }
                	pageContext.getOut().write("</select>");
		}catch(Exception e)
        {
        	e.printStackTrace();
        }finally
        {
        	DbUtils.closeQuietly(conn, stmt, rs);
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
