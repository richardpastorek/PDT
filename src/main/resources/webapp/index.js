(function () {
    mapboxgl.accessToken = 'pk.eyJ1IjoicmljaGFyZHBhc3RvcmVrIiwiYSI6ImNpdnpjYWFraTAwMTkyb3BqZHh3aW90bHgifQ.qgvDdvEkPwOVQVZsYNt2XQ';
    var map = new mapboxgl.Map({
        container: 'map', // container id
        style: 'mapbox://styles/mapbox/streets-v9', //stylesheet location
        center: [-0.088551, 51.491029], // starting position
        zoom: 6 // starting zoom

    });





// Holds mousedown state for events. if this
// flag is active, we move the point on `mousemove`.
var isDragging;

// Is the cursor over a point? if this
// flag is active, we listen for a mousedown event.
var isCursorOverPoint;

var canvas = map.getCanvasContainer();
var coordinates = document.getElementById('coordinates');


map.on('click', function (e) {
    var features = map.queryRenderedFeatures(e.point, { layers: ['crashSites1'] });

    if (features.length) {
         var feature = features[0];

            // Populate the popup and set its coordinates
            // based on the feature found.
            var popup = new mapboxgl.Popup()
                .setLngLat(feature.geometry.coordinates)
                .setHTML("Datum: " + feature.properties.date+
                "<br>Pocet aut: " +feature.properties.auta+
                "<br>Pocet obeti: "+ feature.properties.obete)
                .addTo(map);
    }


});


var geojson = {
    "type": "FeatureCollection",
    "features": [{
        "type": "Feature",
        "geometry": {
            "type": "Point",
            "coordinates": [-1.6, 52.64]
        }
    }]
};

function mouseDown() {
    if (!isCursorOverPoint) return;

    isDragging = true;

    // Set a cursor indicator
    canvas.style.cursor = 'grab';

    // Mouse events
    map.on('mousemove', onMove);
    map.on('mouseup', onUp);
}

function onMove(e) {
    if (!isDragging) return;
    var coords = e.lngLat;

    // Set a UI indicator for dragging.
    canvas.style.cursor = 'grabbing';

    // Update the Point feature in `geojson` coordinates
    // and call setData to the source layer `point` on it.
    geojson.features[0].geometry.coordinates = [coords.lng, coords.lat];
    map.getSource('point').setData(geojson);
}
 var coords;
function onUp(e) {
    if (!isDragging) return;
    coords = e.lngLat;

    // Print the coordinates of where the point had
    // finished being dragged to on the map.
    /*coordinates.style.display = 'block';
    coordinates.innerHTML = 'Longitude: ' + coords.lng + '<br />Latitude: ' + coords.lat;*/
    canvas.style.cursor = '';
    isDragging = false;


    $('#textFieldX').val(coords.lng);
    $('#textFieldY').val(coords.lat);

}

map.on('load', function() {


      //Add regions to the map
    $.ajax({

        url: "http://localhost:4567/showregions",
        success: function (data) {

            map.addSource("regions", jQuery.parseJSON(data));

               map.addLayer({
                   "id": "regions",
                   "type": "line",
                   "source": "regions",
                   "filter": ["!=", "$type", "Point"]
               })




               map.addLayer({
                  "id": "regionsNames",
                  "type": "symbol",
                  "source": "regions",
                  "minzoom": 7,

                  "layout": {
                         "visibility": "none",
                         "text-field": "{title}",
                         "text-font": ["Open Sans Semibold", "Arial Unicode MS Bold"],
                         "text-offset": [0, 0.6],
                         "text-anchor": "top",

                         "text-allow-overlap": true

                  },
                  "filter": ["==", "$type", "Point"]
              })

        }
    });


    // Add a single point to the map
    map.addSource('point', {
        "type": "geojson",
        "data": geojson
    });

    map.addLayer({
        "id": "point",
        "type": "circle",
        "source": "point",
        "paint": {
            "circle-radius": 10,
            "circle-color": "#3887be"
        }
    });

    // If a feature is found on map movement,
    // set a flag to permit a mousedown events.
    map.on('mousemove', function(e) {
        var features = map.queryRenderedFeatures(e.point, { layers: ['point'] });

        // Change point and cursor style as a UI indicator
        // and set a flag to enable other mouse events.
        if (features.length) {
            map.setPaintProperty('point', 'circle-color', '#3bb2d0');
            canvas.style.cursor = 'move';
            isCursorOverPoint = true;
            map.dragPan.disable();
        } else {
            map.setPaintProperty('point', 'circle-color', '#3887be');
            canvas.style.cursor = '';
            isCursorOverPoint = false;
            map.dragPan.enable();
        }
    });

    // Set `true` to dispatch the event before other functions call it. This
    // is necessary for disabling the default map dragging behaviour.
    map.on('mousedown', mouseDown, true);
});



    $("#buttonCrashesByRegion").click(function (e) {

                $.ajax({

                    url: "http://localhost:4567/showcrashbyregion?lon="+$('#textFieldX').val()+"&lat="+$('#textFieldY').val(),
                    success: function (data) {

                        showCrashes(data);
                        if(window.Notification && Notification.permission !== "denied") {
                        	Notification.requestPermission(function(status) {  // status is "granted", if accepted by user
                        		var n = new Notification('Request Completed', {
                        			body: 'Done!',


                        		});
                        	});
                        }
                    }
                });
        });

    $("#buttonCrashesByRoad").click(function (e) {
                        var map_bounds = map.getBounds();

                    $.ajax({

                        url: "http://localhost:4567/showcrashbyroad?lon="+$('#textFieldX').val()+"&lat="+$('#textFieldY').val()+"&rad="+$('#textFieldRoadWidth').val()+
                        "&sw="+map_bounds.getSouthWest().toArray()+"&ne="+map_bounds.getNorthEast().toArray(),
                        success: function (data) {

                            showCrashes(data);

                            if(window.Notification && Notification.permission !== "denied") {

                                                    	Notification.requestPermission(function(status) {  // status is "granted", if accepted by user
                                                    		new Notification('Request Completed', {
                                                    			body: 'Done!',


                                                    		});
                                                    	});

                                                    }
                        }
                    });


    });

    $("#checkboxRegionNames").click(function (e) {

                if($('#checkboxRegionNames:checked').val() == "") {

                    map.setLayoutProperty("regionsNames", "visibility", "visible");
                }else{
                    map.setLayoutProperty("regionsNames", "visibility", "none");
                }


        });

    $("#getCrashSitesButton1").click(function (e) {
                $.ajax({

                    url: "http://localhost:4567/showcrash?lon="+$('#textFieldX').val()+"&lat="+$('#textFieldY').val()+"&rad="+$('#textFieldRad').val(),
                    success: function (data) {

                        showCrashes(data);
                        if(window.Notification && Notification.permission !== "denied") {
                                                	Notification.requestPermission(function(status) {  // status is "granted", if accepted by user
                                                		var n = new Notification('Request Completed', {
                                                			body: 'Done!',


                                                		});
                                                	});
                                                }
                    }
                });



        });




    function showCrashes(data){

            if(map.getSource("crashSites1") != undefined){

                map.removeLayer("crashSites1");

                map.removeLayer("road");
                map.removeSource("crashSites1");
            }

           if(data=="")
               return;

               var testjson = jQuery.parseJSON(data);
                if(testjson.data.features == null){
                    return;
                }


               map.addSource("crashSites1", testjson);

               map.addLayer({
                   "id": "crashSites1",
                   "type": "symbol",
                   "source": "crashSites1",
                   "layout": {
                       "icon-image": "car-15",
                       "text-font": ["Open Sans Semibold", "Arial Unicode MS Bold"],
                       "text-offset": [0, 0.6],
                       "text-anchor": "top",
                       "icon-allow-overlap": true,
                       "text-optional": true
                   },
                   "filter": ["==", "$type", "Point"]




               })
               map.addLayer({
                  "id": "road",
                  "type": "fill",
                  "source": "crashSites1",
                  'paint': {
                      'fill-color': '#FFFF00',
                      'fill-opacity': 0.5
                  },
                  "filter": ["==", "$type", "Polygon"]




              })

       }

}
)();

