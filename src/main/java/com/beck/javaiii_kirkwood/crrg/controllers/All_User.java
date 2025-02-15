package com.beck.javaiii_kirkwood.crrg.controllers;
/******************
 Create the Servlet  For Viewing all of the  User table
 Created By Jonathan Beck 10/15/2024
 ***************/

import com.beck.javaiii_kirkwood.crrg.data.User_DAO;
import com.beck.javaiii_kirkwood.crrg.models.User;
import com.beck.javaiii_kirkwood.crrg.models.User;
import com.beck.javaiii_kirkwood.crrg.data_interfaces.iUser_DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/allCrrgUser")
public class All_User extends HttpServlet {
  static List<String> allRoles ;
  private iUser_DAO userDAO;
  @Override
  public void init() throws ServletException{
    userDAO = new User_DAO();
    List<String> allRoles;
  }
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//To restrict this page based on privilege level
    int PRIVILEGE_NEEDED = 0;
    List<String> ROLES_NEEDED = new ArrayList<>();
    ROLES_NEEDED.add("Jonathan");
    ROLES_NEEDED.add("BOD");
//add roles here
    HttpSession session = req.getSession();
    User user = (User)session.getAttribute("User");
    if (user==null||!user.isInRole(ROLES_NEEDED)){
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    session.setAttribute("currentPage",req.getRequestURL());
    List<User> users = null;

    try {
      users =userDAO.getAllUser(20,0, null);
      allRoles = userDAO.getDistinctRoleForDropdown();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    req.setAttribute("Roles", allRoles);
    req.setAttribute("Users", users);
    req.setAttribute("pageTitle", "All Users");
    req.getRequestDispatcher("WEB-INF/crrg/all-Users.jsp").forward(req,resp);

  }
}
