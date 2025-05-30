package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	* */
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	@Override
	//ページを取得したいためGetメソッドを使用する
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		//logを書き込んでいる
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//requestに値に紐づいているmessageのtextを取得することができる。それをtextという変数に入れる
		String editid = request.getParameter("editid");

		int editMessageid = Integer.parseInt(editid);

		//MessageServiceを呼び出してselectを依頼する
		MessageService messageservice = new MessageService();
		Message  message = messageservice.select(editMessageid);

		request.setAttribute("message", message);
		//直接edit.jspを表示させる
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	@Override
	//入力したものを更新したいためPostメソッドを使用する
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		//logを書き込んでいる
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//requestに値に紐づいているmessageのidを取得することができる。それをeditという変数に入れる
		String messageid = request.getParameter("messageId");

		//MessageServiceを呼び出してupdataを依頼する
		MessageService messageservice = new MessageService();
		Message message = messageservice.updata(messageid);

		request.setAttribute("message", message);

		//TopServletに送ってTop.jspに送る
		response.sendRedirect("./");
	}
}
