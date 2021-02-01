

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Follow_tw
 */
@WebServlet("/follow")
public class Follow_people extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//表示
	Disp dp = new Disp();
	//ついっと追加
	Insert in  = new Insert();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Follow_people() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//セッション関係
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		request.setAttribute("userId", userId);
		dp.twDisp(request,response,userId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		request.getRequestDispatcher("home.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		request.setAttribute("userId", userId);
		in.twIst(request,response,userId);
		dp.twDisp(request,response,userId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		request.getRequestDispatcher("home.jsp").forward(request, response);

	}

}
