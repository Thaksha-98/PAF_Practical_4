package com;
import java.sql.*;

public class Student {
	
	public Connection connection(){
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/student", "root", "");
			
			System.out.print("Successfully Connected");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;		
	}
	
	public String insertStudentDetails(String name, String phone, String email, String address, String course) {
		String result = "";
		
		try {
			Connection con = connection();
			if(con == null) {
				return "Error while connecting to the Database";
			}
			
			String query = "INSERT INTO students(studentID,studentName,phone,email,address,course)" + " VALUES(?,?,?,?,?,?)";
			PreparedStatement preparedSt = con.prepareStatement(query);
			
			preparedSt.setInt(1, 0);
			preparedSt.setString(2, name);
			preparedSt.setString(3, phone);
			preparedSt.setString(4, email);
			preparedSt.setString(5, address);
			preparedSt.setString(6, course);
			
			preparedSt.execute();
			con.close();
			
			result = "Record has been Inserted Successfully";
		}catch(Exception e) {
			result = "Error while Inserting the Student details.";
			System.err.print(e.getMessage());
		}
		
		return result;
	}
	
	public String readStudentDetails() {
		String result = "";
		
		try {
			Connection con = connection();
			if(con == null) {
				return "Error while connecting to the Database";
			}
			
			result = "<br><table class='table table-striped table-dark'><thead><tr><th scope='col'>Student ID</th>"
					+ "<th scope='col'>Student Name</th><th scope='col'>Phone</th><th scope='col'>Email</th>"
					+ "<th scope='col'>Address</th><th scope='col'>Course</th>"
					+ "<th scope='col'>Update</th><th scope='col'>Remove</th></tr></thead>";
			
			String query = "SELECT * FROM students";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			result += "<tbody>";
			while(rs.next()) {
				String studentID = Integer.toString(rs.getInt("studentID"));
				String studentName = rs.getString("studentName");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String course = rs.getString("course");
				
				result += "<tr><th scope='row'>" + studentID + "</th>";
				result += "<td>" + studentName + "</td>";
				result += "<td>" + phone + "</td>";
				result += "<td>" + email + "</td>";
				result += "<td>" + address + "</td>";
				result += "<td>" + course + "</td>";
				
				result += "<td><form method='post' action='Students.jsp'>" 
						+ "<input name='btnGet' " + " type='submit' value='Edit' class='btn btn-success'></td>"
						+ "<input name='id' type='hidden' " + " value='" + studentID + "'>" + "</form></td>"
						+ "<td><form method='post' action='Students.jsp'>"
						+ "<input name='btnDelete' " + " type='submit' value='Delete' class='btn btn-danger'>" 
						+ "<input name='studentID' type='hidden' " + " value='" + studentID + "'>" + "</form></td></tr>";
			}
			result +="</tbody>";
			
			con.close();
			
			result += "</table>";
			
		}catch(Exception e) {
			result = "Error while Reading the Student details.";
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
	public String getStudentDetail(String studentID) {
		String result = "";
		
		try {
			Connection con = connection();
			if(con == null) {
				return "Error while connecting to the Database";
			}
			
			String query = "SELECT * FROM students WHERE studentID=?";
			PreparedStatement preparedSt = con.prepareStatement(query);
			preparedSt.setInt(1, Integer.parseInt(studentID));
			ResultSet rs = preparedSt.executeQuery();
			
			while(rs.next()) {
				result = "<form method='post' action='Students.jsp'>"
					    + " Student Name: <input name='stName' type='text'" + " value='" + rs.getString("studentName") + "' class='form-control'><br>"
						+ "	Phone: <input name='phone' type='text' pattern='[0-9]{10}'" + " value='" + rs.getString("phone") + "' class='form-control'><br>"
					    + " Email: <input name='email' type='email'" + " value='" + rs.getString("email") + "' class='form-control'><br>"
						+ " Address: <input name='address' type='text'" + " value='" + rs.getString("address") + "' class='form-control'><br>"
					    + " Course: <input name='course' type='text'" + " value='" + rs.getString("course") + "' class='form-control'><br>"
					    + "<form method='post' action='Students.jsp'>"
					    + "<input name='btnUpdate' type='submit' value='Save Change' class='btn btn-info'>"
					    + "<input name='stId' type='hidden' " + " value='" + studentID + "'>" + "</form><br>";
			}
			
			con.close();

		}catch(Exception e) {
			result = "Error while Geting the Student details.";
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
	public String updateStudentDetail(String id, String name, String phone, String email, String address, String course) {
		String result = "";
		
		try {
			Connection con = connection();
			if(con == null) {
				return "Error while connecting to the Database";
			}
			
			String query = "UPDATE students SET studentName=?, phone=?, email=?, address=?, course=? WHERE studentID=?";
			PreparedStatement preparedSt = con.prepareStatement(query);
			
			preparedSt.setString(1, name);
			preparedSt.setString(2, phone);
			preparedSt.setString(3, email);
			preparedSt.setString(4, address);
			preparedSt.setString(5, course);
			preparedSt.setInt(6, Integer.parseInt(id));

			preparedSt.execute();
			con.close();
			
			result = "Record has been Updated Successfully";
		}catch(Exception e) {
			result = "Error while Updating the Student details.";
			System.err.println(e.getMessage());
		}
		
		return result;
	}

	public String deleteStudentDetail(String studentID) {
		String result = "";
		
		try {
			Connection con = connection();
			if(con == null) {
				return "Error while connecting to the Database";
			}
			
			String query = "DELETE FROM students WHERE studentID=?";
			PreparedStatement preparedSt = con.prepareStatement(query);
			preparedSt.setInt(1, Integer.parseInt(studentID));
			preparedSt.execute();
			
			con.close();
			
			result = "Record has been Deleted Successfully";
		}catch(Exception e) {
			result = "Error while Deleting the Student detail.";
			System.err.print(e.getMessage());
		}
		
		return result;
	}
}
