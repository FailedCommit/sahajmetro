



## Assumptions

1. Timezone is not considered
2. Peak Hour start times are accurate to minutes only. 


## Test Cases

1. TripRateService
   1. testTripRate_weekendMorningPeakHour
   2. testTripRate_weekdayEveningPeakHour
   3. testTripRate_weekendOffPeak
   4. testTripRate_weekdayOffPeak

2. FareCalculatorServiceImplTest
   1. testCalculate_singleDayFare
   2. testCalculate_singleWeekFare_noCap
   3. testCalculate_singleWeekFare_withCap
   4. testCalculate_multiWeekFare
   5. testCalculate_multiWeekFareWithCapping
   
3. FareCappingServiceImplTest
   1. testGetCapAmount_crossZone
   2. testGetCapAmount_ZoneOneToOne
   3. testGetCapAmount_ZoneTwoToTwo


