package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bd.dbos.Donation;

/**
 * Servlet implementation class Donations
 */
@WebServlet("/donations")
public class Donations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Donations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title_input");
		String description = request.getParameter("description_input");
		String contact = request.getParameter("contact_input");
		String city = request.getParameter("city_input");
		String state = request.getParameter("state_input");
		String address = request.getParameter("address_input");
		
		try {
			Donation donation = new Donation(title, description, contact, city, state, address);
			bd.daos.Donations.insert(donation);
		} catch(Exception error) {
			System.out.println(error.getMessage());
		}
	}
}
