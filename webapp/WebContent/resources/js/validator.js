$(document).ready(function() {
	
	jQuery.validator.addMethod("greaterThan", 
	function(value, element, params) {
		console.log($(params).val());
		console.log(value);
		if(value!="" && $(params).val()!=""){
			return new Date(value) > new Date($(params).val());
		}else{
			return true;
		}
	});

	
	$("#addComputer").validate({
		rules: {
			computerName : {
				required: true,
				minlength: 2
			},
			introduced: {
				required: false
			},
			discontinued:{
				greaterThan: "#introduced" 
			}
		},
		messages : {
			computerName: {
				minlength: "Name should be at least 2 characters"
			},
			discontinued: {
				greaterThan: "Date of dicontinuation need to be after the introduction"
			}
		}
	});
}); 