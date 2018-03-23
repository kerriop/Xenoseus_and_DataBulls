<!DOCTYPE html>
<html lang="en" style="" class="js flexbox flexboxlegacy canvas canvastext webgl no-touch geolocation postmessage websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections csstransforms csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
		<title>Xenoseus</title>
		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="css/font-awesome.min.css">
		<link rel="stylesheet" href="css/animate+animo.css">
		<link rel="stylesheet" href="css/csspinner.min.css">
		<link rel="stylesheet" href="css/app.css">
		<script src="js/modernizr.js" type="application/javascript"></script>
		<script src="js/fastclick.js" type="application/javascript"></script>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
	</head>
	<body>
		<section class="wrapper">
			<?php include("navigation.php"); ?>
			<?php include("sidebar.php"); ?>
			<section>
				<section class="main-content">
					<?php include("content.php"); ?>
				</section>
			</section>
		</section>
		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/chosen.jquery.min.js"></script>
		<script src="js/bootstrap-slider.js"></script>
		<script src="js/bootstrap-filestyle.min.js"></script>
		<script src="js/animo.min.js"></script>
		<script src="js/jquery.sparkline.min.js"></script>
		<script src="js/jquery.slimscroll.min.js"></script>
		<script src="js/app.js"></script>
		<?php include("preloader.php"); ?>
		<script>
			var mainResult = $("#mainResult > tbody");
			var mainHead = $("#mainResult > thead");
			function setTable(state) {
				$("#mainResult").toggle(state);
			}
			function setPreloader(state) {
				if (state) {
					$('#preloader').fadeIn('slow', function() {});
				} else {
					$('#preloader').fadeOut('slow', function() {});
				}
			}
			setTable(false);
			setPreloader(false);
			
			$("form#search").submit(function(e) {
				console.log($("form#search input[name='request']"));
				alert($("form#search input[name='request']").val());
				return false;
			});
			
			
			/*
				Xenoseus state machine
			*/
			var STATE_NONE = -1;
			var STATE_SHOPS = 0;
			var state = STATE_NONE;
			
			var args = [];
			
			
			function setState(newState) {
				state = newState;
				setTable(false);
				setPreloader(true);
				
				switch (state) {
					case STATE_SHOPS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/test",
							success: function (data) {
								setTable(true);
								
								mainHead.html("<tr><th>id</th><th>Имя</th><th>Действие</th></tr>");
								mainResult.html('');
								data.forEach(function(obj) {
									mainResult.append("<tr><td>" + obj.id + "</td><td>" + obj.name + "</td><td></td></tr>");
									setPreloader(false);
								});
							}
						});
						break;
				}
			}
		</script>
   </body>
</html>