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
		
		<script src="https://code.highcharts.com/highcharts.js"></script>
		<script src="https://code.highcharts.com/modules/series-label.js"></script>
		<script src="https://code.highcharts.com/modules/exporting.js"></script>
		
		
		<?php include("preloader.php"); ?>
		<script>
			var mainBody = $("#mainResult table > tbody");
			var mainHead = $("#mainResult table > thead");
			
			var preloader = $("#preloader");
			function setTable(state) {
				$("#mainResult").toggle(state);
			}
			function setGraph(state) {
				$("#graphResult").toggle(state);
			}
			function setPreloader(state) {
				console.log("state: " + state);
				if (state) {
					preloader.fadeIn('slow');
				} else {
					preloader.fadeOut('slow');
				}
			}
			
			$("form#search").submit(function(e) {
				alert($("form#search input[name='request']").val());
				return false;
			});
			
			
			/*
			 *	Xenoseus state machine
			 */
			var STATE_NONE = -1;
			var STATE_SHOPS = 0;
			var STATE_SEASONS = 1;
			var STATE_PRODUCTS = 2;
			var STATE_GRAPH = 3;
			var STATE_OPERATIONS = 4;
			var STATE_PRODUCTS_GRAPH = 5;
			
			var state = STATE_NONE;
			setState(STATE_NONE);
			
			var paginationPage = -1;
			var paginationWaitingResponse = false;
			
			/*
			 * Вспомогательная функция для автоподгрузки данных
			 */
			function waitPagination() {
				paginationWaitingResponse = true;
				paginationPage++;
				switch (state) {
					case STATE_SHOPS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/shops/" + paginationPage,
							success: function (data) {
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.name
										+ "</td><td></td></tr>"
									);
								});
								
								if (data.length == 0)
									paginationPage = -1;
								paginationWaitingResponse = false;
							}
						});
						break;
					case STATE_SEASONS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/seasons/" + paginationPage,
							success: function (data) {
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.name
										+ "</td><td></td></tr>"
									);
								});
								
								if (data.length == 0)
									paginationPage = -1;
								paginationWaitingResponse = false;
							}
						});
						break;
					case STATE_PRODUCTS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/products/" + paginationPage,
							success: function (data) {
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.season
										+ "</td><td>"
										+ obj.model
										+ "</td><td>"
										+ obj.color
										+ "</td><td>"
										+ obj.size
										+ "</td><td></td></tr>"
									);
								});
								
								if (data.length == 0)
									paginationPage = -1;
								paginationWaitingResponse = false;
							}
						});
						break;
					case STATE_OPERATIONS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/operations/" + paginationPage,
							success: function (data) {
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.order
										+ "</td><td>"
										+ obj.date
										+ "</td><td>"
										+ obj.shop
										+ "</td><td>"
										+ obj.model
										+ "</td><td>"
										+ obj.color
										+ "</td><td>"
										+ obj.size
										+ "</td><td>"
										+ obj.season
										+ "</td><td>"
										+ obj.count
										+ "</td><td>"
										+ obj.total
										+ "</td><td></td></tr>"
									);
								});
								
								if (data.length == 0)
									paginationPage = -1;
								paginationWaitingResponse = false;
							}
						});
						break;
					default:
						paginationPage = -1;
						paginationWaitingResponse = false;
						break;
				}
			}
			
			/*
			 * Смена состояния state машины
			 */
			function setState(newState) {
				state = newState;
				setTable(false);
				setGraph(false);
				setPreloader(state != STATE_NONE);
				
				switch (state) {
					case STATE_SHOPS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/shops/0",
							success: function (data) {
								setTable(true);
								
								mainHead.html("<tr><th>id</th><th>Имя</th><th>Действие</th></tr>");
								mainBody.html('');
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.name
										+ "</td><td></td></tr>"
									);
								});
								setPreloader(false);
								
								paginationPage = data.length == 0 ? -1 : 0;
								paginationWaitingResponse = false;
							}
						});
						break;
					case STATE_SEASONS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/seasons/0",
							success: function (data) {
								setTable(true);
								
								mainHead.html("<tr><th>id</th><th>Имя</th><th>Действие</th></tr>");
								mainBody.html('');
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.name
										+ "</td><td></td></tr>"
									);
								});
								setPreloader(false);
								
								paginationPage = data.length == 0 ? -1 : 0;
								paginationWaitingResponse = false;
							}
						});
						break;
					case STATE_PRODUCTS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/products/0",
							success: function (data) {
								setTable(true);
								
								mainHead.html("<tr><th>id</th><th>Сезон</th><th>Модель</th><th>Цвет</th><th>Размер</th><th>Действие</th></tr>");
								mainBody.html('');
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.season
										+ "</td><td>"
										+ obj.model
										+ "</td><td>"
										+ obj.color
										+ "</td><td>"
										+ obj.size
										+ "</td><td></td></tr>"
									);
								});
								setPreloader(false);
								
								paginationPage = data.length == 0 ? -1 : 0;
								paginationWaitingResponse = false;
							}
						});
						break;
					case STATE_GRAPH:
						stateGraphProcess("graph");
						break;
					case STATE_PRODUCTS_GRAPH:
						stateGraphProcess("productsGraph");
						break;
					case STATE_OPERATIONS:
						$.ajax({
							method: "GET",
							url: "http://127.0.0.1:8080/operations/0",
							success: function (data) {
								setTable(true);
								
								mainHead.html("<tr><th>id</th><th>Заказ</th><th>Дата</th><th>Магазин</th><th>Модель</th><th>Цвет</th><th>Размер</th><th>Сезон</th><th>Количество</th><th>Сумма</th><th>Действие</th></tr>");
								mainBody.html('');
								data.forEach(function(obj) {
									mainBody.append("<tr><td>"
										+ obj.id
										+ "</td><td>"
										+ obj.order
										+ "</td><td>"
										+ obj.date
										+ "</td><td>"
										+ obj.shop
										+ "</td><td>"
										+ obj.model
										+ "</td><td>"
										+ obj.color
										+ "</td><td>"
										+ obj.size
										+ "</td><td>"
										+ obj.season
										+ "</td><td>"
										+ obj.count
										+ "</td><td>"
										+ obj.total
										+ "</td><td></td></tr>"
									);
								});
								setPreloader(false);
								
								paginationPage = data.length == 0 ? -1 : 0;
								paginationWaitingResponse = false;
							}
						});
						break;
				}
			}
			
			function stateGraphProcess(addr) {
				$.ajax({
					method: "GET",
					url: "http://127.0.0.1:8080/" + addr,
					success: function (data) {
						generateChart(data);
						setGraph(true);
						setPreloader(false);
						paginationPage = -1;
					}
				});
			}
			
			
			function generateChart(obj) {
				functions = [];
				obj.functions.forEach(function(func) {
					functions.push(func);
				});
				
				Highcharts.chart('graphResultGraph', {
					chart: {
						type: 'spline'
					},
					title: {
						text: 'Пример графика'
					},
					subtitle: {
						text: 'Описание графика'
					},
					xAxis: {
						categories: obj.points,
					},
					yAxis: {
						title: {
							text: obj.leftLabel
						},
						labels: {
							formatter: function () {
								return this.value + obj.suffix;
							}
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {
							marker: {
								radius: 4,
								lineColor: '#777777',
								lineWidth: 1
							}
						}
					},
					series: functions
				});
			}
			
			window.onscroll = function(e) {
				if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100) {
					if (paginationPage != -1 && !paginationWaitingResponse) {
						waitPagination();
					}
				}
			};
		</script>
   </body>
</html>