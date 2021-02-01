

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OtherAcServlet
 */
@WebServlet("/otherac")
public class OtherAcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Disp dp = new Disp();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OtherAcServlet() {
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
		String other_uId = request.getParameter("other_uId");
		//System.out.println(userId);
		//System.out.println(other_uId);

		if(userId.equals(other_uId)) {
			//System.out.println(1);
			request.setAttribute("userId", userId);
			dp.acDisp(request,response,userId);
			dp.twDisp(request,response,userId);
			request.getRequestDispatcher("acount.jsp").forward(request, response);
		}else {
			//System.out.println(2);
			//済み判定
			boolean flg = false;
			int f = 0;
			try {
				// JDBC ドライバのロード
							Class.forName("com.mysql.cj.jdbc.Driver");
							// コネクションの確立
							Connection con = DriverManager.getConnection(
									"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
									"root",
									"");

				String sql = "select count(*) from follows where userId = ? and followId = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, other_uId);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					f = rs.getInt("count(*)");
				}

				if(f==0) {
					flg = false;
					}else {
					flg = true;
				}

				//smtのクローズ
				ps.close();
				// コネクションのクローズ
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}

			if(flg) {
				dp.oacDisp(request, response, other_uId);
				dp.onlyDisp(request, response, other_uId);
				dp.flDisp(request, response, userId);
				dp.flwerDisp(request, response, userId);
				request.setAttribute("other_uId", other_uId);
				request.getRequestDispatcher("ac_other2.jsp").forward(request, response);
			}else {
				dp.oacDisp(request, response, other_uId);
				dp.onlyDisp(request, response, other_uId);
				dp.flDisp(request, response, userId);
				dp.flwerDisp(request, response, userId);
				request.setAttribute("other_uId", other_uId);
				request.getRequestDispatcher("ac_other.jsp").forward(request, response);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
