

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class UserFollowServlet2
 */
@WebServlet("/userfollow2")
public class UserFollowServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Disp dp = new Disp();
	Delete dl = new Delete();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserFollowServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//セッション関係
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		String other_uId = request.getParameter("other_uId");
		dl.flDe(request,response,userId,other_uId);

		dp.oacDisp(request, response, other_uId);
		dp.onlyDisp(request, response, other_uId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		request.setAttribute("other_uId", other_uId);
		request.getRequestDispatcher("ac_other.jsp").forward(request, response);

	}

}
