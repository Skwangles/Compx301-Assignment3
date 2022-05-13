# Compx301-Assignment3

## AStar
Uses the default Priority queue datastucture.

To allow time to view the Astar working at the end of '-----Astar implementation---' 
we have a try catch (labelled 'REMOVE TRY/CATCH FOR INSTANTANEOUS CALCULATION' 
which runs a Thread.sleep delaying the execution. 
If you would like instantaneous calculation feel free to remove the aforementioned try/catch

There will be a popup with an OK button - which will autoexit the program once clicked. 

## Plotting
We use a JFrame + Jpanel to draw the graph.

## Visualising the search

If you would like to see the A* working, i.e. all its branches, just add a 5th commandline argument (anything will do) - and it will apply a Thread.sleep and will draw the search as it happens. 

There is an optimsation area to be aware of - if the Thread.sleep milliseconds is too low, because the graph is printing out so many path lines for the 'algorithm visualisation' it will freeze upon drawing the final path (the path update calls are too close together)
, we have set the Thread.sleep for 30ms - determined from a pretty beefy computer - so If it doesn't give you a final path yr computer is bad. 

 
