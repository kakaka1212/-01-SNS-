
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.mysql.cj.util.StringUtils;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("message", "");
		request.setAttribute("userId", "");
		request.getRequestDispatcher("ac_create.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String self = request.getParameter("self");
		String startMn = null;

		boolean isOK = true;

		int u_len = userId.length();
		int p_len = password.length();

		String errMessage = "";

		//現在時刻取得
		if (password != null) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			startMn = sdf.format(timestamp);
		}

		try {

			//ユーザID、パスワードのチェック
			Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
			Matcher ui = p.matcher(userId);
			Matcher pw = p.matcher(password);

			boolean ng = (ui.find() && pw.find()) ? true : false;

			if(StringUtils.isNullOrEmpty(userId)&&StringUtils.isNullOrEmpty(password)) {
				throw new Exception("Userid、Passwordを入力してください");
			}
			if (u_len < 4 || 10 < u_len) {
				throw new Exception("Useridは4文字以上、10文字以下で入力してください");
			}
			if (p_len < 4 || 20 < p_len) {
				throw new Exception("Passwordは4文字以上、20文字以下で入力してください");
			}
			if (!ng) {
				throw new Exception("半角英数(記号は除く)で入力してください");
			}
			//@追加
			userId = "@"+userId;

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "INSERT INTO userdata (userId, password,name,self,startMn) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, userId);
			smt.setString(2, password);
			smt.setString(3, name);
			smt.setString(4, self);
			smt.setString(5, startMn);

			smt.executeUpdate();

			//smtのクローズ
			smt.close();
			// コネクションのクローズ
			con.close();
		} catch (SQLException e) {
			isOK = false;
			if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
				errMessage = "Userid、Passwordを入力しなおしてください";
			} else {
				errMessage = "エラーが発生しました";
			}
		} catch (Exception e) {
			errMessage = e.getMessage();
			isOK = false;
		} finally {
			if (!isOK) {
				if (userId != null && (u_len < 4 || 10 < u_len)) {
					request.setAttribute("userId", userId);
				} else {
					request.setAttribute("userId", "");
				}
				request.setAttribute("message", errMessage);
				request.getRequestDispatcher("ac_create.jsp").forward(request, response);
			}
		}

		if (isOK) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			session.setMaxInactiveInterval(60*60);
			response.sendRedirect("account");
		}

		///////////
		/*
		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		boolean isSuccess = false;

		//ユーザID、パスワードのチェック
		boolean finalNg = false;
		int u_len = userId.length();
		int p_len = password.length();
		Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher ui = p.matcher(userId);
		Matcher pw = p.matcher(password);

		boolean ng = (ui.find() && pw.find()) ? true : false;

		if (u_len < 4 || 10 < u_len) {
			request.setAttribute("err_msg1", "Useridは4文字以上、10文字以下で入力してください");
			finalNg = true;
		}
		if (p_len < 4 || 20 < p_len) {
			request.setAttribute("err_msg2", "Passwordは4文字以上、20文字以下で入力してください");
			finalNg = true;
		}
		if (!ng) {
			request.setAttribute("err_msg3", "半角英数(記号は除く)で入力してください");
			finalNg = true;
		}
		if (finalNg) {
			request.setAttribute("userId", "");
			request.setAttribute("password", "");
			request.setAttribute("message", "Useridまたは、Passwordが入力されていません");
			request.getRequestDispatcher("ac_create.jsp").forward(request, response);
			return;
		}

		//チェック終了

		// JDBC ドライバのロード
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "INSERT INTO users (userId, password) VALUES (?, ?)";
			PreparedStatement smt = con.prepareStatement(sql);
			smt.setString(1, userId);
			smt.setString(2, password);
			int ck = smt.executeUpdate();
			if (ck == 1) {
				isSuccess = true;
			}

			//smtのクローズ
			smt.close();
			// コネクションのクローズ
			con.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
				request.setAttribute("message", "登録済みのIDです");
				request.setAttribute("userId", "");
				request.setAttribute("password", "");
				request.getRequestDispatcher("ac_create.jsp").forward(request, response);
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.get);
			request.setAttribute("userId", "");
			request.setAttribute("password", "");
			request.getRequestDispatcher("ac_create.jsp").forward(request, response);
		}

		//入力チェック後分岐
		if (isSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);

			response.sendRedirect("input");
		} else {
			request.setAttribute("userId", "");
			request.setAttribute("password", "");
			request.setAttribute("message", "Useridまたは、Passwordが入力されていません");
			request.getRequestDispatcher("ac_create.jsp").forward(request, response);
		}

		*/

	}

}
