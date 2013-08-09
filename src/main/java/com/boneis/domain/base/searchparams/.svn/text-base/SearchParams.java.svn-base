package com.boneis.domain.base.searchparams;

import com.boneis.domain.base.root.ValueObject;

public class SearchParams<T> extends ValueObject {

	protected String word;
	protected T option;

	// Constructor
	public SearchParams(){}
	public SearchParams(String word, T option){
		this.word = word;
		this.option = option;
	}
	
	// Behavior
	public boolean isWord(){
		return this.word!=null&&this.word.trim().length()>0;
	}
	
	// Get & Set
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public T getOption() {
		return option;
	}
	public void setOption(T option) {
		this.option = option;
	}
	
}
