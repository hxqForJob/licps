/**
 * 
 */
	
	     function isOnlyChecked(){
	    	 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
			//jquery
			//var count = $("[input name='id']:checked").size();
			if(count==1)
				return true;
			else
				return false;
	     }
	     function toView(url){
	    	 if(isOnlyChecked()){
	    		 //var id = $("[input name='id']:checked").val();
	    		
	    		formSubmit(url,'_self');
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
	    	 }
	     }
	     //实现更新
	     function toUpdate(url){
	    	 if(isOnlyChecked()){
	    		
	    		// var id = $("[input name='id']:checked").val();
	    		 formSubmit(url,'_self');
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
	    	 }
	     }
	     //实现删除
	     function deleteDept(a,url) {
	    	 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
				if(count<=0)
					{
						alert("请先选择一项删除,再进行操作！")
						return;
					}
	    	 var flag=window.confirm("确认删除？");
	    	 if(flag)
	    		 {
	    		 formSubmit(url,'_self');
		    	 a.blur();	
	    		 }
	    	
		}
	     
	     //实现取消
	     function cancelDept(a,url) {
	    	 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
				if(count<=0)
					{
						alert("请先选择一项取消,再进行操作！")
						return;
					}
	    	 var flag=window.confirm("确认取消？");
	    	 if(flag)
	    		 {
	    		 formSubmit(url,'_self');
		    	 a.blur();	
	    		 }
	    	
		}
	     
	   //实现提交
	     function submitDept(a,url) {
	    	 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
				if(count<=0)
					{
						alert("请先选择一项提交,再进行操作！")
						return;
					}
	    	 var flag=window.confirm("确认提交？");
	    	 if(flag)
	    		 {
	    		 formSubmit(url,'_self');
		    	 a.blur();	
	    		 }
	    	
		}
	     
