package com.boneis.domain.base.root;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.boneis.domain.base.message.Message;
import com.boneis.domain.base.paging.Paging;
import com.boneis.domain.base.searchparams.SearchParams;

public abstract class Domain {
	
	private String NAMESPACE;
	private String findmode;
	private Message message;					// 메시지
	private Paging paging;						// 페이징
	private SearchParams<?> searchparams;		// 검색 파라미터
	
	// Behavior
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
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
