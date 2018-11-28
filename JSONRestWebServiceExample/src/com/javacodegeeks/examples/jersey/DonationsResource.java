package com.javacodegeeks.examples.jersey;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bd.daos.Comments;
import bd.daos.Donations;
import bd.dbos.Comment;
import bd.dbos.Donation;

@Path("/donations")
public class DonationsResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Donation> getDonations() throws Exception {
		System.err.println ("GET /donations/");
		return Donations.getDonations();
	}
	
	@GET 
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Donation getDonation(@PathParam("id") String donationIDParam) throws Exception {
		System.err.println ("GET /donations/" + donationIDParam);
		Integer donationID = Integer.parseInt(donationIDParam);
		Donation result = Donations.getDonation(donationID);
		return result;
	}
	
	@GET 
	@Path("{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getDonationComments(@PathParam("id") String donationIDParam) throws Exception {
		System.err.println ("GET /donations/" + donationIDParam + "/comments/");
		Integer donationID = Integer.parseInt(donationIDParam);
		return Comments.getCommentsForDonation(donationID);
	}
	
	@POST
	@Path("{id}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Comment postComment(@PathParam("id") String donationIDParam, @FormParam("comment") String commentParam) throws Exception {
		System.err.println ("POST /donations/" + donationIDParam + "/comments/");
		Integer donationID = Integer.parseInt(donationIDParam);
		Comment comment = new Comment(donationID, commentParam);
		return Comments.insert(comment);
	}
}