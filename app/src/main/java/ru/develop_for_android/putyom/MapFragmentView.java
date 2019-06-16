package ru.develop_for_android.putyom;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPolygon;
import com.here.android.mpa.common.GeoPolyline;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewRect;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapCircle;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapPolygon;
import com.here.android.mpa.mapping.MapPolyline;
import com.here.android.mpa.mapping.SupportMapFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

/**
 * This class encapsulates the properties and functionality of the Map view.
 */
class MapFragmentView {
    private static final int ADD_MARKER_MENU_ID = 0;
    private static final int REMOVE_MARKER_MENU_ID = 1;
    private static final int ADD_POLYGON_MENU_ID = 2;
    private static final int REMOVE_POLYGON_MENU_ID = 3;
    private static final int ADD_POLYLINE_MENU_ID = 4;
    private static final int REMOVE_POLYLINE_MENU_ID = 5;
    private static final int ADD_CIRCLE_MENU_ID = 6;
    private static final int REMOVE_CIRCLE_MENU_ID = 7;
    private static final int NAVIGATE_TO_MENU_ID = 8;

    private SupportMapFragment m_mapFragment;
    private AppCompatActivity m_activity;
    private Map m_map;

    private final LinkedList<MapPolygon> m_polygons = new LinkedList<>();
    private final LinkedList<MapPolyline> m_polylines = new LinkedList<>();
    private final LinkedList<MapCircle> m_circles = new LinkedList<>();
    private final LinkedList<MapMarker> m_map_markers = new LinkedList<>();

    private MapViewModel viewModel;
    private GeoCoordinate myCurrentLocation;
    private MapMarker myLocationMarker;

    MapFragmentView(AppCompatActivity activity) {
        m_activity = activity;
        viewModel = ViewModelProviders.of(activity).get(MapViewModel.class);
        viewModel.myPosition.observe(activity, location -> {
            if (location != null) {
                showCurrentPosition();
            }
        });
        initMapFragment();
    }

    private SupportMapFragment getMapFragment() {
        return (SupportMapFragment) m_activity.getSupportFragmentManager().findFragmentById(R.id.mapfragment);
    }

    private void initMapFragment() {
        /* Locate the mapFragment UI element */
        m_mapFragment = getMapFragment();

        // Set path of isolated disk cache
        String diskCacheRoot = Environment.getExternalStorageDirectory().getPath()
                + File.separator + ".isolated-here-maps";
        // Retrieve intent name from manifest
        String intentName = "";
        try {
            ApplicationInfo ai = m_activity.getPackageManager().getApplicationInfo(m_activity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            intentName = bundle.getString("INTENT_NAME");
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e("Failed to find intent name, NameNotFound: %s", e.getMessage());
        }

        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(diskCacheRoot, intentName);
        if (!success) {
            // Setting the isolated disk cache was not successful, please check if the path is valid and
            // ensure that it does not match the default location
            // (getExternalStorageDirectory()/.here-maps).
            // Also, ensure the provided intent name does not match the default intent name.
        } else {
            if (m_mapFragment != null) {
                /* Initialize the SupportMapFragment, results will be given via the called back. */
                m_mapFragment.init(error -> {

                    if (error == OnEngineInitListener.Error.NONE) {
                        /*
                         * If no error returned from map fragment initialization, the map will be
                         * rendered on screen at this moment.Further actions on map can be provided
                         * by calling Map APIs.
                         */
                        m_map = m_mapFragment.getMap();

                        /* Set the zoom level to the average between min and max zoom level. */
                        m_map.setZoomLevel(14);

                        m_activity.supportInvalidateOptionsMenu();

                    } else {
                        Timber.e(error.getThrowable(), "onEngineInitializationCompleted: " +
                                "ERROR=" + error.getDetails());
                    }
                });
            }
        }
    }

    boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, ADD_MARKER_MENU_ID, ADD_MARKER_MENU_ID, "Add Marker");
        menu.add(0, REMOVE_MARKER_MENU_ID, REMOVE_MARKER_MENU_ID, "Remove Marker");
        menu.add(0, ADD_POLYGON_MENU_ID, ADD_POLYGON_MENU_ID, "Add Polygon");
        menu.add(0, REMOVE_POLYGON_MENU_ID, REMOVE_POLYGON_MENU_ID, "Remove polygon");
        menu.add(0, ADD_POLYLINE_MENU_ID, ADD_POLYLINE_MENU_ID, "Add polyline");
        menu.add(0, REMOVE_POLYLINE_MENU_ID, REMOVE_POLYLINE_MENU_ID, "Remove polyline");
        menu.add(0, ADD_CIRCLE_MENU_ID, ADD_CIRCLE_MENU_ID, "Add circle");
        menu.add(0, REMOVE_CIRCLE_MENU_ID, REMOVE_CIRCLE_MENU_ID, "Remove circle");
        menu.add(0, NAVIGATE_TO_MENU_ID, NAVIGATE_TO_MENU_ID, "Navigate to added markers");

