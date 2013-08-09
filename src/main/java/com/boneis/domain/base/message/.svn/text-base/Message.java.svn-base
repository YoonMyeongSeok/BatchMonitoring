package com.boneis.domain.base.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.context.support.MessageSourceAccessor;

import com.boneis.domain.base.root.ValueObject;

public class Message extends ValueObject {

	private static MessageSourceAccessor messageSourceAccessor = null;
	protected String key;
	protected String param;//msg parameter
	protected String url;
	protected String params;//url parameters
	protected String tgp;
	protected String dataType;
	protected String sfn;
	protected List<Message> listMessage;
	
	// Constructor
	public Message() {
		this.key = "INFO_MSG_PROCESS_TRUE";
		this.url = "";
		this.param = "";
		this.tgp = "";
		this.dataType = "json";
		this.sfn = "";
	}
	public Message(String url, String sfn) {
		this();
		this.url = url;
		this.sfn = sfn;
	}
	public Message(String url, String params, String dataType) {
		this();
		this.url = url;
		this.params = params;
		this.dataType = dataType;
		if(this.dataType=="json"){
			this.sfn = "A.msg";	
		}else if(this.dataType=="html"){
			this.sfn = "A.html";
		}
	}
	public Message(String url, String params, String dataType, String sfn) {
		this();
		this.url = url;
		this.params = params;
		this.dataType = dataType;
		this.sfn = sfn;
	}
	
	// Behavior
	/**
     * KEY에 해당하는 메세지 반환
     * @param key
     * @return
     */
	@JsonIgnore
    public static String getMessage(String key) {
        return messageSourceAccessor.getMessage(key, Locale.getDefault());
    }
	
	/**
     * KEY에 해당하는 메세지 반환 (외부인터페이스 단순화용)
     * @param key
     * @param objs
     * @return
     */
	@JsonIgnore
    public static String getMessage(String key, String param) {
        return getMessage(key, new String[]{param});
    }
	
	/**
     * KEY에 해당하는 메세지 반환
     * @param key
     * @param objs
     * @return
     */
	@JsonIgnore
    public static String getMessage(String key, Object[] objs) {
		return messageSourceAccessor.getMessage(key, objs, Locale.getDefault());
    }
	
	@JsonIgnore
	public boolean isNext(){
		if(this.key.equals("INFO_MSG_PROCESS_TRUE"))
			return true;
		else 
			return false;
	}
	
	public String getDesc(){
		String msg = "";
		if("".equals(this.param)){
			msg = getMessage(this.key);
		}else{
			msg = getMessage(this.key, this.param);	
		}
		return msg;
	}
	
	public void setMsg(String key, String param){
		this.key = key;
		this.param = param;
	}
	
	public void setMsgAdd(Message message){
		if(listMessage==null)
			listMessage = new ArrayList<Message>();
		listMessage.add(message);
	}
	
	// Get & Set
	public void setMessageSourceAccessor(
			MessageSourceAccessor messageSourceAccessor) {
		Message.messageSourceAccessor = messageSourceAccessor;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getTgp() {
		return tgp;
	}
	public void setTgp(String tgp) {
		this.tgp = tgp;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getSfn() {
		return sfn;
	}
	public void setSfn(String sfn) {
		this.sfn = sfn;
	}
	public List<Message> getListMessage() {
		return listMessage;
	}
	public void setListMessage(List<Message> listMessage) {
		this.listMessage = listMessage;
	}
	
}
