/*** In The Name of Allah ***/
package com.scut.mplas.graphs.cfg;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import com.scut.mplas.graphs.pdg.PDNode;

/**
 * Class type of Control Flow (CF) nodes.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class CFNode {
	
	private Map<String, Object> properties;
	
	public CFNode() {
		properties = new LinkedHashMap<>();
	}
	
	public void setLineOfCode(int line) {
		properties.put("line", line);
	}
	
	public int getLineOfCode() {
		return (Integer) properties.get("line");
	}
	
	public void setCode(String code) {
		properties.put("code", code);
	}
	
	public String getCode() {
		return (String) properties.get("code");
	}
    
    public PDNode getPDNode() {
        return (PDNode) getProperty("pdnode");
    }
	
	public void setProperty(String key, Object value) {
		properties.put(key.toLowerCase(), value);
	}
	
	public Object getProperty(String key) {
		return properties.get(key.toLowerCase());
	}
	
	public Set<String> getAllProperties() {
		return properties.keySet();
	}
	
	@Override
	public String toString() {
		return (Integer) properties.get("line") + ": " + 
				(String) properties.get("code");
	}
}
