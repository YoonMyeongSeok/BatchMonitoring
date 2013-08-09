package com.boneis.domain.base.paging;

import javax.servlet.http.HttpServletRequest;

import com.boneis.domain.base.root.ValueObject;
import com.boneis.support.util.Util;

public class Paging extends ValueObject {
	private long nowpage;
	private int pagesize;
	private int listsize;
	private long totalrow;
	private long limitstart;
	
	// Static Constant
	public static final int LISTSIZE = 20;
	public static final int PAGESIZE = 10;
	
	// Constructor ..................................
	public Paging(){}
	public Paging(HttpServletRequest request){
		this.nowpage = Util.parseLong(request.getParameter("np"),1);
		this.pagesize = Paging.PAGESIZE;
		this.listsize = Paging.LISTSIZE;
		this.limitstart = (this.nowpage-1)*this.listsize;
	}
	public Paging(HttpServletRequest request, int pagesize, int listsize){
		this.nowpage = Util.parseLong(request.getParameter("np"),1);
		this.pagesize = pagesize;
		this.listsize = listsize;
		this.limitstart = (this.nowpage-1)*this.listsize;
	}
	
	// Behavior ........................................
	public String getList(long totalrow, String name) {
		long nTotalPages, nStartPage, nEndPage;
		String sResultPaging = " | ";
		this.totalrow = totalrow;
		
		if( this.totalrow%this.listsize>0 )
			nTotalPages = (this.totalrow/this.listsize)+1;
		else
			nTotalPages = (this.totalrow/this.listsize)+0;
		nStartPage = ((this.nowpage-1) / this.pagesize) * this.pagesize + 1;
		nEndPage = nStartPage + (this.pagesize - 1);
		if( nEndPage>nTotalPages ) 
			nEndPage = nTotalPages;
		
		if( nStartPage>1 )
			sResultPaging = "<a class=\"btnPage"+name+"\" np=\""+(nStartPage-1)+"\" title=\""+(nStartPage-1)+"페이지로\">이전</a> | ";
		for( long idx=nStartPage;idx<=nEndPage;idx++ ){
			if( idx==this.nowpage )
				sResultPaging += "<a class=\"btnPage"+name+"\" np=\""+idx+"\" title=\""+idx+"페이지로\"><b>" + idx + "</b></a> | ";
			else
				sResultPaging += "<a class=\"btnPage"+name+"\" np=\""+idx+"\" title=\""+idx+"페이지로\">" + idx + "</a> | ";
		}
		if( nTotalPages>nEndPage )
			sResultPaging += "<a class=\"btnPage"+name+"\" np=\""+(nEndPage+1)+"\" title=\""+(nEndPage+1)+"페이지로\">다음</a>";
		if( !(sResultPaging.length()>0) ) {
			sResultPaging = "1";
		}

		return sResultPaging;
	}
	
	// Get & Set .............................................
	public long getNowpage() {
		return nowpage;
	}
	public void setNowpage(long nowpage) {
		this.nowpage = nowpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getListsize() {
		return listsize;
	}
	public void setListsize(int listsize) {
		this.listsize = listsize;
	}
	public long getTotalrow() {
		return totalrow;
	}
	public void setTotalrow(long totalrow) {
		this.totalrow = totalrow;
	}
	public long getLimitstart() {
		return limitstart;
	}
	public void setLimitstart(long limitstart) {
		this.limitstart = limitstart;
	}
}