        return true;
    }

    /**
     * Create a MapPolygon and add the MapPolygon to active map view.
     */
    private void addPolygonObject() {
        // create an bounding box centered at current cent
        GeoBoundingBox boundingBox = new GeoBoundingBox(m_map.getCenter(), 1000, 1000);
        // add boundingbox's four vertices to list of Geocoordinates.
        List<GeoCoordinate> coordinates = new ArrayList<>();
        coordinates.add(boundingBox.getTopLeft());
        coordinates.add(new GeoCoordinate(boundingBox.getTopLeft().getLatitude(),
                boundingBox.getBottomRight().getLongitude(),
                boundingBox.getTopLeft().getAltitude()));
        coordinates.add(boundingBox.getBottomRight());
        coordinates.add(new GeoCoordinate(boundingBox.getBottomRight().getLatitude(),
                boundingBox.getTopLeft().getLongitude(), boundingBox.getTopLeft().getAltitude()));
        // create GeoPolygon with list of GeoCoordinates.
        GeoPolygon geoPolygon = new GeoPolygon(coordinates);
        // create MapPolygon with GeoPolygon.
        MapPolygon polygon = new MapPolygon(geoPolygon);
        // set line color, fill color and line width
        polygon.setLineColor(Color.RED);
        polygon.setFillColor(Color.GRAY);
        polygon.setLineWidth(12);
        // add MapPolygon to map.
        m_map.addMapObject(polygon);

        m_polygons.add(polygon);
    }

    /**
     * Create a MapPolyline and add the MapPolyline to active map view.
     */
    private void addPolylineObject() {
        // create boundingBox centered at current location
        GeoBoundingBox boundingBox = new GeoBoundingBox(m_map.getCenter(), 1000, 1000);
        // add boundingBox's top left and bottom right vertices to list of GeoCoordinates
        List<GeoCoordinate> coordinates = new ArrayList<>();
        coordinates.add(boundingBox.getTopLeft());
        coordinates.add(boundingBox.getBottomRight());
        // create GeoPolyline with list of GeoCoordinates
        GeoPolyline geoPolyline = new GeoPolyline(coordinates);
        MapPolyline polyline = new MapPolyline(geoPolyline);
        polyline.setLineColor(Color.BLUE);
        polyline.setLineWidth(12);
        // add GeoPolyline to current active map
        m_map.addMapObject(polyline);

        m_polylines.add(polyline);
    }


    /**
     * create a MapCircle and add the MapCircle to active map view.
     */
    private void addCircleObject() {
        // create a MapCircle centered at current location with radius 400
        MapCircle circle = new MapCircle(400.0, m_map.getCenter());
        circle.setLineColor(Color.BLUE);
        circle.setFillColor(Color.GRAY);
        circle.setLineWidth(12);
        m_map.addMapObject(circle);

        m_circles.add(circle);
    }

    /**
     * create a MapMarker and add the MapMarker to active map view.
     */
    private MapMarker addMapMarkerObject(int imageResource, GeoCoordinate coordinate) {
        // create an image from cafe.png.
        Image marker_img = new Image();
        try {
            marker_img.setImageResource(imageResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MapMarker marker = new MapMarker(coordinate, marker_img);

        m_map.addMapObject(marker);
        m_map_markers.add(marker);
        return marker;
    }

    private void navigateToMapMarkers(List<MapMarker> markers, int padding) {
        // find max and min latitudes and longitudes in order to calculate
        // geo bounding box so then we can map.zoomTo(geoBox, ...) to it.
        double minLat = 90.0d;
        double minLon = 180.0d;
        double maxLat = -90.0d;
        double maxLon = -180.0d;

        for (MapMarker marker : markers) {
            GeoCoordinate coordinate = marker.getCoordinate();
            double latitude = coordinate.getLatitude();
            double longitude = coordinate.getLongitude();
            minLat = Math.min(minLat, latitude);
            minLon = Math.min(minLon, longitude);
            maxLat = Math.max(maxLat, latitude);
            maxLon = Math.max(maxLon, longitude);
        }

        GeoBoundingBox box = new GeoBoundingBox(new GeoCoordinate(maxLat, minLon),
                new GeoCoordinate(minLat, maxLon));

        ViewRect viewRect = new ViewRect(padding, padding, m_map.getWidth() - padding * 2,
                m_map.getHeight() - padding * 2);
        m_map.zoomTo(box, viewRect, Map.Animation.LINEAR, Map.MOVE_PRESERVE_ORIENTATION);
    }

    public void showCurrentPosition() {
        Location myPosition = viewModel.myPosition.getValue();
        if (myPosition == null) return;
        GeoCoordinate myPositionCoordinate = new GeoCoordinate(myPosition.getLatitude(), myPosition.getLongitude(), myPosition.getAltitude());
        if (myCurrentLocation != null) {
            m_map_markers.remove(myLocationMarker);
            m_map.removeMapObject(myLocationMarker);
        }
        myLocationMarker = addMapMarkerObject(R.drawable.my_position, myPositionCoordinate);
        myCurrentLocation = myPositionCoordinate;
        m_map.setCenter(myPositionCoordinate, Map.Animation.NONE);
    }
}
