(function($){
	
	A = new function(){
		
		var CI = this;
		
		/**
		 * CI 객체 초기화한다.
		 * 외부에서는 A로 사용한다.
		 */
		CI.init = function(){
			
		};
		
		/**
		 * console이나 alert으로 메시지를 출력한다.
		 * @param msg
		 */
		CI.log = function(msg){
			if(window.console){
				console.log(msg);
			}else{
				alert(msg);
			}
		};
		
		/**
		 * 아작스로 json을 얻어온다.
		 * @param url
		 * @param params
		 * @param sfn - 성공시 콜할 함수
		 */
		CI.getJson = function(url,params,sfn){
			if(!sfn){
				sfn = CI.msg;
			}
			CI.ajax(url,params,"json",sfn);
		};
		
		/**
		 * 아작스로 html를 얻어온다.
		 * @param url
		 * @param params
		 * @param sfn - 성공시 콜할 함수
		 */
		CI.getHtml = function(url,params,sfn){
			if(!sfn){
				sfn = CI.html;
			}
			CI.ajax(url,params,"html",sfn);
		};
		
		/**
		 * 아작스 호출
		 * @param url
		 * @param params
		 * @param datatype - 'json', 'html'등
		 * @param sfn - 성공시 호출할 함수
		 * @returns {CI} - 제이쿼리 체인닝
		 */
		CI.ajax = function(url,params,datatype,sfn){
			var tgp = "";
			if(!params){
				params = "";
			}
			if(!datatype){
				datatype = "json";
			}
			if(!sfn){
				if(datatype=="html"){
					sfn = CI.html;
				}else{
					sfn = CI.msg;	
				}
			}
			if(sfn==CI.html){
				if(params.tgp){
					tgp = params.tgp;
				}else{
					tgp = A.parseParams(params).tgp;
				}
				sfn = function(html){
					CI.html(html,tgp);
				};
			}
			$.ajax({
				url: url,
				data: params,
				type: "POST",
				dataType: datatype,
				async: true,
				success: sfn,
				error: function(jqXHR, textStatus, errorThrown){
					CI.log("ajax error : "+errorThrown);
				}
			});
			return this;
		};
		
		/**
		 * 대화상자 출력
		 * @param o - 옵션
		 * @param url
		 * @param params
		 * @param sfn - 성공시 호출할 함수
		 */
		CI.dlg = function(o,url,params,sfn){
			var d = {
					id:"",
					tb:"dialog",
					w:300,
					h:200,
					t:$(window).height()/2,
					l:$(window).width()/2
				},
				p = {tgp:""},
				$lim = $("<li></li>").addClass("behind").append('<a href="#"><i class="icon-download-alt icon-white"></i></a>'),
				$liM = $("<li></li>").addClass("behind").append('<a href="#"><i class="icon-fullscreen icon-white"></i></a>'),
				$liC = $("<li></li>").append('<a href="#"><i class="icon-remove-sign icon-white"></i></a>'),
				$ul = $("<ul></ul>").append($lim).append($liM).append($liC),
				$icons = $("<span></span>").append($ul),
				$title = $("<h4></h4>"),
				$titlebar = $("<div></div>").addClass("tb").append($title).append($icons),
				$content = $("<div></div>").addClass("ct"),
				$dialog = $("<div></div>").addClass("dialog").append($titlebar).append($content);
			
			$.extend(d,o||{});
			if(d.id==""){
				alert("잘못된 창 호출 입니다.");
			}else if($("#"+d.id).length>0){
				alert("이미 존재하는 창 입니다."+"[#"+d.id+"]");
			}else if($("#"+d.id).length==0){
				d.t = d.t-(d.h/2)+$(window).scrollTop();
				d.l = d.l-(d.w/2);
				$titlebar.css({cursor:"move"}).find("h4").text("◈ "+d.tb);
				$content.attr("id",d.id+"_ct").append("<div id='loading'><p><img src='/resources/img/common/loading.gif' /></p></div>");
				$content.find("#loading").css({paddingTop:d.h/2-30,paddingLeft:d.w/2-120});
				$dialog.attr("id",d.id).css({top:d.t,left:d.l,height:d.h,width:d.w}).appendTo("body").eDlg({duration:500});
				$.extend(p,params||{});
				p.tgp = d.id+"_ct";
				CI.getHtml(url,p,sfn);
			}
		};
		
		/**
		 * 폼의 값에 대한 정합성 체크
		 * @param f - 폼아이디
		 * @param s - 정합성 체크할 셀렉터
		 * @returns {Boolean} - 결과
		 */
		CI.validate = function(f,s){
			var i = 0,
				max = f.find(s).size(),
				$item = null,
				title = "";

			for(i=0;i<max;i++){
				$item = f.find(s).eq(i);
				if($.trim($item.val())==""){
					title = $item.attr("title");
					if(!title){
						title = $item.attr("name");
					}
					CI.showMsg("warning","warning &gt; "+title+"을(를) 입력하셔야 합니다.");
					alert("warning> "+title+"을(를) 입력하셔야 합니다.");
					$item.focus();
					return false;
				}
			}
			return true;
		};
		
		/**
		 * 알파벳, 숫자, 하이픈으로만 구성된 문자열인지 검증
		 * @param s
		 * @returns
		 */
		CI.isAlphaNumericHyphen = function(s){ 
			return /^[_A-Za-z0-9-]+$/.test(s); 
		};
		
		/**
		 * 페이지 하단 동작 메시지 출력
		 * @param t - success, warning, info, error 등
		 * @param m
		 */
		CI.showMsg = function(t,m){
			$("#divMessage").html("").append($("<div></div>").addClass("alert alert-"+t).append(m));
			$("#divMessage").find(">.alert").stop().fadeOut(10000);
		};
		
		/**
		 * 쿼리스트링을 객체로 변환
		 * @param querystring - 주소가 없는 쿼리스트링
		 * @returns {___anonymous4568_4569} - 결과 객체
		 */
		CI.parseParams = function(querystring){
			var re = /([^&=]+)=?([^&]*)/g;
			var decodeRE = /\+/g;  // Regex for replacing addition symbol with a space
			var decode = function (str) {return decodeURIComponent( str.replace(decodeRE, " ") );};
			var params = {}, e;
		    while ( e = re.exec(querystring) ) { 
		        var k = decode( e[1] ), v = decode( e[2] );
		        if (k.substring(k.length - 2) === '[]') {
		            k = k.substring(0, k.length - 2);
		            (params[k] || (params[k] = [])).push(v);
		        }
		        else params[k] = v;
		    }
		    return params;
		};
		
		CI.showEditor = function(selector){
			if(selector){
				if(CKEDITOR.instances[selector])
					delete CKEDITOR.instances[selector];
			    CKEDITOR.replace(selector,{enterMode : CKEDITOR.ENTER_BR});	
			}
		};
		
		CI.updateCK = function(){
			for ( instance in CKEDITOR.instances )
		        CKEDITOR.instances[instance].updateElement();
		};
		
		CI.imgError = function (img){
			img.onerror = "";
			img.src = "/resources/img/common/noimage.png";
		    return true;
		};

		/***********************/
		/* Call Back Function. */
		/***********************/
		
		/**
		 * 서버단의 메시지객체를 받아 케이스 별로 분기 처리
		 * 케이스1. 병렬분기
		 * 케이스2. 성공메시지출력
		 * 케이스3. 오류메시지출력
		 */
		CI.msg = function(json){
			var i = 0,
				max = 0,
				msg = json.msg,
				item = null;
			if(msg.listMessage&&msg.listMessage.length&&msg.listMessage.length>=0){
				max = json.msg.listMessage.length;
				for(i=0;i<max;i++){
					item = msg.listMessage[i];
					CI.ajax(item.url,item.params,item.dataType,eval(item.sfn));
					CI.log("CI.msg > url:"+item.url+", params:"+item.params+", dataType:"+item.dataType+", sfn:"+item.sfn);
				}
				$("#"+msg.tgp).remove();
				CI.showMsg("success","links &gt; "+max+"개의 링크가 "+msg.desc+"("+msg.tgp+")");
			}else if(msg.key=="INFO_MSG_PROCESS_TRUE"){
				$("#"+msg.tgp).remove();
				CI.showMsg("success","complete &gt; "+msg.desc+"("+msg.tgp+")");
			}else{
				alert(msg.desc);
				CI.showMsg("error","error &gt; "+msg.desc+")");
			}
		};
		
		/**
		 * 아작스로 호출된 화면을 페이지에 보여준다.
		 * @param html
		 * @param tgp - 보여줄 레이어 아이디
		 */
		CI.html = function(html,tgp){
			$("#"+tgp).html(html);
		};
		
	};
	
})(jQuery);