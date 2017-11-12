package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task.DB;
import weblogic.servlet.annotation.WLServlet;

@WLServlet(mapping = "/proceed")
public class HelloServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cookies;
		try {
			cookies = request.getCookies().toString();
		} catch (Exception ex) {
			cookies = "default";
		}
		DB db = DB.getInstance();
		String job = request.getParameter("job");
		if (job == null)
			return;
		if (job.equals("PRINTALL")) {
			response.getWriter().append(db.printAll());
			return;
		}
		if (db.exists(cookies) && job.equals("STATUS")) {
			response.getWriter().append(db.print(cookies));
			return;
		} else {
			if (job.equals("STATUS")) {
				response.getWriter().append("Trip Entry not found!\n");
				return;
			}
		}

		if (!db.exists(cookies) && job.equals("NEW")) {
			db.addTrip(cookies, 1);
			response.getWriter().append("New trip was added.\n");
			return;
		} else {
			if (db.exists(cookies) && (job.equals("PAID") || job.equals("RESERVATION"))) {

				if (db.proceed(job, cookies)) {
					response.getWriter().append("Successfuly proceeded to next stage.")
							.append(db.print(cookies) + "\n");
					return;
				} else {
					response.getWriter().append("Proceed was NOT successfull\n");
					return;
				}
			} else {
				response.getWriter().append("Not correct order or not valid request.\n");

			}
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DB db = DB.getInstance();
		response.getWriter().append(db.printAll());
	}

}
