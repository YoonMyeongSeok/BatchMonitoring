package com.boneis.domain.base.root;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.boneis.domain.base.message.Message;
import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.searchparams.SearchParams;

public abstract class Domain {
	
	private String NAMESPACE;
	private String findmode;
	private Message message;
	private Paging paging;
	private SearchParams<?> searchparams;
	
	// Behavior
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
		
		/*
		Method[] arrMethod = this.getClass().getMethods();
		StringBuffer sb = new StringBuffer(this.getClass().toString());
		sb.append(" => [");
		Object[] args = null;
		
		try {
			for(Method m : arrMethod) {
				if(m.getName().startsWith("get") && !m.getName().equals("getClass")
				&& !m.getName().equals("getListCount") && !m.getName().equals("getInfo")
				&& !m.getName().equals("getList")&& !m.getName().equals("getRepository")) { 
					sb.append(m.getName());
					sb.append(": ");
					sb.append(m.invoke(this, args));
					sb.append(", ");
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return sb.toString().substring(0, sb.length() - 2)+"]";
		*/
	}
	
	// Get & Set
	public String getNAMESPACE() {
		return NAMESPACE;
	}
	public String getFindmode() {
		return findmode;
	}
	public void setFindmode(String findmode) {
		this.findmode = findmode;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public SearchParams<?> getSearchparams() {
		return searchparams;
	}
	public void setSearchparams(SearchParams<?> searchparams) {
		this.searchparams = searchparams;
	}

}
