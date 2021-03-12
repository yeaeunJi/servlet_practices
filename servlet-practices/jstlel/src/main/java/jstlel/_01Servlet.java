package jstlel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/01")
public class _01Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int iVal = 10;
		long lVal = 10;
		float fVal = 3.14f;
		boolean bVal = true;
		String sVal = "가나다라마바사";
		
		// 객체 테스트
		UserVo userVo = new UserVo();
		userVo.setNo(10L);
		userVo.setName("안녕");
		
		// Map 테스트
		Map<String, Object> map = new HashMap<>();
		map.put("ival", iVal);
		map.put("fval", fVal);
		
		// null
		Object obj = null;
		
		request.setAttribute("iVal", iVal); // autoboxing일어남(객체로 저장하기 때문)
		request.setAttribute("lVal",lVal );
		request.setAttribute("fVal", fVal);
		request.setAttribute("bVal", bVal);
		request.setAttribute("sVal",sVal );
		
		request.setAttribute("map",map);
		
		request.setAttribute("vo", userVo);
		request.setAttribute("o", obj);
		request.getRequestDispatcher("/WEB-INF/views/01.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
