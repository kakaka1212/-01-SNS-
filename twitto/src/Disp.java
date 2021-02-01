import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Disp {


	//プロフィール詳細
	public void acDisp(HttpServletRequest request, HttpServletResponse response,String u) {

		String name = "";
		String self = "";
		String startMn = "";
		int follow_p = 0;
		int follower_p = 0;

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "SELECT name,self,startMn  FROM userData where userId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
				self = rs.getString("self");
				startMn = rs.getString("startMn");
			}

			String sql2 = "select count(followId) from follows where userId = ?";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.setString(1, u);
			ResultSet rs2 = ps2.executeQuery();
			if(rs2.next()) {
				follow_p = rs2.getInt("count(followID)");
			}

			String sql3 = "select count(userId) from follows where followId = ?";
			PreparedStatement ps3 = con.prepareStatement(sql3);
			ps3.setString(1, u);
			ResultSet rs3 = ps3.executeQuery();
			if(rs3.next()) {
				follower_p = rs3.getInt("count(userId)");
			}

			//smtのクローズ
			ps.close();
			ps2.close();
			ps3.close();
			// コネクションのクローズ
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("name", name);
		request.setAttribute("self", self);
		request.setAttribute("startMn", startMn);
		request.setAttribute("follow_p", follow_p);
		request.setAttribute("follower_p", follower_p);
	}

	//プロフィール更新
	public void upDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		//String password ="";
		String name="";
		String userId ="";
		String self="";
		String startMn="";

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");
			String sql = "SELECT userId,name,self,startMn  FROM userData where userId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				userId = rs.getString("userId");
				//password = rs.getString("password");
				name = rs.getString("name");
				self = rs.getString("self");
				startMn = rs.getString("startMn");
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*int psln = password.length();
		String rePass = "";
		for(int i=0; i<psln; i++){
			rePass+="*";
		}*/

		request.setAttribute("userId", userId);
		//request.setAttribute("password", rePass);
		request.setAttribute("name", name);
		request.setAttribute("self", self);
		request.setAttribute("startMn", startMn);
	}

	//検索結果表示
	public void schDisp(HttpServletRequest request, HttpServletResponse response, String t) {

		//配列初期化
		ArrayList<HashMap<String, String>> tweets = new ArrayList<HashMap<String, String>>();

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "select userId,tweet,tmStamp from twdata where tweet collate utf8_unicode_ci like ? order by tmStamp desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%"+t+"%");

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, String> log =new HashMap<String, String>();
				log.put("userId", rs.getString("userId"));
				log.put("tweet", rs.getString("tweet"));
				log.put("tmStamp", rs.getString("tmStamp"));
				tweets.add(log);
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("tweets", tweets);
	}

	/*ユーザー検索*/
	public void schuDisp(HttpServletRequest request, HttpServletResponse response, String t) {

		//配列初期化
		ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = "select name,userId,self from userdata where userId like ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "@%"+t+"%");

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, String> log =new HashMap<String, String>();
				log.put("name", rs.getString("name"));
				log.put("userId", rs.getString("userId"));
				log.put("self", rs.getString("self"));
				flw.add(log);
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("flw", flw);
	}

	//ついっと表示
	public void twDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		//配列初期化
		ArrayList<HashMap<String, String>> tweets = new ArrayList<HashMap<String, String>>();

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");
			if(request.getAttribute("follow_p")=="0") {
				String sql = "select userId,tweet,tmStamp from twdata where userId = ? order by tmStamp desc";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, "u");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					HashMap<String, String> log =new HashMap<String, String>();
					log.put("userId", rs.getString("userId"));
					log.put("tweet", rs.getString("tweet"));
					log.put("tmStamp", rs.getString("tmStamp"));
					tweets.add(log);
				}
				//smtのクローズ
				ps.close();

			}else {
				//フォローID＆ユーザID取得
				String sql = "select followId from follows where userId = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1,u);
				ResultSet rs = ps.executeQuery();
				ArrayList<String> tws = new ArrayList<String>();
				while(rs.next()) {
					tws.add(rs.getString("followId"));
				}

				//sql作成
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<tws.size(); i++) {
					String sqlP = " "+"or userId = "+"'"+tws.get(i)+"'";
				    sb.append(sqlP);
				}
				String sqlQ = sb.toString();
				//System.out.println(sqlQ);

				//ツイートデータ取得
				String sql2 = " select * from twdata where userId = ?"+ sqlQ +" order by tmStamp desc;";
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.setString(1, u);
				ResultSet  rs2 = ps2.executeQuery();
				while(rs2.next()) {
					HashMap<String, String> log3 =new HashMap<String, String>();
					log3.put("userId", rs2.getString("userId"));
					log3.put("tweet", rs2.getString("tweet"));
					log3.put("tmStamp", rs2.getString("tmStamp"));
					tweets.add(log3);
				}
				//smtのクローズ
				ps.close();
				ps2.close();
			}

			// コネクションのクローズ
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("tweets", tweets);

	}

	/*ユーザーついっと*/
	public void onlyDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		//配列初期化
		ArrayList<HashMap<String, String>> tweets = new ArrayList<HashMap<String, String>>();

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");
			String sql = "select userId,tweet,tmStamp from twdata where userId = ? order by tmStamp desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, String> log =new HashMap<String, String>();
				log.put("userId", rs.getString("userId"));
				log.put("tweet", rs.getString("tweet"));
				log.put("tmStamp", rs.getString("tmStamp"));
				tweets.add(log);
			}

			//smtのクローズ
			ps.close();
			// コネクションのクローズ
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("tweets", tweets);

	}

	/*フォロー表示*/
	public void flDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		//配列初期化
		ArrayList<HashMap<String, String>> flwId = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql="select followId from follows where userId = ? order by followtime desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);


			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, String> log =new HashMap<String, String>();
				log.put("followId", rs.getString("followId"));
				flwId.add(log);
			}

			for(HashMap<String, String> log: flwId) {
				sql="select name,userId,self from userdata where userId = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, log.get("followId"));
				ResultSet rs2 = ps.executeQuery();
				while(rs2.next()) {
					HashMap<String, String> log2 =new HashMap<String, String>();
					log2.put("name", rs2.getString("name"));
					log2.put("userId", rs2.getString("userId"));
					log2.put("self", rs2.getString("self"));
					flw.add(log2);
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("flw", flw);
	}

	/*フォロー表示2*/
	public void fl2Disp(HttpServletRequest request, HttpServletResponse response,String u) {
		//配列初期化
		ArrayList<HashMap<String, String>> flwId = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();

		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql="select followId from follows where userId = ? order by followtime desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);


			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, String> log =new HashMap<String, String>();
				log.put("followId", rs.getString("followId"));
				flwId.add(log);
			}

			for(HashMap<String, String> log: flwId) {
				sql="select name,userId,self from userdata where userId = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, log.get("followId"));
				ResultSet rs2 = ps.executeQuery();
				while(rs2.next()) {
					HashMap<String, String> log2 =new HashMap<String, String>();
					log2.put("name", rs2.getString("name"));
					log2.put("userId", rs2.getString("userId"));
					log2.put("self", rs2.getString("self"));
					flw.add(log2);
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("flw2", flw);
	}

	/*フォロワー表示*/
	public void flwerDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		//配列初期化
				ArrayList<HashMap<String, String>> flwId = new ArrayList<HashMap<String, String>>();
				ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();

				try {
					// JDBC ドライバのロード
					Class.forName("com.mysql.cj.jdbc.Driver");
					// コネクションの確立
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
							"root",
							"");

					String sql="select userId from follows where followId = ? order by followtime desc";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, u);

					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						HashMap<String, String> log =new HashMap<String, String>();
						log.put("followId", rs.getString("userId"));
						flwId.add(log);
					}

					for(HashMap<String, String> log: flwId) {
						sql="select name,userId,self from userdata where userId = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, log.get("followId"));
						ResultSet rs2 = ps.executeQuery();
						while(rs2.next()) {
							HashMap<String, String> log2 =new HashMap<String, String>();
							log2.put("name", rs2.getString("name"));
							log2.put("userId", rs2.getString("userId"));
							log2.put("self", rs2.getString("self"));
							flw.add(log2);
						}
					}


				}catch(Exception e) {
					e.printStackTrace();
				}

				request.setAttribute("flwer", flw);
	}

	/*フォロワー表示2*/
	public void flwer2Disp(HttpServletRequest request, HttpServletResponse response,String u) {
		//配列初期化
				ArrayList<HashMap<String, String>> flwId = new ArrayList<HashMap<String, String>>();
				ArrayList<HashMap<String, String>> flw = new ArrayList<HashMap<String, String>>();

				try {
					// JDBC ドライバのロード
					Class.forName("com.mysql.cj.jdbc.Driver");
					// コネクションの確立
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
							"root",
							"");

					String sql="select userId from follows where followId = ? order by followtime desc";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, u);

					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						HashMap<String, String> log =new HashMap<String, String>();
						log.put("followId", rs.getString("userId"));
						flwId.add(log);
					}

					for(HashMap<String, String> log: flwId) {
						sql="select name,userId,self from userdata where userId = ?";
						ps = con.prepareStatement(sql);
						ps.setString(1, log.get("followId"));
						ResultSet rs2 = ps.executeQuery();
						while(rs2.next()) {
							HashMap<String, String> log2 =new HashMap<String, String>();
							log2.put("name", rs2.getString("name"));
							log2.put("userId", rs2.getString("userId"));
							log2.put("self", rs2.getString("self"));
							flw.add(log2);
						}
					}


				}catch(Exception e) {
					e.printStackTrace();
				}

				request.setAttribute("flwer2", flw);
	}

	//他ユーザープロフィール詳細
	public void oacDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		String name = "";
		String self = "";
		String startMn = "";
		int follow_p = 0;
		int follower_p = 0;

		try {
			// JDBC ドライバのロード
						Class.forName("com.mysql.cj.jdbc.Driver");
						// コネクションの確立
						Connection con = DriverManager.getConnection(
								"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
								"root",
								"");

			String sql = "select name, self,startMn from userdata where userId = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
				self = rs.getString("self");
				startMn = rs.getString("startMn");
			}

			String sql2 = "select count(followId) from follows where userId = ?";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.setString(1, u);
			ResultSet rs2 = ps2.executeQuery();
			if(rs2.next()) {
				follow_p = rs2.getInt("count(followID)");
			}

			String sql3 = "select count(userId) from follows where followId = ?";
			PreparedStatement ps3 = con.prepareStatement(sql3);
			ps3.setString(1, u);
			ResultSet rs3 = ps3.executeQuery();
			if(rs3.next()) {
				follower_p = rs3.getInt("count(userId)");
			}

			//smtのクローズ
			ps.close();
			ps2.close();
			ps3.close();
			// コネクションのクローズ
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		request.setAttribute("name", name);
		request.setAttribute("other_uId", u);
		request.setAttribute("self", self);
		request.setAttribute("startMn", startMn);
		request.setAttribute("follow_p", follow_p);
		request.setAttribute("follower_p", follower_p);

	}

	//メール表示
	public void mailDisp(HttpServletRequest request, HttpServletResponse response,String u) {
		String adId = request.getParameter("addressId");

		ArrayList<HashMap<String, String>> mails = new ArrayList<HashMap<String, String>>();
		try {
			// JDBC ドライバのロード
			Class.forName("com.mysql.cj.jdbc.Driver");
			// コネクションの確立
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost/sample01?characterEncoding=utf8&serverTimezone=JST",
					"root",
					"");

			String sql = ("select userId,msg,msgTime from dmdata where (userId=? and addressId=?) or (userId=? and addressId=?)");
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u);
			ps.setString(2, adId);
			ps.setString(3, adId);
			ps.setString(4, u);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				HashMap<String, String> log = new HashMap<String, String>();
				log.put("userId", rs.getString("userId"));
				log.put("mtext", rs.getString("msg"));
				log.put("tmStamp", rs.getString("msgTime"));
				mails.add(log);
			}

			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("mails", mails);

	}


}
