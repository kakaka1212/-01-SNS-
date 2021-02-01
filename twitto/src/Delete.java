

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Delete {

	public void flDe(HttpServletRequest request, HttpServletResponse response,String u, String v) {
		String followId = request.getParameter("followId");
		try {
			// JDBC ドライバのロード
						Class.forName("com.mysql.cj.jdbc.Driver");
						// コネクションの確立
						Connection con = DriverManager.getConnection(
								"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
								"root",
								"");

			String sql = "delete from follows where userId = ? and followId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			if(followId!=null&&u!=null) {
				ps.setString(1, u);
				ps.setString(2, v);
				ps.executeUpdate();
			}

			ps.close();
			con.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void likeDe(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String likeId = request.getParameter("userId");
		String tweet = request.getParameter("tweet");
		String tmStamp = request.getParameter("tmStamp");

		try {
			// JDBC ドライバのロード
						Class.forName("com.mysql.cj.jdbc.Driver");
						// コネクションの確立
						Connection con = DriverManager.getConnection(
								"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
								"root",
								"");

			String sql = "delete from likedata where userId = ? and likeId = ? and tweet = ? and tmStamp = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			if(userId!=null&&likeId!=null&&tweet!=""&&tmStamp!=null) {
				ps.setString(1, userId);
				ps.setString(2, likeId);
				ps.setString(3, tweet);
				ps.setString(4, tmStamp);
				ps.executeUpdate();
			}

			ps.close();
			con.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
