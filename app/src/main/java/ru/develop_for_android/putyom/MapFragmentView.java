package ru.develop_for_android.putyom;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPolyline;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.ViewRect;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapPolyline;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.SupportMapFragment;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.Router;
import com.here.android.mpa.routing.RoutingError;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.develop_for_android.putyom.model.SmartDevice;
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

    private final LinkedList<MapPolyline> m_polylines = new LinkedList<>();
    private final LinkedList<MapMarker> m_map_markers = new LinkedList<>();
    private final List<MapObject> signMarkers = new ArrayList<>();
    private final List<MapObject> routes = new ArrayList<>();

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
        viewModel.devices.observe(activity, smartDevices -> {
            if (smartDevices == null || m_map == null) return;
            m_map.removeMapObjects(signMarkers);
            for (SmartDevice device : smartDevices) {
                addSign(device);
            }
        });
        viewModel.routesSource.observe(activity, doubles -> {
            if (doubles == null || m_map == null) return;
            m_map.removeMapObjects(routes);
            for (double[] routeSource : doubles) {
                createRoute(new GeoCoordinate(routeSource[0], routeSource[1]),
                        new GeoCoordinate(routeSource[2], routeSource[3]));
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

                        if (viewModel.devices.getValue() != null) {
                            m_map.removeMapObjects(signMarkers);
                            signMarkers.clear();
                            for (SmartDevice device : viewModel.devices.getValue()) {
                                addSign(device);
                            }
                        }
                        if (viewModel.routesSource.getValue() != null) {
                            m_map.removeMapObjects(routes);
                            routes.clear();
                            for (double[] routeSource : viewModel.routesSource.getValue()) {
                                createRoute(new GeoCoordinate(routeSource[0], routeSource[1]),
                                        new GeoCoordinate(routeSource[2], routeSource[3]));
                            }
                        }

                        m_activity.supportInvalidateOptionsMenu();

                    } else {
                        Timber.e(error.getThrowable(), "onEngineInitializationCompleted: " +
                                "ERROR=" + error.getDetails());
                    }
                });
            }
        }
    }

    private void createRoute(GeoCoordinate start, GeoCoordinate end) {
        /* Initialize a CoreRouter */
        CoreRouter coreRouter = new CoreRouter();

        /* Initialize a RoutePlan */
        RoutePlan routePlan = new RoutePlan();

        /*
         * Initialize a RouteOption.HERE SDK allow users to define their own parameters for the
         * route calculation,including transport modes,route types and route restrictions etc.Please
         * refer to API doc for full list of APIs
         */
        RouteOptions routeOptions = new RouteOptions();
        /* Other transport modes are also available e.g Pedestrian */
        routeOptions.setTransportMode(RouteOptions.TransportMode.PEDESTRIAN);
        routeOptions.setHighwaysAllowed(true);
        routeOptions.setRouteType(RouteOptions.Type.SHORTEST);
        /* Calculate 1 route. */
        routeOptions.setRouteCount(1);
        /* Finally set the route option */
        routePlan.setRouteOptions(routeOptions);

        /* Define waypoints for the route */
        /* START: 4350 Still Creek Dr */
        RouteWaypoint startPoint = new RouteWaypoint(start);
        /* END: Langley BC */
        RouteWaypoint destination = new RouteWaypoint(end);

        /* Add both waypoints to the route plan */
        routePlan.addWaypoint(startPoint);
        routePlan.addWaypoint(destination);

        /* Trigger the route calculation,results will be called back via the listener */
        coreRouter.calculateRoute(routePlan,
                new Router.Listener<List<RouteResult>, RoutingError>() {
                    @Override
                    public void onProgress(int i) {
                        /* The calculation progress can be retrieved in this callback. */
                    }

                    @Override
                    public void onCalculateRouteFinished(List<RouteResult> routeResults,
                                                         RoutingError routingError) {
                        /* Calculation is done. Let's handle the result */
                        if (routingError == RoutingError.NONE) {
                            if (routeResults.get(0).getRoute() != null) {
                                /* Create a MapRoute so that it can be placed on the map */
                                MapRoute m_mapRoute = new MapRoute(routeResults.get(0).getRoute());

                                /* Show the maneuver number on top of the route */
                                m_mapRoute.setManeuverNumberVisible(true);

                                /* Add the MapRoute to the map */
                                m_map.addMapObject(m_mapRoute);
                                routes.add(m_mapRoute);
                            }
                        }
                    }
                });
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

    void showCurrentPosition() {
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

    private void addSign(SmartDevice device) {
        GeoCoordinate deviceCoordinate = new GeoCoordinate(device.latitude, device.longitude);
        myLocationMarker = addMapMarkerObject(R.drawable.construction, deviceCoordinate);
        signMarkers.add(myLocationMarker);
    }
}
