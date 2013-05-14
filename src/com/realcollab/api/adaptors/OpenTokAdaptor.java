package com.realcollab.api.adaptors;

import com.opentok.api.*;
import com.opentok.api.constants.RoleConstants;
import com.opentok.exception.OpenTokException;



public class OpenTokAdaptor {
	
	public String getNewSessionId() {
		OpenTokSDK sdk = new OpenTokSDK(API_Config.API_KEY, API_Config.API_SECRET);
		OpenTokSession session = null;
		try {
			session = sdk.create_session();
		} catch (OpenTokException e) {
			e.printStackTrace();
			return null;
		}
		
		return session.session_id;
	}
	
	public String getNewToken(String sessionId) {
		OpenTokSDK sdk = new OpenTokSDK(API_Config.API_KEY, API_Config.API_SECRET);

        String token;
		try {
			String connectionMetadata = "username=Bob,userLevel=4";

	        token = sdk.generate_token(sessionId, RoleConstants.PUBLISHER, null, connectionMetadata);
		} catch (OpenTokException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        System.out.println("opentok token: " + token);
        
        return token;
	}
	
}
