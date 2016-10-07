package com.test.autobot.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FunctionService {
	
	public String nearestStore(String city) {
		try {
			URL obj = new URL("http://bot.lenskart.com:8083/franchise/details/"+city);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) { // success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            String status = response.toString();
	            if(status!=null){
	            	return status;
	            } else {
	            	return "No Store found";
	            }
	        } else {
	        	return "Order Status Not available";
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "No Store found";
	}
	
	public String trackOrder(String orderId) {
		try {
			URL obj = new URL("http://bot.lenskart.com:8083/track_order/"+orderId);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) { // success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            String status = response.toString();
	            if("Order Not Yet Dispatched".equalsIgnoreCase(status)) {
	            	return "Order Not Yet Dispatched";
	            } else if("No Such Order Found".equalsIgnoreCase(status)){
	            	return "No Such Order Found";
	            } else{
	            	return "Your Order tracking url is " + status;
	            }
	        } else {
	        	return "Order Tracking URL Not available";
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Order Tracking URL Not available";
	}
	
	public String dispatchDate(String orderId) {
		try {
			URL obj = new URL("http://bot.lenskart.com:8083/order/dispatch_date/"+orderId);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) { // success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            String status = response.toString();
	            if("No Data Available".equalsIgnoreCase(status)) {
	            	return "No Data Available";
	            } else {
	            	return "Your Order expected dispatch date is " + status;
	            }
	        } else {
	        	return "Your Order dispatch date Not available";
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Your Order dispatch dateNot available";
	}
	
	public String deliveryDate(String orderId) {
		try {
			URL obj = new URL("http://bot.lenskart.com:8083/order/delivery_date/"+orderId);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) { // success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            String status = response.toString();
	            if("No Data Available".equalsIgnoreCase(status)) {
	            	return "No Data Available";
	            } else {
	            	return "Your Order expected delivery date is " + status;
	            }
	        } else {
	        	return "Your Order delivery date Not available";
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Your Order dispatch dateNot available";
	}
	
	
	public String storeCredit(String email, Integer hashCode) {
		return "You have Rs. 2000/- available in your account.";
	}
	
	public String getOrderStatus(String incrementId) {
		try {
			URL obj = new URL("http://bot.lenskart.com:8083/"+incrementId+"/status");
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) { // success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            String status = response.toString();
	            return "Your Order status is " + status;
	        } else {
	        	return "Order Status Not available";
	        }
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return "Order Status Not available";
	}
}
