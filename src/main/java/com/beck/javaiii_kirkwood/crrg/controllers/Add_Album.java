package com.beck.javaiii_kirkwood.crrg.controllers;

import com.beck.javaiii_kirkwood.crrg.data.Album_DAO;
import com.beck.javaiii_kirkwood.crrg.models.Album;
import com.beck.javaiii_kirkwood.crrg.models.User;
import com.beck.javaiii_kirkwood.crrg.data_interfaces.iAlbum_DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/******************
 Create the Servlet  For adding to The  Album table
 Created By Jonathan Beck 10/9/2024
 ***************/

@WebServlet("/addAlbum")
public class Add_Album extends HttpServlet{
  private iAlbum_DAO albumDAO;
  @Override
  public void init() throws ServletException{
    albumDAO = new Album_DAO();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//To restrict this page based on privilege level
    int PRIVILEGE_NEEDED = 0;
    List<String> ROLES_NEEDED = new ArrayList<>();
    ROLES_NEEDED.add("Jonathan");
//add roles here
    HttpSession session = req.getSession();
    User user = (User)session.getAttribute("User");
    if (user==null||!user.isInRole(ROLES_NEEDED)){
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    session.setAttribute("currentPage",req.getRequestURL());
    req.setAttribute("pageTitle", "Add Album");
    req.getRequestDispatcher("WEB-INF/crrg/AddAlbum.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//To restrict this page based on privilege level
    int PRIVILEGE_NEEDED = 0;
    List<String> ROLES_NEEDED = new ArrayList<>();
    ROLES_NEEDED.add("Jonathan");
//add roles here
    HttpSession session = req.getSession();
    User user = (User)session.getAttribute("User");
    if (user==null||!user.isInRole(ROLES_NEEDED)){
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    String _Album_Name = req.getParameter("inputalbumAlbum_Name");
    _Album_Name=_Album_Name.trim();
    Map<String, String> results = new HashMap<>();
    results.put("Album_Name",_Album_Name);
    Album album = new Album();
    int errors =0;
    try {
      album.setAlbum_Name(_Album_Name);
    } catch(IllegalArgumentException e) {results.put("albumAlbum_Nameerror", e.getMessage());
      errors++;
    }
    int result=0;
    if (errors==0){
      try{
        result=albumDAO.add(album);
      }catch(Exception ex){
        results.put("dbStatus","Database Error");
      }
      if (result>0){
        results.put("dbStatus","Album Added");
        resp.sendRedirect("all-Albums");
        return;
      } else {
        results.put("dbStatus","Album Not Added");

      }
    }
    req.setAttribute("results", results);
    req.setAttribute("pageTitle", "Add Album");
    req.getRequestDispatcher("WEB-INF/crrg/AddAlbum.jsp").forward(req, resp);

  }
}
