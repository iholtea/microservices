

Why use separate Data classes in 
Repository layer - named domain classes
And in Controller to be passed to client - named DTOs ?
	keep the domain model( database entities ) hidden from the client.
	allow to send or receive only the fields relevant to the client 
		( this could be done be JSON serialization - @JSONIgnore kind of stuff ?? 
		probably it is clearer/safer to use sepparate classes  )
	allow to validate client input. 
		( hmm, this could be done with de Domain Entites also :) )