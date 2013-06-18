package com.realcollab.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.realcollab.controllers.NewPage;

/**
 * Servlet implementation class LoginServlet
 */
public class OTSessionTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OTSessionTokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String[]> reqParamMap = request.getParameterMap();
		String[] realCollabIdArray = reqParamMap.get("rcId");
		NewPage page = new NewPage();
		JSONObject responseJson = new JSONObject();
		if(realCollabIdArray != null) {
			responseJson = page.getPageInfo(realCollabIdArray[0]);
		}
		else if(reqParamMap.get("invitor") != null) {
			System.out.println("invitor defined");
			
			responseJson.put("realCollabId", page.createNewPage(reqParamMap.get("invitor")[0]));
		}
		
				
		System.out.println("response = " + responseJson);
				
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding("utf8");
		response.setContentType("application/json");   
		
		PrintWriter out = response.getWriter();
		out.print(responseJson);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		// Not Implemented
	}

}
