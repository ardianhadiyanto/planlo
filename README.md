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

Assume that the latitude is 37.452 and longitude is -122.184, a list of growable plants in that
 area can be retrieved by sending the below request:
 
```
http://localhost:8080/plants?latitude=37.452&longitude=-122.184
```

## Acknowledgements
PlanLo relies heavily on 3 APIs:
1. [Trefle](https://trefle.io)
2. [phzmapi](https://phzmapi.org)
3. [Nominatim OpenStreetMap](https://nominatim.openstreetmap.org)