package com.beck.javaiii_kirkwood.learnx.controllers;


import com.beck.javaiii_kirkwood.learnx.Models.Course;
import com.beck.javaiii_kirkwood.learnx.Models.User;
import com.beck.javaiii_kirkwood.learnx.data.CourseDAO;
import com.beck.javaiii_kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    User activeUser = Helpers.getUserFromSession(session);
    if(activeUser == null || !Helpers.isStudent(activeUser)) {
      session.setAttribute("flashMessageWarning", "You must be a student to view this content.");
      resp.sendRedirect("signin?redirect=student");
      return;
    }

    // To do: For pagination
    int limit = 5;
    int offset = 0;
    Map<Course, Instant> enrollments = CourseDAO.getCoursesEnrolled(limit, offset, activeUser.getID());
    req.setAttribute("enrollments", enrollments);

    req.setAttribute("pageTitle", "Student Dashboard");
    req.getRequestDispatcher("WEB-INF/learnx/student.jsp").forward(req, resp);
  }
}
