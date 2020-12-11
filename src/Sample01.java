import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQLアクセスのサンプル
 * 外部JARとしてMySQL Connector/Jを入れておくこと
 * @author densuke-st
 *
 */
public class Sample01 {
	
	// 実際の利用においてはこれらの情報は追い出しておきましょう
	private static String dbhost = "127.0.0.1"; // ポートフォワードを通してのVM側へ
	private static String dbname = "accesssample"; // DB名
	private static String dbuser = "pg4"; // ユーザー
	private static String dbpass = "ProgrammingIV2020"; // パスワード
	
	public static void main(String[] args) {
		/* (1) JDBCではDBに対する接続用URL(接続文字列)を用意する必要がある
		 * 
		 * - "jdbc:ドライバ名://接続先ホスト名/DB名"が基本
		 * - ドライバは自動的に検索される
		 * - mysqlであればmysql-connectorにて宣言されるので、ビルドパス上に存在してれば束縛される
		 */		
		String dburl = "jdbc:mysql://" + dbhost + "/" + dbname;
		System.out.println(dburl);
		
		/* (2) 接続を行うために、接続状態を保持するインスタンス(Connectionクラス)を生成する 
		 * 
		 * - インスタンス生成はJDBCの持つConnectionManagerを使うとよしなに準備してくれる
		 * - DriverManager.getConnection(接続文字列, ユーザー, パスワード) -> Connection
		 * 
		 * ここではクローズを手軽にするためにtry-with-resource構文を使用してます
		 */
		try(Connection dbconn = DriverManager.getConnection(dburl, dbuser, dbpass)) {
			// この例は、接続できたらインスタンスも文字列化をして出力するだけ
			System.out.println("OK: " + dbconn);
		} catch (SQLException e) {
			// 接続に失敗すれば、こちらでメッセージを出して終了
			System.err.println("DB接続中にエラーが起きました:" + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

}
