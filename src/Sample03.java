import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySQLアクセスのサンプル(3) 値の取得例 その2
 * 外部JARとしてMySQL Connector/Jを入れておくこと
 * @author densuke-st
 *
 */
public class Sample03 {
	
	// 実際の利用においてはこれらの情報は追い出しておきましょう
	private static String dbhost = "127.0.0.1"; // ポートフォワードを通してのVM側へ
	private static String dbname = "accesssample"; // DB名
	private static String dbuser = "pg4"; // ユーザー
	private static String dbpass = "ProgrammingIV2020"; // パスワード
	
	public static void main(String[] args) {
		String dburl = "jdbc:mysql://" + dbhost + "/" + dbname;
		System.out.println(dburl);		
		try(Connection dbconn = DriverManager.getConnection(dburl, dbuser, dbpass); 
				Statement query = dbconn.createStatement()) {
			String sql = "select count(name) from users";
			query.execute(sql);
			ResultSet results = query.getResultSet();
			results.next();
			Integer count = results.getInt(1);
			System.out.println("データ件数: " + count);
			results.close(); // 結果が不要になったら早めにclose()をおすすめ(メモリ解放しやすくなる)
			
			// 今度は複数のデータを持つクエリをしてみる(1995/1/1以前に生まれた人)
			sql = "select name, birthday from users where birthday < '1995/1/1'";
			results = query.executeQuery(sql); // executeQuery(sql)だといきなりResultSetがもらえる
			while(results.next()) { // next()は「次のレコードに移動をしてみる、できたかどうかをbooleanでおしらせ」
				String name = results.getString(1);
				Date birthday = results.getDate(2);
				System.out.println(name + " " + birthday);
			} // 次に移動することができないとfalse(=データ末尾までなめたということ)
		} catch (SQLException e) {
			// 接続に失敗すれば、こちらでメッセージを出して終了
			System.err.println("DB接続中にエラーが起きました:" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

}
