

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("message", "");
		request.getRequestDispatcher("ac_login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		boolean isOK = false;
		String errMessage = "";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "SELECT userId FROM userdata WHERE userId = ? AND password= ?";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, userId);
			smt.setString(2, password);
			ResultSet rs = smt.executeQuery();
			if (rs.next()) {
				isOK = true;
			}else {
				errMessage = "正しいUserid、Passwordを入力してください";
			}

			//smtのクローズ
			smt.close();
			// コネクションのクローズ
			con.close();
		}catch (SQLException e){
			isOK = false;
			errMessage = "エラーが発生しました";
		} catch (Exception e) {
			errMessage = e.getMessage();
			isOK = false;
		}finally {
			if(!isOK) {
				request.setAttribute("message", errMessage);
				request.getRequestDispatcher("ac_login.jsp").forward(request, response);
			}
		}

		if (isOK) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			session.setMaxInactiveInterval(60*60);
			response.sendRedirect("account");
		}
	}

}
