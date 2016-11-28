# General course assignment

Build a map-based application, which lets the user see geo-based data on a map and filter/search through it in a meaningfull way. Specify the details and build it in your language of choice. The application should have 3 components:

1. Custom-styled background map, ideally built with [mapbox](http://mapbox.com). Hard-core mode: you can also serve the map tiles yourself using [mapnik](http://mapnik.org/) or similar tool.
2. Local server with [PostGIS](http://postgis.net/) and an API layer that exposes data in a [geojson format](http://geojson.org/).
3. The user-facing application (web, android, ios, your choice..) which calls the API and lets the user see and navigate in the map and shows the geodata. You can (and should) use existing components, such as the Mapbox SDK, or [Leaflet](http://leafletjs.com/).


# My project
- Map-based web application able to show car accidents in Great Britain

## Application description
- Showing car accidents within a radius from a draggable point
- Showing car accidents within a (administrative) region containing draggable point
- Showing car accidents on a selected road using draggable point

## Data source
- [Administrative boundaries of United Kingdoms](http://biogeo.ucdavis.edu/data/gadm2.8/shp/GBR_adm_shp.zip)
- [Road safety data](https://data.gov.uk/dataset/road-accidents-safety-data)
- [Roads of England](http://download.geofabrik.de/osm/europe/great-britain/england-latest-free.shp.zip)

## Technologies used
- [Java](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) (v1.8.0_20)
- [Mapbox GL JS](https://www.mapbox.com/mapbox-gl-js/api/) (v0.28.0)
- [PostgreSQL](http://www.postgresql.org/) (v9.6)
- [PostGIS](http://postgis.net/) (v2.2.4)
- [ogr2gui](https://sourceforge.net/p/ogr2gui/wiki/Home/)
- [Spark](http://sparkjava.com/)

## Application source code
- [https://github.com/richardpastorek/PDT/tree/master/src/main](https://github.com/richardpastorek/PDT/tree/master/src/main)

## Used data 
- [data](https://github.com/richardpastorek/PDT/tree/master/data)
- Roads data missing due to size (over 500MB)
