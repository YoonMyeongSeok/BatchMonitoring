#!/bin/bash
x=1
while [ $x -le 200000 ]
do
	echo "Welcome $x times"
	x=$(( $x + 1))
done
