package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet("/DeleteMessage")
public class DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public DeleteMessageServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		//logを書き込んでいる
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		//requestに値に紐づいているmessageのmessageを取得することができる。それをmessageという変数に入れる
		String message = request.getParameter("message");

		//MessageServiceを呼び出してdeleteを依頼する
		MessageService messageservice = new MessageService();
		messageservice.delete(message);

		//TopServletに送ってTop.jspに送る
        response.sendRedirect("./");
	}
}
