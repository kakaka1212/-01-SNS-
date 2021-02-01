

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class input_twServlet
 */
@WebServlet("/account")
public class Account_twServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//表示
	Disp dp = new Disp();

	//private Connection con;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Account_twServlet() {
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
		dp.acDisp(request,response,userId);
		dp.twDisp(request,response,userId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);

		request.getRequestDispatcher("acount.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
