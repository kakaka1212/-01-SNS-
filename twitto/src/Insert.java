import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Insert {

	public void twIst(HttpServletRequest request, HttpServletResponse response,String u) {

		String tweet = request.getParameter("tweet");
		String tmStamp = null;
		boolean erflg = false;
		String erMessage = "";

		//ついっと時 現在時刻取得
		if(tweet!=null) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
			tmStamp = sdf.format(timestamp);
		}

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "INSERT INTO twdata (userId, tweet, tmStamp) VALUES (?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(u!=null&&tweet!=""&&tmStamp!=null) {
				ps.setString(1, u);
				ps.setString(2, tweet);
				ps.setString(3, tmStamp);
				ps.executeUpdate();
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();

		}catch(SQLException e){
			erflg = true;
			erMessage = "50文字以内で入力してください";
		}catch(Exception e){
			erMessage = e.getMessage();
			erflg = true;
		}finally {
			if(erflg) {
				request.setAttribute("message", erMessage);
			}
		}
	}

	public void followIst(HttpServletRequest request, HttpServletResponse response,String u ,String v) {

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "insert into follows (userId,followId) values (?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(u!=null) {
				ps.setString(1, u);
				ps.setString(2, v);
				ps.executeUpdate();
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void mailIst(HttpServletRequest request, HttpServletResponse response,String u,String a) {

		String msg = request.getParameter("mtext");
		System.out.println(msg);

		try {
			// JDBC ドライバのロード
						Class.forName("com.mysql.cj.jdbc.Driver");
						// コネクションの確立
						Connection con = DriverManager.getConnection(
								"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
								"root",
								"");

			String sql = "insert into dmdata(userId,addressId,msg) values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(a!=null && msg!="") {
				ps.setString(1, u);
				ps.setString(2, a);
				ps.setString(3, msg);
				ps.executeUpdate();
			}

			ps.close();
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void likeIst(HttpServletRequest request, HttpServletResponse response) {

		//セッション関係
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

			String sql = "INSERT INTO likedata (userId, likeId, tweet, tmStamp) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			if(userId!=null&&likeId!=null&&tweet!=""&&tmStamp!=null) {
				ps.setString(1, userId);
				ps.setString(2, likeId);
				ps.setString(3, tweet);
				ps.setString(4, tmStamp);
				ps.executeUpdate();
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
