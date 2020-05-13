# PlanLo
PlanLo is an application that was created to address a specific problem most commonly experienced by amateur
 gardeners: to conveniently find out what are the growable plants on the area.
 
## How does PlanLo work?
PlanLo provides an endpoint which takes in as inputs the client's geolocation (latitude and
 longitude). Based on that
 information, PlanLo will then:
1. Perform a reverse geocoding lookup to find out the client's zipcode.
2. Match the hardiness zone of that area.
3. Find out the temperature range associated with the hardiness zone.
4. Retrieve all plants that are growable within the temperature range.

## Acknowledgements
PlanLo relies heavily on 3 APIs:
1. 