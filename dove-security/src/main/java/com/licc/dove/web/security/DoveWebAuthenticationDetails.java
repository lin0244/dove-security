package com.licc.dove.web.security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class DoveWebAuthenticationDetails extends WebAuthenticationDetails{

	/**
	 */
	private static final long serialVersionUID = 5254875350742064432L;

	private Map<String,Object> params = new HashMap<String,Object>();
	
	public DoveWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        getParamsFromRequest(request);
    }
	
	public Map<String,Object> getParams(){
		return params;
	}
	
	private void getParamsFromRequest(HttpServletRequest request){
		Map<String,String[]> properties = request.getParameterMap();
		Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator(); 
		Map.Entry<String, String[]> entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = entries.next(); 
			name = (String) entry.getKey(); 
			String[] valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			params.put(name, value); 
		}
	}
}
