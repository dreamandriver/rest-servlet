为Servlet添加支持REST式URL:
不改变习惯，仅仅在servlet内部完成doGet, doPost,doDelete,doPut等方法即可映射到较为复杂的REST URL

eg:
```
/book/head first java/
对应的URL表达式为:
/book/*/

/book/head first java/chapter/1
对应的URL表达式为:
/book/*/chapter/*
```

您所要做的：
仅仅需要在web.xml中配置一个filter
仅仅需要在集成HttpServlet添加一个注解(eg:`@RestSupport("/book/*/chapter/*")`)

下面附加一个使用示范:
```
@RestSupport("/book/*/chapter/*")
public class ChapterServlet extends HttpServlet {
	private static final long serialVersionUID = -1534235656L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// code here ...
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// code here ...
	}

	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// code here ...
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// code here ...
	}
}
```
