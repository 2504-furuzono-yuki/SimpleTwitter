package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.Message;
import chapter6.exception.SQLRuntimeException;
import chapter6.logging.InitApplication;

public class MessageDao {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public MessageDao() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	public void insert(Connection connection, Message message) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append("    user_id, ");
			sql.append("    text, ");
			sql.append("    created_date, ");
			sql.append("    updated_date ");
			sql.append(") VALUES ( ");
			sql.append("    ?, "); // user_id
			sql.append("    ?, "); // text
			sql.append("    CURRENT_TIMESTAMP, "); // created_date
			sql.append("    CURRENT_TIMESTAMP "); // updated_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, message.getUserId());
			ps.setString(2, message.getText());

			ps.executeUpdate();

		} catch (SQLException e) {
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw new SQLRuntimeException(e);
		}finally {
			close(ps);
		}
	}

	public void delete(Connection connection , String message) {

		//logを書き込んでいる
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		//psという変数名を作成
		PreparedStatement ps = null;

		//messageをintに型変換する
		int messageId = Integer.parseInt(message);

		//SQLを動かす文を作成する
		try {
			//DELETEしたいものを指定する
			String sql = "DELETE FROM messages WHERE id = ?";

			//これからセットしたいSQL文を接続
			ps = connection.prepareStatement(sql);

			//?に入れたい値をセットする
			ps.setInt(1, messageId);

			//SQLを実行する
			ps.executeUpdate();

			return ;

		} catch (SQLException e) {
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//EditServletのdoGetで使用(編集画面表示)
	public Message select(Connection connection , int id) {

		//logを書き込んでいる
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		//psという変数名を作成
		PreparedStatement ps = null;

		//SQLを動かす文を作成する
		try {
			//SELECTしたいものを指定する
			String sql = "SELECT * FROM messages WHERE id = ?";

			//これからセットしたいSQL文を接続
			ps = connection.prepareStatement(sql);

			//?に入れたい値をセットする
			ps.setInt(1, id);

			//SQLを実行する
			ResultSet rs = ps.executeQuery();


			List<Message> messages = toUserMessages(rs);
			return (messages.get(0));
		} catch (SQLException e) {
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<Message> toUserMessages(ResultSet rs) throws SQLException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		List<Message> messages = new ArrayList<Message>();
		try {
			while (rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setText(rs.getString("text"));
				message.setUserId(rs.getInt("user_id"));
				message.setCreatedDate(rs.getTimestamp("created_date"));

				messages.add(message);
			}
			return messages;
		} finally {
			close(rs);
		}
	}
	public void updata(Connection connection, String messageid, String messagetext) {

		//logを書き込んでいる
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		//psという変数名を作成
		PreparedStatement ps = null;

		//messageをintに型変換する
		int text = Integer.parseInt(messagetext);
		int id = Integer.parseInt(messageid);

		//SQLを動かす文を作成する
		try {
			//UPDATEしたいものを指定する
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE messages SET");
			sql.append("    text = ?, ");
			sql.append("WHERE id = ?");

			//これからセットしたいSQL文を接続
			ps = connection.prepareStatement(sql.toString());

			//?に入れたい値をセットする
			ps.setString(1, Message.getText());
			ps.setInt(2, id);

			//SQLを実行する
			ps.executeUpdate();

			return ;

		} catch (SQLException e) {
			log.log(Level.SEVERE, new Object() {
			}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
