

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MailServlet
 */
@WebServlet("/mail")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Disp dp = new Disp();
	Insert it = new Insert();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
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
		String adId = request.getParameter("addressId");

		dp.acDisp(request, response, adId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		dp.mailDisp(request, response, userId);
		request.setAttribute("userId", adId);
		request.getRequestDispatcher("dmail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//セッション関係
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String adId = request.getParameter("addressId");
		System.out.println(adId);
		it.mailIst(request, response, userId, adId);

		dp.acDisp(request, response, adId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		dp.mailDisp(request, response, userId);
		request.setAttribute("userId", adId);
		request.getRequestDispatcher("dmail.jsp").forward(request, response);

	}

}
