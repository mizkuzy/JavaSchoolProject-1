package com.tsystems.controllers;

import com.javaschool.dto.TrainsStationsDTO;
import com.javaschool.dto.WaypointsStationsDTO;
import com.javaschool.services.StationService;
import com.javaschool.services.TrainService;
import com.tsystems.utils.DateTimeComponent;
import com.tsystems.utils.DateTimePatterns;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by bugav on 08.10.2017.
 */
@Controller
public class MainPageController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private StationService stationService;

    @Autowired
    private DateTimeComponent converter;

    @RequestMapping("/findTrains")
    public String redirectToTrainsResult(@RequestParam(value = "stationFrom") String stationFrom,
                                         @RequestParam(value = "stationTo") String stationTo,
                                         @RequestParam(value = "travelDate") String travelDate,
                                         Model model) {

        DateTime convertedDate = converter.convertStringToDate(travelDate, DateTimePatterns.COMMON_DATE_WITHOUT_TIME_AMERICAN.getValue());
        List<TrainsStationsDTO> trains = trainService.getTrainsByStationsAndDate(stationFrom, stationTo, convertedDate);

        model.addAttribute("trains", trains);

        return "trains";
    }

    @RequestMapping("/findStationWaypoints")
    public String redirectToWaypointsResult(@RequestParam(value = "stationName") String stationName,
                                         @RequestParam(value = "waypointDate") String travelDate2,
                                         Model model) {

        DateTime convertedDate2 = converter.convertStringToDate(travelDate2, DateTimePatterns.COMMON_DATE_WITHOUT_TIME_AMERICAN.getValue());
        List<WaypointsStationsDTO> waypoints = stationService.getStationsSchedule(stationName, convertedDate2);

        model.addAttribute("waypoints", waypoints);

        return "waypoints";
    }
}
