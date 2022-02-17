package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class AddNumber_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_out_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_s_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_s_out_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_s_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_s_out_value_nobody.release();
    _jspx_tagPool_s_set_var_value_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

        if(request.getParameter("submit") != null){
            int n1=Integer.parseInt(request.getParameter("num1"));
            int n2=Integer.parseInt(request.getParameter("num2"));

      out.write("\n");
      out.write("        ");
      //  s:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_s_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_s_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_s_set_0.setPageContext(_jspx_page_context);
      _jspx_th_s_set_0.setParent(null);
      _jspx_th_s_set_0.setVar("num1");
      _jspx_th_s_set_0.setValue(n1 );
      int _jspx_eval_s_set_0 = _jspx_th_s_set_0.doStartTag();
      if (_jspx_th_s_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_0);
        return;
      }
      _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_0);
      out.write("\n");
      out.write("        ");
      //  s:set
      org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_s_set_1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_s_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
      _jspx_th_s_set_1.setPageContext(_jspx_page_context);
      _jspx_th_s_set_1.setParent(null);
      _jspx_th_s_set_1.setVar("num2");
      _jspx_th_s_set_1.setValue(n2 );
      int _jspx_eval_s_set_1 = _jspx_th_s_set_1.doStartTag();
      if (_jspx_th_s_set_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_1);
        return;
      }
      _jspx_tagPool_s_set_var_value_nobody.reuse(_jspx_th_s_set_1);
      out.write("\n");
      out.write("        \n");
      out.write("        ");
      if (_jspx_meth_s_out_0(_jspx_page_context))
        return;
      out.write("        \n");
      out.write(" ");

     }
  
      out.write("   \n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Add</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <form action=\"#\" method=\"post\">\n");
      out.write("            <table>\n");
      out.write("                <tr>\n");
      out.write("                    <td>number1</td>\n");
      out.write("                    <td><input type=\"text\" name=\"num1\"></td>\n");
      out.write("                </tr>\n");
      out.write("                 <tr>\n");
      out.write("                    <td>number2</td>\n");
      out.write("                    <td><input type=\"text\" name=\"num2\"></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td><input type=\"Submit\" name=\"submit\"></td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_s_out_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_s_out_0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _jspx_tagPool_s_out_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_s_out_0.setPageContext(_jspx_page_context);
    _jspx_th_s_out_0.setParent(null);
    _jspx_th_s_out_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${num1+num2}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_s_out_0 = _jspx_th_s_out_0.doStartTag();
    if (_jspx_th_s_out_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_s_out_value_nobody.reuse(_jspx_th_s_out_0);
      return true;
    }
    _jspx_tagPool_s_out_value_nobody.reuse(_jspx_th_s_out_0);
    return false;
  }
}
