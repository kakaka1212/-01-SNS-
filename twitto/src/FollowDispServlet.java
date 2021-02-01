

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FollowDispServlet
 */
@WebServlet("/fdisp")
public class FollowDispServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Disp dp = new Disp();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDispServlet() {
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
		int id = Integer.parseInt(request.getParameter("id"));

		switch(id) {
			case 0:
				dp.acDisp(request, response, userId);
				dp.flDisp(request, response, userId);
				dp.flwerDisp(request, response, userId);
				request.setAttribute("userId", userId);
				request.getRequestDispatcher("follows.jsp").forward(request, response);
				break;

			case 1:
				dp.acDisp(request, response, userId);
				dp.flDisp(request, response, userId);
				dp.flwerDisp(request, response, userId);
				request.setAttribute("userId", userId);
				request.getRequestDispatcher("follows2.jsp").forward(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
