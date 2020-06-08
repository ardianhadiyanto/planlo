# PlanLo
PlanLo is an application that was created to address a specific problem most commonly experienced by amateur
 gardeners: to conveniently find out what are the growable plants on the area.
 
## How does PlanLo work?
PlanLo provides an endpoint which takes in as inputs the client's zipcode. Based on that information, PlanLo will
 then retrieve the hardiness zone from https://phzmapi.org by passing in the given zipcode. The API will return back
  information about the hardiness zone. One useful information is the temperature range associated with the hardiness
   zone. Next, PlanLo will make another request to https://trefle.io to retrieve allplants that are growable within
    the temperature range.

## Example
Assuming that you have planlo the zipcode is 94025, a list of growable plants in that area can be retrieved by sending
 the below
 request:
 
[http://localhost:8080/plants?zipcode=94025](http://localhost:8080/plants?zipcode=94025)

## Acknowledgements
PlanLo relies heavily on 3 APIs:
1. [Trefle](https://trefle.io)
2. [phzmapi](https://phzmapi.org)
