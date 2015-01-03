package com.blog.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.blog.model.Article;
import com.blog.model.Utilisateur;
import com.blog.dao.ArticleDAO;
import com.blog.dao.UtilisateurDAO;

/**
 * Servlet implementation class Artcle_my
 */
public class Article_my extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String VUE   = "/views/Article_my.jsp"; 
    public static final String VUE_CONNEXION    = "/Connexion";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Article_my() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// récupère l'utilisateur
		HttpSession session = request.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		if(user == null) {
			this.getServletContext().getRequestDispatcher( VUE_CONNEXION ).forward( request, response );
            
		} else {
			ArticleDAO daoArt = new ArticleDAO();
			UtilisateurDAO daoUt = new UtilisateurDAO();
			
		    Utilisateur auteur = user;
		    
		    
		    DateTime datetime;
		    int day =0; 
			int month =0; 
			int year =0; 

	
			List<Article> list = daoArt.findArticlesByAuteur(auteur.getId());
			for(Article a : list){
				
				
				datetime = new DateTime(a.getDateCreation());
				
				 day = datetime.getDayOfWeek();
				 month = datetime.getMonthOfYear();
				 year = datetime.getYear();
				
				System.out.println("Article n°"+a.getId()+", titre : "+a.getTitre() + day + month + year);
				
			}
			
			
			// Set des paramètres
			//request.setAttribute("datetime", datetime);
			request.setAttribute("day", day);
			request.setAttribute("month", month);
			request.setAttribute("year", year);
			request.setAttribute("list_article", list);
			
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
