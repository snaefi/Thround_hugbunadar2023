# Flugverkefni
HBV401G Þróun hugbúnaðar

Testin virka ekki lengur en ég var í vandræðum með að láta FlightRepository skila lista af flugum eftir að ég tók NewFlightController út og breytti gamla. Testin virkuðu á gamla þar til ég fór að implimenta searchFlights fallið í FlightRepository. Þurfti þá að taka út sambærilegt fall sem var í FlightController og virkaði með Mock-inu og skilaði null og var því ekki að skila lista af flugum. Átta mig ekki á því hvernig ég get látið Test kalla í searchFlight í controllernum (og skilar null) en kalla í searchFlight fallið í FlightRepository þegar aðal kóðinn er keyrður. Hefur eitthvað með það að gera að searchFlight er í FlightRepositoryInterface en útfærslan annars staðar
