

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AcUpdate
 */
@WebServlet("/update")
public class AcUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//アカウント詳細表示
	Disp dp = new Disp();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcUpdate() {
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
		dp.upDisp(request,response,userId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);

		request.getRequestDispatcher("ac_update.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//セッション関係
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		//取得
		String reuserId = request.getParameter("userId");
		String password = request.getParameter("password");
		String cnPassword = request.getParameter("cnPassword");
		String name = request.getParameter("name");
		String self = request.getParameter("self");
		boolean okPs = false;
		String erms ="";

		//System.out.println(reuserId);

		if(password.equals(cnPassword)) {
			okPs=true;
		}else {
			erms = "パスワードが一致しておりません";
		}

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");
			if(name!="") {
				String sql = "update userdata set name=? where userId=? ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2,userId);
				ps.executeUpdate();
				//smtのクローズ
				ps.close();
			}
			if(reuserId!="") {
				//System.out.println(reuserId);
				String sql = "update userdata set userId=? where userId=? ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, reuserId);
				ps.setString(2,userId);
				ps.executeUpdate();
				sql = "update twdata set userId=? where userId=? ";
				ps = con.prepareStatement(sql);
				ps.setString(1, reuserId);
				ps.setString(2,userId);
				ps.executeUpdate();
				sql = "update follows set userId=?  where userId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, reuserId);
				ps.setString(2,userId);
				ps.executeUpdate();
				sql = "update follows set followId=?  where followId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, reuserId);
				ps.setString(2,userId);
				ps.executeUpdate();

				userId = reuserId;
				session.setAttribute("userId", reuserId);
				session.setMaxInactiveInterval(60*60);
				//smtのクローズ
				ps.close();
			}
			if(password!=""&&okPs) {
				String sql = "update userdata set password=? where userId=? ";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, password);
					ps.setString(2,userId);
					ps.executeUpdate();
					//smtのクローズ
					ps.close();
			}
			if(self!=""){
				String sql = "update userdata set self=? where userId=? ";
				PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, self);
					ps.setString(2, userId);
					ps.executeUpdate();
					//smtのクローズ
					ps.close();
			}
			// コネクションのクローズ
			con.close();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(!okPs)  {
				request.setAttribute("message", erms);
			}
		}

		//System.out.println(userId);
		request.setAttribute("userId", userId);
		dp.upDisp(request,response,userId);
		dp.flDisp(request, response, userId);
		dp.flwerDisp(request, response, userId);
		request.getRequestDispatcher("ac_update.jsp").forward(request, response);

	}

}
