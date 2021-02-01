

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OtherFollowServletDisp
 */
@WebServlet("/otherfdisp")
public class OtherFollowServletDisp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Disp dp = new Disp();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OtherFollowServletDisp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		String oid = request.getParameter("oid");

		dp.acDisp(request, response, oid);
		dp.flDisp(request, response, oid);
		dp.fl2Disp(request, response, userId);
		dp.flwer2Disp(request, response, userId);
		request.setAttribute("userId", oid);
		request.getRequestDispatcher("otherfollows.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
