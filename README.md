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
There is an optimsation area to be aware of - if the Thread.sleep milliseconds is too low, because the graph is printing out so many path lines for the 'algorithm visualisation', 
there will not be enough time for the JFrame to process the path list - then switch the the final path. As a result it will freeze. 

Therefore, if you seek to reduce the sleep to 0, please REMOVE the 'plot.add(temp);' as well (Or you can just remove the enitre Try/Catch); otherwise the FINAL PATH will NOT print. 
 
