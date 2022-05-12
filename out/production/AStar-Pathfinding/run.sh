CUR=$PWD                              # save workspace directory to return

for line in $(find -type d -name src)  # find and loop through src paths
do
    eval cd $line                     # go to src
    eval javac *.java
    cd $CUR
done
eval java AStar small.csv 0 20 400