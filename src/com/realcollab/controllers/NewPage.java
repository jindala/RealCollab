package com.realcollab.controllers;

import java.net.UnknownHostException;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;

import com.realcollab.modal.DBUtil;
import com.realcollab.api.adaptors.OpenTokAdaptor;

public class NewPage {
	final static String PAGE_COLLECTION = "page";
	public synchronized String createNewPage(String invitorId, String[] invitees) throws UnknownHostException
	{
		System.out.println("invitor: " + invitorId);
		DBUtil dbUtil = new DBUtil();		
		
		BasicDBObject newPageDoc = new BasicDBObject();
		JSONArray inviteesArray = new JSONArray();
		if(invitees != null && invitees.length > 0) {
			for (String invitee: invitees) {
				if(invitee.equals(""))
					continue;
				JSONObject eachInviteeObj = new JSONObject();
				
				eachInviteeObj.put("yammer_id", invitee);
				eachInviteeObj.put("status", 0);
				inviteesArray.add(eachInviteeObj);
			}
		}
		
		// Get opentok session
		OpenTokAdaptor otAd = new OpenTokAdaptor();
		String sessionId = otAd.getNewSessionId();
		String token = otAd.getNewToken(sessionId);
		
		String realCollabId = invitorId.concat(Long.toString(System.currentTimeMillis()));
		
		newPageDoc.put("realCollabId", realCollabId);
		newPageDoc.put("ot_sessionId", sessionId);
		newPageDoc.put("ot_token", token);
		newPageDoc.put("invitee", inviteesArray);
		newPageDoc.put("invitor", invitorId);
		
		String id = null;
		try {
			id = dbUtil.insertintoDB(PAGE_COLLECTION, newPageDoc);
		} catch (UnknownHostException e) {
			throw e;
		}
		return realCollabId;
	}
	
	public JSONObject getPageInfo(String realCollabId) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("realCollabId", realCollabId);
		DBUtil dbUtil = new DBUtil();
		
		return dbUtil.queryDocs(PAGE_COLLECTION, queryObj);
	}
	
	public JSONObject getPageInfoFromId(String id) throws UnknownHostException
	{
		BasicDBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		DBUtil dbUtil = new DBUtil();
		
		return dbUtil.queryDocs(PAGE_COLLECTION, queryObj);
	}

}
