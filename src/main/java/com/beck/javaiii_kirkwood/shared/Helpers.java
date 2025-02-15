package com.beck.javaiii_kirkwood.shared;

import com.beck.javaiii_kirkwood.learnx.Models.User;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Helpers {
  public static long ageInYears(String birthDay) {
    DateTimeFormatter formatter = null;
    LocalDate birthDate = null;
    try {
      formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.US);
      birthDate = LocalDate.parse(birthDay, formatter);
    } catch(DateTimeParseException e) {
      try {
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        birthDate = LocalDate.parse(birthDay, formatter);
      } catch(DateTimeParseException e2) {
        return 0;
      }
    }
    return Period.between(birthDate, LocalDate.now()).getYears();
  }

  public static User getUserFromSession(HttpSession session){
    User user = (User)session.getAttribute("activeUser");
    return user;

  }

  public static Boolean isActive(User user){
    return user.getStatus().equals("active");
  }

  public static Boolean isStudent(User user){
    return user.getPrivileges().equals("student");
  }

  public static Boolean isTeacher(User user){
    return user.getPrivileges().equals("teach");
  }

  public static Boolean isAdmin(User user){
    return user.getPrivileges().equals("admin");
  }

  public static Boolean noErrors(HashMap results, List<String> potentialErrors){
    Boolean result = true;
    for (String errorMessage : potentialErrors){
      if(results.containsKey(errorMessage)){
        result=false;
        break;
      }

    }
    return result;
  }
  public static String genPW(){
    String pw = "KW";
    Random r = new Random();

    for (int i=0;i<12;i++){
      pw += (char)(r.nextInt(26) + 'a');
    }
    for (int i=0;i<5;i++){
      pw+=r.nextInt(9);
    }
    pw+="!";



    return pw;
  }
}