

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Search_tw
 */
@WebServlet("/search")
public class Search_tw extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Disp dp = new Disp();


    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search_tw() {
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

		//配列初期化
		ArrayList<HashMap<String, String>> tweets = new ArrayList<HashMap<String, String>>();
		request.setAttribute("tweets", tweets);

		ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();
		request.setAttribute("flw", flw);

		dp.fl2Disp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		request.getRequestDispatcher("search.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//セッション関係
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		//配列初期化
		ArrayList<HashMap<String, String>> tweets = new ArrayList<HashMap<String, String>>();
		request.setAttribute("tweets", tweets);

		ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();
		request.setAttribute("flw", flw);

		String shText = request.getParameter("shText");
		//System.out.println(shText);
		boolean flg = shText.startsWith("@");
		//System.out.println(flg);

		if(flg) {
			//System.out.println(shText.replaceFirst("@+", ""));
			dp.schuDisp(request, response, shText.replaceFirst("@+", ""));
		}else if(shText!="") {
			dp.schDisp(request, response, shText);
		}
		dp.fl2Disp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		request.getRequestDispatcher("search.jsp").forward(request, response);

	}

}
