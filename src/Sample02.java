import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySQLアクセスのサンプル(2) 値の取得例 その1
 * 外部JARとしてMySQL Connector/Jを入れておくこと
 * @author densuke-st
 *
 */
public class Sample02 {
	
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
			/*                                ↑↑↑↑↑↑↑↑↑↑
			 * (1) SQLを送るためにStatementのインスタンスを生成する
			 * 
			 * 接続状態を保持するConnectionのインスタンスが持つメソッド createStatement() で作成しておく
			 * 使い回せるので一度作ってからしばらく保持しておけば良い
			 */
			String sql = "select count(name) from users";
			query.execute(sql); // (2) クエリ用のインスタンスに渡して実行させる
			ResultSet results = query.getResultSet(); // (3) 実行結果を取得 -> ResultSet
			results.next(); // (4) !! 結果の最初にはなにもはいってない(この理由は後述)
			Integer count = results.getInt(1); // (5) 結果を取得(Integerにて) ※ 添え字は1から!
			System.out.println(count);
		} catch (SQLException e) {
			// 接続に失敗すれば、こちらでメッセージを出して終了
			System.err.println("DB接続中にエラーが起きました:" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

}
