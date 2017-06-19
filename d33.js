$('document').ready(function(){
	d3.select("#drawings").append("p").style("color", "black").text("This text is writeen using d3.js");
	
	d3.select("#drawings").append("p").style("color", "black").text("Circel Animation using transition");
	var canvas = d3.select("#drawings").append("svg").attr("width", 500).attr("height",500);
	var circle = canvas.append("circle").attr("cx",250).attr("cy",250).attr("r",50).attr("fill", "hotpink");	
	var line = canvas.append("line").attr("x1",0).attr("y1",0).attr("x2",500).attr("y2",500).attr("stroke","blue").attr("stroke-width", 5);
	var line = canvas.append("line").attr("x1",500).attr("y1",0).attr("x2",0).attr("y2",500).attr("stroke","blue").attr("stroke-width", 5);
	circle.transition().
			delay(1000)
			.duration(1500)
			.attr("r", 250)
			.transition()
			.attr("r", 25)
			.transition()
			.attr("cx", 0)
			.transition()
			.attr("cx", 500)
			.transition()
			.attr("cx", 250)
			.transition()
			.attr("r", 250);
			
	

	d3.select("#graphs").append("p").style("color", "black").text("Bar graph");
	var dataArray = [20,30,50,60];
	var width = 500;
	var height = 500;
	
 	var widthScale = d3.scale.linear()
						.domain([0,60])
						.range([0,width]);
						
	var colors = d3.scale.linear()
						.domain([0,60])
						.range(["red", "green"]); 
						
	var axis = d3.svg.axis()
				.scale(widthScale);
				
	var canvas1 = d3.select("#graphs").append("svg")
					.attr("width", width)
					.attr("height",height)
					.append("g")
					.attr("transform", "translate(50, 50)");
					
						
	var bars = canvas1.selectAll("rect")
		.data(dataArray)
		.enter()
			.append("rect")
			.attr("width", function(d){ return widthScale(d);} )
			.attr("height", 50)
			.attr("fill", function(d){return colors(d)})
			.attr("y", function(d, i){return i*100 });
			
			
	canvas1.append("g")
			.attr("transform", "translate(0, 400)")
			.call(axis); 
			
		
		
	var widthScale1 = d3.scale.linear()
						.domain([0,40])
						.range([0,400]);	
	
	var axis1 = d3.svg.axis()
				.orient("left")
				.scale(widthScale1);
	
	
						
						
	d3.json("mydata.json", function(data){
		
		
		
		var canvas3 = d3.select("#arr").append("svg")
					.attr("width", width)
					.attr("height",height)
					.append("g")
					.attr("transform", "translate(50, 50)");
					
					
		canvas3.selectAll("rect")
				.data(data)
				.enter()
					.append("rect")
					.attr("width", 40 )
					.attr("height", function(d){return d.age*10})
					.attr("x", function(d, i){return i*80 })
					.attr("fill", "blue");
					
					
					
		canvas3.selectAll("text")
				.data(data)
				.enter()
					.append("text")
					.attr("fill", "black" )
					.attr("x", function(d, i){return i*80 - 8})
					.text(function(d){return d.name});	

		canvas3.append("g")	
				.attr("transform", "translate(0, 0)")
				.call(axis1); 					

				
	});
	
	
	
	var canvas4 = d3.select("#paths").append("svg")
					.attr("width", width)
					.attr("height",height);
					
	
	var data1 = [
					{x:10,y:10},
					{x:10, y:300},
					{x:400, y:300},
					{x:400, y:10},
					{x:5,y:10}
					];
					
	var group = canvas4.append("g")
						.attr("transform", "translate(100,100)");
	var line = d3.svg.line()
				.x(function(d){return d.x;})
				.y(function(d){return d.y;});
	
	canvas4.selectAll("path")
						.data([data1])
						.enter()
							.append("path")
							.attr("d", line)
							.attr("fill", "none")
							.attr("stroke", "#000")
							.attr("stroke-width", 10)
	
	
	
	
	
	
	
	
	})	 